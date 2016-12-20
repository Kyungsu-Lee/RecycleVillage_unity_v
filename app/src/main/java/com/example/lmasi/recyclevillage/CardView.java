package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class CardView extends RelativeLayout{

    private RelativeLayout.LayoutParams params;
    private int WIDTH;
    private int HEIGHT;
    private Drawable background;
    private onTextView title;
    private onTextView info;
    private TouchImageView red_arrow;

    CardView(Context context, String stitle, String sbody)
    {
        super(context);

        title = new onTextView(context);
        title.setText(stitle);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,(float) (45 * ScreenParameter.getScreenparam_y()));
        title.setTextColor(Color.rgb(209, 211, 212));
        title.setTypeface(MainActivity.typeface);
        title.setLocation(45* ScreenParameter.getScreenparam_x(), 50* ScreenParameter.getScreenparam_y());
        this.addView(title);

        info = new onTextView(context);
        info.setText(sbody);
        info.setTextSize(TypedValue.COMPLEX_UNIT_PX,(float) (40 * ScreenParameter.getScreenparam_y()));
        info.setTextColor(Color.argb(77 ,204,204,204));
        info.setLocation(45* ScreenParameter.getScreenparam_x(), 120* ScreenParameter.getScreenparam_y());
        info.setTypeface(MainActivity.typeface);

        this.addView(info);

        red_arrow = new TouchImageView(context);
        red_arrow.setSize(28* ScreenParameter.getScreenparam_x(),24* ScreenParameter.getScreenparam_y());
        red_arrow.setLocation(1050* ScreenParameter.getScreenparam_x(),260* ScreenParameter.getScreenparam_y());
        red_arrow.setBackground(R.drawable.more_text);
        /*
        red_arrow.setOnTouchListener(new OnTouchListener() {
            boolean ismore = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    if(!ismore) {
                        params.height += 830;
                        setLayoutParams(params);
                        setBackgroundColor(Color.rgb(1, 60, 76));
                        invalidate();


                        red_arrow.setRotation(180);
                        red_arrow.setY(800);

                        ismore=true;

                        bringToFront();
                    }
                    else {
                        params.height -= 830;
                        setLayoutParams(params);
                        setBackground(R.drawable.notice_bg_1);
                        red_arrow.setRotation(0);
                        red_arrow.setY(260);

                        ismore=false;
                    }



                }
                return false;
            }
        });

        */
        this.addView(red_arrow);

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

}