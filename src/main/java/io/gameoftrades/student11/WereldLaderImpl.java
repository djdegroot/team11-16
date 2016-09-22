package io.gameoftrades.student11;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WereldLaderImpl implements WereldLader {

    @Override
    public Wereld laad(String resource) {
        //
        // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
        //
        // Kijk in src/test/resources voor voorbeeld kaarten.
        //
        // TODO Laad de wereld!
        //
        URL tempFile = this.getClass().getResource(resource);
        File file = null;
        try {
            file = new File(tempFile.toURI());
        } catch (Exception e) {

        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WereldLaderImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String input = null;
        input = scanner.nextLine().trim();
        int x;
        int y;

        String[] list = input.split(",");

        x = Integer.parseInt(list[0]);
        y = Integer.parseInt(list[1]);
        Kaart kaart = new Kaart(x, y);
        for (int i = 0; i < y; i++) {
            input = scanner.nextLine().trim();
            if (input.length() < x){
                throw new java.lang.IllegalArgumentException();
            }
            for (int l = 0; l < x; l++) {
                String type;
                TerreinType typeWhole = null;
                type = input.substring(l, l + 1);
                switch (type) {
                    case "Z":
                        typeWhole = TerreinType.ZEE;
                        break;
                    case "G":
                        typeWhole = TerreinType.GRASLAND;
                        break;
                    case "B":
                        typeWhole = TerreinType.BOS;
                        break;
                    case "R":
                        typeWhole = TerreinType.BERG;
                        break;
                    case "S":
                        typeWhole = TerreinType.STAD;
                        break;
                }
                Terrein t = new Terrein(kaart, Coordinaat.op(l, i), typeWhole);

            }
        }
        input = scanner.nextLine().trim();
        int aantalSteden;
        aantalSteden = Integer.parseInt(input);
        String[] StedenSplitList;
        int StadX;
        int StadY;
        String StadNaam;
        List<Stad> StedenList = new ArrayList<>();
        for (int i = 0; i < aantalSteden; i++) {
            input = scanner.nextLine().trim();
            System.out.println(input);
            StedenSplitList = input.split(",");
            StadX = Integer.parseInt(StedenSplitList[0]);
            StadY = Integer.parseInt(StedenSplitList[1]);
            StadNaam = StedenSplitList[2];
            if (kaart.getTerreinOp(Coordinaat.op(StadX, StadY)).getTerreinType().equals(TerreinType.STAD)){
                throw new java.lang.IllegalArgumentException();
            }
            Stad t = new Stad(Coordinaat.op(StadX, StadY), StadNaam);
            StedenList.add(t);
            System.out.println(t);
        }

        input = scanner.nextLine().trim();
        int aantalHandel;
        aantalHandel = Integer.parseInt(input);

        String[] HandelSplitList;
        HandelType handel = null;
        Handelswaar handelwaar;
        int prijs;
        List<Handel> HandelList = new ArrayList<>();
        for (int i = 0; i < aantalHandel; i++) {
            String handelTypeSwitch;
            input = scanner.nextLine().trim();
            System.out.println(input);
            HandelSplitList = input.split(",");
            StadNaam = HandelSplitList[0];
            Stad t = null;
            for (Stad temp : StedenList) {
                if (temp.getNaam().equals(HandelSplitList[0])) {
                    t = temp;
                    break;
                }
            }

            handelTypeSwitch = HandelSplitList[1];
            switch (handelTypeSwitch) {
                case "BIEDT":
                    handel = HandelType.BIEDT;
                    break;
                case "VRAAGT":
                    handel = HandelType.VRAAGT;
                    break;
            }
            handelwaar = new Handelswaar(HandelSplitList[2]);
            prijs = Integer.parseInt(HandelSplitList[3]);
            Handel h = new Handel(t, handel, handelwaar, prijs);
            HandelList.add(h);
            System.out.println(h);
        }
        Markt m = new Markt(HandelList);
        Wereld wereld = new Wereld(kaart, StedenList, m);
        return wereld;
    }

}
