package git.combmoy01firebase.httpsgithub.patientprof;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Bryan on 4/27/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static String myString = null;

    @Override
    public void onReceive(Context context, Intent arg1) {

        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        buildNotification(context);

    }

    private void buildNotification(Context context){

        NotificationManager notificationManager
                = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);

        Intent intent = new Intent(context, SelectedMedicationActivity.class);

        PendingIntent pendingIntent
                = PendingIntent.getActivity(context, 0, intent, 0);

        builder

                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Medication Alarm")
                .setContentText("Its time to take your medication!")
                .setContentInfo("ContentInfo")
                .setTicker("Patient Profile Medication Reminder")
                .setLights(0xFFFF0000, 500, 500) //setLights (int argb, int onMs, int offMs)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = builder.getNotification();

        notificationManager.notify(R.mipmap.ic_launcher, notification);
    }

}
