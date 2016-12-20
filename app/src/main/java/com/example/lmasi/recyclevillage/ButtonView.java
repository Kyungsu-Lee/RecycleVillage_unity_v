package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public abstract class ButtonView extends  TouchImageView {

    protected   Drawable img_clicked;
    protected  Drawable img_unclicked;

    protected boolean Isclicked = false;
    protected  boolean canClick = true;

    protected ArrayList<Boolean> conditions = new ArrayList<>();

    public ButtonView(Context context, int unclicked, int clicked)
    {
        super(context);


        this.img_clicked = getResources().getDrawable(clicked);
        this.img_unclicked = getResources().getDrawable(unclicked);

        super.setBackground(getResources().getDrawable(unclicked));

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    TouchedEvent();

                return false;
            }
        });

        conditions.add(canClick);
        conditions.add(MainActivity.semaphore);
    }

    public void setClicked(boolean condition) { this.Isclicked = condition;}
    public boolean IsClicked(){return this.Isclicked;}
    public void CanClick(boolean condition){canClick = condition;}

    private void TouchedEvent()
    {
        boolean allTrue = true;

        for(int i=0; i<conditions.size() && allTrue; i++)
            allTrue &= conditions.get(i);

        if(allTrue && canClick && MainActivity.semaphore)
            if(IsClicked())
            {
                UnClick();
            }
            else
            {
                Click();
            }
    }

    public void Click()
    {
        System.gc();

        setClicked(true);
        super.setBackground(img_clicked);
        this.invalidate();
        MainActivity.canNotice = false;
        ClickAction();
    }

    public void UnClick()
    {
        System.gc();

        setClicked(false);
        super.setBackground(img_unclicked);
        this.invalidate();
        UnClickAction();
    }

    public abstract void ClickAction();

    public abstract void UnClickAction();

    public void addCondition(boolean condition){
        conditions.add(condition);
    }

    public void setImages(int clicked, int unclicked)
    {
        this.img_clicked = getResources().getDrawable(clicked);
        this.img_unclicked = getResources().getDrawable(unclicked);
    }

}
