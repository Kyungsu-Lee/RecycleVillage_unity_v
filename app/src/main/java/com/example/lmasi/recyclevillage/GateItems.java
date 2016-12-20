package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Administrator on 2016-07-13.
 */
public class GateItems extends GateGameView {

    public boolean plus;
    public boolean d;
    public boolean life;
    public boolean attack;
    public boolean gen;

    private List<View> Views;
    private List<View> viewList;
    static  public List<Drawable> imgs;

    Context main_Activity;
    RelativeLayout main;

    Handler[] itemHandle;

    Random random = new Random();
    Random iRandom = new Random();

    GateMgr a;

    private int item_no = 7;
    private int ran;
    private int index;
    private int dr;

    GateObject[] items;
    GateStuff[] lives;
    GateOnTextView coin;

    GateCharacter character;

    Runnable fRunnable;
    Handler fHandler;

    MediaPlayer itemG,itemB;


    public GateItems(Context context, List<View> viewList, RelativeLayout r,GateCharacter character, GateStuff[] life, GateOnTextView coin,GateObject[] items,GateMgr mgr)
    {

        super(context, viewList);
        itemG=MediaPlayer.create(context,R.raw.effectsound_good);
        itemB=MediaPlayer.create(context,R.raw.effectsound_bad);


        this.character = character;
        main_Activity = context;
        this.main = r;
        this.a=mgr;

        Views = new Vector<View>();

        imgs = new Vector<Drawable>();
        super.setCanMove(true);

        itemHandle= new Handler[3*item_no];

        lives = new GateStuff[3];
        this.items = new GateObject[3*item_no];

        for(int i=0;i<3;i++)
        {
            lives[i] = life[i];
        }

        for(int i=0; i<3*item_no;i++) {
            this.items[i] =items[i];
        }

        this.coin = coin;



        makeItems();

    }





    private void makeItems()
    {

        index = 0;

        int j=0;
        int a=0;
        // int ran = 0;


        for(int i=0; i<3*item_no;i++)
        {


            if(i % item_no ==0)
                j=0;

            dr = R.drawable.item_1 + j;

            if(dr == R.drawable.itemminus_1 || dr == R.drawable.itemminus_2 || dr == R.drawable.itemminus_3)
            {
                items[i].isPlus(false);
            }
            else
            {
                items[i].isPlus(true);
            }
            items[i].setAffect(true);
            items[i].setGen(true);
            items[i].setBackground(dr);
            items[i].setSize(1 * screenparam_x, 1 * screenparam_y);



            main.addView(items[i]);

            ran = iRandom.nextInt(3*item_no);

            this.moveItems(ran,i);
          //  items[ran].moveItems(ran,i);

            //this.moveItems(i);

            j++;



        }
/*
        for(int i=0; i<4*item_no;i++)
        {
            ran = iRandom.nextInt(4*item_no);
            this.moveItems(ran,i);
        }
*/

        fRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3 * item_no; i++) {
                    if(items[i].getAffect())
                    {
                        main.removeView(items[i]);
                        items[i].setAffect(false);
                    }

                    items[i].setBackground(null);
                }
               fHandler.removeMessages(0);
            }
        };
        fHandler = new Handler();
        fHandler.postDelayed(fRunnable, 18000);//17


        character.bringToFront();


    }

    public void moveItems(final int i,final int index) {

        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();

        final int ran;
        ran = random.nextInt(9);



        if(GateMgr.gameCount>=0&&GateMgr.gameCount<6) {
            itemHandle[index] = new Handler() { //hanlder 오브젝트를 생성해서 overriding


                public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
                {

                    items[i].setY(items[i].getY() + (float) (0.1 * screenparam_y));// 12가 좋을듯?

                    if ((items[i].getPlus() || i % 7 == 6)) {
                            items[i].setSize(items[i].getWIDTH() + 2.5, items[i].getHEIGHT() + 2.5);
                    }
                    else if (i % 7 != 6) {
                             items[i].setSize(items[i].getWIDTH() + 4.5, items[i].getHEIGHT() + 2.3);
                    }

                    if (items[i].getGen()) {
                        items[i].setLocation(280 * screenparam_x + (20 * ran) * screenparam_x, 380 * screenparam_y);
                        items[i].setGen(false);
                    } else {
                        items[i].setLocation(items[i].getX(), items[i].getY());
                    }

                    if (items[i].getX() < screen_x / 2) {

                        // System.out.println(i + "L");
                        items[i].setX(items[i].getX() - (float) (0.03* screenparam_x));
                    } else if (items[i].getX() > screen_x / 2) {

                        //  System.out.println(i + "R");
                        items[i].setX(items[i].getX() + (float) (0.03 * screenparam_x));
                    }

                    if (items[i].getY() < 1000 * screenparam_y && items[i].getY() > 700 * screenparam_y
                            && items[i].getX() < character.getX() + character.getWIDTH()-80*screenparam_x && items[i].getX() + items[i].getWIDTH() > character.getX()+80*screenparam_x) {
                        if (items[i].getPlus()) {

                            if(!GateMgr.isFever) {
                                switch (GateRunnerActivity.charType) {

                                    case 0:     //노랑머리
                                        character.setBackground(R.drawable.char1_item);
                                        break;
                                    case 1:        //검은머리
                                        character.setBackground(R.drawable.char2_item);
                                        break;
                                    case 2:        //감자
                                        character.setBackground(R.drawable.char3_item);
                                        break;
                                }
                            }

                            if (items[i].getAffect()) {
                                itemG.start();
                                System.out.println("돈: " + GateMgr.curCoin);
                                if (i % 7 == 0 || i % 7 == 1)
                                    GateMgr.curCoin = GateMgr.curCoin + 10;
                                else if (i % 7 == 2 || i % 7 == 3)
                                    GateMgr.curCoin = GateMgr.curCoin + 50;

                                if (GateMgr.curCoin >= 10 && GateMgr.curCoin < 100)
                                    coin.setLocation(screenparam_x * 675, screenparam_y * 56);
                                if (GateMgr.curCoin >= 100 && GateMgr.curCoin < 1000)
                                    coin.setLocation(screenparam_x * 665, screenparam_y * 56);
                                if (GateMgr.curCoin >= 1000 && GateMgr.curCoin < 10000)
                                    coin.setLocation(screenparam_x * 655, screenparam_y * 56);


                                String to = Integer.toString(GateMgr.curCoin);
                                coin.setText(to);
                               // main.removeView(items[i]);
                                items[i].setBackground(null);
                                items[i].setAffect(false);
                            }
                        } else {
                            if (!GateMgr.isFever && items[i].getAffect()) {///////////////////////////////////////////////
                                itemB.start();
                                switch (GateRunnerActivity.charType){

                                    case 0:     //노랑머리
                                        character.setBackground(R.drawable.char1_ob);
                                        break;
                                    case 1:        //검은머리
                                        character.setBackground(R.drawable.char2_ob);
                                        break;
                                    case 2:        //감자
                                        character.setBackground(R.drawable.char3_ob);
                                        break;
                                }

                                if (!lives[2].getLife() && !lives[1].getLife()) {
                                    main.removeView(lives[0]);
                                    GateMgr.lifeCheck0 = false;
                                    lives[0].setLife(GateMgr.lifeCheck0);

                                      GateMgr.master=false;
                                      a.finish(); //게임 종료
                                }
                                if (!lives[2].getLife() && lives[1].getLife()) {
                                    main.removeView(lives[1]);
                                    GateMgr.lifeCheck1 = false;
                                    lives[1].setLife(GateMgr.lifeCheck1);
                                }
                                if (lives[2].getLife()) {
                                    main.removeView(lives[2]);
                                    GateMgr.lifeCheck2 = false;
                                    lives[2].setLife(GateMgr.lifeCheck2);
                                }

                                items[i].setAffect(false);
                            }
                        }
                    }


                    if (items[i].getY() > screen_y|| !GateMgr.master) {
                        //item에도 nullify해줘라
                        if(getAffect()) {
                            main.removeView(items[i]);
                           items[i].setBackground(null);
                            items[i].setAffect(false);
                            // items[i]=null;
                        }
                        main.removeView(items[i]);
                        itemHandle[index].removeMessages(0);

                    }


                    sendEmptyMessageDelayed(0, 10); // (delay, msec) 10
                }
            };

            itemHandle[index].sendEmptyMessageDelayed(0, index * 350);//handler 실행
            }



        if(GateMgr.gameCount>=6&&GateMgr.gameCount<12) {

            itemHandle[index] = new Handler() { //hanlder 오브젝트를 생성해서 overriding


                public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
                {

                    items[i].setY(items[i].getY() + (float) (0.5 * screenparam_y));// 12가 좋을듯?

                    if ((items[i].getPlus() || i % 7 == 6))
                        items[i].setSize(items[i].getWIDTH() + 4, items[i].getHEIGHT() + 4);
                    else if (i % 7 != 6&&items[i].getX() < screen_y)
                        items[i].setSize(items[i].getWIDTH() + 8, items[i].getHEIGHT() + 4);

                    if (items[i].getGen()) {
                        items[i].setLocation(250 * screenparam_x + (20 * ran) * screenparam_x, 380 * screenparam_y);
                        items[i].setGen(false);
                    } else {
                        items[i].setLocation(items[i].getX(), items[i].getY());
                    }

                    if (items[i].getX() < screen_x / 2) {
                        // System.out.println(i + "L");
                        items[i].setX(items[i].getX() - (float) (0.15 * screenparam_x));//0.15
                    } else if (items[i].getX() > screen_x / 2) {
                        //  System.out.println(i + "R");
                        items[i].setX(items[i].getX() + (float) (0.15 * screenparam_x));
                    }

                    if (items[i].getY() < 1000 * screenparam_y && items[i].getY() > 700 * screenparam_y
                            && items[i].getX() < character.getX() + character.getWIDTH()-80*screenparam_x && items[i].getX() + items[i].getWIDTH() > character.getX()+80*screenparam_x) {
                        if (items[i].getPlus()) {
                            switch (GateRunnerActivity.charType){

                                case 0:     //노랑머리
                                    character.setBackground(R.drawable.char1_item);
                                    break;
                                case 1:        //검은머리
                                    character.setBackground(R.drawable.char2_item);
                                    break;
                                case 2:        //감자
                                    character.setBackground(R.drawable.char3_item);
                                    break;
                            }
                            if (items[i].getAffect()) {
                                System.out.println("돈: " + GateMgr.curCoin);
                                if (i % 7 == 0 || i % 7 == 1)
                                    GateMgr.curCoin = GateMgr.curCoin + 10;
                                else if (i % 7 == 2 || i % 7 == 3)
                                    GateMgr.curCoin = GateMgr.curCoin + 50;

                                if (GateMgr.curCoin >= 10 && GateMgr.curCoin < 100)
                                    coin.setLocation(screenparam_x * 675, screenparam_y * 56);
                                if (GateMgr.curCoin >= 100 && GateMgr.curCoin < 1000)
                                    coin.setLocation(screenparam_x * 665, screenparam_y * 56);
                                if (GateMgr.curCoin >= 1000 && GateMgr.curCoin < 10000)
                                    coin.setLocation(screenparam_x * 655, screenparam_y * 56);


                                String to = Integer.toString(GateMgr.curCoin);
                                coin.setText(to);
                                items[i].setBackground(null);
                                items[i].setAffect(false);
                            }
                        } else {
                            if (!GateMgr.isFever && items[i].getAffect()) {///////////////////////////////////////////////
                                switch (GateRunnerActivity.charType){

                                    case 0:     //노랑머리
                                        character.setBackground(R.drawable.char1_ob);
                                        break;
                                    case 1:        //검은머리
                                        character.setBackground(R.drawable.char2_ob);
                                        break;
                                    case 2:        //감자
                                        character.setBackground(R.drawable.char3_ob);
                                        break;
                                }
                                if (!lives[2].getLife() && !lives[1].getLife()) {
                                    main.removeView(lives[0]);
                                    GateMgr.lifeCheck0 = false;
                                    lives[0].setLife(GateMgr.lifeCheck0);

                                    //  finish(); //게임 종료
                                }
                                if (!lives[2].getLife() && lives[1].getLife()) {
                                    main.removeView(lives[1]);
                                    GateMgr.lifeCheck1 = false;
                                    lives[1].setLife(GateMgr.lifeCheck1);
                                }
                                if (lives[2].getLife()) {
                                    main.removeView(lives[2]);
                                    GateMgr.lifeCheck2 = false;
                                    lives[2].setLife(GateMgr.lifeCheck2);
                                }

                                items[i].setAffect(false);
                            }
                        }
                    }


                    if (items[i].getY() > screen_y || !GateMgr.master) {
                        //item에도 nullify해줘라
                        if(getAffect()) {
                            main.removeView(items[i]);
                            items[i].setBackground(null);
                            items[i].setAffect(false);
                            // items[i]=null;
                        }
                        itemHandle[index].removeMessages(0);

                    }


                    sendEmptyMessageDelayed(0, 10); // (delay, msec) 10
                }
            };

            itemHandle[index].sendEmptyMessageDelayed(0, index * 350);//handler 실행
        }

        if (GateMgr.gameCount >= 12 && GateMgr.gameCount < 18) {

            itemHandle[index] = new Handler() { //hanlder 오브젝트를 생성해서 overriding


                public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
                {

                    items[i].setY(items[i].getY() + (float) (1 * screenparam_y));// 12가 좋을듯?

                    if (items[i].getPlus() || i % 7 == 6)
                        items[i].setSize(items[i].getWIDTH() + 6, items[i].getHEIGHT() + 6);
                    else if (i % 7 != 6)
                        items[i].setSize(items[i].getWIDTH() + 12, items[i].getHEIGHT() + 6);

                    if (items[i].getGen()) {
                        items[i].setLocation(250 * screenparam_x + (20 * ran) * screenparam_x, 380 * screenparam_y);
                        items[i].setGen(false);
                    } else {
                        items[i].setLocation(items[i].getX(), items[i].getY());
                    }

                    if (items[i].getX() < screen_x / 2) {
                        // System.out.println(i + "L");
                        items[i].setX(items[i].getX() - (float) (0.15 * screenparam_x));
                    } else if (items[i].getX() > screen_x / 2) {
                        //  System.out.println(i + "R");
                        items[i].setX(items[i].getX() + (float) (0.15 * screenparam_x));
                    }

                    if (items[i].getY() < 1000 * screenparam_y && items[i].getY() > 700 * screenparam_y
                            && items[i].getX() < character.getX() + character.getWIDTH()-80*screenparam_x && items[i].getX() + items[i].getWIDTH() > character.getX()+80*screenparam_x) {
                        if (items[i].getPlus()) {
                            switch (GateRunnerActivity.charType){

                                case 0:     //노랑머리
                                    character.setBackground(R.drawable.char1_item);
                                    break;
                                case 1:        //검은머리
                                    character.setBackground(R.drawable.char2_item);
                                    break;
                                case 2:        //감자
                                    character.setBackground(R.drawable.char3_item);
                                    break;
                            }
                            if (items[i].getAffect()) {
                                System.out.println("돈: " + GateMgr.curCoin);
                                if (i % 7 == 0 || i % 7 == 1)
                                    GateMgr.curCoin = GateMgr.curCoin + 10;
                                else if (i % 7 == 2 || i % 7 == 3)
                                    GateMgr.curCoin = GateMgr.curCoin + 50;

                                if (GateMgr.curCoin >= 10 && GateMgr.curCoin < 100)
                                    coin.setLocation(screenparam_x * 675, screenparam_y * 56);
                                if (GateMgr.curCoin >= 100 && GateMgr.curCoin < 1000)
                                    coin.setLocation(screenparam_x * 665, screenparam_y * 56);
                                if (GateMgr.curCoin >= 1000 && GateMgr.curCoin < 10000)
                                    coin.setLocation(screenparam_x * 655, screenparam_y * 56);


                                String to = Integer.toString(GateMgr.curCoin);
                                coin.setText(to);
                                items[i].setBackground(null);
                                items[i].setAffect(false);
                            }
                        } else {
                            if (!GateMgr.isFever && items[i].getAffect()) {///////////////////////////////////////////////
                                switch (GateRunnerActivity.charType){

                                    case 0:     //노랑머리
                                        character.setBackground(R.drawable.char1_ob);
                                        break;
                                    case 1:        //검은머리
                                        character.setBackground(R.drawable.char2_ob);
                                        break;
                                    case 2:        //감자
                                        character.setBackground(R.drawable.char3_ob);
                                        break;
                                }
                                if (!lives[2].getLife() && !lives[1].getLife()) {
                                    main.removeView(lives[0]);
                                    GateMgr.lifeCheck0 = false;
                                    lives[0].setLife(GateMgr.lifeCheck0);

                                    //  finish(); //게임 종료
                                }
                                if (!lives[2].getLife() && lives[1].getLife()) {
                                    main.removeView(lives[1]);
                                    GateMgr.lifeCheck1 = false;
                                    lives[1].setLife(GateMgr.lifeCheck1);
                                }
                                if (lives[2].getLife()) {
                                    main.removeView(lives[2]);
                                    GateMgr.lifeCheck2 = false;
                                    lives[2].setLife(GateMgr.lifeCheck2);
                                }

                                items[i].setAffect(false);
                            }
                        }
                    }


                    if (items[i].getY() > screen_y || !GateMgr.master) {
                        //item에도 nullify해줘라
                        if (getAffect()) {
                            main.removeView(items[i]);
                            items[i].setBackground(null);
                            items[i].setAffect(false);
                            // items[i]=null;
                        }
                        itemHandle[index].removeMessages(0);

                    }


                    sendEmptyMessageDelayed(0, 10); // (delay, msec) 10
                }
            };

            itemHandle[index].sendEmptyMessageDelayed(0, index * 350);//handler 실행
        }
    }


    public void setItems(int item)
    {
        super.setBackground( getResources().getDrawable(item));
    }

    public boolean isPlus(boolean b)
    {
        this.plus = b;
        return plus;
    }

    public boolean getPlus()
    {
        return this.plus;
    }


    public void setD(boolean a)
    {
        this.d = a;
    }
    public boolean getD()
    {
        return this.d;
    }

    public void setLife(boolean a){this.life = a; }
    public boolean getLife(){return this.life;}

    public void setAffect(boolean a){this.attack = a;}
    public boolean getAffect(){return this.attack;}

    public void setGen(boolean a){this.gen=a;}
    public boolean getGen(){return this.gen;}




}
