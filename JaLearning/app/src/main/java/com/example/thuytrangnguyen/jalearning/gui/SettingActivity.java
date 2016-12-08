package com.example.thuytrangnguyen.jalearning.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thuytrangnguyen.jalearning.R;

/**
 * Created by Thuy Trang Nguyen on 12/8/2016.
 */
public class SettingActivity extends AppCompatActivity{
    Context context;
    ImageView imFlashCard,imQuesAns;
    TextView tv;
    int status_choice=0, level=5;
    String prefname = "mydata";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setting);
        context = this;
        init();
    }

    public void init(){
        imFlashCard = (ImageView)findViewById(R.id.imFlashCard);
        imQuesAns = (ImageView)findViewById(R.id.imQuesAns);
        tv = (TextView)findViewById(R.id.tvChoice);
        imFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoringPreferences();
                status_choice=1;
                savingPreferences();
            }
        });
        imQuesAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoringPreferences();
                status_choice = 0;
                savingPreferences();
            }
        });
    }
    private void showIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    /**
     * hàm lưu trạng thái
     */
    public void savingPreferences() {
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre=getSharedPreferences(prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.clear();
        editor.putInt("status_choice",status_choice);
        editor.putInt("level",level);
        //chấp nhận lưu xuống file
        editor.commit();
    }
    /**
     * hàm đọc trạng thái đã lưu trước đó
     */
    public void restoringPreferences()
    {
        SharedPreferences pre=getSharedPreferences
                (prefname, MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        status_choice = pre.getInt("status_choice",0);
        level = pre.getInt("level",5);
//        tv.setText(""+status_choice);
    }
}
