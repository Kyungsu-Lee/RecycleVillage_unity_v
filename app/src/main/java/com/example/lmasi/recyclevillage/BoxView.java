package com.example.lmasi.recyclevillage;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by lmasi on 2016-06-23.
 */
public class BoxView extends ImageView{ //image view:

    private RelativeLayout.LayoutParams params; //이미지를 넣을때 필수 적인 선언
    private int LX, LY;
    private int LWIDTH, LHEIGHT;



    BoxView(Context context)
    {
        super(context);
    } //생성자

    public int setLX(int x){ this.LX = x; return x;}
    public int setLY(int y){ this.LY = y; return y;}
    private void setWIDTH(int width){this.LWIDTH = width;}
    private void setHEIGHT(int height){ this.LHEIGHT = height;}


    public int getLX() {return this.LX;}
    public int getLY() {return this.LY;}
    public int getWIDTH() {return this.LWIDTH;}
    public int getHEIGHT() {return this.LHEIGHT;}




    public void setSize(int width, int height)
    {
        params = new RelativeLayout.LayoutParams(width, height); //object를 만들어 가로, 세로 값을 넣어 만드는 것.
        this.setLayoutParams(params);//object를 만들어 가로, 세로 값을 넣어 만드는 것.
        setWIDTH(width);
        setHEIGHT(height);
    }

    public void setlocation(int x, int y)
    {
        if(params == null)
        {
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(params);
            setWIDTH(0);
            setHEIGHT(0);
        }

        setLX(x);
        setLY(y);
        params.setMargins(x,y,0,0); //여백 설정 function.
        this.setLayoutParams(params);//object를 만들어 가로, 세로 값을 넣어 만드는 것.

    }

    public void set_location_size(int x, int y, int width, int height)
    {
        setSize(width, height);
        setlocation(x,y);
    }





}
