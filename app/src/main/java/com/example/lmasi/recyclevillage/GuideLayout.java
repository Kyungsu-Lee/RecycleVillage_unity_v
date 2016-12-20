package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GuideLayout extends RelativeLayout{

    private TouchImageView mainImage;
    private onTextView name;
    private onTextView category;
    private onTextView how;
    private onTextView cannot;
    private RelativeLayout layout;
    private RelativeLayout main;
    private int index;

    Context activity;

    private RelativeLayout.LayoutParams params;
    private int WIDTH;
    private int HEIGHT;
    private int LX;
    private int LY;

    public int setWidth(int width)
    {
        this.WIDTH = width;
        return width;
    }

    public int setHeight(int height)
    {
        this.HEIGHT = height;
        return height;
    }

    public int getWIDTH() {return this.WIDTH;}
    public int getHEIGHT() {return this.HEIGHT;}

    private void setsize(double width, double height)
    {
        setWidth((int)width);
        setHeight((int)height);
    }

    public void setSize(double width, double height)
    {
        params = new RelativeLayout.LayoutParams((int)width, (int)height);
        this.setLayoutParams(params);
        setsize(width, height);
    }


    public void setLX(double x){this.LX = (int)x;}
    public void setLY(double y){this.LY = (int)y;}

    public int getLX(){return this.LX;}
    public int getLY(){return this.LY;}

    public void setLocation(double x, double y)
    {
        if(params != null)
            params.setMargins((int)x,(int)y,0,0);

        this.LX = (int)x;
        this.LY = (int)y;
    }

    public void setMain(RelativeLayout main)
    {
        this.main = main;
    }
    public RelativeLayout getMain()
    {
        return main;
    }

    public void setMainImage(int index)
    {
        mainImage.setBackground(R.drawable.guide_main_a+index);
    }
    public void setName(int index)
    {
        name.setText(Resource.guide_name[index]);
    }
    public void setCategory(int index)
    {
        category.setText(Resource.guide_category[index]);
    }
    public void setHow(int index)
    {
        how.setText(Resource.guide_guide[index]);
    }
    public void setCannot(int index)
    {
        cannot.setText(Resource.guide_cannot[index]);
    }


    public GuideLayout(Context context){
        super(context);

        activity = context;

        mainImage = new TouchImageView(context);
        mainImage.setSize(180 * ScreenParameter.getScreenparam_x(),180 * ScreenParameter.getScreenparam_y());
        mainImage.setLocation(423 * ScreenParameter.getScreenparam_x(),136 * ScreenParameter.getScreenparam_y());
        this.addView(mainImage);

        name = new onTextView(context);
        name.setLocation(ScreenParameter.getScreenparam_x() * 135, ScreenParameter.getScreenparam_y() * 425);
        name.setTypeface(Typeface.createFromAsset(context.getAssets(), "KoPubDotumBold_0.ttf"));
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45  * ScreenParameter.getScreenparam_y()));
        name.setTextColor(Color.rgb(209, 211, 212));
        this.addView(name);

        category = new onTextView(context);
        category.setLocation(ScreenParameter.getScreenparam_x() * 135, ScreenParameter.getScreenparam_y() * 503);
        category.setTypeface(Typeface.createFromAsset(context.getAssets(), "KoPubDotumMedium_0.ttf"));
        category.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        category.setTextColor(Color.rgb(209, 211, 212));
        this.addView(category);

        onTextView way = new onTextView(context);
        way.setText("배출 방법");
       way.setLocation(ScreenParameter.getScreenparam_x() * 135, ScreenParameter.getScreenparam_y() * 764);
        way.setTypeface(Typeface.createFromAsset(context.getAssets(), "KoPubDotumBold_0.ttf"));
        way.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        way.setTextColor(Color.rgb(209, 211, 212));
        this.addView(way);

        how = new onTextView(context);
        how.setLocation(ScreenParameter.getScreenparam_x() * 135, ScreenParameter.getScreenparam_y()* 843);
        how.setTypeface(Typeface.createFromAsset(context.getAssets(), "KoPubDotumMedium_0.ttf"));
        how.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        how.setTextColor(Color.rgb(209, 211, 212));
        this.addView(how);

        onTextView not = new onTextView(context);
        not.setText("재활용 불가 제품");
        not.setLocation(ScreenParameter.getScreenparam_x() * 135, ScreenParameter.getScreenparam_y() * 1116);
        not.setTypeface(Typeface.createFromAsset(context.getAssets(), "KoPubDotumBold_0.ttf"));
        not.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        not.setTextColor(Color.rgb(209, 211, 212));
        this.addView(not);

        cannot = new onTextView(context);
        cannot.setLocation(ScreenParameter.getScreenparam_x() * 135, ScreenParameter.getScreenparam_y() * 1194);
        cannot.setTypeface(Typeface.createFromAsset(context.getAssets(), "KoPubDotumMedium_0.ttf"));
        cannot.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (45 * ScreenParameter.getScreenparam_y()));
        cannot.setTextColor(Color.rgb(0, 255, 255));
        this.addView(cannot);

    }

    public void setLayout(int index)
    {
        this.index = index;
    }

    public void viewLayout()
    {
        setMainImage(index);
        setName(index);
        setCategory(index);
        setHow(index);
        setCannot(index);
    }

}
