package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {



    TextView welcome;
    String userName = "";
    String splitEmail[];
    Button btnLogOut;
    FirebaseAuth mAuth;
    String header;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DatabaseReference reference;
        //reference = FirebaseDatabase.getInstance().getReference().getRoot();

        btnLogOut = findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();
        welcome = (TextView) findViewById(R.id.textView);

        btnLogOut.setOnClickListener(view->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            userEmail = user.getEmail();
            splitEmail = user.getEmail().split("@",0); // welcome mesajını maile göre dinamik yazdırma kısmı
            userName = splitEmail[0];
            welcome.setText("Welcome " + userName + " :)");
        }
        else{
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }


    public void cardActivity(@NonNull View v){

        if(v.getId() == R.id.sport){
            Intent myIntent = new Intent(MainActivity.this,CardDetails.class);
            myIntent.putExtra("name","sport");
            myIntent.putExtra("email",userEmail);
            startActivity(myIntent);
        }
        else if(v.getId() == R.id.study){
            Intent myIntent = new Intent(MainActivity.this,CardDetails.class);
            myIntent.putExtra("name","study");
            myIntent.putExtra("email",userEmail);
            startActivity(myIntent);
        }

        else if(v.getId() == R.id.art){
            Intent myIntent = new Intent(MainActivity.this,CardDetails.class);
            myIntent.putExtra("name","art");
            myIntent.putExtra("email",userEmail);
            startActivity(myIntent);
        }
        else if(v.getId() == R.id.food){
            Intent myIntent = new Intent(MainActivity.this,CardDetails.class);
            myIntent.putExtra("name","food");
            myIntent.putExtra("email",userEmail);
            startActivity(myIntent);
        }
        else if(v.getId() == R.id.game){
            Intent myIntent = new Intent(MainActivity.this,CardDetails.class);
            myIntent.putExtra("name","game");
            myIntent.putExtra("email",userEmail);
            startActivity(myIntent);
        }
        else if(v.getId() == R.id.chat){
            Intent myIntent = new Intent(MainActivity.this,CardDetails.class);
            myIntent.putExtra("name","chat");
            myIntent.putExtra("email",userEmail);
            startActivity(myIntent);
        }
    }
}

