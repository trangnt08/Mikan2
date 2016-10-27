package com.example.thuytrangnguyen.jalearning.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thuytrangnguyen.jalearning.object.Question;
import com.example.thuytrangnguyen.jalearning.object.Word;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thuy Trang Nguyen on 2/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_PATH = "/data/data/com.example.thuytrangnguyen.jalearning/databases/";
    public static final String DB_NAME = "japan.sqlite";
    public static final String TABLE_BASIC = "Words";
    public static final String N1 = "N1";
    public static final String N2 = "N2";
    public static final String N3 = "N3";
    public static final String N4 = "N4";
    public static final String N5 = "N5";

    public static final String CN2 = "CN2";
    public static final String CN3 = "CN3";
    public static final String ID_WORD = "id";
    public static final String LEVEL = "level";
    public static final String STATUS = "status";
    public static final String CHECK = "checked";
    public static final String CREATE_TABLE_N1 = "CREATE TABLE " + N1+ "(" + ID_WORD + " INTEGER PRIMARY KEY NOT NULL" + ", " + STATUS + " INTEGER " + "," + CHECK + " INTEGER )";
    public static final String CREATE_TABLE_N2 = "CREATE TABLE " + N2+ "(" + ID_WORD + " INTEGER PRIMARY KEY NOT NULL" + ", " + STATUS + " INTEGER " + "," + CHECK + " INTEGER )";
    public static final String CREATE_TABLE_N3 = "CREATE TABLE " + N3+ "(" + ID_WORD + " INTEGER PRIMARY KEY NOT NULL" + ", " + STATUS + " INTEGER " + "," + CHECK + " INTEGER )";
    public static final String CREATE_TABLE_N4 = "CREATE TABLE " + N4+ "(" + ID_WORD + " INTEGER PRIMARY KEY NOT NULL" + ", " + STATUS + " INTEGER " + "," + CHECK + " INTEGER )";
    public static final String CREATE_TABLE_N5 = "CREATE TABLE " + N5+ "(" + ID_WORD + " INTEGER PRIMARY KEY NOT NULL" + ", " + STATUS + " INTEGER " + "," + CHECK + " INTEGER )";
    public static final String DB_TO_N2 = "INSERT INTO "+N2+" ("+ID_WORD+") SELECT id FROM "+TABLE_BASIC + " WHERE "+LEVEL+"='"+N2+"'";

    public static final String KEY_ID_RANK = "id_rank";
    public static final String WORD = "word";
    public static final String MEAN = "mean";

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
        try {
            db.execSQL(CREATE_TABLE_N1);
            db.execSQL(getSqlDB(N1));
            db.execSQL(CREATE_TABLE_N2);
            db.execSQL(getSqlDB(N2));
            db.execSQL(CREATE_TABLE_N3);
            db.execSQL(CREATE_TABLE_N4);
            db.execSQL(CREATE_TABLE_N5);
            db.execSQL(getSqlDB(N3));
            db.execSQL(getSqlDB(N4));
            db.execSQL(getSqlDB(N5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query1 = "DROP TABLE IF EXISTS "+N1;
        db.execSQL(query1);
        String query2 = "DROP TABLE IF EXISTS "+N2;
        db.execSQL(query2);
        String query3 = "DROP TABLE IF EXISTS " + N3;
        db.execSQL(query3);
        String query4 = "DROP TABLE IF EXISTS " + N4;
        db.execSQL(query4);
        String query5 = "DROP TABLE IF EXISTS " + N5;
        db.execSQL(query5);
        this.onCreate(db);
    }

    public String getSqlDB(String n){
        return "INSERT INTO "+n+" ("+ID_WORD+") SELECT id FROM "+TABLE_BASIC + " WHERE "+LEVEL+"='n2'";
    }

    private boolean copyDataBase(Context context) {

        try {
            //Open your local db as the input stream
            InputStream myInput = context.getAssets().open(DB_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;

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
//    public void closeDatabase(){
//        if (db!=null){
//            db.close();
//        }
//    }

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
        //Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_BASIC + " WHERE level = '" + n + "'",null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_BASIC + " WHERE level = '" + n + "'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            word = new Word(cursor.getInt(0),cursor.getString(1),cursor.getString(2),0,0);
            wordsList.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return wordsList;
    }
    public List<Word> getListWord2(String n){
        Word word = null;
        List<Word> wordsList = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_BASIC+","+n + " WHERE "+TABLE_BASIC+"."+ID_WORD+"="+n+"."+ID_WORD ,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            word = new Word(cursor.getInt(0),cursor.getString(cursor.getColumnIndex("word")),cursor.getString(cursor.getColumnIndex("mean")),cursor.getInt(cursor.getColumnIndex("status")),cursor.getInt(cursor.getColumnIndex("checked")));
            wordsList.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return wordsList;
    }


    public List<Word> getWordRank(String sql){
        Word word = null;
        List<Word> wordsList = new ArrayList<>();
        open();

        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            word = new Word(cursor.getInt(0),cursor.getString(1),cursor.getString(2),0,0);
            wordsList.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        close();
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
            if (i == wordsList.size()-4)
            question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+2).getMean(),wordsList.get(i+3).getMean(),wordsList.get(0).getMean());
            else if (i==wordsList.size()-3)
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+2).getMean(),wordsList.get(0).getMean(),wordsList.get(1).getMean());
            else if (i==wordsList.size()-2)
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(0).getMean(),wordsList.get(1).getMean(),wordsList.get(2).getMean());
            else if (i==wordsList.size()-1)
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(1).getMean(),wordsList.get(2).getMean(),wordsList.get(3).getMean());
            else
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+2).getMean(),wordsList.get(i+3).getMean(),wordsList.get(i+4).getMean());

            questionList.add(question);
            i++;
        }
        close();
        return questionList;
    }

    public List<Question> getQuestionsRank(String sql){
        Question question = null;
        open();
        List<Word> wordsList = getWordRank(sql);
        List<Question> questionList = new ArrayList<>();
        int i = 0;
        while (i<wordsList.size()){
            if (i == wordsList.size()-4)
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+2).getMean(),wordsList.get(i+3).getMean(),wordsList.get(0).getMean());
            else if (i==wordsList.size()-3)
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+2).getMean(),wordsList.get(0).getMean(),wordsList.get(1).getMean());
            else if (i==wordsList.size()-2)
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(0).getMean(),wordsList.get(1).getMean(),wordsList.get(2).getMean());
            else if (i==wordsList.size()-1)
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(1).getMean(),wordsList.get(2).getMean(),wordsList.get(3).getMean());
            else
                question = new Question(wordsList.get(i).getId(),wordsList.get(i).getWord(),wordsList.get(i).getMean(),wordsList.get(i+2).getMean(),wordsList.get(i+3).getMean(),wordsList.get(i+4).getMean());

            questionList.add(question);
            i++;
        }
        close();
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
        return update(n, wordToValues(word), ID_WORD + " = " + word.getId());
    }

    /**
     * delete id row of table
     */
    public boolean deleteNote(String where, String n) {
        return delete(n, where);
    }


    private ContentValues wordToValues(Word word) {
        ContentValues values = new ContentValues();
        values.put(STATUS, +word.getStatus());
        values.put(CHECK, +word.getCheck());
        return values;
    }
}
