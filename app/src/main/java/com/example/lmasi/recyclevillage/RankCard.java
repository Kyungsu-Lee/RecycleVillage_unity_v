package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.RelativeLayout;

public class RankCard extends RelativeLayout {

    private RelativeLayout.LayoutParams params;
    private int WIDTH;
    private int HEIGHT;
    private Drawable background;
    public onTextView grade;
    public TouchImageView upDown;
    public BorderTextView point;
    public TouchImageView profile;
    public onTextView name;
    public int profileIndex;

    public int score_sea;
    public int score_gate;

    RankCard(Context context) {
        super(context);

        profile = new TouchImageView(context);
        profile.setSize(199 * ScreenParameter.getScreenparam_x(),199 * ScreenParameter.getScreenparam_y());
        profile.setLocation(51 * ScreenParameter.getScreenparam_x(),48 * ScreenParameter.getScreenparam_y());
        profile.setBackground(R.drawable.c_profile_01);
        this.addView(profile);

        TouchImageView name_frame = new TouchImageView(context);
        name_frame.setSize(389 * ScreenParameter.getScreenparam_x(),86 * ScreenParameter.getScreenparam_y());
        name_frame.setLocation(270 * ScreenParameter.getScreenparam_x(),52 * ScreenParameter.getScreenparam_y());
        name_frame.setBackground(R.drawable.rank_namebox);
        this.addView(name_frame);

        name = new onTextView(context);
        name.setText(Resource.rank_name[0]);
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        name.setTextColor(Color.rgb(0, 255, 255));
        name.setTypeface(MainActivity.typeface);
        name.setLocation(322* ScreenParameter.getScreenparam_x(), 72* ScreenParameter.getScreenparam_y());
        this.addView(name);

        point = new BorderTextView(context);
        point.setText("2589");
        point.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (88* ScreenParameter.getScreenparam_y()));
        point.setTypeface(MainActivity.typeface2);
        point.setLocation(308* ScreenParameter.getScreenparam_x(), 156* ScreenParameter.getScreenparam_y());
        this.addView(point);

        grade = new onTextView(context);
        grade.setText("1위");
        grade.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (54.5* ScreenParameter.getScreenparam_y()));
        grade.setTextColor(Color.rgb(0, 255, 255));
        grade.setTypeface(MainActivity.typeface2);
        grade.setLocation(1033* ScreenParameter.getScreenparam_x(), 121* ScreenParameter.getScreenparam_y());
        this.addView(grade);

        upDown = new TouchImageView(context);
        upDown.setSize(72 * ScreenParameter.getScreenparam_x(),25 * ScreenParameter.getScreenparam_y());
        upDown.setLocation(1210 * ScreenParameter.getScreenparam_x(),140 * ScreenParameter.getScreenparam_y());
        upDown.setBackground(R.drawable.rank_none);
        this.addView(upDown);

        TouchImageView underBar = new TouchImageView(context);
        underBar.setSize(1356 * ScreenParameter.getScreenparam_x(),10 * ScreenParameter.getScreenparam_y());
        underBar.setLocation(0 * ScreenParameter.getScreenparam_x(),299 * ScreenParameter.getScreenparam_y());
        underBar.setBackground(R.drawable.under_bar);
        this.addView(underBar);
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
