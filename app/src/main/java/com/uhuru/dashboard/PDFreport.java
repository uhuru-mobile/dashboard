package com.uhuru.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PDFreport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfreport);
        Bundle extras = getIntent().getExtras();
        //Log.i(TAG, "onStartCommand " + extras.getString("notif"));
        if (extras.containsKey("content")) {
            TextView report = (TextView) findViewById(R.id.report_text);
            report.setText(extras.getString("content"));
        }else {
            Log.i("PDF_report", "NO REPORT!");
        }
    }
}
