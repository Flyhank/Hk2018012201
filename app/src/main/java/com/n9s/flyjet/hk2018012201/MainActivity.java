package com.n9s.flyjet.hk2018012201;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");    //建立檔案連結, 每次重run,SQLite資料庫資料為初始資料
        InputStream is = getResources().openRawResource(R.raw.student); //資料來源
        try {
            OutputStream os = new FileOutputStream(dbFile);
            int r;
            while ((r = is.read()) != -1)
            {
                os.write(r);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void click2(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.rawQuery("Select * from students", null);
        c.moveToFirst();                                                         //將指標移至第一筆資料
        Log.d("DB", c.getString(1) + "," + c.getInt(2));        //列出"name" & "score"
        while (c.moveToNext())
        {
            Log.d("DB", c.getString(1) + "," + c.getInt(2));    //列出"name" & "score"
        }
        db.close();                                                             //最好要做close()
    }

    public void click3(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        String strSql = "Select * from students where _id=?";           //id?
        Cursor c = db.rawQuery(strSql, new String[] {"2"});         //Cursor指向id2
        c.moveToFirst();                                            //指標回到第一筆資料
        Log.d("DB", c.getString(1) + "," + c.getInt(2));    //列出"name" & "score"
        db.close();                                                             //最好要做close()
    }

    public void click4(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("students", new String[] {"_id", "name", "score"}, null, null, null, null, null);   //按照第一筆選擇
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));    //的"name" & "score"
        db.close();                                                            //最好要做close()
    }

    public void click5(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("students", new String[] {"_id", "name", "score"}, "_id=?", new String[] {"2"}, null, null, null);  //選第二筆資料
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
        db.close();
    }

    public void click6(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        db.execSQL("Insert into students (_id, name, score) values (3, 'Bob', 95)");        //新增第三筆資料; 按第二次此button會當掉
        db.close();
    }

    public void click7(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put("_id", 4);
        cv.put("name", "Jane");
        cv.put("score", 92);
        db.insert("students", null, cv);                    //新增第四筆資料; 按第二次此button會當掉
        db.close();
    }

    public void click8(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();                                  //更改第二筆資料
        cv.put("score", 85);
        db.update("students", cv, "_id=?", new String[] {"2"});
        db.close();
    }
    public void click9(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("students", "_id=?", new String[] {"3"});         //刪除第三筆資料
        db.close();
    }

}
