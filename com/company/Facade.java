package com.company;

import com.company.Entity.Agent;
import com.company.Entity.Materiel;

import java.io.*;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
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

    void lancerLAppli() throws IOException {
        lectureFichierListe();
        lectureDossierAgents();
        genererLaPageDAccueil();
        genererLeCSS();
        ecrireLeCss();
        ecrirePageAccueil();
        genererLHtaccess();
        ecrirelHtaccess();

        List<MyThread> listThreads = new ArrayList<>();

        for (Agent agent : listAgents){
            MyThread thread = new MyThread(agent, listAgents, listMateriels);
            listThreads.add(thread);
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
            // Recevoir le fichier
            File f = new File("/var/www/groupe5CDA.com/public_html/" + "index.html");

            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile())
                System.out.println("File created");
            else
                System.out.println("File already exists");
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void genererLeCSS() throws IOException {
        String fileName = "/var/www/groupe5CDA.com/public_html/assets";

        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
            System.out.println("New Directory created !");
        } else {
            System.out.println("Directory already exists");
        }
        creationFichier("style.css", "/var/www/groupe5CDA.com/public_html/assets/");
    }

    void ecrireLeCss() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("/var/www/groupe5CDA.com/public_html/assets/style.css", "UTF-8");
        writer.println("body\n" +
                "{\n" +
                "    height: 100vh;\n" +
                "    margin:0px;\n" +
                "}\n" +
                "\n" +
                ".parent {\n" +
                "    display: grid;\n" +
                "    grid-template-columns: repeat(7, 1fr);\n" +
                "    grid-template-rows: repeat(9, 1fr);\n" +
                "    grid-column-gap: 0px;\n" +
                "    grid-row-gap: 0px;\n" +
                "    height: 100%;\n" +
                "    background-image: linear-gradient(to left, #379ec1, #00a1ae, #00a08a, #2b9b5a, #659224);\n" +
                "    background-blend-mode: multiply;\n" +
                "}\n" +
                "\n" +
                ".div1 {\n" +
                "    grid-area: 2 / 2 / 9 / 7;\n" +
                "    background: white;\n" +
                "    border-radius: 80px;\n" +
                "}\n" +
                ".div2 {\n" +
                "    grid-area: 2 / 4 / 3 / 5;\n" +
                "}\n" +
                ".div3 {\n" +
                "    grid-area: 4 / 3 / 8 / 6;\n" +
                "    position: relative;\n" +
                "}\n" +
                ".div4\n" +
                "{\n" +
                "    position: absolute;\n" +
                "    width: 50%;\n" +
                "    margin-left: 25%;\n" +
                "\n" +
                "}\n" +
                "th,table\n" +
                "{\n" +
                "    padding: 10px;\n" +
                "    border-collapse: collapse;\n" +
                "    border: 1px solid black;\n" +
                "    width: 100%;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                ".scroller {\n" +
                "    height: 100%;\n" +
                "    overflow-y: auto;\n" +
                "    scrollbar-width: thin;\n" +
                "  }");
        writer.close();
    }

    void ecrirePageAccueil() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("/var/www/groupe5CDA.com/public_html/index.html", "UTF-8");
        writer.println("<!doctype html>\n" +
                "\n" +
                "<html lang=\"fr\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Accueil</title>\n" +
                "    <meta name=\"Accueil\" content=\"A simple home page.\">\n" +
                "    <meta name=\"Groupe5\" content=\"SiteMSPR\">\n" +
                "    <link rel=\"stylesheet\" href=\"assets/style.css\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"parent\">\n" +
                "        <div class=\"div1\">\n" +
                "        </div>\n" +
                "        <div class=\"div2\"> <img src=\"assets/logo.png\" alt=\"\" style=\"align-content: center; margin-top: 20px;\">\n" +
                "        </div>\n" +
                "        <div class=\"div3\">\n" +
                "            <div class=\"div4 scroller\">\n" +
                "                <table>\n" +
                "                    <thead>\n" +
                "                        <tr>\n" +
                "                            <th>Nom des agents</th>\n" +
                "                        </tr>\n" +
                "                    </thead>\n" +
                "                    <tbody>");
        for (Agent agent: listAgents){
            writer.println("<tr>\n" +
                    "<td><a href='http://82.66.91.88:41015/" + agent.getNom() + agent.getPrenom() + ".html'>"
                    + agent.getNom() + agent.getPrenom() + "</a> </td>\n" +
                    "</tr>\n");
        }
        writer.println("</tbody>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <script src=\"js/scripts.js\"></script>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
        writer.close();
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

    void genererLesHtpasswd(String nomFichier, String chemin, Agent agent){
        try{
            PrintWriter writer = new PrintWriter(chemin + nomFichier, "UTF-8");
            byte b[] = java.security.MessageDigest.getInstance("MD5").digest( (agent.getNom() + agent.getPrenom() + ":" + "Private" + ":" + agent.getMotDePasse() ).getBytes());
            java.math.BigInteger bi = new java.math.BigInteger(1, b);
            String s = bi.toString(16);
            while(s.length() < 32)
                s = "0" + s;
            writer.println(agent.getNom() + agent.getPrenom() + ":Private:" + s);
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    void genererLHtaccess(){
        try{
            // Recevoir le fichier
            File f = new File("/var/www/groupe5CDA.com/public_html/" + ".htaccess");

            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile())
                System.out.println("File created");
            else
                System.out.println("File already exists");
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void ecrirelHtaccess() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("/var/www/groupe5CDA.com/public_html/" + ".htaccess", "UTF-8");
        for (Agent agent : listAgents){
            writer.println("<Files " + agent.getNom() + agent.getPrenom() + ".html>\n" +
                    "AuthType Digest\n" +
                    "AuthName \"Private\"\n" +
                    "AuthUserFile /var/www/groupe5CDA.com/public_html/.htpasswd" + agent.getNom() + agent.getPrenom() + "\n" +
                    "Require valid-user\n" +
                    "</Files>\n");
        }
        writer.close();
    }

}
