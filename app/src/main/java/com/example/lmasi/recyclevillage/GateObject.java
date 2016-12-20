package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Administrator on 2016-07-13.
 */
public class GateObject extends GateGameView {

    public boolean plus;
    public boolean d;
    public boolean life;
    public boolean attack;
    public boolean gen;

    private List<View> Views;

    static  public List<Drawable> imgs;

    Random random = new Random();
    Random iRandom = new Random();

    Context main_Activity;
    RelativeLayout main;

    private int item_no = 7;
    private int ran;
    private int index;
    private int dr;

    GateObject[] items;
    GateStuff[] lives;
    GateOnTextView coin;

    Handler[] itemHandle;

    GateCharacter character;

    Runnable fRunnable;
    Handler fHandler;


    public GateObject(Context context, List<View> viewList, RelativeLayout r,GateCharacter character, GateStuff[] life, GateOnTextView coin)
    {

        super(context, viewList);



        this.character = character;
        main_Activity = context;
        this.main = r;

        Views = new Vector<View>();

        imgs = new Vector<Drawable>();
        super.setCanMove(true);

        itemHandle= new Handler[3*item_no];

        lives = new GateStuff[3];

        for(int i=0;i<3;i++)
        {
            lives[i] = life[i];
        }

        this.coin = coin;



    }


    public void setItems(int item)
    {
        super.setBackground( getResources().getDrawable(item));
    }

    public boolean isPlus(boolean b)
    {
        this.plus = b;
        return plus;
    }

    public boolean getPlus()
    {
        return this.plus;
    }


    public void setD(boolean a)
    {
        this.d = a;
    }
    public boolean getD()
    {
        return this.d;
    }

    public void setLife(boolean a){this.life = a; }
    public boolean getLife(){return this.life;}

    public void setAffect(boolean a){this.attack = a;}
    public boolean getAffect(){return this.attack;}

    public void setGen(boolean a){this.gen=a;}
    public boolean getGen(){return this.gen;}




}

