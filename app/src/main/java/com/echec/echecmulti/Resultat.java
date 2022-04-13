package com.echec.echecmulti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.echec.echecmulti.Connection.Profile;
import com.echec.echecmulti.Room.RoomActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Resultat extends AppCompatActivity {
    TextView textView;
    Button profil;
    Button Rooms;
    String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        textView = findViewById(R.id.voud);
        profil =findViewById(R.id.Profilvoud);
        Rooms = findViewById(R.id.Roomsvoud);
        String douv ="";

        Bundle extra = getIntent().getExtras();//récuper l'extrat envoiller par roomActivity
        if(extra != null) {
            douv = extra.getString("douv");
        }
        textView.setText(douv);


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                finish();
            }
        });

        Rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
                finish();
            }
        });
    }
}