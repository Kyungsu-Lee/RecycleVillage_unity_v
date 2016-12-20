package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class MainActivity extends Activity {

    public static Context context_this;

    TouchImageView land;
    static Character character;
    TouchImageView moon;
    TouchImageView stars[];
    ClickButtonView nfc;
    ClickButtonView nfc2;
    TouchImageView guide_character;
    TouchImageView guide_moon;
    TouchImageView noti_num;

    public static onTextView ticket;
    public static onTextView money;
    public static ItemGrid grid;
    public static List<GridPoint> g_point = new Vector<GridPoint>();
    public static List<GridPoint> air_point = new Vector<GridPoint>();
    SlideView slideView;

    MainActivity mainActivity;

    public static boolean semaphore=true;

    static mainButtonView item[] = new mainButtonView[3];

    static final int num_stars = 15;
    static final int size_stars = 60;

    public int check_notice = 3;

    static boolean canNotice = true;

    static Typeface typeface;
    static Typeface typeface2;

    static Boolean clear_condition = false;
    static  Boolean clear_condition_sub = false;
    static Boolean clear_condition_tri =false;
    static boolean coinCheck = false;

    RelativeLayout main;

    static menuButton menu;

    static onTextView ischeck;

    static int isNumSpace;
    static int isNumHeli;
    static boolean NOTICE = true;


    public ArrayList<Integer> time = new ArrayList<>();
    public ArrayList<String> product_name = new ArrayList<>();

    static Handler timeHhandler;


    static ItemGrid sky_grid;


    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;
    String[][] nfcLists;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setTheme(android.R.style.Theme_Translucent_NoTitleBar);


        setContentView(R.layout.activity_main);

        ScreenParameter.setScreenparam_x(ScreenSize().x/ScreenParameter.getDefaultsizeX());
        ScreenParameter.setScreenparam_y(ScreenSize().y/ScreenParameter.getDefaultsizeY());
        ScreenParameter.setScreen_x(ScreenSize().x);
        ScreenParameter.setScreen_y(ScreenSize().y);


        try {

            DbResource.conn = new DBConect(this, "recyclevillageDB.db",null,1);

            DbResource.db =  DbResource.conn.getWritableDatabase();


            DbResource.db.execSQL("insert into c_table values ('space', 0 , 1);");
            DbResource.db.execSQL("insert into c_table values ('space', 1 , 0);");
            DbResource.db.execSQL("insert into c_table values ('space', 2 , 0);");
            DbResource.db.execSQL("insert into c_table values ('space', 3 , 0);");
            DbResource.db.execSQL("insert into c_table values ('space', 4 , 0);");
            DbResource.db.execSQL("insert into c_table values ('space', 5 , 0);");
            DbResource.db.execSQL("insert into c_table values ('space', 6 , 0);");
            DbResource.db.execSQL("insert into c_table values ('space', 7 , 0);");

            DbResource.db.execSQL("insert into character values ('space', 0);");

            DbResource.db.execSQL("insert into resources values('money',100000);");
            DbResource.db.execSQL("insert into resources values('ticket',100);");
            DbResource.db.execSQL("insert into resources values('char',0);");
            DbResource.db.execSQL("insert into resources values('Gamepoint_sea',0);");
            DbResource.db.execSQL("insert into resources values('Gamepoint_gate',0);");

            DbResource.db.execSQL("insert into account values('space', '82+1050912131', 'dltkdi173@gmail.com', '경북 포항시 북구 한동대학교');");


            DbResource.db .execSQL("insert into product values('휴지이',100000, 0);");
            DbResource.db.execSQL("insert into product values('생수', 10000, 1);");
            DbResource.db.execSQL("insert into product values('액체 세제', 1000, 2);");
            DbResource.db .execSQL("insert into product values('샴푸 및 컨디셔너',50000, 3);");
            DbResource.db.execSQL("insert into product values('쓰레기 봉투 10L', 3000, 4);");
            DbResource.db.execSQL("insert into product values('ALL 세탁 세제', 30, 5);");

            DbResource.db.execSQL("insert into idp values('space', '휴지이', 0);");
            DbResource.db.execSQL("insert into idp values('space', '생수', 0);");
            DbResource.db.execSQL("insert into idp values('space', '액체 세제', 0);");
            DbResource.db.execSQL("insert into idp values('space', '샴푸 및 컨디셔너', 0);");
            DbResource.db.execSQL("insert into idp values('space', '쓰레기 봉투 10L', 0);");
            DbResource.db.execSQL("insert into idp values('space', 'ALL 세탁 세제', 0);");

        }
        catch (SQLException e)
        {
            Log.e("message", e.toString());
        }

        finally {

            Cursor c =  DbResource.db.rawQuery("select * from resources;", null);
            c.moveToNext();
            DbResource.setMoney(c.getInt(c.getColumnIndex("numb")));
            c.moveToNext();
            DbResource.setTicket(c.getInt(c.getColumnIndex("numb")));
            c.moveToNext();
            DbResource.setChar_index(c.getInt(c.getColumnIndex("numb")));

            c.close();

            c = DbResource.db.rawQuery("select * from resources;", null);
            Log.e("SQL COUNT", Integer.toString(c.getCount()));
            c.close();

            c = DbResource.db.rawQuery("select * from product;", null);
            Log.e("SQL COUNT", Integer.toString(c.getCount()));
            c.close();

            c = DbResource.db.rawQuery("select * from idp;", null);
            Log.e("SQL COUNT", Integer.toString(c.getCount()));
            c.close();

            c = DbResource.db.rawQuery("select * from account;", null);
            Log.e("SQL COUNT", Integer.toString(c.getCount()));
            c.close();

            c = DbResource.db.rawQuery("select * from character;", null);
            Log.e("SQL COUNT", Integer.toString(c.getCount()));
            c.close();

            c = DbResource.db.rawQuery("select * from c_table;", null);
            Log.e("SQL COUNT", Integer.toString(c.getCount()));
            c.close();

        }

        try {
            Cursor c = DbResource.db.rawQuery("select * from product;", null);

            while (c.moveToNext())
            {
                time.add(c.getInt(c.getColumnIndex("timer")));
                product_name.add(c.getString(c.getColumnIndex("name")));
            }
            c.close();
            timeHhandler = new Handler()
            {
                int num;
                @Override
                public void handleMessage(Message msg)
                {
                    for(int i=0; i<product_name.size() && time.get(i)-num >= 0; i++)
                    {
                        DbResource.db.execSQL("update product set timer="+ (time.get(i)-num) +" where name = '" + product_name.get(i) + "';");
                    }
                    num++;
                    sendEmptyMessageDelayed(0, 1000);
                }
            };

            timeHhandler.sendEmptyMessage(0);

        }
        catch (Exception e)
        {
            Log.e("SQLMESSAGE", e.toString());
        }




        mainActivity = this;

        typeface = Typeface.createFromAsset(getAssets(), "KoPubDotumMedium_0.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(), "GodoB.ttf");

        if(g_point.size() != 0)
            g_point.clear();
        if(air_point.size() != 0)
            air_point.clear();

        context_this = this;

        semaphore = true;
        if(!itemLayout.viewList.isEmpty())
            itemLayout.viewList.clear();

        main = new RelativeLayout(this);
        (main).setBackground(getResources().getDrawable(R.drawable.grad));
        setContentView(main);


        DbResource.conn = new DBConect(this, "recyclevillageDB.db",null,1);
        DbResource.db = DbResource.conn.getWritableDatabase();


        if(ObjectSizeLocation.GRID_POINT[5][8].equals(new Point(206,1335)))
        {
            for(int i=0; i < ObjectSizeLocation.GRID_POINT.length; i++)
                for(int j=0; j<ObjectSizeLocation.GRID_POINT[i].length; j++)
                    ObjectSizeLocation.GRID_POINT[i][j] = new Point(ObjectSizeLocation.GRID_POINT[i][j].x - ObjectSizeLocation.re_x, ObjectSizeLocation.GRID_POINT[i][j].y - ObjectSizeLocation.re_y);

            for(int i=0; i<ObjectSizeLocation.point.length; i++)
            {
                ObjectSizeLocation.point[i][0] = ObjectSizeLocation.point[i][0]  - ObjectSizeLocation.re_x;
                ObjectSizeLocation.point[i][1] = ObjectSizeLocation.point[i][1]  - ObjectSizeLocation.re_y;
            }

            for(int i=0; i<ObjectSizeLocation.GRID_AIR_POINT.length; i++)
                for(int j=0; j<ObjectSizeLocation.GRID_AIR_POINT[0].length; j++)
                {
                    //ObjectSizeLocation.GRID_AIR_POINT[i][0] = new Point(ObjectSizeLocation.GRID_AIR_POINT[i][j].x - ObjectSizeLocation.re_x, ObjectSizeLocation.GRID_AIR_POINT[i][j].y - 2 * ObjectSizeLocation.re_y);
                }
        }


        for(int i = 0; i<ObjectSizeLocation.GRID_POINT.length; i++)
            for(int j=0; j<ObjectSizeLocation.GRID_POINT[i].length; j++)
                g_point.add(new GridPoint(ObjectSizeLocation.GRID_POINT[i][j]));

        try {
            for (int i = 0; i < ObjectSizeLocation.GRID_POINT.length; i++) {
                for (int j = 0; j < ObjectSizeLocation.GRID_POINT[i].length; j++) {

                    if ((g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j)).x != 2) {

                        try {
                            g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setRD(g_point.get(ObjectSizeLocation.GRID_POINT[i].length * (i+1) + (j)));
                        } catch (Exception e) {
                            Log.v("Boundary exception", e.toString() + "i : " + i + "j : " + j);
                        }


                        try {
                            g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setRU(g_point.get(ObjectSizeLocation.GRID_POINT[i].length * (i) + (j+1)));
                        } catch (Exception e) {
                            Log.v("Boundary exception", e.toString() + "i : " + i + "j : " + j);
                        }

                        try {
                            g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setLU(g_point.get(ObjectSizeLocation.GRID_POINT[i].length * (i - 1) + (j)));
                        } catch (Exception e) {
                            Log.v("Boundary exception", e.toString() + "i : " + i + "j : " + j);
                        }

                        try {
                            g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setLD(g_point.get(ObjectSizeLocation.GRID_POINT[i].length * (i) + (j - 1)));
                        } catch (Exception e) {
                            Log.v("Boundary exception", e.toString() + "i : " + i + "j : " + j);
                        }
                    }

                    if (g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).x != 2 && g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).getRD().x == 2)
                        g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setRD(null);
                    if (g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).x != 2 && g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).getRU().x == 2)
                        g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setRU(null);
                    if (g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).x != 2 && g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).getLD().x == 2)
                        g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setLD(null);
                    if (g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).x != 2 && g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).getLU().x == 2)
                        g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setLU(null);

                }
            }
        }catch (Exception e)
        {
            Log.e("Boundary exception", e.toString() +  " " + ObjectSizeLocation.GRID_POINT.length + " " + ObjectSizeLocation.GRID_POINT[16].length );
        }

        finally {
            Log.e("Message",g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).x + ", " + g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).y + "");
            Log.e("Message",g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).getRD().x + ", " +  g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).getRD().y + "");
            Log.e("Message",g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).getRU().x + ", " +  g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).getRU().y + "");
            Log.e("Message",g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).getLU().x + ", " +  g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).getLU().y + "");
            Log.e("Message",g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).getLD().x + ", " +  g_point.get(ObjectSizeLocation.GRID_POINT[0].length * 6 + 6).getLD().y + "");

            Log.e("OBJECTSIZE", "GRIDPOINT_X_length : " + ObjectSizeLocation.GRID_POINT.length);
            for(int i=0; i<ObjectSizeLocation.GRID_POINT.length; i++)
                Log.e("OBJECTSIZE", "GRIDPOINT_X_length_" + i + " : " + ObjectSizeLocation.GRID_POINT[i].length);
            Log.e("OBJECTSIZE", "g_point_length : " + g_point.size());
        }


        for(int j=0; j<ObjectSizeLocation.GRID_POINT[0].length; j++)
            for(int i=0; i<ObjectSizeLocation.GRID_POINT.length; i++)
                g_point.get(ObjectSizeLocation.GRID_POINT[i].length * i + j).setPriority(j + ObjectSizeLocation.GRID_POINT.length - i);

        for(int i=0; i<g_point.size(); i++)
        {
            g_point.get(i).x = (int)(g_point.get(i).x * ScreenParameter.getScreenparam_x());
            g_point.get(i).y = (int)(g_point.get(i).y * ScreenParameter.getScreenparam_y());
        }

        for(int i = 0; i<ObjectSizeLocation.GRID_AIR_POINT.length; i++)
            for(int j=0; j<ObjectSizeLocation.GRID_AIR_POINT[i].length; j++)
                air_point.add(new GridPoint(ObjectSizeLocation.GRID_AIR_POINT[i][j]));

        for(int i=0; i<air_point.size(); i++)
        {
            air_point.get(i).x = (int)(air_point.get(i).x * ScreenParameter.getScreenparam_x());
            air_point.get(i).y = (int)(air_point.get(i).y * ScreenParameter.getScreenparam_y());
        }


        Handler aboutSea = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                String str = "";

                if (coinCheck)
                {
                    try {

                        InputStream inputStream = openFileInput("seacoin.txt");
                        int i = 0;

                        StringBuffer buffer = new StringBuffer();
                        byte[] b = new byte[1000];

                        while ((i = inputStream.read(b)) != -1) {
                            buffer.append(new String(b, 0, i));
                        }

                        str = buffer.toString();

                        int coin = Integer.valueOf(str.substring(0,5));

                        if (DbResource.getMoney() + 10< coin) {
                            DbResource.setMoney(coin);
                            coinCheck = false;
                            DbResource.db.execSQL("update resources set numb= "+ coin +" where attr='money'");
                        }

                        inputStream.close();


                    } catch (Exception e) {
                      //  Log.e("FILE EXCEPTION", e.toString() + "......///" + str.substring(0,str.length()-1) + "////" + str.length());

                    }
                    finally {

                    }

                }

                sendEmptyMessage(3000);
            }
        };

        aboutSea.sendEmptyMessage(0);






        ///////////// create objects ////////////
        int star_x = 170;
        int star_y = 100;

        Random rd = new Random();

        stars = new TouchImageView[num_stars];
        for(int i=0; i<num_stars; i++)
        {
            stars[i] = new TouchImageView(this);
            stars[i].setSize(ScreenParameter.getScreenparam_x() * size_stars, ScreenParameter.getScreenparam_y() * size_stars);
            //  star_x += rd.nextInt(ScreenSize().x/4);
            //  star_y += rd.nextInt(ScreenSize().y/5);
            stars[i].setLocation(ObjectSizeLocation.star_locate[i][0] *ScreenParameter.getScreenparam_x(), ObjectSizeLocation.star_locate[i][1]* ScreenParameter.getScreenparam_y());
            stars[i].setBackground(R.drawable.mainpage_star);
            main.addView(stars[i]);
        }

        TouchImageView bg = new TouchImageView(this);
        bg.setSize((int)(ScreenParameter.getScreenparam_x() * 1440), (int)(ScreenParameter.getScreenparam_y()*322));
        bg.setLocation(0, ScreenParameter.getScreenparam_y() * 2153);
        bg.setBackground(R.drawable.mainpage_bgimg);
        main.addView(bg);


        bg.screenparam_x = ScreenParameter.getScreenparam_x();
        bg.screenparam_y = ScreenParameter.getScreenparam_y();
        bg.screen_x = (int)ScreenParameter.getScreen_x();
        bg.screen_y = (int)ScreenParameter.getScreen_y();
        bg.narrawSize = (int)ScreenParameter.getScreen_x();
        bg.wideSize = (int)ScreenParameter.getScreen_y();


        moon = new TouchImageView(this);
        moon.setSize( (int)(55 * ScreenParameter.getScreenparam_x()) , (int)(78 * ScreenParameter.getScreenparam_y()));
        moon.setLocation(ScreenParameter.getScreen_x() / 2 - moon.getWIDTH() / 2, (450-85)  * ScreenParameter.getScreenparam_y());
        moon.setBackground(R.drawable.mainpage_moon);
        main.addView(moon);
        moon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                //  check_notice--;

                //if(check_notice == 0)
                {
                    if(guide_character != null)
                    {
                        main.removeView(guide_character);
                        guide_character = null;
                    }

                    main.removeView(guide_moon);
                    guide_moon = null;

                    main.removeView(noti_num);
                    noti_num = null;

                    check_notice = 0;
                }


                return true;
            }
        });


/*
        Handler landmonivg= new Handler(){

            public  boolean F = true;
            public int gradle = 8;

            public void handleMessage(Message msg)
            {
                if(F)
                {
                    land.setY(land.getY()+1);

                    for(int i=0; i<viewList.size(); i++)
                        if(((TouchImageView)viewList.get(i)).getCanMove())
                            ((ImageView)viewList.get(i)).setY(viewList.get(i).getY() + 1);


                }

                else
                {
                    land.setY(land.getY()-1);

                    for(int i=0; i<viewList.size(); i++)
                        if(((TouchImageView)viewList.get(i)).getCanMove())
                            ((ImageView)viewList.get(i)).setY(viewList.get(i).getY() - 1);

                }

                if(land.getY() > ScreenParameter.getScreenparam_y() * (782 + gradle) || land.getY() < ScreenParameter.getScreenparam_y() * (782 - gradle))
                    F = !F;

                double maxmem =  Runtime.getRuntime().freeMemory() / (1024.0f * 1024.0f);
                double mem = Debug.getNativeHeapFreeSize() / (1024.0f * 1024.0f);
                int n = Debug.getBinderLocalObjectCount();

                sendEmptyMessageDelayed(0,100);
            }

        };
*/
/*
        Handler frontCharacter = new Handler()
        {

            public void handleMessage(Message msg)
            {
                character.bringToFront();

                sendEmptyMessageDelayed(0,10);
            }
        };

        frontCharacter.sendEmptyMessage(0);
*/
        land = new TouchImageView(this);
        land.setSize((1132 * ScreenParameter.getScreenparam_x()) , 1064 * ScreenParameter.getScreenparam_y());
        land.setLocation(140 * ScreenParameter.getScreenparam_x(), ScreenParameter.getScreenparam_y() * (782-85));
        land.setBackground(R.drawable.mainpage_land);
        main.addView(land);
        // land.startHandler();

        TouchButton toEntry = new TouchButton(this, R.drawable.main_arrow) {
            @Override
            public void touchEvent() {
                Intent intent = new Intent(mainActivity, Entry.class);
                mainActivity.startActivity(intent);
            }
        };
        toEntry.setSize(53 * ScreenParameter.getScreenparam_x(), 78 * ScreenParameter.getScreenparam_y());
        toEntry.setLocation(456 * ScreenParameter.getScreenparam_x(), (279-85) * ScreenParameter.getScreenparam_x());
        toEntry.setBackground(R.drawable.main_arrow);
        main.addView(toEntry);


        for(int i=0; i<3; i++)
        {
            item[i] = new mainButtonView(this,R.drawable.mainpage_item1 + 2*i, R.drawable.mainpage_item1 + 2*i + 1, i);
            item[i].getresources(main);
            item[i].setSize((int)(ScreenParameter.getScreenparam_x()*215), (int)(ScreenParameter.getScreenparam_y()*271));
            item[i].setLocation(ScreenParameter.getScreenparam_x() * (290 + i*(220 + 100)), ScreenParameter.getScreenparam_y()*(782 + 1064 + 60-85));
            main.addView(item[i]);
        }

        nfc = new ClickButtonView(this, R.drawable.mainpage_nfc, R.drawable.mainpage_nfc_clicked) {
            @Override
            public void clickEvent() {
                clear_condition_sub = clear_condition;
            }
        };   nfc.setSize((int) (ScreenParameter.getScreenparam_x() * 178), (int) (ScreenParameter.getScreenparam_x() * 178));
        nfc.setLocation(ScreenParameter.getScreenparam_x() * 82, ScreenParameter.getScreenparam_y() * 2221);
        main.addView(nfc);



        menu = new menuButton(this, R.drawable.mainpage_menu, R.drawable.mainpage_menu_clicked);

        menu.setMain(main);
        menu.setSize((int) (ScreenParameter.getScreenparam_x() * 178), (int) (ScreenParameter.getScreenparam_x() * 178));
        menu.setLocation(ScreenParameter.getScreenparam_x() * 1180, ScreenParameter.getScreenparam_y() * 2221);
        main.addView(menu);

        TouchImageView score = new TouchImageView(this);
        score.setSize(ScreenParameter.getScreenparam_x() * 452, ScreenParameter.getScreenparam_y() * 214);
        score.setLocation(ScreenParameter.getScreenparam_x() * 82, ScreenParameter.getScreenparam_y() * (160-85));
        score.setBackground(R.drawable.mainpage_score);
        main.addView(score);

        Cursor c = DbResource.db.rawQuery("select * from character",null);
        c.moveToNext();


        character = new Character(this);
        character.setSize(ScreenParameter.getScreenparam_x() * 179, ScreenParameter.getScreenparam_y() * 238);
        character.setLocation(ScreenParameter.getScreenparam_x() * 774.492 , ScreenParameter.getScreenparam_y() * (1088.444-85));
        character.setBackground(R.drawable.character_0 + c.getInt(1));
        character.setCanDrag(false);
        character.setCanMove(true);
        main.addView(character);

        money = new onTextView(this);
        money.setLocation(ScreenParameter.getScreenparam_x() * 209, ScreenParameter.getScreenparam_y() * (180-85));
        money.setText(Integer.toString(DbResource.getMoney()));
        money.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        money.setTextColor(Color.WHITE);
        main.addView(money);

        ticket = new onTextView(this);
        ticket.setLocation(ScreenParameter.getScreenparam_x() * 209, ScreenParameter.getScreenparam_y() * (288-85));
        ticket.setText(Integer.toString(DbResource.getTicket()));
        ticket.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45* ScreenParameter.getScreenparam_y()));
        ticket.setTextColor(Color.WHITE);
        main.addView(ticket);

        guide_character = null;


        Handler guideTimer_moon = new Handler()
        {
            int n = 0;

            public void handleMessage(Message msg)
            {
                if(n<3)
                    n++;

                if(check_notice<3 && guide_moon == null)
                    check_notice++;


                if(guide_moon == null && n == 3 && check_notice == 3 && canNotice && NOTICE)
                {
                    noti_num = new TouchImageView(context_this);
                    noti_num.setSize(51 * ScreenParameter.getScreenparam_x(), 51 * ScreenParameter.getScreenparam_y());
                    noti_num.setLocation(moon.getX() + 25 * ScreenParameter.getScreenparam_x(), moon.getY() - 20 * ScreenParameter.getScreenparam_y());
                    noti_num.setBackground(R.drawable.guide_95);
                    main.addView(noti_num);


                    guide_moon = new TouchImageView(context_this);
                    guide_moon.setSize(477 * ScreenParameter.getScreenparam_x(), 155 * ScreenParameter.getScreenparam_y());
                    guide_moon.setLocation(moon.getX()-15*ScreenParameter.getScreenparam_x(),  moon.getY() - 190* ScreenParameter.getScreenparam_y());
                    guide_moon.setBackground(R.drawable.guide_93);
                    guide_moon.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {

                            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                            //  check_notice--;

                            //if(check_notice == 0)
                            {
                                if(guide_character != null)
                                {
                                    main.removeView(guide_character);
                                    guide_character = null;
                                }

                                main.removeView(guide_moon);
                                guide_moon = null;

                                main.removeView(noti_num);
                                noti_num = null;

                                check_notice = 0;
                            }


                            return true;
                        }
                    });
                    main.addView(guide_moon);


                    // check_notice = 3;



                }

                Random rd = new Random();


                sendEmptyMessageDelayed(0,rd.nextInt(5) * 1000 + 5000);
            }
        };

        Handler guideTimer = new Handler()
        {
            int m = 0;

            public void handleMessage(Message msg)
            {
                if(m<3)
                    m++;

                if(guide_character == null && guide_moon != null && m==3 && canNotice && NOTICE)
                {
                    guide_character = new TouchImageView(context_this);
                    guide_character.setSize(477 * ScreenParameter.getScreenparam_x(), 177 * ScreenParameter.getScreenparam_y());
                    guide_character.setLocation(character.getX()+26*ScreenParameter.getScreenparam_x(), character.getY()- 180* ScreenParameter.getScreenparam_y());
                    guide_character.setBackground(R.drawable.guide_94);
                    main.addView(guide_character);
                    guide_character.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {

                            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                            //  check_notice--;

                            //if(check_notice == 0)
                            {
                                if(guide_character != null)
                                {
                                    main.removeView(guide_character);
                                    guide_character = null;
                                }

                                main.removeView(guide_moon);
                                guide_moon = null;

                                main.removeView(noti_num);
                                noti_num = null;

                                check_notice = 0;
                            }


                            return true;
                        }
                    });
                    guide_character.setCanMove(true);

                    //check_notice = 3;
                }


                Random rd = new Random();

                sendEmptyMessageDelayed(0,rd.nextInt(10) * 1000 + 5000);
            }
        };

        Handler guideTimer2 = new Handler()
        {
            int m = 0;

            public void handleMessage(Message msg)
            {
                if(m<3)
                    m++;

                if(guide_character != null && guide_moon != null && m==3 && canNotice && NOTICE)
                {
                    main.removeView(guide_character);
                    guide_character = null;

                }


                Random rd = new Random();

                sendEmptyMessageDelayed(0,rd.nextInt(10) * 1000 + 5000);
            }
        };


        guideTimer.sendEmptyMessage(0);
        guideTimer2.sendEmptyMessage(0);
        guideTimer_moon.sendEmptyMessage(0);





/*
        TouchImageView grid = new TouchImageView(this);
        grid.setSize((1132 * screenparam_x()) , 592 * screenparam_y());
        grid.setLocation(140/DEFAULTSIZE_X * ScreenSize().x, ScreenSize().y / DEFAULTSIZE_Y * 782);
        grid.setBackground(R.drawable.grid);
        grid.setHandler(landmonivg);
        main.addView(grid);
        grid.setCanMove(true);
*/

/*
        itemView = new ItemView(this, viewList);
        itemView.setCanDrag(true);
        itemView.setImges(R.drawable.item_right_h, R.drawable.item_left_h);
        itemView.setSize(100, 233);
        itemView.setLocation(ObjectSizeLocation.point[0][0] , ObjectSizeLocation.point[0][1] );
        itemView.setPivot(new Point(-10,+225));
        main.addView(itemView);

*/

        /*
        for(int i=0; i<ObjectSizeLocation.point.length; i++)
        {
            TouchImageView Grid = new TouchImageView(this);
            Grid.setSize(10,10);
            Grid.setLocation(ObjectSizeLocation.point[i][0], ObjectSizeLocation.point[i][1]);
            Grid.setBackground(R.drawable.start);
            main.addView(Grid);
        }

        for(int i=0; i<g_point.size(); i++)
        {
            TouchImageView Grid = new TouchImageView(this);
            Grid.setSize(10,10);
            Grid.setLocation(g_point.get(i).x, g_point.get(i).y);
            Grid.setBackground(R.drawable.arrow);
            main.addView(Grid);
        }
        */

        try {
            ischeck = new onTextView(this);
            ischeck.setLocation(500, 1000);
            ischeck.setText(Integer.toString(g_point.indexOf(new GridPoint(146, 1031))) + "  " + g_point.get(180).x);
            ischeck.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
            ischeck.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
            ischeck.setTextColor(Color.rgb(255, 255, 255));
            main.addView(ischeck);
        }
        catch(Exception e)
        {
            Log.d("//////////", e.toString());
        }

        Cursor itemCursor = DbResource.db.rawQuery("select * from point", null);

        while(itemCursor.moveToNext())
        {
            Log.e("messagegegege", "HHHHHHHH");

            GridPoint p = g_point.get(itemCursor.getInt(itemCursor.getColumnIndex("point_index")));
            Log.e("//asd//asdasd//", Integer.toString(p.y));
            //p = new GridPoint(g_point.get(180).x, g_point.get(180).y);
            int item_index = itemCursor.getInt(itemCursor.getColumnIndex("item_index"));
            ItemView item = new ItemView(this, item_index);
            item.setSize(ObjectSizeLocation.size_item_locate[item_index][0]* ScreenParameter.getScreenparam_x(),ObjectSizeLocation.size_item_locate[item_index][1]* ScreenParameter.getScreenparam_y());
            item.setMain(main);
            item.setIsright(false);
            item.setIsright(itemCursor.getInt(itemCursor.getColumnIndex("right")) == 1 ? true : false);

            if (item.IsRight())
                item.setLocation((Math.abs(p.x) - ObjectSizeLocation.pivot_position_left[item_index].x * ScreenParameter.getScreenparam_x()), p.y - ObjectSizeLocation.pivot_position_left[item_index].y * ScreenParameter.getScreenparam_y());
            else
                item.setLocation(Math.abs(p.x) - ObjectSizeLocation.pivot_position_left[item_index].x * ScreenParameter.getScreenparam_x(), p.y - ObjectSizeLocation.pivot_position_left[item_index].y * ScreenParameter.getScreenparam_y());


            if (item.IsRight())
                item.setBackground(R.drawable.item_right_a + item.getIndex());
            else
                item.setBackground(R.drawable.item_left_a + item.getIndex());

            item.setCanDrag(false);
            item.setState(1);
            item.setPoint(p, itemCursor.getInt(itemCursor.getColumnIndex("right")) == 1 ? true : false);
            item.midPoint = p;
            main.addView(item);

            for (int i = 0; i < item.getPoint().size(); i++) {
                item.getPoint().get(i).setItem_index(item.getIndex());
                item.getPoint().get(i).setCanPut(false);
                item.getPoint().get(i).setOnitem(item);
                item.getPoint().get(i).setOnitem(true);
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

        }

        itemCursor.close();


        nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            intentFilter.addDataType("/");
            intentFilters = new IntentFilter[]{intentFilter};
        }
        catch (Exception e)
        {

        }
        finally {
            nfcLists = new String[][] {
                    new String[] {NfcF.class.getName()}
            };
        }

    }


    ///////FINISH EVENT

    // define variables for back key : 2 pressed end!
    private boolean isBackKeyPressed = false;             // flag
    private long currentTimeByMillis = 0;                     // calculate time interval

    private static final int MSG_TIMER_EXPIRED = 1;    // switch - key
    private static final int BACKKEY_TIMEOUT = 2;      // define interval
    private static final int MILLIS_IN_SEC = 1000;        // define millisecond
    // end of back key variable.


    @Override
    public void onBackPressed()
    {
        if(menu.IsClicked())
            menu.UnClick();
        else if(item[0].IsClicked())
        {
            item[0].UnClick();
        }
        else if(item[1].IsClicked())
        {
            item[1].UnClick();
        }
        else if(item[2].IsClicked())
        {
            item[2].UnClick();
        }
        else
        {
            if ( isBackKeyPressed == false ){
                // first click
                isBackKeyPressed = true;

                currentTimeByMillis = Calendar.getInstance().getTimeInMillis();
                Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();

                startTimer();
            }else{
                // second click : 2초 이내면 종료! 아니면 아무것도 안한다.
                isBackKeyPressed = false;
                if ( Calendar.getInstance().getTimeInMillis() <= (currentTimeByMillis + (BACKKEY_TIMEOUT * MILLIS_IN_SEC)) ) {
                    finish();
                }
            }
        }


    }


    // startTimer : 2초의 시간적 여유를 가지게 delay 시킨다.
    private void startTimer(){
        backTimerHandler.sendEmptyMessageDelayed(MSG_TIMER_EXPIRED, BACKKEY_TIMEOUT * MILLIS_IN_SEC);
    }

    private Handler backTimerHandler = new Handler(){
        public void handleMessage(Message msg){
            switch( msg.what ){
                case MSG_TIMER_EXPIRED:{
                    isBackKeyPressed = false;
                }
                break;
            }
        }
    };


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


    public Point ScreenSize() { //현재 스크린의 사이즈를 가져오는 메서드. 정형화된 틀이다.

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Point size = new Point(width, height);

        return size;

    }

    public int narrowSize() {
        int x = ScreenSize().x; //현재 스크린의 사이즈; 가로, 세로 화면.
        int y = ScreenSize().y;

        return (x > y ? y : x);
    }

    public int wideSize() {
        int x = ScreenSize().x;
        int y = ScreenSize().y;

        return (x < y ? y : x);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        DbResource.setTicket(DbResource.getTicket() + 1);
        ticket.setText(Integer.toString(DbResource.getTicket()));
        DbResource.db.execSQL("update resources set numb = " + DbResource.getTicket() + " where attr ='ticket';");

        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(nfcAdapter != null)
            nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(nfcAdapter != null)
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }
}

