package com.example.crdiggs.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditCompetency extends AppCompatActivity {

    RecyclerView subCompetencyRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_competency);

        setSubCompetencyRecycler();
        loadDataIntoSubCompetencyRecycler();

    }

    void setSubCompetencyRecycler(){
        subCompetencyRecycler = findViewById(R.id.subcompetency_recycler);
        subCompetencyRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    void loadDataIntoSubCompetencyRecycler(){

        final String competency = getIntent().getStringExtra(FirebaseHandler.COMPETENCY_REF);
        DatabaseReference subCompetenciesRef = FirebaseHandler.getRootRef().child(FirebaseHandler.COMPETENCY_REF).child(competency);
        subCompetenciesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SubCompetency> subCompetencies = new ArrayList<>();
                for(DataSnapshot sub: dataSnapshot.getChildren()){
                    SubCompetency subCompetency = new SubCompetency(competency);
                    subCompetency.setName(sub.getKey());
                    subCompetency.setCompetency(competency);

                    ArrayList<String> definitions = new ArrayList<>();
                    for(DataSnapshot note: sub.getChildren()){
                        String noteString = (String) note.getValue();
                        if(noteString != null){
                            definitions.add(noteString);
                        }
                    }
                    subCompetency.setDefinitions(definitions);
                    subCompetencies.add(subCompetency);
                }
                if(subCompetencies.size() > 0) {
                    subCompetencyRecycler.setAdapter(new CompetencyRecyclerAdapter(subCompetencies));
                }else{
                    subCompetencyRecycler.setVisibility(View.GONE);
                    TextView textView = findViewById(R.id.subcompetency_list_title);
                    String s = "No Subcompetencies in this competency!";
                    textView.setText(s);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private class CompetencyRecyclerAdapter extends RecyclerView.Adapter<EditCompetency.SubCompetencyViewHolder>{

        private ArrayList<SubCompetency> mSubCompetencies;

        CompetencyRecyclerAdapter(ArrayList<SubCompetency> subCompetencies){
            mSubCompetencies = subCompetencies;
        }

        @NonNull
        @Override
        public SubCompetencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView competencyTextView = (TextView) getLayoutInflater().inflate(R.layout.competency_view_holder, parent, false);
            return new SubCompetencyViewHolder(competencyTextView);
        }

        @Override
        public void onBindViewHolder(@NonNull final SubCompetencyViewHolder holder, int position) {
            holder.subCompetency = mSubCompetencies.get(position);
            holder.mView.setText(holder.subCompetency.getName());

            final AlertDialog alertDialog = viewSubCompetencyDialog(holder.subCompetency);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mSubCompetencies.size();
        }

        AlertDialog viewSubCompetencyDialog(SubCompetency subCompetency){
            AlertDialog.Builder builder = new AlertDialog.Builder(EditCompetency.this);
            LinearLayout linearLayout = new LinearLayout(EditCompetency.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);

            for(String note: subCompetency.getDefinitions()){

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                TextView noteView = new TextView(EditCompetency.this);
                String in = "- " + note;
                noteView.setText(in);

                int leftValueInPx = (int) noteView.getContext().getResources().getDimension(R.dimen.dialog_text_start_margin);
                params.setMargins(leftValueInPx, params.topMargin, params.rightMargin, params.bottomMargin);
                noteView.setLayoutParams(params);

                linearLayout.addView(noteView);
            }

            builder.setTitle(subCompetency.getName());
            builder.setView(linearLayout);
            builder.setPositiveButton("OK", null);

            return builder.create();
        }

    }



    private class SubCompetencyViewHolder extends RecyclerView.ViewHolder{

        final TextView mView;
        SubCompetency subCompetency; // Not sure if I'll need this

        SubCompetencyViewHolder(TextView view){
            super(view);
            mView = view;
            setTextViewLayout();
        }

        void setTextViewLayout(){
            // Nothing to implement with layout yet.
        }
    }


}
