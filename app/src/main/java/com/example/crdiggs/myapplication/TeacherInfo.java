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

        // Will remove later
        final Teacher teacher = FirebaseHandler.generateMainTeacher();

        Button continueButton = findViewById(R.id.student_names_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent studentInfoIntent = new Intent(TeacherInfo.this, StudentInfo.class);

                String name = ((EditText) findViewById(R.id.teacher_name_input)).getText().toString();
                String schoolName = ((EditText) findViewById(R.id.school_name_input)).getText().toString();
                int grade = Integer.parseInt(((EditText) findViewById(R.id.grade_input)).getText().toString());
                int studentCount = Integer.parseInt(((EditText) findViewById(R.id.number_students_input)).getText().toString());

                if(teacher != null) {
                    teacher.setTeacherName(name);
                    teacher.setSchool(schoolName);
                    teacher.setGrade(grade);
                    teacher.setStudentCount(studentCount);
                    FirebaseHandler.updateMainUserData(teacher);
                    studentInfoIntent.putExtra("teacher", teacher);
                }else{
                    Log.d("NullTeacher", "There's a headless teacher running");
                }

                startActivity(studentInfoIntent);

            }
        });
    }

}
