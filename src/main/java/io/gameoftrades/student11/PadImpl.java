/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student11;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author djdeg
 */
public class PadImpl implements Pad{
private Richting[] bewegingen;
private Coordinaat beginpunt;
private Coordinaat eindpunt;
Kaart kaart;

    public PadImpl(Richting[] bewegingen, Coordinaat beginpunt, Kaart kaart){
        this.bewegingen = bewegingen;
        this.beginpunt = beginpunt;
        this.kaart = kaart;
    }

    @Override
    public int getTotaleTijd() {
        
        Coordinaat coordinaat = beginpunt;
        double totaleTijd = 0;
        for(int i = 0; i<bewegingen.length; i++){
            
            coordinaat = coordinaat.naar(bewegingen[i]);
            System.out.println(coordinaat.getX());
            System.out.println(coordinaat.getY());
            System.out.println(kaart.getTerreinOp(coordinaat).getTerreinType().getLetter());
           totaleTijd = totaleTijd + kaart.getTerreinOp(coordinaat).getTerreinType().getBewegingspunten();//fout zit hier ergens ik kan hem niet vinden nullpointer
            System.out.println(totaleTijd);
        }
        return (int) totaleTijd;
    }

    @Override
    public Richting[] getBewegingen() {
        return bewegingen;
    }

    @Override
    public Pad omgekeerd() {
        Coordinaat begin = volg(beginpunt);
        for(int i = 0; i < bewegingen.length / 2; i++)
        {
        Richting temp = bewegingen[i];
        bewegingen[i] = bewegingen[bewegingen.length - i - 1];
        bewegingen[bewegingen.length - i - 1] = temp;
        }
        PadImpl pad = new PadImpl(bewegingen, begin, kaart);
        return pad;
    }

    @Override
    public Coordinaat volg(Coordinaat Start) {
        Coordinaat coordinaat =  Start;
        for(Richting r : bewegingen){
            coordinaat = coordinaat.naar(r);
            
        }
        return coordinaat;
    }
    }
    

