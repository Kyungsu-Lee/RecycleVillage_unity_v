package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public abstract class TouchButton extends TouchImageView {

    boolean canClick = true;

    public TouchButton(Context context, int image)
    {
        super(context);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                    touchEvent();

                return true;
            }
        });

        setBackground(image);
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    public boolean getCanClick()
    {
        return canClick;
    }

    public abstract void touchEvent();

}
