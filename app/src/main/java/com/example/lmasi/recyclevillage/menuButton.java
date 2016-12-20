package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Vector;

public class menuButton extends ButtonView {

    List<View> viewList = new Vector<View>();
    RelativeLayout main;
    Context context;

    menuButton(Context context, int unclicked, int clicked)
    {
        super(context,unclicked, clicked);

        this.context = context;

        setViews();
    }

    public void setMain(RelativeLayout relativeLayout)
    {
        this.main = relativeLayout;
    }

    private void setViews()
    {


        RelativeLayout A = new RelativeLayout(context);
        A.setBackgroundColor(Color.argb(210,0,0,0));
        RelativeLayout.LayoutParams a = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        A.setLayoutParams(a);
        A.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                UnClick();

                return true;
            }
        });
        viewList.add(A);


        TouchImageView base = new TouchImageView(context);
        base.setSize(1002 * screenparam_x, 901 * screenparam_y);
        base.setLocation(220 * screenparam_x, 772 * screenparam_y);
        base.setBackground(R.drawable.base);
        viewList.add(base);

        menuButtons ticket = new menuButtons(context, R.drawable.main_1, R.drawable.main_1_click, menuButtons.TYPE.TICKET);
        ticket.setSize(160 * screenparam_x, 222 * screenparam_y);
        ticket.setLocation(319 * screenparam_x, 1046 * screenparam_y);
        ticket.setMain(main);
        viewList.add(ticket);

        menuButtons guide = new menuButtons(context, R.drawable.main_2, R.drawable.main_2_click, menuButtons.TYPE.GUIDE);
        guide.setSize(160 * screenparam_x, 222 * screenparam_y);
        guide.setLocation(645 * screenparam_x, 1046 * screenparam_y);
        guide.setMain(main);
        viewList.add(guide);

        menuButtons village = new menuButtons(context, R.drawable.main_3, R.drawable.main_3_click, menuButtons.TYPE.VILLAGE);
        village.setSize(160 * screenparam_x, 222 * screenparam_y);
        village.setLocation(971 * screenparam_x,1046 * screenparam_y);
        village.setMain(main);
        viewList.add(village);

        menuButtons account = new menuButtons(context, R.drawable.main_4, R.drawable.main_4_click, menuButtons.TYPE.ACCOUNT);
        account.setSize(160 * screenparam_x, 222 * screenparam_y);
        account.setLocation(319 * screenparam_x, 1363 * screenparam_y);
        account.setMain(main);
        viewList.add(account);

        menuButtons rank = new menuButtons(context, R.drawable.main_5, R.drawable.main_5_click, menuButtons.TYPE.RANK);
        rank.setSize(160 * screenparam_x, 222 * screenparam_y);
        rank.setLocation(645 * screenparam_x, 1363 * screenparam_y);
        rank.setMain(main);
        viewList.add(rank);

        menuButtons notice = new menuButtons(context, R.drawable.main_6, R.drawable.main_6_click, menuButtons.TYPE.NOTICE);
        notice.setSize(160 * screenparam_x, 222 * screenparam_y);
        notice.setLocation(971 * screenparam_x, 1363 * screenparam_y);
        notice.setMain(main);
        viewList.add(notice);

        ClickButtonView entry = new ClickButtonView(context, R.drawable.main_7, R.drawable.main_7_click) {
            @Override
            public void clickEvent() {

            }
        };
        entry.setSize(100*screenparam_x, 149* screenparam_y);
        entry.setLocation(966 * screenparam_x,1727*screenparam_y );
        entry.setBackground(R.drawable.main_7);
        viewList.add(entry);

        ClickButtonView setting = new ClickButtonView(context, R.drawable.main_8, R.drawable.main_8_click) {
            @Override
            public void clickEvent() {

            }
        };
        setting.setSize(90*screenparam_x, 149* screenparam_y);
        setting.setLocation(1130 * screenparam_x,1727*screenparam_y );
        setting.setBackground(R.drawable.main_8);
        viewList.add(setting);

        TouchImageView exit = new TouchImageView(context);
        exit.setSize(127*screenparam_x,127*screenparam_y);
        exit.setLocation(1132*screenparam_x,823*screenparam_y);
        exit.setBackground(R.drawable.main_x);
        exit.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                UnClick();
                return true;
            }
        });
        viewList.add(exit);
    }

    @Override
    public void ClickAction()
    {


        for(int i=0; i<3; i++)
            if(MainActivity.item[i].IsClicked())
                MainActivity.item[i].UnClick();

        for(int i=0; i<viewList.size(); i++)
            main.addView(viewList.get(i));

        MainActivity.canNotice = false;
        MainActivity.NOTICE = false;
    }

    @Override
    public void UnClickAction()
    {

        for(int i=0; i<viewList.size(); i++)
            main.removeView(viewList.get(i));


        MainActivity.canNotice = true;
        MainActivity.NOTICE = true;
    }
}
