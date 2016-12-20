package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Vector;

public abstract class MiniGameButton extends ButtonView {

    private boolean canStart = false;

    public MiniGameButton(Context context, int unclicked, int clicked)
    {
        super(context, unclicked, clicked);

        Isclicked = false;

    }

    public abstract void startGame();

}
