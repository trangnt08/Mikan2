package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.helper.DatabaseHelper;
import com.example.thuytrangnguyen.jalearning.object.Question;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Thuy Trang Nguyen on 9/15/2016.
 */
public class Learning extends AppCompatActivity implements View.OnClickListener {
    TextView tvQues, tvNumberQues, tvDemlui;
    TextToSpeech t1;
    Context context;
    ImageButton ibLoa;
    private List<Word> wordList;
    List<Question> questionsList;
    private DatabaseHelper dbHelper;
    TextView tvA, tvB, tvC, tvD;
    TextView tickA, tickB, tickC, tickD;
    private int isSoundon = 1 ;
    private String chooseanswer="";
    private String correct = "";
    private int numberquestion=0;
    private static final int MY_DATA_CHECK_CODE = 1234;
    String toSpeak = "";
    int r;
    int next=0;
    int i;
    private final Handler timeHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.answer);
        dbHelper = new DatabaseHelper(this);
        File database = getApplicationContext().getDatabasePath(dbHelper.DB_NAME);
        if (false == database.exists()) {
            dbHelper.getReadableDatabase();
            if (copyDataBase(this)) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //wordList = dbHelper.getListWord("n2");
        questionsList = dbHelper.getQuestions("n2");
        Log.d("Size", "" + questionsList.size());

        connectView();
        toNextQuestion();
        learn();
    }


    public void connectView() {
        tvQues = (TextView) findViewById(R.id.tvQues);
        tvNumberQues = (TextView) findViewById(R.id.tvNumberQues);
        tvDemlui = (TextView) findViewById(R.id.tvDemlui);
        ibLoa = (ImageButton) findViewById(R.id.ibLoa);
        tvA = (TextView) findViewById(R.id.tvA);
        tvB = (TextView) findViewById(R.id.tvB);
        tvC = (TextView) findViewById(R.id.tvC);
        tvD = (TextView) findViewById(R.id.tvD);
        tickA = (TextView) findViewById(R.id.textView_tickA);
        tickB = (TextView) findViewById(R.id.textView_tickB);
        tickC = (TextView) findViewById(R.id.textView_tickC);
        tickD = (TextView) findViewById(R.id.textView_tickD);
    }
    private boolean copyDataBase(Context context) {

        try {
            //Open your local db as the input stream
            InputStream myInput = context.getAssets().open(dbHelper.DB_NAME);

            // Path to the just created empty db
            String outFileName = dbHelper.DB_PATH + dbHelper.DB_NAME;

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void learn(){
        timeProcess();
        getOnClick();
    }

    public void timeProcess(){
        countDownTimer.start();



    }

    public void getOnClick(){
        tvA.setOnClickListener(this);
        tvB.setOnClickListener(this);
        tvC.setOnClickListener(this);
        tvD.setOnClickListener(this);
        ibLoa.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tvA:
                if(next<questionsList.size()) {
                    aEvent();
//                    Log.d("size  = "+questionsList.size(),"i = "+next);
                }
                break;
            case R.id.tvB:
                if(next<questionsList.size()) {
                    bEvent();
//                    Log.d("size  = " + questionsList.size(), "i = " + next);
                }
                break;
            case R.id.tvC:
                if(next<questionsList.size()) {
                    cEvent();
//                    Log.d("size  = " + questionsList.size(), "i = " + next);
                }
                break;
            case R.id.tvD:
                if(next<questionsList.size()) {
                    dEvent();
//                    Log.d("size  = " + questionsList.size(), "i = " + next);
                }
                break;
            case R.id.ibLoa:
                soundEvent();
                break;
        }
    }

    public void aEvent(){
        setStatusOnClickABCD(false);
        chooseanswer="A";
        getAnswer(chooseanswer,tvA,tickA);
    }

    public void bEvent(){
        setStatusOnClickABCD(false);
        chooseanswer="B";
        getAnswer(chooseanswer,tvB,tickB);
    }

    public void cEvent(){
        setStatusOnClickABCD(false);
        chooseanswer="C";
        getAnswer(chooseanswer, tvC, tickC);
    }

    public void dEvent(){
        setStatusOnClickABCD(false);
        chooseanswer="D";
        getAnswer(chooseanswer, tvD, tickD);
    }

    public void soundEvent(){
        if(isSoundon==1){
            isSoundon=0;
            ibLoa.setImageResource(R.drawable.ic_volume_off);
            onPause();
        } else{
            isSoundon=1;
            ibLoa.setImageResource(R.drawable.ic_volume_down_black_24dp);
            sound();
        }
    }
    public void sound(){
        String toSpeak = tvQues.getText().toString();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void setStatusOnClickABCD(boolean status){
        //status=false: remove onClick from button
        //status=true:
        tvA.setClickable(status);
        tvB.setClickable(status);
        tvC.setClickable(status);
        tvD.setClickable(status);
    }

    public void stopPlaying(){
        //TODO
        countDownTimer.cancel();
        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentStopPlay = new Intent(Learning.this, StopLearnActivity.class);
                startActivity(intentStopPlay);
            }
        }, 1000);

    }

    //setup dem lui 10s ->(11000,1000)
    private final CountDownTimer countDownTimer = new CountDownTimer(6000, 1000){
        public void onTick(long millisecond){
            tvDemlui.setText(Integer.toString((int) (millisecond / 1000)));
        }
        public void onFinish(){
            tvDemlui.setText(Integer.toString(0));
            if(numberquestion<10){
                toNextQuestion();
            }else{
                stopPlaying();
            }
        }
    };
    public void setCurrentScreen(TextView backgroundanswer,TextView tick,boolean isTrue){
        //neu tra loi dung
        if(isTrue){
            tick.setBackgroundResource(R.drawable.iconticktrue);
            backgroundanswer.setBackgroundResource(R.drawable.border_textview_true);
        }else{
            tick.setBackgroundResource(R.drawable.window_close);
            backgroundanswer.setBackgroundResource(R.drawable.border_textview_false);
        }
    }

    public void updateScreen(int n){
        Random ran = new Random();
        r = ran.nextInt(4);
        if(n>= questionsList.size()){
            return;
        }
        Question q = questionsList.get(n);
        //tick.setBackgroundResource(0);
        //backgroundanswer.setBackgroundResource(R.drawable.border_textview);

        tvNumberQues.setText(numberquestion+"/10");
        tvDemlui.setText("10");
        showAnswers(q,r);
    }

    public void toNextQuestion(){
        setStatusOnClickABCD(true);
        if(numberquestion<10){
            numberquestion++;
            chooseanswer="";
            updateScreen(next);
            Log.d("size  = " + questionsList.size(), "i = " + next);
            next++;
        }else{
            stopPlaying();
        }

        //update question

        countDownTimer.start();
    }

    public boolean trueAnswer(String ans){
        if(ans == correct) return true;
//        if(question.getMean().trim()==tv.getText().toString().trim())
//            return true;
        else return false;
    }

    public void showAnswers(Question question, int ran) {
        tvQues.setText(question.getWord().toString());
        toSpeak = tvQues.getText().toString();

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.JAPANESE);
                // dòng t1. speak trong hàm onInit để phát âm luôn ko phải đợi click

//                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        switch (ran) {
            case 0:
                tvA.setText(question.getMean().toString());
                tvB.setText(question.getAns1().toString());
                tvC.setText(question.getAns2().toString());
                tvD.setText(question.getAns3().toString());
                correct = "A";
                break;
            case 1:
                tvB.setText(question.getMean().toString());
                tvA.setText(question.getAns1().toString());
                tvC.setText(question.getAns2().toString());
                tvD.setText(question.getAns3().toString());
                correct = "B";
                break;
            case 2:
                tvC.setText(question.getMean().toString());
                tvB.setText(question.getAns1().toString());
                tvA.setText(question.getAns2().toString());
                tvD.setText(question.getAns3().toString());
                correct = "C";
                break;
            default:
                tvD.setText(question.getMean().toString());
                tvB.setText(question.getAns1().toString());
                tvC.setText(question.getAns2().toString());
                tvA.setText(question.getAns3().toString());
                correct = "D";
                break;
        }
    }
    public void getAnswer( final String chooseanswer, TextView backgroundanswer, TextView tick){
        countDownTimer.cancel();

        if(trueAnswer(chooseanswer)) {
            //change layout button
            setCurrentScreen(backgroundanswer,tick,true);
        }else{
            setCurrentScreen(backgroundanswer,tick,false);
            //countDownTimer.cancel();
//            toNextQuestion();
        }
        if(numberquestion<10 && numberquestion<=questionsList.size()) {
            //time wait before next question
            timeHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (chooseanswer) {
                        case "A":
                            tickA.setBackgroundResource(0);
                            tvA.setBackgroundResource(R.drawable.border_textview);
                            break;
                        case "B":
                            tickB.setBackgroundResource(0);
                            tvB.setBackgroundResource(R.drawable.border_textview);
                            break;
                        case "C":
                            tickC.setBackgroundResource(0);
                            tvC.setBackgroundResource(R.drawable.border_textview);
                            break;
                        case "D":
                            tickD.setBackgroundResource(0);
                            tvD.setBackgroundResource(R.drawable.border_textview);
                            break;
                    }
                    toNextQuestion();
                }
            }, 1000);
        }else{
            stopPlaying();
        }

    }


    private Dialog makeDialog(){
        //khoi tao Dialog
        Dialog dialog=new Dialog(Learning.this);
        //set layout cho Dialog
        //dialog.setContentView();
        //set message for dialog
        dialog.setTitle("   Ban co muon thoat khong");

        return dialog;
    }
}
