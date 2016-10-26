/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student11;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author djdeg
 */
public class StedenTourAlgoritmeImpl implements StedenTourAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();

    @Override
    public List<Stad> bereken(Kaart kaart, List<Stad> notvisited) 
    {
        LinkedList<Stad> visited = new LinkedList(); //visited list aanmaken
        Stad Current = notvisited.get(0);
        visited.add(Current); //het beginvakje erin zetten
        Pad pad;
        int Tijd;
        int totaletijd = 0;
        int snelsteTijd = 0;
        SnelstePadAlgoritmeImpl snelste = new SnelstePadAlgoritmeImpl();    //nieuw algoritme aanroepen
        Stad dichtstbijzijnde = null;
        while (visited.size() < notvisited.size())  //loopt de loop door voor als de notvisited even groot is
        {
            for (Stad c : notvisited)       //loopt de notvisited door
            {
                if (!visited.contains(c)) //kijkt of die al in de visited lijst is gezet
                {

                    pad = snelste.bereken(kaart, Current.getCoordinaat(), c.getCoordinaat());   //berekenen
                    Tijd = pad.getTotaleTijd();     //tijd opslaan

                    if (snelsteTijd == 0)       //als er nog niks is 
                    {
                        snelsteTijd = Tijd;
                        dichtstbijzijnde = c;
                    } 
                    else if (snelsteTijd > Tijd)    //snelste tijd opslaan
                    {
                        snelsteTijd = Tijd;
                        dichtstbijzijnde = c;
                    } 
                }
            }
            visited.add(dichtstbijzijnde);
            Current = dichtstbijzijnde;         //zet de dichtstbijzijnde in de list en zet alles op null
            dichtstbijzijnde = null;
            snelsteTijd = 0;
        }
        debug.debugSteden(kaart, visited);
        return visited;
    }

    @Override
    public void setDebugger(Debugger dbgr)
    {
        this.debug = dbgr;
    }

}
