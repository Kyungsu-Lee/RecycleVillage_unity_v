package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Vector;


public class Character extends InfoView {

    static final int num_character = 9;

    public List<Drawable> imgs;
    public List<Drawable> unselected_img;

    private boolean Islocked = true;

    private int index;

    enum STATUS  {LOCK, BUY, NONE, MAIN};

    public STATUS character_status = STATUS.LOCK;

    public Character(Context context)
    {
        super(context);

        imgs = new Vector<Drawable>();
        unselected_img = new Vector<Drawable>();

        if(imgs != null && unselected_img != null)
            for(int i=0; i<num_character; i++)
            {
                imgs.add(getResources().getDrawable(R.drawable.character_0 + i));
                unselected_img.add(getResources().getDrawable(R.drawable.unselected_character_0 + i));
            }

        this.setBackground(imgs.get(0));

        super.setCanMove(true);

    }


    public void selectCharacter(int id, STATUS state)
    {

        if(state == STATUS.BUY || state ==STATUS.MAIN)
        {
            this.setBackground(imgs.get(id));
        }

        else
        {
            this.setBackground(unselected_img.get(id));
        }

        setScaleType(ImageView.ScaleType.FIT_XY);

        this.index = id;
        this.character_status = state;
    }

    public void setLock(boolean condition) {Islocked = condition;}
    public boolean IsLocked() {return this.Islocked;}


    public int getIndex(){return index;}


}
