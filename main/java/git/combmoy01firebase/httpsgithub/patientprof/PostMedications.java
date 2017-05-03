package git.combmoy01firebase.httpsgithub.patientprof;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class PostMedications extends DrawerActivity {

    private EditText inputMedicineName, inputDose, inputFreq, inputReason, inputPrevention;
    private TextInputLayout inputLayoutMedicine, inputLayoutDose, inputLayoutFreq, inputLayoutReason, inputLayoutPrevention;
    private Button btnPost;

    //used to store default picture
    private StorageReference mStorage;
    private ImageView defaultPicture;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_post_medications);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_post_medications, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userID).child("Medications");
        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/patientprof-72591.appspot.com/o/icon-managing-medication.png?alt=media&token=e6330165-2e39-4c1f-888d-14d2c212dd5e");

        defaultPicture = (ImageView) findViewById(R.id.defaultImage);
        //loads image into default picture
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorage).into(defaultPicture);

        inputLayoutPrevention = (TextInputLayout) findViewById(R.id.input_layout_prevention);
        inputLayoutReason = (TextInputLayout) findViewById(R.id.input_layout_reason);
        inputLayoutMedicine = (TextInputLayout) findViewById(R.id.input_layout_medname);
        inputLayoutDose = (TextInputLayout) findViewById(R.id.input_layout_dose);
        inputLayoutFreq = (TextInputLayout) findViewById(R.id.input_layout_freq);
        inputPrevention = (EditText) findViewById(R.id.input_prevention);
        inputReason = (EditText) findViewById(R.id.input_reason);
        inputMedicineName = (EditText) findViewById(R.id.input_medname);
        inputDose = (EditText) findViewById(R.id.input_dose);
        inputFreq = (EditText) findViewById(R.id.input_freq);
        btnPost = (Button) findViewById(R.id.btn_post);

        inputMedicineName.addTextChangedListener(new MyTextWatcher(inputMedicineName));
        inputDose.addTextChangedListener(new MyTextWatcher(inputDose));
        inputFreq.addTextChangedListener(new MyTextWatcher(inputFreq));
        inputReason.addTextChangedListener(new MyTextWatcher(inputReason));
        inputPrevention.addTextChangedListener(new MyTextWatcher(inputPrevention));

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
                startPosting();

                //returns to CurrentMedicine page
                startActivity(new Intent(PostMedications.this, CurrentMedications.class));
            }
        });
    }

    //adding medications to the list

    private void startPosting() {

        String medPrevention = inputPrevention.getText().toString().trim();
        String medReason = inputReason.getText().toString().trim();
        String medName = inputMedicineName.getText().toString().trim();
        String medDose = inputDose.getText().toString().trim();
        String medFreq = inputFreq.getText().toString().trim();

        DatabaseReference newPost = mDatabase.push();

        newPost.child("medName").setValue(medName);
        newPost.child("medDose").setValue(medDose);
        newPost.child("medFreq").setValue(medFreq);
        newPost.child("medReason").setValue(medReason);
        newPost.child("medPrevention").setValue(medPrevention);
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateMedicineName()) {
            return;
        }

        if (!validateDose()) {
            return;
        }

        if (!validateFrequency()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Medication Added...", Toast.LENGTH_SHORT).show();
    }

    private boolean validateMedicineName() {
        if (inputMedicineName.getText().toString().trim().isEmpty()) {
            inputLayoutMedicine.setError(getString(R.string.err_msg_medname));
            requestFocus(inputMedicineName);
            return false;
        } else {
            inputLayoutMedicine.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDose() {
        String dose = inputDose.getText().toString().trim();

        if (dose.isEmpty()) {
            inputLayoutDose.setError(getString(R.string.err_msg_dose));
            requestFocus(inputDose);
            return false;
        } else {
            inputLayoutDose.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateFrequency() {
        if (inputFreq.getText().toString().trim().isEmpty()) {
            inputLayoutFreq.setError(getString(R.string.err_msg_freq));
            requestFocus(inputFreq);
            return false;
        } else {
            inputLayoutFreq.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_medname:
                    validateMedicineName();
                    break;
                case R.id.input_dose:
                    validateDose();
                    break;
                case R.id.input_freq:
                    validateFrequency();
                    break;
                case R.id.input_reason:
                    break;
                case R.id.input_prevention:
                    break;
            }
        }
    }
}

