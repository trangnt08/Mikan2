package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.item.ItemCategory;
import com.example.thuytrangnguyen.jalearning.object.Catego;

import java.util.ArrayList;

/**
 * Created by Thuy Trang Nguyen on 8/13/2016.
 */
public class Category extends Fragment{
    Context context = getActivity();
    ItemCategory adapter;
    ArrayList<Catego> arrayList = new ArrayList<>();
    int level;
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
        for(int i=1;i<=5;i++){
            Catego catego = new Catego(i,100);
            arrayList.add(catego);
        }
        arrayList.add(new Catego(0,100));
        adapter = new ItemCategory(getActivity(),R.layout.custom_level_category,arrayList);
        lvCategory.setAdapter(adapter);
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                level = arrayList.get(position).getN();
                Bundle bundle = new Bundle();
                // gui bt cho biet bt1 hay bt2 duoc click
                bundle.putInt("level", level);
                Fragment f1 = new Tab1();
                f1.setArguments(bundle);
                getFragment(f1);
//                Intent intent = new Intent(getActivity(),Answer.class);
//                intent.putExtra("getLevel",bundle);
//                startActivity(intent);
            }
        });
        return view;
    }

    public void getFragment(Fragment fi){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment f2=new Tab2();
        fragmentTransaction.replace(R.id.layout1, fi);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void onResume(){
        super.onResume();

        // Set title bar
//        ((MainActivity) getActivity())
//                .setActionBarTitle("Category");
    }

}
