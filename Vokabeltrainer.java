import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Vokabeltrainer {
    private List<Vokabel> alleVokabeln;
    private List<Vokabel> bekannteVokabeln;
    private List<Vokabel> falscheVokabeln;
    private List<Vokabel> neueVokabeln;
    private File datei;

    public Vokabeltrainer() {
        alleVokabeln = new List<Vokabel>();
        bekannteVokabeln = new List<Vokabel>();
        falscheVokabeln = new List<Vokabel>();
        neueVokabeln = new List<Vokabel>();
    }

    public void ladeVokabeln(String dateiname) {
        alleVokabeln = new List<Vokabel>();
        bekannteVokabeln = new List<Vokabel>();
        falscheVokabeln = new List<Vokabel>();
        neueVokabeln = new List<Vokabel>();
        datei = new File(dateiname);
        try {
            Scanner scanner = new Scanner(datei);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    Vokabel v = new Vokabel(parts[0], parts[1]);
                    alleVokabeln.add(v);
                    neueVokabeln.add(v);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fehler beim Lesen der Datei " + dateiname + ": " + e.getMessage());
        }
    }

    public List<Vokabel> getAlleVokabeln() {
        return alleVokabeln;
    }

    public List<Vokabel> getBekannteVokabeln() {
        return bekannteVokabeln;
    }

    public List<Vokabel> getFalscheVokabeln() {
        return falscheVokabeln;
    }

    public List<Vokabel> getNeueVokabeln() {
        return neueVokabeln;
    }

    public void beantworteVokabel(Vokabel vokabel, boolean richtigBeantwortet) {
        if (richtigBeantwortet) {
            neueVokabeln.remove(vokabel);
            if (!bekannteVokabeln.contains(vokabel)) {
                bekannteVokabeln.add(vokabel);
            }
        } else {
            if (!falscheVokabeln.contains(vokabel)) {
                falscheVokabeln.add(vokabel);
            }
        }
    }

    public void speichereVokabeln() {
        try {
            FileWriter writer = new FileWriter(datei);
            for (Vokabel v : alleVokabeln) {
                writer.write(v.getDeutsch() + ";" + v.getEnglisch() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei " + datei.getName() + ": " + e.getMessage());
        }
    }
}
