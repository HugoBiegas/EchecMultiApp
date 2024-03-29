package com.echec.echecmulti.Pion;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Roi {
    private  int bougRoi=0,host=0,guest=0;
    private boolean hostRock=false,guestRock=false;
    public int GetbougRoi(){
        return bougRoi;
    }
    public boolean GethostRock(){
        return hostRock;
    }
    public boolean GetguestRock(){
        return guestRock;
    }
    public void sethostRock(boolean hostRock){ this.hostRock = hostRock; }
    public void setguestRock(boolean guestRock){ this.guestRock = guestRock; }

    public ArrayList<String> deplacementRoiHost(String[] Echiquier, int coordoner, String[] color) {
        ArrayList<String> deplacement;
        deplacement =hostroi(Echiquier,coordoner,color);
        return deplacement;
    }
    public ArrayList<String> hostroi(String[] Echiquier, int coordoner, String[] color){
        ArrayList<String> deplacement = new ArrayList<>();
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int ecart,bor=0;
        for (int i=0;i<8;i++){
            if (borderD[i]==coordoner)
                bor=1;
            else if (borderG[i]==coordoner)
                bor=2;
        }
        if (Echiquier[4].equals("") && host == 0){
            hostRock=true;
            bougRoi += 1;
            host=1;
        }
        if ( Echiquier[0].equals("T") && color[0].equals("B") && Echiquier[1].equals("") && Echiquier[2].equals("") && Echiquier[3].equals("") && (bougRoi==0 || bougRoi == 2)){
            deplacement.add("R1:"+0);
        }else if (Echiquier[7].equals("T") && color[7].equals("B") && Echiquier[5].equals("") && Echiquier[6].equals("") && (bougRoi==0 || bougRoi == 2) )
            deplacement.add("R2:"+7);

        //pour le haut
        if (bor==2){
            ecart = coordoner +8;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart++;
            }
        }else if (bor==1){
            ecart = coordoner +7;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart++;
            }
        }else{
            ecart = coordoner +7;
            for (int i=0;i<3;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart++;
            }

        }

        //pour le bat
        if (bor==1){
            ecart = coordoner -8;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart--;
            }
        }else if (bor==2){
            ecart = coordoner -7;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart--;
            }
        }else{
            ecart = coordoner -7;
            for (int i=0;i<3;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart--;
            }
        }
        //droit
        ecart = coordoner+1;
        if (ecart<63 && ecart>0 && bor!=1){
            if (ecart<63 && ecart>0){
                if (Echiquier[ecart].equals("")){
                    deplacement.add("D:"+ecart);
                }else if (color[ecart].equals("N")){
                    deplacement.add("A:"+ecart);
                }
            }
        }
        //gauche
        ecart = coordoner-1;
        if (ecart<63 && ecart>0 && bor!=2){
            if (ecart<63 && ecart>0){
                if (Echiquier[ecart].equals("")){
                    deplacement.add("D:"+ecart);
                }else if (color[ecart].equals("N")){
                    deplacement.add("A:"+ecart);
                }
            }
        }
        return deplacement;
    }

    public ArrayList<String> deplacementRoiGuest(String[] Echiquier, int coordoner, String[] color) {
        ArrayList<String> deplacement = new ArrayList<>();
        deplacement = guestRoi(Echiquier,coordoner,color);
        return deplacement;
    }

    public ArrayList<String > guestRoi(String[] Echiquier, int coordoner, String[] color){
        ArrayList<String> deplacement = new ArrayList<>();
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int ecart,bor=0;
        for (int i=0;i<8;i++){
            if (borderD[i]==coordoner)
                bor=1;
            else if (borderG[i]==coordoner)
                bor=2;
        }
        if (Echiquier[60].equals("") && guest==0){
            guestRock=true;
            bougRoi +=2;
            guest =1;
        }

        if (Echiquier[56].equals("T") && color[56].equals("N") && Echiquier[57].equals("") && Echiquier[58].equals("") && Echiquier[59].equals("") && (bougRoi==0 || bougRoi == 1)){
            deplacement.add("R1:"+56);
        }else if (Echiquier[63].equals("T") && color[63].equals("N") && Echiquier[61].equals("") && Echiquier[62].equals("") && (bougRoi==0 || bougRoi == 1) )
            deplacement.add("R2:"+63);


        //pour le haut
        if (bor==2){
            ecart = coordoner +8;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart++;
            }
        }else if (bor==1){
            ecart = coordoner +7;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart++;
            }
        }else{
            ecart = coordoner +7;
            for (int i=0;i<3;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart++;
            }
        }

        //pour le bat
        if (bor==1){
            ecart = coordoner -8;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart--;
            }
        }else if (bor==2){
            ecart = coordoner -7;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart--;
            }
        }else{
            ecart = coordoner -7;
            for (int i=0;i<3;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                    }
                }
                ecart--;
            }
        }
        //droit
        ecart = coordoner+1;
        if (ecart<63 && ecart>0 && bor!=1){
            if (ecart<63 && ecart>0){
                if (Echiquier[ecart].equals("")){
                    deplacement.add("D:"+ecart);
                }else if (color[ecart].equals("B")){
                    deplacement.add("A:"+ecart);
                }
            }
        }
        //gauche
        ecart = coordoner-1;
        if (ecart<63 && ecart>0 && bor!=2){
            if (ecart<63 && ecart>0){
                if (Echiquier[ecart].equals("")){
                    deplacement.add("D:"+ecart);
                }else if (color[ecart].equals("B")){
                    deplacement.add("A:"+ecart);
                }
            }
        }

        return deplacement;
    }
}
