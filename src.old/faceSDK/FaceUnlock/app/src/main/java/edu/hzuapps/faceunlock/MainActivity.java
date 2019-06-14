package edu.hzuapps.faceunlock;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    private Button button;
    private Switch faceSwich;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(listener);
        faceSwich = (Switch) findViewById(R.id.faceSwich);
        faceSwich.setChecked(isServiceWork(this, MyService.class.getName()));
        faceSwich.setOnClickListener(listener);
        sp  = getSharedPreferences("main", Context.MODE_PRIVATE);
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(4000);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                boolean wasSet = InputActivity.wasPhotoSet(data);
                sp.edit().putBoolean("wasSet", wasSet).commit();
            }

        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button: {
                    new AlertDialog.Builder(MainActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("重新进行录入将会清除之前的数据,确定继续吗？")//设置显示的内容
                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    Intent intent = new Intent(MainActivity.this, InputActivity.class);
                                    startActivityForResult(intent, REQUEST_CODE);
                                }
                            }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            finish();
                        }

                    }).show();
                    break;
                }
                case R.id.faceSwich : {
                    boolean wasSet = sp.getBoolean("wasSet", false);
                    if (!wasSet) {
                        Toast.makeText(MainActivity.this, "请先录入人脸", Toast.LENGTH_SHORT).show();
                        faceSwich.setChecked(false);
                        return;
                    }
                    if (!faceSwich.isChecked()) {
                        Intent intent = new Intent(MainActivity.this, MyService.class);
                        stopService(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, MyService.class);
                        startService(intent);
                    }
                }
            }

        }
    };
}
