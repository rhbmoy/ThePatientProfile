package git.combmoy01firebase.httpsgithub.patientprof;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;


public class UpdateActivity extends DrawerActivity {

    private ImageButton mSelectImage;
    private EditText mpat_name, mpat_gender, mpat_dob, mpat_email, mpat_healthcare, mpat_address, mpat_phonenum, mpat_pharmacy;
    private Button mpat_update;
    private int mYear, mMonth, mDay;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private Uri resultUri = null;
    private ProgressDialog mProgress;
    private static final int GALLERY_REQUEST = 1;


    private Spinner bloodType;
    private TextView result;
    private String mPostKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_update, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile");
        mStorage = FirebaseStorage.getInstance().getReference();

        mSelectImage = (ImageButton) findViewById(R.id.patient_image);
        mpat_name = (EditText) findViewById(R.id.p_name);
        mpat_gender = (EditText) findViewById(R.id.pat_gender);
        mpat_dob = (EditText) findViewById(R.id.pat_dob);
        mpat_email = (EditText) findViewById(R.id.pat_email);
        mpat_healthcare = (EditText) findViewById(R.id.pat_healthcare);
        mpat_address = (EditText) findViewById(R.id.pat_address);
        mpat_phonenum = (EditText) findViewById(R.id.pat_phonenum);
        mpat_pharmacy = (EditText) findViewById(R.id.pat_pharmacy);
        bloodType = (Spinner) findViewById(R.id.bloodSpinner);
        result = (TextView) findViewById(R.id.resultBlood);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(UpdateActivity.this, R.array.blood_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bloodType.setAdapter(adapter);

        mProgress = new ProgressDialog(this);

        //Allows the user to select an image when clicked
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creates a new intent that allows user to select object from the phone based on MIME type
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                //Sets the MIME type to image so that the intent looks for image objects
                galleryIntent.setType("image/*");
                //Once the user selects an image it sets it to the Gallery Request Code and then calls the function startActivityForResult
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        //Allows the user to select their birthdate when clicked
        mpat_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        mpat_dob.setText("" + (selectedmonth+1) + "/" + selectedday + "/" + selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select your Date of Birth");
                mDatePicker.show();  }
        });


        bloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String patient_bloodType = bloodType.getSelectedItem().toString().trim();
                TextView results = (TextView) findViewById(R.id.resultBlood);
                results.setText(patient_bloodType);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        mpat_update = (Button) findViewById(R.id.pat_update);

        mpat_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                postData();
                //Create child in root database
                //Assign some value to the child object

               /* mProgress.setMessage("Storing Profile...");
                mProgress.show();

                StorageReference filepath = mStorage.child("patient_image").child(resultUri.getLastPathSegment());

                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        @SuppressWarnings("VisibleForTests")  Uri downloadUrl = taskSnapshot.getDownloadUrl();


                    }
                });


                String patient_name = mpat_name.getText().toString().trim();
                String patient_email = mpat_email.getText().toString().trim();
                String patient_healthcare = mpat_healthcare.getText().toString().trim();
                String patient_dob = mpat_dob.getText().toString().trim();
                String patient_address = mpat_address.getText().toString().trim();
                String patient_phonenum = mpat_phonenum.getText().toString().trim();
                String patient_gender = mpat_gender.getText().toString().trim();
                String filePath = filepath.toString();
                //String full_address = patient_address.concat(", ".concat(patient_city.concat(", ".concat(patient_state.concat(", ".concat(patient_zip))))));

                HashMap<String, String> dataMap = new HashMap<String, String>();

                dataMap.put("Patient_Name", patient_name);
                dataMap.put("Email", patient_email);
                dataMap.put("Health_Care_Provider", patient_healthcare);
                dataMap.put("Date_of_Birth", patient_dob);
                dataMap.put("Patient_Address", patient_address);
                //dataMap.put("City", patient_city);
                //dataMap.put("State", patient_state);
                //dataMap.put("Zip Code", patient_zip);
                dataMap.put("Phone_Number", patient_phonenum);
                dataMap.put("Gender", patient_gender);
                //dataMap.put("Full Address", full_address);
                dataMap.put("Image", filePath);

                //added this
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //This assigns a new key for each patient name ALSO ADDED .child(userId)
                mDatabase.child(userId).push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    //Test to see whether or not the data was stored
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()) {

                            Toast.makeText(UpdateActivity.this, "Profile Updated...", Toast.LENGTH_LONG).show();
                            mProgress.dismiss();

                            //returns to home page
                            startActivity(new Intent(UpdateActivity.this, UserActivity.class));
                        }
                        else {
                            Toast.makeText(UpdateActivity.this, "Error...", Toast.LENGTH_LONG).show();
                            mProgress.dismiss();
                        }
                    }
                }); */

            }
        });
    }

    //saves user input and image to Firebase
    private void postData() {

        mProgress.setMessage("Storing Profile...");


        //takes all of the user inputs and translates the text into string format
        final String patient_name = mpat_name.getText().toString().trim();
        final String patient_email = mpat_email.getText().toString().trim();
        final String patient_healthcare = mpat_healthcare.getText().toString().trim();
        final String patient_dob = mpat_dob.getText().toString().trim();
        final String patient_address = mpat_address.getText().toString().trim();
        final String patient_phonenum = mpat_phonenum.getText().toString().trim();
        final String patient_gender = mpat_gender.getText().toString().trim();
        final String patient_pharmacy = mpat_pharmacy.getText().toString().trim();
        final String patient_blood_type = result.getText().toString().trim();


        //continue as long as none of the fields are empty
        if(!TextUtils.isEmpty(patient_name) && !TextUtils.isEmpty(patient_email) && !TextUtils.isEmpty(patient_healthcare) && !TextUtils.isEmpty(patient_dob) && !TextUtils.isEmpty(patient_address) && !TextUtils.isEmpty(patient_phonenum) && !TextUtils.isEmpty(patient_gender) && !TextUtils.isEmpty(patient_pharmacy) && !TextUtils.isEmpty(patient_blood_type)&& resultUri != null)
        {

            mProgress.show();

            //creates a storage reference that adds the image as a child under "patient_images"
            StorageReference imageFilepath =  mStorage.child("patient_images").child(resultUri.getLastPathSegment());

            //takes the cropped image and stores it in firebase storage under patient_images
            //then if the image is stored successfully execute the remaining code
            imageFilepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests")
                    //gets the path to the download url for the image and stores it in downloadUrl
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    //gets the current users uid
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    //creates a database reference and pushes values as children under Patient_Information
                    final DatabaseReference newPost = mDatabase.child("UserID").child(userId).child("Patient_Information").push();

                    //sets the values in database from user input
                    newPost.child("Patient_Name").setValue(patient_name);
                    newPost.child("Email").setValue(patient_email);
                    newPost.child("Health_Care_Provider").setValue(patient_healthcare);
                    newPost.child("Date_of_Birth").setValue(patient_dob);
                    newPost.child("Patient_Address").setValue(patient_address);
                    newPost.child("Phone_Number").setValue(patient_phonenum);
                    newPost.child("Gender").setValue(patient_gender);
                    newPost.child("Pharmacy").setValue(patient_pharmacy);
                    newPost.child("Blood_Type").setValue(patient_blood_type);

                    //takes the image downloadUrl and converts it to a string to save
                    newPost.child("Image").setValue(downloadUrl.toString());

                    mProgress.dismiss();


                    //returns to home page
                    startActivity(new Intent(UpdateActivity.this, UserActivity.class));
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode matches the selected images GALLERY_REQUEST code then continue
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            //stores the image uri into resultUri
            resultUri = data.getData();

            //takes the stored image and starts the cropping function
            CropImage.activity(resultUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //Once the user has cropped the image in the way they wanted it the cropped image uri is now saved in result
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                //sets the resultUri to the new uri that stores the newly cropped image
                resultUri = result.getUri();

                //sets the ImageView to hold the cropped image uri
                mSelectImage.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}

