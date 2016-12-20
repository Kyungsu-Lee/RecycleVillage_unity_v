package com.example.lmasi.recyclevillage;

/**
 * Created by Administrator on 2016-07-13.
 */

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class GateOnTextView extends TextView {

    RelativeLayout.LayoutParams params;

    GateOnTextView(Context context)
    {
        super(context);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
    }


    public void setLocation(double x, double y)
    {
        params.setMargins((int)x,(int)y,0,0);
    }
}
