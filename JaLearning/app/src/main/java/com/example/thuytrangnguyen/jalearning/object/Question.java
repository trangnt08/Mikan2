package com.example.thuytrangnguyen.jalearning.object;

/**
 * Created by Thuy Trang Nguyen on 9/9/2016.
 */
public class Question extends Word {
    private String ans1;
    private String ans2;
    private String ans3;

    public Question(){}

    public Question(int id, String w, String m, String a1, String a2, String a3){
        this.id = id;
        this.word = w;
        this.mean = m;
        this.ans1 = a1;
        this.ans2 = a2;
        this.ans3 = a3;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }
}