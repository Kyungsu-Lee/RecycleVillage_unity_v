package com.example.lmasi.recyclevillage;

import android.annotation.SuppressLint;
import android.graphics.Point;


@SuppressLint("ParcelCreator")
public class GridPoint extends Point {

    private GridPoint RU;
    private GridPoint RD;
    private GridPoint LU;
    private GridPoint LD;
    private ItemView item;
    private boolean ismain;
    private boolean onitem;
    private boolean canPut;
    private  int item_index;
    private int priority = -1;
    private boolean canGo = true;

    public GridPoint()
    {
        super();

        LU = null;
        LD = null;
        RU = null;
        RD = null;
        item = null;
        ismain = false;
        onitem = false;
        canPut = true;
    }

    public GridPoint(int x, int y)
    {
        super(x,y);

        LU = null;
        LD = null;
        RU = null;
        RD = null;
        item = null;
        ismain = false;
        onitem = false;
        canPut = true;
    }

    public GridPoint(double x, double y)
    {
        super((int)x,(int)y);

        LU = null;
        LD = null;
        RU = null;
        RD = null;
        item = null;
        ismain = false;
        onitem = false;
        canPut = true;
    }

    public GridPoint(Point p)
    {
        super(p.x,p.y);

        LU = null;
        LD = null;
        RU = null;
        RD = null;
        item = null;
        ismain = false;
        onitem = false;
        canPut = true;
    }

    //set variables
    public void setRU(Point p){RU = new GridPoint(p);}
    public void setRD(Point p){RD = new GridPoint(p);}
    public void setRU(GridPoint p){RU = p;}
    public void setRD(GridPoint p){RD = p;}
    public void setLU(Point p){LU = new GridPoint(p);}
    public void setLD(Point p){LD = new GridPoint(p);}
    public void setLU(GridPoint p){LU = p;}
    public void setLD(GridPoint p){LD = p;}

    public boolean isCanGo() {
        return canGo;
    }

    public void setOnitem(ItemView v){this.item = v;}

    public void setMain(boolean F){this.ismain = F;}

    public void setOnitem(boolean F){this.onitem = F;}

    public void setCanPut(boolean F){this.canPut = F;}

    public void setItem_index(int index) { this.item_index = index;}

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCanGo(boolean canGo) {
        this.canGo = canGo;
    }

    //get variables
    public GridPoint getRU(){return this.RU;}
    public GridPoint getRD(){return this.RD;}
    public GridPoint getLU(){return this.LU;}
    public GridPoint getLD(){return this.LD;}

    public ItemView getOnItem(){return this.item;}

    public boolean IsMain(){return this.ismain;}

    public boolean OnItem(){return this.onitem;}

    public boolean CanPut(){return this.canPut;}

    public int getItem_index() { return this.item_index;}

    //method
    @Override
    public boolean equals(Object obj)
    {
        return (this.x == ((GridPoint)obj).x) && (this.y == ((GridPoint)obj).y);
    }

    public boolean IsNear(Point p, int density_x, int density_y)
    {
        return ((p.x - this.x) > 0) && ((p.x - this.x) < density_x) && (Math.abs(p.y - this.y) < density_y) && isCanGo();
    }


}