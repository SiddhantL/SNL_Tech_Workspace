package com.example.snltech.ui.gallery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.snltech.OnSwipeTouchListener;
import com.example.snltech.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class DisplayFiles extends AppCompatActivity {
    int position;
    TextView tv;
    String imageURL;
    String filename;
    VideoView videoView;
    SimpleExoPlayerView exoPlayerView;
    ImageView swipeImage;
    String id;
    // creating a variable for exoplayer
    SimpleExoPlayer exoPlayer;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_files);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView appbarTV = (TextView)toolbar.findViewById(R.id.appbartextview);
        appbarTV.setText("SNL Tech");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        exoPlayerView=findViewById(R.id.idExoPlayerView);
        final String url=getIntent().getStringExtra("url");
        imageURL=url;
        videoView=findViewById(R.id.videoView);
        id=getIntent().getStringExtra("id");
        position=getIntent().getIntExtra("pos",1);
        filename=getIntent().getStringExtra("filename");
        final Button left=findViewById(R.id.buttonleft);
        Button right=findViewById(R.id.buttonright);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                left();
            }
        });
        tv=findViewById(R.id.text);
        tv.setText(filename);
        swipeImage=findViewById(R.id.displayFile);
        Glide.with(getApplicationContext()).load(url).into(swipeImage);
        if (filename!=null) {
            if (!filename.substring(filename.lastIndexOf(".")).equals(".mp4")) {
                exoPlayerView.setVisibility(View.GONE);
            } else {
                exoPlayerView.setVisibility(View.VISIBLE);
                initializeExoplayerView(imageURL);
            }
        }
        swipeImage.setOnTouchListener(new OnSwipeTouchListener(DisplayFiles.this) {
            public void onSwipeLeft() {
                left();
            }

            public void onSwipeRight() {
                right();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_download_file, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_download:
                if (verifyPermissions()) {
                    if (imageURL!=null) {
                        downloadfile();
                    }else {
                       // Toast.makeText(this, "Hm", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public Boolean verifyPermissions() {

        // This will return the current Status
        int permissionExternalMemory = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {

            String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            // If permission not granted then ask for permission real time.
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS, 1);
            return false;
        }

        return true;

    }
    private void downloadfile() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(imageURL);

        final ProgressDialog  pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Downloading Please Wait!");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        final File rootPath = new File(Environment.getExternalStorageDirectory(), "SNL Tech");

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }


        final File localFile = new File(rootPath, filename);

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener <FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                if (localFile.canRead()){

                    pd.dismiss();
                }
                Toast.makeText(DisplayFiles.this, "Download Completed", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
              //  Toast.makeText(this, "Download Incompleted", Toast.LENGTH_LONG).show();
                Toast.makeText(DisplayFiles.this, "Download Incompleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initializeExoplayerView(String videoURL) {
        try {
            // bandwidthmeter is used for getting default bandwidth
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            // track selector is used to navigate between video using a default seeker.
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            // we are adding our track selector to exoplayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // we are parsing a video url and
            // parsing its video uri.
            Uri videouri = Uri.parse(videoURL);

            // we are creating a variable for data source
            // factory and setting its user agent as 'exoplayer_view'
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

            // we are creating a variable for extractor
            // factory and setting it to default extractor factory.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // we are creating a media source with above variables
            // and passing our event handler as null,
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

            // inside our exoplayer view
            // we are setting our player
            exoPlayerView.setPlayer(exoPlayer);

            // we are preparing our exoplayer
            // with media source.
            exoPlayer.prepare(mediaSource);

            // we are setting our exoplayer
            // when it is ready.
            exoPlayer.setPlayWhenReady(true);
        } catch (Exception e) {
            // below line is used for handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }
    public void left(){
        Glide.with(getApplicationContext()).load(R.drawable.document).into(swipeImage);
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("file").child(id);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                position++;
                filename=snapshot.child("File"+Integer.toString(position)).getValue(String.class);
                if (filename!=null) {
                    if (!filename.substring(filename.lastIndexOf(".")).equals(".mp4")) {
                        exoPlayerView.setVisibility(View.GONE);
                    } else {
                        exoPlayerView.setVisibility(View.VISIBLE);
                    }
                }
                tv.setText(filename);
                if (snapshot.child("File"+Integer.toString(position)).getValue()!=null){
                    FirebaseStorage stor = FirebaseStorage.getInstance();
                    stor.getReference().child("files/"+id+"/"+(snapshot.child("File"+Integer.toString(position)).getValue(String.class))).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            imageURL=url;
                            initializeExoplayerView(imageURL);
                            //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                            Glide.with(getApplicationContext()).load(url).into(swipeImage);
                        }
                    });
                }else{
                    position=1;
                    filename=snapshot.child("File"+Integer.toString(position)).getValue(String.class);
                    if (filename!=null) {
                        if (!filename.substring(filename.lastIndexOf(".")).equals(".mp4")) {
                            exoPlayerView.setVisibility(View.GONE);
                        } else {
                            exoPlayerView.setVisibility(View.VISIBLE);
                        }
                    }
                    tv.setText(filename);
                    if (snapshot.child("File"+Integer.toString(position)).getValue()!=null) {
                        FirebaseStorage stor = FirebaseStorage.getInstance();
                        stor.getReference().child("files/" + id + "/" + (snapshot.child("File" + Integer.toString(position)).getValue(String.class))).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                imageURL=url;
                                initializeExoplayerView(imageURL);
                                //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                                Glide.with(getApplicationContext()).load(url).into(swipeImage);
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void right(){
        Glide.with(getApplicationContext()).load(R.drawable.document).into(swipeImage);
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("file").child(id);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                position--;
                filename=snapshot.child("File"+Integer.toString(position)).getValue(String.class);
                if (filename!=null) {
                    if (!filename.substring(filename.lastIndexOf(".")).equals(".mp4")) {
                        exoPlayerView.setVisibility(View.GONE);
                    } else {
                        exoPlayerView.setVisibility(View.VISIBLE);
                    }
                }
                tv.setText(filename);
                if (snapshot.child("File"+Integer.toString(position)).getValue()!=null){
                    FirebaseStorage stor = FirebaseStorage.getInstance();
                    stor.getReference().child("files/"+id+"/"+(snapshot.child("File"+Integer.toString(position)).getValue(String.class))).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            imageURL=url;
                            initializeExoplayerView(imageURL);
                            //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                            Glide.with(getApplicationContext()).load(url).into(swipeImage);
                        }
                    });
                }else{
                    position=1;
                    filename=snapshot.child("File"+Integer.toString(position)).getValue(String.class);
                    if (filename!=null) {
                        if (!filename.substring(filename.lastIndexOf(".")).equals(".mp4")) {
                            exoPlayerView.setVisibility(View.GONE);
                        } else {
                            exoPlayerView.setVisibility(View.VISIBLE);
                        }
                    }
                    tv.setText(filename);
                    if (snapshot.child("File"+Integer.toString(position)).getValue()!=null) {
                        FirebaseStorage stor = FirebaseStorage.getInstance();
                        stor.getReference().child("files/" + id + "/" + (snapshot.child("File" + Integer.toString(position)).getValue(String.class))).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                imageURL=url;
                                initializeExoplayerView(imageURL);
                                //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                                Glide.with(getApplicationContext()).load(url).into(swipeImage);
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }

