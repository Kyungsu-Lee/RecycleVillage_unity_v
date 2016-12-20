package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import java.util.*;

public class NoticeButton extends TouchImageView{

    private Drawable img_activate;
    private Drawable img_deactivate;
    private boolean isActivated;
    static List<NoticeButton> arrayList = new ArrayList<>();
    public boolean canClick = true;

    NoticeButton(Context context, final int deactivate, int activate)
    {
        super(context);

        img_activate = getResources().getDrawable(activate);
        img_deactivate = getResources().getDrawable(deactivate);

        isActivated = false;

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN && canClick) {
                    if (isActivated) {
                        //  deactivate();
                    } else {
                        activate();
                    }
                }
                return false;
            }
        });

        this.setBackground(deactivate);
        arrayList.add(this);

    }

    public void activate()
    {
        for(int i = 0; i<arrayList.size();i++)
        {
            if(arrayList.get(i).isActivated)
            {
                arrayList.get(i).deactivate();
            }
        }

        this.setBackground(img_activate);

        isActivated = true;
    }

    public void deactivate()
    {
        this.setBackground(img_deactivate);

        isActivated = false;
    }

}
