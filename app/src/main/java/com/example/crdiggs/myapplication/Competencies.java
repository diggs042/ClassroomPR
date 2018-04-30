package com.example.crdiggs.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class Competencies extends AppCompatActivity {
    private DatabaseReference competenciesDatabase;
    private String [] competencies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competencies);

        competenciesDatabase = FirebaseDatabase.getInstance().getReference("Competencies");
        competenciesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                competencies = new String [(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    competencies[i] = ds.getKey();
                    i++;
                }

                Spinner competency1 = (Spinner) findViewById(R.id.spinner1);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Competencies.this, android.R.layout.simple_spinner_item, competencies);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                competency1.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                    Log.e("onCancelled", databaseError.getMessage());
            }
        });


        Button competenciesContinueButton = findViewById(R.id.competencies_continue_button);
        competenciesContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subcompetenciesIntent = new Intent(Competencies.this, Subcompetencies.class);
                startActivity(subcompetenciesIntent);
            }
        });
    }
}
