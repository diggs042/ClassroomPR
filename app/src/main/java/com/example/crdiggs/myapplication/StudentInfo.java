package com.example.crdiggs.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class StudentInfo extends AppCompatActivity {

    Student student = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        Intent intent = getIntent();
        int studentCount = intent.getExtras().getInt("studentCount");
        String studentName = ((EditText) findViewById(R.id.grade_input)).getText().toString();
        if (student != null)
            student.setStudentName(studentName);

        //startActivity(next page intent)
    }

}
