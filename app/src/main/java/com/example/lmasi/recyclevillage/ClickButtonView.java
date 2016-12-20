package com.example.lmasi.recyclevillage;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class ClickButtonView extends TouchImageView {

    public Drawable img_clicked;
    public  Drawable img_unclicked;

    boolean canClick = true;
    protected boolean Isclicked = false;

    protected ArrayList<Boolean> conditions = new ArrayList<>();

    public ClickButtonView(Context context, int unclicked, int clicked) {
        super(context);
        this.img_clicked = getResources().getDrawable(clicked);
        this.img_unclicked = getResources().getDrawable(unclicked);

        super.setBackground(getResources().getDrawable(unclicked));



        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                TouchEvent(motionEvent);

                return true;
            }
        });

        conditions.add(canClick);

    }

    private void TouchEvent(MotionEvent motionEvent)
    {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            TouchDown();
        if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            Click();
            clickEvent();
        }
    }

    private void TouchDown()
    {
        super.setBackground(img_clicked);
        this.invalidate();
    }

    private void Click()
    {
        super.setBackground(img_unclicked);
        this.invalidate();
    }

    public void setClicked(boolean condition) { this.Isclicked = condition;}
    public boolean IsClicked(){return this.Isclicked;}

    public abstract void clickEvent();

    public void addCondition(boolean condition){
        conditions.add(condition);
    }

    public void setImages(int clicked, int unclicked)
    {
        this.img_clicked = getResources().getDrawable(clicked);
        this.img_unclicked = getResources().getDrawable(unclicked);
    }

}
