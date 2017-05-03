package git.combmoy01firebase.httpsgithub.patientprof;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.twitter.sdk.android.core.models.TwitterCollection;

import java.io.File;
import java.io.IOException;


public class LabResults extends DrawerActivity {

    private Button bloodBtn;
    private Button a1cBtn;
    private TextView hba_1c_test, blood_test;
    private WebView webView;

    private StorageReference labResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // don’t set any content view here, since its already set in DrawerActivity
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_lab_results, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs

        webView = (WebView) findViewById(R.id.webview);

        blood_test = (TextView) findViewById(R.id.bloodDate);
        bloodBtn = (Button) findViewById(R.id.bloodBtn);

        hba_1c_test = (TextView) findViewById(R.id.a1cDate);
        a1cBtn = (Button) findViewById(R.id.a1cBtn);


        final String bloodtest = "https://firebasestorage.googleapis.com/v0/b/patientprof-72591.appspot.com/o/Lipid_Sample_Report.pdf?alt=media&token=c4d5b80b-1c0c-446c-8f12-b10ed1d8a157";
        final String a1c = "https://firebasestorage.googleapis.com/v0/b/patientprof-72591.appspot.com/o/A1c_Sample_Report.pdf?alt=media&token=dfb00262-d33c-43d5-a718-f2425fbb9204";

        bloodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("" + bloodtest);
            }
        });

        a1cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + a1c);
            }
        });
    }
}
