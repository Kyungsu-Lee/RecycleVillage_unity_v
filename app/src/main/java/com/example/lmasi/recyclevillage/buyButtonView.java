package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public abstract class buyButtonView extends ClickButtonView {

    C_STATE characterState = C_STATE.LOCK;

    protected ArrayList<Boolean> conditions = new ArrayList<>();

    int[] btn_imgs;

    enum btn_state {buy_unclick(0), buy_click(1), buy_lock(2), change_click(3), change_unclick(4), main_character(5);

        private final int value;
        btn_state(int value){this.value = value;}
        public int getValue(){return this.value;}
    }


    public buyButtonView(Context context, int unclicked, int clicked) {
        super(context, unclicked, clicked);

        int[] drawables = {R.drawable.buy_unclick, R.drawable.buy_click, R.drawable.buy_x, R.drawable.change_unclick, R.drawable.change_click, R.drawable.main_character};

        btn_imgs = drawables;

        addCondition(characterState != C_STATE.LOCK);
        addCondition(characterState != C_STATE.MAIN);
    }




    public void setCharacterState(C_STATE state)
    {
        this.characterState = state;

        if(state == C_STATE.BUY)
        {
            img_clicked = getResources().getDrawable(R.drawable.change_click);
            img_unclicked = getResources().getDrawable(R.drawable.change_unclick);
        }
        else if(state == C_STATE.MAIN)
        {
            img_clicked = getResources().getDrawable(R.drawable.main_character);
            img_unclicked = getResources().getDrawable(R.drawable.main_character);
        }

        else if(state == C_STATE.LOCK)
        {
            img_clicked = getResources().getDrawable(R.drawable.buy_x);
            img_unclicked = getResources().getDrawable(R.drawable.buy_x);
        }

        else
        {
            img_clicked = getResources().getDrawable(R.drawable.buy_click);
            img_unclicked = getResources().getDrawable(R.drawable.buy_unclick);
        }
    }

    public C_STATE getCharacterState() {return this.characterState;}



    public void setImages(btn_state state)
    {
        setBackground(btn_imgs[state.getValue()]);
        this.invalidate();
    }

    public void addCondition(boolean condition){
        conditions.add(condition);
    }

}
