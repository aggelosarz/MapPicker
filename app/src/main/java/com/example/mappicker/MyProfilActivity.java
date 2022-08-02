package com.example.mappicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyProfilActivity extends AppCompatActivity {

    CheckBox architecture, texni, fusi, koultoura,mouseio,mnimeio,athlitismos,theatr,camp,gardens;
    ArrayList<String> xaraktiristika = new ArrayList<String>();
    ArrayList<String> xaraktiristika2 = new ArrayList<String>();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef,ref;
    Spinner sp;
    public String m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11;
    String full1,full2,full3,full4,full5,full6;
    SeekBar seekBar;
    TextView distance,km;
    Switch aSwitch;
  SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profil);

        m1="";
        m2="";
        m3="";
        m4="";
        m5="";
        m6="";
        m7="";
        m8="";
        m9="";
        m10="";
        m11="";

        km = findViewById(R.id.textView5);
        aSwitch = findViewById(R.id.switch1);
        distance=findViewById(R.id.dist);
        seekBar = findViewById(R.id.seekBar);
        sp=findViewById(R.id.spinner);
        architecture = findViewById(R.id.arxitektoniki);
        texni = findViewById(R.id.art);
        fusi = findViewById(R.id.nature);
        koultoura = findViewById(R.id.culture);
        mouseio=findViewById(R.id.musuem);
        mnimeio=findViewById(R.id.monument);
        theatr=findViewById(R.id.theatro);
        athlitismos=findViewById(R.id.sports);
        camp=findViewById(R.id.camping);
        gardens=findViewById(R.id.gardens);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();



        seekBar.setEnabled(false);

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()) {seekBar.setEnabled(true); distance.setVisibility(View.VISIBLE); km.setVisibility(View.VISIBLE);}
                else seekBar.setEnabled(false);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance.setText("" + progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        List<String> ages = new ArrayList<>();
        ages.add(0,"Choose Age Category");
        ages.add("Elderly");
        ages.add("Adults");
        ages.add("Teens");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyProfilActivity.this, android.R.layout.simple_list_item_1,ages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        ref = database.getReference("Users").child(user.getUid()).child("Mychoices");
        myRef = database.getReference("Users").child(user.getUid());


        architecture.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (architecture.isChecked() == true) {
                    xaraktiristika.add(architecture.getText().toString());
                  m1="Architecture";
                } else {
                    xaraktiristika.remove(architecture.getText().toString());
                   m1="";
                }

            }
        });


        texni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (texni.isChecked() == true) {
                    xaraktiristika.add(texni.getText().toString());
                   m2="Art";
                } else {
                    xaraktiristika.remove(texni.getText().toString());
                   m2="";
                }

            }
        });

        fusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fusi.isChecked() == true) {
                    xaraktiristika.add(fusi.getText().toString());
                   m3="Nature";
                } else {
                    xaraktiristika.remove(fusi.getText().toString());
                    m3="";
                }

            }
        });

        koultoura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (koultoura.isChecked() == true) {
                    xaraktiristika.add(koultoura.getText().toString());
                    m4="Culture";
                } else {
                    xaraktiristika.remove(koultoura.getText().toString());
                  m4="";
                }

            }
        });

        mnimeio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mnimeio.isChecked() == true) {
                    xaraktiristika.add(mnimeio.getText().toString());
                 m5="Monuments";
                } else {
                    xaraktiristika.remove(mnimeio.getText().toString());
                    m5="";
                }

            }
        });

        mouseio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mouseio.isChecked() == true) {
                    xaraktiristika.add(mouseio.getText().toString());
                 m6="Museum";
                } else {
                    xaraktiristika.remove(mouseio.getText().toString());
                    m6="";
                }

            }
        });

        athlitismos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (athlitismos.isChecked() == true) {
                    xaraktiristika.add(athlitismos.getText().toString());
                    m7="Sports";
                } else {
                    xaraktiristika.remove(athlitismos.getText().toString());
                    m7="";
                }

            }
        });

        theatr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (theatr.isChecked() == true) {
                    xaraktiristika.add(theatr.getText().toString());
                    m8="Theatre";
                } else {
                    xaraktiristika.remove(theatr.getText().toString());
                   m8="";
                }

            }
        });


        camp.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (camp.isChecked() == true) {
                    xaraktiristika.add(camp.getText().toString());
                    m10="Camping";
                } else {
                    xaraktiristika.remove(camp.getText().toString());
                    m10="";
                }

            }
        });

        gardens.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (gardens.isChecked() == true) {
                    xaraktiristika.add(gardens.getText().toString());
                    m11="Garden";
                } else {
                    xaraktiristika.remove(gardens.getText().toString());
                    m11="";
                }

            }
        });

    }

    public void save(View view) {

        if(architecture.isChecked()==true) {m1="Architecture";}
        if(texni.isChecked()==true) {m2="Art";}
        if(koultoura.isChecked()==true) {m4="Culture";}
        if(fusi.isChecked()==true) {m3="Nature";}
        if(mnimeio.isChecked()==true){ m5="Monuments";}
        if(mouseio.isChecked()==true) {m6="Museum";}
        if(athlitismos.isChecked()==true) {m7="Sports";}
        if(theatr.isChecked()==true) {m8="Theatre";}
        if(camp.isChecked()==true) {m10="Camping";}
        if(gardens.isChecked()==true) {m11="Garden";}

        full1 = m1+" "+m5;
        full2 = m2+" "+m4+" "+ m6;
        full3=m3+" "+ m10;
        full6=m3+" "+ m11;
        full4 = m8 ;
        full5 = m7;

        if(full1.contains("Architecture") || full1.contains("Monuments")) xaraktiristika2.add(full1);
        if(full2.contains("Art") || full2.contains("Culture") || full2.contains("Museum")) xaraktiristika2.add(full2);
        if(full4.contains("Theatre") || full4.contains("Entertainment")) xaraktiristika2.add(full4);
        if(full5.contains("Sports")) xaraktiristika2.add(full5);
        if(full3.contains("Nature"))
        {
            if(full3.contains("Camping") && full6.contains("Garden")) {xaraktiristika2.add(full3); xaraktiristika2.add(full6); }
            else if(full3.contains("Camping") && full6.contains("Garden")==false){xaraktiristika2.add(full3);}
            else if(full3.contains("Camping")==false && full6.contains("Garden")){xaraktiristika2.add(full6);}
            else xaraktiristika2.add("Nature");
        }
        if(full6.contains("Garden") && full6.contains("Nature")==false) xaraktiristika2.add(full6);
        if(full3.contains("Camping")&& full3.contains("Nature")==false) xaraktiristika2.add(full3);

        if(aSwitch.isChecked())  ref.child("maxdistance" ).setValue(Long.parseLong(distance.getText().toString()));
        else ref.child("maxdistance" ).setValue(0);


        int y = 1;
        for (String items : xaraktiristika2) {
            ref.child("full" + String.valueOf(y)).setValue(items.toString());

            y++;
        }

        while (y < 6) {
            ref.child("full" + String.valueOf(y)).setValue("null");
            y++;
        }

        if(sp.getSelectedItem().toString() == "Elderly"){
            ref.child("age").setValue("Elderly");
        }
        else if(sp.getSelectedItem().toString() == "Adults"){
            ref.child("age").setValue("Adults");
        }
        else if(sp.getSelectedItem().toString() == "Teens"){
            ref.child("age").setValue("Teens");}
            else  ref.child("age").setValue("null");


            int i = 1;
            for (String items : xaraktiristika) {
                myRef.child("xaraktiristiko" + String.valueOf(i)).setValue(items.toString());

                i++;
            }

            while (i < 9) {
                myRef.child("xaraktiristiko" + String.valueOf(i)).setValue("null");
                i++;
            }

            startActivity(new Intent(MyProfilActivity.this, Arxiki.class));




    }


}