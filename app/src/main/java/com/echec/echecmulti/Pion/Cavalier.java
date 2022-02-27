package com.echec.echecmulti.Pion;

import java.util.ArrayList;

public class Cavalier {
    public ArrayList<String> deplacementCavalierHost(String[] Echiquier, int coordoner, String[] color) {
        ArrayList<String> déplacement = new ArrayList<>();
        int bordbase = 0,ecart=coordoner + 8+7;
        int[] borderG1= new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int[] borderG2 = new int[]{1,9,17,25,33,41,49,57};
        int[] borderD1 = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderD2 = new int[]{ 6,14,2,30,38,46,54,62};

        for (int i=0;i<8;i++){
            if (borderD1[i]==coordoner){
                bordbase = 1;
                i=8;
            }else if (borderD2[i] == coordoner){
                bordbase = 2;
                i=8;
            }else if (borderG1[i] == coordoner){
                bordbase = 3;
                i=8;
            }else if (borderG2[i] == coordoner){
                bordbase = 4;
                i=8;
            }
        }
        if (bordbase == 1){//le cavalier est sur la droit
            if (ecart<63 ){
                if (Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("N"))
                    déplacement.add("A:" + ecart);
            }
        }else if (bordbase == 3){//le cavalier est sur la gauche
            ecart +=2;
            if (ecart<63 ){
                if (Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("N"))
                    déplacement.add("A:" + ecart);
            }
        }else{//le cavalier est autre part
            for (int i=0;i< 2;i++){
                if (ecart<63){
                    if (Echiquier[ecart].equals(""))
                        déplacement.add("D:"+ecart);
                    else if (color[ecart].equals("N"))
                        déplacement.add("A:" + ecart);
                }else
                    i=2;
                ecart +=2;//+8 pour monter d'une carse,plus 8 monter d'une case et +7 pour aller ver la droit
            }
        }
        //déplacement ver le haut

        ecart = coordoner -8-9;//-8 pour décendre d'une carse,plus 8 décendre d'une case et -9pour aller ver la gauche
        if (bordbase == 1){
            if (ecart>0){
                if (Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("N"))
                    déplacement.add("A:"+ecart);
            }
        }else if (bordbase == 3){
            ecart +=2;
            if (ecart>0){
                if (Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("N"))
                    déplacement.add("A:"+ecart);
            }
        }else{
            for (int i=0;i< 2;i++){
                if (ecart>0){
                    if (Echiquier[ecart].equals(""))
                        déplacement.add("D:"+ecart);
                    else if (color[ecart].equals("N"))
                        déplacement.add("A:"+ecart);
                }else
                    i=2;
                ecart +=2;
            }
        }

        ecart = coordoner +6;//+6pour mionter en haut as gauche
        //déplacement ver la gauche
        for (int i=0;i< 2;i++){
                if (ecart<64 && ecart>0 && bordbase !=3 && bordbase !=4){
                    if (Echiquier[ecart].equals("")){
                        déplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        déplacement.add("A:"+ecart);
                    }
                }else
                    i=2;
            ecart =coordoner-10;//-12 pour décendre en bat a gauche
        }
        ecart = coordoner +10;//+10pour mionter en haut as droit
        //déplacement ver la droit
        for (int i=0;i< 2;i++){
           if (ecart<64 && ecart>0 &&  bordbase !=2 && bordbase !=1){
                if (Echiquier[ecart].equals("")){
                    déplacement.add("D:"+ecart);
                }else if (color[ecart].equals("N")){
                    déplacement.add("A:"+ecart);
                }
            }else
                i=2;
            ecart =coordoner-6;//-6 pour décendre en bat a droit
        }
        return déplacement;
    }

    public ArrayList<String> deplacementCavalierGuest(String[] Echiquier, int coordoner, String[] color) {
        ArrayList<String> déplacement = new ArrayList<>();
        int bordbase = 0,ecart=coordoner + 8+7;
        int[] borderG1= new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int[] borderG2 = new int[]{1,9,17,25,33,41,49,57};
        int[] borderD1 = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderD2 = new int[]{ 6,14,2,30,38,46,54,62};

        for (int i=0;i<8;i++){
            if (borderD1[i]==coordoner){
                bordbase = 1;
                i=8;
            }else if (borderD2[i] == coordoner){
                bordbase = 2;
                i=8;
            }else if (borderG1[i] == coordoner){
                bordbase = 3;
                i=8;
            }else if (borderG2[i] == coordoner){
                bordbase = 4;
                i=8;
            }
        }
        if (bordbase == 1){//le cavalier est sur la droit
            if (ecart<63 ){
                if (Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("B"))
                    déplacement.add("A:" + ecart);
            }
        }else if (bordbase == 3){//le cavalier est sur la gauche
            ecart +=2;
            if (ecart<63 ){
                if (Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("B"))
                    déplacement.add("A:" + ecart);
            }
        }else{//le cavalier est autre part
            for (int i=0;i< 2;i++){
                if (ecart<63){
                    if (Echiquier[ecart].equals(""))
                        déplacement.add("D:"+ecart);
                    else if (color[ecart].equals("B"))
                        déplacement.add("A:" + ecart);
                }else
                    i=2;
                ecart +=2;//+8 pour monter d'une carse,plus 8 monter d'une case et +7 pour aller ver la droit
            }
        }
        //déplacement ver le haut

        ecart = coordoner -8-9;//-8 pour décendre d'une carse,plus 8 décendre d'une case et -9pour aller ver la gauche
        if (bordbase == 1){
            if (ecart>0){
                if (Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("B"))
                    déplacement.add("A:"+ecart);
            }
        }else if (bordbase == 3){
            ecart +=2;
            if (ecart>0){
                if (Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("B"))
                    déplacement.add("A:"+ecart);
            }
        }else{
            for (int i=0;i< 2;i++){
                if (ecart>0){
                    if (Echiquier[ecart].equals(""))
                        déplacement.add("D:"+ecart);
                    else if (color[ecart].equals("B"))
                        déplacement.add("A:"+ecart);
                }else
                    i=2;
                ecart +=2;
            }
        }

        ecart = coordoner +6;//+6pour mionter en haut as gauche
        //déplacement ver la gauche
        for (int i=0;i< 2;i++){
            if (ecart<64 && ecart>0 && bordbase !=3 && bordbase !=4){
                if (Echiquier[ecart].equals("")){
                    déplacement.add("D:"+ecart);
                }else if (color[ecart].equals("B")){
                    déplacement.add("A:"+ecart);
                }
            }else
                i=2;
            ecart =coordoner-10;//-12 pour décendre en bat a gauche
        }
        ecart = coordoner +10;//+10pour mionter en haut as droit
        //déplacement ver la droit
        for (int i=0;i< 2;i++){
            if (ecart>0 && ecart<64 && bordbase !=2 && bordbase !=1){
                if (Echiquier[ecart].equals("")){
                    déplacement.add("D:"+ecart);
                }else if (color[ecart].equals("B")){
                    déplacement.add("A:"+ecart);
                }
            }else
                i=2;
            ecart =coordoner-6;//-6 pour décendre en bat a droit
        }
        return déplacement;
    }

}
