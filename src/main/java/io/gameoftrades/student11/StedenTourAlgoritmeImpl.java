/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student11;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.model.markt.actie.HandelsPositie;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author djdeg
 */
public class StedenTourAlgoritmeImpl implements StedenTourAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();

    @Override
    public List<Stad> bereken(Kaart kaart, List<Stad> notvisited) {
        LinkedList<Stad> visited = new LinkedList();
        System.out.println(notvisited.toString());
        System.out.println(notvisited.size());
        Stad Current = notvisited.get(0);
        System.out.println(Current.toString());
        visited.add(Current);
        Pad pad;
        int Tijd;
        int totaletijd = 0;
        int snelsteTijd = 0;
        Stad dichtstbijzijnde = null;
        while (visited.size() < notvisited.size()) {
            for (Stad c : notvisited) {
                if (!visited.contains(c)) {
                    SnelstePadAlgoritmeImpl snelste = new SnelstePadAlgoritmeImpl();
                    pad = snelste.bereken(kaart, Current.getCoordinaat(), c.getCoordinaat());
                    Tijd = pad.getTotaleTijd();
                    
                    if (snelsteTijd == 0) {
                        snelsteTijd = Tijd;
                        dichtstbijzijnde = c;
                        System.out.println(c.getNaam() + "laagste begin");
                    } else if (snelsteTijd > Tijd) {
                        System.out.println(c.getNaam() + "laagste verder");
                        snelsteTijd = Tijd;
                        dichtstbijzijnde = c;
                    } else {
                        System.out.println(c.getNaam() + "niet laagste");
                    }
                }
            }
           totaletijd = totaletijd + snelsteTijd;
            System.out.println(totaletijd);
            visited.add(dichtstbijzijnde);
            Current = dichtstbijzijnde;
            dichtstbijzijnde = null;
            snelsteTijd = 0;
        }
        debug.debugSteden(kaart, visited);
        System.out.println(totaletijd);
        return visited;
    }

    @Override
    public void setDebugger(Debugger dbgr) {
        this.debug = dbgr;
    }

}
