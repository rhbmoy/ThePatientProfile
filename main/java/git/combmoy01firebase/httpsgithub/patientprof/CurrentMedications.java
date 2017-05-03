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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CurrentMedications extends DrawerActivity {

    private RecyclerView med_rv;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // don’t set any content view here, since its already set in DrawerActivity
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_current_medications, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userID).child("Medications");
        med_rv = (RecyclerView) findViewById(R.id.med_recyclerview);
        med_rv.setHasFixedSize(true);
        med_rv.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Medications, MedicineViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Medications, MedicineViewHolder>(
                Medications.class,
                R.layout.currentmeds_row,
                MedicineViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(MedicineViewHolder viewHolder, Medications model, int position) {

                //Stores the position of the medication and gets the key or unique id that it is stored under
                final String post_key = getRef(position).getKey();

                viewHolder.setMedName(model.getMedName());
                viewHolder.setMedDose(model.getMedDose());
                viewHolder.setMedFreq(model.getMedFreq());
                viewHolder.setMedReason(model.getMedReason());
                viewHolder.setMedPrevention(model.getMedPrevention());

                //testing on click listener
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Intent that passes the post_key to the selected medication upon the view being clicked
                        Intent singleMedicationIntent = new Intent(CurrentMedications.this, SelectedMedicationActivity.class);
                        singleMedicationIntent.putExtra("medication_id", post_key);
                        startActivity(singleMedicationIntent);
                    }
                });
            }
        };

        med_rv.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setMedName(String medName)
        {
            TextView post_medName = (TextView) mView.findViewById(R.id.post_medName);
            post_medName.setText(medName);
        }

        public void setMedDose(String medDose)
        {
            TextView post_medDose = (TextView) mView.findViewById(R.id.post_medDose);
            post_medDose.setText(medDose);
        }

        public void setMedFreq(String medFreq)
        {
            TextView post_medFreq = (TextView) mView.findViewById(R.id.post_medFreq);
            post_medFreq.setText(medFreq);
        }

        public void setMedReason(String medReason)
        {
            TextView post_medReason = (TextView) mView.findViewById(R.id.post_medReason);
            post_medReason.setText(medReason);
        }

        public void setMedPrevention(String medPrevention)
        {
            TextView post_medPrevention = (TextView) mView.findViewById(R.id.post_medPrevention);
            post_medPrevention.setText(medPrevention);
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
            startActivity(new Intent(CurrentMedications.this, PostMedications.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
