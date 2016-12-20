package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class TouchImageView extends ImageView implements ObjectSizeLocation{

    private RelativeLayout.LayoutParams params;
    protected Drawable background;
    private Handler handler;
    private boolean canDrag = false;    //condition for drag
    private boolean canMove = false;    //condition for move

    private int WIDTH;
    private int HEIGHT;

    private int LX;
    private int LY;

    protected TouchImageView who;

    static public double screenparam_x;
    static public double screenparam_y;

    static public int screen_x;
    static public int screen_y;

    static public int narrawSize;
    static public int wideSize;



    public TouchImageView(Context context)
    {

        super(context);

        who = this;

        setDrag();

        screen_x = (int)ScreenParameter.getScreen_x();
        screen_y = (int)ScreenParameter.getScreen_y();

    }

    public void setLX(double x){this.LX = (int)x;}
    public void setLY(double y){this.LY = (int)y;}

    public int getLX(){return this.LX;}
    public int getLY(){return this.LY;}



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
            params.setMargins((int)x,(int)y,-1000,-1000);

        this.LX = (int)x;
        this.LY = (int)y;

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

    public void startHandler()
    {
        handler.sendEmptyMessage(0);
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
                            Y = event.getRawY();

                            setCanMove(false);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            mov = false;
                            setCanMove(true);
                            return true;
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE && mov) {
                            float x = event.getRawX() - X;
                            float y = event.getRawY() - Y;


                            v.setX((float) (v.getX() + x));
                            v.setY((float) (v.getY() + y));


                           // setLocation(x, y);

                            X = event.getRawX();
                            Y = event.getRawY();

                        }
                    }
                }

                return false;
            }
        });
    }


    public TouchImageView setScaleAnim(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (385  * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));

        return who;
    }

    public TouchImageView setScaleAnim_item_i(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (385-(130 + 0 + (23 + 127) * 4)  * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1700-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));

        return who;
    }

    public TouchImageView setScaleAnim1_char_i(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726-(130 + 0 + (23 + 127) * 4)   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1600-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));
//        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726.122   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
        return who;
    }

    public TouchImageView setScaleAnim1_char_imgview(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726-200   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-880-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));
//        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726.122   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
        return who;
    }

    public TouchImageView setScaleAnim1_char_info(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726-630   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-900-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));
//        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726.122   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
        return who;
    }

    public TouchImageView setScaleAnim1_char_buy(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) ((726-ObjectSizeLocation.Location_buy.x)   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) ((1882-ObjectSizeLocation.Location_buy.y)  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));
//        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726.122   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
        return who;
    }

    public TouchImageView setScaleAnim1_char_skill(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726-920   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1200-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));
//        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726.122   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
        return who;
    }

    public TouchImageView setScaleAnim1_char_heart(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726-700   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1200-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));
//        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726.122   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
        return who;
    }

    public TouchImageView setScaleAnim1_char_price_star(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726-730+50   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1424-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));
//        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726.122   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
        return who;
    }

    public TouchImageView setScaleAnim_game_mission(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (1048-170   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-925-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));

        return who;
    }
    public TouchImageView setScaleAnim_game_sea(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (1048-150   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1250-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));

        return who;
    }
    public TouchImageView setScaleAnim_game_gate(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (1048-515   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1250-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));

        return who;
    }
    public TouchImageView setScaleAnim_game_move(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (1048-905   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1250-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));

        return who;
    }

    public TouchImageView setScaleAnim_game_start(int speed)
    {
        //ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (1048.225   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882   * ScreenParameter.getScreenparam_y()));
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (1048-1000   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1670-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));

        return who;
    }

    public TouchImageView showAnim(int speed)
    {
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.25f, Animation.RELATIVE_TO_SELF, 1);
        this.startAnimation(anim);
        anim.setDuration(speed);

        return who;
    }
}
