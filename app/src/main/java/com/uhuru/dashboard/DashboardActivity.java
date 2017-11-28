package com.uhuru.dashboard;
        import android.app.NotificationManager;
        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.view.ViewPager;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.RelativeLayout;


public class DashboardActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    private ViewPager viewPager;
    private RelativeLayout clickedLayout;
    private Toolbar toolbar;
    private static Intent logReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.groumf_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_36dp);

            toolbar.setSubtitle(R.string.system);
        }

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.drawerToggle = new ActionBarDrawerToggle(this,this.drawerLayout,0,0);
        this.drawerLayout.setDrawerListener(this.drawerToggle);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);


        String[] tabs = { getString(R.string.system), getString(R.string.applications_tab), getString(R.string.stats) , getString(R.string.action_settings) };

        // Adding Tabs
        for (String tab_name : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(tab_name));
        }

        /*tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));*/
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        onNewIntent(getIntent());

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                if (clickedLayout != null){
                    clickedLayout.setBackground(null);
                }
                switch (position) {
                    case 0:
                        toolbar.setSubtitle(R.string.system);
                        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_sys);
                        break;
                    case 1:
                        toolbar.setSubtitle(R.string.applications_tab);
                        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_apps);
                        break;
                    case 2:
                        toolbar.setSubtitle(R.string.stats);
                        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_stats);
                        break;
                    case 3:
                        toolbar.setSubtitle(R.string.action_settings);
                        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_settings);
                        break;
                    default:
                        toolbar.setSubtitle("");
                        break;
                }
                clickedLayout.setBackgroundColor(getResources().getColor(R.color.MD_Grey_300));
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(position * 10 + 1);
                nMgr.cancel(position * 10 + 2);
                nMgr.cancel(position * 10 + 3);
                nMgr.cancel(position * 10 + 4);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // synchroniser le drawerToggle après la restauration via onRestoreInstanceState
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            viewPager.setCurrentItem(3);
            return true;
        }else if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * générartion de logs de debug pour test
     */

    public void onClick_app(View v){
        Log.i("LogUhuru", "pm#0#com.groumf.root");
    }

    public void onClick_bin(View v){
        Log.i("LogUhuru", "execve#0#evilExploit");
    }

    public void onClick_lib(View v){
        Log.i("LogUhuru", "lib#0#libRoot_evil");
    }

     /** */

    @Override
    public void onNewIntent(Intent intent){
        logReader = new Intent(DashboardActivity.this, LogReader.class);
        startService(logReader);

        Bundle extras = getIntent().getExtras();
        int page;
        String from;
        if (extras != null && extras.containsKey("level") && extras.containsKey("title"))
        {
            page = extras.getInt("level");
            from = extras.getString("title");
            if (from != null && from.equals("notif")){
                viewPager.setCurrentItem(page);

                switch (page) {
                    case 0:
                        toolbar.setSubtitle(R.string.system);
                        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_sys);
                        break;
                    case 1:
                        toolbar.setSubtitle(R.string.applications_tab);
                        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_apps);
                        break;
                    case 2:
                        toolbar.setSubtitle(R.string.stats);
                        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_stats);
                        break;
                    case 3:
                        toolbar.setSubtitle(R.string.action_settings);
                        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_settings);
                        break;
                    default:
                        toolbar.setSubtitle("");
                        break;
                }

                getIntent().putExtra("level", 0);
                getIntent().putExtra("title", "");

                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(page * 10 + 1);
                nMgr.cancel(page * 10 + 2);
                nMgr.cancel(page * 10 + 3);
                nMgr.cancel(page * 10 + 4);
            }
        } else {
            Log.i("Dashboard", "onCreate extra is null or doesn't have the right fields!");
        }

        clickedLayout = (RelativeLayout) findViewById(R.id.drawer_sys);
        clickedLayout.setBackgroundColor(getResources().getColor(R.color.MD_Grey_300));
    }

    public void onClickDrawer(View v){
        switch (v.getId()){
            case R.id.drawer_sys:
                viewPager.setCurrentItem(0);
                break;
            case R.id.drawer_apps:
                viewPager.setCurrentItem(1);
                break;
            case R.id.drawer_stats:
                viewPager.setCurrentItem(2);
                break;
            case R.id.drawer_settings:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

    }
}