package com.dbuzin.hashcalculator.utils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.os.EnvironmentCompat;

import com.dbuzin.tablayoutsample.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class SaveToFile {
    public static boolean saveToFile(String value, String name, Context context) throws IOException {
        boolean success = false;
        //TODO java.io.IOException: Permission denied исправить
        if(!value.equals(null) && value.length() > 0){
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),name + ".txt");
            if(file.createNewFile()){
                FileWriter mFileWriter = new FileWriter(file);
                mFileWriter.write(value);
                System.out.println("Пароль сохранен в " + file.getAbsolutePath());
                mFileWriter.close();
                success = true;
                //Делаем файл видимым для поиска
                MediaScannerConnection.scanFile(context,
                        new String[] { file.toString() }, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> uri=" + uri);
                            }
                        });
            }
        }
        return success;
    }
}
