package com.echec.echecmulti.Pion;

import android.icu.text.RelativeDateTimeFormatter;

import java.util.ArrayList;

public class Tour {
    public ArrayList<String> deplacementTourHost(String[] Echiquier, int coordoner, String[] color) {
        ArrayList<String> déplacement = new ArrayList<>();
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};

        int ecart = coordoner+8;
        int non = 0;
        int bordBase=4;
        //tour pour manger ver le haut
        for(int i=0;i<8;i++){
            if (non == 0 && ecart<64){
                if(Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("N")){
                    déplacement.add("A:"+ecart);
                    non =1;
                 }else
                    non =1;
            }else
                i=8;//sortire de la boucle
            ecart+=8;
        }
        non=0;
        ecart = coordoner-8;
        //tour pour manger ver le bat
        for (int i=0;i<8;i++){
            if (non ==0 && ecart>=0){
                if(Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("N")){
                   déplacement.add("A:"+ecart);
                    non =1;
                }else
                    non=1;
            }else
                i=8;
            ecart-=8;
        }
        for (int i=0;i<7;i++){
            if (coordoner == 0 || coordoner == 63)
                bordBase = 0;
            else if (coordoner == borderD[i])
                bordBase = 1;
            else if (coordoner == borderG[i])
                bordBase = 2;
        }
        non=0;
        ecart = coordoner+1;
        //tour pour manger sur la droit
        for (int i=0;i<8;i++){
                    if (bordBase ==0 ||ecart == (coordoner+1) && bordBase == 1||bordBase == 2 && ecart == (coordoner-1))
                        non=1;
                    else if (non == 0){
                        for (int j=0;j<7;j++){
                            if (borderD[j] == ecart || borderG[j] ==ecart){
                                non=1;
                                if (!Echiquier[ecart].equals("") && color[ecart].equals("N"))
                                    déplacement.add("A:"+ecart);
                                else if (color[ecart].equals("B"))
                                    déplacement.add("O:"+ecart);
                                else
                                    déplacement.add("D:"+ecart);
                            }
                        }
                        if(Echiquier[ecart].equals(""))
                         déplacement.add("D:"+ecart);
                        else if (color[ecart].equals("N")){
                                déplacement.add("A:"+ecart);
                                non=1;
                            }else if (color[ecart].equals("B")){
                            déplacement.add("O:"+ecart);
                            non=1;
                            }
                    }else
                        i=8;
           ecart++;
        }
        non=0;
        ecart = coordoner-1;
        //tour pour manger sur la gauche
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner+1) && bordBase == 1||bordBase == 2 && ecart == (coordoner-1))
                non=1;
            else if (non == 0){
                for (int j=0;j<7;j++){
                    if (borderD[j] == ecart || borderG[j] ==ecart){
                        non=1;
                        if (!Echiquier[ecart].equals("") && color[ecart].equals("N"))
                            déplacement.add("A:"+ecart);
                        else if (color[ecart].equals("B"))
                            déplacement.add("O:"+ecart);
                        else
                            déplacement.add("D:"+ecart);
                    }
                }
                 if(Echiquier[ecart].equals(""))
                     déplacement.add("D:"+ecart);
                 else if (color[ecart].equals("N")){
                      déplacement.add("A:"+ecart);
                     non=1;
                 }else if (color[ecart].equals("B")) {
                     déplacement.add("O:" + ecart);
                     non = 1;
                 }
                }else
                    i=8;
            ecart--;
        }
        return déplacement;
    }


    public ArrayList<String> deplacementTourGuest(String[] Echiquier, int coordoner, String[] color){
        ArrayList<String> déplacement = new ArrayList<>();
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};

        int ecart = coordoner+8;
        int non = 0;
        int bordBase=4;
        //tour pour manger ver le haut
        for(int i=0;i<8;i++){
            if (non == 0 && ecart<64){
                if(Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("B")){
                    déplacement.add("A:"+ecart);
                    non =1;
                }else{
                    non =1;
                }
            }else
                i=8;//sortire de la boucle
            ecart+=8;
        }
        non=0;
        ecart = coordoner-8;
        //tour pour manger ver le bat
        for (int i=0;i<8;i++){
            if (non ==0 && ecart>=0){
                if(Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("B")){
                    déplacement.add("A:"+ecart);
                    non =1;
                }else
                    non=1;
            }else
                i=8;
            ecart-=8;
        }
        for (int i=0;i<7;i++){
            if (coordoner == 0 || coordoner == 63)
                bordBase = 0;
            else if (coordoner == borderD[i])
                bordBase = 1;
            else if (coordoner == borderG[i])
                bordBase = 2;
        }
        non=0;
        ecart = coordoner+1;
        //tour pour manger sur la droit
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner+1) && bordBase == 1||bordBase == 2 && ecart == (coordoner-1))
                non=1;
            else if (non == 0){
                for (int j=0;j<7;j++){
                    if (borderD[j] == ecart || borderG[j] ==ecart){
                        non=1;
                        if (!Echiquier[ecart].equals("") && color[ecart].equals("B"))
                            déplacement.add("A:"+ecart);
                        else if (color[ecart].equals("N"))
                            déplacement.add("O:"+ecart);
                        else
                            déplacement.add("D:"+ecart);
                    }
                }
                if(Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("B")){
                    déplacement.add("A:"+ecart);
                    non=1;
                }else if (color[ecart].equals("N")){
                    déplacement.add("O:"+ecart);
                    non=1;
                }
            }else
                i=8;
            ecart++;
        }
        non=0;
        ecart = coordoner-1;
        //tour pour manger sur la gauche
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner+1) && bordBase == 1||bordBase == 2 && ecart == (coordoner-1))
                non=1;
            else if (non == 0){
                for (int j=0;j<7;j++){
                    if (borderD[j] == ecart || borderG[j] ==ecart){
                        non=1;
                        if (!Echiquier[ecart].equals("") && color[ecart].equals("B"))
                            déplacement.add("A:"+ecart);
                        else if (color[ecart].equals("N"))
                            déplacement.add("O:"+ecart);
                        else
                            déplacement.add("D:"+ecart);
                    }
                }
                if(Echiquier[ecart].equals(""))
                    déplacement.add("D:"+ecart);
                else if (color[ecart].equals("B")){
                    déplacement.add("A:"+ecart);
                    non=1;
                }else if (color[ecart].equals("N")) {
                    déplacement.add("O:" + ecart);
                    non = 1;
                }
            }else
                i=8;
            ecart--;
        }
        return déplacement;
    }
}
