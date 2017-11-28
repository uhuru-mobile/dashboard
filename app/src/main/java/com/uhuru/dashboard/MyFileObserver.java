package com.uhuru.dashboard;

/**
 * Created by Thibaut on 13/01/16.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.FileObserver;
import android.util.Log;
import android.webkit.MimeTypeMap;

public class MyFileObserver extends FileObserver {

    public String absolutePath;
    private String TAG = "iNotify";

    public MyFileObserver(String path) {

        super(path, FileObserver.ALL_EVENTS);

        absolutePath = path;
        Log.i("iNotify", "MyFileObserver est initialisé ");

    }

    @Override

    public void onEvent(int event, String path) {

        /*if (path == null) {

            return;

        }*/

        //a new file or subdirectory was created under the monitored directory

        /*if ((FileObserver.CREATE & event)!=0) {

            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is created\n";

        }

        //a file or directory was opened

        if ((FileObserver.OPEN & event)!=0) {

            FileAccessLogStatic.accessLogMsg += path + " is opened\n";

        }*/

        //data was read from a file

        if ((FileObserver.ACCESS & event)!=0) {

            //FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is accessed/read\n";
            Log.i(TAG, "onEvent file " + absolutePath + path + " was accessed");

        }

        //data was written to a file

        if ((FileObserver.MODIFY & event)!=0) {

            if (getMimeType("" + absolutePath + "/" + path).split("/")[1].equals("pdf") || getMimeType("" + absolutePath + path).split("/")[1].equals("PDF")){
                //Log.i(TAG, "onEvent new pdf file detected " + absolutePath + path);
                Log.i("LogUhuru", "pdf#0#" + absolutePath + "/" + path);
            }

        }

    }


    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}

