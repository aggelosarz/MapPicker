package com.example.mappicker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
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

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    LocationManager managerl;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef,ref;
    private FirebaseUser user;
    Double x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        managerl = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        sharedPreferences = getSharedPreferences("Distance",MODE_PRIVATE);
         mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        searchView = findViewById(R.id.search);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                map.clear();
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                 if(location!= null || !location.equals(""))
                 {
                     Geocoder geocoder = new Geocoder(MapActivity.this);
                     try
                     {
                         addressList = geocoder.getFromLocationName(location,1);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }

                     Address address =addressList.get(0);
                     LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                     map.addMarker(new MarkerOptions().position(latLng).title(location));
                     map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                 }


                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.
                    requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 234);
            return;
        }
        else {

            managerl.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);}

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;

        LatLng Home = new LatLng(38.02714,23.72774);
        map.addMarker(new MarkerOptions().position(Home).title("Home)"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Home));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Home,10));

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