package git.combmoy01firebase.httpsgithub.patientprof;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import git.combmoy01firebase.httpsgithub.patientprof.KeyPairBoolData;
import git.combmoy01firebase.httpsgithub.patientprof.MultiSpinnerSearch;
import git.combmoy01firebase.httpsgithub.patientprof.SpinnerListener;
import git.combmoy01firebase.httpsgithub.patientprof.MultiSpinner;

public class UpdateMedicalHistory extends DrawerActivity {

    private static final String TAG = "UpdateMedicalHistory";
    private EditText mpast_surgeries;
    private EditText mpast_family_illness;
    private EditText msmoker_answer;
    private EditText mdrinker_answer;
    private EditText mdrug_answer;
    private EditText mallergies;
    private TextView mspinner_data;

    private Button mupdate_History;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update_medical_history);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_update_medical_history, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userId).child("Medical_History_Info");

        mallergies = (EditText) findViewById(R.id.listAllergies);
        mpast_surgeries = (EditText) findViewById(R.id.past_surgeries);
        mpast_family_illness = (EditText) findViewById(R.id.past_family_illness);
        msmoker_answer = (EditText) findViewById(R.id.smoker_answer);
        mdrinker_answer = (EditText) findViewById(R.id.drinker_answer);
        mdrug_answer = (EditText) findViewById(R.id.drug_answer);

        mupdate_History = (Button) findViewById(R.id.update_History);

        mupdate_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateHistory();
            }
        });

        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.med_history_array));

        final List<KeyPairBoolData> listArray0 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }

        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        MultiSpinnerSearch searchMultiSpinnerUnlimited = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerUnlimited);

        searchMultiSpinnerUnlimited.setItems(listArray0, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });
    }

    private void updateHistory() {

        mspinner_data = (TextView) findViewById(R.id.listTextViewSpinner);

        final String user_allergies = mallergies.getText().toString().trim();
        final String spinner_data = mspinner_data.getText().toString().trim();
        final String past_surgerie_val = mpast_surgeries.getText().toString().trim();
        final String past_family_illness_val = mpast_family_illness.getText().toString().trim();
        final String smoker_answer = msmoker_answer.getText().toString();
        final String drinker_answer = mdrinker_answer.getText().toString();
        final String drug_answer = mdrug_answer.getText().toString();

        if(!TextUtils.isEmpty(past_surgerie_val) && !TextUtils.isEmpty(past_family_illness_val) && !TextUtils.isEmpty(smoker_answer) && !TextUtils.isEmpty(drinker_answer) && !TextUtils.isEmpty(drug_answer)){

            DatabaseReference updateMedicalHistory = mDatabase.push();

            updateMedicalHistory.child("spinner_data").setValue(spinner_data);
            updateMedicalHistory.child("user_allergies").setValue(user_allergies);
            updateMedicalHistory.child("alcoholic_answer").setValue(drinker_answer);
            updateMedicalHistory.child("drug_answer").setValue(drug_answer);
            updateMedicalHistory.child("past_fam_illness").setValue(past_family_illness_val);
            updateMedicalHistory.child("past_surgeries").setValue(past_surgerie_val);
            updateMedicalHistory.child("smoker_answer").setValue(smoker_answer);


            startActivity(new Intent(UpdateMedicalHistory.this, MedicalHistoryTemp.class));
        }
    }
}
