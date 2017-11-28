package com.uhuru.dashboard;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class UpdateWidgetService extends Service {
    private String last_event = "";
    private int last_event_level = 0;
    private String TAG = "Dashboard";
    SQLiteDatabaseHandler db;
    private List<SaveData> showdata = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        if (extras.containsKey("notif") && extras.containsKey("from")) {
            buildUpdate(extras.getString("notif"), extras.getInt("from"));
        }else {
            Log.i(TAG, "onStartCommand Pas de champ notif!");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void buildUpdate(String msg, int from) {
        db = new SQLiteDatabaseHandler(this);

        traitementNotif(msg, from);

        showdata = db.showAll();
        // on reinitialise avant de compter
        int CompteurLibrairies=0;
        int CompteurApplications=0;
        int CompteurBinaires=0;
        //lecture de la BDD pour la mise a jour de la vue
        for(SaveData save : showdata){
            if(save.getModule().equals("pm")){
                CompteurApplications++;
            }
            else if (save.getModule().equals("lib")){
                CompteurLibrairies++;
            }
            else if (save.getModule().equals("execve")){
                CompteurBinaires++;
            }
        }
        int total = CompteurBinaires + CompteurApplications + CompteurLibrairies;

        Log.i(TAG, "buildUpdate count app : " + CompteurApplications);
        Log.i(TAG, "buildUpdate count bin : " + CompteurBinaires);
        Log.i(TAG, "buildUpdate count lib : " + CompteurLibrairies);
        //Mise a jour de la vue
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.activity_main);

        view.setTextViewText(R.id.event_count, "" + total);
        view.setTextViewText(R.id.last_event, last_event);
        if (last_event_level == 1){
            view.setInt(R.id.event_status, "setBackgroundResource", R.drawable.status_warn);
        }else if (last_event_level == 2){
            view.setInt(R.id.event_status, "setBackgroundResource", R.drawable.status_critic);
        }else if (last_event_level == 3){
            view.setInt(R.id.event_status, "setBackgroundResource", R.drawable.status_critic);
        }else if (last_event_level == 0){
            view.setInt(R.id.event_status, "setBackgroundResource", R.drawable.status_ok);
        }

        // Push update for this widget to the home screen
        ComponentName thisWidget = new ComponentName(this, MainActivity.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, view);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void CustomNotification(String title, String message, int level, int from) {
        // Using RemoteViews to bind custom layouts into Notification
        int notif_layout;
        int icon_color;
        int cas = level + 10 * from;
        boolean ongoing = false;
        Log.i(TAG, "CustomNotification Cas n°" + cas);
        switch (cas){
            case 1:
                notif_layout = R.layout.notif_warn;
                icon_color = R.mipmap.notif_jaune;
                break;
            case 2:
                notif_layout = R.layout.notif_red;
                icon_color = R.mipmap.notif_rouge;
                break;
            case 3:
                notif_layout = R.layout.notif_red;
                icon_color = R.mipmap.notif_rouge;
                break;
            case 11:
                notif_layout = R.layout.notif_blue;
                icon_color = R.mipmap.notif_bleue;
                ongoing = true;
                break;
            case 12:
                notif_layout = R.layout.notif_green;
                icon_color = R.mipmap.notif_verte;
                break;
            case 13:
                notif_layout = R.layout.notif_warn;
                icon_color = R.mipmap.notif_jaune;
                break;
            case 14:
                notif_layout = R.layout.notif_red;
                icon_color = R.mipmap.notif_rouge;
                break;
            default:
                notif_layout = R.layout.notif_red;
                icon_color = R.mipmap.notif_rouge;
                break;
        }

        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                notif_layout);

        // Set Notification Title
        // Set Notification Text

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, DashboardActivity.class);
        // Send data to NotificationView Class
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("title", "notif");
        intent.putExtra("text", title);
        Log.i(TAG, "CustomNotification level = " + level );
        intent.putExtra("level", from);
        // Open NotificationView.java Activity

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        /*PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.mipmap.uhuru_blanc)
                        // Set Ticker Message
                .setTicker("")//getString(R.string.enrolement_string))
                        // Set PendingIntent into Notification
                .setOngoing(ongoing)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                        // Set RemoteViews into Notification
                .setContent(remoteViews);

        // Locate and set the Image into customnotificationtext.xml ImageViews
        remoteViews.setImageViewResource(R.id.imagenotileft, icon_color);

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title, getString(R.string.app_name));
        remoteViews.setTextViewText(R.id.text, message);//getString(R.string.mqtt_no_connect));

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(from * 10 + level, builder.build());

    }


    public void traitementNotif(String msg, int from){
        /*String[] FirstSplit = msg.split(":");
        int insert = 0; // Veriable pour savoir si on insert le log dans la BDD ou pas
        String[] CompareTimestamp = FirstSplit[1].split("#");*/ // split pour separer chaque partie
        int insert = 0;
        String[] CompareTimestamp = msg.split("#");
        for(int j =0; j< showdata.size(); j++) {
            if(CompareTimestamp[0].trim().equals(showdata.get(j).getTimestamp().trim())){
                System.out.println("COMPARE OK");
                insert=1;
                break;
            }
        }
        if(insert==0){
            if (CompareTimestamp[1].trim().equals("pdf")){
                CustomNotification("Dashboard", "UHURU has detected a new PDF file", 1, 1);
                Log.i(TAG, "traitementNotif new PDF " + CompareTimestamp[3].trim());
                new PdfAnalyzeTask().execute(CompareTimestamp[0].trim(), CompareTimestamp[1].trim(), CompareTimestamp[2].trim(), CompareTimestamp[3].trim());

            } else {

                //Insertion dans la BDD

                try {
                    SaveData Ajout = new SaveData(CompareTimestamp[0].trim(), CompareTimestamp[1].trim(), Integer.parseInt(CompareTimestamp[2].trim()), CompareTimestamp[3].trim());
                    db.addOne(Ajout);
                    Log.i(TAG, "traitementNotif last_event_level = " + last_event_level);
                    if (CompareTimestamp[1].trim().equals("execve")) {
                        CustomNotification("Dashboard", "Uhuru has blocked an unknown program!", 3, from);
                        last_event = getResources().getString(R.string.unknown_bin);
                        last_event_level = (last_event_level < 3 ? 3 : last_event_level);
                    } else if (CompareTimestamp[1].trim().equals("lib")) {
                        CustomNotification("Dashboard", "Uhuru has blocked an unknown library!", 2, from);
                        last_event = getResources().getString(R.string.unknown_lib);
                        last_event_level = (last_event_level < 2 ? 2 : last_event_level);
                    } else if (CompareTimestamp[1].trim().equals("pm")) {
                        CustomNotification("Dashboard", "Uhuru has blocked a forbidden app!", 1, from);
                        last_event = getResources().getString(R.string.unknown_app);
                        last_event_level = (last_event_level < 1 ? 1 : last_event_level);
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "Exception: " + ex.getMessage());
                }
            }

        }
    }

    private class PdfAnalyzeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {
            try {

                Process process = Runtime.getRuntime().exec("pdfAnalyzer " + param[3]);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                String prefix = "";
                String result = "";
                StringBuilder log = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null)
                {
                    log.append(prefix);
                    prefix = "\n";
                    log.append(line);
                    if (line.contains("Coef = ")){
                        result = line.split(" = ")[1];
                    }
                }
                Log.i(TAG, "doInBackground fin lecture, coeff = " + Integer.parseInt(result));
                Log.i(TAG, "doInBackground fin lecture, report = " + log.toString());
                return  "" + result + "#" + param[0] /* timestamp */ + "#" + param[1] /* module */ + "#" + param[2] /* verbosity */ + "#" + param[3] /* pdf name*/ + "#" + log.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "-1";
            }

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            String[] ret_value = result.split("#");
            int coef = Integer.parseInt(ret_value[0]);

            if (coef == -1){
                Log.i(TAG, "onPostExecute GROUMF!");
            } else{
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(11);

                if (0 <= coef && coef < 70){
                    // PDF sain
                    CustomNotification("Dashboard", "" + ret_value[4].split("/")[ret_value[4].split("/").length - 1] + " is safe", 2, 1);
                    // ajout du PDF dans la base de données
                    SaveData Ajout = new SaveData(ret_value[1], ret_value[2] + "-OK", Integer.parseInt(ret_value[3]), ret_value[4], ret_value[5]);
                    db.addOne(Ajout);
                } else if (70 <= coef && coef <= 90){
                    // PDF suspicieux
                    CustomNotification("Dashboard", "" + ret_value[4].split("/")[ret_value[4].split("/").length - 1] + " is suspicious!", 3, 1);
                    // ajout du PDF dans la base de données
                    SaveData Ajout = new SaveData(ret_value[1], ret_value[2] + "-SUSPICIOUS", Integer.parseInt(ret_value[3]), ret_value[4], ret_value[5]);
                    db.addOne(Ajout);
                } else if (coef > 90){
                    // PDF malveillant
                    CustomNotification("Dashboard", "" + ret_value[4].split("/")[ret_value[4].split("/").length - 1] + " is malicious!", 4, 1);
                    // ajout du PDF dans la base de données
                    SaveData Ajout = new SaveData(ret_value[1], ret_value[2] + "-BAD", Integer.parseInt(ret_value[3]), ret_value[4], ret_value[5]);
                    db.addOne(Ajout);
                }
            }
        }
    }

}
