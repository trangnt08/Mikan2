package com.example.thuytrangnguyen.jalearning.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.thuytrangnguyen.jalearning.object.Question;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thuy Trang Nguyen on 2/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_PATH = "/data/data/com.example.thuytrangnguyen.jalearning/databases/";
    public static final String DB_NAME = "japan.sqlite";
    public static final String TABLE_BASIC = "Words";
    public static final String N2 = "N2";
    public static final String ID_WORD = "id";
    public static final String KEY_ID_RANK = "id_rank";
    public static final String WORD = "word";
    public static final String MEAN = "mean";
    public static final String STATUS = "status";
    public static final String CHECK = "check";
    public static final String A = "a";
    public static final String B = "b";
    public static final String C = "c";

    private Context context;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//    public void openDataBase() {
//
//        //Open the database
//        String dbPath = context.getDatabasePath(DB_NAME).getPath();
//        if (db!=null && db.isOpen()){
//            return;
//        }
//        //String myPath = DB_PATH + DB_NAME;
//        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
//
//    }
    public void closeDatabase(){
        if (db!=null){
            db.close();
        }
    }

    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close database
     */
    public void close() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Word> getListWord(String n){
        Word word = null;
        List<Word> wordsList = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_BASIC + " WHERE level = '" + n + "'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            word = new Word(cursor.getInt(0),cursor.getString(1),cursor.getString(2),0,0);
            wordsList.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return wordsList;
    }
    public Cursor getAll(String sql) {
        open();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        close();
        return cursor;
    }
    public Word getWord(String sql){
        Word word = null;
        Cursor cursor = getAll(sql);
        if (cursor!=null){
            word = cursorToWord(cursor);
            cursor.close();
        }
        return word;
    }

    private Word cursorToWord(Cursor cursor) {
        Word word = new Word();
        word.setId(cursor.getInt(cursor.getColumnIndex(ID_WORD)));
        //word.setWord(cursor.getString(cursor.getColumnIndex(WORD)));
        //word.setMean(cursor.getString(cursor.getColumnIndex(MEAN)));
        word.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
        word.setCheck(0);
        return word;
    }
    public List<Question> getQuestions(String n){
        Question question = null;
        open();
        List<Word> wordsList = getListWord(n);
        List<Question> questionList = new ArrayList<>();
        int i = 0;
        while (i<wordsList.size()){
            if (i == wordsList.size()-3)
            question = new Question(wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+1).getMean(),wordsList.get(i+2).getMean(),wordsList.get(0).getMean());
            else if (i==wordsList.size()-2)
                question = new Question(wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+1).getMean(),wordsList.get(0).getMean(),wordsList.get(1).getMean());
            else if (i==wordsList.size()-1)
                question = new Question(wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(0).getMean(),wordsList.get(1).getMean(),wordsList.get(2).getMean());
            else
                question = new Question(wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+1).getMean(),wordsList.get(i+2).getMean(),wordsList.get(i+3).getMean());

            questionList.add(question);
            i++;
        }
        closeDatabase();
        return questionList;
    }



    /**
     * insert contentvaluse to table
     *
     * @param values value of data want insert
     * @return index row insert
     */
    public long insert(String table, ContentValues values) {
        open();
        long index = db.insert(table, null, values);
        close();
        return index;
    }

    /**
     * update values to table
     *
     * @return index row update
     */
    public boolean update(String table, ContentValues values, String where) {
        open();
        long index = db.update(table, values, where, null);
        close();
        return index > 0;
    }

    /**
     * delete id row of table
     */
    public boolean delete(String table, String where) {
        open();
        long index = db.delete(table, where, null);
        close();
        return index > 0;
    }

    /**
     * insert note to table
     *
     * @param word note to insert
     * @return id of note
     */
    public long insertWord(Word word,String n) {
        return insert(n, wordToValues(word));
    }

    /**
     * @param word note to update
     * @return id of note update
     */
    public boolean updateWord(Word word, String n) {
        open();
        ContentValues w = wordToValues(word);
        db.execSQL("UPDATE " + n +" SET status = "+word.getStatus()+", check=" + word.getCheck()+" WHERE id = "+word.getId()+";");
        //return update(n, w, ID_WORD + " = " + word.getId());
        close();
        return true;
    }

    /**
     * delete id row of table
     */
    public boolean deleteNote(String where, String n) {
        return delete(n, where);
    }


    private ContentValues wordToValues(Word word) {
        Log.d("wordddd status ",""+word.getStatus());
        ContentValues values = new ContentValues();
        //values.put(ID_WORD, word.getId());
        values.put(STATUS, word.getStatus());
        values.put(CHECK, 1);
        return values;
    }
}
