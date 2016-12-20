package com.example.lmasi.recyclevillage;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;



public class itemLayout extends RelativeLayout{

    private TouchImageView mainImage;
    private onTextView title;
    private onTextView info;
    private TouchImageView skill;
    private TouchImageView price;
    private onTextView money;
    private ClickButtonView buy;
    private RelativeLayout main;
    private int index;

    private RelativeLayout.LayoutParams params;
    private int WIDTH;
    private int HEIGHT;
    private int LX;
    private int LY;

    Context activity;

    static List<View> viewList = new ArrayList<>();


    public int setWidth(int width)
    {
        this.WIDTH = width;
        return width;
    }

    public int setHeight(int height)
    {
        this.HEIGHT = height;
        return height;
    }

    public int getWIDTH() {return this.WIDTH;}
    public int getHEIGHT() {return this.HEIGHT;}

    private void setsize(double width, double height)
    {
        setWidth((int)width);
        setHeight((int)height);
    }

    public void setSize(double width, double height)
    {
        params = new RelativeLayout.LayoutParams((int)width, (int)height);
        this.setLayoutParams(params);
        setsize(width, height);
    }


    public void setLX(double x){this.LX = (int)x;}
    public void setLY(double y){this.LY = (int)y;}

    public int getLX(){return this.LX;}
    public int getLY(){return this.LY;}

    public void setLocation(double x, double y)
    {
        if(params != null)
            params.setMargins((int)x,(int)y,0,0);

        this.LX = (int)x;
        this.LY = (int)y;
    }

    public void setMain(RelativeLayout main)
    {
        this.main = main;
    }

    public RelativeLayout getMain()
    {
        return main;
    }

    public void setMainImage(int index)
    {
        mainImage.setBackground(R.drawable.edit_item_01 + index);
    }
    public void setTitle(int index)
    {
        title.setText(ObjectSizeLocation.name_item[index]);
    }
    public void setInfo(int index)
    {
        info.setText(ObjectSizeLocation.info_item[index]);
    }
    public void setSkill(int index)
    {
        skill.setBackground(R.drawable.edit_info_01+index);
        skill.setSize((ObjectSizeLocation.size_skill[index][0])* ScreenParameter.getScreenparam_x(), (ObjectSizeLocation.size_skill[index][1])* ScreenParameter.getScreenparam_x());
        skill.setLocation(580 * ScreenParameter.getScreenparam_x(), (400-85) * ScreenParameter.getScreenparam_y());
    }
    public void setMoney(int index)
    {
        money.setText(Integer.toString(ObjectSizeLocation.price_item[index]));
    }

    public itemLayout(Context context){
        super(context);

        activity = context;

        mainImage = new TouchImageView(context);
        mainImage.setSize(507 * ScreenParameter.getScreenparam_x(),550 * ScreenParameter.getScreenparam_y());
        mainImage.setLocation(40 * ScreenParameter.getScreenparam_x(),(80-85) * ScreenParameter.getScreenparam_y());
        this.addView(mainImage);

        title = new onTextView(context);
        title.setLocation(ScreenParameter.getScreenparam_x() * 580, ScreenParameter.getScreenparam_y() * (140-85));
        title.setTypeface(Typeface.createFromAsset(context.getAssets(), "GodoB.ttf"));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (85* ScreenParameter.getScreenparam_y()));
        title.setTextColor(Color.rgb(0, 255, 255));
        this.addView(title);

        info = new onTextView(context);
        info.setLocation(ScreenParameter.getScreenparam_x()* 580, ScreenParameter.getScreenparam_y() * (250-85));
        info.setTypeface(Typeface.createFromAsset(context.getAssets(), "KoPubDotumMedium_0.ttf"));
        info.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (36* ScreenParameter.getScreenparam_y()));
        info.setLineSpacing(5.0f, 1.0f);
        info.setTextColor(Color.WHITE);
        this.addView(info);

        skill = new TouchImageView(context);
        skill.setSize(350 * ScreenParameter.getScreenparam_x(), 90 * ScreenParameter.getScreenparam_y());
        skill.setLocation(580 * ScreenParameter.getScreenparam_x(), (400-85) * ScreenParameter.getScreenparam_y());
        this.addView(skill);

        price = new TouchImageView(context);
        price.setSize(53 * ScreenParameter.getScreenparam_x(), 89 * ScreenParameter.getScreenparam_y());
        price.setLocation((610+50) * ScreenParameter.getScreenparam_x(), (624-85) * ScreenParameter.getScreenparam_y());
        price.setBackground(R.drawable.price);
        this.addView(price);

        money = new onTextView(context);
        money.setLocation(ScreenParameter.getScreenparam_x() * (670+50), ScreenParameter.getScreenparam_y() * (635-85));
        money.setTypeface(Typeface.createFromAsset(context.getAssets(), "GodoM.ttf"));
        money.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (58* ScreenParameter.getScreenparam_y()));
        money.setTextColor(Color.rgb(245, 175, 1));
        this.addView(money);

        buy = new ClickButtonView(context, R.drawable.buy_unclick, R.drawable.buy_click) {
            @Override
            public void clickEvent() {
                buy();
            }
        };
        buy.setSize((int) (ScreenParameter.getScreenparam_x() * ObjectSizeLocation.width_buy), (int) (ScreenParameter.getScreenparam_x() * ObjectSizeLocation.height_buy));
        buy.setLocation(ScreenParameter.getScreenparam_x() * ObjectSizeLocation.Location_buy.x -120 * ScreenParameter.getScreenparam_x(), ScreenParameter.getScreenparam_y() * ObjectSizeLocation.Location_buy.y-800 * ScreenParameter.getScreenparam_y());
        this.addView(buy);
    }

    public void setitem(int index)
    {
        this.index = index;
    }

    public void viewitem()
    {
        setMainImage(index);
        setTitle(index);
        setInfo(index);
        setSkill(index);
        setMoney(index);
    }

    public void buy()
    {
        MainActivity.item[0].UnClick();

        MainActivity.menu.canClick = false;
        MainActivity.canNotice = false;


        MainActivity.grid = new ItemGrid(activity);
        main.addView(MainActivity.grid);
        MainActivity.sky_grid = new ItemGrid(activity, 1);
        main.addView((MainActivity.sky_grid));

        ItemView item = new ItemView(activity, index);
        item.setMain(main);
        main.addView(item);
        viewList.add(item);

        ItemBuyOX itemBuyO = new ItemBuyOX(activity);
        ItemBuyOX itemBuyX = new ItemBuyOX(activity);
        itemBuyO.setbutton(0);
        itemBuyX.setbutton(1);
        itemBuyO.setSize(91*ScreenParameter.getScreenparam_x(), 91*ScreenParameter.getScreenparam_y());
        itemBuyX.setSize(91*ScreenParameter.getScreenparam_x(), 91*ScreenParameter.getScreenparam_y());
        if(index==13)
        {
            if(MainActivity.isNumHeli<2) {
                itemBuyO.setbutton(0);
                itemBuyX.setbutton(2);
                itemBuyO.setLocation((277 / 2 + 350 - itemBuyO.getWIDTH() - itemBuyX.getWIDTH() / 2) * ScreenParameter.getScreenparam_x(), (420 + 271) * ScreenParameter.getScreenparam_x());
                itemBuyX.setLocation((277 / 2 + 350 + itemBuyX.getWIDTH() / 2) * ScreenParameter.getScreenparam_x(), (420 + 271) * ScreenParameter.getScreenparam_x());
            }
            else
            {
                itemBuyO.setbutton(0);
                itemBuyX.setbutton(1);
                itemBuyO.setLocation((277 / 2 + 350 - itemBuyO.getWIDTH() - itemBuyX.getWIDTH() / 2) * ScreenParameter.getScreenparam_x(), (420 + 271) * ScreenParameter.getScreenparam_x());
                itemBuyX.setLocation((277 / 2 + 350 + itemBuyX.getWIDTH() / 2) * ScreenParameter.getScreenparam_x(), (420 + 271) * ScreenParameter.getScreenparam_x());
            }

        }
        else if(index==14)
        {
            if(MainActivity.isNumSpace<2)
            {
                itemBuyO.setbutton(0);
                itemBuyX.setbutton(2);
                itemBuyO.setLocation((277/2+875-itemBuyO.getWIDTH()-itemBuyX.getWIDTH()/2)* ScreenParameter.getScreenparam_x(),(420+271)* ScreenParameter.getScreenparam_x());
                itemBuyX.setLocation((277/2+875+itemBuyX.getWIDTH()/2)* ScreenParameter.getScreenparam_x(),(420+271)* ScreenParameter.getScreenparam_x());
            }
            else
            {
                itemBuyO.setbutton(0);
                itemBuyX.setbutton(1);
                itemBuyO.setLocation((277/2+875-itemBuyO.getWIDTH()-itemBuyX.getWIDTH()/2)* ScreenParameter.getScreenparam_x(),(420+271)* ScreenParameter.getScreenparam_x());
                itemBuyX.setLocation((277/2+875+itemBuyX.getWIDTH()/2)* ScreenParameter.getScreenparam_x(),(420+271)* ScreenParameter.getScreenparam_x());
            }

        }
        else
        {
            itemBuyO.setLocation((ObjectSizeLocation.size_item[index][0] / 2 + 620 - 91 - 91 / 2) * ScreenParameter.getScreenparam_x(), ((830 + ObjectSizeLocation.size_item[index][1]) * ScreenParameter.getScreenparam_y()));
            itemBuyX.setLocation((ObjectSizeLocation.size_item[index][0] / 2 + 620 + 91 / 2) * ScreenParameter.getScreenparam_x(), ((830 + ObjectSizeLocation.size_item[index][1]) * ScreenParameter.getScreenparam_y()));

        }
        main.addView(itemBuyO);
        main.addView(itemBuyX);


        item.setItemBuyO(itemBuyO);
        item.setItemBuyX(itemBuyX);
        itemBuyO.setItemView(item);
        itemBuyX.setItemView(item);

        for(int i=0; i<viewList.size(); i++)
            viewList.get(i).bringToFront();

        MainActivity.character.bringToFront();
        MainActivity.semaphore = false;

        ArrayList<GridPoint> grids = new ArrayList<>();

        try {
            for(int i=0; i<MainActivity.g_point.size(); i++)
                //if(MainActivity.g_point.get(i).getPriority() > itemView.getPoint().get(0).getPriority())
                grids.add(MainActivity.g_point.get(i));
        }catch (Exception e)
        {
            Log.e("Boundary Exception", e.toString());
        }

        Collections.sort(grids, new GridCompare());

        Collections.reverse(grids);

        for(int i=0; i<grids.size(); i++)
            if(grids.get(i).getOnItem() != null)
                grids.get(i).getOnItem().bringToFront();

        grids.clear();

        itemBuyO.bringToFront();
        itemBuyX.bringToFront();
        item.bringToFront();
/*
        for(int i=0;i<MainActivity.g_point.size(); i++)
        {
            MainActivity.g_point.get(i).setCanGo(true);
            if(!MainActivity.g_point.get(i).OnItem())
                MainActivity.g_point.get(i).setCanPut(true);
        }
        */

        List<GridPoint> pointList = new ArrayList<>();

        for(int i =0; i<MainActivity.g_point.size(); i++)
            MainActivity.g_point.get(i).setCanGo(true);

        if(index==0) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 12; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 12; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==1)
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 13; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 0; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==2)
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 14; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 0; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 13; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==3)
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 0; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==4)
        {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 13; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 12; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 11; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==5)
        {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==6)
        {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==7)
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 12; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==8)
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 12; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==9)
        {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==10)
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 12; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[3].length * 3 + i));
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[4].length * 4 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 19; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 18; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==11)
        {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 14; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 8; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 4; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }
        else if(index==12)
        {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 23; j++)
                    pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j));
            }
            for (int i = 0; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[5].length * 5 + i));
            for (int i = 0; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[6].length * 6 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[7].length * 7 + i));
            for (int i = 0; i < 7; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[8].length * 8 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[9].length * 9 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 17; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[10].length * 10 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[11].length * 11 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 16; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 0; i < 5; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 15; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 0; i < 6; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 13; i < 23; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanGo(false);
        }

        pointList.clear();

        if(index==0) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }

        else if (index==1) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 2; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==2) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 2; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==3) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 2; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==4) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 2; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==5) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==6) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==7) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==8) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==9) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==10) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[16].length * 16 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }
        else if (index==11) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }

        else if (index==12) {

            for (int i = 7; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[12].length * 12 + i));
            for (int i = 6; i < 11; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[13].length * 13 + i));
            for (int i = 5; i < 10; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[14].length * 14 + i));
            for (int i = 4; i < 9; i++)
                pointList.add(MainActivity.g_point.get(ObjectSizeLocation.GRID_POINT[15].length * 15 + i));

            for (int i = 0; i < pointList.size(); i++)
                pointList.get(i).setCanPut(false);
        }

    }



    public void func()
    {
        android.os.Handler hd = new android.os.Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);



            }
        };

        hd.sendEmptyMessage(100);
    }

    static class GridCompare implements Comparator<GridPoint>
    {

        @Override
        public int compare(GridPoint gridPoint, GridPoint t1)
        {
            if(gridPoint.getPriority() > t1.getPriority())
                return 1;
            else if(gridPoint.getPriority() == t1.getPriority())
                return 0;
            else
                return -1;
        }
    }



    public void setScaleAnim(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (385-120  * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-800  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.8));

    }

    public void showAnim(int speed)
    {
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.25f, Animation.RELATIVE_TO_SELF, 1);
        this.startAnimation(anim);
        anim.setDuration(speed);

    }


}
