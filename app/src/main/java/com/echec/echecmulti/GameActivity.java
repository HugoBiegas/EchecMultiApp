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
    ArrayList<String> colorActionPion = new ArrayList<>();
    ArrayList<Integer> PositionValble =new ArrayList<>();
    PetitePion pion = new PetitePion();//créer les pion
    Tour tour = new Tour();//créer les tour
    Cavalier cavalier = new Cavalier();//créer les cavalier
    Dame dame = new Dame();//créer les dame
    Foue foue = new Foue();//créer les foue
    Roi roi = new Roi();//créer les roi
    int selectionner=-1;//emplacement celectionner par la personne, initialiser a -1 car nes pas sur le plataux
    int coup=0;//nombre de coup jouer par la personne
    int couleur=0;
    TextView textView;
    Integer positiondepart=0;
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
        //textView = findViewById(R.id.NomJoueur);

        gridView.setEnabled(false);
        database = FirebaseDatabase.getInstance();//créer une instance

        //SharedPreferences preferences = getSharedPreferences("PREFS",0);
        //playerName = preferences.getString("playerName","");
        //textView.setText("joueur : " + playerName);

    }
    private void echecini(){
        initialisationsEchiquier();
        piecedeplacement();
    }

    private void deplacement(){
        String déplace;
        if(positionarriver == 0 && (roi.GetbougRoi() == 0 || roi.GetbougRoi() == 2)){
            déplace = BordPiece[positiondepart];
            BordPiece[positiondepart]="";
            BordPiece[2]=déplace;
            déplace = BordPiece[positionarriver];
            BordPiece[positionarriver]="";
            BordPiece[3]=déplace;
        }else if (positionarriver==7 && (roi.GetbougRoi() == 0 || roi.GetbougRoi() == 2)){
            déplace = BordPiece[positiondepart];
            BordPiece[positiondepart]="";
            BordPiece[6]=déplace;
            déplace = BordPiece[positionarriver];
            BordPiece[positionarriver]="";
            BordPiece[5]=déplace;
        }
        else if (positionarriver==56 && (roi.GetbougRoi() == 0 || roi.GetbougRoi() == 1)){
            déplace = BordPiece[positiondepart];
            BordPiece[positiondepart]="";
            BordPiece[58]=déplace;
            déplace = BordPiece[positionarriver];
            BordPiece[positionarriver]="";
            BordPiece[59]=déplace;
        }
        else if (positionarriver==63 && (roi.GetbougRoi() == 0 || roi.GetbougRoi() == 1))
        {
            déplace = BordPiece[positiondepart];
            BordPiece[positiondepart]="";
            BordPiece[62]=déplace;
            déplace = BordPiece[positionarriver];
            BordPiece[positionarriver]="";
            BordPiece[61]=déplace;
        }else{
            déplace = BordPiece[positiondepart];
            BordPiece[positiondepart]="";
            BordPiece[positionarriver]=déplace;

            déplace = colorP[positiondepart];
            colorP[positiondepart] ="";
            colorP[positionarriver]= déplace;
        }
        piecedeplacement();

    }

    private void piecedeplacement(){
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
                    if(coup==1)//si la piéce et vide ou que ces le deusiéme coup
                        if (selectionner == i){
                            caseNonValide();
                            colorActionPion.clear();
                        }
                        else{
                            trouverPositionCorecte(i);
                            colorActionPion.clear();
                        }
                    else
                        trouverLesdeplacement(i);
                }else if(role.equals("guest") && colorP[i].equals("N") || coup==1){
                    if(coup==1)//si la piéce et vide ou que ces le deusiéme coup
                        if (selectionner == i){
                            caseNonValide();
                            colorActionPion.clear();
                        }
                        else{
                            trouverPositionCorecte(i);
                            colorActionPion.clear();
                        }
                    else
                        trouverLesdeplacement(i);
                }
            }
        };
    }
    private void trouverPositionCorecte(int i){
        int posible=0;
        for (int j = 0; j < PositionValble.size(); j++) {
            if (PositionValble.get(j) == i) {
                posible = 1;
            }
        }
        PositionValble.clear();//nétoiller le tableaux
        if (BordPiece[selectionner].equals("P"))
            posibiliter(posible,i);
        else if (BordPiece[selectionner].equals("T"))
            posibiliter(posible,i);
        else if(BordPiece[selectionner].equals("C"))
            posibiliter(posible,i);
        else if (BordPiece[selectionner].equals("F"))
            posibiliter(posible,i);
        else if (BordPiece[selectionner].equals("D"))
            posibiliter(posible,i);
        else if (BordPiece[selectionner].equals("R"))
            posibiliter(posible,i);
    }

    private void posibiliter(int posible,int i){
        if (posible == 1)
            caseValide(i);
        else
            caseNonValide();
    }

    private void caseNonValide(){
        positiondepart = -1;
        coup--;
        piecedeplacement();
        Toast.makeText(GameActivity.this, "imposible", Toast.LENGTH_SHORT).show();
    }
    private void caseValide(int i){
        positiondepart = selectionner;
        positionarriver = i;
        deplacement();
        gridView.setEnabled(false);
        messageRef.setValue(role + ":" + selectionner + ":" + i);//change l'informatiosn dans la BDD
        coup = 0;
        selectionner = -1;
        Toast.makeText(GameActivity.this, "a votre adversaire de jouer", Toast.LENGTH_SHORT).show();

    }

    private void trouverLesdeplacement(int i){
        PositionValble.clear();
        if (BordPiece[i].equals("P")){
            if (role.equals("host"))
                colorActionPion.addAll(pion.deplacementHostPion(BordPiece,i,colorP));
            else
                colorActionPion.addAll(pion.deplacementGuestPion(BordPiece,i,colorP));
        }
        else if (BordPiece[i].equals("T")){
            if (role.equals("host"))
                colorActionPion = tour.deplacementTourHost(BordPiece,i,colorP);
            else
                colorActionPion = tour.deplacementTourGuest(BordPiece,i,colorP);
        }else if(BordPiece[i].equals("C")){
            if (role.equals("host"))
                colorActionPion = cavalier.deplacementCavalierHost(BordPiece,i,colorP);
            else
                colorActionPion = cavalier.deplacementCavalierGuest(BordPiece,i,colorP);
        }else if (BordPiece[i].equals("F")){
            if (role.equals("host"))
                colorActionPion = foue.deplacementFoueHost(BordPiece,i,colorP);
            else
                colorActionPion = foue.deplacementFoueGuest(BordPiece,i,colorP);
        }else if (BordPiece[i].equals("D")){
            if (role.equals("host"))
                colorActionPion = dame.deplacementDameHost(BordPiece,i,colorP);
            else
                colorActionPion = dame.deplacementDameGuest(BordPiece,i,colorP);
        }else if (BordPiece[i].equals("R")){
            if (role.equals("host"))
                colorActionPion = roi.deplacementRoiHost(BordPiece,i,colorP);
            else
                colorActionPion = roi.deplacementRoiGuest(BordPiece,i,colorP);
        }
        piéceColorDeplacement();
        coup++;
        selectionner = i;
    }

    private void piéceColorDeplacement() {
        //permet de mettre la couleur et de remettre le tableaux car il passe tout le temps
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BordPiece) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        int color,couleurNb=0,fin=0;
                        couleur=0;
                        color = coloration(position);
                        String[] Lettre = new String[colorActionPion.size()];
                        Integer[] coordonner = new Integer[colorActionPion.size()];
                        for (int i=0;i<colorActionPion.size();i++){
                            coordonner[i] = Integer.parseInt(colorActionPion.get(i).substring(colorActionPion.get(i).indexOf(":")+1,colorActionPion.get(i).length()));
                            Lettre[i] = colorActionPion.get(i).substring(0,colorActionPion.get(i).indexOf(":"));

                                if(position == coordonner[i]){
                                    if (Lettre[i].equals("O")){
                                        fin=1;
                                    } else if (Lettre[i].equals("A")){
                                         couleurNb = 3;
                                         PositionValble.add(coordonner[i]);
                                     }
                                     else if (Lettre[i].equals("D") && fin == 0){
                                         couleurNb = 2;
                                         PositionValble.add(coordonner[i]);
                                     }else if (Lettre[i].equals("R1")){
                                        couleurNb = 4;
                                        PositionValble.add(coordonner[i]);
                                    }else if (Lettre[i].equals("R2")){
                                        couleurNb = 4;
                                        PositionValble.add(coordonner[i]);
                                    }

                                 }
                        }
                        if(couleurNb == 2 )
                            color = Color.GREEN;
                        else if(couleurNb == 3)
                            color = Color.RED;
                        else if (couleurNb == 4)
                            color = Color.rgb(51,102,0);
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
            if(CompartPlayer.equals("hugo"))//teste pour savoir si ces le joueur1 ou 2(playerName)
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
                        action(snapshot);
                        //est affiche un message
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("guest:"+positiondepart+":"+positionarriver,"a toi de jouer"), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(snapshot.getValue().toString().contains("host")){//regarde si l'endroit ou les donnée a changer contient host:
                        action(snapshot);
                        //est affiche un message
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("host:"+positiondepart+":"+positionarriver,"a toi de jouer"), Toast.LENGTH_SHORT).show();

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
    private void action(DataSnapshot snapshot){
        gridView.setEnabled(true);
        séparateur(snapshot.getValue().toString());
    }

    private void séparateur(String snapshot){
        String séparaeur = snapshot.substring(snapshot.indexOf(":")+1,snapshot.length());
        positiondepart = Integer.parseInt(séparaeur.substring(0,séparaeur.indexOf(":")));
        positionarriver = Integer.parseInt(séparaeur.substring(séparaeur.indexOf(":")+1,séparaeur.length()));
        deplacement();
    }
}