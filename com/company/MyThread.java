package com.company;

import com.company.Entity.Agent;
import com.company.Entity.Materiel;

import java.util.List;

public class MyThread extends Thread {

    public volatile boolean isRunning = true;
    public Agent agent;
    List<Agent> listAgents;
    List<Materiel> listMateriels;

    public MyThread(Agent agent, List<Agent> listAgents, List<Materiel> listMateriels) {
        this.agent = agent;
        this.listAgents = listAgents;
        this.listMateriels = listMateriels;
    }

    @Override
    public void run() {
        while (isRunning){
            Facade facade = new Facade(listAgents, listMateriels);
            facade.creationFichier(agent.getNom() + agent.getPrenom() + ".html", System.getProperty("user.dir") + "\\com\\company\\agentsHTML\\");
            facade.genererLesPagesAgents(agent.getNom() + agent.getPrenom() + ".html", System.getProperty("user.dir") + "\\com\\company\\agentsHTML\\", agent);
            facade.genererLesHtpasswd(".htpasswd" + agent.getNom() + agent.getPrenom(), System.getProperty("user.dir") + "\\com\\company\\agentsHTML\\", agent);
            isRunning = false;
        }
    }

}
