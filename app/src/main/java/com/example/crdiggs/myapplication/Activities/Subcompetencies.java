package com.example.crdiggs.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.crdiggs.myapplication.R;

public class Subcompetencies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcompetencies);

        Spinner competency1Spinner = (Spinner) findViewById(R.id.competency_1_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.competency_1_subcompetencies, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        competency1Spinner.setAdapter(adapter);

        Spinner competency2Spinner = (Spinner) findViewById(R.id.competency_2_spinner);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.competency_2_subcompetencies, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        competency2Spinner.setAdapter(adapter2);

        Spinner competency3Spinner = (Spinner) findViewById(R.id.competency_3_spinner);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.competency_3_subcompetencies, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        competency3Spinner.setAdapter(adapter3);

        Spinner operationalDefinition1 = (Spinner) findViewById(R.id.operational_definition_1);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,R.array.operational_definition_1, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationalDefinition1.setAdapter(adapter4);

        Spinner operationalDefinition2 = (Spinner) findViewById(R.id.operational_definition_2);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,R.array.operational_definition_2, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationalDefinition2.setAdapter(adapter5);

        Spinner operationalDefinition3 = (Spinner) findViewById(R.id.operational_definition_3);

        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this,R.array.operational_definition_3, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationalDefinition3.setAdapter(adapter6);
    }
}
