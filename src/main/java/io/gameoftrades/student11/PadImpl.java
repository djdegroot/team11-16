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

/**
 *
 * @author djdeg
 */
public class PadImpl implements Pad 
{

    private Richting[] bewegingen;
    private Coordinaat beginpunt;
    private Coordinaat eindpunt;
    Kaart kaart;

    public PadImpl(Richting[] bewegingen, Coordinaat beginpunt, Kaart kaart) 
    {
        this.bewegingen = bewegingen;
        this.beginpunt = beginpunt;
        this.kaart = kaart;
    }

    @Override
    public int getTotaleTijd() 
    {

        Coordinaat coordinaat = beginpunt;
        double totaleTijd = 0;
        for (int i = 0; i < bewegingen.length; i++) 
        {

            coordinaat = coordinaat.naar(bewegingen[i]); // hij bekijkt alle bewegingspunten voor alle coordinaten die worden afgelopen
            totaleTijd = totaleTijd + kaart.getTerreinOp(coordinaat).getTerreinType().getBewegingspunten();
        }
        return (int) totaleTijd;
    }

    @Override
    public Richting[] getBewegingen() 
    {
        return bewegingen;      // geeft de array van richtingen terug
    }

    @Override
    public Pad omgekeerd() 
    {
        Coordinaat begin = volg(beginpunt);         //zoekt het eindpunt op en zet dat als beginpunt
        for (int i = 0; i < bewegingen.length / 2; i++) 
        {
            Richting temp = bewegingen[i];                              //draait de array volledig om
            bewegingen[i] = bewegingen[bewegingen.length - i - 1];
            bewegingen[bewegingen.length - i - 1] = temp;
        }
        for (int i = 0; i < bewegingen.length; i++) 
        {
            bewegingen[i] = bewegingen[i].omgekeerd();          //zorgt dat ook de richtingen worden omgedraaid
        }
        PadImpl pad = new PadImpl(bewegingen, begin, kaart);    // maakt nieuw pad aan
        return pad;
    }

    @Override
    public Coordinaat volg(Coordinaat Start) 
    {
        Coordinaat coordinaat = Start;                  //loopt het pad af met behulp van het start coordinaat
        for (Richting r : bewegingen) 
        {
            coordinaat = coordinaat.naar(r);

        }
        return coordinaat;
    }
}
