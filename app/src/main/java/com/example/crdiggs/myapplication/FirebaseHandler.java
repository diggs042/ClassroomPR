package com.example.crdiggs.myapplication;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Static Singleton Class - all static methods (can't make an instance of this class)
public class FirebaseHandler {

    static final String TEACHER_REF = "teachers";
    static final String COMPETENCY_REF = "Competencies";

    private static final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    
    private static FirebaseUser mUser;
    private static Teacher mTeacher;
    
    private static boolean isMainUserAuthenticated(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        return mUser != null;
    }
    
    static Teacher generateMainTeacher(){
        if(isMainUserAuthenticated()){
            Teacher teacher = new Teacher(mUser.getDisplayName());
            teacher = loadMainUserData(teacher);
            return teacher;
        }else{
            // Will start throwing IllegalAccessExceptions once the control flow is all figured out
            return null;
        }
    }

    private static Teacher loadMainUserData(final Teacher teacher){
        // Load main data (and set listener) from database - if not already loaded
        if (mTeacher == null) {
            DatabaseReference userRef = rootRef.child(TEACHER_REF).child(mUser.getUid());

            // Read Data
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        // Create new user in database
                        createNewUserData(teacher);
                        // Loaded User is whatever it is now since there is no previous data to load
                        mTeacher = teacher;
                    }
                    else {
                        // This works even after the initial data read since mTeacher's pointer is returned at the end of the method.
                        Log.d("MainActivityCycle", "mainUserSet");
                        mTeacher = dataSnapshot.getValue(Teacher.class);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        // This will be null at first since the ValueEventListener
        // only puts the data in this pointer after all the code (onCreate) has run
        // The plan is to only call this function once when the app is first started
        return mTeacher;
    }

    private static void createNewUserData(Teacher teacher){
        // Firebase User (mUser) ID is going to be the unique key in the database
        rootRef.child(TEACHER_REF).child(mUser.getUid()).setValue(teacher);
    }

    static void updateMainUserData(Teacher teacher){
        if (isMainUserAuthenticated()){
            DatabaseReference userRef = rootRef.child(TEACHER_REF).child(mUser.getUid());
            userRef.setValue(teacher);
        }
    }

    static String getUID() throws IllegalAccessException{
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser != null){
            return mUser.getUid();
        }else {
            throw new IllegalAccessException("User not authorized");
        }
    }

    static DatabaseReference getRootRef() {
        return rootRef;
    }
}
