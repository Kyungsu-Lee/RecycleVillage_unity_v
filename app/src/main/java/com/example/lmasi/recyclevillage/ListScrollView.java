package com.example.lmasi.recyclevillage;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class ListScrollView extends ScrollView{

    private RelativeLayout layout;
    private ArrayList<RankCard> rankcardViews = new ArrayList<>();
    private ArrayList<CardView> cardViews = new ArrayList<>();
    private RelativeLayout.LayoutParams params;
    private View who;
    private int speed;

    ListScrollView(Context context)
    {
        super(context);

        layout = new RelativeLayout(context);
        this.addView(layout);

        params = new RelativeLayout.LayoutParams(0,0);

        this.setVerticalScrollBarEnabled(false);

        who = this;

        speed = 2000;

    }

    public int getSpeed() {
        return speed;
    }



    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getLayoutHeight(){return  params.height;}

    public int getCount()
    {
        return cardViews.size();
    }
    public int getRankCount()
    {
        return rankcardViews.size();
    }

    public void addCard(CardView cardView)
    {
        cardViews.add(cardView);
        //showCard();
    }

    public void addRankCard(RankCard rankCard)
    {
        rankcardViews.add(rankCard);
        //showCard();
    }

    public void deleteCard(CardView cardView)
    {
        cardViews.remove(cardView);
    }

    public void showCard()
    {
        params.width = 1201;
        params.height = 340*getCount();

        for(int i = 0; i<getCount(); i++)
        {
            cardViews.get(i).setSize(1201* ScreenParameter.getScreenparam_x(),310* ScreenParameter.getScreenparam_y());
            cardViews.get(i).setLocation(0,(30+340*i)* ScreenParameter.getScreenparam_y());
            layout.addView(cardViews.get(i));
            cardViews.get(i).setBackground(R.drawable.notice_bg_1+i%2);
        }

    }

    public void showRankCard()
    {
        params.width = 1362;
        params.height = 301*getRankCount();

        for(int i = 0; i<getRankCount(); i++)
        {
            rankcardViews.get(i).setSize(1362* ScreenParameter.getScreenparam_x(),303* ScreenParameter.getScreenparam_y());
            rankcardViews.get(i).setLocation(32* ScreenParameter.getScreenparam_x(),(303*i)* ScreenParameter.getScreenparam_y());
            layout.addView(rankcardViews.get(i));
        }

    }

    public void scrollAt(int n)
    {
        final int index = n;

        this.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofInt(who,"scrollY", 340* (index) - (int)(getHeight() * 0.65) ).setDuration(speed).start();
            }
        });
    }

    public  void scrollTop()
    {
        this.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofInt(who,"scrollY", 0).setDuration(speed).start();
            }
        });
    }

    public  void scrollBottom()
    {
        this.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofInt(who,"scrollY", ((ListScrollView)who).getLayoutHeight()).setDuration(speed).start();
            }
        });
    }
}