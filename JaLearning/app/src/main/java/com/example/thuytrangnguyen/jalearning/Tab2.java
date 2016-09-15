package com.example.thuytrangnguyen.jalearning;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Thuy Trang Nguyen on 8/3/2016.
 */
public class Tab2 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab2, container, false);
        ListView lvCategory = (ListView)view.findViewById(R.id.lvCategory);
        return view;
    }
}
