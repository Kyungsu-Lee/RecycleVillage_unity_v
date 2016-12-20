package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import static android.graphics.Color.argb;
import static android.graphics.Color.rgb;

/**
 * Created by Administrator on 2016-07-13.
 */
public class GateMgr extends GateGameView {

    public static boolean master = true;

    public static int gameCount = 0;
    int f=0;
    static boolean feverLife,fsafe;

    public static boolean q[]=new boolean[18];
    public static int noError = 0;
    private static int noError1 = 0;
    private int ci = 0;
    private int i = 0;
    private int limit = 0;
    private int index = 0;
    private int item_no = 7;
    private int lbuild_no = 9;
    private int rbuild_no = 9;
    private int dr;
    private int emoin = 0;
    private int ran, rand;
    public static int curCoin = 0;
    public static int curPoint = 0;
    public boolean gate_enabler = false;
    private boolean gate_broken = false;
    private boolean plus;
    public static boolean isFever;
    public static boolean isFeverBtn,isNaFeverBtn;
    static public boolean lifeCheck0, lifeCheck1, lifeCheck2;

    private List<View> Views;
    private List<View> viewList;
    static public List<Drawable> imgs;

    Context main_Activity;
    RelativeLayout main;

    Handler[] gateHandle;
    Handler[] emotionHandle;
    Handler[] breakHandle;

    Handler pointHandle;

    private int build_no = 23;


    public GateCharacter character, feverCharacter, xCharacter;
    GateStuff[] life;
    //GateObject[] items;
    GateStuff[] breaks;
    GateGameView coinTable;
    GateGameView window;
    GateGameView count, count1;
    GateGameView oGate;
    GateGameView xGate;
    GateGameView o;
    GateGameView x;
    GateGameView star1,star2,star3,star4;
    GateGameView end;
    GateGameView endCpic;
    GateGameView black;
    GateGameView light1, light2;
    GateGameView stageBlack;
    GateGameView stageNo2, stageNo3;
    GateGameView startcnt, startcnt1;


    GateOnTextView question;
    GateOnTextView info;
    GateOnTextView coin;
    GateOnTextView endCoin;
    GateOnTextView bestPoint;


    GateStrokeTextView point;
    GateStrokeTextView endPoint;

    GateButtonView feverTimeButton,feverTimeButtonNa;
    GateButtonView toHome;
    GateButtonView again;


    Handler fHandler, oneHandler, sOneHandler;
    Runnable fRunnable, oneRunnable, sOneRunnable;

    MediaPlayer gate;
    private boolean gateS;

    public GateMgr(Context context, List<View> viewList) {
        super(context, viewList);
    }

    public GateMgr(Context context, List<View> viewList, RelativeLayout r, GateCharacter character, GateOnTextView coin) {
        super(context, viewList);

        gate=MediaPlayer.create(context,R.raw.effectsound_gatecrash);

        // main.setBackground(getResources().getDrawable(R.drawable.bg_1));

        main_Activity = context;
        this.main = r;
        this.character = character;
        this.coin = coin;

        Views = new Vector<View>();

        imgs = new Vector<Drawable>();

        life = new GateStuff[3];

        gateHandle = new Handler[2];
        emotionHandle = new Handler[4];
        breakHandle = new Handler[6];

        for(int a=0;a<18;a++)
        {
            q[a]=true;
        }

        this.gameManager();
        // this.timeManager();


    }

    public GateMgr(Context context, List<View> viewList, RelativeLayout r, int i) {
        super(context, viewList);

        // main.setBackground(getResources().getDrawable(R.drawable.bg_1));

        main_Activity = context;
        this.main = r;

        Views = new Vector<View>();

        imgs = new Vector<Drawable>();

        life = new GateStuff[3];

        gateHandle = new Handler[2];
        emotionHandle = new Handler[4];
        breakHandle = new Handler[6];

        for(int a=0;a<18;a++)
        {
            q[a]=true;
        }

        //new GateTime(main_Activity,Views,main);
        this.initGameManager();
        // this.timeManager();


    }


    public void gameManager() {

        // main.setBackground(getResources().getDrawable(R.drawable.edit_03));
        String[] quesText = new String[15];



            for(int i=0;i<18;i++)
            {
                q[i]=true;
            }


        quesText[0] = "Q1. 요구르트병은 PET병류에 버려야 한다?";

        quesText[1] = "Q2. 스티로폼과 플라스틱은 따로 버려야 한다?";

        quesText[2] = "Q3. 폐형광등은 유리병과 같이 버리면 안된다?";

        quesText[3] = "Q4. 후라이팬과 수저는 같이 재활용 할 수 없다?";

        quesText[4] = "Q5. 기저귀는 재활용 할 수 없다?";

        quesText[5] = "Q6. 1회용 도시락 용기는 음료수 PET병과 같이 버려야 한다?";

        quesText[6] = "Q7. 양말은 재활용 할 수 있다?";

        quesText[7] = "Q8. 유리컵은 유리병과 같이 버리면 된다?";

        quesText[8] = "Q9. 고무제품은 플라스틱과 함께 재활용 할 수 있다?";


        startcnt = new GateGameView(main_Activity, viewList);
        startcnt.setSize(70 * screenparam_x, 350 * screenparam_y);
        startcnt.setLocation(325 * screenparam_x, 300 * screenparam_y);
        startcnt.setCanDrag(false);
        startcnt.setCanMove(false);

        startcnt1 = new GateGameView(main_Activity,viewList);
        startcnt1.setSize(45* screenparam_x, 350 * screenparam_y);
        startcnt1.setLocation(335* screenparam_x , 300 * screenparam_y);
        startcnt1.setCanDrag(false);
        startcnt1.setCanMove(false);

        coinTable = new GateGameView(main_Activity, viewList);
        coinTable.setSize(110 * screenparam_x, 100 * screenparam_y);
        coinTable.setLocation(600 * screenparam_x, 40 * screenparam_y);
        coinTable.setBackground(R.drawable.but_point);
        coinTable.setCanDrag(false);
        coinTable.setCanMove(false);
        main.addView(coinTable);

        coin.bringToFront();

        point = new GateStrokeTextView(main_Activity);
        point.setLocation(screenparam_x * 695, screenparam_y * 160); //1의자리:695 ,680, 665
        point.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoB.ttf"));
        point.setTextSize(27);
        point.setTextColor(rgb(65, 64, 66));

        main.addView(point);


        if (curPoint >= 10 && curPoint < 100)
            point.setLocation(screenparam_x * 680, screenparam_y * 160);
        if (curPoint >= 100 && curPoint < 1000)
            point.setLocation(screenparam_x * 665, screenparam_y * 160);
        if (curPoint >= 1000 && curPoint < 10000)
            point.setLocation(screenparam_x * 650, screenparam_y * 160);


        String topoint = Integer.toString(curPoint);
        point.setText(topoint);


        feverCharacter = new GateCharacter(main_Activity, viewList, main);
        if (GateRunnerActivity.charType == 0) {
            //노랑머리
            feverCharacter.setImges(R.drawable.fchar1_f, R.drawable.fchar1_f, R.drawable.fchar1_left_f, R.drawable.fchar1_right_f);
        }
        if (GateRunnerActivity.charType == 1) {
            //검은머리
            feverCharacter.setImges(R.drawable.fchar2_f, R.drawable.fchar2_f, R.drawable.fchar2_left_f, R.drawable.fchar2_right_f);
        }
        if (GateRunnerActivity.charType == 2) {
            //검은머리
            feverCharacter.setImges(R.drawable.fchar3_f, R.drawable.fchar3_f, R.drawable.fchar3_left_f, R.drawable.fchar3_right_f);
        }
        feverCharacter.setCanDrag(true);


        window = new GateGameView(main_Activity, viewList);
        window.setSize(470 * screenparam_x, 390 * screenparam_y);
        window.setLocation(130 * screenparam_x, 240 * screenparam_y);
        window.setBackground(R.drawable.quiz_60);
        window.setCanDrag(false);
        window.setCanMove(false);
        //main.addView(window);

        question = new GateOnTextView(main_Activity);
        question.setLocation(screenparam_x * 170, screenparam_y * 440);
        question.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoM.ttf"));
        question.setTextSize(15);
        question.setTextColor(rgb(255, 255, 255));
        switch (gameCount) {
            case 0:
                question.setText(quesText[0]);
                break;
            case 2:
                question.setText(quesText[1]);
                break;
            case 4:
                question.setText(quesText[2]);
                break;
            case 6:
                question.setText(quesText[3]);
                break;
            case 8:
                question.setText(quesText[4]);
                break;
            case 10:
                question.setText(quesText[5]);
                break;
            case 12:
                question.setText(quesText[6]);
                break;
            case 14:
                question.setText(quesText[7]);
                break;
            case 16:
                question.setText(quesText[8]);
                break;
            case 18:
                question.setText(quesText[9]);
                break;
            case 20:
                question.setText(quesText[10]);
                break;
            case 22:
                question.setText(quesText[11]);
                break;
            case 24:
                question.setText(quesText[12]);
                break;
            case 26:
                question.setText(quesText[13]);
                break;
            case 28:
                question.setText(quesText[14]);
                break;

        }

        // main.addView(question);

        info = new GateOnTextView(main_Activity);
        info.setLocation(screenparam_x * 170, screenparam_y * 515);
        info.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoM.ttf"));
        info.setTextSize(12);
        info.setTextColor(rgb(169, 169, 169));
        info.setText("문제를 읽고 OX게이트로 이동해 주세요");
        //main.addView(info);

        count = new GateGameView(main_Activity, viewList);
        count.setSize(40 * screenparam_x, 200 * screenparam_y);
        count.setLocation(530 * screenparam_x, 370 * screenparam_y);
        count.setBackground(R.drawable.but_3);
        count.setCanDrag(false);
        count.setCanMove(false);
        //main.addView(count);

        count1 = new GateGameView(main_Activity, viewList);
        count1.setSize(25 * screenparam_x, 200 * screenparam_y);
        count1.setLocation(535 * screenparam_x, 370 * screenparam_y);
        count1.setCanDrag(false);
        count1.setCanMove(false);

        oGate = new GateGameView(main_Activity, viewList);
        oGate.setSize(60 * screenparam_x, 300 * screenparam_y);
        oGate.setLocation(400 * screenparam_x, -350 * screenparam_y); //원래 270
        oGate.setBackground(R.drawable.gate_o);
        oGate.setCanDrag(false);
        oGate.setCanMove(false);
        // main.addView(oGate);

        xGate = new GateGameView(main_Activity, viewList);
        xGate.setSize(60 * screenparam_x, 300 * screenparam_y);
        xGate.setLocation(520 * screenparam_x, -350 * screenparam_y);
        xGate.setBackground(R.drawable.gate_x);
        xGate.setCanDrag(false);
        xGate.setCanMove(false);
        //main.addView(xGate);

        o = new GateGameView(main_Activity, viewList);
        o.setSize(180 * screenparam_x, 540 * screenparam_y);
        o.setLocation(270 * screenparam_x, 130 * screenparam_y);
        o.setBackground(R.drawable.ox_o);
        o.setCanDrag(false);
        o.setCanMove(false);
        // main.addView(o);

        x = new GateGameView(main_Activity, viewList);
        x.setSize(180 * screenparam_x, 540 * screenparam_y);
        x.setLocation(270 * screenparam_x, 130 * screenparam_y);
        x.setBackground(R.drawable.ox_x);
        x.setCanDrag(false);
        x.setCanMove(false);
        // main.addView(x);


        feverTimeButtonNa = new GateButtonView(main_Activity, viewList, R.drawable.button_un);
        feverTimeButtonNa.setSize(90 * screenparam_x, 270 * screenparam_y);
        feverTimeButtonNa.setLocation(10 * screenparam_x, 620 * screenparam_y);


        feverTimeButton = new GateButtonView(main_Activity, viewList, R.drawable.button);
        feverTimeButton.setSize(80 * screenparam_x, 250 * screenparam_y);
        feverTimeButton.setLocation(10 * screenparam_x, 620 * screenparam_y);
        feverTimeButton.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //feverTimeButton.Click();
                   // main.removeView(character);
                    if (GateRunnerActivity.charType == 0) {
                        //노랑머리
                        character.setImges(R.drawable.fchar1_f, R.drawable.fchar1_f, R.drawable.fchar1_left_f, R.drawable.fchar1_right_f);
                    }
                    if (GateRunnerActivity.charType == 1) {
                        //검은머리
                        character.setImges(R.drawable.fchar2_f, R.drawable.fchar2_f, R.drawable.fchar2_left_f, R.drawable.fchar2_right_f);
                    }
                    if (GateRunnerActivity.charType == 2) {
                        //검은머리
                        character.setImges(R.drawable.fchar3_f, R.drawable.fchar3_f, R.drawable.fchar3_left_f, R.drawable.fchar3_right_f);
                    }

                    isFever = true;

                    fRunnable = new Runnable() {
                        @Override
                        public void run() {
                            isFever = false;
                           // feverLife=true;
                            isFeverBtn = false;


                            if (GateRunnerActivity.charType == 0) {
                                //노랑머리
                                character.setImges(R.drawable.char1_walk1, R.drawable.char1_walk1, R.drawable.char1_left1, R.drawable.char1_right1);
                            }
                            if (GateRunnerActivity.charType == 1) {
                                //검은머리
                                character.setImges(R.drawable.char2_walk1, R.drawable.char2_walk1, R.drawable.char2_left1, R.drawable.char2_right1);
                            }
                            if (GateRunnerActivity.charType == 2) {
                                //감자
                                character.setImges(R.drawable.char3_walk1, R.drawable.char3_walk1, R.drawable.char3_left1, R.drawable.char3_right1);
                            }
                           // main.addView(character);

                            main.removeView(feverTimeButton);

                        }
                    };
                    fHandler = new Handler();
                    if (GateRunnerActivity.charType == 0) {
                        //빅헫
                        fHandler.postDelayed(fRunnable, 3000);
                    }
                    if (GateRunnerActivity.charType == 1) {
                        //너루토
                        fHandler.postDelayed(fRunnable, 7000);
                    }
                    if (GateRunnerActivity.charType == 2) {
                        //감튀
                        fHandler.postDelayed(fRunnable, 13000);
                    }
                    //fHandler.postDelayed(fRunnable,3000);//피버캐릭터 지속시간 조건문걸기
                }
                return true;


            }
        });


        star1 = new GateGameView(main_Activity, viewList);
        star1.setSize(170 * screenparam_x, 100 * screenparam_x);
        star1.setLocation(280 * screenparam_x,60 * screenparam_y);
        star1.setBackground(R.drawable.lighteffect4);
        star1.setCanDrag(false);
        star1.setCanMove(false);

        black = new GateGameView(main_Activity, viewList);
        black.setSize(screen_x, screen_y);
        black.setLocation(0 * screenparam_x, 0 * screenparam_y);
        black.setBackgroundColor(argb(120, 35, 31, 32));
        black.setCanDrag(false);
        black.setCanMove(false);
        //   main.addView(black);

        end = new GateGameView(main_Activity, viewList);
        end.setSize(450 * screenparam_x, screen_y);
        end.setLocation(140 * screenparam_x, 0 * screenparam_y);
        end.setBackground(R.drawable.gateendbase);
        end.setCanDrag(false);
        end.setCanMove(false);
        //   main.addView(end);

        endCpic = new GateGameView(main_Activity, viewList);
        endCpic.setSize(30 * screenparam_x, 30 * screenparam_x);
        endCpic.setLocation(325 * screenparam_x, 640 * screenparam_y);
        endCpic.setBackground(R.drawable.but_36);
        endCpic.setCanDrag(false);
        endCpic.setCanMove(false);
        //      main.addView(endCpic);


        //endPoint = new GateStrokeTextView(main_Activity);
        endPoint = new GateStrokeTextView(main_Activity);
        endPoint.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoB.ttf"));
        endPoint.setTextSize(53);
        endPoint.setTextColor(rgb(255, 219, 99));


        //  main.addView(endPoint);


        endCoin = new GateOnTextView(main_Activity);
        endCoin.setLocation(screenparam_x * 360, screenparam_y * 660); //1의자리:695 ,680, 665
        endCoin.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoB.ttf"));
        endCoin.setTextSize(20);
        endCoin.setTextColor(rgb(255, 210, 61));

        //   main.addView(endCoin);

        bestPoint = new GateOnTextView(main_Activity);
        bestPoint.setLocation(screenparam_x * 320, screenparam_y * 540); //1의자리:695 ,680, 665
        bestPoint.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoB.ttf"));
        bestPoint.setTextSize(21);
        bestPoint.setTextColor(argb(140, 35, 31, 32));
        //String to2 = Integer.toString(curPoint);
        // bestPoint.setText("최고기록 " +to);
        //  main.addView(bestPoint);


        toHome = new GateButtonView(main_Activity, viewList, R.drawable.but_34);
        toHome.setSize(60 * screenparam_x, 250 * screenparam_y);
        toHome.setLocation(280 * screenparam_x, 800 * screenparam_y);
        toHome.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    GateMySQLiteOpenHelper helper;
                    String dbName = "st_file.db";
                    int dbVersion = 1; // 데이터베이스 버전
                    SQLiteDatabase db;
                    String tag = "SQLite"; // Log 에 사용할 tag

                    helper = new GateMySQLiteOpenHelper(
                            main_Activity,  // 현재 화면의 제어권자
                            dbName,// db 이름
                            null,  // 커서팩토리-null : 표준커서가 사용됨
                            dbVersion);       // 버전

                    db = helper.getWritableDatabase(); // 읽고 쓸수 있는 DB

                    db.execSQL("create table if not exists endgate(count integer,name text,PRIMARY KEY(name));");

                    try
                    {
                        db.execSQL("insert into endgate (count,name) values(" + 0 + ",'player');");
                    }

                    catch (SQLException e)
                    {
                        Log.d("message", e.toString());
                    }

                    try {
                        db.execSQL("update endgate set score="+1+" where name='player';");
                    }

                    catch (SQLException e)
                    {
                        Log.d("message", e.toString());
                    }


                    android.os.Process.killProcess(android.os.Process.myPid());
                    ((Activity)(main_Activity)).finish();
                }
                return true;


            }
        });

        //  main.addView(toHome);


        again = new GateButtonView(main_Activity, viewList, R.drawable.but_35);
        again.setSize(60 * screenparam_x, 250 * screenparam_y);
        again.setLocation(395 * screenparam_x, 795 * screenparam_y);
        again.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {


                    Intent i = ((Activity)(main_Activity)).getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(((Activity)(main_Activity)). getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    ((Activity)(main_Activity)).finish();
                    ((Activity)(main_Activity)).startActivity(i);
                    android.os.Process.killProcess(android.os.Process.myPid());

                }
                return true;


            }
        });


        stageBlack = new GateGameView(main_Activity, viewList);
        stageBlack.setSize(screen_x, screen_y);
        stageBlack.setLocation(0 * screenparam_x, 0 * screenparam_y);
        stageBlack.setBackgroundColor(rgb(35, 31, 32));
        stageBlack.setCanDrag(false);
        stageBlack.setCanMove(false);
        //main.addView(stageBlack);


        stageNo2 = new GateGameView(main_Activity, viewList);
        stageNo2.setSize(170 * screenparam_x, 50 * screenparam_x);
        stageNo2.setLocation(285 * screenparam_x, 430 * screenparam_y);
        stageNo2.setBackground(R.drawable.stage2);
        stageNo2.setCanDrag(false);
        stageNo2.setCanMove(false);
        //  main.addView(stageNo2);

        stageNo3 = new GateGameView(main_Activity, viewList);
        stageNo3.setSize(170 * screenparam_x, 50 * screenparam_x);
        stageNo3.setLocation(285 * screenparam_x, 430 * screenparam_y);
        stageNo3.setBackground(R.drawable.stage3);
        stageNo3.setCanDrag(false);
        stageNo3.setCanMove(false);
        //  main.addView(stageNo3);

        light1 = new GateGameView(main_Activity, viewList);
        light1.setSize(400 * screenparam_x, 60 * screenparam_x);
        light1.setLocation(0 * screenparam_x, 400 * screenparam_y);
        light1.setBackground(R.drawable.stage_l);
        light1.setCanDrag(false);
        light1.setCanMove(false);
        //  main.addView(light1);


        light2 = new GateGameView(main_Activity, viewList);
        light2.setSize(400 * screenparam_x, 60 * screenparam_x);
        light2.setLocation(300 * screenparam_x, 430 * screenparam_y);
        light2.setBackground(R.drawable.stage_r);
        light2.setCanDrag(false);
        light2.setCanMove(false);
        // main.addView(light2);
        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();

    }

    public void initGameManager() {



        for(int i=0;i<18;i++)
        {
            q[i]=true;
        }


        for (int i = 0; i < 3; i++) {
            life[i] = new GateStuff(main_Activity, viewList, main);
            life[i].setBackground(R.drawable.heart);
            life[i].setSize(30 * screenparam_x, 80 * screenparam_y);
            life[i].setLocation(20 * screenparam_x + (i * 40 * screenparam_x), 50 * screenparam_y);

            if (gameCount == 0) {
                lifeCheck0 = true;
                lifeCheck1 = true;
                lifeCheck2 = true;
                life[i].setLife(true);
            } else {
                switch (i) {
                    case 0:
                        life[i].setLife(lifeCheck0);
                        break;
                    case 1:
                        life[i].setLife(lifeCheck1);
                        break;
                    case 2:
                        life[i].setLife(lifeCheck2);
                        break;
                }
            }

            if (life[i].getLife())
                main.addView(life[i]);
        }

        coin = new GateOnTextView(main_Activity);
        coin.setLocation(screenparam_x * 685, screenparam_y * 56); //1의자리:685 ,675
        coin.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoB.ttf"));
        coin.setTextSize(23);
        coin.setTextColor(rgb(255, 200, 0));
        //    coin.setText("999");
        main.addView(coin);


        if (curCoin >= 10 && curCoin < 100)
            coin.setLocation(screenparam_x * 675, screenparam_y * 56);
        if (curCoin >= 100 && curCoin < 1000)
            coin.setLocation(screenparam_x * 665, screenparam_y * 56);
        if (curCoin >= 1000 && curCoin < 10000)
            coin.setLocation(screenparam_x * 655, screenparam_y * 56);


        String tocoin = Integer.toString(curCoin);
        coin.setText(tocoin);


        character = new GateCharacter(main_Activity, viewList, main);
        if (GateRunnerActivity.charType == 0) {
            //노랑머리
            character.setImges(R.drawable.char1_walk1, R.drawable.char1_walk1, R.drawable.char1_left1, R.drawable.char1_right1);
        }
        if (GateRunnerActivity.charType == 1) {
            //검은머리
            character.setImges(R.drawable.char2_walk1, R.drawable.char2_walk1, R.drawable.char2_left1, R.drawable.char2_right1);
        }
        if (GateRunnerActivity.charType == 2) {
            //감자
            character.setImges(R.drawable.char3_walk1, R.drawable.char3_walk1, R.drawable.char3_left, R.drawable.char3_right);
        }
       // character.setSize(80 * screenparam_x, 365 * screenparam_y);
        character.setSize(150 * screenparam_x, 500* screenparam_y);//200
        character.setLocation(285* screenparam_x, 760 * screenparam_y);
        character.setCanDrag(true);
        main.addView(character);

        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();

        new GateTime(main_Activity, Views, main, character, life, coin);



    }


    public void increasePoint() {
        pointHandle = new Handler() { //hanlder 오브젝트를 생성해서 overriding

            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
            {
                noError1++;

                if (noError1 % 2 == 0) {
                    curPoint = curPoint + 10;
                    String to = Integer.toString(curPoint);

                    if (curPoint >= 10 && curPoint < 100)
                        point.setLocation(screenparam_x * 680, screenparam_y * 160);
                    if (curPoint >= 100 && curPoint < 1000)
                        point.setLocation(screenparam_x * 665, screenparam_y * 160);
                    if (curPoint >= 1000 && curPoint < 10000)



                        point.setLocation(screenparam_x * 650, screenparam_y * 160);

                    if (curPoint >= 2000 &&curPoint<4000&& !isFeverBtn&&!feverLife) {
                        feverLife=true;
                        if(isNaFeverBtn) {
                            main.removeView(feverTimeButtonNa);
                            isNaFeverBtn=false;
                        }
                        main.addView(feverTimeButton);
                        isFeverBtn = true;

                    }
                    if(curPoint>=4000&&!fsafe)
                    {
                        feverLife=false;
                        fsafe=true;
                    }
                    if (curPoint>=4000&& !isFeverBtn&&!feverLife) {
                        main.removeView(feverTimeButtonNa);
                        main.addView(feverTimeButton);
                        isFeverBtn = true;

                    }

                    if(!isFeverBtn) {
                        if(!isNaFeverBtn) {
                            isNaFeverBtn = true;
                            main.addView(feverTimeButtonNa); //이거해결해
                        }
                    }
                    main.removeView(point);
                    point.setText(to);
                    main.addView(point);
                }
                sendEmptyMessageDelayed(0, 1000); //무한 실행. (delay, msec)
            }

        };

        pointHandle.sendEmptyMessage(0);//handler 실행


    }

    public void countDown(){

        main.addView(startcnt);
        main.addView(startcnt1);

        ScaleAnimation ani;
        int duration3 = 1000;
        int duration = 2500;
        final int duration1 =1000;



        ani = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        startcnt.startAnimation(ani);
        ani.setDuration(1000);
        ani.setFillAfter(true);

        AnimationDrawable mAnimation = new AnimationDrawable();
        //BitmapDrawable frame1 = (BitmapDrawable)getResources().getDrawable(R.drawable.but_1);
        BitmapDrawable frame2 = (BitmapDrawable)getResources().getDrawable(R.drawable.but_2);
        BitmapDrawable frame3 = (BitmapDrawable)getResources().getDrawable(R.drawable.but_3);
        mAnimation.addFrame(frame3, duration3);
        mAnimation.addFrame(frame2, duration);
        //mAnimation.addFrame(frame1, duration1);
        startcnt.setBackground(mAnimation);
        mAnimation.start();


        sOneRunnable = new Runnable() {
            @Override
            public void run() {
                main.removeView(startcnt);
                AnimationDrawable mAnimation1 = new AnimationDrawable();
                BitmapDrawable frame1 = (BitmapDrawable)getResources().getDrawable(R.drawable.but_1);
                mAnimation1.addFrame(frame1, duration1);
                startcnt1.setBackground(mAnimation1);
                mAnimation1.start();
            }
        };
        sOneHandler = new Handler();
        sOneHandler.postDelayed(sOneRunnable, 1500);//2초후...



    }
    public void countDownEnd(){

        ScaleAnimation aniEnd;

        aniEnd = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        startcnt.startAnimation(aniEnd);
        aniEnd.setDuration(1000);
        aniEnd.setFillAfter(true);
        main.removeView(startcnt1);
    }
    public void openWindow() {



        pointHandle.removeMessages(0);
        main.addView(window);
        main.addView(question);
        main.addView(info);
        main.addView(count);
        main.addView(count1);

        ScaleAnimation[] ani;
        ani = new ScaleAnimation[4];
        int duration3 = 1500;
        int duration = 1500;
        final int duration1 = 500;


        for (int i = 0; i < 4 * item_no; i++) {
            // items[i].setAffect(false);
            //  items[i]=null;
        }


        for (int i = 0; i < 4; i++) {

            switch (i) {
                case 0:

                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    // ani[i].setStartOffset(18000);
                    window.startAnimation(ani[i]);

                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);

                    break;

                case 1:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
                    //  ani[i].setStartOffset(18000);
                    question.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);
                    break;

                case 2:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1.3f, Animation.RELATIVE_TO_SELF, -2f);
                    // ani[i].setStartOffset(18000);
                    info.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);
                    break;
                case 3:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, -4, Animation.RELATIVE_TO_SELF, 0.5f);
                    //  ani[i].setStartOffset(18000);
                    count.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);


                    AnimationDrawable mAnimation = new AnimationDrawable();

                    //   BitmapDrawable frame1 = (BitmapDrawable)getResources().getDrawable(R.drawable.but_1);
                    BitmapDrawable frame2 = (BitmapDrawable) getResources().getDrawable(R.drawable.but_2);
                    BitmapDrawable frame3 = (BitmapDrawable) getResources().getDrawable(R.drawable.but_3);
                    mAnimation.addFrame(frame3, duration3);
                    mAnimation.addFrame(frame2, duration);
                    // mAnimation.addFrame(frame1, duration1);

                    count.setBackground(mAnimation);
                    mAnimation.start();


                    oneRunnable = new Runnable() {
                        @Override
                        public void run() {
                            main.removeView(count);
                            AnimationDrawable mAnimation1 = new AnimationDrawable();
                            BitmapDrawable frame1 = (BitmapDrawable) getResources().getDrawable(R.drawable.but_1);
                            mAnimation1.addFrame(frame1, duration1);
                            count1.setBackground(mAnimation1);
                            mAnimation1.start();
                        }
                    };
                    oneHandler = new Handler();
                    oneHandler.postDelayed(oneRunnable, 2500);//26

                    break;


            }


        }

        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();


    }

    public void closeWindow() {
        ScaleAnimation[] ani_c;
        ani_c = new ScaleAnimation[4];

        for (int i = 0; i < 4; i++) {

            switch (i) {
                case 0:

                    ani_c[i] = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    //  ani_c[i].setStartOffset(26000);
                    window.startAnimation(ani_c[i]);
                    ani_c[i].setDuration(1000);
                    ani_c[i].setFillAfter(true);
                    break;

                case 1:
                    ani_c[i] = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);
                    //  ani_c[i].setStartOffset(26000);
                    question.startAnimation(ani_c[i]);
                    ani_c[i].setDuration(1000);
                    ani_c[i].setFillAfter(true);
                    break;

                case 2:
                    ani_c[i] = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 1.3f, Animation.RELATIVE_TO_SELF, -2f);
                    //   ani_c[i].setStartOffset(26000);
                    info.startAnimation(ani_c[i]);
                    ani_c[i].setDuration(1000);
                    ani_c[i].setFillAfter(true);
                    break;
                case 3:
                    ani_c[i] = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, -4, Animation.RELATIVE_TO_SELF, 0.5f);
                    // ani_c[i].setStartOffset(26000);
                    count.startAnimation(ani_c[i]);
                    ani_c[i].setDuration(1000);
                    ani_c[i].setFillAfter(true);

                    break;


            }


        }
        main.removeView(count1);

    }


    public void moveGates() {



        gate_enabler = true;
        gateS=true;

        main.addView(oGate);
        main.addView(xGate);

        gateHandle[0] = new Handler() { //hanlder 오브젝트를 생성해서 overriding


            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
            {


                oGate.setY(oGate.getY() + 15);
                oGate.setX(oGate.getX() - 8f);


                oGate.setSize(oGate.getWIDTH() + 8, oGate.getHEIGHT() + 8);
                oGate.setLocation(300 * screenparam_x, 0 * screenparam_y);//원래 270

                if (oGate.getY() < 1000 * screenparam_y && oGate.getY() > 400 * screenparam_y && oGate.getX() < character.getX() + character.getWIDTH() -80*screenparam_x&& oGate.getX() + oGate.getWIDTH() > character.getX()+80*screenparam_x) {
                    // character.setBackground(R.drawable.char_o4);
                    /*
                    if(gateS) {

                        gateS=false;
                    }
                    */
                    if (gate_enabler == true) {
                        gate.start();
                        character.bringToFront();
                        //System.out.println(noError);
                        noError++;

                        q[0]=false;
                        q[2] = true;
                        q[4]=true;
                        q[6] = true;
                        q[8]=true;
                        q[10]=false;
                        q[12]=false;
                        q[14]=false;
                        q[16]=false;
                        oGate.setBackground(R.drawable.break_left);
                        makeBreakGate(true);//왼쪽문이 부서지는 경우
                        // System.out.println(curPoint);


                        gate_enabler = false;
                    } else
                        oGate.bringToFront();
                    // charEmotion(true);
                }

                if (oGate.getY() > screen_y) {
                    main.removeView(oGate);
                    oGate.setBackground(null);
                    // oGate = null;
                    gateHandle[0].removeMessages(0);
                }

                xGate.setY(xGate.getY() + 15);
                xGate.setX(xGate.getX() - 0.5f);


                xGate.setSize(xGate.getWIDTH() + 8, xGate.getHEIGHT() + 8);
                xGate.setLocation(370 * screenparam_x, 0 * screenparam_y);


                if (xGate.getY() < 1000 * screenparam_y && xGate.getY() > 400 * screenparam_y && xGate.getX() < character.getX() + character.getWIDTH()-80*screenparam_x && xGate.getX() + xGate.getWIDTH() > character.getX()+80*screenparam_x) {
                    // character.setBackground(R.drawable.char_x_4);
                    /*
                    if(gateS) {

                        gateS=false;
                    }
                    */
                    if (gate_enabler == true) {
                        gate.start();
                        character.bringToFront();
                        q[0]=true;
                        q[2] = false;
                        q[4]=false;
                        q[6] = false;
                        q[8]=false;
                        q[10]=true;
                        q[12]=true;
                        q[14]=true;
                        q[16]=true;
                        xGate.setBackground(R.drawable.break_right);
                        makeBreakGate(false);//오른쪽문이 부서지는 경우

                        gate_enabler = false;
                    } else
                        xGate.bringToFront();
                    //  character.setDirection(3);
                }

                if (xGate.getY() > screen_y) {
                    main.removeView(xGate);
                    xGate.setBackground(null);
                    //   xGate = null;
                    gateHandle[0].removeMessages(0);
                }


                sendEmptyMessageDelayed(0, 30); // (delay, msec)
            }
        };
        gateHandle[0].sendEmptyMessage(0);//handler 실행

        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();
    }


    public void charEmotion(boolean a)
    {


        emoin = 0;
        //  character.setLocation(430 * screenparam_x, 760* screenparam_y);
       // character.setDirection(3);
       // character.setCanDrag(false);
        final GateStuff[] oEmo;
        final GateStuff xEmo = new GateStuff(main_Activity, viewList,main);

        oEmo = new GateStuff[2];
        oEmo[0] = new GateStuff(main_Activity, viewList,main);
        oEmo[1] = new GateStuff(main_Activity, viewList,main);
        oEmo[0].setBackground(R.drawable.oxquizfeedback_o);
        oEmo[1].setBackground(R.drawable.oxquizfeedback_o);
        oEmo[0].setSize(17 * screenparam_x, 90 * screenparam_y);
        oEmo[1].setSize(17 * screenparam_x, 90 * screenparam_y);
        oEmo[0].setLocation(character.getX() + 20 * screenparam_x, character.getY() - 30 * screenparam_y);
        oEmo[1].setLocation(character.getX() + 60 * screenparam_x, character.getY() -  screenparam_y);
        //oEmo[0].setLocation(450 * screenparam_x, 620 * screenparam_y);
        //  oEmo[1].setLocation(490 * screenparam_x, 650 * screenparam_y);

        xEmo.setBackground(R.drawable.oxquizfeedback_x);
        xEmo.setSize(35 * screenparam_x, 180 * screenparam_y);
        xEmo.setLocation(character.getX() + 35 * screenparam_x, character.getY() - 60 * screenparam_y);
        //    xEmo.setLocation(465 * screenparam_x, 520 * screenparam_y);


        if(a)
        {
            xCharacter = new GateCharacter(main_Activity, viewList, main);
            if (GateRunnerActivity.charType == 0) {
                //노랑머리
                  xCharacter.setImges(R.drawable.char1o_walk1o, R.drawable.char1o_walk1o, R.drawable.char1o_left1o, R.drawable.char1o_right1o);
            }
            if (GateRunnerActivity.charType == 1) {
                //검은머리
                xCharacter.setImges(R.drawable.char2o_walk1o, R.drawable.char2o_walk1o, R.drawable.char2o_left1o, R.drawable.char2o_right1o);
            }
            if (GateRunnerActivity.charType == 2) {
                //감자
                xCharacter.setImges(R.drawable.char3o_walk1o, R.drawable.char3o_walk1o, R.drawable.char3o_lefto, R.drawable.char3o_righto);
            }
            xCharacter.setSize(150 * screenparam_x, 600);
            //  xCharacter.setLocation(315 * screenparam_x, 760 * screenparam_y);
            xCharacter.setLocation(character.getX(), character.getY());
            xCharacter.setCanDrag(true);
            main.removeView(character);
            main.addView(xCharacter);
            fRunnable = new Runnable() {
                @Override
                public void run() {
                    main.removeView(o);
                    main.removeView(xCharacter);

                }
            };
            fHandler = new Handler();
            fHandler.postDelayed(fRunnable, 3000);



        }
        else
        {



            xCharacter = new GateCharacter(main_Activity, viewList, main);
            if (GateRunnerActivity.charType == 0) {
                //노랑머리
                xCharacter.setImges(R.drawable.char1x_walk1x, R.drawable.char1x_walk1x, R.drawable.char1x_left1x, R.drawable.char1x_right1x);
            }
            if (GateRunnerActivity.charType == 1) {
                //검은머리
                xCharacter.setImges(R.drawable.char2x_walk1x, R.drawable.char2x_walk1x, R.drawable.char2x_left1x, R.drawable.char2x_right1x);
            }
            if (GateRunnerActivity.charType == 2) {
                //감자
                xCharacter.setImges(R.drawable.char3x_walk1x, R.drawable.char3x_walk1x, R.drawable.char3x_leftx, R.drawable.char3x_rightx);
            }
            xCharacter.setSize(150 * screenparam_x, 600);
          //  xCharacter.setLocation(315 * screenparam_x, 760 * screenparam_y);
            xCharacter.setLocation(character.getX(), character.getY());
            xCharacter.setCanDrag(true);
            main.removeView(character);
            main.addView(xCharacter);
            fRunnable = new Runnable() {
                @Override
                public void run() {
                    main.removeView(x);
                    main.removeView(xCharacter);

                }
            };
            fHandler = new Handler();
            fHandler.postDelayed(fRunnable, 3000);




        }

        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();

    }

    public void makeBreakGate(boolean direction)
    {


        breaks = new GateStuff[6];

        for(int i=0;i<6;i++)
        {
            breaks[i] = new GateStuff(main_Activity, viewList,main);
            breaks[i].setBackground(R.drawable.piece1 + i);
            breaks[i].setSize(60 * screenparam_x, 100 * screenparam_y);
            if(direction)
            {
                switch(i)
                {
                    case 0:  breaks[i].setLocation(oGate.getX() + oGate.getWIDTH() / 5, oGate.getY() +oGate.getHEIGHT()/3);
                        break;
                    case 1: breaks[i].setLocation(oGate.getX() + oGate.getWIDTH() / 5, oGate.getY() +oGate.getHEIGHT()/2);
                        break;
                    case 2:  breaks[i].setLocation(oGate.getX() + oGate.getWIDTH() / 5, oGate.getY() +oGate.getHEIGHT()/1.5f);
                        break;
                    case 3:  breaks[i].setLocation(oGate.getX() +2*(oGate.getWIDTH() / 4), oGate.getY() +oGate.getHEIGHT()/3);
                        break;
                    case 4: breaks[i].setLocation(oGate.getX() +2*(oGate.getWIDTH() / 4), oGate.getY() +oGate.getHEIGHT()/2);
                        break;
                    case 5: breaks[i].setLocation(oGate.getX() +2*(oGate.getWIDTH() / 4), oGate.getY() +oGate.getHEIGHT()/1.5f);
                        break;
                }


            }
            if(!direction)
            {
                switch(i)
                {
                    case 0:  breaks[i].setLocation(xGate.getX() + xGate.getWIDTH() / 5, xGate.getY() +xGate.getHEIGHT()/3);
                        break;
                    case 1: breaks[i].setLocation(xGate.getX() + xGate.getWIDTH() / 5, xGate.getY() +xGate.getHEIGHT()/2);
                        break;
                    case 2:  breaks[i].setLocation(xGate.getX() + xGate.getWIDTH() / 5, xGate.getY() +xGate.getHEIGHT()/1.5f);
                        break;
                    case 3:  breaks[i].setLocation(xGate.getX() +2*(xGate.getWIDTH() / 3), xGate.getY() +xGate.getHEIGHT()/3);
                        break;
                    case 4: breaks[i].setLocation(xGate.getX() +2*(xGate.getWIDTH() / 3), xGate.getY() +xGate.getHEIGHT()/2);
                        break;
                    case 5: breaks[i].setLocation(xGate.getX() +2*(xGate.getWIDTH() / 3), xGate.getY() +xGate.getHEIGHT()/1.6f);
                        break;
                }
            }
            main.addView(breaks[i]);
            moveBreakGate(i);
        }

    }

    public void moveBreakGate(final int i)
    {
        breakHandle[i] = new Handler() { //hanlder 오브젝트를 생성해서 overriding
            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
            {


                switch(i)
                {
                    case 0: breaks[i].setX(breaks[i].getX()-10);
                        breaks[i].setY(breaks[i].getY()-10);
                        break;
                    case 1: breaks[i].setX(breaks[i].getX()-10);
                        break;
                    case 2: breaks[i].setX(breaks[i].getX()-10);
                        breaks[i].setY(breaks[i].getY()+7);
                        break;
                    case 3: breaks[i].setX(breaks[i].getX()+10);
                        breaks[i].setY(breaks[i].getY()-10);
                        break;
                    case 4: breaks[i].setX(breaks[i].getX()+10);
                        break;
                    case 5: breaks[i].setX(breaks[i].getX()+10);
                        breaks[i].setY(breaks[i].getY()+10);
                        break;
                }


                if(oGate.getY() > character.getY()-200*screenparam_y || xGate.getY() > character.getY()-200*screenparam_y) {
                    main.removeView(breaks[i]);
                    breakHandle[i].removeMessages(0);
                    System.out.println("아웃");
                }
                sendEmptyMessageDelayed(0, 10); //무한 실행. (delay, msec)
            }

        };

        breakHandle[i].sendEmptyMessage(0);//handler 실행

        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();
    }


    public void changeStage()
    {


            TranslateAnimation[] ani;
            ani = new TranslateAnimation[2];


            for (int i = 0; i < 2; i++) {

                switch (i) {
                    case 0:

                        ani[i] = new TranslateAnimation((float)(-800* screenparam_x), screen_x,0, 0);
                         //ani[i].setStartOffset(200);
                        light1.startAnimation(ani[i]);
                        ani[i].setDuration(2000);
                        ani[i].setFillAfter(true);

                        break;

                    case 1:
                        ani[i] = new TranslateAnimation(screen_x, (float)(-800* screenparam_x), 0, 0);
                        // ani[i].setStartOffset(18000);
                        light2.startAnimation(ani[i]);
                        ani[i].setDuration(2000);
                        ani[i].setFillAfter(true);
                        break;

                }


            }




    }

    public void emotion(int emo)
    {
        character.setBackground( getResources().getDrawable(emo));
    }

    public void insertViews(View viewList)
    {
        Views.add(viewList);
    }




    public void finish() {

        GateMySQLiteOpenHelper helper;
        String dbName = "st_file.db";
        int dbVersion = 1; // 데이터베이스 버전
        int score = 0;
        int tempc = 0;
        SQLiteDatabase db;
        String tag = "SQLite"; // Log 에 사용할 tag
        String sql, sql2,sql3,sql4,test;
        String name;

        helper = new GateMySQLiteOpenHelper(
                main_Activity,  // 현재 화면의 제어권자
                dbName,// db 이름
                null,  // 커서팩토리-null : 표준커서가 사용됨
                dbVersion);       // 버전

        db = helper.getWritableDatabase(); // 읽고 쓸수 있는 DB

        db.execSQL("create table if not exists mytable(score integer,name text,PRIMARY KEY(name));");
        db.execSQL("create table if not exists cointable(coin integer,name text,PRIMARY KEY(name));");
        db.execSQL("create table if not exists gatemission(count integer,name text,PRIMARY KEY(name));");

        try
        {
            db.execSQL("insert into gatemission (count,name) values(" + 0 + ",'player');");
        }

        catch (SQLException e)
        {
            Log.d("message", e.toString());
        }


        try {
            db.execSQL("update gatemission set score=score+1 where name='player';");
        }

        catch (SQLException e)
        {
            Log.d("message", e.toString());
        }
        try {
            db.execSQL("update gatemission set count=count+1 where name='player';");
        }

        catch (SQLException e)
        {
            Log.d("message", e.toString());
        }


        /*
        Cursor f = db.rawQuery("select count from gatemission where name = 'player';", null);
        while (f.moveToNext()) {
            tempc = f.getInt(0);
        }

*/


        try
        {
            db.execSQL("insert into mytable (score,name) values(" + 0 + ",'player');");
        }
        catch (SQLException e)
        {
            Log.d("message", e.toString());
        }

     sql2 = "select score from mytable where name='player';";

    Cursor c = db.rawQuery(sql2, null);
    while (c.moveToNext()) {
        score = c.getInt(0);

    }

        System.out.println(score);
        if (curPoint > score) {
            System.out.println("옴");
            String to = Integer.toString(curPoint);
            bestPoint.setText("최고기록 " + to);
            // sql = "insert into mytable (score,name) values(" + curPoint + ",'player');";
            sql = "update mytable set score=" + curPoint + " where name='player';";

            try {
                db.execSQL(sql);
            }
            catch (SQLException e)
            {
                Log.d("message", e.toString());
            }
/*
            Cursor c1 = db.rawQuery(sql2, null);
            while (c.moveToNext()) {
                score = c1.getInt(0);
            }
            */
        } else {
            String to = Integer.toString(score);
            bestPoint.setText("최고기록 " + score);
        }

        if (!lifeCheck0) {
            // master=false;
            pointHandle.removeMessages(0);
            main.removeView(point);
            main.removeView(coin);
            main.removeView(character);
        }


        ///////////////애니메이션 추가

        main.addView(black);
        main.addView(end);
        main.addView(endCoin);
        main.addView(endPoint);
        main.addView(bestPoint);
        main.addView(endCpic);
        main.addView(toHome);
        main.addView(again);
        main.addView(star1);
        ScaleAnimation[] ani;
        ani = new ScaleAnimation[8];


        String to = Integer.toString(curPoint);
        endPoint.setText(to);


        if (curPoint >= 0 && curPoint < 10)
            endPoint.setLocation(screenparam_x * 350, screenparam_y * 380);
        if (curPoint >= 10 && curPoint < 100)
            endPoint.setLocation(screenparam_x * 337, screenparam_y * 380);
        if (curPoint >= 100 && curPoint < 1000)
            endPoint.setLocation(screenparam_x * 329, screenparam_y * 380);
        if (curPoint >= 1000 && curPoint < 10000)
            endPoint.setLocation(screenparam_x * 320, screenparam_y * 380);
        if (curPoint >= 10000 && curPoint < 100000)
            endPoint.setLocation(screenparam_x * 300, screenparam_y * 380);

        String to1 = Integer.toString(curCoin);
        endCoin.setText(to1);


        try
        {
            db.execSQL("insert into cointable (coin,name) values(" + 0 + ",'player');");
        }
        catch (SQLException e)
        {
            Log.d("message", e.toString());
        }

        sql3 = "select coin from cointable where name = 'player';";

        Cursor d = db.rawQuery(sql3, null);
        while (d.moveToNext()) {
            tempc = d.getInt(0);
        }
        tempc = curCoin + tempc;
        sql4 = "update cointable set coin=" + tempc + " where name = 'player';";
        try {
            db.execSQL(sql4);
        }
        catch (SQLException e)
        {
            Log.d("message", e.toString());
        }


       int t=0;
       test = "select coin from cointable where name = 'player';";
        try {
            Cursor a = db.rawQuery(test, null);
            while (a.moveToNext()) {
                t = a.getInt(0);
            }
        }
        catch (SQLException e)
        {
            Log.d("message", e.toString());
        }

        System.out.println("코인: "+t);



        emotionHandle[2] = new Handler() { //hanlder 오브젝트를 생성해서 overriding

            public boolean F = true;
            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
            {
               switch(f)
               {
                   case 0:
                       System.out.println("별0");
                       f++;
                       sendEmptyMessageDelayed(0, 2000);
                       break;
                   case 1:System.out.println("별1");
                       star1.setBackground(R.drawable.lighteffect1);
                       f++;
                       sendEmptyMessageDelayed(0, 1);
                       break;
                   case 2:System.out.println("별2");
                       star1.setBackground(R.drawable.lighteffect2);
                       f++;
                       sendEmptyMessageDelayed(0, 1);
                       break;
                   case 3:System.out.println("별3");
                       star1.setBackground(R.drawable.lighteffect3);
                       f++;
                       sendEmptyMessageDelayed(0, 1);
                       break;
                   case 4:System.out.println("별4");
                       star1.setBackground(R.drawable.lighteffect4);
                       f=0;
                       sendEmptyMessageDelayed(0, 1);
                       break;
               }

              //  sendEmptyMessageDelayed(0, 500); //무한 실행. (delay, msec)

            }

        };
        emotionHandle[2].sendEmptyMessage(0);


        /*
        for (int i = 0; i < 8; i++) {

            switch (i) {
                case 0:

                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    // ani[i].setStartOffset(18000);
                    black.startAnimation(ani[i]);

                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);

                    break;
                case 1:

                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    // ani[i].setStartOffset(18000);
                    end.startAnimation(ani[i]);

                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);

                    break;

                case 2:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
                    //  ani[i].setStartOffset(18000);
                    endCoin.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);
                    break;

                case 3:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1.3f, Animation.RELATIVE_TO_SELF, -2f);
                    // ani[i].setStartOffset(18000);
                    endPoint.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);
                    break;
                case 4:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, -4, Animation.RELATIVE_TO_SELF, 0.5f);
                    //  ani[i].setStartOffset(18000);
                    bestPoint.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);
                    break;
                case 5:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, -4, Animation.RELATIVE_TO_SELF, 0.5f);
                    //  ani[i].setStartOffset(18000);
                    endCpic.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);
                    break;
                case 6:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, -4, Animation.RELATIVE_TO_SELF, 0.5f);
                    //  ani[i].setStartOffset(18000);
                    toHome.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);
                    break;
                case 7:
                    ani[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, -4, Animation.RELATIVE_TO_SELF, 0.5f);
                    //  ani[i].setStartOffset(18000);
                    again.startAnimation(ani[i]);
                    ani[i].setDuration(1000);
                    ani[i].setFillAfter(true);
                    break;
            }


        }
        */
    }







}
