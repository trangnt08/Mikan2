package com.example.thuytrangnguyen.jalearning.gui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Thuy Trang Nguyen on 10/13/2016.
 */
public class AnswerFragment extends Fragment implements View.OnClickListener{

    TextView tvQues, tvNumberQues, tvDemlui;
    TextToSpeech t1;
    Context context;
    ImageButton ibLoa;
    private List<Word> wordList;
    List<Question> questionsList;
    List<Question> ques10 = new ArrayList<>();
    private DatabaseHelper dbHelper;
    TextView tvA, tvB, tvC, tvD;
    TextView tickA, tickB, tickC, tickD;
    ProgressBar progressBar;
    private int isSoundon = 1 ;
    private String chooseanswer="";
    private String correct = "";
    private int numberquestion=0;
    Word curentWord = new Word();
    String toSpeak = "";
    String n="";
    int r;
    int next=0;
    int d;
    int t=0,bad;
    private final Handler timeHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.answer, container, false);
        tvQues = (TextView) view.findViewById(R.id.tvQues);
        tvNumberQues = (TextView) view.findViewById(R.id.tvNumberQues);
        tvDemlui = (TextView) view.findViewById(R.id.tvDemlui);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        ibLoa = (ImageButton) view.findViewById(R.id.ibLoa);
        tvA = (TextView) view.findViewById(R.id.tvA);
        tvB = (TextView) view.findViewById(R.id.tvB);
        tvC = (TextView) view.findViewById(R.id.tvC);
        tvD = (TextView) view.findViewById(R.id.tvD);
        tickA = (TextView) view.findViewById(R.id.textView_tickA);
        tickB = (TextView) view.findViewById(R.id.textView_tickB);
        tickC = (TextView) view.findViewById(R.id.textView_tickC);
        tickD = (TextView) view.findViewById(R.id.textView_tickD);
        context = getActivity();
        dbHelper = new DatabaseHelper(context);
        File database = getActivity().getDatabasePath(dbHelper.DB_NAME);
        if (false == database.exists()) {
            dbHelper.getReadableDatabase();
            if (copyDataBase(context)) {
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
            }
        }


        questionsList = dbHelper.getQuestions("n2");


        for(int j=0;j<questionsList.size();j++){
            Word w = new Word();
            w.setId(questionsList.get(j).getId());
            w.setWord(questionsList.get(j).getWord());
            w.setMean(questionsList.get(j).getMean());
            w.setStatus(0);
            w.setCheck(0);
        }
        wordList = dbHelper.getListWord("n2");
        Log.d("Size", "" + questionsList.size());

        toNextQuestion();
        learn();
        return view;
    }
    public void toNextQuestion(){
        setStatusOnClickABCD(true);
        t=0;
        progressBar.setProgress(59);
        progressBar.setMax(59);
        if(numberquestion<10){
            numberquestion++;
            chooseanswer="";
            updateScreen(next);
            ques10.add(questionsList.get(next));
            Log.d("size  = " + questionsList.size(), "i = " + next);
            next++;
        }else{
            //stopPlay();
        }
        countDownTimer.start();
    }

    //setup dem lui 5s ->(6000,1000)
    private final CountDownTimer countDownTimer = new CountDownTimer(5900, 100){
        public void onTick(long millisecond){
            d = (int) ((millisecond/100));
            progressBar.setProgress(d-5);
            if(d%10==0)
                tvDemlui.setText(Integer.toString(d/10-1));
            t++;
        }
        public void onFinish(){
            d=d-10;
            progressBar.setProgress(d);
            tvDemlui.setText(Integer.toString(0));
            if(numberquestion<10){
                toNextQuestion();
            }else{
                stopPlay();
            }
        }
    };

    public void stopPlay() {
        countDownTimer.cancel();
    }

    public void updateScreen(int n){
        Random ran = new Random();
        r = ran.nextInt(4);
        if(n>= questionsList.size()){
            return;
        }
        Question q = questionsList.get(n);
        tvNumberQues.setText(numberquestion + "/10");
        tvDemlui.setText("5");
        showAnswers(q, r);
    }
    public void showAnswers(Question question, int ran) {
        tvQues.setText(question.getWord().toString());
        toSpeak = tvQues.getText().toString();

        t1 = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.JAPANESE);
                // dòng t1. speak trong hàm onInit để phát âm luôn ko phải đợi click

                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
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
    public void onResume(){
        super.onResume();

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
    public void aEvent(){
        setStatusOnClickABCD(false);
        chooseanswer="A";
        getAnswer(chooseanswer, tvA, tickA);
    }

    public void bEvent(){
        setStatusOnClickABCD(false);
        chooseanswer="B";
        getAnswer(chooseanswer, tvB, tickB);
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
    public boolean trueAnswer(String ans){
        if(ans == correct) return true;
//        if(question.getMean().trim()==tv.getText().toString().trim())
//            return true;
        else return false;
    }

    public void getAnswer( final String chooseanswer, TextView backgroundanswer, TextView tick){
        countDownTimer.cancel();

        Question question = questionsList.get(next-1);

        Word word = new Word();
        word.setId(question.getId());
        word.setWord(question.getWord());
        word.setMean(question.getMean());

        if(trueAnswer(chooseanswer)) {
            //change layout button
            setCurrentScreen(backgroundanswer, tick, true);
            word.setCheck(1);
            Log.d("tttttttttttttt",""+t);
            if(t<=15) word.setStatus(1);
            else if(t>15 && t<=30) word.setStatus(2);
            else if(t>30 && t<=40) word.setStatus(3);
            else word.setStatus(5);

            n = "N2";
            dbHelper.updateWord(word,"N2");
        }else{
            word.setCheck(0);
            word.setStatus(5);
            setCurrentScreen(backgroundanswer,tick,false);
            Log.d("status ", "" + word.getStatus());
            Log.d("check ", "" + word.getCheck());
            dbHelper.updateWord(word, "N2");
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
            }, 100);
        }else{
            stopPlay();
        }

    }

    @Override
    public void onClick(View v) {
        tvA.setOnClickListener(this);
        tvB.setOnClickListener(this);
        tvC.setOnClickListener(this);
        tvD.setOnClickListener(this);
        ibLoa.setOnClickListener(this);
    }
}
