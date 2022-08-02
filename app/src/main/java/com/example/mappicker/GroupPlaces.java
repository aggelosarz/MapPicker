package com.example.mappicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class GroupPlaces extends AppCompatActivity {

    CardView c1,c2,c3,c4,c5;
    TextView t1,t2,t3,t4,t5;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_places);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();


        t1=findViewById(R.id.text1);
        t2=findViewById(R.id.text2);
        t3=findViewById(R.id.text3);
        t4=findViewById(R.id.text4);
        t5=findViewById(R.id.text5);

        c1=findViewById(R.id.card1);
        c2=findViewById(R.id.card2);
        c3=findViewById(R.id.card3);
        c4=findViewById(R.id.card4);
        c5=findViewById(R.id.card5);


        ref = database.getReference("Users").child(user.getUid()).child("Mychoices");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String full1 = snapshot.child("full1").getValue().toString();

                if(full1.equals("null"))
                {
                    String distance = snapshot.child("maxdistance").getValue().toString();
                    String age = snapshot.child("age").getValue().toString();
                    if(!age.equals("null")){
                        c1.setVisibility(View.VISIBLE);
                    t1.setText("Places for"+ " "+snapshot.child("age").getValue().toString());
                }

                    if(age.equals("null") && !distance.equals("null"))
                    {
                        c1.setVisibility(View.VISIBLE);
                        t1.setText("Places with distance less than" + snapshot.child("maxdistance").getValue().toString()+"Km");
                    }
                }
                else{

                for(int i=1;i<6;i++)
                {

                    String a = snapshot.child("full" + i).getValue().toString();
                    if (!a.equals("null")) {
                        switch (i) {
                            case 1:
                               c1.setVisibility(View.VISIBLE);
                                t1.setText(a);
                                break;
                            case 2:
                                c2.setVisibility(View.VISIBLE);
                                t2.setText(a);
                                break;
                            case 3:
                                c3.setVisibility(View.VISIBLE);
                                t3.setText(a);
                                break;
                            case 4:
                                c4.setVisibility(View.VISIBLE);
                                t4.setText(a);
                                break;
                            case 5:
                                c5.setVisibility(View.VISIBLE);
                                t5.setText(a);
                                break;

                        }}
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void card1(View view){
        Intent intent = new Intent(GroupPlaces.this, RecyclerViewActivity.class);
        intent.putExtra("category", t1.getText().toString());
        startActivity(intent);

    }
    public void card2(View view){
        Intent intent = new Intent(GroupPlaces.this, RecyclerViewActivity.class);
        intent.putExtra("category", t2.getText().toString());
        startActivity(intent);

    }
    public void card3(View view){
        Intent intent = new Intent(GroupPlaces.this, RecyclerViewActivity.class);
        intent.putExtra("category", t3.getText().toString());
        startActivity(intent);

    }
    public void card4(View view){
        Intent intent = new Intent(GroupPlaces.this,RecyclerViewActivity.class);
        intent.putExtra("category", t4.getText().toString());
        startActivity(intent);

    }
    public void card5(View view){
        Intent intent = new Intent(GroupPlaces.this, RecyclerViewActivity.class);
        intent.putExtra("category", t5.getText().toString());
        startActivity(intent);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(),Arxiki.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}