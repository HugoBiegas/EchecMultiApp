package com.echec.echecmulti;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    int selectionner=-1;
    int coup=0;
    Integer positiondépart=0;
    Integer positionarriver=0;
    String[] number = new String[64];
    String playerName = "";
    String CompartPlayer="";
    String roomName = "";
    String role = "";
    String message = "";
    Button buttonqui;
    FirebaseDatabase database;//pour se connecter as la BDD
    DatabaseReference messageRef;//pour faire référence as la BDD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //tout les méthode que nous allons utiliser
        initialistions();
        echecini();
        initialisationshost();
        extragerer();
        itemaction();
        quiterBTN();
    }
    private void initialisationshost(){
        messageRef = database.getReference("rooms/"+roomName);
        messageRef.addListenerForSingleValueEvent(unjoueuroudeux());
    }
    private ValueEventListener unjoueuroudeux(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().contains("player1")){
                    message();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    private void initialistions(){
        gridView = findViewById(R.id.grid_echec);
        gridView.setEnabled(false);

        database = FirebaseDatabase.getInstance();//créer une instance
        buttonqui = findViewById(R.id.quiter);//récupére le bouton quiter
        gridView = findViewById(R.id.grid_echec);
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
    }
    private void echecini(){
        initialisationsEchiquier();
        piécedéplacement();

    }

    private void déplacement(){
        String déplace = number[positiondépart];
        number[positiondépart]="";
        number[positionarriver]=déplace;
        piécedéplacement();

    }

    private void piécedéplacement(){
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, number) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        int color;
                        int couleurNb=0;
                        int[] positionColor = new int[]{1, 3, 5, 7, 8, 10, 12, 14, 17, 19, 21, 23, 24, 26, 28, 30, 33, 35, 37, 39, 40, 42, 44, 46, 49, 51, 53, 55, 56, 58, 60, 62};
                        for(int i=0;i<32;i++){
                            if(position == positionColor[i])
                                couleurNb =1;
                        }
                        if (couleurNb == 1)
                            color = Color.DKGRAY; // noir
                        else
                            color = Color.LTGRAY; // blanc
                        view.setBackgroundColor(color);
                        return view;
                    }
                };
        gridView.setAdapter(adapter);
    }

    private void initialisationsEchiquier(){
        for(int i = 0 ; i<number.length;i++){//pour intégrer tout les case
            if(i==0||i==7||i==56||i==63)//tour
                number[i]="T";
            else if (i==1||i==6||i==57||i==62)//cavalier
                number[i]="C";
            else if (i==2||i==5||i==58||i==61)//foue
                number[i]="F";
            else if(i==3||i==59)//dame
                number[i]="D";
            else if(i==4||i==60)//roie
                number[i]="R";
            else if(8<=i && i<=15 || 48<=i && i<=55)//petite pion
                number[i]="P";
            else
                number[i]="";
        }
    }

    private void itemaction(){
        gridView.setOnItemClickListener(grilleaction());
    }
    private AdapterView.OnItemClickListener grilleaction(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (coup == 1 && selectionner == i){
                    Toast.makeText(GameActivity.this, "veuiller selectionner une case valide", Toast.LENGTH_SHORT).show();
                    coup--;
                    selectionner=-1;
                }
                else if(coup == 0){
                        coup++;
                        selectionner = i;
                    }else if(selectionner != i && coup == 1){
                            positiondépart=selectionner;
                            positionarriver=i;
                            déplacement();
                            gridView.setEnabled(false);
                            messageRef.setValue(role+":"+selectionner+":"+i);//change l'informatiosn dans la BDD
                            coup=0;
                            selectionner = -1;
                            Toast.makeText(GameActivity.this, "a votre adversaire de jouer", Toast.LENGTH_SHORT).show();
                        }
            }
        };
    }

    private void extragerer(){
        Bundle extra = getIntent().getExtras();//récuper l'extrat envoiller par roomActivity
        if(extra != null){
            roomName = extra.getString("roomName");;//récupére la valeur envoiller
            CompartPlayer = extra.getString("playerhost");
            if(CompartPlayer.equals(playerName))//teste pour savoir si ces le joueur1 ou 2
                role = "host";
            else
                role = "guest";
        }

    }

    private void quiterBTN(){

        buttonqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bouton pour quiter l'applications
            }
        });
    }



    private void message(){
        messageRef = database.getReference("rooms/"+roomName+"/message");//crée le message de la BDD
        messageRef.setValue(role+":1:1");//change l'informatiosn dans la BDD
        addRoomEventListener();
    }

    private void addRoomEventListener(){
        messageRef.addValueEventListener(addRoomEvent());
    }

    private ValueEventListener addRoomEvent(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //message recu
                if(role.equals("host")){//teste si le joueur est l'host ou pas
                    if(snapshot.getValue().toString().contains("guest")){//regarde si l'endroit ou les donnée a changer contient guest
                        gridView.setEnabled(true);
                        //Toast.makeText(GameActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                        séparateur(snapshot.getValue().toString());//pour récupérer les changement de piéce
                        //est affiche un message
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("guest:"+positiondépart+":"+positionarriver,"a toi de jouer"), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(snapshot.getValue().toString().contains("host")){//regarde si l'endroit ou les donnée a changer contient host:
                        gridView.setEnabled(true);
                        séparateur(snapshot.getValue().toString());
                        //est affiche un message
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("host:"+positiondépart+":"+positionarriver,"a toi de jouer"), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //erreur retenter
                messageRef.setValue(message);
            }
        };
    }
    private void séparateur(String snapshot){
        String séparaeur = snapshot.substring(snapshot.indexOf(":")+1,snapshot.length());
        positiondépart = Integer.parseInt(séparaeur.substring(0,séparaeur.indexOf(":")));
        positionarriver = Integer.parseInt(séparaeur.substring(séparaeur.indexOf(":")+1,séparaeur.length()));
        déplacement();
    }
}