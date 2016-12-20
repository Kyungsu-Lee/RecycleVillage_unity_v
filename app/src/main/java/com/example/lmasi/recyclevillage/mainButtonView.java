package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.theantranch.UnityPlayerActivity;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.parseColor;

public class mainButtonView extends ButtonView {

    private List<View> Views;
    // RelativeLayout main;
    //  Context main_Activity;

    int index;
    //TouchImageView imgView,info;
    //static List<mainButtonView> lists = new Vector<mainButtonView>();
    static int itemno = 0;
    static int j = 0;

    static List<mainButtonView> lists = new Vector<mainButtonView>();

    ////variable
    private int character_index = 0;
    Character characters[];
    ////variable
    TouchImageView imgView;
    TouchImageView character_info;
    buyButtonView buy;
    TouchImageView buy_popup;
    TouchImageView buy_ok;
    TouchImageView buy_x;
    TouchImageView buy_success;
    TouchImageView buy_fail;
    MiniGameButton sea,gate,moving;
    RelativeLayout relativeLayout;
    onTextView info;
    onTextView price_t;
    TouchImageView skill;
    TouchImageView price_star;
    TouchImageView heart;
    onTextView title;
    TouchImageView[] frame;


    RelativeLayout main;
    Context main_Activity;
    TouchButton btn, xbtn;
    TouchImageView arrow ;

    TouchImageView grid;
    TouchImageView fly1;
    TouchImageView fly2;
    static ButtonView item_o;
    static ButtonView item_x;

    int game_index = -1;

    MiniGameButton[] miniGameButtons = new MiniGameButton[3];
    ClickButtonView start;

    int item_index = 0;


    List<View> located_item = new Vector<>();

    int anim_speed = 100;

    int index_main_character = 0;


    mainButtonView(Context context, int unclicked, int clicked, int in)
    {
        super(context, unclicked, clicked);

        main_Activity = context;

        Views = new Vector<View>();

        lists.add(this);


        index = in;
        /*  0 => Item
            1 => Character
            2 => MINIGAME
        */

        character_index = 0;

        xbtn = new TouchButton(main_Activity, R.drawable.x) {
            @Override
            public void touchEvent() {
                main.removeView(xbtn);
                UnClick();
                //UnClickAction();
            }
        };

        frame = new TouchImageView[3];

        characters = new Character[Character.num_character];

        for(int i=0; i<Character.num_character; i++) {
            characters[i] = new Character(main_Activity);
            characters[i].selectCharacter(i, Character.STATUS.NONE);
        }


/*
        characters[0].selectCharacter(0, Character.STATUS.MAIN);
        characters[3].selectCharacter(3, Character.STATUS.NONE);
        characters[5].selectCharacter(5, Character.STATUS.NONE);
        characters[7].selectCharacter(7, Character.STATUS.NONE);
        */

        try {
            Cursor c = DbResource.db.rawQuery("select * from c_table", null);

            int i=0;

            while (c.moveToNext())
            {
                if(c.getInt(2) == 1)
                {
                    characters[i].selectCharacter(i, Character.STATUS.BUY);
                }

                else
                {
                    characters[i].selectCharacter(i, Character.STATUS.NONE);
                }

                i++;
            }

            c.close();

            c = DbResource.db.rawQuery("select * from character",null);
            c.moveToNext();

            characters[c.getInt(1)].selectCharacter(c.getInt(1), Character.STATUS.MAIN);

            c.close();
        }
        catch (Exception e)
        {
            Log.e("SQL EXCEPTION", e.toString());
        }

    }


    public void getresources(RelativeLayout r)
    {
        this.main = r;
    }


    private void clicked_item() //when clicked item.
    {
        frame[0] = new TouchImageView(main_Activity);
        frame[0].setSize(width_box * ScreenParameter.getScreenparam_x(), height_box  * ScreenParameter.getScreenparam_y());
        frame[0].setLocation(ObjectSizeLocation.Location_Box.x * ScreenParameter.getScreenparam_x(), ObjectSizeLocation.Location_Box.y * ScreenParameter.getScreenparam_y());
        frame[0].setBackground(R.drawable.edit_base_item);
        frame[0].setCanDrag(false);
        frame[0].setCanMove(false);
        frame[0].showAnim(anim_speed);
        this.insertViews(frame[0]);

        itemLayout item_layout = new itemLayout(main_Activity);
        item_layout.setSize(1210 * ScreenParameter.getScreenparam_x(),750 * ScreenParameter.getScreenparam_y());
        item_layout.setLocation(120 * ScreenParameter.getScreenparam_x(),800 * ScreenParameter.getScreenparam_y());
        item_layout.setitem(item_index);
        item_layout.setMain(main);
        this.insertViews(item_layout);
        item_layout.viewitem();
        item_layout.setScaleAnim(anim_speed);


        relativeLayout = new RelativeLayout(main_Activity);


        arrow = new TouchImageView(main_Activity);
        arrow.setSize(20  * ScreenParameter.getScreenparam_x(), 23 * ScreenParameter.getScreenparam_x());
        arrow.setLocation(180* ScreenParameter.getScreenparam_x(), 70 * ScreenParameter.getScreenparam_x());
        relativeLayout.addView(arrow);



        ItemImageView[] item = new ItemImageView[21];



        for(int i=0; i<20; i++)
        {
            item[i] = new ItemImageView(main_Activity,i){
                @Override
                public  void touchEvent()
                {
                    super.touchEvent();

                    item_index = getIndex();
                    arrow.setSize(arrow.getWidth(), arrow.getHeight());
                    arrow.setLocation( getX() + (getWidth() / 2 - arrow.getWidth() / 2) + 2, arrow.getY());
                    arrow.setBackground(R.drawable.arrow);
                }
            };
            item[i].setSize(ObjectSizeLocation.info_size_item[i <15 ? i : 14][0] * ScreenParameter.getScreenparam_x(), ObjectSizeLocation.info_size_item[i <15 ? i : 14][1] * ScreenParameter.getScreenparam_y());
            item[i].setLocation(0 + item[(i==0 ? 0 : i-1)].getLX() + (i==0 ? 0 : 1)*item[(i==0 ? 0 : i-1)].getWIDTH() + 20 * ScreenParameter.getScreenparam_x(), 95 * ScreenParameter.getScreenparam_y());
            item[i].setItemLayout(item_layout);
            relativeLayout.addView(item[i]);
            item[i].setScaleAnim(anim_speed);

        }

        HorizontalScrollView scrollView = new HorizontalScrollView(main_Activity);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (1150 * ScreenParameter.getScreenparam_x()), (int) (250  * ScreenParameter.getScreenparam_y()));
        params.setMargins((int) (130  * ScreenParameter.getScreenparam_x()), (int) ((1520-85)  * ScreenParameter.getScreenparam_y()), 0, 0);
        scrollView.setLayoutParams(params);

        scrollView.addView(relativeLayout);
        this.insertViews(scrollView);
        scrollView.setHorizontalScrollBarEnabled(false);

    }


    private void unclicked_item(View view, float f)
    {
        int speed = 100;

        ScaleAnimation ani_c = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, f, Animation.RELATIVE_TO_SELF, 1);
        view.startAnimation(ani_c);
        ani_c.setDuration(speed);
    }




    private void clicked_character()    //when clicked character.
    {
        frame[1] = new TouchImageView(main_Activity);
        frame[1].setSize(width_box * ScreenParameter.getScreenparam_x(), height_box * ScreenParameter.getScreenparam_y());
        frame[1].setLocation(ObjectSizeLocation.Location_Box.x * ScreenParameter.getScreenparam_x() , ObjectSizeLocation.Location_Box.y * ScreenParameter.getScreenparam_y());
        frame[1].setBackground(R.drawable.edit_base_char);
        frame[1].showAnim(anim_speed);
        this.insertViews(frame[1]);



        for(int i=0; i<Character.num_character; i++)
        {
            characters[i].setSize(120 * ScreenParameter.getScreenparam_x(), 163 * ScreenParameter.getScreenparam_y());
            characters[i].setLocation((130 + 0 + (23 + 127) * i) * ScreenParameter.getScreenparam_x(), (1620-85) * ScreenParameter.getScreenparam_y());
            characters[i].setCanMove(false);
            characters[i].setCanDrag(false);
            characters[i].setScaleAnim1_char_i(anim_speed);
            characters[i].setScaleType(ScaleType.FIT_XY);
            //characters[i].setBackgroundColor(Color.BLUE);
            characters[i].setInfo(ObjectSizeLocation.name_character[i], ObjectSizeLocation.info_character[i], R.drawable.charinfo_skill_1 + i, ObjectSizeLocation.price_character[i],R.drawable.charinfo_heart_1 + i);
            characters[i].setOnTouchListener(new OnTouchListener() {

                boolean F = false;

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {


                        Drawable d = view.getBackground();

                        character_index = ((Character) view).getIndex();
                        ((Character)view).setBackgroundColor(Color.RED);
                        view.invalidate();
                        ((Character) view).setScaleType(ScaleType.FIT_XY);



                        if(characters[character_index].character_status == Character.STATUS.LOCK)
                        {
                            buy.setCharacterState(C_STATE.LOCK);
                            buy.setBackground(R.drawable.buy_x);
                        }

                        else if(characters[character_index].character_status == Character.STATUS.NONE)
                        {
                            buy.setCharacterState(C_STATE.NONE);
                            buy.setBackground(R.drawable.buy_unclick);
                        }

                        else if(characters[character_index].character_status == Character.STATUS.BUY)
                        {
                            buy.setCharacterState(C_STATE.BUY);
                            buy.setBackground(R.drawable.change_click);
                        }

                        else if(characters[character_index].character_status == Character.STATUS.MAIN)
                        {
                            buy.setCharacterState(C_STATE.MAIN);
                            buy.setBackground(R.drawable.main_character);
                        }


                        arrow.setLocation(((Character) view).getX() + (view.getWidth() / 2 - arrow.getWidth() / 2), arrow.getY());
                        arrow.setBackground(R.drawable.arrow);

                        imgView.setBackground(null);
                        // imgView.setBackground(view.getBackground());
                        imgView.invalidate();

                        character_info.setBackground(null);
                        //  character_info.setBackground(getResources().getDrawable(R.drawable.character_info_0 + ((Character) view).getIndex()));
                        character_info.invalidate();




                        title.setText(characters[character_index].Name());
                        info.setText(characters[character_index].Info());
                        skill.setBackground(characters[character_index].Skills());
                        price_t.setText(Integer.toString(characters[character_index].Money()));
                        heart.setBackground(characters[character_index].Heart());


                        ((Character)view).setBackground(d);
                        imgView.setBackground(R.drawable.d_infochar_1 + character_index);
                    }
                    return true;
                }
            });
            this.insertViews(characters[i]);
        }

        characters[Character.num_character - 1].setSize(0,0);


        imgView = new TouchImageView(main_Activity);
        imgView.setSize(425 * ScreenParameter.getScreenparam_x(), 565 * ScreenParameter.getScreenparam_y());
        imgView.setLocation(200 * ScreenParameter.getScreenparam_x(), (880-85) * ScreenParameter.getScreenparam_y());
        this.insertViews(imgView);
        imgView.setScaleAnim1_char_imgview(anim_speed);

        character_info = new TouchImageView(main_Activity);
        character_info.setSize(ScreenParameter.getScreenparam_x() * 605, ScreenParameter.getScreenparam_y() *434);
        character_info.setLocation(ScreenParameter.getScreenparam_x() * 630, ScreenParameter.getScreenparam_y() * (900-85));
        this.insertViews(character_info);
        character_info.setScaleAnim1_char_info(anim_speed);

        arrow = new TouchImageView(main_Activity);
        arrow.setSize(25 * ScreenParameter.getScreenparam_x(), 23 * ScreenParameter.getScreenparam_y());
        arrow.setLocation(213 * ScreenParameter.getScreenparam_x(), (1591-85) * ScreenParameter.getScreenparam_y());
        this.insertViews(arrow);

        buy_popup = new TouchImageView(main_Activity);
        buy_popup.setSize(1257 * ScreenParameter.getScreenparam_x(), 1243 * ScreenParameter.getScreenparam_y());
        buy_popup.setLocation(99 * ScreenParameter.getScreenparam_x() , (665-85) * ScreenParameter.getScreenparam_y());
        buy_popup.setBackground(R.drawable.buy_frame);
        //this.insertViews(buy_popup);

        buy_ok = new TouchButton(main_Activity, R.drawable.buy_frame_ok) {
            @Override
            public void touchEvent() {
                // UnClickAction();

                //MainActivity.character.selectCharacter(character_index, Character.STATUS.MAIN);

                if(characters[character_index].character_status == Character.STATUS.NONE && DbResource.getMoney()-ObjectSizeLocation.price_character[character_index]>0)
                {
                    for(int i=0; i<3; i++)
                        MainActivity.item[i].canClick = true;
                    MainActivity.menu.canClick = true;


                    buy.setCharacterState(C_STATE.MAIN);
                    buy.setImages(buyButtonView.btn_state.main_character);
                    characters[character_index].selectCharacter(character_index, Character.STATUS.MAIN);
                    setmainCharacter(character_index);

                    DbResource.setMoney(DbResource.getMoney()-ObjectSizeLocation.price_character[character_index]);
                    DbResource.db.execSQL("update resources set numb = " + DbResource.getMoney() + " where attr = 'money';");
                    DbResource.db.execSQL("update c_table set buy=1 where c_index=" + Integer.toString(character_index) + ";");
                    MainActivity.money.setText(Integer.toString(DbResource.getMoney()));

                    buy_success = new TouchImageView(main_Activity);
                    buy_success.setSize(801 * ScreenParameter.getScreenparam_x(), 143 * ScreenParameter.getScreenparam_y());
                    buy_success.setLocation(320 * ScreenParameter.getScreenparam_x(), (1216-85) * ScreenParameter.getScreenparam_y());
                    buy_success.setBackground(R.drawable.buy_success);

                    Handler handler = new Handler()
                    {
                        int i = 0;

                        public void handleMessage(Message msg)
                        {
                            if(i == 1)
                                main.removeView(buy_success);

                            i++;

                            sendEmptyMessageDelayed(0,700);
                        }
                    };

                    handler.sendEmptyMessage(0);

                    main.addView(buy_success);
                }

                else if(characters[character_index].character_status == Character.STATUS.NONE && DbResource.getMoney()-ObjectSizeLocation.price_character[character_index]<0)
                {
                    buy_fail = new TouchImageView(main_Activity);
                    buy_fail.setSize(801 * ScreenParameter.getScreenparam_x(), 143 * ScreenParameter.getScreenparam_y());
                    buy_fail.setLocation(320 * ScreenParameter.getScreenparam_x(), (1216-85) * ScreenParameter.getScreenparam_y());
                    buy_fail.setBackground(R.drawable.buy_failed);

                    Handler handler = new Handler()
                    {
                        int i = 0;

                        public void handleMessage(Message msg)
                        {
                            if(i == 1)
                                main.removeView(buy_fail);

                            i++;

                            sendEmptyMessageDelayed(0,700);
                        }
                    };

                    handler.sendEmptyMessage(0);

                    main.addView(buy_fail);

                }


                main.removeView(buy_popup);
                main.removeView(buy_ok);
                main.removeView(buy_x);

                for(int i=0; i<3; i++)
                    ((MainActivity)main_Activity).item[i].CanClick(true);

                MainActivity.menu.canClick = true;

            }
        };
        buy_ok.setSize(121 * ScreenParameter.getScreenparam_x(), 121 * ScreenParameter.getScreenparam_y());
        buy_ok.setLocation(1160 * ScreenParameter.getScreenparam_x() , (1440-85) * ScreenParameter.getScreenparam_y());
        buy_ok.bringToFront();


        buy_x = new TouchButton(main_Activity, R.drawable.buy_frame_x) {
            @Override
            public void touchEvent() {
                main.removeView(buy_popup);
                main.removeView(buy_x);
                main.removeView(buy_ok);
                buy.bringToFront();


                for(int i=0; i<3; i++)
                    ((MainActivity)main_Activity).item[i].CanClick(true);

                MainActivity.menu.canClick = true;

            }
        };
        buy_x.setSize(121 * ScreenParameter.getScreenparam_x(), 121 * ScreenParameter.getScreenparam_y());
        buy_x.setLocation(1010 * ScreenParameter.getScreenparam_x() , (1440-85) * ScreenParameter.getScreenparam_y());


        // this.insertViews(buy_success);

        buy = new buyButtonView(main_Activity, R.drawable.buy_unclick, R.drawable.buy_click) {
            @Override
            public void clickEvent() {

                for(int i=0; i<3; i++)
                    ((MainActivity)main_Activity).item[i].CanClick(false);

                MainActivity.menu.canClick = false;

                if(characters[character_index].character_status == Character.STATUS.LOCK || characters[character_index].character_status == Character.STATUS.MAIN)
                {
                    for(int i=0; i<3; i++)
                        MainActivity.item[i].canClick = true;
                    MainActivity.menu.canClick = true;


                    return;
                }

                else if(characters[character_index].character_status == Character.STATUS.BUY)
                {

                    for(int i=0; i<3; i++)
                        MainActivity.item[i].canClick = true;
                    MainActivity.menu.canClick = true;

                    setmainCharacter(character_index);
                    setImages(btn_state.main_character);
                }

                else if(characters[character_index].character_status == Character.STATUS.NONE)
                {
                    main.addView(buy_popup);
                    main.addView(buy_ok);
                    main.addView(buy_x);
                }


            }
        };

        buy.setSize(width_buy * ScreenParameter.getScreenparam_x(), height_buy * ScreenParameter.getScreenparam_y());
        buy.setLocation(ObjectSizeLocation.Location_buy.x * ScreenParameter.getScreenparam_x() ,ObjectSizeLocation.Location_buy.y * ScreenParameter.getScreenparam_y());
        this.insertViews(buy);


        if(characters[character_index].character_status == Character.STATUS.LOCK)
        {
            buy.setCharacterState(C_STATE.LOCK);
            buy.setBackground(R.drawable.buy_x);
        }

        else if(characters[character_index].character_status == Character.STATUS.NONE)
        {
            buy.setCharacterState(C_STATE.NONE);
            buy.setBackground(R.drawable.buy_unclick);
        }

        else if(characters[character_index].character_status == Character.STATUS.BUY)
        {
            buy.setCharacterState(C_STATE.BUY);
            buy.setBackground(R.drawable.change_click);
        }

        else if(characters[character_index].character_status == Character.STATUS.MAIN)
        {
            buy.setCharacterState(C_STATE.MAIN);
            buy.setBackground(R.drawable.main_character);
        }


        buy.setScaleAnim1_char_buy(anim_speed);


        info = new onTextView(main_Activity);
        info.setLocation(ScreenParameter.getScreenparam_x() * 700, ScreenParameter.getScreenparam_y() * (1050-85));
        info.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "KoPubDotumMedium_0.ttf"));
        info.setScaleAnim_char_info(anim_speed);
        info.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(36 * ScreenParameter.getScreenparam_y()));
        info.setLineSpacing(5.0f, 1.0f);
        info.setTextColor(Color.WHITE);
        this.insertViews(info);

        skill = new TouchImageView(main_Activity);
        skill.setSize(324 * ScreenParameter.getScreenparam_x(), 75 * ScreenParameter.getScreenparam_y());
        skill.setLocation(920 * ScreenParameter.getScreenparam_x(), (1200-85+45) * ScreenParameter.getScreenparam_y());
        this.insertViews(skill);
        skill.setScaleAnim1_char_skill(anim_speed);

        heart = new TouchImageView(main_Activity);
        heart.setSize(190 * ScreenParameter.getScreenparam_x(), 75 * ScreenParameter.getScreenparam_y());
        heart.setLocation(700 * ScreenParameter.getScreenparam_x(), (1200-85+45) * ScreenParameter.getScreenparam_y());
        this.insertViews(heart);
        heart.setScaleAnim1_char_heart(anim_speed);

        price_t = new onTextView(main_Activity);
        price_t.setLocation(ScreenParameter.getScreenparam_x() * (790+50), ScreenParameter.getScreenparam_y() * (1435-85));
        price_t.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoB.ttf"));
        price_t.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(58 * ScreenParameter.getScreenparam_y()));
        price_t.setTextColor(Color.rgb(245, 175, 1));
        this.insertViews(price_t);
        price_t.setScaleAnim_char_price(anim_speed);

        price_star = new TouchImageView(main_Activity);
        price_star.setSize(53 * ScreenParameter.getScreenparam_x(), 89 * ScreenParameter.getScreenparam_y());
        price_star.setLocation((730+50) * ScreenParameter.getScreenparam_x(), (1424-85) * ScreenParameter.getScreenparam_y());
        price_star.setBackground(R.drawable.price);
        this.insertViews(price_star);
        price_star.setScaleAnim1_char_price_star(anim_speed);

        title = new onTextView(main_Activity);
        title.setLocation(ScreenParameter.getScreenparam_x() * 700, ScreenParameter.getScreenparam_y() * (940-85));
        title.setTypeface(Typeface.createFromAsset(main_Activity.getAssets(), "GodoB.ttf"));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(85 * ScreenParameter.getScreenparam_y()));
        title.setTextColor(Color.rgb(0, 255, 255));
        this.insertViews(title);
        title.setScaleAnim_char_title(anim_speed);
    }

    private void clicked_minigame() //whem clicked mini game
    {
        game_index = -1;

        frame[2]= new TouchImageView(main_Activity);
        frame[2].setSize(width_box * ScreenParameter.getScreenparam_x(), height_box * ScreenParameter.getScreenparam_y());
        frame[2].setLocation(ObjectSizeLocation.Location_Box.x * ScreenParameter.getScreenparam_x(), ObjectSizeLocation.Location_Box.y * ScreenParameter.getScreenparam_y());
        frame[2].setBackground(R.drawable.edit_base_mini);
        this.insertViews(frame[2]);
        frame[2].showAnim(anim_speed);


        TouchImageView mission1_clear = new TouchImageView(main_Activity);
        mission1_clear.setSize(1030 * ScreenParameter.getScreenparam_x(), 91 * ScreenParameter.getScreenparam_y());
        mission1_clear.setLocation(170 * ScreenParameter.getScreenparam_x(), (825-85) * ScreenParameter.getScreenparam_y());
        mission1_clear.setBackground(R.drawable.mission1_clear);
        this.insertViews(mission1_clear);
        mission1_clear.setScaleAnim_game_mission(anim_speed);


        TouchImageView mission2 = new TouchImageView(main_Activity);
        mission2.setSize(1030 * ScreenParameter.getScreenparam_x(), 91 * ScreenParameter.getScreenparam_y());
        mission2.setLocation(170 * ScreenParameter.getScreenparam_x(), (925-85) * ScreenParameter.getScreenparam_y());
        mission2.setBackground(R.drawable.mission2);
        this.insertViews(mission2);
        mission2.setScaleAnim_game_mission(anim_speed);


        TouchImageView mission3 = new TouchImageView(main_Activity);
        mission3.setSize(1030 * ScreenParameter.getScreenparam_x(), 91 * ScreenParameter.getScreenparam_y());
        mission3.setLocation(170 * ScreenParameter.getScreenparam_x(), (1025-85)* ScreenParameter.getScreenparam_y());
        mission3.setBackground(R.drawable.mission3);
        this.insertViews(mission3);
        mission3.setScaleAnim_game_mission(anim_speed);


        sea = new MiniGameButton(main_Activity, R.drawable.game1, R.drawable.game_click1) {
            @Override
            public void ClickAction() {
                game_index = 0;
                gate.UnClick();
                start.setBackground(R.drawable.start_click);

            }

            @Override
            public void UnClickAction() {
                start.setBackground(R.drawable.start);

            }

            @Override
            public void startGame() {

//                Intent i = new Intent(main_Activity, SliderActivity.class);
//                main_Activity.startActivity(i);

                try {
                    FileOutputStream fileOutputStream = main_Activity.openFileOutput("charno.txt", Context.MODE_WORLD_READABLE);

                    Cursor c = DbResource.db.rawQuery("select c_index from character", null);
                    c.moveToNext();

                    int charType = c.getInt(0);

                    String str;

                    if(charType == 3)
                        str = "1";
                    else if(charType == 5)
                        str = "2";
                    else
                        str = "0";


                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.close();

                }catch (Exception e)
                {
                    Log.e("FILE EXCEPTION", e.toString());
                }

                try {

                    FileOutputStream fileOutputStream = main_Activity.openFileOutput("seacoin.txt", Context.MODE_WORLD_READABLE);

                    Cursor c = DbResource.db.rawQuery("select numb from resources where attr = 'money';", null);
                    c.moveToNext();

                    String str = c.getString(0);

                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.close();

                    c.close();

                }catch (Exception e)
                {
                    Log.e("FILE EXCEPTION", e.toString());
                }

                try {
                    FileOutputStream fileOutputStream = main_Activity.openFileOutput("endsea.txt", Context.MODE_WORLD_READABLE);

                    String str = "0";

                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.close();
                }catch (Exception e)
                {
                    Log.e("FILE SETTING EXCEPTION", e.toString());

                }

                MainActivity.coinCheck = true;

                Handler handler = new Handler()
                {
                    String str;

                    @Override
                    public void handleMessage(Message msg)
                    {


                        try {

                            InputStream inputStream = getContext().openFileInput("endsea.txt");
                            int i = 0;

                            StringBuffer buffer = new StringBuffer();
                            byte[] b = new byte[1000];

                            while ((i = inputStream.read(b)) != -1) {
                                buffer.append(new String(b, 0, i));
                            }

                            str = buffer.toString().substring(0, 1);

                            Log.e("TIMER FILE CHECK/////", str);

                            if (Integer.parseInt(str) == 1)
                            {
                                android.os.Process.killProcess(android.os.Process.myPid());
                                getContext().startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class));
                            }

                            sendEmptyMessageDelayed(0, 300);

                        }
                        catch (Exception e)
                        {
                            Log.e("FILE READ ERROR", e.toString());
                        }




                    }
                };


                handler.sendEmptyMessage(0);


                Intent intent = new Intent(main_Activity, UnityPlayerActivity.class);
                ((MainActivity)(main_Activity)).startActivity(intent);
            }

        };
        sea.setSize(367 * ScreenParameter.getScreenparam_x(), 397 * ScreenParameter.getScreenparam_y());
        sea.setLocation(302 * ScreenParameter.getScreenparam_x(), (1218-85) * ScreenParameter.getScreenparam_y());
        this.insertViews(sea);
        sea.setScaleAnim_game_sea(anim_speed);


        gate = new MiniGameButton(main_Activity, R.drawable.game2, R.drawable.game_click2) {
            @Override
            public void ClickAction() {
                game_index = 1;
                sea.UnClick();
                start.setBackground(R.drawable.start_click);

            }

            @Override
            public void UnClickAction() {
                start.setBackground(R.drawable.start);

            }

            @Override
            public void startGame() {



                DbResource.db.execSQL("create table if not exists endgate(count integer,name text,PRIMARY KEY(name));");

                try
                {
                    DbResource.db.execSQL("insert into endgate (count,name) values(" + 0 + ",'player');");
                }

                catch (SQLException e)
                {
                    Log.d("message", e.toString());
                }






                Intent intent = new Intent(main_Activity, GateRunnerActivity.class);
                ((MainActivity)(getContext())).startActivity(intent);
            }
        };
        gate.setSize(367 * ScreenParameter.getScreenparam_x(), 397 * ScreenParameter.getScreenparam_y());
        gate.setLocation((302+367+116) * ScreenParameter.getScreenparam_x(), (1218-85) * ScreenParameter.getScreenparam_y());
        this.insertViews(gate);
        gate.setScaleAnim_game_gate(anim_speed);

        moving = new MiniGameButton(main_Activity, R.drawable.moving, R.drawable.moving) {
            @Override
            public void ClickAction() {
            }

            @Override
            public void UnClickAction() {

            }

            @Override
            public void startGame() {
            }
        };
        moving.setSize(367 * ScreenParameter.getScreenparam_x(), 397 * ScreenParameter.getScreenparam_y());
        moving.setLocation(920 * ScreenParameter.getScreenparam_x(), (1250-85) * ScreenParameter.getScreenparam_y());
        moving.setScaleAnim_game_move(anim_speed);

        miniGameButtons[0] = sea;
        miniGameButtons[1] = gate;
        miniGameButtons[2] = moving;

        start= new ClickButtonView(main_Activity, R.drawable.start, R.drawable.start_click) {
            @Override
            public void clickEvent() {
                try{

                    miniGameButtons[game_index].startGame();
                }
                catch(Exception e)
                {
                    Log.e("FINAL EXCEPTION", e.getClass().toString());
                }
            }
        };
        start.setSize(259 * ScreenParameter.getScreenparam_x(), 89 * ScreenParameter.getScreenparam_y());
        start.setLocation(940 * ScreenParameter.getScreenparam_x(), (1670-85+48) * ScreenParameter.getScreenparam_y());
        this.insertViews(start);
        start.setScaleAnim_game_start(anim_speed);

    }


    public void insertViews(View viewList)
    {
        Views.add(viewList);
    }

    public void showViews()
    {

        if(Views == null)
            return;

        for(int i=0; i< Views.size(); i++)
            main.addView(Views.get(i));


    }

    public void deleteViews()
    {
        if(Views == null)
            return;

        for(int i=0; i<Views.size(); i++)
        {
            main.removeView(Views.get(i));
        }


        Views.clear();
    }



    @Override
    public void ClickAction() {



        switch(index)
        {
            case 0:clicked_item();
                if(MainActivity.item[1].IsClicked())
                    MainActivity.item[1].UnClick();

                if(MainActivity.item[2].IsClicked())
                    MainActivity.item[2].UnClick();

                MainActivity.clear_condition = true;
                break;
            case 1:

                if(MainActivity.clear_condition_sub)
                    MainActivity.clear_condition_tri = true;

                if(MainActivity.item[0].IsClicked())
                    MainActivity.item[0].UnClick();

                if(MainActivity.item[2].IsClicked())
                    MainActivity.item[2].UnClick();

                clicked_character();
                imgView.setBackground(R.drawable.d_infochar_1 + character_index);
                title.setText(characters[character_index].Name());
                info.setText(characters[character_index].Info());
                skill.setBackground(characters[character_index].Skills());
                heart.setBackground(characters[character_index].Heart());
                price_t.setText(Integer.toString(characters[character_index].Money()));
                break;
            case 2:

                if(MainActivity.item[1].IsClicked())
                    MainActivity.item[1].UnClick();

                if(MainActivity.item[0].IsClicked())
                    MainActivity.item[0].UnClick();
                clicked_minigame();break;

        }

        showViews();

        xbtn.setSize((int)(screenparam_x * width_xBox), (int)(screenparam_x * height_xBox));
        xbtn.setLocation(screenparam_x * ObjectSizeLocation.Location_xBox.x, screenparam_y * ObjectSizeLocation.Location_xBox.y);
        main.addView(xbtn);


        MainActivity.canNotice = false;
        MainActivity.NOTICE = false;
    }



    @Override
    public void UnClickAction()
    {

        MainActivity.clear_condition = false;
        MainActivity.clear_condition_sub = false;

        Log.e("CONDITION", MainActivity.clear_condition_tri.toString());

        if(index == 1 && MainActivity.clear_condition_tri)
        {

            MainActivity.timeHhandler.removeMessages(0);


            DbResource.db.execSQL("update resources set numb=100000 where attr='money'");
            DbResource.db.execSQL("update resources set numb=0 where attr='Gamepoint_sea'");
            DbResource.db.execSQL("update resources set numb=0 where attr='Gamepoint_gate'");
            DbResource.db.execSQL("update resources set numb=100 where attr='ticket'");
            DbResource.db.execSQL("update product set timer=100000 where name='휴지이'");
            DbResource.db.execSQL("update product set timer=10000 where name='생수'");
            DbResource.db.execSQL("update product set timer=1000 where name='향수'");
            DbResource.db.execSQL("update product set timer=5000 where name='바디워시'");
            DbResource.db.execSQL("update product set timer=7200 where name='쓰레기 봉투 10L'");
            DbResource.db.execSQL("update product set timer=30 where name='올(all) 세탁 세제'");
            DbResource.db.execSQL("update character set c_index=0");
            DbResource.db.execSQL("update idp set entry_num=" + Integer.toString(0) + ";");
            DbResource.db.execSQL("update account set phone='82+1050912131'");
            DbResource.db.execSQL("update account set email='dltkdi173@gmail.com1'");
            DbResource.db.execSQL("update account set addr='경북 포항시 북구 한동대학교'");
            DbResource.db.execSQL("delete from point");

            try {

                FileOutputStream fileOutputStream = main_Activity.openFileOutput("best.txt", Context.MODE_WORLD_READABLE);


                String str = "0";

                fileOutputStream.write(str.getBytes());
                fileOutputStream.close();

            }catch (Exception e)
            {
                Log.e("FILE EXCEPTION", e.toString());
            }


            for(int i=1; i<8; i++)
                DbResource.db.execSQL("update c_table set buy=0 where c_index=" + Integer.toString(i) + ";");

            ((MainActivity)(main_Activity)).finish();
            Intent intent = new Intent(main_Activity, SliderActivity.class);
            main_Activity.startActivity(intent);



            System.gc();

        }


        if(!(index == 0 ))
            MainActivity.clear_condition_tri = false;

        main.removeView(xbtn);
        // ((TouchImageView)viewList.get(42)).setBackground(R.drawable.mainpage_land);

        unclicked_item(frame[index], 0.25f + index * 0.25f);

        deleteViews();


        MainActivity.canNotice = true;
        MainActivity.NOTICE = true;
    }

    public void setmainCharacter(int n)
    {
        index_main_character = n;

        for(int i=0; i<Character.num_character-1; i++)
            if(characters[i].character_status == Character.STATUS.MAIN)
                characters[i].selectCharacter(i, Character.STATUS.BUY);

        characters[n].selectCharacter(n, Character.STATUS.MAIN);

        MainActivity.character.selectCharacter(index_main_character, Character.STATUS.MAIN );
        MainActivity.character.setBackground(R.drawable.d_infochar_1 + index_main_character);

        DbResource.db.execSQL("update character set c_index=" + Integer.toString(n) + ";");

    }
}

