package com.example.thuytrangnguyen.jalearning.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
    List<Word> wordList = new ArrayList<>();
    ItemCheckList adapter;
    DatabaseHelper db;
    ListView lv;
    TextView tvCorrect,tvPasento;
    int level=5,bt;
    String table;
    ArrayList<Integer> listComplete = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_screen);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("toStop");
        level = bundle.getInt("level");
        bt = bundle.getInt("bt");
        table = bundle.getString("table");
        // nhan 1 list cac id  cua cac cau vua tra loi xong
        listComplete = intent.getIntegerArrayListExtra("listComplete");
        db = new DatabaseHelper(this);
        connectView();

    }
    @Override
    public void onClick(View v) {

    }

    public void connectView(){
        tvCorrect = (TextView)findViewById(R.id.tvCorrect);
        tvPasento = (TextView)findViewById(R.id.tvPasento);
        lv = (ListView)findViewById(R.id.lvComplete);
        arrList = new ArrayList<Word>();
        int correct = 0;
//        int i=0;
//        for(i=0;i<wordList.size();i++){
//            Word word = wordList.get(i);
//            arrList.add(word);
//        }

        // list cac cau vua tra loi xong
        for (int i=0;i<listComplete.size();i++){
            wordList = db.getListWordInStop(table,"N"+level,listComplete.get(i));
            Word word = wordList.get(0);
            if(word.getCheck()==1) correct++;
            arrList.add(word);
        }
        tvCorrect.setText("Chính xác: "+correct+"/10");
        tvPasento.setText("Tỉ lệ làm đúng: "+correct*10+"%");
        adapter = new ItemCheckList(this,R.layout.item_word_list,arrList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showNote(context, arrList.get(position).getId());
            }
        });
    }
    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        // bundle.putCharSequenceArrayList("number10",ques10);
        bundle.putInt("level", level);
        bundle.putString("table", table);
        Intent intent = new Intent(StopLearnActivity.this,MainActivity.class);
        intent.putExtra("back", bundle);
        startActivity(intent);
    }
}
