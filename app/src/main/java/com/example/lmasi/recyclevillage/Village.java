package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.transition.Slide;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Comparator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Village extends Activity {

    RelativeLayout main;
    TouchImageView pop_up;
    TouchImageView next_land;
    TouchImageView big_land;
    TouchImageView back_land;
    TouchImageView small_land;
    TouchImageView inner_circle;
    TouchImageView outer_circle;
    TouchImageView stick1;
    TouchImageView stick2;
    TouchImageView stick3;
    TouchImageView stick4;
    TouchImageView stick5;
    TouchImageView stick6;
    onTextView point;
    onTextView point_num;
    onTextView item;
    onTextView item_num;
    onTextView mission;
    onTextView mission_num;
    onTextView entry;
    onTextView entry_num;
    onTextView recycle;
    onTextView recycle_num;
    onTextView prize;
    onTextView prize_num;
    ClickButtonView btn_x;
    /*
    RelativeLayout point_gauge;
    RelativeLayout item_gauge;
    RelativeLayout mission_gauge;
    RelativeLayout entry_gauge;
    RelativeLayout recycle_gauge;
    */
    RelativeLayout[] relativeLayouts = new RelativeLayout[5];
    RelativeLayout.LayoutParams[] paramses = new RelativeLayout.LayoutParams[5];
    TouchImageView[] Boxes = new TouchImageView[5];
    onTextView month;
    onTextView year;

    LinearLayout[][] linearLayouts;
    RelativeLayout[] planet = new RelativeLayout[40];
    LinearLayout.LayoutParams[] linear_params = new LinearLayout.LayoutParams[8];

    LinearLayout.LayoutParams[] planet_params = new LinearLayout.LayoutParams[40];

    RelativeLayout[] r = new RelativeLayout[4];
    RelativeLayout.LayoutParams[] p = new RelativeLayout.LayoutParams[4];

    ButtonView[] villages = new ButtonView[10];

    CircularScrollView lands;
    TouchImageView[] land = new TouchImageView[10];
    RelativeLayout[] R_lands = new RelativeLayout[10];

    TouchImageView[] small_circle = new TouchImageView[4];

    TouchImageView[] touchBox = new TouchImageView[10];

    int village_index = 0;

    Handler hd2;

    float[] scale_1, scale_2;

    float degree = 0;

    TouchImageView[] stars;


    Handler remove_when_show;

    boolean ispopup = false;
    boolean canclose = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.village);

        main = (RelativeLayout) findViewById(R.id.main_R);

        // main.setBackground(getResources().getDrawable(R.drawable.bg_none));
        main.setBackgroundColor(Color.rgb(14, 35, 40));

        int star_x = 170;
        int star_y = 100;

        Random rd = new Random();

        stars = new TouchImageView[MainActivity.num_stars];
        for(int i=0; i<MainActivity.num_stars; i++)
        {
            stars[i] = new TouchImageView(this);
            stars[i].setSize(ScreenParameter.getScreenparam_x() * MainActivity.size_stars, ScreenParameter.getScreenparam_y() * MainActivity.size_stars);
            //  star_x += rd.nextInt(ScreenSize().x/4);
            //  star_y += rd.nextInt(ScreenSize().y/5);
            stars[i].setLocation(ObjectSizeLocation.star_locate[i][0] *ScreenParameter.getScreenparam_x(), ObjectSizeLocation.star_locate[i][1]* ScreenParameter.getScreenparam_y());
            stars[i].setBackground(R.drawable.mainpage_star);
            main.addView(stars[i]);
        }

        TouchImageView back = new TouchImageView(this);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                finish();

                return false;
            }
        });
        back.setSize(110* ScreenParameter.getScreenparam_x(), 156 * ScreenParameter.getScreenparam_y());
        back.setLocation(44 * ScreenParameter.getScreenparam_x(), 31* ScreenParameter.getScreenparam_y());
        main.addView(back);

        TouchImageView arrow = new TouchImageView(this);
        arrow.setSize(46 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_y());
        arrow.setLocation(64 * ScreenParameter.getScreenparam_x(), 51* ScreenParameter.getScreenparam_y());
        arrow.setBackground(R.drawable.entry_back_arrow);
        main.addView(arrow);


        scale_1 = new float[6];
        scale_2 = new float[6];

        linearLayouts = new LinearLayout[3][];

        for (int i = 0; i < 3; i++) {
            linearLayouts[i] = new LinearLayout[4];

            for (int j = 0; j < 4; j++)
                linearLayouts[i][j] = new LinearLayout(this);
        }

        for (int i = 0; i < 8; i++) {
            linear_params[i] = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            linear_params[i].weight = 0.5f;

            linearLayouts[i % 2 + 1][i / 2].setLayoutParams(linear_params[i]);
        }

        for (int i = 0; i < 4; i++) {
            linearLayouts[0][i].setOrientation(LinearLayout.VERTICAL);
            linearLayouts[0][i].setWeightSum(1);

            for (int j = 1; j < 3; j++) {
                linearLayouts[j][i].setOrientation(LinearLayout.HORIZONTAL);
            }

            linearLayouts[0][i].addView(linearLayouts[1][i]);
            linearLayouts[0][i].addView(linearLayouts[2][i]);
        }

        for (int i = 0; i < 40; i++) {
            planet[i] = new RelativeLayout(this);
            planet_params[i] = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            planet_params[i].weight = 1;
            planet[i].setLayoutParams(planet_params[i]);
            //linearLayouts[(i%10 <5 ? 1 : 2)][i/10].addView(planet[i]);
            linearLayouts[(i % 10) / 5 + 1][i / 10].addView(planet[i]);

            planet[i].setBackground(getResources().getDrawable(R.drawable.village_grid));
            //planet[i].setBackgroundColor(Color.rgb(i*5,i*5,i*5));

        }

        for (int i = 0; i < 10; i++) {
            villages[i] = new ButtonView(this, R.drawable.bottom_01r + 2 * i, R.drawable.bottom_01 + 2 * i) {
                @Override
                public void ClickAction() {

                }

                @Override
                public void UnClickAction() {

                }
            };
            villages[i].setSize(166 * ScreenParameter.getScreenparam_x(), 205 * ScreenParameter.getScreenparam_y());
            villages[i].setLocation(50 * ScreenParameter.getScreenparam_x(), 50 * ScreenParameter.getScreenparam_y());
            villages[i].setBackground(R.drawable.bottom_01r + 2 * i);
            villages[i].setId(i);
            villages[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    lands.setPage(view.getId());
                    return false;
                }
            });
            planet[i].addView(villages[i]);
        }
/*
        RelativeLayout.LayoutParams L_param = new RelativeLayout.LayoutParams(1371,606);
        L_param.setMargins(32,1767,0,0);

        for(int i=0; i<4; i++)
            linearLayouts[0][i].setLayoutParams(L_param);
*/
        SlideView viewpager = new SlideView(this) {
            @Override
            public void BeforeAction() {

                for (int i = 0; i < getCount(); i++) {
                    if (i == getIndex())
                        small_circle[i].setBackground(R.drawable.greencircle);
                    else
                        small_circle[i].setBackground(R.drawable.greycircle);
                }
            }

            @Override
            public void AfterAction() {

            }

            @Override
            public void onDrag() {

            }
        };

        viewpager.setSize((int) (1371 * ScreenParameter.getScreenparam_x()), (int) (606 * ScreenParameter.getScreenparam_y()));
        viewpager.setLocation(30 * ScreenParameter.getScreenparam_x(), 1767 * ScreenParameter.getScreenparam_y());

        for (int i = 0; i < 4; i++) {
            r[i] = new RelativeLayout(this);
            p[i] = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayouts[0][i].setLayoutParams(p[i]);
            r[i].addView(linearLayouts[0][i]);
            viewpager.addLayout(r[i]);
        }

        main.addView(viewpager);

        for (int i = 0; i < 4; i++) {
            small_circle[i] = new TouchImageView(this);
            small_circle[i].setSize(24 * ScreenParameter.getScreenparam_x(), 24 * ScreenParameter.getScreenparam_y());
            small_circle[i].setLocation((660 + 43 * i) * ScreenParameter.getScreenparam_x(), 1700 * ScreenParameter.getScreenparam_y());
            small_circle[i].setBackground(R.drawable.greycircle);
            main.addView(small_circle[i]);
        }

        small_circle[0].setBackground(R.drawable.greencircle);

        onTextView title = new onTextView(this);
        title.setText("내 보유마을");
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (60 * ScreenParameter.getScreenparam_y()));
        title.setTextColor(Color.rgb(255, 255, 255));
        title.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        title.setLocation(582 * ScreenParameter.getScreenparam_x(), 63 * ScreenParameter.getScreenparam_y());
        main.addView(title);

        TouchImageView graph = new TouchImageView(this);
        graph.setSize(105 * ScreenParameter.getScreenparam_x(), 104 * ScreenParameter.getScreenparam_y());
        graph.setLocation(1288 * ScreenParameter.getScreenparam_x(), 42 * ScreenParameter.getScreenparam_y());
        graph.setBackground(R.drawable.village_icon);
        main.addView(graph);
        graph.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showViews();
                remove_when_show.sendEmptyMessage(1000);
                return false;
            }
        });
/*
        next_land = new TouchImageView(this);
        next_land.setSize(437 * ScreenParameter.getScreenparam_x(), 539 * ScreenParameter.getScreenparam_y());
        // next_land.setLocation(-218 * ScreenParameter.getScreenparam_x(), 312 * ScreenParameter.getScreenparam_y());
        next_land.setBackground(R.drawable.big_2);

        big_land = new TouchImageView(this);
        big_land.setSize(829 * ScreenParameter.getScreenparam_x(), 1023 * ScreenParameter.getScreenparam_y());
        //big_land.setLocation(308 * ScreenParameter.getScreenparam_x(), 446 * ScreenParameter.getScreenparam_y());
        big_land.setLocation(280 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y());
        big_land.setBackground(R.drawable.big_3);

        back_land = new TouchImageView(this);
        back_land.setSize(437 * ScreenParameter.getScreenparam_x(), 539 * ScreenParameter.getScreenparam_y());
        // back_land.setLocation(1221 * ScreenParameter.getScreenparam_x(), 312 * ScreenParameter.getScreenparam_y());
        back_land.setBackground(R.drawable.big_4);
*/

        lands = new CircularScrollView(this) {
            @Override
            public void BeforeAction() {
                for (int i = 0; i < getCount(); i++) {
                    if (i == getIndex())
                        villages[i].Click();
                    else
                        villages[i].UnClick();
                }

            }

            @Override
            public void AfterAction() {
            }

            @Override
            public void onDrag() {

            }

            @Override
            public void TouchEvent() {
                showViews();
                remove_when_show.sendEmptyMessage(1000);
            }
        };
        lands.setSize((int) ScreenParameter.getScreen_x(), (int) (1300 * ScreenParameter.getScreenparam_y()));
        lands.setLocation(0, 101 * ScreenParameter.getScreenparam_y());
        main.addView(lands);
        villages[0].Click();
        //lands.setTime(50);
        for (int i = 0; i < 10; i++) {
            R_lands[i] = new RelativeLayout(this);
            land[i] = new TouchImageView(this);
            touchBox[i] = new TouchImageView(this);
            touchBox[i].setSize(150 * ScreenParameter.getScreenparam_x(), 150 * ScreenParameter.getScreenparam_y());
            touchBox[i].setLocation(670 * ScreenParameter.getScreenparam_x(), 400 * ScreenParameter.getScreenparam_y());
            land[i].setSize(829 * ScreenParameter.getScreenparam_x(), 1023 * ScreenParameter.getScreenparam_y());
            land[i].setLocation(280 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y());
            land[i].setBackground(R.drawable.big_02 + i);
            R_lands[i].addView(touchBox[i]);
            lands.addCircleView(land[i]);
        }
        lands.startView();


        month = new onTextView(this);
        month.setText("6");
        month.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (149 * ScreenParameter.getScreenparam_y()));
        month.setTextColor(Color.rgb(0, 255, 255));
        month.setTypeface(Typeface.createFromAsset(this.getAssets(), "GodoM.ttf"));
        month.setLocation(678 * ScreenParameter.getScreenparam_x(), 473 * ScreenParameter.getScreenparam_y());
        month.setHighlightColor(Color.YELLOW);
        month.setShadowLayer(25, -0, -0, Color.rgb(0, 255, 255));

        year = new onTextView(this);
        year.setText("2016");
        year.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        year.setTextColor(Color.rgb(0, 255, 255));
        year.setTypeface(Typeface.createFromAsset(this.getAssets(), "GodoM.ttf"));
        year.setLocation(666 * ScreenParameter.getScreenparam_x(), 630 * ScreenParameter.getScreenparam_y());
        year.setShadowLayer(20, -0, -0, Color.rgb(0, 255, 255));
        year.setPaintFlags(year.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);


        small_land = new TouchImageView(this);
        small_land.setSize(231 * ScreenParameter.getScreenparam_x(), 286 * ScreenParameter.getScreenparam_y());
        small_land.setLocation(608 * ScreenParameter.getScreenparam_x(), 850 * ScreenParameter.getScreenparam_y());
        small_land.setBackground(R.drawable.info_01);

        inner_circle = new TouchImageView(this);
        inner_circle.setSize(380 * ScreenParameter.getScreenparam_x(), 383 * ScreenParameter.getScreenparam_y());
        inner_circle.setLocation(533 * ScreenParameter.getScreenparam_x(), 798 * ScreenParameter.getScreenparam_y());
        inner_circle.setBackground(R.drawable.village_inner_circle);

        outer_circle = new TouchImageView(this);
        outer_circle.setSize(420 * ScreenParameter.getScreenparam_x(), 420 * ScreenParameter.getScreenparam_y());
        outer_circle.setLocation(514 * ScreenParameter.getScreenparam_x(), 779 * ScreenParameter.getScreenparam_y());
        outer_circle.setBackground(R.drawable.village_outer_circle);

        stick1 = new TouchImageView(this);
        stick1.setSize(157 * ScreenParameter.getScreenparam_y(), 106 * ScreenParameter.getScreenparam_y());
        stick1.setLocation(452 * ScreenParameter.getScreenparam_x(), 722 * ScreenParameter.getScreenparam_y());
        stick1.setBackground(R.drawable.stick_1);

        stick2 = new TouchImageView(this);
        stick2.setSize(78 * ScreenParameter.getScreenparam_x(), 46 * ScreenParameter.getScreenparam_y());
        stick2.setLocation(452 * ScreenParameter.getScreenparam_x(), 966 * ScreenParameter.getScreenparam_y());
        stick2.setBackground(R.drawable.stick_2);
        //stick2.setBackgroundColor(Color.MAGENTA);

        stick3 = new TouchImageView(this);
        stick3.setSize(157 * ScreenParameter.getScreenparam_x(), 105 * ScreenParameter.getScreenparam_y());
        stick3.setLocation(452 * ScreenParameter.getScreenparam_x(), 1147 * ScreenParameter.getScreenparam_y());
        stick3.setBackground(R.drawable.stick_3);

        stick4 = new TouchImageView(this);
        stick4.setSize(157 * ScreenParameter.getScreenparam_x(), 107 * ScreenParameter.getScreenparam_y());
        stick4.setLocation(837 * ScreenParameter.getScreenparam_x(), 720 * ScreenParameter.getScreenparam_y());
        stick4.setBackground(R.drawable.stick_4);

        stick5 = new TouchImageView(this);
        stick5.setSize(78 * ScreenParameter.getScreenparam_x(), 47 * ScreenParameter.getScreenparam_y());
        stick5.setLocation(917 * ScreenParameter.getScreenparam_x(), 966 * ScreenParameter.getScreenparam_y());
        stick5.setBackground(R.drawable.stick_5);

        stick6 = new TouchImageView(this);
        stick6.setSize(157 * ScreenParameter.getScreenparam_x(), 106 * ScreenParameter.getScreenparam_y());
        stick6.setLocation(837 * ScreenParameter.getScreenparam_x(), 1146 * ScreenParameter.getScreenparam_y());
        stick6.setBackground(R.drawable.stick_6);

        point = new onTextView(this);
        point.setText("POINT");
        point.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        point.setTextColor(Color.rgb(233, 103, 103));
        point.setTypeface(Typeface.createFromAsset(this.getAssets(), "GodoM.ttf"));
        point.setLocation(280 * ScreenParameter.getScreenparam_x(), 720 * ScreenParameter.getScreenparam_y());

        point_num = new onTextView(this);
        point_num.setText("11270");
        point_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        point_num.setTextColor(Color.rgb(237, 133, 133));
        point_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        point_num.setLocation(298 * ScreenParameter.getScreenparam_x(), 788 * ScreenParameter.getScreenparam_y());

        item = new onTextView(this);
        item.setText("ITEM");
        item.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        item.setTextColor(Color.rgb(233, 103, 103));
        item.setTypeface(Typeface.createFromAsset(this.getAssets(), "GodoM.ttf"));
        item.setLocation(313 * ScreenParameter.getScreenparam_x(), 964 * ScreenParameter.getScreenparam_y());

        item_num = new onTextView(this);
        item_num.setText("7");
        item_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        item_num.setTextColor(Color.rgb(237, 133, 133));
        item_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        item_num.setLocation(402 * ScreenParameter.getScreenparam_x(), 1032 * ScreenParameter.getScreenparam_y());

        mission = new onTextView(this);
        mission.setText("MISSION");
        mission.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        mission.setTextColor(Color.rgb(233, 103, 103));
        mission.setTypeface(Typeface.createFromAsset(this.getAssets(), "GodoM.ttf"));
        mission.setLocation(224 * ScreenParameter.getScreenparam_x(), 1201 * ScreenParameter.getScreenparam_y());

        mission_num = new onTextView(this);
        mission_num.setText("33");
        mission_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        mission_num.setTextColor(Color.rgb(237, 133, 133));
        mission_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        mission_num.setLocation(376 * ScreenParameter.getScreenparam_x(), 1269 * ScreenParameter.getScreenparam_y());

        entry = new onTextView(this);
        entry.setText("ENTRY");
        entry.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        entry.setTextColor(Color.rgb(0, 255, 255));
        entry.setTypeface(Typeface.createFromAsset(this.getAssets(), "GodoM.ttf"));
        entry.setLocation(1013 * ScreenParameter.getScreenparam_x(), 720 * ScreenParameter.getScreenparam_y());

        entry_num = new onTextView(this);
        entry_num.setText("24");
        entry_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        entry_num.setTextColor(Color.rgb(204, 255, 255));
        entry_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        entry_num.setLocation(1013 * ScreenParameter.getScreenparam_x(), 788 * ScreenParameter.getScreenparam_y());

        recycle = new onTextView(this);
        recycle.setText("RECYCLE");
        recycle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        recycle.setTextColor(Color.rgb(0, 255, 255));
        recycle.setTypeface(Typeface.createFromAsset(this.getAssets(), "GodoM.ttf"));
        recycle.setLocation(1013 * ScreenParameter.getScreenparam_x(), 964 * ScreenParameter.getScreenparam_y());

        recycle_num = new onTextView(this);
        recycle_num.setText("16");
        recycle_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        recycle_num.setTextColor(Color.rgb(204, 255, 255));
        recycle_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        recycle_num.setLocation(1013 * ScreenParameter.getScreenparam_x(), 1032 * ScreenParameter.getScreenparam_y());

        prize = new onTextView(this);
        prize.setText("PRIZE");
        prize.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        prize.setTextColor(Color.rgb(0, 255, 255));
        prize.setTypeface(Typeface.createFromAsset(this.getAssets(), "GodoM.ttf"));
        prize.setLocation(1013 * ScreenParameter.getScreenparam_x(), 1201 * ScreenParameter.getScreenparam_y());

        prize_num = new onTextView(this);
        prize_num.setText("3rd");
        prize_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        prize_num.setTextColor(Color.rgb(204, 255, 255));
        prize_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        prize_num.setLocation(1013 * ScreenParameter.getScreenparam_x(), 1269 * ScreenParameter.getScreenparam_y());


        TouchImageView list_frame = new TouchImageView(this);
        list_frame.setSize(1424 * ScreenParameter.getScreenparam_x(), 615 * ScreenParameter.getScreenparam_y());
        list_frame.setLocation(8 * ScreenParameter.getScreenparam_x(), 1765 * ScreenParameter.getScreenparam_y());
        list_frame.setBackground(R.drawable.village_frame);
        main.addView(list_frame);


        for (int i = 0; i < 5; i++) {

            rd = new Random();

            relativeLayouts[i] = new RelativeLayout(this);
            paramses[i] = new RelativeLayout.LayoutParams((int) (166 * ScreenParameter.getScreenparam_x()), (int) (10 * ScreenParameter.getScreenparam_y()));
            Boxes[i] = new TouchImageView(this);
            Boxes[i].setSize(rd.nextInt((1000) % 147 + 10), 20);
            Boxes[i].setLocation(0, 0);
            relativeLayouts[i].addView(Boxes[i]);

            if (i < 3)
                Boxes[i].setBackgroundColor(Color.rgb(233, 103, 103));
            else
                Boxes[i].setBackgroundColor(Color.rgb(0, 255, 255));
        }

        paramses[0].setMargins((int) (257 * ScreenParameter.getScreenparam_x()), (int) (848 * ScreenParameter.getScreenparam_y()), 0, 0);
        paramses[1].setMargins((int) (257 * ScreenParameter.getScreenparam_x()), (int) (1089 * ScreenParameter.getScreenparam_y()), 0, 0);
        paramses[2].setMargins((int) (257 * ScreenParameter.getScreenparam_x()), (int) (1330 * ScreenParameter.getScreenparam_y()), 0, 0);
        paramses[3].setMargins((int) (1017 * ScreenParameter.getScreenparam_x()), (int) (848 * ScreenParameter.getScreenparam_y()), 0, 0);
        paramses[4].setMargins((int) (1017 * ScreenParameter.getScreenparam_x()), (int) (1089 * ScreenParameter.getScreenparam_y()), 0, 0);

        for (int i = 0; i < 5; i++) {
            relativeLayouts[i].setLayoutParams(paramses[i]);
            relativeLayouts[i].setBackgroundColor(Color.rgb(74, 85, 95));
        }

        pop_up = new TouchImageView(this);
        pop_up.setSize(1257 * ScreenParameter.getScreenparam_x(), 1074 * ScreenParameter.getScreenparam_y());
        pop_up.setLocation(93 * ScreenParameter.getScreenparam_x(), 440 * ScreenParameter.getScreenparam_y());
        pop_up.setBackground(R.drawable.village_window);


        btn_x = new ClickButtonView(this, R.drawable.button_base, R.drawable.button_click) {
            @Override
            public void clickEvent() {

            }
        };
        btn_x.setSize(117 * ScreenParameter.getScreenparam_x(), 116 * ScreenParameter.getScreenparam_y());
        btn_x.setLocation(1278 * ScreenParameter.getScreenparam_x(), 390 * ScreenParameter.getScreenparam_y());

        final int show_time = 1000;

        remove_when_show = new Handler() {
            @Override
            public void handleMessage(Message msg) {
            }
        };
/*
        big_land.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    showViews();
                    remove_when_show.sendEmptyMessage(1000);

                }

                return true;
            }
        });
*/

    }

    public void showViews() {

        ispopup = true;
        canclose = false;

        final Random rd = new Random();

        for (int i = 0; i < 6; i++) {
            scale_1[i] = (rd.nextInt(100) % 21 + 11) / 10.0f;
            scale_2[i] = (rd.nextInt(100) % 10) / 10.0f;
        }

        main.removeView(lands);
        main.addView(pop_up);
        main.addView(btn_x);
        // main.addView(small_land);
        main.addView(inner_circle);
        main.addView(outer_circle);
        main.addView(month);
        main.addView(year);

        ScaleAnimation ani_c = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        ani_c.setDuration(150);
        pop_up.startAnimation(ani_c);

        final int time = 350;

        ScaleAnimation ani_c2 = new ScaleAnimation(0, 2.0f, 0, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //  ani_c2.setDuration(time);
        //  small_land.startAnimation(ani_c2);

        ani_c2 = new ScaleAnimation(0, 1.8f, 0, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ani_c2.setDuration(time);
        inner_circle.startAnimation(ani_c2);

        ani_c2 = new ScaleAnimation(0, 1.4f, 0, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ani_c2.setDuration(time);
        outer_circle.startAnimation(ani_c2);


        ani_c2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ani_c2.setDuration(time * 2);
        month.startAnimation(ani_c2);
        year.startAnimation(ani_c2);


        final Handler hd = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                main.addView(small_land);

                main.addView(stick1);
                main.addView(stick2);
                main.addView(stick3);
                main.addView(stick4);
                main.addView(stick5);
                main.addView(stick6);
                main.addView(point);
                main.addView(item);
                main.addView(mission);
                main.addView(entry);
                main.addView(recycle);
                main.addView(prize);
                main.addView(point_num);

                for (int i = 0; i < 5; i++)
                    main.addView(relativeLayouts[i]);


                ScaleAnimation ani_c2 = new ScaleAnimation(2.0f, 0.8f, 2.0f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ani_c2.setDuration((int) (time * 2));
                small_land.startAnimation(ani_c2);

                ani_c2 = new ScaleAnimation(1.8f, 1, 1.8f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ani_c2.setDuration((int) (time * 2));
                inner_circle.startAnimation(ani_c2);

                ani_c2 = new ScaleAnimation(1.4f, 1, 1.4f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ani_c2.setDuration((int) (time * 3));
                outer_circle.startAnimation(ani_c2);


                ani_c2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ani_c2.setDuration(time * 2);

                point.startAnimation(ani_c2);
                item.startAnimation(ani_c2);
                mission.startAnimation(ani_c2);
                entry.startAnimation(ani_c2);
                recycle.startAnimation(ani_c2);
                prize.startAnimation(ani_c2);


                ani_c2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 1);
                ani_c2.setDuration(time * 2);
                stick1.startAnimation(ani_c2);

                ani_c2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0.5f);
                ani_c2.setDuration(time * 2);
                stick2.startAnimation(ani_c2);

                ani_c2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
                ani_c2.setDuration(time * 2);
                stick3.startAnimation(ani_c2);

                ani_c2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
                ani_c2.setDuration(time * 2);
                stick4.startAnimation(ani_c2);

                ani_c2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
                ani_c2.setDuration(time * 2);
                stick5.startAnimation(ani_c2);

                ani_c2 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                ani_c2.setDuration(time * 2);
                stick6.startAnimation(ani_c2);

                point_num.startAnimation(ani_c2);


                for (int i = 0; i < 5; i++) {
                    ani_c2 = new ScaleAnimation(0, scale_1[i], 1, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
                    ani_c2.setDuration(time * 2);
                    Boxes[i].startAnimation(ani_c2);
                }


            }
        };

        hd2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                inner_circle.setRotation(degree * 2);
                outer_circle.setRotation(-degree * 4);

                degree += 0.25;

                sendEmptyMessageDelayed(0, 1);
            }
        };

        final Handler hd3 = new Handler() {

            @Override
            public void handleMessage(Message msg) {


                main.addView(prize_num);
                main.addView(recycle_num);
                main.addView(mission_num);
                main.addView(item_num);
                main.addView(entry_num);

                ScaleAnimation ani_c2 = new ScaleAnimation(0.8f, 1.2f, 0.8f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ani_c2.setDuration((int) (time * 1.5));
                small_land.startAnimation(ani_c2);


                for (int i = 0; i < 5; i++) {
                    ani_c2 = new ScaleAnimation(scale_1[i], scale_2[i], 1, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
                    ani_c2.setDuration((int) (time * 1.5));
                    Boxes[i].startAnimation(ani_c2);
                }


                Animation ani_c1 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                //Animation ani_c1 = new Animation(0,255);
                ani_c1.setDuration(time * 2);

                item_num.startAnimation(ani_c1);
                prize_num.startAnimation(ani_c1);
                mission_num.startAnimation(ani_c1);
                entry_num.startAnimation(ani_c1);
                recycle_num.startAnimation(ani_c1);
            }
        };

        final Handler hd4 = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                ScaleAnimation ani_c2 = new ScaleAnimation(1.2f, 1, 1.2f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ani_c2.setDuration((int) (time * 2));
                small_land.startAnimation(ani_c2);


                for (int i = 0; i < 5; i++) {
                    ani_c2 = new ScaleAnimation(scale_2[i], 1, 1, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
                    ani_c2.setDuration(time * 2);
                    Boxes[i].startAnimation(ani_c2);
                }

            }
        };

        final Handler points = new Handler() {
            int point = 11270;

            @Override
            public void handleMessage(Message msg) {
                point = rd.nextInt(1000000000) % 11270;
                point_num.setText(Integer.toString(point));

                sendEmptyMessageDelayed(0, 1);
            }
        };

        points.sendEmptyMessage(0);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                hd.sendEmptyMessage(0);
                hd2.sendEmptyMessage(0);
            }
        }, time);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, time * 3);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                hd3.sendEmptyMessage(0);

            }
        }, (int) (time * 3));


        final Handler setpoint = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                point_num.setText(Integer.toString(11270));
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                hd4.sendEmptyMessage(0);
                points.removeMessages(0);

                setpoint.sendEmptyMessage(0);
                //point_num.setText(Integer.toString(11270));
            }
        }, (int) (time * 4.5));


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // points.removeMessages(0);
                // point_num.setText(Integer.toString(11270));

                canclose = true;

                btn_x.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_UP) {

                            ispopup = false;


                            ScaleAnimation ani_c = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
                            ani_c.setDuration(200);
                            pop_up.startAnimation(ani_c);

                            main.addView(lands);
                            main.removeView(pop_up);
                            main.removeView(small_land);
                            main.removeView(inner_circle);
                            main.removeView(outer_circle);
                            main.removeView(stick1);
                            main.removeView(stick2);
                            main.removeView(stick3);
                            main.removeView(stick4);
                            main.removeView(stick5);
                            main.removeView(stick6);
                            main.removeView(point);
                            main.removeView(point_num);
                            main.removeView(item);
                            main.removeView(item_num);
                            main.removeView(mission);
                            main.removeView(mission_num);
                            main.removeView(entry);
                            main.removeView(entry_num);
                            main.removeView(recycle);
                            main.removeView(recycle_num);
                            main.removeView(prize);
                            main.removeView(prize_num);
                            main.removeView(month);
                            main.removeView(year);
                            main.removeView(btn_x);

                            hd2.removeMessages(0);


                            for (int i = 0; i < 5; i++)
                                main.removeView(relativeLayouts[i]);

                            btn_x.setOnTouchListener(null);

                        }

                        return true;
                    }
                });

            }
        }, (int) (time * 6));

    }

    @Override
    public void onBackPressed()
    {
        if(ispopup) {

            if (canclose) {

                ispopup = false;


                ScaleAnimation ani_c = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
                ani_c.setDuration(200);
                pop_up.startAnimation(ani_c);

                main.addView(lands);
                main.removeView(pop_up);
                main.removeView(small_land);
                main.removeView(inner_circle);
                main.removeView(outer_circle);
                main.removeView(stick1);
                main.removeView(stick2);
                main.removeView(stick3);
                main.removeView(stick4);
                main.removeView(stick5);
                main.removeView(stick6);
                main.removeView(point);
                main.removeView(point_num);
                main.removeView(item);
                main.removeView(item_num);
                main.removeView(mission);
                main.removeView(mission_num);
                main.removeView(entry);
                main.removeView(entry_num);
                main.removeView(recycle);
                main.removeView(recycle_num);
                main.removeView(prize);
                main.removeView(prize_num);
                main.removeView(month);
                main.removeView(year);
                main.removeView(btn_x);

                hd2.removeMessages(0);


                for (int i = 0; i < 5; i++)
                    main.removeView(relativeLayouts[i]);
            }
        }

        else
            super.onBackPressed();
    }

}
