package com.example.warpwallpapers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.PermissionRequest;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Permission;
import java.security.Permissions;
import java.security.PrivateKey;

import javax.security.auth.callback.PasswordCallback;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class Orignal_image_screen extends AppCompatActivity {

   private ImageButton back , dots;
   private ImageView img;
   private TextView Photoby , Photograper_id , site_description;

   public Bitmap bitmap;
   public BitmapDrawable bitmapDrawable;
   private String global_url;

   private AdRequest adRequest ;

   private InterstitialAd minterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orignal_image_screen);

        adRequest = new AdRequest.Builder().build();

        adshow(adRequest);

        adTimeHandler();



        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        Intent i = getIntent();
        global_url = i.getStringExtra("orignal_url");



        //back button listener...
        back = findViewById(R.id.backbuton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                adshow(adRequest);
                adTimeHandler();
            }

        });

       // Putting img in imnageview...
        try{
            img = findViewById(R.id.wallppaper);
            if(global_url.equals(null)){
                Toast.makeText(this, "Failed to load..", Toast.LENGTH_SHORT).show();
            }else {
                Log.d("url" , global_url);
                Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
                Picasso.get().load(global_url).into(img);
            }
        }catch (Exception e){
            Toast.makeText(this, "Fail to Load...", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

        //photographer name...
        Photoby = findViewById(R.id.photographer_name);
        String photographer = i.getStringExtra("initial");
        Photoby.setText("Clicked By - "+photographer);

        //photographer id...
        Photograper_id = findViewById(R.id.photographer_id);
        String photographer_id  = i.getStringExtra("id");
        Photograper_id.setText("Camera man id - "+photographer_id);

        //site descp..
        site_description = findViewById(R.id.databy);
        site_description.setText("Data by - Pexels.com");

        //dots
        dots = findViewById(R.id.dot_button);
        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiloag();
            }
        });


    }

    private void adTimeHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (minterstitialAd != null){
                    minterstitialAd.show(Orignal_image_screen.this);
                }
                else {
                    Log.d("adpending.." , "pending....");
                }
            }
        }, 5000);

    }

    private void adshow(AdRequest adRequest) {

        InterstitialAd.load(this, getString(R.string.Inter_ad_unit_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("ad_fail" , loadAdError.toString());
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                minterstitialAd = interstitialAd;

                minterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        minterstitialAd = null;
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                });
            }
        });

    }

    private void showDiloag(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        LinearLayout downloadLayout = dialog.findViewById(R.id.download_btn);
        LinearLayout shareLayout = dialog.findViewById(R.id.share_btn);

        downloadLayout.setOnClickListener(new View.OnClickListener()  {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View v) {
                    bitmapDrawable=(BitmapDrawable) img.getDrawable();
                    bitmap=bitmapDrawable.getBitmap();

                    FileOutputStream fileOutputStream = null;
                    File sdcard = Environment.getExternalStorageDirectory();
                    File Directory = new File(sdcard.getAbsolutePath()+ "/Download");
                    Directory.mkdir();

                    String filename = String.format("%d.jpg", System.currentTimeMillis());
                    File outputfile = new  File(Directory, filename);

                    Toast.makeText(Orignal_image_screen.this, "Downloading....", Toast.LENGTH_SHORT).show();

                    try {
                        fileOutputStream = new FileOutputStream(outputfile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();


                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(outputfile));
                        sendBroadcast(intent);

                    }catch (FileNotFoundException e ){
                        e.printStackTrace();

                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }


        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_SEND);
                in.setType("text/plain");
                in.putExtra(Intent.EXTRA_TEXT, global_url );

                Intent chooserintent = Intent.createChooser(in , "choose an app");

                startActivity(chooserintent);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}