package git.combmoy01firebase.httpsgithub.patientprof;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OverviewList extends DrawerActivity {

    private RecyclerView doc_rv;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_overview_list);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_overview_list, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userID).child("Visit_Overview");
        doc_rv = (RecyclerView) findViewById(R.id.visit_recyclerview);
        doc_rv.setHasFixedSize(true);
        doc_rv.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Visits, OverviewList.VisitViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Visits, OverviewList.VisitViewHolder>(
                Visits.class,
                R.layout.visit_row,
                OverviewList.VisitViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(OverviewList.VisitViewHolder viewHolder, Visits model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setDoctor_name(model.getDoctor_name());
                viewHolder.setDoctor_specialty(model.getDoctor_specialty());
                viewHolder.setDoctor_location(model.getDoctor_location());
                viewHolder.setVisit_date(model.getVisit_date());
                viewHolder.setVisit_synopsis(model.getVisit_synopsis());

                //testing on click listener
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(CurrentMedications.this, "You clicked a view", Toast.LENGTH_LONG).show();
                        Intent singleVisitIntent = new Intent(OverviewList.this, SelectedVisitActivity.class);
                        singleVisitIntent.putExtra("visit_id", post_key);
                        startActivity(singleVisitIntent);
                    }
                });
            }
        };

        doc_rv.setAdapter(firebaseRecyclerAdapter);
    }

    public static class VisitViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public VisitViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDoctor_name(String doctor_name)
        {
            TextView post_doctor_name = (TextView) mView.findViewById(R.id.post_doctor_name);
            post_doctor_name.setText(doctor_name);
        }

        public void setDoctor_specialty(String doctor_specialty)
        {
            TextView post_doctor_specialty = (TextView) mView.findViewById(R.id.post_doctor_specialty);
            post_doctor_specialty.setText(doctor_specialty);
        }

        public void setDoctor_location(String doctor_location)
        {
            TextView post_doctor_location = (TextView) mView.findViewById(R.id.post_doctor_location);
            post_doctor_location.setText(doctor_location);
        }

        public void setVisit_date(String visit_date)
        {
            TextView post_visit_date = (TextView) mView.findViewById(R.id.post_visit_date);
            post_visit_date.setText(visit_date);
        }

        public void setVisit_synopsis(String visit_synopsis)
        {
            TextView post_visit_synopsis = (TextView) mView.findViewById(R.id.post_visit_synopsis);
            post_visit_synopsis.setText(visit_synopsis);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_add)
        {
            startActivity(new Intent(OverviewList.this, PostDoctorOverview.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

