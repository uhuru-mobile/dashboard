package com.uhuru.dashboard;

/**
 * Created by Thibaut on 21/04/15.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AppsFragment extends Fragment {

    private String TAG = "Dashboard";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_apps, container, false);
    }


    public void onResume(){
        Log.i(TAG, "onResume APP");
        super.onResume();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Dashboard", "onActivityCreated APPS");

        SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(getActivity());
        List<SaveData> showdata;// = new ArrayList<SaveData>();
        showdata = db.showAll();

        ListView lvMain = (ListView) getActivity().findViewById(R.id.listViewApps);

        List<cellule> tableau = new ArrayList<>();

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        //list = new ArrayList<String>();

        for(SaveData save : showdata){
            if(save.getModule().equals("pdf-OK")) {
                cal.setTimeInMillis(Long.parseLong(save.getTimestamp()));
                String date = DateFormat.format("dd/MM/yyyy HH:mm:ss", cal).toString();
                tableau.add(0, new cellule(R.mipmap.notif_verte, getString(R.string.file) + save.getIncident() + getString(R.string.pdf_safe) , date, save.getComment()));
            }
            if(save.getModule().equals("pdf-SUSPICIOUS")) {
                cal.setTimeInMillis(Long.parseLong(save.getTimestamp()));
                String date = DateFormat.format("dd/MM/yyyy HH:mm:ss", cal).toString();
                tableau.add(0, new cellule(R.mipmap.notif_jaune, getString(R.string.file) + save.getIncident() + getString(R.string.pdf_suspicious) , date, save.getComment()));
            }
            if(save.getModule().equals("pdf-BAD")) {
                cal.setTimeInMillis(Long.parseLong(save.getTimestamp()));
                String date = DateFormat.format("dd/MM/yyyy HH:mm:ss", cal).toString();
                tableau.add(0, new cellule(R.mipmap.notif_rouge, getString(R.string.file) + save.getIncident() + getString(R.string.pdf_malicious) , date, save.getComment()));
            }
        }
        final celluleAdapter adapter = new celluleAdapter(getActivity(), tableau);
        lvMain.setAdapter(adapter);



        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                cellule cell = adapter.getItem(position);

                Intent intent = new Intent(getActivity(), PDFreport.class);
                intent.putExtra("content", cell.getComment());

                getActivity().startActivity(intent);


            }
        });

    }
}

