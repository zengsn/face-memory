package edu.hzuapps.faceunlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.it138.facesdk.FaceApp;
import top.it138.facesdk.FaceAppException;
import top.it138.facesdk.Person;

public class InputActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final String EXTRA_PHOTO_IS_SET = "edu.hzuapps.faceunlock.InputActivity.photo_is_set";
    public static final int RESULT_CODE= 0;
    private final String LOG_TAG = this.getClass().getName();
    private SurfaceView surface;
    private SurfaceHolder holder;
    private Camera camera;
    private Button mButton;
    private ImageView mShowImageView;
    private Long personId;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        surface = (SurfaceView) findViewById(R.id.camera_surfaceview);

        holder = surface.getHolder();//获得句柄
        holder.addCallback(this);//添加回调
        mButton = (Button) findViewById(R.id.take_photo);
        mShowImageView = (ImageView) findViewById(R.id.show_photo);
        mButton.setOnClickListener(listener);
        sp = getSharedPreferences("main", Context.MODE_PRIVATE);
        personId = sp.getLong("personId", -1);
    }

    //点击响应Listenner
    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //快门
            camera.autoFocus(new Camera.AutoFocusCallback() {//自动对焦
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    //设置参数，并拍照
                    Camera.Parameters params = camera.getParameters();
                    params.setPictureFormat(PixelFormat.JPEG);//图片格式
                    params.setPreviewSize(800, 480);//图片大小
                    camera.setParameters(params);//将参数设置到我的camera

                    try {
                        camera.takePicture(null, null, jpeg);//将拍摄到的照片给自定义的对象
                    } catch (Exception e) {
                        show("拍照失败");
                    }
                }
            });
        }
    };

    private  void show(String text) {
        runOnUiThread(new ShowMsgRunable(text));
    }

    private class ShowMsgRunable implements Runnable {
        private String text;

        ShowMsgRunable(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            Toast.makeText(InputActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //当surfaceview创建时开启相机
        if (camera == null) {
            if (Camera.getNumberOfCameras() >= 2) {
                camera = Camera.open(1);
            } else {
                camera = Camera.open();
            }

            try {
                camera.setDisplayOrientation(90);
                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                camera.startPreview();//开始预览
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //当surfaceview关闭时，关闭预览并释放资源
        camera.stopPreview();
        camera.release();
        camera = null;
        holder = null;
        surface = null;
    }

    //创建jpeg图片回调数据对象
    Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                show("拍摄成功正在上传，请稍后");
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                // 定义矩阵对象
                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                //bmp.getWidth(), 500分别表示重绘后的位图宽高
                Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                        matrix, true);
                bitmap.recycle();
                File file = new File(getStorageDir(), "1.jpg");
                FileOutputStream fos = new FileOutputStream(file);
                dstbmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                //自定义文件保存路径  以拍摄时间区分命名
                mShowImageView.setImageBitmap(dstbmp);
                fos.close();

                //上传新增Person
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                dstbmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                camera.startPreview();//数据处理完后继续开始预览
                new AddPhotoThread(byteArray, "jpg").start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public File getStorageDir() {
        String albumName = "mypic";
        // 获得用户公共的图片目录
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }


    public static boolean wasPhotoSet(Intent result) {
        return result.getBooleanExtra(EXTRA_PHOTO_IS_SET, false);
    }

    class AddPhotoThread extends Thread {
        private byte[] image;
        private String type;

        public AddPhotoThread(byte[] image, String type) {
            this.image = image;
            this.type = type;
        }

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public void run() {
            FaceApp faceApp = Util.getFaceApp();
            try {
                if (personId == -1) {
                    personId = faceApp.createNewPersonId("App", "face Unlock");
                    sp.edit().putLong("personId", personId).commit();
                } else {
                    faceApp.getPersonById(personId).deleteAllPhotos();
                }
                Person p = faceApp.getPersonById(personId);
                p.addPhoto(image, type);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        Intent data = new Intent();
                        data.putExtra(EXTRA_PHOTO_IS_SET, true);
                        setResult(RESULT_CODE, data);
                        finish();
                    }
                });
            } catch (FaceAppException e) {
                e.printStackTrace();
                show("发生错误:" + e.getMessage());
            }
        }
    }
}
