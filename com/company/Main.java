package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        System.out.println("On lance bien l'execution");
        Facade facade = new Facade(new ArrayList<>(), new ArrayList<>());
        facade.lancerLAppli();
    }
}
