package com.example.thuytrangnguyen.jalearning.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.helper.DatabaseHelper;
import com.example.thuytrangnguyen.jalearning.item.ItemCheckList;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Thuy Trang Nguyen on 9/15/2016.
 */
public class StopLearnActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Word> arrList;
    private List<Word> wordList;
    ItemCheckList adapter;
    DatabaseHelper db;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_screen);
        db = new DatabaseHelper(this);
        connectView();
    }
    @Override
    public void onClick(View v) {

    }

    public void connectView(){
        lv = (ListView)findViewById(R.id.lvComplete);
        arrList = new ArrayList<Word>();
        wordList = db.getListWord2(DatabaseHelper.N2);
        int i=0;
        for(i=0;i<wordList.size();i++){
            Word word = wordList.get(i);
            arrList.add(word);
        }
        adapter = new ItemCheckList(this,R.layout.item_word_list,arrList);
        Log.d("adapter", "" + adapter.getCount());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showNote(context, arrList.get(position).getId());
            }
        });
    }
}
