package com.uhuru.dashboard;

/**
 * Created by Thibaut on 21/04/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatsFragment extends Fragment {

    private String TAG = "Dashboard";

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        ImageButton refresh = (ImageButton) rootView.findViewById(R.id.refresh_stats_button);
        Log.i("Dashboard", "onCreateView TOP");
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCharts();
            }
        });

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Dashboard", "onActivityCreated STATS");
        updateCharts();
    }

    public void updateCharts(){
        ListView lvMain = (ListView) getActivity().findViewById(R.id.listViewStats);

        final GraphicItem[] items = new GraphicItem[6];
        SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(getActivity());
        List<SaveData> showdata;// = new ArrayList<>();
        showdata = db.showAll();

        int CompteurApplications = 0, CompteurLibrairies = 0, CompteurBinaires = 0, total, last_apps_total = 0, last_bin_total = 0, last_lib_total = 0;
        int CompteurPDFsains = 0, CompteurPDFsusp = 0, CompteurPDFbad = 0, total_pdf;

        float[][] pm_values = {{0,0}, {1,0}, {2, 0}, {3,0}, {4,0}, {5,0},{6,0}};
        float[][] execve_values = {{0,0}, {1,0}, {2, 0}, {3,0}, {4,0}, {5,0},{6,0}};
        float[][] lib_values = {{0,0}, {1,0}, {2, 0}, {3,0}, {4,0}, {5,0},{6,0}};

        final Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        int currentTime = date.getMinutes();
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

            save.getTimestamp();
            cal.setTimeInMillis(Long.parseLong(save.getTimestamp()));
            date = cal.getTime();
            if((currentTime - date.getMinutes()) < 7 && (currentTime - date.getMinutes()) >= 0){
                    if(save.getModule().equals("pm")) {
                        pm_values[6 - (currentTime - date.getMinutes())][1]++;
                        last_apps_total++;
                    } else if(save.getModule().equals("execve")) {
                        execve_values[6 - (currentTime - date.getMinutes())][1]++;
                        last_bin_total++;
                    } else if(save.getModule().equals("lib")) {
                        lib_values[6 - (currentTime - date.getMinutes())][1]++;
                        last_lib_total++;
                    }
                }

        }
        total = CompteurApplications + CompteurBinaires + CompteurLibrairies;
        double apps_chart = ((double)CompteurApplications * 100/(double)total);
        double bin_chart = ((double)CompteurBinaires * 100/(double)total);
        double lib_chart = ((double)CompteurLibrairies * 100/(double)total);

        ArrayList<PieChartItem> pie_items = new ArrayList<>();
        ArrayList<PieChartItem> pie_items2 = new ArrayList<>();
        ArrayList<LineChartItem> line_items = new ArrayList<>();

        if(apps_chart != 0){
            pie_items.add(new PieChartItem(apps_chart, getResources().getColor(R.color.MD_LightGreen_300), getResources().getColor(R.color.MD_LightGreen), getString(R.string.stat_app)));
            if (last_apps_total > 0){
                line_items.add(new LineChartItem(pm_values, getResources().getColor(R.color.MD_LightGreen), getString(R.string.stat_app)));
            }
        }
        if (bin_chart != 0){
            pie_items.add(new PieChartItem(bin_chart, getResources().getColor(R.color.MD_LightBlue_300), getResources().getColor(R.color.MD_LightBlue), getString(R.string.stat_bin)));
            if (last_bin_total > 0){
                line_items.add(new LineChartItem(execve_values, getResources().getColor(R.color.MD_LightBlue), getString(R.string.stat_bin)));
            }
        }if (lib_chart != 0){
            pie_items.add(new PieChartItem(lib_chart, getResources().getColor(R.color.MD_Amber_300), getResources().getColor(R.color.MD_Amber), "" + getString(R.string.stat_lib)));
            if (last_lib_total > 0){
                line_items.add(new LineChartItem(lib_values, getResources().getColor(R.color.MD_Amber), getString(R.string.stat_lib)));
            }
        }


        for(SaveData save : showdata) {
            if (save.getModule().equals("pdf-OK")) {
                CompteurPDFsains++;
            } else if (save.getModule().equals("pdf-SUSPICIOUS")) {
                CompteurPDFsusp++;
            } else if (save.getModule().equals("pdf-BAD")) {
                CompteurPDFbad++;
            }
        }


        total_pdf = CompteurPDFbad + CompteurPDFsains + CompteurPDFsusp;
        double safe_chart = ((double)CompteurPDFsains * 100/(double)total_pdf);
        double bad_chart = ((double)CompteurPDFbad * 100/(double)total_pdf);
        double susp_chart = ((double)CompteurPDFsusp * 100/(double)total_pdf);

        if(CompteurPDFsains != 0) {
            pie_items2.add(new PieChartItem(safe_chart, getResources().getColor(R.color.MD_LightGreen_300), getResources().getColor(R.color.MD_LightGreen), getString(R.string.safe)));
        }
        if (CompteurPDFbad != 0) {
            pie_items2.add(new PieChartItem(bad_chart, getResources().getColor(R.color.MD_Red_300), getResources().getColor(R.color.MD_Red), getString(R.string.bad)));
        }
        if (CompteurPDFsusp != 0) {
            pie_items2.add(new PieChartItem(susp_chart, getResources().getColor(R.color.MD_Orange_300), getResources().getColor(R.color.MD_Orange), getString(R.string.susp)));
        }


        items[0] = new GraphicItem(getString(R.string.security_events), GraphicAdapter.TYPE_SEPARATOR);
        items[1] = new GraphicItem(pie_items, total, GraphicAdapter.TYPE_PIE);

        items[2] = new GraphicItem(getString(R.string.last_events), GraphicAdapter.TYPE_SEPARATOR);
        items[3] = new GraphicItem(line_items, GraphicAdapter.TYPE_LINE);

        items[4] = new GraphicItem(getString(R.string.pdf_file), GraphicAdapter.TYPE_SEPARATOR);
        items[5] = new GraphicItem(pie_items2, total_pdf, GraphicAdapter.TYPE_PIE);

        GraphicAdapter customAdapter = new GraphicAdapter(getActivity(), R.id.text, items);
        lvMain.setAdapter(customAdapter);
    }


    /*ArrayList<String> list;
    ListAdapter la;
    private String TAG = "Dashboard";
    private PieChartItem app;
    private PieChartItem lib;
    private PieChartItem bin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        app = new PieChartItem(0, getResources().getColor(R.color.MD_LightGreen_300), getResources().getColor(R.color.MD_LightGreen));
        bin = new PieChartItem(0, getResources().getColor(R.color.MD_LightBlue_300), getResources().getColor(R.color.MD_LightBlue));
        lib = new PieChartItem(0, getResources().getColor(R.color.MD_Amber_300), getResources().getColor(R.color.MD_Amber));
        ImageButton button = (ImageButton) rootView.findViewById(R.id.imageButton4);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PieChartView pieChart = (PieChartView) getActivity().findViewById(R.id.pieChart);
                pieChart.invalidate();
                pieChart.refresh();
                update_pieChart();
            }
        });

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        update_pieChart();
    }

    public void update_pieChart(){
        SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(getActivity());
        List<SaveData> showdata = new ArrayList<SaveData>();
        showdata = db.showAll();

        int CompteurApplications = 0, CompteurLibrairies = 0, CompteurBinaires = 0, total;

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

        total = CompteurApplications + CompteurBinaires + CompteurLibrairies;
        double apps_chart = ((double)CompteurApplications * 100/(double)total);
        double bin_chart = ((double)CompteurBinaires * 100/(double)total);
        double lib_chart = ((double)CompteurLibrairies * 100/(double)total);
        final PieChartView pieChartView = (PieChartView) getActivity().findViewById(R.id.pieChart);
        Log.i(TAG, "onActivityCreated apps : " + apps_chart + ", bin : " + bin_chart + " lib : " + lib_chart);
        if(apps_chart != 0) {
            pieChartView.addValue(app);
            app.setValue(apps_chart);
        }
        if (bin_chart != 0) {
            pieChartView.addValue(bin);
            bin.setValue(bin_chart);
        }
        if (lib_chart != 0) {
            pieChartView.addValue(lib);
            lib.setValue(lib_chart);
        }

        TextView total_text = (TextView) getActivity().findViewById(R.id.text_total);
        total_text.setText("" + total);
    }*/

}
