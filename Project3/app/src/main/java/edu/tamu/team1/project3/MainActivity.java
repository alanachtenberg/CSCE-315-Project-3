package edu.tamu.team1.project3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;

public class MainActivity extends ActionBarActivity implements
        OnFragmentInteractionListener {

    //Data members
//------------------------------------------------------------------------------
    Toolbar tb;
    Context context;

    private String[] drawerItems;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence drawerTitle;
    private CharSequence title;

    //Lifecycle and Initialization
//------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        SharedPreferences preferences = context.getSharedPreferences("msettings", 0);
        boolean firstTime = preferences.getBoolean("FIRST_TIME", true);

        //if it is the first time opening the app, add the xml files in the
        //xml resource directory to internal storage
        try {
            if(firstTime) {
                Settings.create(this);
            }
            String theme = Settings.getSettingsTheme();

            if (theme.equals("Red")) {
                setTheme(R.style.Red);
            } else if (theme.equals("Green")) {
                setTheme(R.style.Green);
            } else if (theme.equals("Blue")) {
                setTheme(R.style.Blue);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            Log.e("SET THEME", e.getMessage());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOverflowMenu();
        setupActionBar();

        Fragment fragment = GameSetupFragment.newInstance();
        setFragment(fragment);

        setupNavDrawer();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
        }
        return super.onKeyDown(keyCode, event);
    }

    //ActionBar
//------------------------------------------------------------------------------
    //Forces three dot overflow on devices with hardware menu button.
    //Some might consider this a hack...
    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupActionBar() {
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(drawerList)) {
                    drawerLayout.closeDrawer(drawerList);
                }
                else {
                    drawerLayout.openDrawer(drawerList);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Navigation Drawer
//------------------------------------------------------------------------------
    private void setupNavDrawer() {
        drawerItems = new String[] {
                getResources().getString(R.string.title_game_fragment),
                getResources().getString(R.string.title_topics_fragment),
                getResources().getString(R.string.title_settings_fragment)
        };
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems));
        drawerList.setOnItemClickListener(new MainActivity.DrawerItemClickListener());

        title = drawerTitle = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                tb,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                tb.setTitle(title);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                tb.setTitle(drawerTitle);
                supportInvalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment = GameSetupFragment.newInstance();
        Bundle data = new Bundle();

        switch(position) {
            case 0:
                fragment = GameSetupFragment.newInstance();
                break;
            case 1:
                fragment = TopicsFragment.newInstance();
                break;
            case 2:
                fragment = SettingsFragment.newInstance();
                break;
        }

        fragment.setArguments(data);
        setFragment(fragment);
        drawerList.setItemChecked(position, true);
        setTitle(drawerItems[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        tb.setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
