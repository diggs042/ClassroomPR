package com.example.crdiggs.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TeacherInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);

        final EditText studentCount = findViewById(R.id.number_students_input);
        Button continueButton = findViewById(R.id.student_names_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentInfoIntent = new Intent(TeacherInfo.this, StudentInfo.class);
                String numStudents = studentCount.getText().toString();
                studentInfoIntent.putExtra("numStudents", numStudents);
                Log.wtf("msg", numStudents);
                startActivity(studentInfoIntent);
            }
        });

    }

}
