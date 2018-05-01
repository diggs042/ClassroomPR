package com.example.crdiggs.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CompetencyList extends AppCompatActivity {

    private RecyclerView competencyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competency_list);
        setCompetencyRecyclerView();
        loadDataIntoCompetencyRecycler();
    }

    private void setCompetencyRecyclerView(){

        competencyRecyclerView = findViewById(R.id.competency_recycler);
        competencyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadDataIntoCompetencyRecycler(){

        DatabaseReference competenciesRefs = FirebaseHandler.getRootRef().child(FirebaseHandler.COMPETENCY_REF);
        competenciesRefs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[] competencies = new String[(int) dataSnapshot.getChildrenCount()];
                int i = 0;
                for(DataSnapshot competency: dataSnapshot.getChildren()){
                    competencies[i] = competency.getKey();
                    i++;
                }
                competencyRecyclerView.setAdapter(new CompetencyRecyclerAdapter(competencies));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private class CompetencyRecyclerAdapter extends RecyclerView.Adapter<CompetencyViewHolder>{

        private String[] mCompetencies;

        CompetencyRecyclerAdapter(String[] competencies){
            mCompetencies = competencies;
        }

        @NonNull
        @Override
        public CompetencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView competencyTextView = (TextView) getLayoutInflater().inflate(R.layout.competency_view_holder, parent, false);
            return new CompetencyViewHolder(competencyTextView);
        }

        @Override
        public void onBindViewHolder(@NonNull final CompetencyViewHolder holder, int position) {
            holder.mCompetency = mCompetencies[position];
            holder.mView.setText(holder.mCompetency);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CompetencyList.this, EditCompetency.class);
                    intent.putExtra(FirebaseHandler.COMPETENCY_REF, holder.mCompetency);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCompetencies.length;
        }
    }


    private class CompetencyViewHolder extends RecyclerView.ViewHolder{

        final TextView mView;
        String mCompetency; // Not sure if I'll need this

        CompetencyViewHolder(TextView view){
            super(view);
            mView = view;
            setTextViewLayout();
        }

        void setTextViewLayout(){
            // Nothing to implement with layout yet.
        }
    }
}
