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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    String[] ColorAction = new String[32];
    ArrayList<Integer> PositionValble =new ArrayList<>();
    PetitePion pion = new PetitePion();
    int selectionner=-1;
    int coup=0;
    int couleur=0;
    int debugPiece=0;
    TextView textView;
    Integer positiondépart=0;
    Integer positionarriver=0;
    String[] BordPiece = new String[64];
    String[] colorP = new String[64];
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
        buttonqui = findViewById(R.id.quiter);//récupére le bouton quiter
        gridView = findViewById(R.id.grid_echec);
        textView = findViewById(R.id.NomJoueur);

        gridView.setEnabled(false);
        database = FirebaseDatabase.getInstance();//créer une instance

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
        textView.setText("joueur : " + playerName);

    }
    private void echecini(){
        initialisationsEchiquier();
        piécedéplacement();
    }

    private void déplacement(){
        String déplace = BordPiece[positiondépart];
        BordPiece[positiondépart]="";
        BordPiece[positionarriver]=déplace;
        déplace = colorP[positiondépart];
        colorP[positiondépart] ="";
        colorP[positionarriver]= déplace;
        piécedéplacement();

    }

    private void piécedéplacement(){
        //permet de mettre la couleur et de remettre le tableaux
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BordPiece) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        int color;
                        couleur=0;
                        color = coloration(position);
                        view.setBackgroundColor(color);
                        //couleur
                        return view;
                    }
                };
        gridView.setAdapter(adapter);
    }

    private void initialisationsEchiquier(){
        for(int i = 0 ; i<BordPiece.length;i++){//pour intégrer tout les case
            if(i==0||i==7){
                BordPiece[i]="T"; //tour
                colorP[i] = "B";
            }else if(i==56||i==63){
                BordPiece[i]="T"; //tour
                colorP[i] = "N";
            }
            else if (i==1||i==6){
                BordPiece[i]="C";//cavalier
                colorP[i] = "B";
            }else if (i==57||i==62){
                BordPiece[i]="C";//cavalier
                colorP[i] = "N";
            }
            else if (i==2||i==5){
                BordPiece[i]="F";//foue
                colorP[i] = "B";
            }else if (i==58||i==61){
                BordPiece[i]="F";//foue
                colorP[i] = "N";
            }
            else if(i==3){
                BordPiece[i]="D"; //dame
                colorP[i] = "B";
            }else if (i==59){
                BordPiece[i]="D"; //dame
                colorP[i] = "N";
            }
            else if(i==4){
                BordPiece[i]="R";//roie
                colorP[i] = "B";
            }else if (i==60){
                BordPiece[i]="R";//roie
                colorP[i] = "N";
            }
            else if(8<=i && i<=15){
                BordPiece[i]="P";//petite pion
                colorP[i] = "B";
            }else if (48<=i && i<=55){
                BordPiece[i]="P";//petite pion
                colorP[i] = "N";
            }
            else{
                BordPiece[i]="";
                colorP[i] = "";
            }

        }
    }

    private void itemaction(){
        gridView.setOnItemClickListener(grilleaction());
    }
    private AdapterView.OnItemClickListener grilleaction(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //on regarde si ces l'host et que la couleur est Blanc alors on mais le coup a 1 et selecltionner a i pui si coup == 1 alors on fait des test pour bouger
                int posible=0;
                if(role.equals("host") && colorP[i].equals("B") || coup==1){
                    if(coup==1){//si la piéce et vide ou que ces le deusiéme coup
                        if (selectionner == i){
                            Toast.makeText(GameActivity.this, "veuiller selectionner une case valide", Toast.LENGTH_SHORT).show();
                            coup--;
                            selectionner=-1;
                            piécedéplacement();
                        }
                        else{
                            trouverPositionCorecte(i);
                        }
                    }else
                        trouverLesDéplacement(i);
                }else if(role.equals("guest") && colorP[i].equals("N") || coup==1){
                    if(coup==1){//si la piéce et vide ou que ces le deusiéme coup
                        if (selectionner == i){
                            Toast.makeText(GameActivity.this, "veuiller selectionner une case valide", Toast.LENGTH_SHORT).show();
                            coup--;
                            selectionner=-1;
                            piécedéplacement();
                        }
                        else{
                            trouverPositionCorecte(i);
                            //trouverPositionCorecte(i);
                        }

                    }else
                        trouverLesDéplacement(i);
                }
            }
        };
    }
    private void trouverPositionCorecte(int i){
        int posible=0;
        if (BordPiece[selectionner].equals("P")) {
            for (int j = 0; j < PositionValble.size(); j++) {
                if (i == PositionValble.get(j)) {
                    posible = 1;
                }
            }
            PositionValble.clear();
            if (posible == 1) {
                positiondépart = selectionner;
                positionarriver = i;
                déplacement();
                gridView.setEnabled(false);
                messageRef.setValue(role + ":" + selectionner + ":" + i);//change l'informatiosn dans la BDD
                coup = 0;
                selectionner = -1;
                Toast.makeText(GameActivity.this, "a votre adversaire de jouer", Toast.LENGTH_SHORT).show();
            } else {
                positiondépart = -1;
                coup--;
                piécedéplacement();
                Toast.makeText(GameActivity.this, "imposible", Toast.LENGTH_SHORT).show();
            }
        }else if (BordPiece[i].equals("T")){
        }else if(BordPiece[i].equals("C")){

        }else if (BordPiece[i].equals("F")){

        }else if (BordPiece[i].equals("D")){

        }else if (BordPiece[i].equals("R")){

        }else{
            positiondépart = selectionner;
            positionarriver = i;
            déplacement();
            gridView.setEnabled(false);
            messageRef.setValue(role + ":" + selectionner + ":" + i);//change l'informatiosn dans la BDD
            coup = 0;
            selectionner = -1;
            Toast.makeText(GameActivity.this, "a votre adversaire de jouer", Toast.LENGTH_SHORT).show();

        }
    }

    private void trouverLesDéplacement(int i){
        if (BordPiece[i].equals("P")){
            if (role.equals("host"))
                ColorAction = pion.déplacementHost(BordPiece,i,colorP);
            else
                ColorAction = pion.déplacementGuest(BordPiece,i,colorP);

            piéceColorDeplacement();
            debugPiece=0;
        }
        coup++;
        selectionner = i;
    }

    private void piéceColorDeplacement() {
        //permet de mettre la couleur et de remettre le tableaux
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BordPiece) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        int color,couleurNb=0;
                        debugPiece = 0;
                        couleur=0;
                        color = coloration(position);
                        String[] Lettre = new String[4];
                        Integer[] coordonner = new Integer[4];
                        for (int i=0;i<4;i++){
                            coordonner[i] = Integer.parseInt(ColorAction[i].substring(ColorAction[i].indexOf(":")+1,ColorAction[i].length()));
                            Lettre[i] = ColorAction[i].substring(0,ColorAction[i].indexOf(":"));
                            if (coordonner[i] == position){
                                 if (Lettre[i].equals("A")){
                                    couleurNb = 3;
                                    PositionValble.add(coordonner[i]);
                                }
                                else if (Lettre[i].equals("D")){
                                    couleurNb = 2;
                                    PositionValble.add(coordonner[i]);
                                }
                            }
                        }
                        if(couleurNb == 2 )
                            color = Color.GREEN;
                        else if(couleurNb == 3)
                            color = Color.RED;
                        view.setBackgroundColor(color);
                        return view;
                    }
                };
        gridView.setAdapter(adapter);
    }

    private int coloration(int position){
        int color;

        int[] positionColor = new int[]{1, 3, 5, 7, 8, 10, 12, 14, 17, 19, 21, 23, 24, 26, 28, 30, 33, 35, 37, 39, 40, 42, 44, 46, 49, 51, 53, 55, 56, 58, 60, 62};
        for(int i=0;i<32;i++){
            if(position == positionColor[i])
                couleur =1;
        }
        if (couleur == 1)
            color = Color.DKGRAY; // noir
        else
            color = Color.LTGRAY; // blanc
        return color;
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