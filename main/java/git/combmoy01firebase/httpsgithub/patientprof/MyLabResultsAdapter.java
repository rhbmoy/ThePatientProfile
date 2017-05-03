package git.combmoy01firebase.httpsgithub.patientprof;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Bryan on 4/28/2017.
 */

public class MyLabResultsAdapter extends RecyclerView.Adapter<MyLabResultsAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploads;

    public MyLabResultsAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_files, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Upload upload = uploads.get(position);

        holder.labViewName.setText(upload.getName());
        holder.labViewDoctor.setText(upload.getDoctor());

        Glide.with(context).load(upload.getUrl()).into(holder.imageView);

        /*final String mPostKey = upload.getUrl();
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), mPostKey, Toast.LENGTH_SHORT).show();

                WebView webView = (WebView) v.findViewById(R.id.webview);

                webView.setWebViewClient(new MyWebClient());

                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl(mPostKey);
                //webView.loadUrl("https://docs.google.com/viewer?url=" + mPostKey);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView labViewName;
        public TextView labViewDoctor;
        public ImageView imageView;


        public ViewHolder(final View itemView) {
            super(itemView);

            labViewName = (TextView) itemView.findViewById(R.id.labViewName);
            labViewDoctor = (TextView) itemView.findViewById(R.id.labViewDoctor);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position

            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Upload upload = uploads.get(position);

                final String mPostKey = upload.getUrl();
                // We can access the data within the views
                Toast.makeText(context, mPostKey, Toast.LENGTH_SHORT).show();

                //convert mPostKey to uri
                Uri uri = Uri.parse(mPostKey);
                //creates new intent open browser and send to uri
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //starts the intent
                context.startActivity(intent);

            }
        }
    }

}
