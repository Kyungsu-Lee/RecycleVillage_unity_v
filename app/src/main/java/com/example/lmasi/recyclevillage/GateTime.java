package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2016-07-13.
 */
public class GateTime extends GateGameView {

    GateRunnerActivity ma;

    static int ch=0;
    boolean qc=true;

    public boolean plus;
    public boolean d;
    public boolean attack;
    public boolean gen;
    private int item_no = 7;

    private List<View> Views;

    static  public List<Drawable> imgs;

    Context main_Activity;
    RelativeLayout main;

    GateMgr mgr;

    GateObject[] items;
    GateStuff[] lives;
    GateOnTextView coin;
    GateCharacter character;

    GateStuff[] lbuildings;
    GateStuff[] rbuildings;
    private int lbuild_no=9;
    private int rbuild_no=9;

    Handler fHandler,ffHandler,sC2Handler,sC3Handler,oneHandler;
    Runnable fRunnable,ffRunnable,sC2Runnable,sC3Runnable,oneRunnable;
    Handler bHandler, b1Handler, mgrHandler, oHandler, cHandler, iHandler, gateHandler, cGateHandler, answerHandler, tHandler, pHandler;
    Runnable bRunnable, b1Runnable, mgrRunnable, oRunnable, cRunnable, iRunnable, gateRunnable, cGateRunnable, answerRunnable, tRunnable, pRunnable;



    public GateTime(Context context, List<View> viewList, RelativeLayout r, GateCharacter character,GateStuff[] life, GateOnTextView coin)
    {

        super(context, viewList);

        this.character = character;
        main_Activity = context;
        this.main = r;

        Views = new Vector<View>();

        imgs = new Vector<Drawable>();

        lives = new GateStuff[3];

        for(int i=0;i<3;i++)
        {
            lives[i] = life[i];
        }

        this.coin = coin;

        mgr = new GateMgr(main_Activity,Views,main,this.character,this.coin);

        this.timeManager();

    }


    public void timeManager()
    {
       //게임 운영에 있어서 적용 시간을 담당하는 부분이다. o는 open, c는 close를 나타낸다.

        if(GateMgr.gameCount==0)
        {
            mgrRunnable = new Runnable() {
                @Override
                public void run() {

                    mgr.countDown();
                }
            };
            mgrHandler = new Handler();
            mgrHandler.postDelayed(mgrRunnable, 0);
        }

        bRunnable = new Runnable() {
            @Override
            public void run() {
                if(GateMgr.gameCount==0) {
                    mgr.countDownEnd();
                    mgrHandler.removeMessages(0);
                }

                lbuildings=new GateStuff[lbuild_no];
                rbuildings=new GateStuff[rbuild_no];
                for(int i=0; i<lbuild_no;i++) {
                    lbuildings[i] = new GateStuff(main_Activity, Views, main);
                    rbuildings[i] = new GateStuff(main_Activity, Views, main);
                }

                new GateBuilding(main_Activity,Views,main,lbuildings,rbuildings);

            }
        };
        bHandler = new Handler();
       // if(GateMgr.gameCount<2)
       // bHandler.postDelayed(bRunnable,4000);
       // else
            bHandler.postDelayed(bRunnable,2000);





        iRunnable = new Runnable() {
                @Override
                public void run() {
                  //  mgrHandler.removeMessages(0);

                    items = new GateObject[3*item_no];

                    for(int i=0; i<3*item_no;i++) {
                        items[i] = new GateObject(main_Activity, Views, main, character, lives, coin);
                    }
                  new GateItems(main_Activity,Views,main,character,lives,coin,items,mgr);
/*
                    for(int i=0; i<4*item_no;i++) {
                        if (items[i].getY() > screen_y || !GateMgr.master) {
                            //item에도 nullify해줘라
                          //  main.removeView(items[i]);
                          //  items[i].setBackground(null);
                         //   items[i].setAffect(false);
                            System.out.println("왔니");
                           items[i]=null;

                        }
                    }*/
                }
            };
            iHandler = new Handler();
     //   if(GateMgr.gameCount<2)
     //       iHandler.postDelayed(iRunnable, 4000);
       // else
            iHandler.postDelayed(iRunnable, 2000);

            pRunnable = new Runnable() {
                @Override
                public void run() {


                    mgr.increasePoint();

                }
            };
            pHandler = new Handler();
      //  if(GateMgr.gameCount<2)
     //       pHandler.postDelayed(pRunnable, 4000);
     //   else
            pHandler.postDelayed(pRunnable, 2000);

            oRunnable = new Runnable() {
                @Override
                public void run() {
                    if(GateMgr.isFeverBtn)
                       main.removeView(mgr.feverTimeButton);
                    else
                        main.removeView(mgr.feverTimeButtonNa);

                    GateMgr.isFeverBtn=false;
                    GateMgr.isNaFeverBtn=false;
                    for(int i=0; i<3*item_no;i++) {
                            items[i]=null;
                    }
                    for(int i=0; i<lbuild_no;i++) {
                        lbuildings[i]=null;
                        rbuildings[i]=null;
                    }
                    iHandler.removeMessages(0);
                    bHandler.removeMessages(0);
                    pHandler.removeMessages(0);





                    if(GateMgr.master) {
                        mgr.openWindow();
                    }
                }
            };
            oHandler = new Handler();
     //   if(GateMgr.gameCount<2)
     //       oHandler.postDelayed(oRunnable, 15000);//17
     //   else
            oHandler.postDelayed(oRunnable, 13000);//13

            cRunnable = new Runnable() {
                @Override
                public void run() {

                    oHandler.removeMessages(0);
                    if(GateMgr.master) {
                        mgr.closeWindow();
                    }
                }
            };
            cHandler = new Handler();
      //  if(GateMgr.gameCount<2)
      //      cHandler.postDelayed(cRunnable, 23000);//25
      //  else
            cHandler.postDelayed(cRunnable, 18000);//21

            gateRunnable = new Runnable() {
                @Override
                public void run() {
                    main.removeView(mgr.window);
                    main.removeView(mgr.question);
                    main.removeView(mgr.info);


                    mgr.window.setBackground(null);
                    mgr.question.setBackground(null);
                    mgr.info.setBackground(null);
                    mgr.count1.setBackground(null);

                    cHandler.removeMessages(0);

                    if(GateMgr.master) {
                     //   mgr.gate_enabler = true;
                       mgr.moveGates();
                    }

                }
            };
            gateHandler = new Handler();
      //  if(GateMgr.gameCount<2)
      //      gateHandler.postDelayed(gateRunnable, 25000);//26
      //  else
            gateHandler.postDelayed(gateRunnable, 21000);//23

            answerRunnable = new Runnable() {
                @Override
                public void run() {

                    GateRunnerActivity.black1.bringToFront();
                    GateRunnerActivity.resume.bringToFront();
                    GateRunnerActivity.replay.bringToFront();
                    GateRunnerActivity.over.bringToFront();

                    gateHandler.removeMessages(0);
                   // if(GateMgr.master&&(ch%2==0||ch==0)) {
                    if(GateMgr.master) {

                        mgr.gate_enabler = true;
                        System.out.println("ch값: "+ch);
                        System.out.println(GateMgr.gameCount+"의 tf: "+mgr.q[GateMgr.gameCount] );

                        if (mgr.q[GateMgr.gameCount] == true) {
                            ch++;
                            System.out.println("정답에 들어옴");
                            main.addView(mgr.o);
                            mgr.charEmotion(true);

                            mgr.noError++;
                            if(mgr.noError%2==0||(mgr.noError%2==2&&GateMgr.gameCount>=10)) {
                                GateMgr.curPoint = GateMgr.curPoint + 600;
                                System.out.println(GateMgr.curPoint);
                                if (GateMgr.curPoint >= 10 && GateMgr.curPoint < 100)
                                    mgr.point.setLocation(screenparam_x * 680, screenparam_y * 160);
                                if (GateMgr.curPoint >= 100 && GateMgr.curPoint < 1000)
                                    mgr.point.setLocation(screenparam_x * 665, screenparam_y * 160);
                                if (GateMgr.curPoint >= 1000 && GateMgr.curPoint < 10000)
                                   mgr. point.setLocation(screenparam_x * 650, screenparam_y * 160);

                                String to = Integer.toString(GateMgr.curPoint);
                                main.removeView(mgr.point);
                               mgr.point.setText(to);
                                main.addView(mgr.point);
                            }
                        } else if (mgr.q[GateMgr.gameCount] == false) {
                            ch++;
                            System.out.println("오답에 들어옴");
                            main.addView(mgr.x);
                            mgr.charEmotion(false);


                             qc=false;
                             if (!lives[2].getLife() && !lives[1].getLife()) {
                                 main.removeView(lives[0]);
                                 GateMgr.lifeCheck0 = false;
                                 GateMgr.master = false;
                                 lives[0].setLife(false);

                                 ffRunnable = new Runnable() {
                                     @Override
                                     public void run() {
                                         mgr.finish();
                                     }
                                 };
                                 ffHandler = new Handler();
                                 ffHandler.postDelayed(ffRunnable, 4000);

                             }
                             if (!lives[2].getLife() && lives[1].getLife()) {
                                 main.removeView(lives[1]);
                                 GateMgr.lifeCheck1 = false;
                                 lives[1].setLife(false);
                             }
                             if (lives[2].getLife()) {
                                 main.removeView(lives[2]);
                                 GateMgr.lifeCheck2 = false;
                                 lives[2].setLife(false);
                             }


                        }
                    }
                }
            };
            answerHandler = new Handler();
     //   if(GateMgr.gameCount<2)
     //       answerHandler.postDelayed(answerRunnable, 30000);//33
    //    else
            answerHandler.postDelayed(answerRunnable, 22500);//25


            tRunnable = new Runnable() {
                @Override
                public void run() {
                    answerHandler.removeMessages(0);
                    qc=true;

                    if(GateMgr.master) {
                        if (lives[2].getLife()) {
                            main.removeView(lives[2]);
                            main.removeView(lives[1]);
                            main.removeView(lives[0]);
                        }
                        if (!lives[2].getLife() && lives[1].getLife()) {
                            main.removeView(lives[1]);
                            main.removeView(lives[0]);
                        }
                        if (!lives[2].getLife() && !lives[1].getLife() && lives[0].getLife()) {
                            main.removeView(lives[0]);
                        }

                        main.removeView(coin);
                        main.removeView(mgr.point);
                        GateMgr.gameCount++;
                        System.out.println(GateMgr.gameCount);

                        if(GateMgr.gameCount==6)
                            main.setBackground(getResources().getDrawable(R.drawable.bg_2));
                        if(GateMgr.gameCount==12)
                            main.setBackground(getResources().getDrawable(R.drawable.bg_3));

                        if(GateMgr.gameCount==6){
                            main.addView(mgr.stageBlack);
                            main.addView(mgr.stageNo2);
                            main.addView(mgr.light1);
                            main.addView(mgr.light2);
                            mgr.changeStage();

                            sC2Runnable = new Runnable() {
                                @Override
                                public void run() {
                                    main.removeView(mgr.stageBlack);
                                    main.removeView(mgr.stageNo2);
                                    main.removeView(mgr.light1);
                                    main.removeView(mgr.light2);
                                   mgr= new GateMgr(main_Activity, Views, main,0);
                                    sC2Handler.removeMessages(0);
                                }
                            };
                            sC2Handler = new Handler();
                            sC2Handler.postDelayed(sC2Runnable, 4000);//26

                        }

                        if(GateMgr.gameCount==12){
                            main.addView(mgr.stageBlack);
                            main.addView(mgr.stageNo3);
                            main.addView(mgr.light1);
                            main.addView(mgr.light2);
                            mgr.changeStage();


                            sC3Runnable = new Runnable() {
                                @Override
                                public void run() {
                                    main.removeView(mgr.stageBlack);
                                    main.removeView(mgr.stageNo3);
                                    main.removeView(mgr.light1);
                                    main.removeView(mgr.light2);
                                  mgr=  new GateMgr(main_Activity, Views, main,0);
                                    sC3Handler.removeMessages(0);
                                }
                            };
                            sC3Handler = new Handler();
                            sC3Handler.postDelayed(sC3Runnable, 4000);//26

                        }
                        if(GateMgr.gameCount!=6&&GateMgr.gameCount!=12&&GateMgr.gameCount!=18) {
                            System.out.println("카운트 "+GateMgr.gameCount);

                          mgr= new GateMgr(main_Activity, Views, main,0);


                        }
                        if (GateMgr.gameCount == 18) {
                            mgr.finish();
                        }


                    }

                }
            };
            tHandler = new Handler();
     //   if(GateMgr.gameCount<2)
     //       tHandler.postDelayed(tRunnable, 37000);//27
     //   else
            tHandler.postDelayed(tRunnable, 26000);//35





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



    public void setAffect(boolean a){this.attack = a;}
    public boolean getAffect(){return this.attack;}

    public void setGen(boolean a){this.gen=a;}
    public boolean getGen(){return this.gen;}




}
