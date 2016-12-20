package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
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

class CircularScrollView extends RelativeLayout {


    private LayoutParams thisParams;
    private int WIDTH;
    private int HEIGHT;

    private List<View> viewList = new ArrayList<>();
    public List<RelativeLayout> relativeLayouts = new ArrayList<>();
    private List<RelativeLayout.LayoutParams> layoutParamsList = new ArrayList<>();
    private List<Integer> default_margin = new ArrayList<>();

    private Context context;

    private RelativeLayout main;
    private LayoutParams main_params;


    private int current_index = 0;
    private Handler movHandler;
    private OnTouchListener touchListener;


    public void setWIDTH(int width){this.WIDTH = width;}
    public void setHEIGHT(int height){this.HEIGHT = height;}

    public int getWIDTH(){return this.WIDTH;}
    public int getHEIGHT(){return this.HEIGHT;}

    public int getIndex(){return this.current_index;}
    public int getCount(){return this.relativeLayouts.size();}

    private int d_margin;

    private int speed = 0;
    private int time = 0;

    Handler handler;


    public void setSize(int w, int h)
    {
        this.WIDTH = w;
        this.HEIGHT = h;

        if(this.thisParams == null)
            thisParams = new LayoutParams(w,h);

        this.setLayoutParams(thisParams);

        thisParams.width = w;
        thisParams.height = h;


        if(main_params == null)
            main_params = new LayoutParams(0,h);

        main_params.setMargins(0,0,0,0);
        main.setLayoutParams(main_params);
        this.addView(main);

        main.setOnTouchListener(touchListener);
    }

    public void setLocation(double x, double y)
    {
        this.thisParams.setMargins((int)x,(int)y,0,0);
    }



    CircularScrollView(Context context)
    {
        super(context);

        this.context = context;
        main = new RelativeLayout(context);


        touchListener = new OnTouchListener() {

            boolean mov = false;

            double X;

            int mov_index = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN )
                {
                    mov = true;


                    X = motionEvent.getRawX();




                    return true;

                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP )
                {

                    mov = true;


                    if(mov_index == 0)
                        setPage(current_index);
                    else if(mov_index == 1)
                        setPage(current_index + 1);
                    else
                        setPage(current_index - 1);


                    if(current_index+1 < getCount())
                        viewList.get(current_index+1).setVisibility(VISIBLE);
                    viewList.get(current_index+0).setVisibility(VISIBLE);
                    if(current_index > 0)
                        viewList.get(current_index-1).setVisibility(VISIBLE);


                    return true;
                }

                else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && mov|| motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP || motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN)
                {
                    onDrag();

                    double x = motionEvent.getRawX() - X;

                    if(Math.abs(x) < WIDTH/100)
                    {
                        mov_index =0;
                        return false;
                    }
                        else


                    if((main_params.leftMargin + x < 0 && main_params.leftMargin + x > - WIDTH * (getCount() - 1)/2))
                    {
                        main_params.leftMargin += x;
                        main.setLayoutParams(main_params);


                        if(x>0)
                            mov_index = -1;
                        else
                            mov_index = 1;

                    }

                    else
                    {
                        mov_index =0;
                        return false;
                    }



                    X = motionEvent.getRawX();


                    Log.e("ASWDASd", Integer.toString(current_index) + " //// " + Double.toString(x) + " //// " + Integer.toString(mov_index));


                }

                else {
                    return  true;
                }


                return true;
            }
        };

        handler = new Handler()
        {
            double x = 0;
            double re_x = 0;

            Point p = new Point(WIDTH/2, 0);

            @Override
            public void handleMessage(Message msg) {

                for (int i = (getIndex()-1 < 0 ? 0 : getIndex()-1); i < getIndex() + 2 && i>=0 && i < getCount(); i++)
                {

                    x = default_margin.get(i);
                    re_x = main_params.leftMargin + x - WIDTH/4;

                    double scale = - (2.0/Math.pow(WIDTH,2)) * (re_x - WIDTH/2) * (re_x + WIDTH / 2) + 0.5;

                    viewList.get(i).setScaleX((float) scale);
                    viewList.get(i).setScaleY((float) scale);

                    double theta = Math.atan(( viewList.get(i).getLayoutParams().width - p.x)/( viewList.get(i).getLayoutParams().height - p.y));
                    double R = Math.sqrt(Math.pow( viewList.get(i).getLayoutParams().width - p.x, 2) + Math.pow( viewList.get(i).getLayoutParams().height - p.y, 2));
                    double alpha = Math.atan((re_x + R * Math.sin(theta))/(R * Math.cos(theta)));


                    viewList.get(i).setY((float) ( viewList.get(i).getLayoutParams().height - 500 *ScreenParameter.getScreenparam_y() - R * (Math.cos(theta) - Math.cos(alpha))));




                    //if(i==0)
                    //Log.e("///////asdasdasd?///", Double.toString(re_x));
                }

                sendEmptyMessageDelayed(0,10);
            }
        };



        movHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {


                if(main_params.leftMargin > d_margin  +speed)
                    main_params.leftMargin -= speed;
                else if(main_params.leftMargin < d_margin - speed)
                    main_params.leftMargin += speed;
                else
                {
                    main_params.leftMargin = d_margin;
                    main.setOnTouchListener(touchListener);
/*
                    for(int i=0; i<getCount(); i++)
                        viewList.get(i).setVisibility(INVISIBLE);

                    if(current_index+1 < getCount())
                        viewList.get(current_index+1).setVisibility(VISIBLE);
                    viewList.get(current_index+0).setVisibility(VISIBLE);
                    if(current_index > 0)
                        viewList.get(current_index-1).setVisibility(VISIBLE);
*/
                    removeMessages(0);
                }


                main.setLayoutParams(main_params);

                sendEmptyMessageDelayed(0,10);
            }
        };

    }

    public void addCircleView(View view)
    {
        default_margin.add(WIDTH/4 + WIDTH/2 * getCount());
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayouts.add(relativeLayout);
        RelativeLayout.LayoutParams params = new LayoutParams(WIDTH/2, HEIGHT);
        layoutParamsList.add(params);
        params.setMargins(default_margin.get(getCount()-1),0,0,0);
        relativeLayout.setLayoutParams(params);
        main.addView(relativeLayout);

        RelativeLayout.LayoutParams viewParam = new LayoutParams((int)(700 * ScreenParameter.getScreenparam_x()), (int)(863 * ScreenParameter.getScreenparam_y()));
        viewParam.setMargins(10,250, -1000, 0);
        view.setLayoutParams(viewParam);
        viewList.add(view);
        relativeLayout.addView(view);

        TouchImageView touchBox = new TouchImageView(context);
        touchBox.setSize(120 * ScreenParameter.getScreenparam_x(), 120 * ScreenParameter.getScreenparam_y());
        touchBox.setLocation(320 * ScreenParameter.getScreenparam_x(), 680 * ScreenParameter.getScreenparam_y());
        //touchBox.setBackgroundColor(Color.CYAN);
        relativeLayout.addView(touchBox);
        touchBox.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                TouchEvent();

                return false;
            }
        });


        main_params = new RelativeLayout.LayoutParams(WIDTH/2 * getCount() + WIDTH/4, HEIGHT);
        main_params.setMargins(0, 0,0,0);
        main.setLayoutParams(main_params);
    }


    public void toNextPage()
    {

    }

    public void toPreviousPage()
    {

    }

    public void setPage(int index)
    {
        if(index < 0 || index > getCount()-1)
            return;

        current_index = index;


        d_margin = 0;

        for(int i=0; i<index; i++)
            d_margin -= WIDTH/2;

        time = 200;
        speed = ((int)(Math.abs(main_params.leftMargin - d_margin) * 10 * 1 /time));


        movHandler.sendEmptyMessage(0);
        main.setOnTouchListener(null);

        for(int i=0; i<getCount(); i++)
            if(i != current_index)
                viewList.get(i).setVisibility(INVISIBLE);
            else
                viewList.get(i).setVisibility(VISIBLE);

        BeforeAction();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                main.setOnTouchListener(touchListener);
                movHandler.removeMessages(0);
            }
        }, time * 2);



    }

    public void slidepage(int index)
    {
       /* if(index < 0 || index > getCount()-1)
            return;

        current_index = index;
        BeforeAction();


        d_margin = 0;

        for(int i=0; i<index; i++)
            d_margin -= WIDTH/2;

        speed = 100;
        time =  ((int)(Math.abs(main_params.leftMargin - d_margin) * 50 * 1 /speed));

        Log.e("//////////////", Integer.toString(speed));

        movHandler.sendEmptyMessage(0);
        main.setOnTouchListener(null);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                main.setOnTouchListener(touchListener);
                movHandler.removeMessages(0);
            }
        }, time +10);

        */

        final int idx = index;

        main.setOnTouchListener(null);

        final Handler deP = new Handler()
        {
            int start = current_index;

            @Override
            public void handleMessage(Message msg)
            {
                setPage(current_index--);



                sendEmptyMessageDelayed(0,50);
            }
        };

        final Handler inP = new Handler()
        {
            int start = current_index;

            @Override
            public void handleMessage(Message msg)
            {
                setPage(current_index++);

                sendEmptyMessageDelayed(0,50);
            }
        };

        if(current_index < index)
        {
            deP.sendEmptyMessage(0);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    deP.removeMessages(0);
                    main.setOnTouchListener(touchListener);
                }
            }, 1000 * Math.abs(current_index - idx));
        }

        else
        {
            inP.sendEmptyMessage(0);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    inP.removeMessages(0);
                    main.setOnTouchListener(touchListener);
                }
            }, 1000 * Math.abs(current_index - idx));
        }

        current_index = index;
    }

    public void startView()
    {
        handler.sendEmptyMessage(0);
    }

    abstract public void BeforeAction();
    abstract public void AfterAction();
    abstract public void onDrag();
    abstract public void TouchEvent();



}
