package com.example.lmasi.recyclevillage;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by lmasi on 2016-06-25.
 */
public class MovingBox extends BoxView {

    private ArrayList<Handler> handler;
    private ArrayList<Handler> moving_handler;
    public boolean Ismoving;



    MovingBox(Context context)
    {
        super(context);

        handler = new ArrayList<Handler>();
        moving_handler = new ArrayList<Handler>();
        Ismoving = false;
    }

    public void addTimer(Handler handler)
    {
        this.handler.add(handler);
        Ismoving = true;
    }

    public void startTimer(int n)
    {
        this.handler.get(n).sendEmptyMessage(0); //handler 시작 메시지 .(delay)
        moving_handler.add(handler.get(0));
        Ismoving = true;
    }

    public void stopTimer(int n)
    {
        handler.get(0).removeMessages(0); //handler 정지 메시지
        moving_handler.remove(handler.get(n));

        if(moving_handler.size() == 0)
            Ismoving = false;
    }

    public void removeTimer(int n)
    {
        handler.remove(n);
    }



}
