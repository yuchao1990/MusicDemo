package com.example.musicplayer.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.musicplayer.app.Api;
import com.example.musicplayer.app.App;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.entiy.Song;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 将user序列化到本地并取出的工具
 * Created by 残渊 on 2018/7/24.
 */


public class FileUtil {
    private static String TAG = "FileUtil";

    /**
     * 将person对象保存到文件中
     * params:
     * p:person类对象
     */

    public static void saveSong(Song song) {
        try {
            File file = new File(App.getContext().getExternalFilesDir("yuanmusic").getAbsolutePath());
            if (!file.exists()) {
                file.mkdirs();
            }
            //写对象流的对象
            File userFile = new File(file, "song.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile));
            oos.writeObject(song);//将Person对象p写入到oos中
            oos.close();                        //关闭文件流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读出对象，并且返回Person对象
     */

    public static Song getSong() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(App.getContext().getExternalFilesDir("") + "/yuanmusic/song.txt"));
            Song song = (Song) ois.readObject();//读出对象
            return song;                                       //返回对象
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Song song = new Song();
            return song;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //保存图片到本地
    public static void saveImgToNative(Context context, Bitmap bitmap, String singer) {
        File file = new File(Api.STORAGE_IMG_FILE);
        if (!file.exists()) {
            file.mkdirs();
        }
        File singerImgFile = new File(file, singer + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(singerImgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "saveImgToNative: fileNotFound");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //保存歌词到本地
    public static void saveLrcToNative(String lrc,String songName){
        //开启线程保存歌词
        new Thread(() -> {
            File file = new File(Api.STORAGE_LRC_FILE);
            if (!file.exists()) {
                file.mkdirs();
            }
            File lrcFile = new File(file, songName + Constant.LRC);
            try {
                FileWriter fileWriter = new FileWriter(lrcFile);
                fileWriter.write(lrc);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static String getLrcFromNative(String songName) {
        try {
            FileReader fileReader = new FileReader(Api.STORAGE_LRC_FILE+songName+Constant.LRC);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder lrc = new StringBuilder();
            while (true){
                String s = bufferedReader.readLine();
                if(s == null) break;
                lrc.append(s).append("\n");
            }
            fileReader.close();
            Log.d(TAG, "getLrcFromNative: "+lrc.toString());
            return lrc.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}