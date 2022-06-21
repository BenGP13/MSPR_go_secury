package com.company;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Facade facade = new Facade(new ArrayList<>(), new ArrayList<>());
        facade.lancerLAppli();
    }
}
