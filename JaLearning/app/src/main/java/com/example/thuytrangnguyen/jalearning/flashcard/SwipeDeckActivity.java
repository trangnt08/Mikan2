package com.example.thuytrangnguyen.jalearning.flashcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.thuytrangnguyen.jalearning.R;
import com.example.thuytrangnguyen.jalearning.gui.StopLearnActivity;
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

/**
 * Created by haiyka on 27/10/2016.
 */

public class SwipeDeckActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private SwipeDeck cardStack;
    private Context context = this;
    TextToSpeech t1;

    private SwipeDeckAdapter adapter;
    private ArrayList<String> testData;

    private List<Word> wordList, wordList2 = new ArrayList<>();
    ArrayList<Integer> listComplete = new ArrayList<>();
    List<Question> questionsList = new ArrayList<>(), quesListAll;
    private DatabaseHelper dbHelper;
    private ProgressBar progressBar;
    private ImageButton ibLoa;
    private Button btn, btn2;
    private int numberquestion=0;
    private int isSoundon = 1 ;
    private String txt;
    String toSpeak = "";
    private final Handler timeHandler = new Handler();

    int d;
    int t=0;
    int next=0;
    TextView tvDemlui, tvNumberQuesCard,tvQuestion;

    int level = 5, status_choice = 0, bt;
    String prefname = "mydata";
    String table="";
    Word curentWord = new Word();
    boolean check = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_card);

        connectView();

        dbHelper = new DatabaseHelper(context);
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
        restoringPreferences();

        Intent intent = getIntent();
        Bundle bundle;
        if((bundle= intent.getBundleExtra("btClick"))!=null) {
            bt = bundle.getInt("bt");
//            level = bundle.getInt("level");
            table = bundle.getString("table");
        }

        if(level>0&&level<6) {
            table = DatabaseHelper.TABLE_BASIC;
        }
        else if(level==0) {
            table = DatabaseHelper.TABLE_IT;
        }

        quesListAll = dbHelper.getQuestions(table,"n"+level); // lay tat ca question co level = level
        wordList = dbHelper.getListWord2(table, "N" + level); // lay data ket noi tu bang table(TABLE_BASIC hoac TABLE_IT) va bang N

        if(bt==1){
            for(int j=0;j<wordList.size();j++){
                // add nhung tu tra loi sai (status =5)
                if(wordList.get(j).getStatus()==5){
                    questionsList.add(quesListAll.get(j));
                    wordList2.add(wordList.get(j));     // wordList2 la list cac tu co dieu kien
                }
            }
        }
        else if(bt==2){
            for(int j=0;j<wordList.size();j++){
                // add nhung tu chua tra loi (status =0)
                if(wordList.get(j).getStatus()==0){
                    questionsList.add(quesListAll.get(j));
                    wordList2.add(wordList.get(j));
                }
            }
        }
        else if(bt==3){
            questionsList = dbHelper.getQuestions(table,"n"+level); // lay tat ca cac tu
            wordList2 = wordList;
        }
        connectView();

        adapter = new SwipeDeckAdapter(wordList2, this);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
//                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
//                Log.d("MainActivity", "card was swiped right, position in adapter: " + position);
            }


            @Override
            public void cardsDepleted() {
//                Log.d("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {
//                Log.d(TAG, "cardActionDown");
            }

            @Override
            public void cardActionUp() {
//                Log.i(TAG, "cardActionUp");
            }
        });

        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);
        toNextQuestion();
//        countDownTimer.cancel();
//        timeHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                toNextQuestion();
//            }
//        },100);

        learn();

    }

    public void connectView(){
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        cardStack.setHardwareAccelerationEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        ibLoa = (ImageButton) findViewById(R.id.ibLoa2);
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        tvDemlui = (TextView) findViewById(R.id.tvdownCard);
        tvNumberQuesCard = (TextView)findViewById(R.id.tvNumberQuesCard);

    }

    public void learn(){
        timeProcess();
        getOnClick();
    }

    public void timeProcess(){
        countDownTimer.start();
    }

    public void getOnClick(){
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        ibLoa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                if(next<questionsList.size()) {
                    getClick(0); // tu chua thuoc
                }
                break;
            case R.id.button2:
                if(next<questionsList.size()) {
                    getClick(1);    // tu da thuoc
//                    cardStack.swipeTopCardRight(180);
                }
                break;
            case R.id.ibLoa2:
                soundEvent();
                break;
        }
    }


    public void getClick(int click){
        countDownTimer.cancel();
        Question question = questionsList.get(next-1);
        Word word = new Word();
        word.setId(question.getId());
        word.setWord(question.getWord());
        word.setMean(question.getMean());
        // Neu chon button2 la da biet tu do
        if(click == 1){
            word.setCheck(1);
            if(t<=15) word.setStatus(1);
            else if(t>15 && t<=30) word.setStatus(2);
            else word.setStatus(3);
            dbHelper.updateWord(word,"N"+level);
        }
        else {
            word.setCheck(0);
            word.setStatus(5);
            dbHelper.updateWord(word, "N"+level);
        }
        if(numberquestion<11 && numberquestion<=questionsList.size()){
            timeHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toNextQuestion();
                }
            },100);
        }else {
            stopPlaying();
        }
    }

    public void getNextSwipe(){
        countDownTimer.cancel();
        Question question = questionsList.get(next-1);
        Word word = new Word();
        word.setId(question.getId());
        word.setWord(question.getWord());
        word.setMean(question.getMean());
        // Neu chon button2 la da biet tu do
            word.setCheck(1);
            if(t<=15) word.setStatus(1);
            else if(t>15 && t<=30) word.setStatus(2);
            else word.setStatus(3);
            dbHelper.updateWord(word,"N"+level);
        if(numberquestion<11 && numberquestion<=questionsList.size()){
            timeHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toNextQuestion();
                }
            },100);
        }
    }
    public class SwipeDeckAdapter extends BaseAdapter{
        private List<Word> data;
        private Context context;

        public SwipeDeckAdapter(List<Word> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.test_card2, parent, false);
            }
            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            //ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
            //Picasso.with(context).load(R.drawable.food).fit().centerCrop().into(imageView);
            tvQuestion = (TextView) v.findViewById(R.id.question);


            Word item = (Word)getItem(position);
            txt=item.getWord().trim();
            tvQuestion.setText(txt);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView2 = (TextView) v.findViewById(R.id.answer);
                    Word item = (Word)getItem(position);
                    textView2.setText(item.getMean().trim());
                }
            });
            return v;
        }
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
        String toSpeak = txt;
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
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
//                stopPlaying();
            }
        }
    };

    public void toNextQuestion(){
        t=0;
        progressBar.setProgress(59);
        progressBar.setMax(59);
        if(numberquestion<10){//dếm số câu hỏi dã trả lời
            numberquestion++;
            updateScreen(next);//chuyển đến câu hỏi tiếp theo
            next++;
        }else{
            stopPlaying();
        }

        //update question
        countDownTimer.start();
    }

    public void updateScreen(int n){
        if(n>= questionsList.size()){
            return;
        }
        if(check == true)
            cardStack.swipeTopCardRight(180);
        Question q = questionsList.get(n);

        Log.d("n" + n, "wo" + q.getWord());
        curentWord.setId(q.getId());
        curentWord.setWord(q.getWord());
        curentWord.setMean(q.getMean());
        curentWord.setStatus(5);
        dbHelper.updateWord(curentWord, "N" + level);
        listComplete.add(q.getId());
        tvNumberQuesCard.setText(numberquestion + "/10");

        tvDemlui.setText("5");
        upSound(q);
    }


    public void upSound(Question question){
//        Log.d("bbbb"+next, "" + question.getWord());
//        tvQuestion.setText(question.getWord());
        toSpeak = question.getWord().toString();
//        Log.d("bbbb", "" + question.getMean());

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.JAPANESE);
                // dòng t1. speak trong hàm onInit để phát âm luôn ko phải đợi click

                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public void stopPlaying(){
        //TODO
        countDownTimer.cancel();
        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                // bundle.putCharSequenceArrayList("number10",ques10);
                bundle.putInt("level", level);
                bundle.putInt("bt", bt);
                bundle.putString("table", table);
                Intent intentStopPlay = new Intent(SwipeDeckActivity.this, StopLearnActivity.class);
                intentStopPlay.putIntegerArrayListExtra("listComplete", listComplete);
                intentStopPlay.putExtra("toStop", bundle);
                startActivity(intentStopPlay);
            }
        }, 100);

    }


    public void restoringPreferences()
    {
        SharedPreferences pre=getSharedPreferences
                (prefname, MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        level = pre.getInt("level",5);
        status_choice = pre.getInt("status_choice",0);
    }
}
