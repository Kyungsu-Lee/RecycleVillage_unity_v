package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;


import java.util.List;
import java.util.Vector;


public class GateBuilding extends GateGameView {

    private List<View> Views;
    private List<View> viewList;
    static public List<Drawable> imgs;

    Context main_Activity;
    RelativeLayout main;

    Handler[] lbuildingHandle;
    Handler[] rbuildingHandle;

    private int lbuild_no=9;
    private int rbuild_no=9;

    private int temp=0;

    GateStuff[] lbuildings;
    GateStuff[] rbuildings;

    public GateBuilding(Context context, List<View> viewList, RelativeLayout r,GateStuff[] lbuildings,GateStuff[] rbuildings)
    {
        super(context, viewList);

        main_Activity = context;
        this.main = r;

        Views = new Vector<View>();

        imgs = new Vector<Drawable>();
        super.setCanMove(true);

        lbuildingHandle= new Handler[lbuild_no];
        rbuildingHandle= new Handler[rbuild_no];

        this.lbuildings = new GateStuff[lbuild_no];
        this.rbuildings = new GateStuff[rbuild_no];



        for(int i=0; i<lbuild_no;i++) {
            this.lbuildings[i] =lbuildings[i];
        }
        for(int i=0; i<rbuild_no;i++) {
            this.rbuildings[i] =rbuildings[i];
        }

        makelBuildings();
        makerBuildings();


    }

    public void makelBuildings(){
      //  lbuildings=new GateBuilding[lbuild_no];
        temp=0;
        for(int i=0;i<lbuild_no;i++){
          //  lbuildings[i] = new GateBuilding(main_Activity, viewList,main);


            lbuildings[i].setBackground(R.drawable.edit_1 + i);
            lbuildings[i].setSize(5 * screenparam_x, 5 * screenparam_y);
            main.addView(lbuildings[i]);

            this.lmoveBuildings(i,temp);
            temp=i;
        }
    }

    public void lmoveBuildings(final int i,final int temp) {

        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();

        if(GateMgr.gameCount>=0&&GateMgr.gameCount<6) {
            lbuildingHandle[i] = new Handler() {
                public void handleMessage(Message msg) {
                    //아이템의 y값을 조정해서 내려가는 부분

                   // lbuildings[temp].bringToFront();

                    lbuildings[i].setY(lbuildings[i].getY() + (float) (0));
                    lbuildings[i].setX(lbuildings[i].getX() - (float) (4.5 * screenparam_x));
                    lbuildings[i].setSize(lbuildings[i].getWIDTH() + 8, lbuildings[i].getHEIGHT() + 8);
                    lbuildings[i].setLocation(265 * screenparam_x, 380 * screenparam_y);
                    //경사에 맞게 조정

                    if (lbuildings[i].getY() > screen_y * 1 / 4) {
                        if (i == 0)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1.7));
                        if (i == 1)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                        if (i == 2)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                        if (i == 3)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (3));
                        if (i == 4)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (2));
                        if (i == 6)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                        if (i == 7)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                        if (i == 8)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                    }


                    //화면 밖에 나오면 삭제
                    if (lbuildings[i].getX() - lbuildings[i].getWIDTH() > screen_x|| !GateMgr.master) {
                        main.removeView(lbuildings[i]);
                        lbuildings[i].setBackground(null);
                        lbuildingHandle[i].removeMessages(0);
                    }


                    sendEmptyMessageDelayed(0, 10);
                }

            };
            lbuildingHandle[i].sendEmptyMessageDelayed(0, i * 1000);//handler 실행 아이템, 아이템간의 간격
        }

        if(GateMgr.gameCount>=6&&GateMgr.gameCount<12) {
            lbuildingHandle[i] = new Handler() {
                public void handleMessage(Message msg) {
                    //아이템의 y값을 조정해서 내려가는 부분
                   // lbuildings[temp].bringToFront();

                    lbuildings[i].setY(lbuildings[i].getY() + (float) (0));
                    lbuildings[i].setX(lbuildings[i].getX() - (float) (7.5 * screenparam_x));
                    lbuildings[i].setSize(lbuildings[i].getWIDTH() + 13, lbuildings[i].getHEIGHT() + 13);
                    lbuildings[i].setLocation(280 * screenparam_x, 350 * screenparam_y);
                    //경사에 맞게 조정

                    if (lbuildings[i].getY() > screen_y * 1 / 4) {
                        if (i == 0)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (2));
                        if (i == 4)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (2));
                        if (i == 3)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (4));
                        if (i == 6)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                        if (i == 7)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                        if (i == 8)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                    }


                    //화면 밖에 나오면 삭제
                    if (lbuildings[i].getX() - lbuildings[i].getWIDTH() > screen_x|| !GateMgr.master) {
                        main.removeView(lbuildings[i]);
                        lbuildings[i].setBackground(null);
                        lbuildingHandle[i].removeMessages(0);
                    }


                    sendEmptyMessageDelayed(0, 10);
                }

            };
            lbuildingHandle[i].sendEmptyMessageDelayed(0, i * 750);//handler 실행 아이템, 아이템간의 간격
        }

        if(GateMgr.gameCount>=12&&GateMgr.gameCount<18) {
            lbuildingHandle[i] = new Handler() {
                public void handleMessage(Message msg) {
                    //아이템의 y값을 조정해서 내려가는 부분

                    // lbuildings[temp].bringToFront();

                    lbuildings[i].setY(lbuildings[i].getY() + (float) (0));
                    lbuildings[i].setX(lbuildings[i].getX() - (float) (10* screenparam_x));
                    lbuildings[i].setSize(lbuildings[i].getWIDTH() + 17.5, lbuildings[i].getHEIGHT() + 17.5);
                    lbuildings[i].setLocation(280 * screenparam_x, 350 * screenparam_y);
                    //경사에 맞게 조정

                    if (lbuildings[i].getY() > screen_y * 1 / 4) {
                        if (i == 0)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (2));
                        if (i == 4)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (2));
                        if (i == 3)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (4));
                        if (i == 6)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (0.7));
                        if (i == 7)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                        if (i == 8)
                            lbuildings[i].setX(lbuildings[i].getX() - (float) (1));
                    }


                    //화면 밖에 나오면 삭제
                    if (lbuildings[i].getX() - lbuildings[i].getWIDTH() > screen_x|| !GateMgr.master) {
                        main.removeView(lbuildings[i]);
                        lbuildings[i].setBackground(null);
                        lbuildingHandle[i].removeMessages(0);
                    }


                    sendEmptyMessageDelayed(0, 10);
                }

            };
            lbuildingHandle[i].sendEmptyMessageDelayed(0, i * 650);//handler 실행 아이템, 아이템간의 간격
        }
    }
    public void makerBuildings(){
     //   rbuildings=new GateBuilding[rbuild_no];
        temp=0;
        for(int a=0;a<rbuild_no;a++){
          //  rbuildings[a] = new GateBuilding(main_Activity, viewList,main);

            rbuildings[a].setBackground(R.drawable.edit_r_1 + a);
            rbuildings[a].setSize(5 * screenparam_x, 5  * screenparam_y);
           // rbuildings[temp].bringToFront();

            main.addView(rbuildings[a]);
            this.rmoveBuildings(a,temp);
            temp = a;
        }
        // buildings[a].bringToFront();
    }
    public void rmoveBuildings(final int a,final int temp) {

        GateRunnerActivity.black1.bringToFront();
        GateRunnerActivity.resume.bringToFront();
        GateRunnerActivity.replay.bringToFront();
        GateRunnerActivity.over.bringToFront();

        rbuildings[temp].bringToFront();



        if(GateMgr.gameCount>=0&&GateMgr.gameCount<6) {
            rbuildingHandle[a] = new Handler() {
                public void handleMessage(Message msg) {

                  //  rbuildings[temp].bringToFront();

                    rbuildings[a].setY(rbuildings[a].getY() + (float) (0));
                    rbuildings[a].setX(rbuildings[a].getX() + (float) (10.5 * screenparam_x));
                    rbuildings[a].setSize(rbuildings[a].getWIDTH() + 8, rbuildings[a].getHEIGHT() + 8);
                    rbuildings[a].setLocation(440 * screenparam_x, 380 * screenparam_y);
                    if (rbuildings[a].getY() > screen_y * 1 / 4) {
                        switch(a)
                        {
                            case 0: rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                                break;
                            case 2 :rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                                break;
                            case 3: rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                                break;
                            case 4: rbuildings[a].setX(rbuildings[a].getX() - (float) (7.5 * screenparam_x));
                                break;
                            case 5: rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                                break;
                            case 7: rbuildings[a].setX(rbuildings[a].getX() - (float) (8.2 * screenparam_x));
                                break;
                            case 8: rbuildings[a].setX(rbuildings[a].getX() - (float) (7.8 * screenparam_x));
                                break;
                            default:rbuildings[a].setX(rbuildings[a].getX() - (float) (7.8 * screenparam_x));
                                break;
                        }

                    }


                    if (rbuildings[a].getX() > screen_x|| !GateMgr.master) {
                        main.removeView(rbuildings[a]);
                        rbuildings[a].setBackground(null);
                        rbuildingHandle[a].removeMessages(0);
                    }


                    sendEmptyMessageDelayed(0, 10); // (delay, msec)
                }

            };
            rbuildingHandle[a].sendEmptyMessageDelayed(0, a * 1000);//handler 실행 아이템
        }

        if (GateMgr.gameCount >= 6 && GateMgr.gameCount < 12) {
            rbuildingHandle[a] = new Handler() {
                public void handleMessage(Message msg) {
                   // rbuildings[temp].bringToFront();

                    rbuildings[a].setY(rbuildings[a].getY() + (float) (0));
                    rbuildings[a].setX(rbuildings[a].getX() + (float) (12 * screenparam_x));
                    rbuildings[a].setSize(rbuildings[a].getWIDTH() + 13, rbuildings[a].getHEIGHT() + 13);
                    rbuildings[a].setLocation(425 * screenparam_x, 350 * screenparam_y);
                    if (rbuildings[a].getY() > screen_y * 1 / 4) {
                        if (a == 0) {
                            rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                        }
                        if (a == 2) {
                            rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                        }
                        if (a == 4) {
                            rbuildings[a].setX(rbuildings[a].getX() - (float) (7.2 * screenparam_x));
                        }
                        if (a == 7) {
                            rbuildings[a].setX(rbuildings[a].getX() - (float) (8.2 * screenparam_x));
                        }
                        if (a == 8) {
                            rbuildings[a].setX(rbuildings[a].getX() - (float) (7.6 * screenparam_x));
                        } else if (a != 0 && a != 2 && a != 4 && a != 4 && a != 7 && a != 8) {
                            rbuildings[a].setX(rbuildings[a].getX() - (float) (7.8 * screenparam_x));
                        }
                    }


                    if (rbuildings[a].getX() > screen_x|| !GateMgr.master) {
                        main.removeView(rbuildings[a]);
                        rbuildings[a].setBackground(null);
                        rbuildingHandle[a].removeMessages(0);
                    }


                    sendEmptyMessageDelayed(0, 10); // (delay, msec)
                }

            };
            rbuildingHandle[a].sendEmptyMessageDelayed(0, a * 750);//handler 실행 아이템

        }
        if(GateMgr.gameCount>=12&&GateMgr.gameCount<18) {
            rbuildingHandle[a] = new Handler() {
                public void handleMessage(Message msg) {

                    //  rbuildings[temp].bringToFront();

                    rbuildings[a].setY(rbuildings[a].getY() + (float) (0));
                    rbuildings[a].setX(rbuildings[a].getX() + (float) (13.2 * screenparam_x));
                    rbuildings[a].setSize(rbuildings[a].getWIDTH() + 17.5, rbuildings[a].getHEIGHT() + 17.5);
                    rbuildings[a].setLocation(425 * screenparam_x, 350 * screenparam_y);
                    if (rbuildings[a].getY() > screen_y * 1 / 4) {
                        switch(a)
                        {
                            case 0: rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                                break;
                            case 2 :rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                                break;
                            case 3: rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                                break;
                            case 4: rbuildings[a].setX(rbuildings[a].getX() - (float) (7.5 * screenparam_x));
                                break;
                            case 5: rbuildings[a].setX(rbuildings[a].getX() - (float) (8 * screenparam_x));
                                break;
                            case 7: rbuildings[a].setX(rbuildings[a].getX() - (float) (8.2 * screenparam_x));
                                break;
                            case 8: rbuildings[a].setX(rbuildings[a].getX() - (float) (7.8 * screenparam_x));
                                break;
                            default:rbuildings[a].setX(rbuildings[a].getX() - (float) (7.8 * screenparam_x));
                                break;
                        }

                    }


                    if (rbuildings[a].getX() > screen_x|| !GateMgr.master) {
                        main.removeView(rbuildings[a]);
                        rbuildings[a].setBackground(null);
                        rbuildingHandle[a].removeMessages(0);
                    }


                    sendEmptyMessageDelayed(0, 10); // (delay, msec)
                }

            };
            rbuildingHandle[a].sendEmptyMessageDelayed(0, a * 650);//handler 실행 아이템
        }
    }
}
