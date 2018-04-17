package com.example.crdiggs.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Competencies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competencies);

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
