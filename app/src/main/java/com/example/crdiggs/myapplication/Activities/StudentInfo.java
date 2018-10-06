package com.example.crdiggs.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.crdiggs.myapplication.R;
import com.example.crdiggs.myapplication.Models.Student;

public class StudentInfo extends AppCompatActivity {

    Student student = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        Intent intent = getIntent();
        int studentCount = intent.getExtras().getInt("studentCount");

        //startActivity(next page intent)

        LinearLayout editTextLayout = findViewById(R.id.basic_layout);
        for(int i = 0; i < studentCount; i++){
            EditText editText = new EditText(StudentInfo.this);
            editText.setHint("Student Name");
            editTextLayout.addView(editText);

        }

        for(int i = 0; i < studentCount; i++){
            EditText view = (EditText) editTextLayout.getChildAt(i);
        }

    }

}
