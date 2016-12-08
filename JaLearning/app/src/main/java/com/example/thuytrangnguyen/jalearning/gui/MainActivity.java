package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.fragment.Category;
import com.example.thuytrangnguyen.jalearning.fragment.Tab1;
import com.example.thuytrangnguyen.jalearning.helper.DatabaseHelper;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Word> arrList;
    CheckList adapter;
    Context context;
    int level=5;
    String table= DatabaseHelper.N5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle;
        if((bundle= intent.getBundleExtra("back"))!=null) {
            level = bundle.getInt("level");
            table = bundle.getString("table");
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f1=new Tab1();
        f1.setArguments(bundle);
        fragmentTransaction.add(R.id.layout1, f1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        context = this;

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void getFragment(Fragment fi){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment f2=new Tab2();
        fragmentTransaction.replace(R.id.layout1, fi);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            if(getFragmentManager().getBackStackEntryCount() > 1)
//                getFragmentManager().popBackStack();
//            else
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            MainActivity.this.finish();
                            finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.meNote){
            showNote(context);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSetting();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.luyentap) {
            Fragment f1 = new Tab1();
            getFragment(f1);
            setActionBarTitle("Luyện tập");
        } else if (id == R.id.test) {

        } else if (id == R.id.category) {
            Fragment category = new Category();
            getFragment(category);
            setActionBarTitle("Category");
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.caidat) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showNote(Context context){
        Intent i = new Intent(context,CheckList.class);
        context.startActivity(i);
    }
    public void getSetting(){
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_setting); //Hiện thị Dialog với layout custom_Dialog
//        dialog.setTitle("Setting");
//        dialog.setCancelable(true);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.imFlashCard);
//        imageView.setImageResource(R.drawable.flashcard_icon);
//        dialog.show();
        Intent i = new Intent(context,SettingActivity.class);
        context.startActivity(i);
    }
}
