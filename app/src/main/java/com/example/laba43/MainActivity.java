package com.example.laba43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private EditText login;
    private EditText password;
    Button button, button2;
    static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        dbHelper = new DBHelper(this);
    }

    public void but1click (View view)
    {
        if (login.getText().length() == 0 || password.getText().length() == 0)
        {
            Toast toast = Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (Check("users", log, pas, db))
            {
                ContentValues cv = new ContentValues();
                int count = Integer.parseInt(getCount("users", log, db));
                cv.put("count", count + 1);
                Cursor c = db.query("users", null,null,null,null,null,null);
                int loginColIndex = c.getColumnIndex("login");
                String updStr = "login = " + log;
                db.update("users", cv, updStr, null);
                db.close();
                Intent intent1 = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent1);
                finish();

            }
            else
            {
                Toast toast = Toast.makeText(this, "Неверный логин или пароль!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void but2click (View view)
    {
        Intent intent2 = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent2);
        finish();
    }
    public  boolean Check(String tableName, String value1, String value2, @NonNull SQLiteDatabase db)
    {
        String str = "Select * from " + tableName + " where login " + "= " +  value1 + " and password " + "= " + value2;
        Cursor cursor = db.rawQuery(str, null);
        if (cursor.getCount() <= 0)
        {
            //аккаунта нет
            cursor.close();
            return false;
        }
        else
        {
            cursor.close();
            return true;
        }
    }
    public String getCount(String tableName, String value, @NonNull SQLiteDatabase db)
    {
        String str = "Select count from " + tableName + " where login " + "= " +  value;
        Cursor cursor = db.rawQuery(str, null);
        String cnt = "";
        if (cursor.moveToFirst())
        {
            cnt = cursor.getString(0);
        }
        return cnt;
    }
}