package com.example.lmasi.recyclevillage;

/**
 * Created by Administrator on 2016-07-26.
 */
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GateStrokeTextView extends TextView {

    private boolean stroke = false;
    private float strokeWidth = 0.0f;
    private int strokeColor;
    AttributeSet attrs;

    RelativeLayout.LayoutParams params;

    public GateStrokeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context, attrs);
    }

    public GateStrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public GateStrokeTextView(Context context) {
        super(context);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);

        initView(context, attrs);
    }


    public void setLocation(double x, double y)
    {
        params.setMargins((int)x,(int)y,0,0);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        stroke = a.getBoolean(R.styleable.CustomTextView_textStroke, true);
        strokeWidth = a.getFloat(R.styleable.CustomTextView_textStrokeWidth, 3f);
        strokeColor = a.getColor(R.styleable.CustomTextView_textStrokeColor, 0xffffffff);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (stroke) {
            ColorStateList states = getTextColors();
            getPaint().setStyle(Style.STROKE);
            getPaint().setStrokeWidth(strokeWidth);
            setTextColor(strokeColor);
            super.onDraw(canvas);

            getPaint().setStyle(Style.FILL);
            setTextColor(states);
        }

        super.onDraw(canvas);
    }
}