package com.company;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Facade facade = new Facade(new ArrayList<>(), new ArrayList<>());
        facade.lancerLAppli();
    }
}
