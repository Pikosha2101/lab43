package com.example.laba43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class MainActivity2 extends AppCompatActivity
{
    final String LOG_TAG = "myLogs";
    private EditText login, email, passw1, passw2;
    Button button, back_button;
    static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ContentValues cv = new ContentValues();
        login = (EditText)findViewById(R.id.login1);
        email = (EditText) findViewById(R.id.editTextTextPersonName);
        passw1 = (EditText) findViewById(R.id.editTextTextPassword);
        passw2 = (EditText) findViewById(R.id.editTextTextPassword2);
        button = (Button) findViewById(R.id.button);
        back_button = (Button) findViewById(R.id.back_button);
        dbHelper = new DBHelper(this);
    }

    public void butCreateClick (View view)
    {
        if (login.getText().length() == 0 || email.getText().length() == 0 || passw1.getText().length() == 0 || passw2.getText().length() == 0)
        {
            Toast toast = Toast.makeText(this, "Заполните все поля!",Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            String pas1 = passw1.getText().toString();
            String pas2 = passw2.getText().toString();
            if (pas1.equals(pas2))
            {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String log = login.getText().toString();
                String mail = email.getText().toString();
                String pas = passw1.getText().toString();
                if (Check("users", log, db))
                {
                    Toast toast = Toast.makeText(this, "Аккаунт уже существует!",Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    ContentValues cv = new ContentValues();
                    cv.put("login", log);
                    cv.put("email", mail);
                    cv.put("password", pas);
                    cv.put("count", 0);
                    long rowId = db.insert("users", null, cv);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowId);
                    Toast toast = Toast.makeText(this, "Аккаунт создан!",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            else
            {
                Toast toast = Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void butbackClick (View view)
    {
        Intent intent2 = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent2);
        finish();
    }


    public  boolean Check(String tableName, String value, @NonNull SQLiteDatabase db)
    {
        String str = "Select * from " + tableName + " where login " + "= " +  value;
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
}