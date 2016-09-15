package com.example.thuytrangnguyen.jalearning.gui;

import android.content.Context;
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
 * Created by Thuy Trang Nguyen on 8/7/2016.
 */
public class Answer extends AppCompatActivity {

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
    private int numberquestion=1;
    private static final int MY_DATA_CHECK_CODE = 1234;
    String toSpeak = "";
    int r;
    int next=0;
    int i;
    private final Handler timeHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();

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

        //for(i=0;i<3;i++) {
            i=0;
            Random ran = new Random();
            r = ran.nextInt(4);
            Question q = questionsList.get(i);
            showAnswers(q, r);
        //}

//        Nếu có 3 dòng này thì tiếng sẽ phát trước khi activity này đc hiện ra.
//        Intent checkIntent = new Intent();
//        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        toSpeak = tvQues.getText().toString();
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.JAPANESE);
                // dòng t1. speak trong hàm onInit để phát âm luôn ko phải đợi click
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        ibLoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundEvent();
            }
        });
        tvA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aEvent(r);
                ca.start();
                next = 1;
                toNextQuestion();
            }
        });
        tvB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bEvent(r);
                ca.start();
                next = 2;
                toNextQuestion();
            }
        });
        tvC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cEvent(r);
                ca.start();
                next = 3;
                toNextQuestion();
            }
        });
        tvD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dEvent(r);
                ca.start();
                next = 4;
                toNextQuestion();
            }
        });
        Log.d("", "nexxxxxxxxxxtttttttttttt"+next);
        if (next == 1 || next==2 || next==3 || next==4){
            toNextQuestion();
            Log.d("","nexxxxxxxxxxtttttttttttt");
        }
    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
//            t1.shutdown();
        }
        super.onPause();
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

    public void showAnswers(Question question, int ran) {
        tvQues.setText(question.getWord().toString());
        switch (ran) {
            case 0:
                tvA.setText(question.getMean().toString());
                tvB.setText(question.getAns1().toString());
                tvC.setText(question.getAns2().toString());
                tvD.setText(question.getAns3().toString());
                break;
            case 1:
                tvB.setText(question.getMean().toString());
                tvA.setText(question.getAns1().toString());
                tvC.setText(question.getAns2().toString());
                tvD.setText(question.getAns3().toString());
                break;
            case 2:
                tvC.setText(question.getMean().toString());
                tvB.setText(question.getAns1().toString());
                tvA.setText(question.getAns2().toString());
                tvD.setText(question.getAns3().toString());
                break;
            default:
                tvD.setText(question.getMean().toString());
                tvB.setText(question.getAns1().toString());
                tvC.setText(question.getAns2().toString());
                tvA.setText(question.getAns3().toString());
                break;
        }
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

    public void aEvent(int ran){
            //chooseanswer = "A";
        if(ran==0) {
            tickA.setBackgroundResource(R.drawable.check);
            tvA.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_true));
        }else {
            tickA.setBackgroundResource(R.drawable.window_close);
            tvA.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_false));
        }
    }

    public void bEvent(int ran) {
        if (ran == 1) {
            tickB.setBackgroundResource(R.drawable.check);
            tvB.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_true));
        } else {
            tickB.setBackgroundResource(R.drawable.window_close);
            tvB.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_false));
        }
    }
    public void cEvent(int ran){
        if (ran == 2) {
            tickC.setBackgroundResource(R.drawable.check);
            tvC.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_true));
        } else {
            tickC.setBackgroundResource(R.drawable.window_close);
            tvC.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_false));
        }
    }

    public void dEvent(int ran){
        if (ran == 3) {
            tickD.setBackgroundResource(R.drawable.check);
            tvD.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_true));
        } else {
            tickD.setBackgroundResource(R.drawable.window_close);
            tvD.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview_false));
        }
    }

    CountDownTimer ca = new CountDownTimer(3000,100) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

        }
    };
    //setup dem lui 10s ->(11000,1000)
    private final CountDownTimer countDownTimer = new CountDownTimer(11000, 1000){
        public void onTick(long millisecond){
            tvDemlui.setText(Integer.toString((int) (millisecond / 1000)));
            tvA.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview));
            tickB.setBackgroundResource(0);
            tvB.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview));
            tickC.setBackgroundResource(0);
            tvC.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview));
            tickD.setBackgroundResource(0);
            tvD.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview));
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
    public void updateScreen(){
        //tick.setBackgroundResource(0);
        //backgroundanswer.setBackgroundResource(R.drawable.border_textview);
        if(i<questionsList.size()-1) {
            i++;
            tvNumberQues.setText(numberquestion + "/10");
            Random ran = new Random();
            r = ran.nextInt(4);
            Question q = questionsList.get(i);
            showAnswers(q, r);
//            tickA.setBackgroundColor(Color.parseColor("#ffffff"));
//            tvA.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview));
//            tickB.setBackgroundColor(Color.parseColor("#ffffff"));
//            tvB.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview));
//            tickC.setBackgroundColor(Color.parseColor("#ffffff"));
//            tvC.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview));
//            tickD.setBackgroundColor(Color.parseColor("#ffffff"));
//            tvD.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_textview));
        }
        //clock.setText("10");
    }

    public void toNextQuestion() {
        setStatusOnClickABCD(true);
        if (numberquestion < 10) {
            numberquestion++;
            updateScreen();
        } else {
            stopPlaying();
        }
        tickA.setBackgroundResource(0);


        countDownTimer.start();
    }
    public void stopPlaying(){
        //TODO
        countDownTimer.cancel();
//        timeHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intentStopPlay=new Intent(Answer.this,StopLearnActivity.class);
//                startActivity(intentStopPlay);
//            }
//        },1000);

    }

    public void getAnswer(final String chooseanswer, TextView backgroundanswer, TextView tick){
//        countDownTimer.cancel();
//
//        if(trueAnswer(chooseanswer)) {
//            //change layout button
//            setCurrentScreen(backgroundanswer,tick,true);
//
//            if(numberquestion<10) {
//                //time wait before next question
//                timeHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
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
//                        toNextQuestion();
//                    }
//                }, 1000);
//            }else{
//                stopPlaying();
//            }

//        }else{
//            setCurrentScreen(backgroundanswer,tick,false);
//            countDownTimer.cancel();
//            toNextQuestion();
//        }
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

}