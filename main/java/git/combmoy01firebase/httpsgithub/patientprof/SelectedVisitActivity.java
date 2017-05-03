package git.combmoy01firebase.httpsgithub.patientprof;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectedVisitActivity extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mPostSingleDocName;
    private TextView mPostSingleDocSpecialty;
    private TextView mPostSingleDocLocation;
    private TextView mPostSingleVisitDate;
    private TextView mPostSingleVisitSynopsis;
    private Button mSingleRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_visit);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userID).child("Visit_Overview");

        mPost_key = getIntent().getExtras().getString("visit_id");

        mPostSingleDocName = (TextView) findViewById(R.id.selected_doc_name);
        mPostSingleDocSpecialty = (TextView) findViewById(R.id.selected_doc_specialty);
        mPostSingleDocLocation = (TextView) findViewById(R.id.selected_doc_location);
        mPostSingleVisitDate = (TextView) findViewById(R.id.selected_visit_date);
        mPostSingleVisitSynopsis = (TextView) findViewById(R.id.selected_doc_synopsis);

        mSingleRemoveBtn = (Button) findViewById(R.id.singleRemoveBtn);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_doctor_name = (String) dataSnapshot.child("doctor_name").getValue();
                mPostSingleDocName.setText(post_doctor_name);

                String post_doctor_specialty = (String) dataSnapshot.child("doctor_specialty").getValue();
                mPostSingleDocSpecialty.setText(post_doctor_specialty);

                String post_doctor_location = (String) dataSnapshot.child("doctor_location").getValue();
                mPostSingleDocLocation.setText(post_doctor_location);

                String post_visit_date = (String) dataSnapshot.child("visit_date").getValue();
                mPostSingleVisitDate.setText(post_visit_date);

                String post_visit_synopsis = (String) dataSnapshot.child("visit_synopsis").getValue();
                mPostSingleVisitSynopsis.setText(post_visit_synopsis);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSingleRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPost_key).removeValue();

                Intent visitIntent = new Intent(SelectedVisitActivity.this, OverviewList.class);
                startActivity(visitIntent);
            }
        });


    }
}
