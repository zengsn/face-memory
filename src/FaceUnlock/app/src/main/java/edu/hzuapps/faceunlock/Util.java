package edu.hzuapps.faceunlock;

import top.it138.facesdk.FaceApp;
import top.it138.facesdk.FaceAppFactory;

/**
 * Created by Lenovo on 2017/9/11 0011.
 */

public class Util {
    private static final String APP_KEY = "4344f36a-f3b2-4bdb-9cea-b250e6ca1a73";
    private static final String APP_SECRET = "fd844b7e-2974-4daf-be59-73fbb078d488";
    public static FaceApp getFaceApp() {
        return FaceAppFactory.getBykeyAndSecret(APP_KEY, APP_SECRET);
    }
}
