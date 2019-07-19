package com.torontomans.commute;


import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.torontomans.commute.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    FirebaseAuth firebaseAuth;
    TextView email, password;
    Button btn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.email_sign_in_button);
        signUpBtn = findViewById(R.id.sign_up_button);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String finalEmail = email.getText().toString();
                String finalPassword = password.getText().toString();
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please input a password", Toast.LENGTH_LONG).show();
                } else {
//                    firebaseAuth.signInWithEmailAndPassword(finalEmail, finalPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//
//                            myRef.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    User userR = dataSnapshot.getValue(User.class);
//                                    String name = userR.getNames();
//                                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
//                                    i.putExtra("name", finalEmail);
//                                    startActivity(i);
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    Log.w(TAG, "Failed to read value.", databaseError.toException());
//                                }
//                            });
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(LoginActivity.this, "IT DID NOT WORK :( ", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    firebaseAuth.signInWithEmailAndPassword(finalEmail, finalPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                                i.putExtra("name", finalEmail);
                                startActivity(i);
                            }
                            else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });

    }

//    public User getUser(){
//        return user
//    }



}


