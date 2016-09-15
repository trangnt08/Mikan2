package com.example.thuytrangnguyen.jalearning.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.thuytrangnguyen.jalearning.R;

public class SplishActivity extends AppCompatActivity {

    ImageView mImageView;
    private Animation mFadeIn,mFadeInScale,mFadeOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish);
        mImageView = (ImageView)findViewById(R.id.ivLogo);
        initAnim();
        setListener();
    }

    private void initAnim(){
        this.mFadeIn = AnimationUtils.loadAnimation(this,R.anim.welcome_fade_in);
        this.mFadeIn.setDuration(200);
        this.mFadeInScale = AnimationUtils.loadAnimation(this,R.anim.welcome_fade_in_scale);
        this.mFadeInScale.setDuration(1000);
        this.mFadeOut = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_out);
        this.mFadeOut.setDuration(400);
        this.mImageView.startAnimation(this.mFadeIn);
    }

    public void setListener() {
        this.mFadeIn.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mImageView.startAnimation(SplishActivity.this.mFadeInScale);
            }
        });
        this.mFadeInScale.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {

                Intent intent = new Intent(SplishActivity.this, MainActivity.class);
                startActivity(intent);
                SplishActivity.this.finish();
            }
        });
        this.mFadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }
        });
    }
}
