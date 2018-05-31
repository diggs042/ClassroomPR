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
import java.util.Collections;


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
        setSelectCompetenciesRecycler();
        setContinueButton();

    }

    // Custom RecyclerView.Adapter to handle our custom ViewHolder
    public class CompetenciesRecyclerViewAdapter extends RecyclerView.Adapter<CompetenciesRecyclerViewAdapter.ViewHolder> {

        private static final String TAG = "RecyclerViewAdapter";
        private ArrayList<String> mSelectedCompetencies;
        private Context mContext;

        public CompetenciesRecyclerViewAdapter(Context mContext) {
            this.mSelectedCompetencies = new ArrayList<String>();
            this.mSelectedCompetencies.add(competencies.get(0)); // Default to select first in competency list
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

            // Setup UI elements within each ViewHolder
            setViewHolderSpinner(holder, position);
            setViewHolderAddButton(holder, position);
            setViewHolderDeleteButton(holder, position);
        }

        @Override
        public int getItemCount() {
            return mSelectedCompetencies.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            private String selectedCompetency;
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
            }
        }

        private void setViewHolderSpinner(final ViewHolder holder, final int position) {
            Log.d(TAG, "setViewHolderSpinner: called, position = " + Integer.toString(position));
            holder.selectedCompetency = mSelectedCompetencies.get(position);

            // Create a new list for possible competency options for this particular holder
            ArrayList<String> competencyOptionsSelectable = new ArrayList<String>();
            competencyOptionsSelectable.add(holder.selectedCompetency); // Always show currently selected competency at top of dropdown list
            ArrayList<String> unselectedCompetencies = new ArrayList<String>(competencies);
            unselectedCompetencies.removeAll(mSelectedCompetencies);
            Collections.sort(unselectedCompetencies, String.CASE_INSENSITIVE_ORDER);  // Show unselected competencies alphabetically for the rest of the dropdown options
            competencyOptionsSelectable.addAll(unselectedCompetencies);

            // Create an adapter and add competency options to spinner dropdown
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, competencyOptionsSelectable);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.selectCompetencySpinner.setAdapter(spinnerArrayAdapter);

            // Define behavior when an old competency is replaced by a newly selected one
            holder.selectCompetencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG, "onItemSelected: called, position = " + Integer.toString(i));
                    String selectedCompetency = adapterView.getItemAtPosition(i).toString();
                    if (selectedCompetency == holder.selectedCompetency) {
                        /*
                        This if-block prevents an infinite recursion.
                        onItemSelected is called when choosing the default selection
                        when the activity starts. If we call notifyDataSetChanged() here,
                        setViewHolderSpinner will be called, which does a default onItemSelected,
                        which will call notifyDataSetChanged() here, so setViewHolderSpinner will
                        be called...and so on...

                        Basically, we want to ignore the case in which the selected competency is
                        the same as before.
                         */
                        return;
                    } else {
                        mSelectedCompetencies.set(position, selectedCompetency);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Log.d(TAG, "onNothingSelected: called");
                }
            });
        }

        private void setViewHolderAddButton(final ViewHolder holder, int position) {

            // Make add button only visible on last ViewHolder, unless all possible competencies are selected
            if (position != mSelectedCompetencies.size() - 1 || position == competencies.size() - 1) {
                holder.addSelectCompetencyButton.setVisibility(View.GONE);
            } else {
                holder.addSelectCompetencyButton.setVisibility(View.VISIBLE);
            }

            // Make the next selection spinner default selection to be the second (index 1) in the list of selectable options
            holder.addSelectCompetencyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectedCompetencies.add(holder.selectCompetencySpinner.getItemAtPosition(1).toString());
                    notifyDataSetChanged();
                }
            });
        }

        private void setViewHolderDeleteButton(final ViewHolder holder, int position) {

            // Make delete button only visible on all but first ViewHolder
            if (position == 0) {
                holder.deleteSelectCompetencyButton.setVisibility(View.GONE);
            } else {
                holder.deleteSelectCompetencyButton.setVisibility(View.VISIBLE);
            }

            // Remove the selected competency in this ViewHolder from the list of selected competencies
            holder.deleteSelectCompetencyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectedCompetencies.remove(holder.selectCompetencySpinner.getSelectedItem());
                    notifyDataSetChanged();
                }
            });
        }
    }

    private void setSelectCompetenciesRecycler() {

        // Connect to online instance of competencies data
        competenciesDatabase = FirebaseDatabase.getInstance().getReference(FirebaseHandler.COMPETENCY_REF);

        // Retrieve data once
        competenciesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                competencies = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    competencies.add(ds.getKey());
                }
                Collections.sort(competencies, String.CASE_INSENSITIVE_ORDER);

                // We must have the competencies list loaded before we can set the view adapter
                selectCompetenciesRecycler = findViewById(R.id.select_competencies_recycler);
                CompetenciesRecyclerViewAdapter competenciesRecyclerViewAdapter = new CompetenciesRecyclerViewAdapter(Competencies.this);
                selectCompetenciesRecycler.setAdapter(competenciesRecyclerViewAdapter);
                selectCompetenciesRecycler.setLayoutManager(new LinearLayoutManager(Competencies.this));
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
