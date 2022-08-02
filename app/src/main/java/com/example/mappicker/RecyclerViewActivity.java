package com.example.mappicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.Data;

public class RecyclerViewActivity extends AppCompatActivity implements Adapter.ClickListener {

    RecyclerView recyclerView;
    Adapter adapter;
    List<Data> placeList;
    DatabaseReference reference;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    Query query;
    private DatabaseReference ref;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
   Integer dist;
   float distance;
    Double x,y;
   Double xlong,ylong;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

       sharedPreferences=getApplicationContext().getSharedPreferences("Distance",MODE_PRIVATE);
       dist =(int)(sharedPreferences.getLong("distance",0));

        recyclerView = findViewById(R.id.placesrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeList = new ArrayList<>();
        adapter = new Adapter(this, placeList,this);
        recyclerView.setAdapter(adapter);

            ref = db.getReference("Users").child(user.getUid()).child("Mychoices");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Intent intent = getIntent();
                    String cat=intent.getStringExtra("category");
                    if(cat.contains("Architecture") || cat.contains("Monuments")) query = db.getReference("Places").orderByChild("xaraktiristiko1").equalTo("Architecture");
                    else if(cat.contains("Art") || cat.contains("Culture") || cat.contains("Museum"))  query = db.getReference("Places").orderByChild("xaraktiristiko1").equalTo("Art");
                    else if(cat.contains("Nature") && cat.contains("Garden")) query = db.getReference("Places").orderByChild("xaraktiristiko2").equalTo("Garden");
                    else if(cat.contains("Nature") && cat.contains("Camping")) query = db.getReference("Places").orderByChild("xaraktiristiko2").equalTo("Camping");
                    else if(cat.contains("Nature") && cat.contains("Camping")==false && cat.contains("Garden")==false ) query = db.getReference("Places").orderByChild("xaraktiristiko1").equalTo("Nature");
                    else if(cat.contains("Nature")==false && cat.contains("Camping")) query = db.getReference("Places").orderByChild("xaraktiristiko2").equalTo("Camping");
                    else if(cat.contains("Nature")==false && cat.contains("Garden")) query = db.getReference("Places").orderByChild("xaraktiristiko2").equalTo("Garden");
                    else if(cat.contains("Entertainment") || cat.contains("Theatre")) query = db.getReference("Places").orderByChild("xaraktiristiko1").equalTo("Theatre");
                    else if(cat.contains("Sports") ) query = db.getReference("Places").orderByChild("xaraktiristiko1").equalTo("Sports");
                    else if(cat.contains("Adults")) query = db.getReference("Places").orderByChild("age").equalTo("Adults");
                    else if(cat.contains("Elderly")) query = db.getReference("Places").orderByChild("age2").equalTo("Elderly");
                    else if(cat.contains("Teens")) query = db.getReference("Places").orderByChild("age2").equalTo("Teens");
                    else if(cat.contains("Km")) query = db.getReference("Places");
                    else  query = db.getReference("Places").orderByChild("xaraktiristiko1").equalTo("Sport");

                    query.addListenerForSingleValueEvent(valueEventListener);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref = db.getReference("Users").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                x = (double)snapshot.child("x").getValue();
                y = (double)snapshot.child("y").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            placeList.clear();
            if(snapshot.exists())
            {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    Data data = snap.getValue(Data.class);
                    xlong =Double.parseDouble(data.getX());
                    ylong =Double.parseDouble(data.getY());

                    if(dist==0)  placeList.add(data);

                    else
                        {
                            Location currentLocation = new Location("");
                            currentLocation.setLatitude(x);
                            currentLocation.setLongitude(y);
                            Location destination = new Location("");
                            destination.setLatitude(xlong);
                            destination.setLongitude(ylong);
                             distance =  currentLocation.distanceTo(destination);
                            Double kilometres = Double.valueOf(distance) * 0.001;
                            if(kilometres<=dist)  placeList.add(data);

                        }
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onPlaceClick(int position) {
        Data data = new Data();
        Intent intent = new Intent(RecyclerViewActivity.this, DetailsActivity.class);
        intent.putExtra("name",placeList.get(position).getName());
        intent.putExtra("y", placeList.get(position).getY());
        intent.putExtra("image", placeList.get(position).getImage());
        intent.putExtra("x",placeList.get(position).getX());
        intent.putExtra("info", placeList.get(position).getInfo());

        startActivity(intent);


    }

    public void home(View view){
        startActivity(new Intent(RecyclerViewActivity.this,Arxiki.class));
    }

}