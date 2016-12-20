package com.example.lmasi.recyclevillage;

import android.content.ClipData;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

public class ItemImageView extends TouchButton{

    private int index;
    private itemLayout itemLayout;

    public ItemImageView(Context context,int index)
    {
        super(context,R.drawable.smallitem_01+index);
        setIndex(index);
    }

    public void setIndex(int index)
    {
        this.index = index;

        setBackground(R.drawable.smallitem_01+index);

    }

    public int getIndex()
    {
        return index;
    }

    public void setItemLayout(itemLayout r){this.itemLayout = r;}

    @Override
    public void touchEvent()
    {
        itemLayout.setitem(index);
        itemLayout.viewitem();
    }

}
