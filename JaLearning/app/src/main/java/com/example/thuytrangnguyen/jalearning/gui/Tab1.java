package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.gui.Answer;
import com.msquare.widget.mprogressbar.MProgressBar;

/**
 * Created by Thuy Trang Nguyen on 8/3/2016.
 */
public class Tab1 extends Fragment {
    private Context context;
    private MProgressBar mProgressBar;
    private TextView tvPro;
    int a;
    int i=1;
    TextView tvRank, tvRankonRank;
    int mProgressStatus=0;
    int mProgressStatus2=0;
    private Handler mHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab1, container, false);
        context = getActivity();

        mProgressBar = (MProgressBar)view.findViewById(R.id.mprocess);
        tvPro = (TextView)view.findViewById(R.id.txt_secondsleft);

        ImageButton imageButton1 = (ImageButton)view.findViewById(R.id.ib1);
        ImageButton imageButton2 = (ImageButton)view.findViewById(R.id.ib2);
        Button bt1 = (Button)view.findViewById(R.id.btMinus1);
        Button bt2 = (Button)view.findViewById(R.id.btPrepare1);
        tvRank = (TextView)view.findViewById(R.id.tvRank);
        tvRankonRank = (TextView)view.findViewById(R.id.tvRankonRank);
        a= 20;
        tvPro.setText(a+"%");
        setCircleProgress(20,60);


        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i>1)
                tab(--i);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i<5)
                tab(++i);
//                Tab2 f2 = new Tab2();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.layout1, f2);
//                fragmentTransaction.addToBackStack(null);
//                // Commit the transaction
//                fragmentTransaction.commit();
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(context);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(context);
            }
        });

        return view;
    }

    private void setCircleProgress(final int a,final int b){
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < a || mProgressStatus2 < b) {
                    if (mProgressStatus < a) {
                        mProgressStatus++;
                    }
                    mProgressStatus2++;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                            mProgressBar.setSecondaryProgress(mProgressStatus2);
                        }
                    });
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void showIntent(Context context){
        Intent intent = new Intent(context,Answer.class);
        context.startActivity(intent);
    }
    public void tab(int i){
        tvRank.setText("Rank "+i);
        tvRankonRank.setText(i+"/5");
    }
}
