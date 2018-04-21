package com.example.crdiggs.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Teacher teacher = FirebaseHandler.generateMainTeacher();

        Button signInButton = findViewById(R.id.start_plan_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set Teacher Info and push it to Firebase


                // Push Local Teacher data into next activity


                Intent teacherInfoIntent = new Intent(MainMenu.this, TeacherInfo.class);
                startActivity(teacherInfoIntent);
            }
        });
    }
}
