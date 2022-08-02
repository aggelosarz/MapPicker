package com.example.mappicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapPickerSignIn extends AppCompatActivity {

    SharedPreferences pref;
    TextView text;
    Button bu;
    EditText email;
    EditText password;
    ProgressBar bar;
    FirebaseAuth auth;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_picker_sign_in);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        text = findViewById(R.id.loginhere);
        bu = findViewById(R.id.button2);
        email = findViewById(R.id.editTextTextPersonName2);
        password = findViewById(R.id.editTextTextPersonName4);
        bar = findViewById(R.id.progressBar2);
        auth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String e = email.getText().toString();
        String p = password.getText().toString();

        if (e.isEmpty() || e == null) {
            email.setError("Email is required!");
            return;
        }


        if (p.length() < 6) {
            password.setError("Password must be at least 6 characters!");
            return;
        }
        bar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(MapPickerSignIn.this,"Login Successful!" ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Arxiki.class));

                } else {
                    Toast.makeText(getApplicationContext(),
                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    bar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



    public void gotoregister(View view)
    {
        startActivity(new Intent(getApplicationContext(), Register.class));
        finish();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
