package com.example.crdiggs.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Competencies extends AppCompatActivity {
    private DatabaseReference competenciesDatabase;
    private ArrayList<String> competencies;
    private RecyclerView selectCompetenciesRecycler;
    private Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competencies);

        // Set up UI elements
        loadCompetetencies();
        setContinueButton();

    }

    public class CompetenciesRecyclerViewAdapter extends RecyclerView.Adapter<CompetenciesRecyclerViewAdapter.ViewHolder> {

        private static final String TAG = "RecyclerViewAdapter";
        private ArrayList<String> mSelectedCompetencies;
        private ArrayList<String> mUnselectedCompetencies;
        private int numCompetencies;
        private Context mContext;

        public CompetenciesRecyclerViewAdapter(Context mContext) {
            this.mSelectedCompetencies = new ArrayList<String>();
            this.mSelectedCompetencies.add(competencies.get(0));
            Log.wtf(TAG, competencies.get(0));
            this.mUnselectedCompetencies = new ArrayList<String>(competencies);
            this.numCompetencies = 1;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_competency_spinner, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder: called.");
            setViewHolderSpinner(holder, position);
            setViewHolderAddButton(holder, position);
            setViewHolderDeleteButton(holder, position);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "size: called.");
            return numCompetencies;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            private String selectedCompetency;
            private String previousSelectedCompetency;
            private ConstraintLayout parentLayout;
            private Spinner selectCompetencySpinner;
            private FloatingActionButton addSelectCompetencyButton;
            private FloatingActionButton deleteSelectCompetencyButton;
            public ViewHolder(View itemView) {
                super(itemView);
                parentLayout = (ConstraintLayout)itemView.findViewById(R.id.select_competency_parent_layout);
                selectCompetencySpinner = (Spinner)itemView.findViewById(R.id.select_competency_spinner);
                addSelectCompetencyButton = (FloatingActionButton)itemView.findViewById(R.id.add_select_competency_button);
                deleteSelectCompetencyButton = (FloatingActionButton)itemView.findViewById(R.id.delete_select_competency_button);
                if (selectCompetencySpinner.getSelectedItem() != null) {
                    previousSelectedCompetency = selectedCompetency;
                    selectedCompetency = selectCompetencySpinner.getSelectedItem().toString();
                }
            }
        }

        private void setViewHolderSpinner(final ViewHolder holder, int position) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mUnselectedCompetencies);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.selectCompetencySpinner.setAdapter(spinnerArrayAdapter);
        }

        private void setViewHolderAddButton(final ViewHolder holder, int position) {
            if (position == 0) {
                holder.deleteSelectCompetencyButton.setVisibility(View.GONE);
            } else {
                holder.deleteSelectCompetencyButton.setVisibility(View.VISIBLE);
            }
            holder.addSelectCompetencyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.addSelectCompetencyButton.setVisibility(View.GONE);
                    numCompetencies++;
                    notifyDataSetChanged();
                }
            });
        }

        private void setViewHolderDeleteButton(final ViewHolder holder, int position) {
            if (position != numCompetencies-1) {
                holder.addSelectCompetencyButton.setVisibility(View.GONE);
            } else {
                holder.addSelectCompetencyButton.setVisibility(View.VISIBLE);
            }
            holder.deleteSelectCompetencyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numCompetencies--;
                    notifyDataSetChanged();
                }
            });
        }
    }

    private void setSelectCompetenciesRecycler() {
        selectCompetenciesRecycler = findViewById(R.id.select_competencies_recycler);
        CompetenciesRecyclerViewAdapter competenciesRecyclerViewAdapter = new CompetenciesRecyclerViewAdapter(this);
        selectCompetenciesRecycler.setAdapter(competenciesRecyclerViewAdapter);
        selectCompetenciesRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadCompetetencies() {
        competenciesDatabase = FirebaseDatabase.getInstance().getReference(FirebaseHandler.COMPETENCY_REF);
        competenciesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                competencies = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    competencies.add(ds.getKey());
                }
                setSelectCompetenciesRecycler();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", databaseError.getMessage());
            }
        });
    }

    private void setContinueButton() {
        continueButton = findViewById(R.id.competencies_continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subcompetenciesIntent = new Intent(Competencies.this, Subcompetencies.class);
                startActivity(subcompetenciesIntent);
            }
        });
    }
}
