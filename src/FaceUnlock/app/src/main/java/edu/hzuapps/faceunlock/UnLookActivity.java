package edu.hzuapps.faceunlock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import top.it138.facesdk.FaceApp;
import top.it138.facesdk.FaceAppException;
import top.it138.facesdk.Person;

/**
 * 直接new FullSreanActivity 进行修改
 */
public class UnLookActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private SurfaceView surface;
    private SurfaceHolder holder;
    private Camera camera;
    private Button mButton;
    private ImageView mShowImageView;
    private Long personId;
    private SharedPreferences sp;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };



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
                        e.printStackTrace();
                        show("拍照失败");
                    }
                }
            });
        }
    };

    private  void show(String text) {
        runOnUiThread(new UnLookActivity.ShowMsgRunable(text));
    }

    private class ShowMsgRunable implements Runnable {
        private String text;

        ShowMsgRunable(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            Toast.makeText(UnLookActivity.this, text, Toast.LENGTH_SHORT).show();
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
                e.printStackTrace();
                show("打开相机失败");
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
                Toast.makeText(UnLookActivity.this, "拍摄成功正在上传，请稍后", Toast.LENGTH_SHORT).show();

                new UnLookActivity.AddPhotoThread(data).start();
            } catch (Exception e) {
                e.printStackTrace();
                enable();
            }
        }
    };


    class AddPhotoThread extends Thread {
        private byte[] image;
        private String type;

        public AddPhotoThread(byte[] image) {
            this.image = image;
            this.type = type;
        }

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }
        @Override
        public void run() {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            // 定义矩阵对象
            Matrix matrix = new Matrix();
            matrix.postRotate(-90);
            //bmp.getWidth(), 500分别表示重绘后的位图宽高
            Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);
            bitmap.recycle();
            File file = new File(getStorageDir(), "2.jpg");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                dstbmp.compress(Bitmap.CompressFormat.JPEG, 30, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
            //上传新增Person
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            dstbmp.compress(Bitmap.CompressFormat.JPEG, 30, stream);
            byte[] byteArray = stream.toByteArray();
            FaceApp faceApp = Util.getFaceApp();
            try {
                Person p = faceApp.getPersonById(personId);
                Double d = p.recognition(byteArray, "jpg");
                if (d > 0.8) {
                    show("识别成功");
                    finish();
                } else {
                    show("识别不通过，请重试。" + d);
                    enable();
                }
            } catch (FaceAppException e) {
                e.printStackTrace();
                show("发生错误:" + e.getMessage());
                enable();
            }
        }
    }

    public File getStorageDir() {
        String albumName = "mypic";
        // 获得用户公共的图片目录
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.d("UnLookActivity", "Directory not created");
        }
        return file;
    }

    private void enable() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                camera.startPreview();
                mButton.setEnabled(true);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_un_look);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        surface = (SurfaceView) findViewById(R.id.camera_surfaceview);

        holder = surface.getHolder();//获得句柄
        holder.addCallback(this);//添加回调
        mButton = (Button) findViewById(R.id.dummy_button);
        mShowImageView = (ImageView) findViewById(R.id.show_photo);
        mButton.setOnClickListener(listener);
        sp = getSharedPreferences("main", Context.MODE_PRIVATE);
        personId = sp.getLong("personId", -1);
        if (personId == -1) {
            finish();
        }

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
