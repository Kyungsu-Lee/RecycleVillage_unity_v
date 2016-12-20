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
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class Rank extends Activity {

    RelativeLayout main;
    Handler handler;
    Handler handler2;

    int myRank;

    onTextView gate;
    onTextView sea;

    //////////////////////////remove
    TouchImageView bar;
    TouchImageView frame;
    ListScrollView scrollView;
    TouchImageView[] rank_character = new TouchImageView[3];
    TouchImageView[] rank_frame = new TouchImageView[3];
    onTextView[] rank_name = new onTextView[3];
    RankCard[] card = new RankCard[20];
    BorderTextView[] rank_score = new BorderTextView[3];



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);

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


/////////////////////////////////////////////////////
        try {

            InputStream inputStream = openFileInput("best.txt");
            int i = 0;

            StringBuffer buffer = new StringBuffer();
            byte[] b = new byte[1000];

            while((i = inputStream.read(b)) != -1)
            {
                buffer.append(new String(b,0, i));
            }

            String str = buffer.toString();

            DbResource.db.execSQL("update resources set numb = " + str + " where attr='Gamepoint_sea';");
        }
        catch (Exception e)
        {
            Log.e("FILE READ ERROR", e.toString());
        }

        /////////////////////////////////////

        onTextView title = new onTextView(this);
        title.setText("랭킹");
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (60 * ScreenParameter.getScreenparam_y()));
        title.setTextColor(Color.rgb(255, 255, 255));
        title.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        title.setLocation(675 * ScreenParameter.getScreenparam_x(), 63 * ScreenParameter.getScreenparam_y());
        main.addView(title);

        gate = new onTextView(this);
        gate.setText("게이트러너");
        gate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (55 * ScreenParameter.getScreenparam_y()));
        gate.setTextColor(Color.rgb(0, 255, 255));
        gate.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        gate.setLocation(383 * ScreenParameter.getScreenparam_x(), 236 * ScreenParameter.getScreenparam_y());
        main.addView(gate);
        gate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gateRunner();
                return false;
            }
        });

        sea = new onTextView(this);
        sea.setText("해저탐험");
        sea.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (55 * ScreenParameter.getScreenparam_y()));
        sea.setTextColor(Color.rgb(209, 211, 212));
        sea.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        sea.setLocation(828 * ScreenParameter.getScreenparam_x(), 236 * ScreenParameter.getScreenparam_y());
        main.addView(sea);
        sea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Sea();
                return false;
            }
        });

        gateRunner();
    }

    private void gateRunner()
    {
        removeViews();

        gate.setTextColor(Color.rgb(0,255,255));
        sea.setTextColor(Color.WHITE);

        bar = new TouchImageView(this);
        bar.setSize(3 * ScreenParameter.getScreenparam_x(),36 * ScreenParameter.getScreenparam_y());
        bar.setLocation(718 * ScreenParameter.getScreenparam_x(),243 * ScreenParameter.getScreenparam_y());
        bar.setBackground(R.drawable.rank_bar);
        main.addView(bar);

        frame = new TouchImageView(this);
        frame.setSize(1370 * ScreenParameter.getScreenparam_x(),1533 * ScreenParameter.getScreenparam_y());
        frame.setLocation(34 * ScreenParameter.getScreenparam_x(),898 * ScreenParameter.getScreenparam_y());
        frame.setBackground(R.drawable.rank_scroll);
        main.addView(frame);

        scrollView = new ListScrollView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (1382* ScreenParameter.getScreenparam_x()), (int) (1520 * ScreenParameter.getScreenparam_y()));
        params.setMargins((int) (10 * ScreenParameter.getScreenparam_x()), (int) (905* ScreenParameter.getScreenparam_y()), 0, 0);
        scrollView.setLayoutParams(params);
        scrollView.setBackgroundColor(Color.rgb(29,43,55));
        main.addView(scrollView);


        for(int i=0; i<rank_character.length; i++)
        {
            rank_character[i] = new TouchImageView(this);
            rank_character[i].setSize(265 * ScreenParameter.getScreenparam_x(),  265 * ScreenParameter.getScreenparam_y());
            main.addView(rank_character[i]);
        }

        rank_character[0].setLocation(590 * ScreenParameter.getScreenparam_x(), 350 * ScreenParameter.getScreenparam_y());
        rank_character[1].setLocation(207 * ScreenParameter.getScreenparam_x(), 467 * ScreenParameter.getScreenparam_y());
        rank_character[2].setLocation(971 * ScreenParameter.getScreenparam_x(), 467 * ScreenParameter.getScreenparam_y());


        for(int i=0; i<rank_frame.length; i++)
        {
            rank_frame[i] = new TouchImageView(this);
            rank_frame[i].setSize(389 * ScreenParameter.getScreenparam_x(),  327 * ScreenParameter.getScreenparam_y());
            rank_frame[i].setBackground(R.drawable.rank_1 + i);
            main.addView(rank_frame[i]);
        }

        rank_frame[0].setLocation(526 * ScreenParameter.getScreenparam_x(), 342 * ScreenParameter.getScreenparam_y());
        rank_frame[1].setLocation(143 * ScreenParameter.getScreenparam_x(), 458 * ScreenParameter.getScreenparam_y());
        rank_frame[2].setLocation(907 * ScreenParameter.getScreenparam_x(), 458 * ScreenParameter.getScreenparam_y());


        for(int i=0; i<rank_name.length; i++)
        {
            rank_name[i] = new onTextView(this);
            rank_name[i].setTextColor(Color.rgb(0,255,255));
            rank_name[i].setTypeface(MainActivity.typeface);
            rank_name[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (55* ScreenParameter.getScreenparam_y()));
            main.addView(rank_name[i]);
        }

        for(int i=0 ;i<3; i++)
        {
            rank_score[i] = new BorderTextView(this);
            rank_score[i].setTypeface(MainActivity.typeface);
            rank_score[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (65* ScreenParameter.getScreenparam_y()));
            main.addView(rank_score[i]);
        }



        Cursor c =  DbResource.db.rawQuery("select numb from resources where attr='Gamepoint_gate';", null);
        c.moveToNext();

        card[0] = new RankCard(this);
        card[0].name.setText(Resource.rank_name[0]);
        card[0].point.setText(Integer.toString(c.getInt(0)));
        card[0].profile.setBackground(R.drawable.c_profile_01+Resource.rank_profile[0]);
        card[0].profileIndex = 3;

        for (int i = 1; i < card.length; i++) {
            card[i] = new RankCard(this);
            card[i].name.setText(Resource.rank_name[i>19 ? 19 : i]);
            card[i].point.setText(Integer.toString(Resource.rank_point_gate[i>19 ? 19 : i]));
            card[i].profile.setBackground(R.drawable.c_profile_01+Resource.rank_profile[i>19 ? 19 : i]);
            card[i].profileIndex = Resource.rank_profile[i>19 ? 19 : i];
        }

        Arrays.sort(card, new RankCompare());

        for(int i=0; i<card.length; i++)
        {
            scrollView.addRankCard(card[i]);
            card[i].grade.setText((i+1)+"위");

            if(card[i].name.getText().toString().equals("우주 소년"))
                myRank = i;
        }

        card[myRank].setBackgroundColor(Color.argb(51,0,255,255));
        scrollView.showRankCard();


        for(int i=0; i<3; i++)
        {
            rank_character[i].setBackground(R.drawable.c_profile_01 + card[i].profileIndex);
            rank_character[i].measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            rank_name[i].setText(card[i].name.getText());
            rank_name[i].measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            rank_name[1].setLocation((348/2+165) * ScreenParameter.getScreenparam_x() - rank_name[1].getMeasuredWidth()/2 , (800-85) * ScreenParameter.getScreenparam_y());
            rank_name[0].setLocation((348/2+545) * ScreenParameter.getScreenparam_x() - rank_name[0].getMeasuredWidth()/2 , (682-85) * ScreenParameter.getScreenparam_y());
            rank_name[2].setLocation((348/2+930) * ScreenParameter.getScreenparam_x() - rank_name[2].getMeasuredWidth()/2, (800-85) * ScreenParameter.getScreenparam_y());
            rank_score[i].setText(card[i].point.getText());
            rank_score[i].measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            rank_score[1].setLocation((348/2+165) * ScreenParameter.getScreenparam_x() -  rank_score[1].getMeasuredWidth()/2, (880-85) * ScreenParameter.getScreenparam_y());
            rank_score[0].setLocation((348/2+550) * ScreenParameter.getScreenparam_x() - rank_score[0].getMeasuredWidth()/2 , (763-85) * ScreenParameter.getScreenparam_y());
            rank_score[2].setLocation((348/2+930) * ScreenParameter.getScreenparam_x() - rank_score[2].getMeasuredWidth()/2 , (880-85) * ScreenParameter.getScreenparam_y());

        }


        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                scrollView.scrollBottom();
                scrollView.setSpeed(1000);
            }
        };
        handler2 = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                scrollView.setSpeed(900);
                scrollView.scrollAt(myRank);
            }
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler2.sendEmptyMessage(0);
            }
        }, 900);

        frame.bringToFront();
    }

    private void Sea()
    {
        removeViews();

        sea.setTextColor(Color.rgb(0,255,255));
        gate.setTextColor(Color.WHITE);

        bar = new TouchImageView(this);
        bar.setSize(3 * ScreenParameter.getScreenparam_x(),36 * ScreenParameter.getScreenparam_y());
        bar.setLocation(718 * ScreenParameter.getScreenparam_x(),243 * ScreenParameter.getScreenparam_y());
        bar.setBackground(R.drawable.rank_bar);
        main.addView(bar);

        frame = new TouchImageView(this);
        frame.setSize(1370 * ScreenParameter.getScreenparam_x(),1533 * ScreenParameter.getScreenparam_y());
        frame.setLocation(34 * ScreenParameter.getScreenparam_x(),898 * ScreenParameter.getScreenparam_y());
        frame.setBackground(R.drawable.rank_scroll);
        main.addView(frame);

        scrollView = new ListScrollView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (1382* ScreenParameter.getScreenparam_x()), (int) (1520 * ScreenParameter.getScreenparam_y()));
        params.setMargins((int) (10 * ScreenParameter.getScreenparam_x()), (int) (905* ScreenParameter.getScreenparam_y()), 0, 0);
        scrollView.setLayoutParams(params);
        scrollView.setBackgroundColor(Color.rgb(29,43,55));
        main.addView(scrollView);


        for(int i=0; i<rank_character.length; i++)
        {
            rank_character[i] = new TouchImageView(this);
            rank_character[i].setSize(265 * ScreenParameter.getScreenparam_x(),  265 * ScreenParameter.getScreenparam_y());
            main.addView(rank_character[i]);
        }

        rank_character[0].setLocation(590 * ScreenParameter.getScreenparam_x(), 350 * ScreenParameter.getScreenparam_y());
        rank_character[1].setLocation(207 * ScreenParameter.getScreenparam_x(), 467 * ScreenParameter.getScreenparam_y());
        rank_character[2].setLocation(971 * ScreenParameter.getScreenparam_x(), 467 * ScreenParameter.getScreenparam_y());


        for(int i=0; i<rank_frame.length; i++)
        {
            rank_frame[i] = new TouchImageView(this);
            rank_frame[i].setSize(389 * ScreenParameter.getScreenparam_x(),  327 * ScreenParameter.getScreenparam_y());
            rank_frame[i].setBackground(R.drawable.rank_1 + i);
            main.addView(rank_frame[i]);
        }

        rank_frame[0].setLocation(526 * ScreenParameter.getScreenparam_x(), 342 * ScreenParameter.getScreenparam_y());
        rank_frame[1].setLocation(143 * ScreenParameter.getScreenparam_x(), 458 * ScreenParameter.getScreenparam_y());
        rank_frame[2].setLocation(907 * ScreenParameter.getScreenparam_x(), 458 * ScreenParameter.getScreenparam_y());


        for(int i=0; i<rank_name.length; i++)
        {
            rank_name[i] = new onTextView(this);
            rank_name[i].setTextColor(Color.rgb(0,255,255));
            rank_name[i].setTypeface(MainActivity.typeface);
            rank_name[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (55* ScreenParameter.getScreenparam_y()));
            main.addView(rank_name[i]);
        }

        for(int i=0 ;i<3; i++)
        {
            rank_score[i] = new BorderTextView(this);
            rank_score[i].setTypeface(MainActivity.typeface);
            rank_score[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (65* ScreenParameter.getScreenparam_y()));
            main.addView(rank_score[i]);
        }



        Cursor c =  DbResource.db.rawQuery("select numb from resources where attr='Gamepoint_sea';", null);
        c.moveToNext();

        card[0] = new RankCard(this);
        card[0].name.setText(Resource.rank_name[0]);
        card[0].point.setText(Integer.toString(c.getInt(0)));
        card[0].profile.setBackground(R.drawable.c_profile_01+Resource.rank_profile[0]);
        card[0].profileIndex = 3;

        for (int i = 1; i < card.length; i++) {
            card[i] = new RankCard(this);
            card[i].name.setText(Resource.rank_name[i>19 ? 19 : i]);
            card[i].point.setText(Integer.toString(Resource.rank_point_sea[i>19 ? 19 : i]));
            card[i].profile.setBackground(R.drawable.c_profile_01+Resource.rank_profile[i>19 ? 19 : i]);
            card[i].profileIndex = Resource.rank_profile[i>19 ? 19 : i];
        }

        Arrays.sort(card, new RankCompare());

        for(int i=0; i<card.length; i++)
        {
            scrollView.addRankCard(card[i]);
            card[i].grade.setText((i+1)+"위");

            if(card[i].name.getText().toString().equals("우주 소년"))
                myRank = i;
        }

        card[myRank].setBackgroundColor(Color.argb(51,0,255,255));
        scrollView.showRankCard();


        for(int i=0; i<3; i++)
        {
            rank_character[i].setBackground(R.drawable.c_profile_01 + card[i].profileIndex);
            rank_character[i].measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            rank_name[i].setText(card[i].name.getText());
            rank_name[i].measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            rank_name[1].setLocation((348/2+165) * ScreenParameter.getScreenparam_x() - rank_name[1].getMeasuredWidth()/2 , (800-85) * ScreenParameter.getScreenparam_y());
            rank_name[0].setLocation((348/2+545) * ScreenParameter.getScreenparam_x() - rank_name[0].getMeasuredWidth()/2 , (682-85) * ScreenParameter.getScreenparam_y());
            rank_name[2].setLocation((348/2+930) * ScreenParameter.getScreenparam_x() - rank_name[2].getMeasuredWidth()/2, (800-85) * ScreenParameter.getScreenparam_y());
            rank_score[i].setText(card[i].point.getText());
            rank_score[i].measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            rank_score[1].setLocation((348/2+165) * ScreenParameter.getScreenparam_x() -  rank_score[1].getMeasuredWidth()/2, (880-85) * ScreenParameter.getScreenparam_y());
            rank_score[0].setLocation((348/2+550) * ScreenParameter.getScreenparam_x() - rank_score[0].getMeasuredWidth()/2 , (763-85) * ScreenParameter.getScreenparam_y());
            rank_score[2].setLocation((348/2+930) * ScreenParameter.getScreenparam_x() - rank_score[2].getMeasuredWidth()/2 , (880-85) * ScreenParameter.getScreenparam_y());

        }
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                scrollView.scrollBottom();
                scrollView.setSpeed(1000);
            }
        };
        handler2 = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                scrollView.setSpeed(900);
                scrollView.scrollAt(myRank);
            }
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler2.sendEmptyMessage(0);
            }
        }, 900);

        frame.bringToFront();


    }

    public void removeViews()
    {
        main.removeView(bar);
        main.removeView(frame);
        main.removeView(scrollView);
        for(int i=0; i<rank_character.length; i++)
        {
            main.removeView(rank_character[i]);
            main.removeView(rank_frame[i]);
            main.removeView(rank_name[i]);
            main.removeView(card[i]);
            main.removeView(rank_score[i]);
        }


        System.gc();

    }



    static class RankCompare implements Comparator<RankCard>
    {

        @Override
        public int compare(RankCard rankCard, RankCard other)
        {
            return (Integer.parseInt(rankCard.point.getText().toString()) < Integer.parseInt(other.point.getText().toString())) ? 1 : -1;
        }
    }
}