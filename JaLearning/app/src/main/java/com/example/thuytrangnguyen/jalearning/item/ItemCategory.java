package com.example.thuytrangnguyen.jalearning.item;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.object.Catego;

import java.util.ArrayList;

/**
 * Created by Thuy Trang Nguyen on 8/13/2016.
 */
public class ItemCategory extends ArrayAdapter<Catego>{
    Activity context = null;
    ArrayList<Catego> arrayList = null;
    int layoutId;

    public ItemCategory(Activity context,int layoutId,ArrayList<Catego>arr){
        super(context,layoutId,arr);
        this.context=context;
        this.layoutId=layoutId;
        this.arrayList=arr;
    }
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        if (arrayList.size() > 0 && position >= 0) {
            TextView tvN = (TextView) convertView.findViewById(R.id.tvN);
            TextView tvNumberOfN = (TextView) convertView.findViewById(R.id.tvNumberOfN);
            ImageView imChar = (ImageView) convertView.findViewById(R.id.imChar);
            Catego catego = arrayList.get(position);
            tvN.setText("N"+catego.getN());
            tvNumberOfN.setText(""+catego.getNumber());
        }
        return convertView;
    }
}
