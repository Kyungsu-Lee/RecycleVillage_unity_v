package com.example.lmasi.recyclevillage;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class Notice extends Activity {

    private RelativeLayout main;
    private TouchImageView point;
    SlideView slideView;
    private int index;
    NoticeButton[] btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notice);

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

        onTextView title = new onTextView(this);
        title.setText("공지사항");
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (60 * ScreenParameter.getScreenparam_y()));
        title.setTextColor(Color.rgb(255, 255, 255));
        title.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        title.setLocation(617 * ScreenParameter.getScreenparam_x(), 63 * ScreenParameter.getScreenparam_y());
        main.addView(title);

        btn = new NoticeButton[5];
        for (int i = 0; i < 5; i++) {
            btn[i] = new NoticeButton(this, R.drawable.off_1 + i, R.drawable.on_1 + i);
            btn[i].setSize(167 * ScreenParameter.getScreenparam_x(), 167 * ScreenParameter.getScreenparam_y());
            btn[i].setLocation((150 + i * (167 + 84)) * ScreenParameter.getScreenparam_x(), 279 * ScreenParameter.getScreenparam_y());
            btn[i].setId(i);
            main.addView(btn[i]);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    point.setSize(15 * ScreenParameter.getScreenparam_x(), 15 * ScreenParameter.getScreenparam_x());
                    point.setLocation(v.getX() + (v.getWidth() / 2 - point.getWidth() / 2) + 2* ScreenParameter.getScreenparam_x(),  v.getY() - 40 * ScreenParameter.getScreenparam_y());
                    point.setBackground(R.drawable.on_point);

                    slideView.setSpeed(-slideView.getWIDTH() * slideView.getCount() * 1.75 * Math.abs(slideView.getIndex() - v.getId()));
                    slideView.setTime(200 + 100 * Math.abs(slideView.getIndex() - v.getId()));
                    slideView.slidepage(v.getId());

                }
            });
        }

        point = new TouchImageView(this);
        point.setSize(15 * ScreenParameter.getScreenparam_x(), 15 * ScreenParameter.getScreenparam_y());
        point.setLocation((135 + (167 / 2 - 15 / 2) + 2)* ScreenParameter.getScreenparam_x(), 244* ScreenParameter.getScreenparam_y());
        point.setBackground(R.drawable.on_point);
        main.addView(point);

        btn[0].activate();




        final ListScrollView[] scrollView = new ListScrollView[5];
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (1201* ScreenParameter.getScreenparam_x()), (int) (2000 * ScreenParameter.getScreenparam_y()));
        params.setMargins(0, 0, 0, 0);
        for (int i = 0; i < 5; i++) {
            scrollView[i] = new ListScrollView(this);
            scrollView[i].setLayoutParams(params);
        }

        CardView[] card = new CardView[Resource.notice.length * Resource.notice[0].length];
//        for (int i = 0; i < 100; i++) {
//            card[i] = new CardView(this, );
//            card[i].setSize(1201* ScreenParameter.getScreenparam_x(), 200* ScreenParameter.getScreenparam_y());
//            card[i].setLocation(120* ScreenParameter.getScreenparam_x(), 527* ScreenParameter.getScreenparam_y());
//        }
//
//        for (int j = 0; j < 5; j++) {
//
//            for (int k = 0; k < 20; k++)
//                scrollView[j].addCard(card[20  * j + k]);
//        }

        for(int i=0; i<5; i++)
        {
            for(int j=0; j<Resource.notice[i].length; j++)
            {
                card[i] = new CardView(this, Resource.notice_title[i][j], Resource.notice[i][j]);
                card[i].setSize(1201* ScreenParameter.getScreenparam_x(), 200* ScreenParameter.getScreenparam_y());
                card[i].setLocation(120* ScreenParameter.getScreenparam_x(), 527* ScreenParameter.getScreenparam_y());
                scrollView[i].addCard(card[i]);
            }
        }

        Handler ha = new Handler()
        {
            boolean condition = true;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                /*
                scrollView[0].setSpeed(1000);

                if(condition)
                    scrollView[0].scrollTop();
                else
                    scrollView[0].scrollBottom();

                condition = !condition;
*/
            }
        };

        ha.sendEmptyMessage(0);


        //  scrollView.setBackgroundColor(Color.MAGENTA);

        slideView = new SlideView(this) {
            @Override
            public void BeforeAction() {
                for (int i = 0; i < 5; i++)
                {
                    btn[i].setOnClickListener(null);
                    btn[i].canClick = false;
                }
            }

            @Override
            public void AfterAction() {

                for (int i = 0; i < 5; i++)
                {
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            point.setSize(15 * ScreenParameter.getScreenparam_x(), 15* ScreenParameter.getScreenparam_y());
                            point.setLocation(v.getX() + (v.getWidth() / 2 - point.getWidth() / 2) + 2* ScreenParameter.getScreenparam_x(),  v.getY() - 40 * ScreenParameter.getScreenparam_y());
                            point.setBackground(R.drawable.on_point);

                            slideView.setSpeed(-slideView.getWIDTH() * slideView.getCount() * 3);
                            slideView.setTime(500);
                            slideView.slidepage(v.getId());


                        }
                    });


                    btn[i].canClick = true;
                }


            }

            @Override
            public void onDrag() {
            }

        };
        slideView.setSize((int) (1201* ScreenParameter.getScreenparam_x()), (int) (2000* ScreenParameter.getScreenparam_y()));
        slideView.setLocation(126* ScreenParameter.getScreenparam_x(), 484* ScreenParameter.getScreenparam_y());
        main.addView(slideView);

        RelativeLayout[] relativeLayouts = new RelativeLayout[5];

        for (int i = 0; i < 5; i++) {
            relativeLayouts[i] = new RelativeLayout(this);
            slideView.addLayout(relativeLayouts[i]);
            relativeLayouts[i].addView(scrollView[i]);

            scrollView[i].showCard();
        }

    }
}