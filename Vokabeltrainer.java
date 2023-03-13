import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Vokabeltrainer {

    private String path;
    private List<Vokabel> alleVokabeln;
    private List<Vokabel> bekannteVokabeln;
    private List<Vokabel> falscheVokabeln;
    private List<Vokabel> neueVokabeln;
    private File datei;
    private File falsch;
    private File neu;

    public Vokabeltrainer(String path) {

        this.path = path;

        alleVokabeln = new List<Vokabel>();
        bekannteVokabeln = new List<Vokabel>();
        falscheVokabeln = new List<Vokabel>();
        neueVokabeln = new List<Vokabel>();

        datei = new File(path+"Alle.txt");
        neu = new File(path+"Neu.txt");
        falsch = new File(path+"Falsch.txt");
    }

    public void ladeVokabeln() {

        alleVokabeln = new List<Vokabel>();
        bekannteVokabeln = new List<Vokabel>();
        falscheVokabeln = new List<Vokabel>();
        neueVokabeln = new List<Vokabel>();

        try {
            Scanner scanner = new Scanner(datei);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    Vokabel v = new Vokabel(parts[0], parts[1]);
                    alleVokabeln.append(v);
                }
            }
            scanner.close();

            Scanner scanner2 = new Scanner(neu);
            while (scanner2.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    Vokabel v = new Vokabel(parts[0], parts[1]);
                    neueVokabeln.append(v);
                }
            }
            scanner2.close();

            Scanner scanner3 = new Scanner(falsch);
            while (scanner3.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    Vokabel v = new Vokabel(parts[0], parts[1]);
                    falscheVokabeln.append(v);
                }
            }
            scanner3.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fehler beim Lesen der Datei " + path + ": " + e.getMessage());
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

            neueVokabeln.toFirst();
            while (neueVokabeln.hasAccess()) {
                if (neueVokabeln.getContent() == vokabel) {
                    neueVokabeln.remove();
                }
                
                neueVokabeln.next();
            }
            
            boolean contains = false;

            bekannteVokabeln.toFirst();
            while (bekannteVokabeln.hasAccess()) {
                if (bekannteVokabeln.getContent() == vokabel) contains = true;
                bekannteVokabeln.next();
            }
            
            if (!(contains)) bekannteVokabeln.append(vokabel);
            
        } 
        else {
            
            boolean contains = false;
            
            while (falscheVokabeln.hasAccess()) {
                if (falscheVokabeln.getContent() == vokabel) contains = true;
                falscheVokabeln.next();
            }
            
            if (!(contains)) falscheVokabeln.append(vokabel);
            
        }
    }

    public void speichereVokabeln() {
        try {
            FileWriter writer = new FileWriter(datei);

            alleVokabeln.toFirst();
            while (alleVokabeln.hasAccess()) {
                Vokabel v = alleVokabeln.getContent();
                writer.write(v.getDeutsch() + ";" + v.getEnglisch() + "\n");
                alleVokabeln.next();
            }

            writer.close();


            FileWriter writer1 = new FileWriter(neu);

            neueVokabeln.toFirst();
            while (neueVokabeln.hasAccess()) {
                Vokabel v = neueVokabeln.getContent();
                writer1.write(v.getDeutsch() + ";" + v.getEnglisch() + "\n");
                neueVokabeln.next();
            }

            writer1.close();


            FileWriter writer2 = new FileWriter(falsch);

            falscheVokabeln.toFirst();
            while (falscheVokabeln.hasAccess()) {
                Vokabel v = falscheVokabeln.getContent();
                writer2.write(v.getDeutsch() + ";" + v.getEnglisch() + "\n");
                falscheVokabeln.next();
            }
            
            writer2.close();

        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei " + datei.getName() + ": " + e.getMessage());
        }
    }
}
