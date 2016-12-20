package com.example.lmasi.recyclevillage;

/**
 * Created by Administrator on 2016-07-21.
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Vector;

public class GateButtonView extends GateGameView {

    public   Drawable img_clicked;
    public  Drawable img_unclicked;

    protected boolean Isclicked = false;


    public GateButtonView(Context context, List<View> viewList, int unclicked)
    {
        super(context,viewList);

        this.img_unclicked = getResources().getDrawable(unclicked);

        super.setBackground(getResources().getDrawable(unclicked));

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    TouchedEvent();

                return true;
            }
        });
    }

    public void setClicked(boolean condition) { this.Isclicked = condition;}
    public boolean IsClicked(){return this.Isclicked;}

    public void TouchedEvent() //must override this.
    {
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
        setClicked(true);
      //  super.setBackground(img_clicked);
        this.invalidate();
        GateRunnerActivity.canNotice = false;
    }

    public void UnClick()
    {
        setClicked(false);
        super.setBackground(img_unclicked);
        this.invalidate();
        GateRunnerActivity.canNotice = true;
    }


}