package com.example.laba43;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity3 extends AppCompatActivity
{
    TextView tvResult;
    Button back_button;
    static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        tvResult = (TextView) findViewById(R.id.tvResult);
        back_button = (Button) findViewById(R.id.back_button);
        tvResult.setText(res());
    }

    public String res()
    {
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Cursor c = db.query("users", null,null,null,null,null,null);
        if (c.moveToFirst())
        {
            int idColIndex = c.getColumnIndex("id");
            int loginColIndex = c.getColumnIndex("login");
            int countColIndex = c.getColumnIndex("count");
            String res = "";
            do
            {
                res += "ID = " + c.getInt(idColIndex) + ", login = " + c.getString(loginColIndex) + ", count = " + c.getString(countColIndex) +"\n";
            } while (c.moveToNext());
            dbHelper.close();
            return res;
        }
        else
        {
            c.close();
            dbHelper.close();
           return "Пусто!";
        }
    }
    public void back (View view)
    {
        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}