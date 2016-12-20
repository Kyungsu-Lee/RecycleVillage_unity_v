package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemBuyOX extends ClickButtonView{

    enum TYPE {BUY, LOCK, CANCLE}
    TYPE button;

    private ItemView itemView;

    public ItemBuyOX(Context context)
    {
        super(context,R.drawable.item_o,R.drawable.item_o);
    }

    public void setItemView(ItemView itemView)
    {
        this.itemView = itemView;
    }

    public ItemView getItemView()
    {
        return itemView;
    }

    public void setbutton(int button)
    {
        switch (button)
        {
            case 0 :
            {
                this.button = TYPE.CANCLE;
                setImages(R.drawable.item_x,R.drawable.item_x_clicked);
                setBackground(R.drawable.item_x);
                canClick = true;
                break;
            }
            case 1 :
            {
                this.button = TYPE.LOCK;
                setImages(R.drawable.item_o_lock, R.drawable.item_o_lock);
                setBackground(R.drawable.item_o_lock);
                canClick = false;
                break;
            }
            case 2 :
            {
                this.button = TYPE.BUY;
                setImages(R.drawable.item_o,R.drawable.item_o_clicked);
                setBackground(R.drawable.item_o);
                canClick = true;
                break;
            }

        }

    }

    public int getbutton()
    {
        if(button == TYPE.BUY)
            return 0;
        else if(button == TYPE.LOCK)
            return 1;
        else if(button == TYPE.CANCLE)
            return 2;
        else
            return -1;
    }

    @Override
    public void clickEvent()
    {
        if (button == TYPE.BUY) {
            //itemView.getMain().removeView(itemView);

            itemView.getMain().removeView(itemView.getItemBuyO());
            itemView.getMain().removeView(itemView.getItemBuyX());
            if (itemView.IsRight())
                itemView.setBackground(R.drawable.item_right_a + itemView.getIndex());
            else
                itemView.setBackground(R.drawable.item_left_a + itemView.getIndex());

            DbResource.setMoney(DbResource.getMoney()-ObjectSizeLocation.price_item[itemView.getIndex()]);
            DbResource.db.execSQL("update resources set numb = " + DbResource.getMoney() + " where attr = 'money';");
            MainActivity.money.setText(Integer.toString(DbResource.getMoney()));

            itemView.getMain().removeView(MainActivity.grid);
            itemView.getMain().removeView(MainActivity.sky_grid);
            itemView.setCanDrag(false);
            itemView.setState(1);
            if(itemView.getIndex()==13||itemView.getIndex()==14) {
                MainActivity.semaphore = true;

                for(int i=0; i<3; i++)
                    MainActivity.item[i].canClick = true;

                MainActivity.menu.canClick = true;


                if(itemView.getIndex()==13) {
                    MainActivity.isNumHeli++;
                    MainActivity.ischeck.setText(Integer.toString(MainActivity.isNumHeli));
                }
                else if(itemView.getIndex()==14) {
                    MainActivity.isNumSpace++;
                    MainActivity.ischeck.setText(Integer.toString(MainActivity.isNumSpace));
                }

                return;

            }
            itemView.getPoint().get(0).setMain(true);

            for (int i = 0; i < itemView.getPoint().size(); i++) {
                itemView.getPoint().get(i).setItem_index(itemView.getIndex());
                itemView.getPoint().get(i).setCanPut(false);
                itemView.getPoint().get(i).setOnitem(itemView);
                itemView.getPoint().get(i).setOnitem(true);

            }

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

int p_index =  MainActivity.g_point.indexOf(itemView.midPoint);
            int right = itemView.IsRight() ? 1 : 0;

            try {
                DbResource.db.execSQL("insert into point values (" + p_index +" , " + itemView.getIndex() + " , " + right +");");
            }
            catch (Exception e)
            {
                Log.e("SQLEXCEPTIONNN", e.toString());
                DbResource.db.execSQL("update point set item_index=" + itemView.getIndex() +" where point_index = " + p_index + ";");

            }
            finally {

            }


        } else if(button == TYPE.CANCLE){
            itemView.getMain().removeView(itemView);
            itemView.getMain().removeView(itemView.getItemBuyO());
            itemView.getMain().removeView(itemView.getItemBuyX());
            itemView.getMain().removeView(MainActivity.grid);
            itemView.getMain().removeView(MainActivity.sky_grid);
        }
        MainActivity.semaphore = true;


        for(int i=0; i<3; i++)
            MainActivity.item[i].canClick = true;

        MainActivity.menu.canClick = true;


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
}
