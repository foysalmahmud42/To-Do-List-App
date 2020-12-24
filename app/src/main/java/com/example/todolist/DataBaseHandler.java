package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "item.db";

    private static final String TABLE_ITEM = "items";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM_NAME = "item";
    private static final String COLUMN_DATE = "date";

    String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "(" + COLUMN_ID +
            " INTEGER PRIMARY KEY, " + COLUMN_ITEM_NAME + " TEXT, " +
            COLUMN_DATE + " TEXT " + ")";

    public DataBaseHandler(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_ITEM);

        onCreate(sqLiteDatabase);

    }

    //create
    public void addItem(Item item){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getItem());
        values.put(COLUMN_DATE, item.getDate());

        database.insert(TABLE_ITEM, null, values);
        database.close();

    }

    //read

    public Item getItem(int id){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_ITEM, new String[] {COLUMN_ID,COLUMN_ITEM_NAME,COLUMN_DATE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},null,null,null);
        if(cursor != null){

            cursor.moveToFirst();

        }

        Item item = new Item(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        return item;

    }

    //getting all objects
    public List<Item> getAllItem(){

        List<Item> itemList = new ArrayList<>();

        String selectAllQuery = " SELECT * FROM " + TABLE_ITEM;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectAllQuery, null);

        if(cursor.moveToFirst()){

            do {

                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setItem(cursor.getString(1));
                item.setDate(cursor.getString(2));

                itemList.add(item);

            } while (cursor.moveToNext());

        }

        return itemList;

    }

    //updating a item

    public int uodateItem(Item item){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getItem());
        values.put(COLUMN_DATE, item.getDate());

        return database.update(TABLE_ITEM, values, COLUMN_ID + " = ? ",
                new String[] {String.valueOf(item.getId())});

    }

    //delete

    public void deleteItem(Item item){

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_ITEM, COLUMN_ID + " = ? ", new String[]{String.valueOf(
                item.getId())});
        database.close();

    }

    //getting total number of items

    public int getItemsCount(){

        String itemsCountQuery = " SELECT * FROM " + TABLE_ITEM;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(itemsCountQuery, null);
        cursor.close();

        return cursor.getCount();

    }












}
