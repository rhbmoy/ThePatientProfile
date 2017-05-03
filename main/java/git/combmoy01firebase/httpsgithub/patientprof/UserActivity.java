package git.combmoy01firebase.httpsgithub.patientprof;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class UserActivity extends DrawerActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    //test
    private RecyclerView mpatient_information;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.patient_inform);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.patient_inform, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs
        //test
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userID).child("Patient_Information");
        mpatient_information = (RecyclerView) findViewById(R.id.recyclerview);
        mpatient_information.setHasFixedSize(true);
        mpatient_information.setLayoutManager(new LinearLayoutManager(this));
        mpatient_information.setItemAnimator(new DefaultItemAnimator());

        //test

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //signOutButton = (Button) findViewById(R.id.sign_out);
        // helloUserText = (TextView) findViewById(R.id.text_user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // if user is null launch login activity
                    startActivity(new Intent(UserActivity.this, LoginActivity.class));
                    finish();
                } else {

                    //helloUserText.setText("Hello  " + user.getEmail() +"" + user.getUid() +"");

                }
            }
        };

        //no need for signout button
        /*signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutButton();
            }
        });*/

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
            startActivity(new Intent(UserActivity.this, UpdateActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);

        //test
        FirebaseRecyclerAdapter<PatientInfo, PatientInfoViewHolder> firebaseRecylerAdapter = new FirebaseRecyclerAdapter<PatientInfo, PatientInfoViewHolder>(
                PatientInfo.class,
                R.layout.patient_inform,
                PatientInfoViewHolder.class,
                mDatabase


        ) {
            protected void populateViewHolder(PatientInfoViewHolder viewHolder, PatientInfo pats_info, int position) {

                final String post_key = getRef(0).getKey();

                viewHolder.setPatient_name(pats_info.getPatient_Name());
                viewHolder.setEmail(pats_info.getEmail());
                viewHolder.setDate_of_birth(pats_info.getDate_of_Birth());
                viewHolder.setGender(pats_info.getGender());
                viewHolder.setPhone_number(pats_info.getPhone_Number());
                viewHolder.setPatient_address(pats_info.getPatient_Address());
                viewHolder.setHealth_care_provider(pats_info.getHealth_Care_Provider());
                viewHolder.setPharmacy(pats_info.getPharmacy());
                viewHolder.setBlood_Type(pats_info.getBlood_Type());
                viewHolder.setImage(getApplicationContext(), pats_info.getImage());


                if(position == 1){
                    mDatabase.child(post_key).removeValue();
                }

                //testing to remove post
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(UserActivity.this, post_key, Toast.LENGTH_LONG).show();
                    }
                });

            }
        };
        //test
        mpatient_information.setAdapter(firebaseRecylerAdapter);

    }

    //test
    public static class PatientInfoViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public PatientInfoViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setPatient_name(String patient_name) {

            TextView p_name = (TextView) mView.findViewById(R.id.p_name);
            p_name.setText(patient_name);

        }

        public void setEmail(String email) {

            TextView p_email = (TextView) mView.findViewById(R.id.p_email);
            p_email.setText(email);

        }

        public void setHealth_care_provider(String health_care_provider) {

            TextView p_healthcare = (TextView) mView.findViewById(R.id.p_healthcare);
            p_healthcare.setText(health_care_provider);

        }

        public void setDate_of_birth(String date_of_birth) {

            TextView p_dob = (TextView) mView.findViewById(R.id.p_dob);
            p_dob.setText(date_of_birth);

        }

        public void setPatient_address(String patient_address) {

            TextView p_address = (TextView) mView.findViewById(R.id.p_address);
            p_address.setText(patient_address);

        }

        public void setPhone_number(String phone_number) {

            TextView p_phonenum = (TextView) mView.findViewById(R.id.p_phonenum);
            p_phonenum.setText(phone_number);

        }

        public void setGender(String gender) {

            TextView p_gender = (TextView) mView.findViewById(R.id.p_gender);
            p_gender.setText(gender);

        }

        public void setPharmacy(String pharmacy)
        {
            TextView p_pharmacy = (TextView) mView.findViewById(R.id.p_pharmacy);
            p_pharmacy.setText(pharmacy);
        }

        public void setBlood_Type(String blood_type)
        {
            TextView p_blood_type = (TextView) mView.findViewById(R.id.p_blood_type);
            p_blood_type.setText(blood_type);
        }

        public void setImage(Context ctx, String image) {

            final ImageView p_image = (ImageView) mView.findViewById(R.id.p_image);

            Glide.with(ctx).load(image).into(p_image);

            /*FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(image);

            try {
                final File localFile = File.createTempFile("images", "jpg");
                storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        p_image.setImageBitmap(bitmap);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
            } catch (IOException e ) {} */


        }

    }

    //test
    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}

/*<?xml version="1.0" encoding="utf-8"?>

<ScrollView
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:id="@+id/scroll"
android:layout_width="match_parent"
android:layout_height="wrap_content">


<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_post"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    tools:context="git.combmoy01firebase.httpsgithub.patientprof.UserActivity"
    android:padding="15dp"
    android:paddingBottom="15dp"
    android:paddingEnd="15dp"
    android:paddingLeft="15dp"
    android:paddingRight="15dp"
    android:paddingStart="15dp"
    android:paddingTop="15dp"
    android:weightSum="1"
    android:background="@color/white">

    <TextView
        android:id="@+id/text_user"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="TextView"
        tools:layout_editor_absoluteX="178dp"
        tools:layout_editor_absoluteY="341dp" />

    <Button
        android:id="@+id/sign_out"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Sign Out"
        tools:layout_editor_absoluteX="148dp"
        tools:layout_editor_absoluteY="426dp" />


</LinearLayout>

</ScrollView>*/
