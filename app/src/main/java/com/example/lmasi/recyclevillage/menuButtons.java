package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Vector;

public class menuButtons extends ClickButtonView {

    enum TYPE {TICKET, GUIDE, VILLAGE, ACCOUNT, RANK, NOTICE}

    private List<View> viewList = new Vector<View>();
    private RelativeLayout main;
    private Context context;

    private TYPE btn_type;

    menuButtons(Context context, int unclicked, int clicked, TYPE buttonType)
    {
        super(context,  unclicked, clicked);

        this.context = context;
        this.btn_type = buttonType;
    }


    public void clickEvent()
    {
        System.gc();

        switch (btn_type)
        {
            case TICKET: entry(); break;
            case GUIDE: guide(); break;
            case VILLAGE: village(); break;
            case ACCOUNT: account(); break;
            case RANK:rank(); break;
            case NOTICE: notice(); break;

        }

        for(int i=0; i<viewList.size(); i++)
            main.addView(viewList.get(i));
    }


    public void setMain(RelativeLayout r)
    {
        this.main = r;
    }

    private  void deleteAllViews()
    {
        for(int i=0; i<viewList.size(); i++)
            main.removeView(viewList.get(i));
    }

    private void account()
    {

        Intent intent = new Intent(context, MyAccount.class);
        context.startActivity(intent);
    }

    private void notice()
    {

        Intent intent = new Intent(context, Notice.class);
        context.startActivity(intent);
    }

    private void guide()
    {

        Intent intent = new Intent(context, Guide.class);
        context.startActivity(intent);
    }

    private void entry()
    {

        Intent intent = new Intent(context, Entry.class);
        context.startActivity(intent);
    }

    private void village()
    {

        Intent intent = new Intent(context, Village.class);
        context.startActivity(intent);
    }

    private void rank()
    {
        Intent intent = new Intent(context, Rank.class);
        context.startActivity(intent);
    }

}
