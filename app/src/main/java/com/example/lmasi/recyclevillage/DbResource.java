package com.example.lmasi.recyclevillage;

import android.database.sqlite.SQLiteDatabase;

public class DbResource{

    public static DBConect conn;
    public static SQLiteDatabase db;

    private static int money;
    private static int ticket;
    private static int char_index;

    public static int getChar_index() {
        return char_index;
    }

    public static int getMoney() {
        return money;
    }

    public static int getTicket() {
        return ticket;
    }

    public static void setChar_index(int char_index) {
        DbResource.char_index = char_index;
    }

    public static void setMoney(int money) {
        DbResource.money = money;
    }

    public static void setTicket(int ticket) {
        DbResource.ticket = ticket;
    }
}
