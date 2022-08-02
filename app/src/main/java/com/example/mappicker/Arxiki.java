package com.example.mappicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Arxiki extends AppCompatActivity implements LocationListener {

    LocationManager managerl;
    Double x,y;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arxiki);
        managerl = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        sharedPreferences = getSharedPreferences("Distance",MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.
                    requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 234);
            return;
        }
        else {

            managerl.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);}

        ref = database.getReference("Users").child(user.getUid()).child("Mychoices");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long a = (long)snapshot.child("maxdistance").getValue();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("distance",a);
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void myprofil(View view)
    {
        startActivity(new Intent(Arxiki.this,MyProfilActivity.class));
    }

    public void reccomended(View view)
    {
        startActivity(new Intent(Arxiki.this,GroupPlaces.class));
    }

    public void info(View view)
    {
        String mess = "This application will help you discover Athens according to your interests. Put your interests in my profile format and then go to the reccomended places format to see which places the app suggests you visit. The map's services are also provided.";
        String t = "Online Help";
        alert(mess,t);
    }

    public void map(View view)
    {
        startActivity(new Intent(Arxiki.this,MapActivity.class));
    }

    public void logout(View view)
    {
        startActivity(new Intent(Arxiki.this,MainActivity.class));
    }

    private void alert(String message,String title)
    {
        AlertDialog dlg = new AlertDialog.Builder(Arxiki.this).setTitle(title).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        dlg.show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        x = location.getLatitude();
        y = location.getLongitude();
        ref = database.getReference("Users").child(user.getUid());
        ref.child("x").setValue(x);
        ref.child("y").setValue(y);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}