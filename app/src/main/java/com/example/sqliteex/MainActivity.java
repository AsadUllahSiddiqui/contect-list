package com.example.sqliteex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    TextView add;
    MyRvAdapter adapter;
    List<Contact> ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rv);
        add=findViewById(R.id.add);
        ls=new ArrayList<>();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });


        RecyclerView.LayoutManager manager=new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(manager);
        adapter = new MyRvAdapter(ls,MainActivity.this);
        rv.setAdapter(adapter);
    }
    public void getData()
    {
        MyDBHelper helper=new MyDBHelper(MainActivity.this);
        SQLiteDatabase database=helper.getReadableDatabase();
        String[] columns={ContactContract.Contact._ID,
                ContactContract.Contact._Name,
                ContactContract.Contact._Email,
                ContactContract.Contact._Phno,
                ContactContract.Contact._Address};
        Cursor c=database.query(ContactContract.Contact._ContactTable,columns,null,null
        ,null,null,ContactContract.Contact._Name+" ASC");
        ls.clear();
        while (c.moveToNext())
        {
            ls.add(new Contact(
                    Integer.parseInt(c.getLong(c.getColumnIndex(ContactContract.Contact._ID))+""),
                    c.getString(c.getColumnIndex(ContactContract.Contact._Name)),
                    c.getString(c.getColumnIndex(ContactContract.Contact._Phno)),
                    c.getString(c.getColumnIndex(ContactContract.Contact._Address)),
                    c.getString(c.getColumnIndex(ContactContract.Contact._Email))
            ));
        }
        database.close();
        helper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        adapter=new MyRvAdapter(ls,MainActivity.this);
        rv.setAdapter(adapter);
    }
}