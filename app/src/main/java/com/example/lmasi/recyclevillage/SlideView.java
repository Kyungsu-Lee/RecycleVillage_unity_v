package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lmasi on 2016-08-18.
 */
public abstract

class SlideView extends RelativeLayout {


    private RelativeLayout.LayoutParams thisParams;
    private int WIDTH;
    private int HEIGHT;

    private List<RelativeLayout> viewList = new ArrayList<>();
    private List<Integer> viewSize = new ArrayList<>();

    private Context context;
    private RelativeLayout main;
    private RelativeLayout.LayoutParams main_params;
    private int main_width;
    private int main_height;

    private int current_index = 0;
    private Handler movHandler;
    private OnTouchListener touchListener;

    private double speed;

    private int location = 10;

    public void setWIDTH(int width){this.WIDTH = width;}
    public void setHEIGHT(int height){this.HEIGHT = height;}

    public int getWIDTH(){return this.WIDTH;}
    public int getHEIGHT(){return this.HEIGHT;}

    public void setSpeed(double speed){this.speed = speed;}

    public int getIndex(){return this.current_index;}
    public int getCount(){return this.viewList.size();}

    private int G_WIDTH;
    private int time;


    public void setSize(int w, int h)
    {
        this.WIDTH = w;
        this.HEIGHT = h;

        if(this.thisParams == null)
            thisParams = new RelativeLayout.LayoutParams(w,h);

        this.setLayoutParams(thisParams);

        thisParams.width = w;
        thisParams.height = h;

        main_width = w;
        main_height = h;

        if(main_params == null)
            main_params = new RelativeLayout.LayoutParams(0,h);
        main_params.setMargins(0,0,0,0);
        G_WIDTH = main_params.leftMargin;
        main.setLayoutParams(main_params);
        this.addView(main);


    }

    public void setLocation(double x, double y)
    {
        this.thisParams.setMargins((int)x,(int)y,0,0);
    }



    SlideView(Context context)
    {
        super(context);

        this.context = context;
        main = new RelativeLayout(context);

        speed = 0;

        touchListener = new OnTouchListener() {

            boolean mov = false;

            double X;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN )
                {
                    mov = true;


                    X = motionEvent.getRawX();

                    if(current_index+1 < getCount())
                        viewList.get(current_index+1).setVisibility(VISIBLE);
                    viewList.get(current_index+0).setVisibility(VISIBLE);
                    if(current_index > 0)
                        viewList.get(current_index-1).setVisibility(VISIBLE);


                    return true;

                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP )
                {

                    mov = true;

                    main.setOnTouchListener(null);
                    viewList.get(current_index+0).setVisibility(VISIBLE);
                    setPage(current_index);

                    return true;
                }

                else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && mov|| motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP || motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN)
                {
                    onDrag();

                    double x = motionEvent.getRawX() - X;

                    if(Math.abs(x) < 10)
                        return false;


                    if(main_params.leftMargin + x <= 0 && main_params.leftMargin + x >= -main_width + 2 * getWIDTH()) {

                        main_params.leftMargin += x;
                        G_WIDTH = main_params.leftMargin;
                        main.setLayoutParams(main_params);
                    }

                    G_WIDTH = main_params.leftMargin;
                    X = motionEvent.getRawX();




                    int margin = G_WIDTH;


                    speed = -getWIDTH() + (margin - (current_index ) * getWIDTH());

                    double f =  0.25;

                    if(Math.abs(margin + (current_index ) * getWIDTH()) > getWIDTH()/5)
                    {
                        if(x<0)
                            current_index = (int)Math.abs(Math.floor((float)margin/(float)getWIDTH() + (0.5 - f)));
                        else
                            current_index = (int)Math.abs(Math.floor((float)margin/(float)getWIDTH() + (0.5 + f)));
                    }


                    Log.e("ASWDASd", Integer.toString(current_index));


                }

                else {
                    return  true;
                }


                return true;
            }
        };

        main.setOnTouchListener(touchListener);

        movHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {

                if(main_params.leftMargin > -G_WIDTH  - speed)
                    main_params.leftMargin += speed;
                else if(main_params.leftMargin < -G_WIDTH + speed)
                    main_params.leftMargin -= speed;
                else
                {
                    main_params.leftMargin = -G_WIDTH;
                    removeMessages(0);
                }


                main.setLayoutParams(main_params);

                main.setOnTouchListener(null);

                sendEmptyMessageDelayed(0,10);
            }
        };

        time = 200;

        speed = -2 * getWIDTH();
    }

    public void addLayout(RelativeLayout relativeLayout)
    {

        for(int i=0; i<viewList.size(); i++)
            location += viewSize.get(i) + 0;

        main_params.width += this.getWIDTH() + 0;
        main_width += this.getWIDTH() + 0;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(this.getWIDTH(), ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(location, 0, -1000, -0);
        relativeLayout.setLayoutParams(params);
        main.addView(relativeLayout);

        this.viewList.add(relativeLayout);
        this.viewSize.add(relativeLayout.getLayoutParams().width);

        location = 0;


    }


    public void toNextPage()
    {
        speed = -2 * getWIDTH();

        if(current_index + 1 < viewSize.size())
            setPage(++current_index);
        /*else
        {
            speed = -1.5*getWIDTH() * viewSize.size();
            setPage(current_index = 0);
        }*/
    }

    public void toPreviousPage()
    {
        speed = -2 * getWIDTH();

        if(current_index > 0)
            setPage(--current_index);
       /* else
        {
            speed = -1.5*getWIDTH() * viewSize.size();
            setPage(current_index = viewList.size() - 1);
        }*/
    }

    public void setPage(int index)
    {
        time = 200;

        main.setOnTouchListener(null);


        speed = speed * 10 / time;

        Log.e("123", Double.toString(speed));

        int width = 0;

        for(int i=0; i<index; i++)
            width += viewSize.get(i);

        G_WIDTH = width;

        movHandler.sendEmptyMessage(0);

        final Handler after = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                AfterAction();


            }
        };


        for(int i=0; i<getCount(); i++)
            if(i != current_index)
                viewList.get(i).setVisibility(INVISIBLE);
            else
                viewList.get(i).setVisibility(VISIBLE);


        new Timer().schedule(new TimerTask() {
            @Override
    public void run() {
        movHandler.removeMessages(0);
        main.setOnTouchListener(touchListener);
        after.sendEmptyMessage(0);
    }
},time *1);



        current_index = index;

        BeforeAction();
        }


    public void slidepage(int index)
    {
        time = 600;

        for(int i=0; i<getCount(); i++)
                viewList.get(i).setVisibility(VISIBLE);

        main.setOnTouchListener(null);


        speed = speed * 10 / time;

        Log.e("123", Double.toString(speed));

        int width = 0;

        for(int i=0; i<index; i++)
        {
            width += viewSize.get(i);

        }

        G_WIDTH = width;

        movHandler.sendEmptyMessage(0);

        final Handler after = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                AfterAction();


                for(int i=0; i<getCount(); i++)
                    if(i != current_index)
                        viewList.get(i).setVisibility(INVISIBLE);
                    else
                        viewList.get(i).setVisibility(VISIBLE);


            }
        };


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                movHandler.removeMessages(0);
                main.setOnTouchListener(touchListener);
                after.sendEmptyMessage(0);


            }
        },time *1);



        current_index = index;

        BeforeAction();
    }

    public void setTime(int time){this.time = time;}

    abstract public void BeforeAction();
    abstract public void AfterAction();
    abstract public void onDrag();



}
