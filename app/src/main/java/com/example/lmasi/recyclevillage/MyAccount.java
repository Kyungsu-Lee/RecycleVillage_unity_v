package com.example.lmasi.recyclevillage;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAccount extends Activity{

    public RelativeLayout main_R;
    public RelativeLayout phone;
    public RelativeLayout email;
    public RelativeLayout address;

    ButtonView[] edit_icon;
    onTextView[] textViews;
    EditText[] editTexts;

    onTextView month;
    onTextView total;


    RelativeLayout[] relativeLayouts;

    RelativeLayout.LayoutParams[] params;

    String[] attr;

    ButtonView icon;


    public ArrayList<Integer> entey_num = new ArrayList<>();

    View.OnClickListener touchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount);

        main_R = (RelativeLayout) findViewById(R.id.main_R);
        phone = (RelativeLayout) findViewById(R.id.phone);
        email = (RelativeLayout) findViewById(R.id.email);
        address = (RelativeLayout) findViewById(R.id.address);

        relativeLayouts = new RelativeLayout[3];

        relativeLayouts[0] = phone;
        relativeLayouts[1] = email;
        relativeLayouts[2] = address;

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
        main_R.addView(back);

        TouchImageView arrow = new TouchImageView(this);
        arrow.setSize(46 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_y());
        arrow.setLocation(64 * ScreenParameter.getScreenparam_x(), 51* ScreenParameter.getScreenparam_y());
        arrow.setBackground(R.drawable.entry_back_arrow);
        main_R.addView(arrow);

        edit_icon = new ButtonView[3];
        textViews = new onTextView[3];
        editTexts  =new EditText[3];
        params = new RelativeLayout.LayoutParams[3];

        attr = new String[3];
        attr[0] = "phone";
        attr[1] = "email";
        attr[2] = "addr";


        onTextView title = new onTextView(this);
        title.setText("내 정보");
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (60 * ScreenParameter.getScreenparam_y()));
        title.setTextColor(Color.rgb(255, 255, 255));
        title.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        title.setLocation(617 * ScreenParameter.getScreenparam_x(), 63 * ScreenParameter.getScreenparam_y());
        main_R.addView(title);

        TouchImageView profile = new TouchImageView(this);
        profile.setSize(270* ScreenParameter.getScreenparam_x(), 270* ScreenParameter.getScreenparam_y());
        profile.setLocation(583* ScreenParameter.getScreenparam_x(), 292* ScreenParameter.getScreenparam_y());
        profile.setBackground(R.drawable.profile_img);
        main_R.addView(profile);

        onTextView name = new onTextView(this);
        name.setText("우주소년");
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (55 * ScreenParameter.getScreenparam_y()));
        name.setTextColor(Color.rgb(255, 255, 255));
        name.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        name.setLocation(624 * ScreenParameter.getScreenparam_x(), 593* ScreenParameter.getScreenparam_y());
        main_R.addView(name);

        onTextView month_entry = new onTextView(this);
        month_entry.setText("이번달 응모수");
        month_entry.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40 * ScreenParameter.getScreenparam_y()));
        month_entry.setTextColor(Color.rgb(106, 106, 106));
        month_entry.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        month_entry.setLocation(277  * ScreenParameter.getScreenparam_x(), 787 * ScreenParameter.getScreenparam_y());
        main_R.addView(month_entry);

        Cursor c = DbResource.db.rawQuery("select * from idp;", null);

        while (c.moveToNext())
        {
            entey_num.add(c.getInt(c.getColumnIndex("entry_num")));
        }
        c.close();

        month = new onTextView(this);
        month.setText(Integer.toString((Resource.month)+entey_num.get(0)+entey_num.get(1)+entey_num.get(2)));
        month.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        month.setTextColor(Color.rgb(0, 255, 255));
        month.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        month.setLocation(343 * ScreenParameter.getScreenparam_x(), 714 * ScreenParameter.getScreenparam_y());
        main_R.addView(month);

        onTextView total_entry = new onTextView(this);
        total_entry.setText("총 응모수");
        total_entry.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40 * ScreenParameter.getScreenparam_y()));
        total_entry.setTextColor(Color.rgb(106, 106, 106));
        total_entry.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        total_entry.setLocation(643 * ScreenParameter.getScreenparam_x(), 787* ScreenParameter.getScreenparam_y());
        main_R.addView(total_entry);

        total = new onTextView(this);
        total.setText(Integer.toString((Resource.total)+entey_num.get(0)+entey_num.get(1)+entey_num.get(2)));
        total.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        total.setTextColor(Color.rgb(0, 255, 255));
        total.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        total.setLocation(660 * ScreenParameter.getScreenparam_x(), 714 * ScreenParameter.getScreenparam_y());
        main_R.addView(total);

        onTextView recycle_num = new onTextView(this);
        recycle_num.setText("분리수거 횟수");
        recycle_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (40 * ScreenParameter.getScreenparam_y()));
        recycle_num.setTextColor(Color.rgb(106, 106, 106));
        recycle_num.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        recycle_num.setLocation(942 * ScreenParameter.getScreenparam_x(), 787 * ScreenParameter.getScreenparam_y());
        main_R.addView(recycle_num);

        onTextView recycle = new onTextView(this);
        recycle.setText(Integer.toString(Resource.recycle));
        recycle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        recycle.setTextColor(Color.rgb(0, 255, 255));
        recycle.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        recycle.setLocation(1022 * ScreenParameter.getScreenparam_x(),714 * ScreenParameter.getScreenparam_y());
        main_R.addView(recycle);

        onTextView my_account = new onTextView(this);
        my_account.setText("개인정보");
        my_account.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        my_account.setTextColor(Color.rgb(0, 255, 255));
        my_account.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        my_account.setLocation(160 * ScreenParameter.getScreenparam_x(), 993 * ScreenParameter.getScreenparam_y());
        main_R.addView(my_account);

        TouchImageView border_line = new TouchImageView(this);
        border_line.setSize(2 * ScreenParameter.getScreenparam_x(), 36 * ScreenParameter.getScreenparam_y());
        border_line.setLocation(501 * ScreenParameter.getScreenparam_x(), 1002 * ScreenParameter.getScreenparam_y());
        border_line.setBackground(R.drawable.border);
        main_R.addView(border_line);

        onTextView friend = new onTextView(this);
        friend.setText("친구");
        friend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        friend.setTextColor(Color.rgb(106, 106, 106));
        friend.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        friend.setLocation(669 * ScreenParameter.getScreenparam_x(), 993 * ScreenParameter.getScreenparam_y());
        main_R.addView(friend);

        TouchImageView border_line2 = new TouchImageView(this);
        border_line2.setSize(2 * ScreenParameter.getScreenparam_x(), 36 * ScreenParameter.getScreenparam_y());
        border_line2.setLocation(931 * ScreenParameter.getScreenparam_x(), 1002 * ScreenParameter.getScreenparam_y());
        border_line2.setBackground(R.drawable.border);
        main_R.addView(border_line2);

        onTextView entry = new onTextView(this);
        entry.setText("응모통계");
        entry.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (50 * ScreenParameter.getScreenparam_y()));
        entry.setTextColor(Color.rgb(106, 106, 106));
        entry.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        entry.setLocation(1101* ScreenParameter.getScreenparam_x(), 993* ScreenParameter.getScreenparam_y());
        main_R.addView(entry);

        TouchImageView select_arrow = new TouchImageView(this);
        select_arrow.setSize(52 * ScreenParameter.getScreenparam_x(), 45* ScreenParameter.getScreenparam_y());
        select_arrow.setLocation(230* ScreenParameter.getScreenparam_x(), 0);
        select_arrow.setBackground(R.drawable.select_arr);
        phone.addView(select_arrow);

        TouchImageView phone_icon = new TouchImageView(this);
        phone_icon.setSize(40* ScreenParameter.getScreenparam_x(), 72* ScreenParameter.getScreenparam_y());
        phone_icon.setLocation(237* ScreenParameter.getScreenparam_x(), 77* ScreenParameter.getScreenparam_y());
        phone_icon.setBackground(R.drawable.phone_icon);
        phone.addView(phone_icon);

        TouchImageView email_icon = new TouchImageView(this);
        email_icon.setSize(53* ScreenParameter.getScreenparam_x(), 43* ScreenParameter.getScreenparam_y());
        email_icon.setLocation(230* ScreenParameter.getScreenparam_x(), 101* ScreenParameter.getScreenparam_y());
        email_icon.setBackground(R.drawable.email_icon);
        email.addView(email_icon);


        TouchImageView add_icon = new TouchImageView(this);
        add_icon.setSize(41* ScreenParameter.getScreenparam_x(), 62* ScreenParameter.getScreenparam_y());
        add_icon.setLocation(240* ScreenParameter.getScreenparam_x(), 101* ScreenParameter.getScreenparam_y());
        add_icon.setBackground(R.drawable.add_icon);
        address.addView(add_icon);

        icon = new ButtonView(this, R.drawable.account_edited, R.drawable.account_edit) {
            @Override
            public void ClickAction() {

                for(int index = 0; index < 3; index++)
                {
                    relativeLayouts[index].removeView(textViews[index]);
                    relativeLayouts[index].addView(editTexts[index]);
                    editTexts[index].setSingleLine();
                    editTexts[index].setText(textViews[index].getText());

                    editTexts[index].setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow( editTexts[0].getWindowToken(), 0);

                            return false;
                        }

                    });
                }
            }

            @Override
            public void UnClickAction() {

                for(int index = 0; index < 3; index++)
                {
                    if(!editTexts[index].getText().toString().equals("") && !editTexts[index].getText().toString().equals(null))
                        DbResource.db.execSQL("update account set " + attr[index] + " = '" + editTexts[index].getText() + "' where id='space';");

                    Cursor c = DbResource.db.rawQuery("select phone, email, addr from account where id='space';", null);
                    c.moveToNext();

                    textViews[index].setText(c.getString(index));
                    relativeLayouts[index].removeView(editTexts[index]);
                    relativeLayouts[index].addView(textViews[index]);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( editTexts[index].getWindowToken(), 0);
                }

            }
        };
        icon.setSize(88 * ScreenParameter.getScreenparam_x(), 88* ScreenParameter.getScreenparam_y());
        icon.setLocation(1280* ScreenParameter.getScreenparam_x(), 51* ScreenParameter.getScreenparam_y());
        icon.setBackground(R.drawable.account_edited);
        main_R.addView(icon);

        for(int i=0; i<3; i++)
        {
//            edit_icon[i] = new ButtonView(this, R.drawable.account_edited, R.drawable.account_edit) {
//                @Override
//                public void ClickAction() {
//
//                }
//
//                @Override
//                public void UnClickAction() {
//
//                }
//            };
            textViews[i] = new onTextView(this);
            editTexts[i] = new EditText(this);

            c = DbResource.db.rawQuery("select phone, email, addr from account", null);
            c.moveToNext();

            textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
            textViews[i].setTextColor(Color.rgb(209, 211, 212));
            textViews[i].setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
            textViews[i].setLocation(344* ScreenParameter.getScreenparam_x(), 100* ScreenParameter.getScreenparam_y());
            textViews[i].setText(c.getString(i));

            relativeLayouts[i].addView(textViews[i]);

//            edit_icon[i].setSize(88, 88);
//            edit_icon[i].setLocation(1250, 80);
//            edit_icon[i].setBackground(R.drawable.account_edited);
//            edit_icon[i].setOnClickListener(new View.OnClickListener() {
//
//                boolean active = false;
//
//                @Override
//                public void onClick(View v) {
//
//                    int index = v.getId();
//
//
//                    if (active)
//                    {
//                        if(!editTexts[index].getText().toString().equals("") && !editTexts[index].getText().toString().equals(null))
//                            DbResource.db.execSQL("update account set " + attr[index] + " = '" + editTexts[index].getText() + "' where id='space';");
//
//                        Cursor c = DbResource.db.rawQuery("select phone, email, addr from account where id='space';", null);
//                        c.moveToNext();
//
//                        textViews[index].setText(c.getString(index));
//                        relativeLayouts[index].removeView(editTexts[index]);
//                        relativeLayouts[index].addView(textViews[index]);
//
//                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow( editTexts[index].getWindowToken(), 0);
//
//                        active = false;
//
//                    } else {
//                        edit_icon[index].Click();
//                        relativeLayouts[index].removeView(textViews[index]);
//                        relativeLayouts[index].addView(editTexts[index]);
//                        editTexts[index].setSingleLine();
//                        editTexts[index].setText(textViews[index].getText());
//
//                        editTexts[index].setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                            @Override
//                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                                imm.hideSoftInputFromWindow( editTexts[0].getWindowToken(), 0);
//
//                                return false;
//                            }
//                        });
//
//                        Log.e("Messages", "asdasd");
//
//                        active=true;
//                    }
//                }
//
//            });
//            edit_icon[i].setId(i);
//            relativeLayouts[i].addView(edit_icon[i]);

            params[i] =  new RelativeLayout.LayoutParams(900, ViewGroup.LayoutParams.WRAP_CONTENT);
            params[i].setMargins((int) (310 * ScreenParameter.getScreenparam_x()), (int) (30 * ScreenParameter.getScreenparam_y()),0,0);
            editTexts[i].setLayoutParams(params[i]);

        }

    }
}