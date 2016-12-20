package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.Vector;

import static android.graphics.Color.argb;

/**
 * Created by Administrator on 2016-09-08.
 */



public class GatePause extends Activity{

    double DEFAULTSIZE_Y = 1280.0; //1280
    double DEFAULTSIZE_X = 720.0; //720

    static ListRelativeLayout  main;
    static List<View> viewList;

    GateGameView black;
    GateButtonView resume;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_gate_option);

        main = (ListRelativeLayout) findViewById(R.id.option);

        viewList = new Vector<View>();

        main.setViewList(viewList);

        Intent intent = getIntent();
       // int name = intent.getExtras().getInt("stage");
       // int age = intent.getExtras().getInt("option");

        black = new GateGameView(this, viewList);
        black.setSize(ScreenSize().x, ScreenSize().y);
        black.setLocation(0 * screenparam_x(), 0 * screenparam_y());
        black.setBackgroundColor(argb(120, 35, 31, 32));
        black.setCanDrag(false);
        black.setCanMove(false);
        main.addView(black);


        resume = new GateButtonView(this, viewList, R.drawable.pause1);
        resume.setSize(80 * screenparam_x(), 250 * screenparam_y());
        resume.setLocation(10 * screenparam_x(), 620 * screenparam_y());
        main.addView(resume);
        resume.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println("ㅇㅇㅇ");

                }


                return true;
            }

        });

    }

    public Point ScreenSize() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize( size );
        int width = size.x;
        int height = size.y;

        return size;

    }

    public int narrowSize() {
        int x = ScreenSize().x;
        int y = ScreenSize().y;

        return (x > y ? y : x);
    }

    public int wideSize() {
        int x = ScreenSize().x;
        int y = ScreenSize().y;

        return (x < y ? y : x);
    }

    public double screenparam_x()
    {
        return ScreenSize().x/DEFAULTSIZE_X;
    }

    public double screenparam_y()
    {
        return ScreenSize().y/DEFAULTSIZE_Y;
    }

}
