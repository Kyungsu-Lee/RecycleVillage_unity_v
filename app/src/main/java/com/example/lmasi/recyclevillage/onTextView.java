package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class onTextView extends TextView {

    RelativeLayout.LayoutParams params;

    onTextView(Context context)
    {
        super(context);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
    }

    public void setLocation(double x, double y)
    {
        params.setMargins((int)x,(int)y,0,0);
    }

    public void setScaleAnim_char_title(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726-700   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-940-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.3));
//        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726.122   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882  * ScreenParameter.getScreenparam_y()));
    }

    public void setScaleAnim_char_price(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (726-790+50   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1435-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.3));
        //anim.setDuration((int)(speed * 0.3));
    }

    public void setScaleAnim_char_info(int speed)
    {
        ScaleAnimation anim =  new ScaleAnimation(0, 1, 0, 1, Animation.ABSOLUTE, (float) (723-700   * ScreenParameter.getScreenparam_x()), Animation.ABSOLUTE, (float) (1882-1050-85  * ScreenParameter.getScreenparam_y()));
        this.startAnimation(anim);
        anim.setDuration((int)(speed * 0.3));
    }

    public void showAnim(int speed)
    {
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.25f, Animation.RELATIVE_TO_SELF, 1);
        this.startAnimation(anim);
        anim.setDuration(speed);
    }

}
