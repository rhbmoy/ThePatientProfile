package git.combmoy01firebase.httpsgithub.patientprof;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicalHistoryTemp extends DrawerActivity {

    private RecyclerView mrecyle_history;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userId).child("Medical_History_Info");

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_medical_history_temp, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs
        //test

        mrecyle_history = (RecyclerView) findViewById(R.id.recycle_history);
        mrecyle_history.setHasFixedSize(true);
        mrecyle_history.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Medical_History_Info, HistoryViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Medical_History_Info, HistoryViewHolder>(
                Medical_History_Info.class,
                R.layout.history_row,
                HistoryViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(HistoryViewHolder viewHolder, Medical_History_Info model, int position) {

                final String post_key = getRef(0).getKey();

                viewHolder.setUser_allergies(model.getUser_allergies());
                viewHolder.setSpinner_data(model.getSpinner_data());
                viewHolder.setAlcoholic_answer(model.getAlcoholic_answer());
                viewHolder.setDrug_answer(model.getDrug_answer());
                viewHolder.setPast_fam_illness(model.getPast_fam_illness());
                viewHolder.setPast_surgeries(model.getPast_surgeries());
                viewHolder.setSmoker_answer(model.getSmoker_answer());

                if(position == 1){
                    mDatabase.child(post_key).removeValue();
                }
            }
        };

        mrecyle_history.setAdapter(firebaseRecyclerAdapter);

    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setUser_allergies(String user_allergies)
        {
            TextView mpost_user_allergies = (TextView) mView.findViewById(R.id.rec_listAllergies);
            mpost_user_allergies.setText(user_allergies);
        }

        public void setSpinner_data(String spinner_data)
        {
            TextView mpost_spinner_data = (TextView) mView.findViewById(R.id.post_spinner_data);
            mpost_spinner_data.setText(spinner_data);
        }

        public void setPast_surgeries(String past_surgeries)
        {

            TextView mpost_past_surgeries = (TextView) mView.findViewById(R.id.post_surgeries);
            mpost_past_surgeries.setText(past_surgeries);

        }

        public void setPast_fam_illness(String past_fam_illness)
        {

            TextView mpost_fam_illness = (TextView) mView.findViewById(R.id.post_illness);
            mpost_fam_illness.setText(past_fam_illness);

        }

        public void setSmoker_answer(String smoker_answer)
        {

            TextView mpost_smoker_answer = (TextView) mView.findViewById(R.id.post_smoker);
            mpost_smoker_answer.setText(smoker_answer);

        }

        public void setAlcoholic_answer(String alcoholic_answer)
        {

            TextView mpost_alcohol_answer = (TextView) mView.findViewById(R.id.post_drinker);
            mpost_alcohol_answer.setText(alcoholic_answer);

        }

        public void setDrug_answer(String drug_answer)
        {

            TextView mpost_drug_answer = (TextView) mView.findViewById(R.id.post_drug);
            mpost_drug_answer.setText(drug_answer);

        }
    }

    //implements the main_menu layout which allows the user to edit their profile
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(MedicalHistoryTemp.this, UpdateMedicalHistory.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}