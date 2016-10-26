package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.thuytrangnguyen.jalearning.R;

public class LearnActivity extends AppCompatActivity {

    int bt;
    ListView lvComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        // get bundle de xac dinh bt1 hay bt2 duoc click de chon cau hoi chua tra loi hay tra loi sai
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("btClick");
        bt = bundle.getInt("bt");
        Log.d("bt",""+bt);
        lvComplete = (ListView)findViewById(R.id.lvComplete);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f1=new AnswerFragment();
        fragmentTransaction.add(R.id.layout2, f1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
