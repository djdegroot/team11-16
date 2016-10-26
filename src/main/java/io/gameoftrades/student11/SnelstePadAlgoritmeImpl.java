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
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Parent;

/**
 *
 * @author djdeg
 */
public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();

    @Override
    public Pad bereken(Kaart kaart, Coordinaat Start, Coordinaat Eind) {
        ArrayList<Terrain> openlist = new ArrayList<>();
        ArrayList<Terrain> closedlist = new ArrayList<>();
        Terrain Current = new Terrain(0, Start, Eind);
        openlist.add(Current);
        boolean bestaancheck = false;
        double G = 0;
        double Laagste = 0;
        Terrain LaagsteTerrain = null;
        Richting[] bewegingen = null;
        Terrain Parent;
        Coordinaat tijdelijkCoordinaat = null;
        double GscoreTotaal = 0;
        double Gscore;
        double GscoreTijdelijk = 0;
        Terrain mogelijk = null;
        ArrayList<Richting> tijdelijkeRichting = new ArrayList<>();
        boolean bestaancheckopen = false;
        boolean bestaancheckclosed = false;

        while (!openlist.isEmpty()) {
            System.out.println(Current.getCoordinaat());
            closedlist.add(Current);
            openlist.remove(Current);

            if (Current.getCoordinaat().equals(Eind)) {
                break;
            }

            Richting[] mogelijkeRichtingen = kaart.getTerreinOp(Current.getCoordinaat()).getMogelijkeRichtingen();
            for (Richting r : mogelijkeRichtingen) {
                tijdelijkCoordinaat = Current.getCoordinaat().naar(r);
                if (!openlist.isEmpty()) {
                    for (Terrain c : openlist) {
                        if (!c.getCoordinaat().equals(tijdelijkCoordinaat)) {
                            bestaancheckopen = true;
                        } else {
                            bestaancheckopen = false;
                            if (c.getGscore() < GscoreTotaal) {
                                c.setParent(Current);
                                c.setGscore(GscoreTijdelijk);
                            }
                            break;
                        }
                    }
                } else {
                    bestaancheckopen = true;
                }
                for (Terrain c : closedlist) {
                    if (!c.getCoordinaat().equals(tijdelijkCoordinaat)) {
                        bestaancheckclosed = true;
                    } else {
                        bestaancheckclosed = false;
                        break;
                    }
                }
                if (bestaancheckopen && bestaancheckclosed) {
                    Gscore = kaart.getTerreinOp(Eind).getTerreinType().getBewegingspunten();
                    GscoreTijdelijk = Gscore + GscoreTotaal;
                    mogelijk = new Terrain(GscoreTijdelijk, tijdelijkCoordinaat, Eind, Current);
                    openlist.add(mogelijk);
                }
                bestaancheckopen = false;
                bestaancheckclosed = false;

            }
            for (Terrain c : openlist) 
            {
                if (Laagste == 0) 
                {
                    Laagste = c.getFscore();
                    LaagsteTerrain = c;
                } else if (Laagste > c.getFscore()) {
                    Laagste = c.getFscore();
                    LaagsteTerrain = c;
                } else {
                }
            }
            Current = LaagsteTerrain;
            Laagste = 0;
        }
        for (int i = 0; i < closedlist.size(); i++) {
            Parent = Current.getParent();
            int dx = Current.getCoordinaat().getX() - Parent.getCoordinaat().getX();
            int dy = Current.getCoordinaat().getY() - Parent.getCoordinaat().getY();
            if (dx == 0) {
                if (dy > 0) {
                    tijdelijkeRichting.add(Richting.ZUID);
                } else if (dy < 0) {
                    tijdelijkeRichting.add(Richting.NOORD);
                }
            } else if (dy == 0) {
                if (dx > 0) {
                    tijdelijkeRichting.add(Richting.OOST);
                } else if (dx < 0) {
                    tijdelijkeRichting.add(Richting.WEST);
                }
            }
            Current = Parent;
            if (Current.getCoordinaat().equals(Start)) {
                break;
            }
        }
        int i = 0;
        int richtingSize = tijdelijkeRichting.size();
        bewegingen = new Richting[richtingSize];
        for (Richting r : tijdelijkeRichting) {

            bewegingen[i] = r;
            i++;

        }
        for (int j = 0; j < bewegingen.length / 2; j++) {
            Richting temp = bewegingen[j];
            bewegingen[j] = bewegingen[bewegingen.length - j - 1];
            bewegingen[bewegingen.length - j - 1] = temp;
        }
        PadImpl pad = new PadImpl(bewegingen, Start, kaart);
        debug.debugPad(kaart, Start, pad);
        return pad;

    }

    @Override
    public void setDebugger(Debugger dbgr) {
        this.debug = dbgr;
    }

}
