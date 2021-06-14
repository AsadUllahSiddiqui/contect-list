package com.example.sqliteex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText name,phno,email,address;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name=findViewById(R.id.name);
        phno=findViewById(R.id.phno);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        save=findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper helper=new MyDBHelper(AddActivity.this);
                SQLiteDatabase database=helper.getWritableDatabase();
                ContentValues cv=new ContentValues();
                cv.put(ContactContract.Contact._Name,name.getText().toString());
                cv.put(ContactContract.Contact._Phno,phno.getText().toString());
                cv.put(ContactContract.Contact._Email,email.getText().toString());
                cv.put(ContactContract.Contact._Address,address.getText().toString());
                Long x=database.insert(ContactContract.Contact._ContactTable,null,cv);
                Toast.makeText(AddActivity.this, x+"", Toast.LENGTH_SHORT).show();
                database.close();
                helper.close();
                finish();
            }
        });

    }
}