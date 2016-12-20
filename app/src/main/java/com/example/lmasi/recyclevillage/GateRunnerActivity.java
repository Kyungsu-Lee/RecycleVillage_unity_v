package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Vector;

import static android.graphics.Color.argb;

public class GateRunnerActivity extends Activity {

    double DEFAULTSIZE_Y = 1280.0; //1280
    double DEFAULTSIZE_X = 720.0; //720

    boolean a,b;

    public static GateGameView black1;
    public static GateButtonView resume,replay,over;

    static ListRelativeLayout  main;
    static List<View> viewList;

    public static boolean end=false;

    public static int charType=0;
    public static boolean bm;
    public static boolean check;

    GateItems item;
    MediaPlayer background;
    static boolean canNotice = true;
    GateMgr mgr;

    static int v=10;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_gate_runner);
// Get the AudioManager
        AudioManager audioManager =(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
// Set the volume of played media to your choice.
        audioManager.setStreamVolume (AudioManager.STREAM_MUSIC,v,0);

        if(!bm) {
            background= MediaPlayer.create(this,R.raw.gaterunner_sampling_bgm);
            background.start();
            background.setLooping(true);
            bm=true;
        }

        main = (ListRelativeLayout) findViewById(R.id.main);

        viewList = new Vector<View>();


        main.setViewList(viewList);

        GateGameView bg = new GateGameView(this, viewList);
        bg.screenparam_x = screenparam_x();
        bg.screenparam_y = screenparam_y();
        bg.screen_x = ScreenSize().x;
        bg.screen_y = ScreenSize().y;
        bg.narrawSize = narrowSize();
        bg.wideSize = wideSize();

        black1 = new GateGameView(this, viewList);
        black1.setSize(ScreenSize().x, ScreenSize().y);
        black1.setLocation(0 * screenparam_x(), 0 * screenparam_y());
        black1.setBackgroundColor(argb(120, 35, 31, 32));
        black1.setCanDrag(false);
        black1.setCanMove(false);

        resume = new GateButtonView(this, viewList, R.drawable.pause1);
        resume.setSize(180 * screenparam_x(), 150 * screenparam_y());
        resume.setLocation(270* screenparam_x(), 340 * screenparam_y());

        replay = new GateButtonView(this, viewList, R.drawable.pause2);
        replay.setSize(180 * screenparam_x(), 150 * screenparam_y());
        replay.setLocation(270* screenparam_x(), 540 * screenparam_y());

        over = new GateButtonView(this, viewList, R.drawable.pause3);
        over.setSize(180 * screenparam_x(), 150 * screenparam_y());
        over.setLocation(270* screenparam_x(), 740 * screenparam_y());

     //   audioManager.setStreamVolume (AudioManager.STREAM_MUSIC,0,0);


        Cursor c = DbResource.db.rawQuery("select c_index from character", null);
        c.moveToNext();
        charType = c.getInt(0);

        if(charType == 3)
            charType = 1;
        else if(charType == 5)
            charType = 2;
        else
            charType = 0;

        mgr = new GateMgr(this,viewList,main,0);
       // GateTime mgr = new GateTime(this,viewList,main);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        GateMySQLiteOpenHelper helper;
        String dbName = "st_file.db";
        int dbVersion = 1; // 데이터베이스 버전
       final SQLiteDatabase db;
        String tag = "SQLite"; // Log 에 사용할 tag

        helper = new GateMySQLiteOpenHelper(
                this,  // 현재 화면의 제어권자
                dbName,// db 이름
                null,  // 커서팩토리-null : 표준커서가 사용됨
                dbVersion);

        db = helper.getWritableDatabase(); // 읽고 쓸수 있는 DB

          // 버전

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            v++;
            AudioManager audioManager =(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume (AudioManager.STREAM_MUSIC,v,0);
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            v--;
            AudioManager audioManager =(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume (AudioManager.STREAM_MUSIC,v,0);
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(check)
            {


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
                finish();
            }
            check = true;

            main.addView(black1);
            main.addView(resume);
            main.addView(replay);
            main.addView(over);
            black1.bringToFront();
            resume.bringToFront();
            replay.bringToFront();
            over.bringToFront();

            resume.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        check = false;
                        main.removeView(black1);
                        main.removeView(resume);
                        main.removeView(replay);
                        main.removeView(over);

                    }


                    return true;
                }

            });

            replay.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        Intent i = (getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName())) ;
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        finish();
                        startActivity(i);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }


                    return true;
                }

            });

            over.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {



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
                        finish();

                    }


                    return true;
                }

            });

        }
        return true;
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

    public void ending()
    {
        System.out.println("끝");
        finish();
    }
}
