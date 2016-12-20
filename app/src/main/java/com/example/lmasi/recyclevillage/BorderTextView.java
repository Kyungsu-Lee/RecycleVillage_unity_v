package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by lmasi on 2016-08-20.
 */
public class BorderTextView extends onTextView {


    BorderTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        ColorStateList states = getTextColors(); // text color 값 저장

        // stroke 그리기
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setStrokeWidth(4);
        setTextColor(Color.rgb(255,242,0));
        super.onDraw(canvas);

        // stroke 위에 그리기
        getPaint().setStyle(Paint.Style.FILL);
        setTextColor(states);
        super.onDraw(canvas);

        setTextColor(Color.rgb(255,176,0));
    }
}
