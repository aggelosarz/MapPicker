package com.example.mappicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText email,password;
    Button b;
    TextView login;
    ProgressBar bar;
    FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        b =findViewById(R.id.button);
        login = findViewById(R.id.loginhere);
        bar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

    }

    public void register(View view) {
        String e = email.getText().toString();
        String p = password.getText().toString();

        if (e.isEmpty() || e == null) {
            Toast.makeText(Register.this, "Name is required", Toast.LENGTH_SHORT).show();
        } else if (e.isEmpty() || e == null) {
            email.setError("Email is required!");
            return;
        } else if (p.length() < 6) {
            password.setError("Password must be at least 6 characters!");
            return;

        } else {
            bar.setVisibility(View.VISIBLE);


            auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "User Created!", Toast.LENGTH_SHORT).show();

                        myRef = database.getReference("Users").child(user.getUid());
                      //  myRef.child("maxdistance").setValue("0");
                        startActivity(new Intent(Register.this, MapPickerSignIn.class));
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.INVISIBLE);
                    }

                }
            });

        }
    }

    public void login(View view)
    {
        startActivity(new Intent(getApplicationContext(), MapPickerSignIn.class));
        finish();
    }


}
