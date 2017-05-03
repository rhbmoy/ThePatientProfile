package git.combmoy01firebase.httpsgithub.patientprof;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class PostDoctorOverview extends DrawerActivity {

    private EditText inputDoctorName, inputDoctorSpecialty, inputDoctorLocation, inputVisitDate, inputVisitSynopsis;
    private TextInputLayout inputLayoutDoctorName, inputLayoutDoctorSpecialty, inputLayoutDoctorLocation, inputLayoutVisitDate, inputLayoutDoctorSynopsis;
    private Button add_new_visit;

    private int mYear, mMonth, mDay;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_post_doctor_overview);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_post_doctor_overview, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs

        /*
        1.)	Basic doctor’s information
        2.)	Past visit dates
        3.)	Doctor’s synopsis of visit
        4.)	Current medications prescribed by that doctor
        5.)	Labs and tests given by that doctor
        */

        //Basic Doctor's information
            //Name, Specialty, Office Location

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userID).child("Visit_Overview");

        inputLayoutDoctorName= (TextInputLayout) findViewById(R.id.input_layout_doctor_name);
        inputLayoutDoctorSpecialty = (TextInputLayout) findViewById(R.id.input_layout_doctor_specialty);
        inputLayoutDoctorLocation = (TextInputLayout) findViewById(R.id.input_layout_doctor_location);
        inputLayoutVisitDate = (TextInputLayout) findViewById(R.id.input_layout_visit_date);
        inputLayoutDoctorSynopsis = (TextInputLayout) findViewById(R.id.input_layout_visit_synopsis);
        inputDoctorName = (EditText) findViewById(R.id.doctor_name);
        inputDoctorSpecialty = (EditText) findViewById(R.id.doctor_specialty);
        inputDoctorLocation = (EditText) findViewById(R.id.doctor_location);
        inputVisitDate = (EditText) findViewById(R.id.visit_date);
        inputVisitSynopsis = (EditText) findViewById(R.id.visit_synopsis);
        add_new_visit = (Button) findViewById(R.id.add_visit_btn);

        inputDoctorName.addTextChangedListener(new PostDoctorOverview.MyTextWatcher(inputDoctorName));
        inputDoctorSpecialty.addTextChangedListener(new PostDoctorOverview.MyTextWatcher(inputDoctorSpecialty));
        inputDoctorLocation.addTextChangedListener(new PostDoctorOverview.MyTextWatcher(inputDoctorLocation));
        inputVisitDate.addTextChangedListener(new PostDoctorOverview.MyTextWatcher(inputVisitDate));
        inputVisitSynopsis.addTextChangedListener(new PostDoctorOverview.MyTextWatcher(inputVisitSynopsis));

        inputVisitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(PostDoctorOverview.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        inputVisitDate.setText("" + (selectedmonth+1) + "/" + selectedday + "/" + selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Visit Date");
                mDatePicker.show();
            }
        });

        add_new_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
                startPosting();

                //returns to CurrentMedicine page
                startActivity(new Intent(PostDoctorOverview.this, OverviewList.class));
            }
        });
    }

    //adding medications to the list
    private void startPosting() {

        String doctor_name = inputDoctorName.getText().toString().trim();
        String doctor_specialty = inputDoctorSpecialty.getText().toString().trim();
        String doctor_location = inputDoctorLocation.getText().toString().trim();
        String visit_date = inputVisitDate.getText().toString().trim();
        String visit_synopsis = inputVisitSynopsis.getText().toString().trim();

        DatabaseReference newPost = mDatabase.push();

        newPost.child("doctor_name").setValue(doctor_name);
        newPost.child("doctor_specialty").setValue(doctor_specialty);
        newPost.child("doctor_location").setValue(doctor_location);
        newPost.child("visit_date").setValue(visit_date);
        newPost.child("visit_synopsis").setValue(visit_synopsis);
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateDoctorName()) {
            return;
        }

        if (!validateDoctorSpecialty()) {
            return;
        }

        if (!validateDoctorLocation()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Visit Added...", Toast.LENGTH_SHORT).show();
    }

    private boolean validateDoctorName() {
        if (inputDoctorName.getText().toString().trim().isEmpty()) {
            inputDoctorName.setError(getString(R.string.err_msg_docname));
            requestFocus(inputDoctorName);
            return false;
        } else {
            inputLayoutDoctorName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDoctorSpecialty() {
        String specialty = inputDoctorSpecialty.getText().toString().trim();

        if (specialty.isEmpty()) {
            inputLayoutDoctorSpecialty.setError(getString(R.string.err_msg_docspecialty));
            requestFocus(inputDoctorSpecialty);
            return false;
        } else {
            inputLayoutDoctorSpecialty.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDoctorLocation() {
        if (inputDoctorLocation.getText().toString().trim().isEmpty()) {
            inputLayoutDoctorLocation.setError(getString(R.string.err_msg_doclocation));
            requestFocus(inputDoctorLocation);
            return false;
        } else {
            inputLayoutDoctorLocation.setErrorEnabled(false);
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
                case R.id.doctor_name:
                    validateDoctorName();
                    break;
                case R.id.doctor_specialty:
                    validateDoctorSpecialty();
                    break;
                case R.id.doctor_location:
                    validateDoctorLocation();
                    break;
                case R.id.visit_date:
                    break;
                case R.id.visit_synopsis:
                    break;
            }
        }
    }
}
