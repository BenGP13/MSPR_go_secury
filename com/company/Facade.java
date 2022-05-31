package com.company;

import com.company.Entity.Agent;
import com.company.Entity.Materiel;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Facade {

    List<Agent> listAgents;
    List<Materiel> listMateriels;

    public Facade(List<Agent> listAgents, List<Materiel> listMateriels) {
        this.listAgents = listAgents;
        this.listMateriels = listMateriels;
    }

    void lancerLAppli(){
        lectureFichierListe();
        lectureDossierAgents();
        genererLaPageDAccueil();

        List<MyThread> listThreads = new ArrayList<>();
        for (Agent agent : listAgents){
            MyThread thread = new MyThread(agent, listAgents, listMateriels);
            listThreads.add(thread);
//            Facade facade = new Facade(listAgents, listMateriels);
//            facade.creationFichier(agent.getNom() + agent.getPrenom() + ".html", System.getProperty("user.dir") + "\\com\\company\\agentsHTML\\");
//            facade.genererLesPagesAgents(agent.getNom() + agent.getPrenom() + ".html", System.getProperty("user.dir") + "\\com\\company\\agentsHTML\\", agent);
        }

        for (MyThread thread : listThreads){
            thread.start();
        }
        try {
            for (MyThread thread : listThreads){
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * On va lire le fichier list.txt qui contient les équipements
     * l'ID et la valeur
     *
     * On écrit les valeurs dans la variable listMateriels
     */
    void lectureFichierListe(){
        try{
            String line;
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + "/liste.txt");
            BufferedReader bufferedReader = new BufferedReader (fileReader);

            listMateriels.clear();

            while((line=bufferedReader.readLine())!=null) {
                String[] split = line.split("\t");
                Materiel materiel = new Materiel(split[0], split[1]);
                listMateriels.add(materiel);
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    Agent lectureFichierAgent(String fileName){
        try{
            String line;
            Integer actualLine = 1;
            Agent agent = new Agent();
            List<Materiel> listMaterielsForTheAgent = new ArrayList<>();
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + "/agents/" + fileName);
            BufferedReader bufferedReader = new BufferedReader (fileReader);
            while((line=bufferedReader.readLine())!=null) {
                switch (actualLine){
                    case (1):
                        agent.setNom(line);
                        break;
                    case (2):
                        agent.setPrenom(line);
                        break;
                    case (3):
                        agent.setMission(line);
                        break;
                    case (4):
                        agent.setMotDePasse(line);
                        break;
                    default:
                        if(!line.isEmpty()){
                            for (Materiel materiel: listMateriels){
                                if(Objects.equals(materiel.getIdentifiant(), line)){
                                    listMaterielsForTheAgent.add(new Materiel(line, materiel.getNom()));
                                }
                            }
                        }
                }
                actualLine += 1;
            }
            agent.setListMateriels(listMaterielsForTheAgent);
            bufferedReader.close();
            return agent;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * On va lire le dossier agents qui contient les agents (fichier txt)
     *
     * On écrit les valeurs dans la variable listAgents
     */
    void lectureDossierAgents(){
        lectureFichierListe();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(System.getProperty("user.dir") + "/agents"))) {
            for (Path file: stream) {
                if(".txt".equals(file.toString().substring(file.toString().length() - 4))){
                    listAgents.add(lectureFichierAgent(file.getFileName().toString()));
                }
            }
        } catch (IOException | DirectoryIteratorException ex) {
            System.err.println(ex);
        }
    }

    void creationFichier(String nomFichier, String chemin){
        try {
            // Recevoir le fichier
            File f = new File(chemin + nomFichier);

            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile())
                System.out.println("File created");
            else
                System.out.println("File already exists");
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    void genererLaPageDAccueil(){
        try{
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "\\com\\company\\agentsHTML\\index.html", "UTF-8");
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang=\"fr\">");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\">");
            writer.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            writer.println("<title>Page2</title>");
            writer.println("<meta name=\"Page2\" content=\"A simple second page.\">");
            writer.println("<meta name=\"Groupe5\" content=\"SiteMSPR\">");
            writer.println("<link rel=\"stylesheet\" href=\"assets\\style2.css\">");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<div class=\"parent\">");
            writer.println("<div class=\"div1\" id=\"baseDiv\">\n" +
                    "</div>\n" +
                    "<div class=\"div2\"  id=\"idenfication\">\n" +
                    "Identification\n" +
                    "</div>\n" +
                    "<div class=\"div3\"  id=\"identityCard\">\n" +
                    "carte d'identité\n" +
                    "</div>\n" +
                    "<div class=\"div4\"  id=\"checkboxDiv\">\n" +
                    "<div class=\"div5\">\n");
            writer.println("</div>\n" +
                    "</div>\n" +
                    "</div>");
            writer.println("</body>");
            writer.println("</html>");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void genererLesPagesAgents(String nomFichier, String chemin, Agent agent){
        try{
            List<String> listMaterielIDAgent = new ArrayList<>();
            for (Materiel materiel: agent.getListMateriels()){
                listMaterielIDAgent.add(materiel.getIdentifiant());
            }

            PrintWriter writer = new PrintWriter(chemin + nomFichier, "UTF-8");
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang=\"fr\">");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\">");
            writer.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            writer.println("<title>Page2</title>");
            writer.println("<meta name=\"Page2\" content=\"A simple second page.\">");
            writer.println("<meta name=\"Groupe5\" content=\"SiteMSPR\">");
            writer.println("<link rel=\"stylesheet\" href=\"assets\\style2.css\">");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<div class=\"parent\">");
            writer.println("<div class=\"div1\" id=\"baseDiv\">\n" +
                    "</div>\n" +
                    "<div class=\"div2\"  id=\"idenfication\">\n" +
                    "Identification\n" +
                    "</div>\n" +
                    "<div class=\"div3\"  id=\"identityCard\">\n" +
                    "carte d'identité\n" +
                    "</div>\n" +
                    "<div class=\"div4\"  id=\"checkboxDiv\">\n" +
                    "<div class=\"div5\">\n");
            for (Materiel materiel : listMateriels){
                if(listMaterielIDAgent.contains(materiel.getIdentifiant())){
                    writer.println("<input type=\"checkbox\" id=\"" + materiel.getIdentifiant() + "\" name=\"" + materiel.getIdentifiant() + "\" disabled checked><label>" + materiel.getNom() + "</label>");
                }else{
                    writer.println("<input type=\"checkbox\" id=\"" + materiel.getIdentifiant() + "\" name=\"" + materiel.getIdentifiant() + "\" disabled><label>" + materiel.getNom() + "</label>");
                }
                writer.println("<br>");
            }
            writer.println("</div>\n" +
                    "</div>\n" +
                    "</div>");
            writer.println("</body>");
            writer.println("</html>");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
