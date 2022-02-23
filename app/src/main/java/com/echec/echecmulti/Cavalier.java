package com.echec.echecmulti;

public class Cavalier {
    public String[] déplacementCavalier(String[] Echiquier,int coordoner,String[] color) {
        String[] déplacement = new String[8];
        int cpt=0;
        //déplacement ver le haut
        int écart = coordoner + 8+8+9;//+8 pour monter d'une carse,plus 8 monter d'une case et +9pour aller ver la gauche
        for (int i=0;i< 2;i++){
            if (écart<63){
                if (Echiquier[écart].equals("")){
                    déplacement[cpt] = "D:"+écart;
                    cpt++;
                }else{
                    déplacement[cpt] = "A:"+écart;
                    cpt++;
                }
            }
            écart =coordoner+8+8+7;//+8 pour monter d'une carse,plus 8 monter d'une case et +7 pour aller ver la droit
        }
        écart = coordoner -8-8-9;//-8 pour décendre d'une carse,plus 8 décendre d'une case et -9pour aller ver la gauche
        //déplacement ver le le bat
        for (int i=0;i< 2;i++){
            if (écart>0){
                if (Echiquier[écart].equals("")){
                    déplacement[cpt] = "D:"+écart;
                    cpt++;
                }else{
                    déplacement[cpt] = "A:"+écart;
                    cpt++;
                }
            }
            écart =coordoner-8-8-7;//+8 pour décendre d'une carse,plus 8 décendre d'une case et -7 pour aller ver la droit
        }
        écart = coordoner +6;//+6pour mionter en haut as gauche
        //déplacement ver la gauche
        for (int i=0;i< 2;i++){
            if (écart>0){
                if (Echiquier[écart].equals("")){
                    déplacement[cpt] = "D:"+écart;
                    cpt++;
                }else{
                    déplacement[cpt] = "A:"+écart;
                    cpt++;
                }
            }
            écart =coordoner-12;//-12 pour décendre en bat a gauche
        }
        écart = coordoner +10;//+10pour mionter en haut as droit
        //déplacement ver la droit
        for (int i=0;i< 2;i++){
            if (écart>0){
                if (Echiquier[écart].equals("")){
                    déplacement[cpt] = "D:"+écart;
                    cpt++;
                }else{
                    déplacement[cpt] = "A:"+écart;
                    cpt++;
                }
            }
            écart =coordoner-6;//-6 pour décendre en bat a droit
        }
        return déplacement;
    }
}
