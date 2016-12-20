package com.example.lmasi.recyclevillage;
import android.content.ClipData;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ItemView extends InfoView {

    enum TYPE {CANSET, SET, CANNOTSET};
    TYPE state;
    private boolean Isright;
    private List<GridPoint> gList = new Vector<GridPoint>();
    private int index;
    private Point size;
    private ItemBuyOX itemBuyO;
    private ItemBuyOX itemBuyX;
    private RelativeLayout main;

    private TouchImageView PointCheck;
    public GridPoint midPoint;

    Handler timer;

    Context activity;

    public ItemView(Context context,int index)
    {
        super(context);
        setIndex(index);
        activity = context;

        setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {


                if(state == TYPE.SET && MainActivity.semaphore){

                    DbResource.db.execSQL("delete from point where point_index = " + MainActivity.g_point.indexOf(midPoint) + ";");


                    main.removeView(who);

                    state = TYPE.CANSET;

                    MainActivity.grid = new ItemGrid(activity);
                    main.addView(MainActivity.grid);
                    MainActivity.sky_grid = new ItemGrid(activity, 1);
                    main.addView(MainActivity.sky_grid);

                    ItemView item = new ItemView(activity, getIndex());
                    item.setSize(getWidth(), getHeight());
                    item.setLocation(getX(), getY());
                    item.setMain(main);
                    main.addView(item);
                    itemLayout.viewList.add(item);

                    if(item.getIndex() == 13)
                        MainActivity.isNumHeli--;
                    else if(item.getIndex() ==14)
                        MainActivity.isNumSpace--;

                    for (int i = 0; i < itemLayout.viewList.size(); i++)
                        itemLayout.viewList.get(i).bringToFront();

                    ItemBuyOX itemBuyO = new ItemBuyOX(activity);
                    ItemBuyOX itemBuyX = new ItemBuyOX(activity);
                    itemBuyO.setbutton(0);
                    itemBuyX.setbutton(2);
                    itemBuyO.setSize(91 * ScreenParameter.getScreenparam_x(), 91 * ScreenParameter.getScreenparam_y());
                    itemBuyX.setSize(91 * ScreenParameter.getScreenparam_x(), 91 * ScreenParameter.getScreenparam_y());
                    itemBuyO.setLocation(-100 * ScreenParameter.getScreenparam_x(), -100 * ScreenParameter.getScreenparam_y());
                    itemBuyX.setLocation(-100 * ScreenParameter.getScreenparam_x(), -100 * ScreenParameter.getScreenparam_y());

                    itemBuyO.setItemView(item);
                    itemBuyX.setItemView(item);
                    item.setItemBuyO(itemBuyO);
                    item.setItemBuyX(itemBuyX);

                    MainActivity.character.bringToFront();



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

                    main.addView(itemBuyO);
                    main.addView(itemBuyX);

                    item.bringToFront();

                    MainActivity.semaphore = false;




                    DbResource.setMoney(DbResource.getMoney() + ObjectSizeLocation.price_item[getIndex()]);
                    DbResource.db.execSQL("update resources set numb = " + DbResource.getMoney() + " where attr = 'money';");
                    MainActivity.money.setText(Integer.toString(DbResource.getMoney()));

                    for (int i = 0; i < gList.size(); i++) {
                        gList.get(i).setCanPut(true);
                        gList.get(i).setMain(false);
                        gList.get(i).setItem_index(-1);
                    }


                    setBoundaryPoint();

                }
                return true;

            }
        });
    }


    public void setMain(RelativeLayout main)
    {
        this.main = main;
    }

    public RelativeLayout getMain()
    {
        return main;
    }

    public void setIndex(int index)
    {
        this.index = index;

        // float x = this.getX();
        //  float y = this.getY();

        setSize(ObjectSizeLocation.size_item_locate[index][0]* ScreenParameter.getScreenparam_x(),ObjectSizeLocation.size_item_locate[index][1]* ScreenParameter.getScreenparam_y());

        if(index==13)
            setLocation(350 * ScreenParameter.getScreenparam_x(),420 * ScreenParameter.getScreenparam_y());
        else if(index==14)
            setLocation(865 * ScreenParameter.getScreenparam_x(), 420 * ScreenParameter.getScreenparam_y() );
        else
            setLocation(ScreenParameter.getScreen_x()/2 - 100 * ScreenParameter.getScreenparam_x(), ScreenParameter.getScreen_y()/2 - 450 * ScreenParameter.getScreenparam_y());
        if(index==14)
            setBackground(R.drawable.can_item01_right+2 *index);
        else
            setBackground(R.drawable.can_item01_left+2 *index);


        size = ObjectSizeLocation.ITEM_ALLOCATED_SIZE[index];
    }

    public int getIndex()
    {
        return index;
    }

    public void setState(int state)
    {
        switch (state)
        {
            case 0 : this.state = TYPE.CANSET; break;
            case 1 : this.state = TYPE.SET; break;
            case 2 : this.state = TYPE.CANNOTSET; break;
        }
    }

    public int getState()
    {
        if(state == TYPE.CANSET)
            return 0;
        else if (state==TYPE.SET)
            return 1;
        else if (state==TYPE.CANNOTSET)
            return 2;
        else
            return -1;
    }

    public void setPoint(GridPoint gridPoint,boolean isright)
    {
        try {
            if (getIndex() == 0) {
                gridPoint = gridPoint.getLD().getLD().getLD();
            } else if (getIndex() == 1) {
                gridPoint = gridPoint.getLD().getLD().getLD().getLD().getRD();
            } else if (getIndex() == 2) {
                gridPoint = gridPoint.getLD().getLD().getLD().getLD().getRD();
            } else if (getIndex() == 3) {
                gridPoint = gridPoint.getLD().getLD().getLD().getLD().getRD();
            } else if (getIndex() == 4) {
                gridPoint = gridPoint.getLD().getLD().getLD().getLD();
            } else if (getIndex() == 5) {
                gridPoint = gridPoint.getLD().getLD();
            } else if (getIndex() == 6) {
                gridPoint = gridPoint.getLD().getLD();
            } else if (getIndex() == 7) {
                gridPoint = gridPoint.getLD().getLD().getLD().getRD();
            } else if (getIndex() == 8) {
                gridPoint = gridPoint.getLD().getLD().getLD().getRD();
            } else if (getIndex() == 9) {
                if (!isright)
                    gridPoint = gridPoint.getLU().getLD().getLU();
                else
                    gridPoint = gridPoint.getLD().getLD().getLU();
            } else if (getIndex() == 10) {
                gridPoint = gridPoint.getLD().getLD().getLD().getRD();
            } else if (getIndex() == 11) {
                gridPoint = gridPoint.getLD().getLD().getLU();
            } else if (getIndex() == 12) {
                if (!isright)
                    gridPoint = gridPoint.getLD().getLD().getLD();
                else
                    gridPoint = gridPoint.getLD().getLD().getLU();
            }
        }catch (Exception e)
        {
            Log.e("MAEESS", e.toString() + " //// " + gridPoint.x + " , " + gridPoint.y);
        }
        try {
            if (size.equals(1, 1))
                gList.add(gridPoint);
            else if (size.equals(2, 2)) {
                gList.add(gridPoint);
                gList.add(gridPoint.getRD());
                gList.add(gridPoint.getRU());
                gList.add(gridPoint.getRD().getRU());
            } else if (size.equals(3, 3)) {
                gList.add(gridPoint);
                gList.add(gridPoint.getRU());
                gList.add(gridPoint.getRU().getRU());
                gList.add(gridPoint.getRD());
                gList.add(gridPoint.getRD().getRU());
                gList.add(gridPoint.getRD().getRU().getRU());
                gList.add(gridPoint.getRD().getRD());
                gList.add(gridPoint.getRD().getRD().getRU());
                gList.add(gridPoint.getRD().getRD().getRU().getRU());
            } else if (size.equals(1, 2) && IsRight()) {
                if (getIndex()==9) {
                    gList.add(gridPoint);
                    gList.add(gridPoint.getRU());
                }
                else if(getIndex()==11)
                {
                    gList.add(gridPoint);
                    gList.add(gridPoint.getRD());
                }
            } else if (size.equals(1, 2) && !IsRight()) {
                if(getIndex()==9)
                {
                    gList.add(gridPoint);
                    gList.add(gridPoint.getRD());
                }
                else if (getIndex()==11)
                {
                    gList.add(gridPoint);
                    gList.add(gridPoint.getRU());
                }
            } else if (size.equals(1, 3) && IsRight()) {
                gList.add(gridPoint);
                gList.add(gridPoint.getRD());
                gList.add(gridPoint.getRD().getRD());
            } else if (size.equals(1, 3) && !IsRight()) {
                gList.add(gridPoint);
                gList.add(gridPoint.getRU());
                gList.add(gridPoint.getRU().getRU());
            }
        }catch (Exception e)
        {
            Log.d("Boundary Exception", e.toString());
//            Log.e("", gridPoint.x + ", " + gridPoint.y);
        }
        finally {
            gList.remove(null);
        }
    }

    public void ShowImage()
    {
        if (!IsRight())
        {
            if(state.equals(TYPE.CANSET))
            {
                setBackground(R.drawable.can_item01_right + 2 * index);
            }
            else if(state.equals(TYPE.SET))
            {
                setBackground(R.drawable.item_right_a + 2 *index);
            }
            else
            {
                setBackground(R.drawable.cannot_item01_right + 2 *index);
            }
        }

        else
        {
            if(state.equals(TYPE.CANSET))
            {
                setBackground(R.drawable.can_item01_left + 2 *index);
            }
            else if(state.equals(TYPE.SET))
            {
                setBackground(R.drawable.item_left_a + 2 *index);
            }
            else
            {
                setBackground(R.drawable.cannot_item01_left +2 * index);
            }
        }
    }

    public List<GridPoint> getPoint()
    {
        return gList;
    }

    public void setIsright(boolean condition)
    {
        Isright = condition;
    }
    public boolean IsRight() {return this.Isright;}


    public void ifpossible()    //can allocate
    {
        if(DbResource.getMoney() < money)
        {
            ifimpossible();
            return;
        }

        itemBuyX.setbutton(2);
        state = TYPE.CANSET;
        ShowImage();
    }

    public void ifimpossible()  //cannot allocate
    {
        itemBuyX.setbutton(1);
        state = TYPE.CANNOTSET;
        ShowImage();
    }

    public void setItemBuyO(ItemBuyOX itemBuyO)
    {
        this.itemBuyO = itemBuyO;
    }

    public ItemBuyOX getItemBuyO()
    {
        return itemBuyO;
    }

    public void setItemBuyX(ItemBuyOX itemBuyX)
    {
        this.itemBuyX = itemBuyX;
    }

    public ItemBuyOX getItemBuyX()
    {
        return itemBuyX;
    }

    @Override
    protected void setDrag()
    {
        this.setOnTouchListener(new View.OnTouchListener() {

            public float X;
            public float Y;


            public boolean mov = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (state != (TYPE.SET)) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        mov = true;

                        X = event.getRawX();
                        Y = event.getRawY();

                        setCanMove(false);
                    }

                    else if (event.getAction() == MotionEvent.ACTION_UP)
                    {
                        mov = false;
                        setCanMove(true);
                        return true;
                    }

                    else if (event.getAction() == MotionEvent.ACTION_MOVE && mov) {
                        float x = event.getRawX() - X;
                        float y = event.getRawY() - Y;

                        X = event.getRawX();
                        Y = event.getRawY();

                        if(getX()<screen_x/2 - who.getWidth()/2)
                        {
                            setIsright(true);
                        }
                        else
                        {
                            setIsright(false);
                        }

                        int density = 150;

                        if (index == 13 || index == 14) {
                            for (int i = 0; i < MainActivity.air_point.size(); i++) {
                                if (MainActivity.air_point.get(i).IsNear(new Point((int) X, (int) Y), density *2, density)) {
                                    setSize((ObjectSizeLocation.size_item_locate[index][0]) * ScreenParameter.getScreenparam_x(), (ObjectSizeLocation.size_item_locate[index][1]) * ScreenParameter.getScreenparam_y());

                                    if(MainActivity.air_point.get(0).IsNear(new Point((int) X, (int) Y), density *2, density))
                                         setLocation(350 * ScreenParameter.getScreenparam_x(),420 * ScreenParameter.getScreenparam_y());
                                    else if (MainActivity.air_point.get(1).IsNear(new Point((int) X, (int) Y), density *2, density))
                                        setLocation(865 * ScreenParameter.getScreenparam_x(), 420 * ScreenParameter.getScreenparam_y() );
                                        //setLocation(((MainActivity.air_point.get(i).x)), (MainActivity.air_point.get(i).y));

                                    itemBuyO.setSize((itemBuyO.getWidth()), (itemBuyO.getHeight()));
                                    itemBuyO.setLocation((getWIDTH() / 2 + getLX() - itemBuyO.getWIDTH() - itemBuyX.getWIDTH() / 2), (getLY() + getHEIGHT()));
                                    itemBuyX.setSize((itemBuyX.getWidth()), (itemBuyX.getHeight()));
                                    itemBuyX.setLocation((getWIDTH() / 2 + getLX() + itemBuyX.getWIDTH() / 2), (getLY() + getHEIGHT()));

                                    if((index == 13 && MainActivity.isNumHeli < 2) || (index == 14 && MainActivity.isNumSpace < 2))
                                        ifpossible();
                                    else
                                        ifimpossible();
                                }
                            }
                        }
                        else {
                            for (int i = 0; i < MainActivity.g_point.size(); i++)
                                if (MainActivity.g_point.get(i).IsNear(new Point((int) X, (int) Y), density, density / 10)) {

                                    midPoint = MainActivity.g_point.get(i);

                                    setSize((ObjectSizeLocation.size_item_locate[index][0]) * ScreenParameter.getScreenparam_x(), (ObjectSizeLocation.size_item_locate[index][1]) * ScreenParameter.getScreenparam_y());

                                    if (IsRight())
                                        setLocation((Math.abs(MainActivity.g_point.get(i).x) - ObjectSizeLocation.pivot_position_left[index].x * ScreenParameter.getScreenparam_x()), (MainActivity.g_point.get(i).y - ObjectSizeLocation.pivot_position_left[index].y * ScreenParameter.getScreenparam_y()));
                                    else
                                        setLocation(Math.abs(MainActivity.g_point.get(i).x) - ObjectSizeLocation.pivot_position_left[index].x * ScreenParameter.getScreenparam_x(), MainActivity.g_point.get(i).y - ObjectSizeLocation.pivot_position_left[index].y * ScreenParameter.getScreenparam_y());

                                    itemBuyO.setSize((itemBuyO.getWidth()), (itemBuyO.getHeight()));
                                    itemBuyO.setLocation((getWIDTH() / 2 + getLX() - itemBuyO.getWIDTH() - itemBuyX.getWIDTH() / 2), (getLY() + getHEIGHT()));
                                    itemBuyX.setSize((itemBuyX.getWidth()), (itemBuyX.getHeight()));
                                    itemBuyX.setLocation((getWIDTH() / 2 + getLX() + itemBuyX.getWIDTH() / 2), (getLY() + getHEIGHT()));

                                    gList.clear();

                                    setPoint(MainActivity.g_point.get(i), IsRight());


                                    boolean check = true;

                                    for (int j = 0; j < gList.size(); j++) {
                                        check &= gList.get(j).CanPut();
                                    }

                                    if (check)
                                        ifpossible();
                                    else
                                        ifimpossible();
                                }
                        }
                    }
                }
                return false;
            }

        });
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

    public void setBoundaryPoint()
    {

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

}
