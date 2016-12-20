package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Vector;

public class ListRelativeLayout extends RelativeLayout {

    private List<View> viewList = new Vector<>();

    ListRelativeLayout(Context context)
    {
        super(context);
    }

    public ListRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewList(List<View> viewList)
    {
        this.viewList = viewList;
    }

    @Override
    public void removeView(View view)
    {
        this.viewList.remove(view);

        super.removeView(view);
    }
}