package com.echec.echecmulti;

import java.util.ArrayList;

public class Roi {
    public ArrayList<String> deplacementRoiHost(String[] Echiquier, int coordoner, String[] color) {
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

        //pour le haut
        if (bor==1){
            ecart = coordoner +8;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                        i=2;
                    }
                }
                ecart++;
            }
        }else if (bor==2){
            ecart = coordoner +7;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                        i=2;
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
                        i=2;
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
                        i=2;
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
                        i=2;
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
                        i=2;
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
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int ecart,bor=0;
        for (int i=0;i<8;i++){
            if (borderD[i]==coordoner)
                bor=1;
            else if (borderG[i]==coordoner)
                bor=2;
        }

        //pour le haut
        if (bor==1){
            ecart = coordoner +8;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                        i=2;
                    }
                }
                ecart++;
            }
        }else if (bor==2){
            ecart = coordoner +7;
            for (int i=0;i<2;i++){
                if (ecart<63 && ecart>0){
                    if (Echiquier[ecart].equals("")){
                        deplacement.add("D:"+ecart);
                    }else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                        i=2;
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
                        i=2;
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
                        i=2;
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
                        i=2;
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
                        i=2;
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
