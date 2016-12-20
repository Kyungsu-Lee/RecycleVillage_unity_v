package com.example.lmasi.recyclevillage;

/**
 * Created by Administrator on 2016-07-31.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Administrator on 2016-07-13.
 */
public class GateStuff extends GateGameView {

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


    Handler[] itemHandle;

    GateCharacter character;

    Runnable fRunnable;
    Handler fHandler;


    public GateStuff(Context context, List<View> viewList, RelativeLayout r)
    {

        super(context, viewList);




        main_Activity = context;
        this.main = r;

        Views = new Vector<View>();

        imgs = new Vector<Drawable>();
        super.setCanMove(true);





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

