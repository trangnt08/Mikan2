package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.helper.DatabaseHelper;
import com.example.thuytrangnguyen.jalearning.item.ItemCheckList;
import com.example.thuytrangnguyen.jalearning.object.Question;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thuy Trang Nguyen on 10/13/2016.
 */
public class CompleteFragment extends Fragment{
    Context context;
    private ArrayList<Word> arrList, a2;
    private List<Word> wordList,wl;
    ItemCheckList adapter;
    DatabaseHelper db;
    ListView lvComplete;
    List<Question> questionsComplete = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.complete_screen, container, false);
        context=getActivity();
        db = new DatabaseHelper(context);
        lvComplete = (ListView)view.findViewById(R.id.lvComplete);

        arrList = new ArrayList<Word>();
        wl=db.getn2();
        Log.d("n22222222222222",""+wl.size());

        wordList = db.getListWord2(DatabaseHelper.N2,"n2");
        int i=0;
        for(i=0;i<wordList.size();i++){
            Word word = wordList.get(i);
            arrList.add(word);
        }
//        for(i=0;i<wl.size();i++){
//            Word word = wl.get(i);
//            a2.add(word);
//            Log.d("id"+word.getId(),"status"+word.getStatus());
//        }
        adapter = new ItemCheckList(getActivity(),R.layout.item_word_list,arrList);
        Log.d("adapter", "" + adapter.getCount());
        lvComplete.setAdapter(adapter);
        lvComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showNote(context, arrList.get(position).getId());
            }
        });
        //Bundle args = new Bundle();

        return view;
    }
}
