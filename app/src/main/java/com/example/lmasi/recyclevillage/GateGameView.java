package com.example.lmasi.recyclevillage;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016-07-12.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class GateGameView extends ImageView {

    private RelativeLayout.LayoutParams params;
    protected Drawable background;
    private Handler handler;
    private boolean canDrag = false;    //condition for drag
    private boolean canMove = false;    //condition for move

    private int WIDTH;
    private int HEIGHT;

    protected GateGameView who;

    static public double screenparam_x;
    static public double screenparam_y;

    static public int screen_x;
    static public int screen_y;

    static public int narrawSize;
    static public int wideSize;

    public GateGameView(Context context, List<View> li)
    {

        super(context);

        if(li != null)
            li.add(this);

        who = this;

        setDrag();

    }



    public int setWidth(int width)
    {
        this.WIDTH = width;
        return width;
    }

    public int setHeight(int height)
    {
        this.HEIGHT = height;
        return height;
    }

    public int getWIDTH() {return this.WIDTH;}
    public int getHEIGHT() {return this.HEIGHT;}

    private void setsize(double width, double height)
    {
        setWidth((int)width);
        setHeight((int)height);
    }

    public void setSize(double width, double height)
    {
        params = new RelativeLayout.LayoutParams((int)width, (int)height);
        this.setLayoutParams(params);

        setsize(width, height);
    }

    public void setLocation(double x, double y)
    {
        if(params != null)
            params.setMargins((int)x,(int)y,0,0);
    }


    public void setBackground(int id)
    {
        background = getResources().getDrawable(id);
        super.setBackground(background);
    }

    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    public void startHandler(int a)
    {
        handler.sendEmptyMessageDelayed(0,a);
    }
    public void endHandler()
    {
        handler.removeMessages(1);
    }
    public void setCanDrag(boolean condition)
    {
        canDrag = condition;
    }

    public boolean getCanDrag() {return canDrag;}

    public void setCanMove(boolean condition) {this.canMove = condition;}
    public boolean getCanMove() {return this.canMove;}

    protected void setDrag()
    {
        this.setOnTouchListener(new View.OnTouchListener() {

            public float X;
            public float Y;


            public boolean mov = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //if(v.equals(who))
                {
                    if (canDrag) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            mov = true;

                            X = event.getRawX();
                           // Y = event.getRawY();

                            setCanMove(false);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            mov = false;
                            setCanMove(true);
                            return true;
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE && mov) {
                            float x = event.getRawX() - X;
                         //   float y = event.getRawY() - Y;


                            v.setX((float) (v.getX() + x));
                          //  v.setY((float) (v.getY() + y));


                            // setLocation(x, y);

                            X = event.getRawX();
                       //     Y = event.getRawY();

                        }
                    }
                }

                return true;
            }
        });
    }
}