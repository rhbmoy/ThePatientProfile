package git.combmoy01firebase.httpsgithub.patientprof;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SelectedMedicationActivity extends Activity {

    //test ALSO CHANGED APPCOMPAT TO ACTIVITY
    Button buttonstartSetDialog;
    Button buttonCancelAlarm;
    TextView textAlarmPrompt;

    TimePicker timePicker;
    CheckBox optRepeat;

    EditText alarmRepeatInt;

    Button showAlarm;

    //end test

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mPostSingleMedName;
    private TextView mPostSingleMedDose;
    private TextView mPostSingleMedFreq;
    private TextView mPostSingleMedReason;
    private TextView mPostSingleMedPrevention;
    private Button mSingleRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_medication);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child("UserID").child(userID).child("Medications");

        //stores the medications key into a string
        mPost_key = getIntent().getExtras().getString("medication_id");

        mPostSingleMedName = (TextView) findViewById(R.id.selected_med_name);
        mPostSingleMedDose = (TextView) findViewById(R.id.selected_med_dose);
        mPostSingleMedFreq = (TextView) findViewById(R.id.selected_med_freq);
        mPostSingleMedReason = (TextView) findViewById(R.id.selected_med_reason);
        mPostSingleMedPrevention = (TextView) findViewById(R.id.selected_med_prevention);

        //test
        showAlarm = (Button) findViewById(R.id.showAlarm);
        timePicker = (TimePicker)findViewById(R.id.picker);
        optRepeat = (CheckBox)findViewById(R.id.optrepeat);
        textAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
        alarmRepeatInt = (EditText) findViewById(R.id.alarm_repeat_interval);

        showAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker.setVisibility(View.VISIBLE);
                optRepeat.setVisibility(View.VISIBLE);
                textAlarmPrompt.setVisibility(View.VISIBLE);
                textAlarmPrompt.setVisibility(View.VISIBLE);
                buttonstartSetDialog.setVisibility(View.VISIBLE);
                buttonCancelAlarm.setVisibility(View.VISIBLE);
            }
        });

        optRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmRepeatInt.setVisibility(View.VISIBLE);
            }
        });

        buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();

                calSet.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calSet.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                if(calSet.compareTo(calNow) <= 0){
                    //Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1);
                }

                setAlarm(calSet, optRepeat.isChecked());

            }});

        buttonCancelAlarm = (Button)findViewById(R.id.cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                cancelAlarm();
            }});

        //end test

        mSingleRemoveBtn = (Button) findViewById(R.id.singleRemoveBtn);

        //Toast.makeText(SelectedMedicationActivity.this, mPost_key, Toast.LENGTH_LONG).show();

        //converts the post key to a unique id to use for alarm request code
        //String mPostKey = mPost_key.replaceAll("[^\\d]", "");
        //Toast.makeText(SelectedMedicationActivity.this, mPostKey, Toast.LENGTH_LONG).show();

        //int mPostKeytoInt = Integer.parseInt(mPostKey);
        //Toast.makeText(this,String.valueOf(mPostKeytoInt),Toast.LENGTH_LONG).show();

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_med_name = (String) dataSnapshot.child("medName").getValue();
                mPostSingleMedName.setText(post_med_name);

                String post_med_dose = (String) dataSnapshot.child("medDose").getValue();
                mPostSingleMedDose.setText(post_med_dose);

                String post_med_freq = (String) dataSnapshot.child("medFreq").getValue();
                mPostSingleMedFreq.setText(post_med_freq);

                String post_med_reason = (String) dataSnapshot.child("medReason").getValue();
                mPostSingleMedReason.setText(post_med_reason);

                String post_med_prevention = (String) dataSnapshot.child("medPrevention").getValue();
                mPostSingleMedPrevention.setText(post_med_prevention);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSingleRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPost_key).removeValue();

                Intent currMedicationsIntent = new Intent(SelectedMedicationActivity.this, CurrentMedications.class);
                startActivity(currMedicationsIntent);
            }
        });

    }

    //test
    private void setAlarm(Calendar targetCal, boolean repeat){

        //converts the post key to a unique id to use for alarm request code
        String mPostKey = mPost_key.replaceAll("[^\\d]", "");
        //Toast.makeText(SelectedMedicationActivity.this, mPostKey, Toast.LENGTH_LONG).show();

        int mPostKeytoInt = Integer.parseInt(mPostKey);
        Toast.makeText(this,String.valueOf(mPostKeytoInt),Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), mPostKeytoInt, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

        if(repeat){

            String alarm_repeat_interval = alarmRepeatInt.getText().toString();

            long l = Long.parseLong(alarm_repeat_interval);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    targetCal.getTimeInMillis(),
                    //SECONDS INSTEAD OF HOURS FOR TESTING RIGHT NOW
                    TimeUnit.SECONDS.toMillis(l),
                    pendingIntent);

            textAlarmPrompt.setText(
                    "\n\n***\n"
                            + "Alarm is set@ " + targetCal.getTime() + "\n"
                            + "Repeat every " + l + " hours\n"
                            + "***\n");
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    targetCal.getTimeInMillis(),
                    pendingIntent);

            textAlarmPrompt.setText(
                    "\n\n***\n"
                            + "Alarm is set@ " + targetCal.getTime() + "\n"
                            + "One shot\n"
                            + "***\n");
        }


    }

    private void cancelAlarm(){

        //converts the post key to a unique id to use for alarm request code
        String mPostKey = mPost_key.replaceAll("[^\\d]", "");
        //Toast.makeText(SelectedMedicationActivity.this, mPostKey, Toast.LENGTH_LONG).show();

        int mPostKeytoInt = Integer.parseInt(mPostKey);
        Toast.makeText(this,String.valueOf(mPostKeytoInt),Toast.LENGTH_LONG).show();

        textAlarmPrompt.setText
                (
                        "\n\n***\n"
                                + "Alarm Cancelled! \n"
                                + "***\n");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), mPostKeytoInt, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
    //end test
}
