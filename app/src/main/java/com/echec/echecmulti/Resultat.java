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
    Button rooms;
    boolean sup=false;
    String roomName;
    DatabaseReference messageRef;//pour faire référence as la BDD
    FirebaseDatabase database;//pour se connecter as la BDD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        String voud="";
        textView = findViewById(R.id.douv);
        profil = findViewById(R.id.profil);
        rooms = findViewById(R.id.Rooms);
        database = FirebaseDatabase.getInstance();//créer une instance

        Bundle extra = getIntent().getExtras();//récuper l'extrat envoiller par roomActivity
        if(extra != null) {
            voud = extra.getString("douv");
            sup = extra.getBoolean("sup");
            roomName = extra.getString("roomName");
        }
        textView.setText(voud);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);//créations de la page Game
                intent.putExtra("sup", sup);//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                intent.putExtra("roomName", roomName);//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                startActivity(intent);//on lance l'activiter
                finish();
            }
        });
        rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);//créations de la page Game
                intent.putExtra("sup", sup);//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                intent.putExtra("roomName", roomName);//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                startActivity(intent);//on lance l'activiter
                finish();
            }
        });

    }

}