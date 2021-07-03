package com.example.dictionary.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AssetUtils {

    public static String getAssetsContent(Context context, String filename){
        AssetManager am = context.getResources().getAssets();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            InputStream is = am.open(filename);
            int hasRead = 0;
            byte[]buf = new byte[1024];
            while (true){
                hasRead = is.read(buf);
                if (hasRead==-1) {
                    break;
                }else{
                    baos.write(buf,0,hasRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toString();

    }
}
