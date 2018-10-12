package com.example.crdiggs.myapplication.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.crdiggs.myapplication.Controllers.FirebaseHandler;
import com.example.crdiggs.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentsAndCompetenciesActivity extends AppCompatActivity {
    private String TAG = "SaC";
    private ArrayList<String> studentNames;
    private ArrayList<String> subcompetenciesSelected;
    private ArrayAdapter<String> mAdapter;
    private ListView studentNamesListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_and_competencies);

        studentNames = new ArrayList<String>();
        studentNames.add("Bob");
        subcompetenciesSelected = new ArrayList<String>();

        studentNamesListView = (ListView)findViewById(R.id.student_names_list_view);
        mAdapter = new ArrayAdapter(this, R.layout.student_name_layout, studentNames);
        studentNamesListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu(Menu menu)");
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
        int itemID = item.getItemId();
        switch(itemID) {
            case R.id.add_subcompetency:
                showAddSubcompetencyDialogue();
                break;
            case R.id.add_student:
                showAddStudentDialogue();
        }
        return true;
    }

    private void showAddStudentDialogue() {
        Log.d(TAG, "addStudent()");
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        adBuilder.setTitle("Add Student");
        final EditText studentNameInput = new EditText(this);
        studentNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        adBuilder.setView(studentNameInput);
        adBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String studentName = studentNameInput.getText().toString();
                addStudentToUI(studentName);
                return;
            }
        });

        adBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        adBuilder.show();
    }

    private void addStudentToUI(String studentName) {
        studentNames.add(studentName);
        mAdapter.notifyDataSetChanged();
    }

    private void showAddSubcompetencyDialogue() {
        //TODO
        Log.d(TAG, "addSubcompetency");
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        adBuilder.setTitle("Add Subcompetency");
        adBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        adBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        adBuilder.show();
    }

//    private String[] getCompetencies() {
//        Log.d(TAG, "getCompetencies()");
//        DatabaseReference competenciesRef = FirebaseHandler.getRootRef().child(FirebaseHandler.COMPETENCY_REF);
//        competenciesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Log.d(TAG, ds.getKey());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        return null;
//    }
}
