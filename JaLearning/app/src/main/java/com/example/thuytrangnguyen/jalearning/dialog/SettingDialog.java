package com.example.thuytrangnguyen.jalearning.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thuytrangnguyen.jalearning.R;

/**
 * Created by Thuy Trang Nguyen on 12/8/2016.
 */
public class SettingDialog extends DialogFragment {
    Context context;
    View view;
    public SettingDialog(){

    }

    public static SettingDialog newInstance(String title) {
        SettingDialog frag = new SettingDialog();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_setting, container, false);

//        imageStarA = (ImageView)view.findViewById(R.id.imageStarA);
        context = getActivity();
        // lay gia tri tu item trong listview
//        Bundle bundle = this.getArguments();
//        if(bundle!=null){
//            hira = bundle.getString("hira");
//            tvJi.setText(hira);
//            ex = bundle.getString("ex");
//            // xu li chuoi vd thanh 4 xau cho 4 textview
//            String result[] = ex.split("\n");
//            String r1[] = result[0].split("\t");
//            String r2[] = result[1].split("\t");
//            tvEx1.setText(r1[0]);
//            tvMean1.setText(r1[1]);
//            tvEx2.setText(r2[0]);
//            tvMean2.setText(r2[1]);
//        }

        return view;
    }

}
