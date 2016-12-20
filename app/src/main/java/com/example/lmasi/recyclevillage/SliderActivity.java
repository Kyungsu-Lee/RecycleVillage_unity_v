package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SliderActivity extends Activity { //

    BoxView[] boxview;
    RelativeLayout main;
    MovingBox mOb, mOb2;

    Bundle bundle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //final Intent intent = new Intent(MainActivity.this, intro.class);
        setContentView(R.layout.slide);

        bundle = savedInstanceState;


        ScreenParameter.setScreenparam_x(ScreenSize().x/ScreenParameter.getDefaultsizeX());
        ScreenParameter.setScreenparam_y(ScreenSize().y/ScreenParameter.getDefaultsizeY());
        ScreenParameter.setScreen_x(ScreenSize().x);
        ScreenParameter.setScreen_y(ScreenSize().y);


        Handler handler = new Handler()
        {
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                startActivity(new Intent(SliderActivity.this, MainActivity.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,6000);



        main = (RelativeLayout) findViewById(R.id.main); //id를 갖고 object를 찾는것. textview를 가져왔을때는  이걸로 type casting

        Animation slowly_appear, slowlyDisappear;
        slowlyDisappear = AnimationUtils.loadAnimation(this, R.anim.disapppear);
        slowly_appear = AnimationUtils.loadAnimation(this, R.anim.appear);
        // main.setAnimation(slowlyDisappear);
        main.setAnimation(slowly_appear);

        mOb = new MovingBox(this);
        mOb.setSize((int) (739 * ScreenParameter.getScreenparam_x()), (int) (317  * ScreenParameter.getScreenparam_y()));
        mOb.setlocation((int) (353 * ScreenParameter.getScreenparam_x()), (int) (893  * ScreenParameter.getScreenparam_y()));
        //  mOb.setlocation(mOb.getLX(), mOb.getLY() + ScreenSize().y/30);
        mOb.setBackgroundResource(R.drawable.title);
        main.addView(mOb);


        mOb2 = new MovingBox(this);
        mOb2.setSize((int) (651 * ScreenParameter.getScreenparam_x()), (int) (151  * ScreenParameter.getScreenparam_y()));
        mOb2.setlocation((int) (414 * ScreenParameter.getScreenparam_x()), (int) (1518  * ScreenParameter.getScreenparam_y()));
        //  mOb.setlocation(mOb.getLX(), mOb.getLY() + ScreenSize().y/30);
        mOb2.setBackgroundResource(R.drawable.realshadow);
        main.addView(mOb2);


        mOb.addTimer(new Handler() { //hanlder 오브젝트를 생성해서 overriding

            public boolean F = true;

            double padding = 15;
            int speed = 100;

            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
            {
                if (F) {
                    mOb.setY((float) (mOb.getY() + 2 * ScreenParameter.getScreenparam_y())); //위로 이동
                } else {
                    mOb.setY((float) (mOb.getY() - 2 * ScreenParameter.getScreenparam_y())); //아래로 이동
                }


                if (mOb.getY() > (893 + padding)* ScreenParameter.getScreenparam_y() || mOb.getY() < (893 - padding) * ScreenParameter.getScreenparam_y()) //왕복 움직임 구현
                    F = !F;

                sendEmptyMessageDelayed(0, speed); //무한 실행. (delay, msec)
            }
        });

        mOb.startTimer(0);//handler 실행


    }

    public Point ScreenSize() { //현재 스크린의 사이즈를 가져오는 메서드. 정형화된 틀이다.

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Point size = new Point(width, height);

        return size;

    }

    public int narrowSize() {
        int x = ScreenSize().x; //현재 스크린의 사이즈; 가로, 세로 화면.
        int y = ScreenSize().y;

        return (x > y ? y : x);
    }

    public int wideSize() {
        int x = ScreenSize().x;
        int y = ScreenSize().y;

        return (x < y ? y : x);
    }

    public void restart()
    {
        this.onCreate(bundle);
    }
}