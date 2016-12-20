package com.example.lmasi.recyclevillage;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GuideLayoutView extends TouchImageView{

    private Drawable img_activate;
    private Drawable img_deactivate;
    private boolean isActivated;

    private int index;
    private GuideLayout guideLayout;

    static List<GuideLayoutView> arrayList = new ArrayList<>();

    GuideLayoutView(Context context, final int deactivate, int activate,int index)
    {
        super(context);
        setIndex(index);
        img_activate = getResources().getDrawable(activate);
        img_deactivate = getResources().getDrawable(deactivate);

        isActivated = false;

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    if (isActivated) {
                       //deactivate();
                    } else {
                        activate();
                    }
                }
                return false;
            }
        });

        this.setBackground(deactivate);
        arrayList.add(this);

    }

    public void activate()
    {
        for(int i = 0; i<arrayList.size();i++)
        {
            if(arrayList.get(i).isActivated)
            {
                arrayList.get(i).deactivate();
            }
        }

        this.setBackground(img_activate);
        guideLayout.setLayout(index);
        guideLayout.viewLayout();

        isActivated = true;
    }

    public void deactivate()
    {
        this.setBackground(img_deactivate);

        isActivated = false;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public void setGuideLayout(GuideLayout r){this.guideLayout = r;}

}
