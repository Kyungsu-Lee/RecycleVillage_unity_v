package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2016-07-12.
 */
public class GateCharacter extends GateGameView{

    int limit=0;
    int i =0;
    int temp = 99;
    int check = 99;
    boolean start_motion = true;

    boolean r_barrier = false;
    boolean l_barrier = false;

    static final int num_item = 1;
    private boolean Isright;
    private int index;

    public int img_front;
    public int img_left;
    public int img_right;
    public int pause;

    static  public List<Drawable> imgs;

    Context main_Activity;

    Handler[] handle;




    public GateCharacter(Context context, List<View> viewList,RelativeLayout r)
    {
        super(context, viewList);

        main_Activity = context;

        imgs = new Vector<Drawable>();


        for(int i=0; i<num_item; i++)
        {
            if(GateRunnerActivity.charType==0){
                //노랑
                imgs.add(getResources().getDrawable(R.drawable.char1_walk1 + i));
            }
            if(GateRunnerActivity.charType==1){
                //검정
                imgs.add(getResources().getDrawable(R.drawable.char1_walk1 + i));
            }
            if(GateRunnerActivity.charType==2){
                //감자
                imgs.add(getResources().getDrawable(R.drawable.char1_walk1 + i));
            }

        }

        this.setBackground(imgs.get(0));

        super.setCanMove(true);

        handle= new Handler[5];
        //  handle[index] = new Handler();

        handle[4] = new Handler() { //맨 처음 게임에 들어와서 아무런 터치를 안했을때 혼자서 뛰게 하는 부분
            public boolean F = true;

            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
            {

                if (F)
                    walk(img_front);
                else
                    walk(img_front + 1);
                F = !F;
                sendEmptyMessageDelayed(0, 300); //무한 실행. (delay, msec)
            }
        };
        handle[4].sendEmptyMessage(0);
    }

    public void setDirection(int index)
    {
        if(start_motion)
        {
            handle[4].removeMessages(0);
            start_motion = false;
        }
            switch (index) {

                    case 0:
                        if(check != 0) {
                        Isright = false;

                        if (temp != 99 && temp != index)
                            handle[temp].removeMessages(0);

                        handle[index] = new Handler() {
                            public boolean F = true;

                            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
                            {

                                if (F)
                                    walk(img_front);
                                else
                                    walk(img_front + 1);
                                F = !F;
                                sendEmptyMessageDelayed(0, 300); //무한 실행. (delay, msec)
                            }
                        };
                        handle[index].sendEmptyMessage(0);
                        check = 0;
                }
                    break;

                case 1:
                    if(check !=1) {
                        Isright = true;

                        if (temp != 99 && temp != index)
                            handle[temp].removeMessages(0);

                        handle[index] = new Handler() {

                            public boolean F = true;

                            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
                            {

                                if (F)
                                    walk(img_right);
                                else
                                    walk(img_right + 1);
                                F = !F;
                                sendEmptyMessageDelayed(0, 300); //무한 실행. (delay, msec)
                            }
                        };
                        handle[index].sendEmptyMessage(0);
                        check = 1;
                    }

                    break;

                case 2:
                    if(check != 2) {
                        Isright = false;

                        if (temp != 99 && temp != index)
                            handle[temp].removeMessages(0);

                        handle[index] = new Handler() {

                            public boolean F = true;

                            public void handleMessage(Message msg) //물체의 움직임을 담당하는 메서드. 필수적인 부분
                            {

                                if (F)
                                    walk(img_left);
                                else
                                    walk(img_left + 1);
                                F = !F;
                                sendEmptyMessageDelayed(0, 300); //무한 실행. (delay, msec)
                            }
                        };
                        handle[index].sendEmptyMessage(0);
                        check = 2;
                    }
                    break;

                case 3:
                    if (temp != 99 && temp != index)
                        handle[temp].removeMessages(0);


                        Isright = false;

                        walk(pause);

                    break;


            }

            temp = index;





    }

    public void walk(int direction)
    {
        super.setBackground( getResources().getDrawable(direction));
    }

    public boolean IsRight() {return this.Isright;}

    public void setImges(int pause, int front, int left, int right)
    {
        this.pause = pause;
        img_front = front;
        img_left = left;
        img_right = right;
    }



    @Override
    protected void setDrag()
    {
        this.setOnTouchListener(new View.OnTouchListener() {

            public float X;
            public float priv = 0;


            public boolean mov = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //if(v.equals(who))
                {
                  //  setCanDrag(true);
                    if (getCanDrag()) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            mov = true;

                            X = event.getRawX();


                            setCanMove(false);

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            mov = true;

                            setCanMove(true);
                            index = 0;
                            setDirection(index);
                            return false;
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE && mov) {

                            if(getX() > screen_x - who.getWIDTH())
                            {
                              //  setCanDrag(false);
                               // setCanMove(false);

                                r_barrier = true;
                                v.setX((float) (v.getX() -20));
                            }

                            if(getX() <  who.getWIDTH()/6)
                            {
                                //setCanDrag(false);
                                //setCanMove(false);

                                l_barrier = true;
                                v.setX((float) (v.getX() +20));
                            }

                            if(!r_barrier && getX() > screen_x/2) {
                                float x = event.getRawX() - X;

                                v.setX((float) (v.getX() + x));
                            }

                            if(!l_barrier && getX() < screen_x/2) {
                                float x = event.getRawX() - X;

                                v.setX((float) (v.getX() + x));
                            }
                            //   setLocation(x, y);

                            if(priv != 0) {
                                if (getX() > priv) { //오른쪽으로 드래그 했을때
                                    l_barrier = false;
                                    index = 1;

                                    setDirection(index);
                                  //  System.out.println(screen_x - who.getWIDTH());


                                } else if (getX() < priv) { //왼쪽으로 드래그 했을때
                                    r_barrier = false;
                                    index = 2;

                                    setDirection(index);

                                }
                            }
                                X = event.getRawX();
                                priv = getX();


                        }
                    }
                }

                return true;
            }
        });
    }



}
