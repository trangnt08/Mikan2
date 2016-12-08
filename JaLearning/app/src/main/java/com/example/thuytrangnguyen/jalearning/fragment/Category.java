package com.example.thuytrangnguyen.jalearning.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
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
    int level, status_choice=0;
    String prefname = "mydata";
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
        restoringPreferences();
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
                savingPreferences();
                // Phan nay ko can nua vi da luu level vao sharePreferent roi
                /*
                Bundle bundle = new Bundle();
                // gui bt cho biet bt1 hay bt2 duoc click
                bundle.putInt("level", level);
                Fragment f1 = new Tab1();
                f1.setArguments(bundle);
                */
                Fragment f1 = new Tab1();
                getFragment(f1);
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

    /**
     * hàm lưu trạng thái
     */
    public void savingPreferences() {
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre=getActivity().getSharedPreferences(prefname, Context.MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.clear();
        editor.putInt("level",level);
        editor.putInt("status_choice",status_choice);
        //chấp nhận lưu xuống file
        editor.commit();
    }
    /**
     * hàm đọc trạng thái đã lưu trước đó
     */
    public void restoringPreferences()
    {
        SharedPreferences pre=getActivity().getSharedPreferences(prefname, Context.MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        level = pre.getInt("level",5);
        status_choice = pre.getInt("status_choice",0);
    }
}
