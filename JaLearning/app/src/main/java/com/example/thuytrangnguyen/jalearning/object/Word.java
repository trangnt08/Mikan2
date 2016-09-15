package com.example.thuytrangnguyen.jalearning.object;

/**
 * Created by Thuy Trang Nguyen on 8/10/2016.
 */
public class Word {
    int id;
    String word;
    String mean;
    private int status;
    private int check;

    public Word(){

    }
    public Word(int id, String w,String m, int s, int check){
        this.id = id;
        this.word = w;
        this.mean = m;
        this.status = s;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
