package com.example.lmasi.recyclevillage;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class Guide extends Activity{

    RelativeLayout main;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);

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
        title.setText("가이드");
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (60 * ScreenParameter.getScreenparam_x()));
        title.setTextColor(Color.rgb(255, 255, 255));
        title.setTypeface(Typeface.createFromAsset(this.getAssets(), "KoPubDotumMedium_0.ttf"));
        title.setLocation(641 * ScreenParameter.getScreenparam_x(), 63 * ScreenParameter.getScreenparam_y());
        main.addView(title);

        TouchImageView frame = new TouchImageView(this);
        frame.setSize(1036 * ScreenParameter.getScreenparam_x(),1596 * ScreenParameter.getScreenparam_y());
        frame.setLocation(205 * ScreenParameter.getScreenparam_x(),295 * ScreenParameter.getScreenparam_y());
        frame.setBackground(R.drawable.guide_frame);
        main.addView(frame);

        GuideLayout guideLayout = new GuideLayout(this);
        guideLayout.setSize(895 * ScreenParameter.getScreenparam_x(),1596 * ScreenParameter.getScreenparam_y());
        guideLayout.setLocation(205 * ScreenParameter.getScreenparam_x(),295 * ScreenParameter.getScreenparam_y());
        guideLayout.setLayout(index);
        guideLayout.setMain(guideLayout);
        main.addView(guideLayout);
        guideLayout.viewLayout();

        GuideLayoutView[] btn = new GuideLayoutView[12];
        for(int i=0;i<12;i++)
        {
            btn[i] = new GuideLayoutView(this,R.drawable.guide_btn_unclick_a+i, R.drawable.guide_btn_clicked_a+i,i);
            btn[i].setSize(145 * ScreenParameter.getScreenparam_x(),141 * ScreenParameter.getScreenparam_y());
            if(i<6)
            {
                btn[i].setLocation(216 * ScreenParameter.getScreenparam_x() + i*(176 * ScreenParameter.getScreenparam_x()),1967 * ScreenParameter.getScreenparam_y());
                btn[i].setGuideLayout(guideLayout);
                main.addView(btn[i]);
            }
            else
            {
                btn[i].setLocation(216 * ScreenParameter.getScreenparam_x() + (i-6)*(176 * ScreenParameter.getScreenparam_x()),2171 * ScreenParameter.getScreenparam_y());
                btn[i].setGuideLayout(guideLayout);
                main.addView(btn[i]);
            }
        }
        btn[0].activate();
    }
}
