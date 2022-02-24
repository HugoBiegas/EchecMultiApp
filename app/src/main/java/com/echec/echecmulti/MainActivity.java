package com.echec.echecmulti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MainActivity extends AppCompatActivity {

    //initialisation des variables
    TextView user, email;
    String userId;
    Button mCheckRoom;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recherche par ID sur le layout main
        //user = findViewById(R.id.ProfileUser);
        //email = findViewById(R.id.ProfileEmail);
        //fAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();
        //recherche de l'id du joueur connecté
        //userId = fAuth.getCurrentUser().getUid();
        finish();
        startActivity(new Intent(getApplicationContext(),RoomActivity.class));

        //Vérification de la connexion
        //if(fAuth.getCurrentUser() == null){
        //    startActivity(new Intent(getApplicationContext(), Register.class));
        //}

        //Recherche dans la collection users de la BD à l'aide de de la variable userId
        //DocumentReference documentReference = fStore.collection("users").document(userId);
        //documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
        //    @Override
        //    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
        //        user.setText(value.getString("username"));
        //        email.setText(value.getString("email"));
        //    }
        //});

        //Go dans le layout Room (Jouer!)
        //mCheckRoom = findViewById(R.id.checkRoom);
        //mCheckRoom.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        startActivity(new Intent(getApplicationContext(), connexiontest.class));
        //    }
        //});
    }

    //Déconnexion
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}
