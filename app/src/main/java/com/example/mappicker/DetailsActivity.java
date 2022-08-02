package com.example.mappicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback  {

    TextView nm,inf;
    String a,b,i,x,y;
    ImageView img;
    GoogleMap map;
    SupportMapFragment mapFragment;
    public Double aek,osfp;
    boolean press;
    Button button;
    Double xlocation,ylocation;
    private DatabaseReference ref;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        press = false;
        nm = findViewById(R.id.title_dt);
        inf = findViewById(R.id.info);
        img = findViewById(R.id.image);
        button = findViewById(R.id.button2);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getView().setVisibility(View.INVISIBLE);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        a=intent.getStringExtra("name");
        b = intent.getStringExtra("info");
        i=intent.getStringExtra("image");
        x=intent.getStringExtra("x");
        y=intent.getStringExtra("y");


      aek = Double.valueOf(x);
      osfp= Double.valueOf(y);
        nm.setText(a);
        inf.setText(b);

        Picasso.get().load(i).networkPolicy(NetworkPolicy.OFFLINE).into(img, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

                Picasso.get().load(i).into(img);

            }
        });

        ref = db.getReference("Users").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                xlocation = (double)snapshot.child("x").getValue();
                ylocation = (double)snapshot.child("y").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void viewonmap(View view)
    {
        if(press==false){
      mapFragment.getView().setVisibility(View.VISIBLE);
        press=true;
         button.setText("HIDE") ;
        }
        else
            {
                mapFragment.getView().setVisibility(View.INVISIBLE);
                press=false;
                button.setText("VIEW ON MAP");

            }
    }

    public void trackLocation(View view)
    {
        String address = null;
        Geocoder geocoder = new Geocoder(DetailsActivity.this,Locale.getDefault());
        String errorMessage;
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                   xlocation,
                   ylocation,
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "IOException>>" + ioException.getMessage();
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "IllegalArgumentException>>" + illegalArgumentException.getMessage();
        }
        if (addresses != null && !addresses.isEmpty()) {
            address = addresses.get(0).getAddressLine(0);
        }
        
        Uri uri =Uri.parse("https://www.google.co.in/maps/dir/" +address+"/"+ a);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;

        LatLng Home = new LatLng(aek,osfp);
        map.addMarker(new MarkerOptions().position(Home).title(nm.toString()));
        map.moveCamera(CameraUpdateFactory.newLatLng(Home));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Home,15));
        map=googleMap;
    }
}