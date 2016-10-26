/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student11;

import io.gameoftrades.model.kaart.Coordinaat;

/**
 *
 * @author djdeg
 */
public class Terrain {
    private double Hscore;
    private double Gscore;
    private Coordinaat coordinaat;
    private Terrain parent;
    
    public Terrain(double Gscore, Coordinaat coordinaat, Coordinaat eind){
        this.Gscore = Gscore;
        this.Hscore = coordinaat.afstandTot(eind);
        this.coordinaat = coordinaat;
    }
    public Terrain(double Gscore, Coordinaat coordinaat, Coordinaat eind, Terrain parent){
        this.Gscore = Gscore;
        this.Hscore = coordinaat.afstandTot(eind);
        this.coordinaat = coordinaat;
        this.parent = parent;
    }
    public double getHscore(){
        return Hscore;
    }
    
    public double getGscore(){
        
        return Gscore;
    }
    
    public Coordinaat getCoordinaat(){
        return coordinaat;
    }
    
    public Terrain getParent(){
        return parent;
    }
    
    public double getFscore(){
        return Hscore + Gscore;
    }
    
    public void setHscore(Coordinaat eind){
        Hscore = coordinaat.afstandTot(eind);
    }
    
    public void setGscore(double Gscore){
        this.Gscore = Gscore;
    }
    
    public void setParent(Terrain parent){
        this.parent = parent;
    }
}
