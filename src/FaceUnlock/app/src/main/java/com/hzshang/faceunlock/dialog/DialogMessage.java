package com.hzshang.faceunlock.dialog;

import android.content.Context;
import android.widget.Toast;



public class DialogMessage {
    static public void showDialog(String out,Context context){
        Toast.makeText(context, out, Toast.LENGTH_LONG).show();
    }
}
