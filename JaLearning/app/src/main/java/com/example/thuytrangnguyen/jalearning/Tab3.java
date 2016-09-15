package com.example.thuytrangnguyen.jalearning;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Thuy Trang Nguyen on 8/5/2016.
 */
public class Tab3 extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab3, container, false);
        ImageButton imageButton1 = (ImageButton)view.findViewById(R.id.ibL3);
        ImageButton imageButton2 = (ImageButton)view.findViewById(R.id.ibR3);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tab2 f2 = new Tab2();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout1, f2);
                fragmentTransaction.addToBackStack(null);
                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Tab2 f2 = new Tab2();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.layout1, f2);
//                fragmentTransaction.addToBackStack(null);
//                // Commit the transaction
//                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
