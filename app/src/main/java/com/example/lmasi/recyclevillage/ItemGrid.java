package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.view.View;

import java.util.List;

public class ItemGrid extends TouchImageView{

    public ItemGrid(Context context)
    {
        super(context);
        this.setSize((1132 * ScreenParameter.getScreenparam_x()) , 592 * ScreenParameter.getScreenparam_y());
        this.setLocation(140 * ScreenParameter.getScreenparam_x(), ScreenParameter.getScreenparam_y() * (782-85));
        this.setBackground(R.drawable.grid);
        this.setCanMove(true);
    }

    public ItemGrid(Context context, int index)
    {
        super(context);
        this.setSize(780 * ScreenParameter.getScreenparam_x(), 112 * ScreenParameter.getScreenparam_y());
        this.setLocation(350 * ScreenParameter.getScreenparam_x(), 575 * ScreenParameter.getScreenparam_y());
        this.setBackground(R.drawable.sky_grid);
        this.setCanMove(true);
    }

}
