package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.List;

public class InfoView extends TouchImageView {

    protected String name;
    protected String info;
    protected int skills;
    protected int money;
    protected int heart;
    protected boolean IsBought = false;
    protected boolean Isallocated = false;


    public InfoView(Context context)
    {
        super(context);
    }

    public void setInfo(String name, String info, int skills, int money, int heart)
    {
        this.name = name;
        this.info = info;
        this.skills = skills;
        this.money = money;
        this.heart = heart;
    }


    public void setIsbought(boolean condition) {this.IsBought = condition;}
    public boolean IsBought() {return this.IsBought;}

    public void setIsallocated(boolean condition) {this.Isallocated = condition;}
    public boolean IsAllocatied() {return this.Isallocated;}

    public String Name(){return this.name;}
    public String Info(){return this.info;}
    public int Skills(){return this.skills;}
    public int Money(){return this.money;}
    public int Heart(){return this.heart;}

}