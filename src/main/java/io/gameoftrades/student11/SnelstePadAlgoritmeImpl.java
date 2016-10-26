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
/**
 *
 * @author djdeg
 */
public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme, Debuggable 
{

    private Debugger debug = new DummyDebugger();

    @Override
    public Pad bereken(Kaart kaart, Coordinaat Start, Coordinaat Eind) 
    {
        ArrayList<Terrain> openlist = new ArrayList<>();
        ArrayList<Terrain> closedlist = new ArrayList<>();
        Terrain Current = new Terrain(0, Start, Eind);
        openlist.add(Current);
        double Laagste = 0;
        Terrain LaagsteTerrain = null;
        Richting[] bewegingen = null;
        Terrain Parent;
        Coordinaat tijdelijkCoordinaat = null;
        double Gscore;
        double GscoreTijdelijk = 0;
        Terrain mogelijk = null;
        ArrayList<Richting> tijdelijkeRichting = new ArrayList<>();
        boolean bestaancheckopen = false;
        boolean bestaancheckclosed = false;

        while (!openlist.isEmpty())         //zolang er iets in de openlist staat gaat het door, dus als er geen nodes meer zijn dan stopt het algoritme
        {
            closedlist.add(Current);    //de huidige toevoegen en verwijderen
            openlist.remove(Current);

            if (Current.getCoordinaat().equals(Eind)) //als current gelijk is aan het einde
            {
                break;
            }

            Richting[] mogelijkeRichtingen = kaart.getTerreinOp(Current.getCoordinaat()).getMogelijkeRichtingen(); //alle mogelijke richtingen ophalen
            for (Richting r : mogelijkeRichtingen) // de array list doorlopen en alle mogelijke richtingen uitzoeken
            {
                tijdelijkCoordinaat = Current.getCoordinaat().naar(r);//het coordinaat ophalen
                if (!openlist.isEmpty()) //voor het eerste coordinaat als de openlist leeg is omdat de start node al in de closedlist zit
                {
                    for (Terrain c : openlist) //kijken of een coordinaat al bestaat
                    {
                        if (!c.getCoordinaat().equals(tijdelijkCoordinaat)) 
                        {
                            bestaancheckopen = true;//geeft deze mee zodat er bekend is of het al bestaat
                        } 
                        else 
                        {
                            bestaancheckopen = false; 
                            if (c.getGscore() < (Current.getGscore() + kaart.getTerreinOp(c.getCoordinaat()).getTerreinType().getBewegingspunten())) 
                            {
                                c.setParent(Current);   //als het al bestaat wordt er gekeken of de gscore  beter is
                                c.setGscore(GscoreTijdelijk);
                            }
                            break;
                        }
                    }
                } 
                else        //deze is voor de eerste if statement omdat als de start in de closedlist zit hij wel al bestaat.
                {
                    bestaancheckopen = true;
                }
                for (Terrain c : closedlist)    //hetzelfde alleen met de closed list als hij al bestaat dan kan er geen nieuwe worden aangemaakt
                {
                    if (!c.getCoordinaat().equals(tijdelijkCoordinaat)) 
                    {
                        bestaancheckclosed = true;
                    } 
                    else 
                    {
                        bestaancheckclosed = false;
                        break;
                    }
                }
                if (bestaancheckopen && bestaancheckclosed)     //hier wordt gekeken of hij nog niet bestaat en gaat vervolgens een node(terrain aanmaken)
                {
                    Gscore = kaart.getTerreinOp(tijdelijkCoordinaat).getTerreinType().getBewegingspunten();     //gscore ophalen
                    GscoreTijdelijk = Current.getGscore() + Gscore; //totale gscore
                    mogelijk = new Terrain(GscoreTijdelijk, tijdelijkCoordinaat, Eind, Current); //de node aanmaken
                    openlist.add(mogelijk); //toevoegen aan openlist
                }
                bestaancheckopen = false;   //hier wordt alles weer op false gezet
                bestaancheckclosed = false;

            }
            for (Terrain c : openlist) //openlist doorlopen voor mostlikely
            {
                if (Laagste == 0) 
                {
                    Laagste = c.getFscore();    //laagste fscore opzoeken
                    LaagsteTerrain = c;
                } 
                else if (Laagste > c.getFscore()) 
                {
                    Laagste = c.getFscore();
                    LaagsteTerrain = c;
                } 
            }
            Current = LaagsteTerrain;   //current het laagste terrain aanmaken
            Laagste = 0;
        }
        for (int i = 0; i < closedlist.size(); i++)         //alle parents nalopen
        {
            Parent = Current.getParent();       //parent neerzetten als parent van het huidige vakje
            int dx = Current.getCoordinaat().getX() - Parent.getCoordinaat().getX();        //alle richtingen nalopen
            int dy = Current.getCoordinaat().getY() - Parent.getCoordinaat().getY(); // dit gedeelte is ergens anders uit het project vandaan gehaald maar gaf problemen tijdens het aanroepen
            if (dx == 0) 
            {
                if (dy > 0) 
                {
                    tijdelijkeRichting.add(Richting.ZUID);  //alle richtingen in een arraylist gooien
                } 
                else if (dy < 0) 
                {
                    tijdelijkeRichting.add(Richting.NOORD);
                }
            } else if (dy == 0) 
            {
                if (dx > 0) 
                {
                    tijdelijkeRichting.add(Richting.OOST);
                } 
                else if (dx < 0) 
                {
                    tijdelijkeRichting.add(Richting.WEST);
                }
            }
            Current = Parent;               //de parent neerzetten als current node
            if (Current.getCoordinaat().equals(Start))  //als current gelijk is als het eerste vakje dan stoppen
            {
                break;
            }
        }
        int i = 0; 
        int richtingSize = tijdelijkeRichting.size();
        bewegingen = new Richting[richtingSize];
        for (Richting r : tijdelijkeRichting)     //array aanmaken voor juiste oplevering
        {

            bewegingen[i] = r;//alle richtingen uit de arraylist in een array zetten
            i++;

        }
        for (int j = 0; j < bewegingen.length / 2; j++)     //array staat achterstevoren dus omdraaien
        {
            Richting temp = bewegingen[j];
            bewegingen[j] = bewegingen[bewegingen.length - j - 1];
            bewegingen[bewegingen.length - j - 1] = temp;
        }
        PadImpl pad = new PadImpl(bewegingen, Start, kaart);    //nieuw pad aanmaken een kaart startpunt en array bewegingen
        debug.debugPad(kaart, Start, pad);
        return pad;

    }

    @Override
    public void setDebugger(Debugger dbgr) 
    {
        this.debug = dbgr;
    }

}
