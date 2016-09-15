package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thuytrangnguyen.jalearning.R;

/**
 * Created by Thuy Trang Nguyen on 8/13/2016.
 */
public class Category extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.category, container, false);
        ListView lvCategory = (ListView)view.findViewById(R.id.lvCategory);
        return view;
    }
    public void onResume(){
        super.onResume();

        // Set title bar
//        ((MainActivity) getActivity())
//                .setActionBarTitle("Category");
    }
}
