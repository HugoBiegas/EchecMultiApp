package com.echec.echecmulti.Connection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.echec.echecmulti.GameActivity;
import com.echec.echecmulti.R;
import com.echec.echecmulti.Room.RoomActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class Profile extends AppCompatActivity {

    //initialisation des variables
    TextView user, email, victories, loses, winrate;
    Button mCheckRoom, buttonLogout;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId;
    String playerName="";   //le nom du joueur
    FirebaseDatabase database; // connections as la BDD
    DatabaseReference playerRef;// référence de la BD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recherche par ID sur le layout main
        user = findViewById(R.id.ProfileUser);
        email = findViewById(R.id.ProfileEmail);
        victories = findViewById(R.id.victories);
        loses = findViewById(R.id.loses);
        winrate = findViewById(R.id.winrate);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //recherche de l'id du joueur connecté
        userId = fAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();//créations de l'instance

        //Recherche dans la collection users de la BD à l'aide de de la variable userId
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    Double nbvic = value.getLong("victories").doubleValue();
                    Double nblos = value.getLong("loses").doubleValue();
                    Double pourcentage = 0.0;

                    if (nbvic != 0.0) {
                        pourcentage = (nbvic / (nbvic + nblos)) * 100;
                    }

                    user.setText(value.getString("username"));
                    email.setText(value.getString("email"));
                    mCheckRoom.setEnabled(true);
                    victories.setText("Victoires : " + String.format("%.0f", nbvic));
                    loses.setText("Défaites : " + String.format("%.0f", nblos));
                    winrate.setText("Winrate : " + String.format("%.0f", pourcentage) + "%");
                }
            }
        });

        //Go dans le layout Room (Jouer!)
        mCheckRoom = findViewById(R.id.checkRoom);
        mCheckRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerName = user.getText().toString();//récupérations du nom de l'utilisateur
                if(!playerName.equals("")){//on vérifie bien que l'utilisateur as sési un truque
                    mCheckRoom.setText("Connexion...");//changement du bouton connections en connections en cours
                    mCheckRoom.setEnabled(false);//pour que le bouton ne soit pas clicable une dexiéme foi
                    playerRef = database.getReference("players/" + playerName);//créations de players avec une sous catégori avec le nom du joueur
                    addEventListener();//appelle de fonctions
                    playerRef.setValue("");//on mais la référence du joueur a ""(on pourat mettre d'autre info)
                }
            }
        });

        buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogout.setEnabled(false);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);//créations de la page Game
                intent.putExtra("deco",1);//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                startActivity(intent);//on lance l'activiter
                fAuth.signOut();
                finish();
            }
        });
    }

    public void addEventListener(){
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //succer - continue sur le prochain écran et sauvegarde le nom du joueur
                SharedPreferences preferences = getSharedPreferences("PREFS",0);//permet de modifier un ensemble particulier
                SharedPreferences.Editor editor = preferences.edit();//permet de changer la valeur contenut dans preferences
                editor.putString("playerName", playerName);//remplace le nom du joueur par le vraix nom du joueur
                editor.apply();//applique le changement de nom de joueur
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));//on lance l'activiter RooomActivity
                finish();//on arréte la tache actuelle
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //erreur
                mCheckRoom.setText("connections");//change le texte du bouton
                mCheckRoom.setEnabled(true);//rend visible le bouton
                Toast.makeText(Profile.this,"erreur!",Toast.LENGTH_SHORT).show();//affiche un message d'erreur
            }
        });
    }
}