package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Entry extends Activity {

    RelativeLayout main;
    TouchImageView failFrame;
    TouchImageView successFrame;
    ClickButtonView entry;
    onTextView total_entry_num;

    onTextView goods_name;
    onTextView now_ticket;
    RelativeLayout[] relativeLayouts;
    TouchImageView[] image;

    HalfScrollView slideView;

    String[] product_name = new String[6];

    TouchImageView info;
    ClickButtonView ok;
    onTextView what_entry;
    onTextView what_entry_info;
    onTextView how_entry;
    onTextView how_entry_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

        main = (RelativeLayout) findViewById(R.id.main_R);

        TouchImageView back = new TouchImageView(this);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                finish();

                return false;
            }
        });
        back.setSize(110* ScreenParameter.getScreenparam_x(), 156 * ScreenParameter.getScreenparam_y());
        back.setLocation(44 * ScreenParameter.getScreenparam_x(), 31* ScreenParameter.getScreenparam_y());
        main.addView(back);

        TouchImageView arrow = new TouchImageView(this);
        arrow.setSize(46 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_y());
        arrow.setLocation(64 * ScreenParameter.getScreenparam_x(), 51* ScreenParameter.getScreenparam_y());
        arrow.setBackground(R.drawable.entry_back_arrow);
        main.addView(arrow);

        product_name[0] = "휴지이";
        product_name[1] = "생수";
        product_name[2] = "액체 세제";
        product_name[3] = "샴푸 및 컨디셔너";
        product_name[4] = "쓰레기 봉투 10L";
        product_name[5] = "ALL 세탁 세제";

        onTextView title = new onTextView(this);
        title.setText("응모");
        title.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);  /////  => 이거 써주면 getmeasuredWidth/ Height 쓸 수 있음.
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (60* ScreenParameter.getScreenparam_y()));
        title.setTextColor(Color.rgb(255, 255, 255));
        title.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        //  title.setLocation(668* ScreenParameter.getScreenparam_x(), 63* ScreenParameter.getScreenparam_y());
        title.setLocation(ScreenParameter.getScreen_x()/2 - title.getMeasuredWidth()/2, 63 * ScreenParameter.getScreenparam_y());   // getMeasuredWidth, Height 쓰면 크기 미리 받아올 수 있음

        main.addView(title);

        TouchImageView entry_account = new TouchImageView(this);
        entry_account.setSize(331* ScreenParameter.getScreenparam_x(), 94* ScreenParameter.getScreenparam_y());
        entry_account.setLocation(1059* ScreenParameter.getScreenparam_x(), 157* ScreenParameter.getScreenparam_y());
        entry_account.setBackground(R.drawable.entry_myaccount);
        main.addView(entry_account);

        now_ticket = new onTextView(this);
        now_ticket.setText(Integer.toString(DbResource.getTicket()));
        now_ticket.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        now_ticket.setTextColor(Color.rgb(0, 255, 255));
        now_ticket.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        if(DbResource.getTicket()<10)
            now_ticket.setLocation(1330* ScreenParameter.getScreenparam_x(), 183* ScreenParameter.getScreenparam_y());
        else if(DbResource.getTicket()<100)
            now_ticket.setLocation(1318* ScreenParameter.getScreenparam_x(), 183* ScreenParameter.getScreenparam_y());
        else {
            now_ticket.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
            now_ticket.setLocation(1307 * ScreenParameter.getScreenparam_x(), 187 * ScreenParameter.getScreenparam_y());
        }
        main.addView(now_ticket);

        Cursor c = DbResource.db.rawQuery("select * from idp;", null);
        c.moveToNext();

        Log.e("ASDASD", Integer.toString(c.getCount()));

        onTextView my_entey = new onTextView(this);
        my_entey.setText("내 응모수 : ");
        my_entey.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        my_entey.setTextColor(Color.rgb(0, 255, 255));
        my_entey.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        my_entey.setLocation(444* ScreenParameter.getScreenparam_x(), 554* ScreenParameter.getScreenparam_y());
        main.addView(my_entey);


        goods_name = new onTextView(this);
        goods_name.setText(c.getString(c.getColumnIndex("product_name")));
        goods_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (55* ScreenParameter.getScreenparam_y()));
        goods_name.setTextColor(Color.rgb(255, 255, 255));
        goods_name.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        goods_name.setLocation(670* ScreenParameter.getScreenparam_x(), 439* ScreenParameter.getScreenparam_y());
        main.addView(goods_name);

        final onTextView my_entey_num = new onTextView(this);
        my_entey_num.setText(c.getString(c.getColumnIndex("entry_num")));
        my_entey_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        my_entey_num.setTextColor(Color.rgb(0, 255, 255));
        my_entey_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        my_entey_num.setLocation(644* ScreenParameter.getScreenparam_x(), 554* ScreenParameter.getScreenparam_y());
        main.addView(my_entey_num);

        final onTextView total_entry = new onTextView(this);
        total_entry.setText("전체 응모수 : ");
        total_entry.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        total_entry.setTextColor(Color.rgb(106, 106, 106));
        total_entry.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        total_entry.setLocation(766* ScreenParameter.getScreenparam_x(), 554* ScreenParameter.getScreenparam_y());
        main.addView(total_entry);


        final RelativeLayout A = new RelativeLayout(this);
        A.setBackgroundColor(Color.argb(210,0,0,0));
        RelativeLayout.LayoutParams a = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        A.setLayoutParams(a);
        A.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        entry = new ClickButtonView(this, R.drawable.entry_btn_unclick, R.drawable.entry_btn_click) {
            @Override
            public void clickEvent() {
                if(DbResource.getTicket()<1)
                {
                    Handler handler = new Handler()
                    {
                        int i = 0;

                        public void handleMessage(Message msg)
                        {
                            if(i == 1)
                            {
                                main.removeView(failFrame);
                                main.removeView(A);

                            }
                            i++;
                            sendEmptyMessageDelayed(0,700);
                        }
                    };
                    handler.sendEmptyMessage(0);
                    main.addView(A);
                    main.addView(failFrame);


                }
                else
                {
                    Cursor c = DbResource.db.rawQuery("select * from idp where product_name = '" + product_name[slideView.getIndex()] + "';", null);
                    c.moveToNext();
                    int num = c.getInt(c.getColumnIndex("entry_num")) + 1;
                    DbResource.db.execSQL("update idp set entry_num='" + Integer.toString(num) + "' where product_name = '"+ product_name[slideView.getIndex()] +"';");
                    c.close();
                    c = DbResource.db.rawQuery("select * from idp where product_name = '" + product_name[slideView.getIndex()] + "';", null);
                    c.moveToNext();
                    my_entey_num.setText(c.getString(c.getColumnIndex("entry_num")));
                    total_entry_num.setText(Integer.toString(c.getInt(c.getColumnIndex("entry_num"))+Resource.tEntryNum[slideView.getIndex()]));

                    Handler handler = new Handler()
                    {
                        int i = 0;

                        public void handleMessage(Message msg)
                        {
                            if(i == 1) {
                                main.removeView(successFrame);
                                main.removeView(A);
                            }

                            i++;

                            sendEmptyMessageDelayed(0,700);
                        }
                    };

                    handler.sendEmptyMessage(0);
                    main.addView(A);
                    main.addView(successFrame);
                    DbResource.setTicket(DbResource.getTicket()-1);
                    DbResource.db.execSQL("update resources set numb = "+ DbResource.getTicket() + " where attr = 'ticket';");
                    MainActivity.ticket.setText(Integer.toString(DbResource.getTicket()));
                    now_ticket.setText(Integer.toString(DbResource.getTicket()));
                    if(DbResource.getTicket()<10)
                        now_ticket.setLocation(1330* ScreenParameter.getScreenparam_x(), 183* ScreenParameter.getScreenparam_y());
                    else if(DbResource.getTicket()<100)
                        now_ticket.setLocation(1318* ScreenParameter.getScreenparam_x(), 183* ScreenParameter.getScreenparam_y());
                    else {
                        now_ticket.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
                        now_ticket.setLocation(1307 * ScreenParameter.getScreenparam_x(), 187 * ScreenParameter.getScreenparam_y());
                    }
                }
            }
        };

        entry.setSize(253* ScreenParameter.getScreenparam_x(),86* ScreenParameter.getScreenparam_y());
        entry.setLocation(597* ScreenParameter.getScreenparam_x(), 1747* ScreenParameter.getScreenparam_y());
        main.addView(entry);

        failFrame = new TouchImageView(this);
        failFrame.setSize(1002 * ScreenParameter.getScreenparam_x(),391 * ScreenParameter.getScreenparam_y());
        failFrame.setLocation(ScreenParameter.getScreenparam_x()*213,ScreenParameter.getScreenparam_y()*1000);
        failFrame.setBackground(R.drawable.entry_fail);

        successFrame = new TouchImageView(this);
        successFrame.setSize(1002 * ScreenParameter.getScreenparam_x(),391 * ScreenParameter.getScreenparam_y());
        successFrame.setLocation(ScreenParameter.getScreenparam_x()*213,ScreenParameter.getScreenparam_y()*1000);
        successFrame.setBackground(R.drawable.entry_ok);

        c = DbResource.db.rawQuery("select * from product;", null);
        c.moveToNext();

        int defaulttime = c.getInt(c.getColumnIndex("timer"));


        final onTextView time = new onTextView(this);
        time.setText("남은 시간 : ");
        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        time.setTextColor(Color.rgb(106, 106, 106));
        time.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        time.setLocation(424 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(time);


        final onTextView time_num_day = new onTextView(this);
        time_num_day.setText(Integer.toString(defaulttime/ 86400));
        time_num_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        time_num_day.setTextColor(Color.rgb(106, 106, 106));
        time_num_day.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        time_num_day.setLocation(625 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(time_num_day);

        final onTextView time_day = new onTextView(this);
        time_day.setText("일");
        time_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        time_day.setTextColor(Color.rgb(106, 106, 106));
        time_day.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        time_day.setLocation(670 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(time_day);

        final onTextView time_num_hour = new onTextView(this);
        time_num_hour.setText(Integer.toString(defaulttime%86400/3600));
        time_num_hour.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        time_num_hour.setTextColor(Color.rgb(106, 106, 106));
        time_num_hour.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        time_num_hour.setLocation(725 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(time_num_hour);

        final onTextView time_hour = new onTextView(this);
        time_hour.setText("시");
        time_hour.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        time_hour.setTextColor(Color.rgb(106, 106, 106));
        time_hour.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        time_hour.setLocation(770 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(time_hour);

        final onTextView time_num_min = new onTextView(this);
        time_num_min.setText(Integer.toString(defaulttime%3600/60));
        time_num_min.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        time_num_min.setTextColor(Color.rgb(106, 106, 106));
        time_num_min.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        time_num_min.setLocation(825 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(time_num_min);

        final onTextView time_min = new onTextView(this);
        time_min.setText("분");
        time_min.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        time_min.setTextColor(Color.rgb(106, 106, 106));
        time_min.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        time_min.setLocation(870 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(time_min);

        final onTextView tiem_num_sec = new onTextView(this);
        tiem_num_sec.setText(Integer.toString(defaulttime%60));
        tiem_num_sec.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        tiem_num_sec.setTextColor(Color.rgb(106, 106, 106));
        tiem_num_sec.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        tiem_num_sec.setLocation(925 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(tiem_num_sec);

        final onTextView tiem_sec = new onTextView(this);
        tiem_sec.setText("초");
        tiem_sec.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        tiem_sec.setTextColor(Color.rgb(106, 106, 106));
        tiem_sec.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        tiem_sec.setLocation(970 * ScreenParameter.getScreenparam_x(), 1869 * ScreenParameter.getScreenparam_y());
        main.addView(tiem_sec);

        onTextView text = new onTextView(this);
        text.setText("이번 달 분리수거 누적량은");
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        text.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        text.setTextColor(Color.rgb(255, 255, 255));
        text.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        text.setLocation(363 * ScreenParameter.getScreenparam_x(), 1968 * ScreenParameter.getScreenparam_y());
        main.addView(text);

        onTextView text2 = new onTextView(this);
        text2.setText("57");
        text2.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        text2.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        text2.setTextColor(Color.rgb(0, 255, 255));
        text2.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        text2.setLocation((388 * ScreenParameter.getScreenparam_x() + text.getMeasuredWidth()), 1968 * ScreenParameter.getScreenparam_y());
        main.addView(text2);

        onTextView text3 = new onTextView(this);
        text3.setText("회 입니다");
        text3.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        text3.setTextColor(Color.rgb(255, 255, 255));
        text3.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        text3.setLocation((388 * ScreenParameter.getScreenparam_x() + text.getMeasuredWidth() + text2.getMeasuredWidth()), 1968 * ScreenParameter.getScreenparam_y());
        main.addView(text3);

        what_entry = new onTextView(this);
        what_entry.setText("응모권이란?");
        what_entry.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (55* ScreenParameter.getScreenparam_y()));
        what_entry.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        what_entry.setTextColor(Color.rgb(0, 255, 255));
        what_entry.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        what_entry.setLocation(193 * ScreenParameter.getScreenparam_x(), 850 * ScreenParameter.getScreenparam_y());

        what_entry_info = new onTextView(this);
        what_entry_info.setText("상품을 응모할 수 있는 쿠폰으로 응모 할 시 1개의 응모권이\n소멸됩니다. 응모권은 누적이 가능하며 소진 시 더 이상 응모\n할 수 없게 됩니다.");
        what_entry_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        what_entry_info.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        what_entry_info.setTextColor(Color.rgb(255, 255, 255));
        what_entry_info.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        what_entry_info.setLocation(193 * ScreenParameter.getScreenparam_x(), 920 * ScreenParameter.getScreenparam_y());

        how_entry = new onTextView(this);
        how_entry.setText("응모권은 어떻게 얻나요?");
        how_entry.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (55* ScreenParameter.getScreenparam_y()));
        how_entry.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        how_entry.setTextColor(Color.rgb(0, 255, 255));
        how_entry.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        how_entry.setLocation(193 * ScreenParameter.getScreenparam_x(), 1220 * ScreenParameter.getScreenparam_y());

        how_entry_info = new onTextView(this);
        how_entry_info.setText("응모권은 2가지의 방법으로 얻을 수 있습니다. NFC가 부착된\n쓰레기통에 분리수거 시 1일 1회 태깅을 통해 응모권을 획득\n할 수 있습니다. 혹은 어플 내 매일 주어지는 미션을 달성하면\n최대 1일 3회 까지 응모권을 획득 할 수 있습니다.");
        how_entry_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        how_entry_info.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        how_entry_info.setTextColor(Color.rgb(255, 255, 255));
        how_entry_info.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        how_entry_info.setLocation(193 * ScreenParameter.getScreenparam_x(), 1290 * ScreenParameter.getScreenparam_y());


        ok = new ClickButtonView(this, R.drawable.check_un, R.drawable.check_click) {
            @Override
            public void clickEvent() {
                main.removeView(info);
                main.removeView(ok);
                main.removeView(what_entry);
                main.removeView(what_entry_info);
                main.removeView(how_entry);
                main.removeView(how_entry_info);
                main.removeView(A);
            }
        };
        ok.setSize(253 * ScreenParameter.getScreenparam_x(), 87 * ScreenParameter.getScreenparam_y());
        ok.setLocation((1440-170-256) * ScreenParameter.getScreenparam_x(), (2540-85-768-40) * ScreenParameter.getScreenparam_y());

        info = new TouchImageView(this);
        info.setSize(1221 * ScreenParameter.getScreenparam_x(),1239* ScreenParameter.getScreenparam_y());
        info.setLocation(118 * ScreenParameter.getScreenparam_x(), (648-85) * ScreenParameter.getScreenparam_y());
        info.setBackground(R.drawable.menu);

        TouchImageView what = new TouchImageView(this);
        what.setSize(821 * ScreenParameter.getScreenparam_x(), 43 * ScreenParameter.getScreenparam_y());
        what.setLocation(553 * ScreenParameter.getScreenparam_x(), 2341 * ScreenParameter.getScreenparam_y());
        what.setBackground(R.drawable.entry_what);
        main.addView(what);
        what.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                main.addView(A);
                main.addView(info);
                main.addView(ok);
                main.addView(what_entry);
                main.addView(what_entry_info);
                main.addView(how_entry);
                main.addView(how_entry_info);
                return false;
            }
        });

        relativeLayouts = new RelativeLayout[6];
        image = new TouchImageView[6];

        Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                Cursor c = DbResource.db.rawQuery("select * from product where name = '" + product_name[slideView.getIndex()] + "';", null);
                c.moveToNext();
                //int defaulttime = c.getInt(c.getColumnIndex("timer"));
                int defaulttime = Integer.parseInt(c.getString(1));
                //time.setText( defaulttime/ 86400 + "일" + (defaulttime%86400)/3600 + "시" + (defaulttime%3600)/60 + "분" + defaulttime%60 + "초");
                time.setText("남은 시간 : ");
                time_num_day.setText(Integer.toString(defaulttime/ 86400));
                time_day.setText("일");
                time_num_hour.setText(Integer.toString(defaulttime%86400/3600));
                time_hour.setText("시");
                time_num_min.setText(Integer.toString(defaulttime%3600/60));
                time_min.setText("분");
                tiem_num_sec.setText(Integer.toString(defaulttime%60));
                tiem_sec.setText("초");
                c.close();

                sendEmptyMessageDelayed(0,1000);
            }
        };
        handler.sendEmptyMessage(0);

        for (int i=0;i<6;i++)
        {
            relativeLayouts[i]= new RelativeLayout(this);
            image[i] = new TouchImageView(this);
        }
        slideView = new HalfScrollView(this) {
            @Override
            public void BeforeAction() {

                Cursor c = DbResource.db.rawQuery("select * from idp where product_name = '" + product_name[getIndex()] + "';", null);
                c.moveToNext();
                goods_name.setText(c.getString(c.getColumnIndex("product_name")));
                my_entey_num.setText(c.getString(c.getColumnIndex("entry_num")));
                now_ticket.setText(Integer.toString(DbResource.getTicket()));
                if(DbResource.getTicket()<10)
                    now_ticket.setLocation(1330* ScreenParameter.getScreenparam_x(), 183* ScreenParameter.getScreenparam_y());
                else if(DbResource.getTicket()<100)
                    now_ticket.setLocation(1318* ScreenParameter.getScreenparam_x(), 183* ScreenParameter.getScreenparam_y());
                else {
                    now_ticket.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
                    now_ticket.setLocation(1307 * ScreenParameter.getScreenparam_x(), 187 * ScreenParameter.getScreenparam_y());
                }
                total_entry_num.setText(Integer.toString(c.getInt(c.getColumnIndex("entry_num"))+Resource.tEntryNum[slideView.getIndex()]));
                c.close();


                try {
                    c = DbResource.db.rawQuery("select * from product where name = '" + product_name[getIndex()] + "';", null);
                    c.moveToNext();
                    //int defaulttime = c.getInt(c.getColumnIndex("timer"));
                    int defaulttime = Integer.parseInt(c.getString(1));
                    //time.setText( defaulttime/ 86400 + "일" + (defaulttime%86400)/3600 + "시" + (defaulttime%3600)/60 + "분" + defaulttime%60 + "초");
                    time.setText("남은 시간 : ");
                    time_num_day.setText(Integer.toString(defaulttime/ 86400));
                    time_day.setText("일");
                    time_num_hour.setText(Integer.toString(defaulttime%86400/3600));
                    time_hour.setText("시");
                    time_num_min.setText(Integer.toString(defaulttime%3600/60));
                    time_min.setText("분");
                    tiem_num_sec.setText(Integer.toString(defaulttime%60));
                    tiem_sec.setText("초");
                    if(slideView.getIndex()==0)
                        goods_name.setLocation(670* ScreenParameter.getScreenparam_x(), 439* ScreenParameter.getScreenparam_y());
                    else if(slideView.getIndex()==1)
                        goods_name.setLocation(670* ScreenParameter.getScreenparam_x(), 439* ScreenParameter.getScreenparam_y());
                    else if(slideView.getIndex()==2)
                        goods_name.setLocation(615* ScreenParameter.getScreenparam_x(), 439* ScreenParameter.getScreenparam_y());
                    else if(slideView.getIndex()==3)
                        goods_name.setLocation(555* ScreenParameter.getScreenparam_x(), 439* ScreenParameter.getScreenparam_y());
                    else if(slideView.getIndex()==4)
                        goods_name.setLocation(555* ScreenParameter.getScreenparam_x(), 439* ScreenParameter.getScreenparam_y());
                    else if(slideView.getIndex()==5)
                        goods_name.setLocation(555* ScreenParameter.getScreenparam_x(), 439* ScreenParameter.getScreenparam_y());
                    goods_name.setText(c.getString(c.getColumnIndex("product_name")));

                }
                catch (Exception e)
                {
                    Log.e("SQLMESSAGE", e.toString());
                }
                finally {
                    c.close();
                }

            }

            @Override
            public void AfterAction() {

            }

            @Override
            public void onDrag() {

            }

        };

        slideView.setSize((int) (1440 * ScreenParameter.getScreenparam_x()), (int) (1025* ScreenParameter.getScreenparam_y()));
        slideView.setLocation(0,650* ScreenParameter.getScreenparam_y());
        main.addView(slideView);
        slideView.startView();

        for(int i = 0; i<6; i++)
        {
            slideView.addCircleView(relativeLayouts[i]);
        }

        for (int i = 0; i<6 ; i++)
        {
            image[i].setSize(1019* ScreenParameter.getScreenparam_x(), 1025* ScreenParameter.getScreenparam_y());
            //image[i].setLocation(200* ScreenParameter.getScreenparam_x(),0);
            image[i].setBackground(R.drawable.e_item_1 + i);
            image[i].setScaleType(ImageView.ScaleType.FIT_XY);
            relativeLayouts[i].addView(image[i]);
        }


        c = DbResource.db.rawQuery("select * from idp where product_name = '" + product_name[slideView.getIndex()] + "';", null);
        c.moveToNext();


        total_entry_num = new onTextView(this);
        total_entry_num.setText(Integer.toString(c.getInt(c.getColumnIndex("entry_num"))+Resource.tEntryNum[slideView.getIndex()]));
        total_entry_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40* ScreenParameter.getScreenparam_y()));
        total_entry_num.setTextColor(Color.rgb(106, 106, 106));
        total_entry_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        total_entry_num.setLocation(995* ScreenParameter.getScreenparam_x(), 554* ScreenParameter.getScreenparam_y());
        main.addView(total_entry_num);

        c.close();
    }
}