package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.util.ArrayList;

/**
 * Created by Thuy Trang Nguyen on 8/10/2016.
 */
public class ItemCheckList extends ArrayAdapter<Word>{
    Activity context = null;
    ArrayList<Word> arrayList = null;
    int layoutId;

    public ItemCheckList(Activity context,int layoutId,ArrayList<Word>arr){
        super(context,layoutId,arr);
        this.context=context;
        this.layoutId=layoutId;
        this.arrayList=arr;
    }
    public View getView(int position,View convertView,ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId,null);
        if (arrayList.size()>0&&position>=0){
            TextView tvWord = (TextView)convertView.findViewById(R.id.tvWord);
            TextView tvMean = (TextView)convertView.findViewById(R.id.tvMean);
            CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkbox);
            Word word = arrayList.get(position);
            tvWord.setText(word.getWord().toString());
            tvMean.setText(word.getMean().toString());
            if (word.getStatus()==0)
                checkBox.setChecked(true);
            else checkBox.setChecked(false);
        }
        return convertView;
    }
}
