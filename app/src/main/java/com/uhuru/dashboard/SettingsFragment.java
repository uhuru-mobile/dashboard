package com.uhuru.dashboard;

/**
 * Created by dorian on 21/04/15.
 */
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    ArrayList<String> list;
    ListAdapter la;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_parametres, container, false);
        Button flush_db_button = (Button) rootView.findViewById(R.id.flush_db);
        flush_db_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(getActivity());
                List<SaveData> showdata = new ArrayList<SaveData>();
                showdata = db.showAll();
                for(SaveData save : showdata){
                    db.deleteOne(save);
                }
            }
        });
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
