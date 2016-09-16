package com.example.thuytrangnguyen.jalearning.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.helper.DatabaseHelper;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thuy Trang Nguyen on 8/10/2016.
 */
public class CheckList extends AppCompatActivity{

    private ArrayList<Word> arrList;
    private List<Word> wordList;
    ItemCheckList adapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list_note);

        db = new DatabaseHelper(this);
        connectView();
    }
    public void connectView(){
        ListView lv = (ListView)findViewById(R.id.list_note);
        arrList = new ArrayList<Word>();
        wordList = db.getListWord("n2");
        int i=0;
        for(i=0;i<wordList.size();i++){
            Word word = wordList.get(i);
            arrList.add(word);
        }
//        Word word = new Word();
//        word.setWord("Hello");
//        word.setMean("Xin chao");
//        word.setStatus(0);
//        arrList.add(word);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sxchuathuoc) {

            return true;
        }
        if (id == R.id.sxdathuoc) {

            return true;
        }
        if (id == R.id.tickall) {

            return true;
        }
        if (id == R.id.untickall) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
