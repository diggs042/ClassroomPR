package com.example.crdiggs.myapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.crdiggs.myapplication.Controllers.FirebaseHandler;
import com.example.crdiggs.myapplication.R;
import com.example.crdiggs.myapplication.Models.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {

    Button editCompetenciesButton;
    Teacher mainTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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


        editCompetenciesButton = findViewById(R.id.competency_admin_button);

        setCompetenciesButtonVisibility();
        setCompetenciesButtonOnClick();
    }

    private void setCompetenciesButtonVisibility(){
        editCompetenciesButton.setVisibility(View.GONE);
        try {
            final DatabaseReference teacherRef = FirebaseHandler.getRootRef().child(FirebaseHandler.TEACHER_REF).child(FirebaseHandler.getUID());
            teacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mainTeacher = dataSnapshot.getValue(Teacher.class);
                    if(mainTeacher != null){
                        if(mainTeacher.isAdmin()){
                            editCompetenciesButton.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (IllegalAccessException iae){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    void setCompetenciesButtonOnClick(){

        editCompetenciesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompetencyList.class);
                startActivity(intent);
            }
        });

    }

}
