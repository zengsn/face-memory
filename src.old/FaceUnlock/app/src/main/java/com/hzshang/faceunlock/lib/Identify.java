package com.hzshang.faceunlock.lib;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;

import com.hzshang.faceunlock.R;
import com.hzshang.faceunlock.common.App;
import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import top.it138.facecheck.Connection;
import top.it138.facecheck.DriverManager;
import top.it138.facecheck.RecoginitionException;


public class Identify extends Async<byte[],String,Double> {
    private static Properties properties;

    static {
        properties = new Properties();
        properties.setProperty("appKey", "4344f36a-f3b2-4bdb-9cea-b250e6ca1a73");
        properties.setProperty("appSecret", "fd844b7e-2974-4daf-be59-73fbb078d488");
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        properties.setProperty("server", "http://10.77.116.186:8080/face");
    }
    public Identify(Context context, interFace delegate) {
        super(context, delegate);
    }
    @Override
    protected Double doInBackground(byte[]... params) {
        publishProgress(context.getString(R.string.identify));
        Double ret = 0.0;
        List<Map<String, Object>> faceMap =  Storage.getUsers(context);
        String faceId = (String)faceMap.get(0).get("faceId");
        File file = Storage.getFacePath(context, faceId);
        try {
            Class.forName("com.it138.impl.Driver");
        } catch (ClassNotFoundException e) {
            return ret;
        }
        try {
            byte[] face1 = readFileToByteArray(file);
            File pf = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "123.properties");
            if (pf.exists()) {

                Properties properties2 = new Properties();
                FileInputStream fis = new FileInputStream(pf);
                properties2.load(fis);
                fis.close();
                properties.setProperty("server", properties2.getProperty("server"));
            }
            Connection conn = DriverManager.getConnection(properties);
            return conn.distance(face1, params[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return ret;
        }

    }

    private byte[] readFileToByteArray(File file) throws IOException{
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (baos != null) {
                fis.close();
            }
        }
        return baos.toByteArray();


    }
}
