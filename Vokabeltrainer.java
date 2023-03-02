import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Vokabeltrainer {
    private List<Vokabel> alleVokabeln;
    private List<Vokabel> bekannteVokabeln;
    private List<Vokabel> falscheVokabeln;
    private List<Vokabel> neueVokabeln;
    private boolean deNachEn;

    public Vokabeltrainer(String dateiname) {
        alleVokabeln = new ArrayList<Vokabel>();
        bekannteVokabeln = new ArrayList<Vokabel>();
        falscheVokabeln = new ArrayList<Vokabel>();
        neueVokabeln = new ArrayList<Vokabel>();
        deNachEn = true;
        try (BufferedReader br = new BufferedReader(new FileReader(dateiname))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    Vokabel v = new Vokabel(parts[0].trim(), parts[1].trim());
                    alleVokabeln.add(v);
                    neueVokabeln.add(v);
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
    }

    public void setDeNachEn(boolean deNachEn) {
        this.deNachEn = deNachEn;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Bitte wählen Sie einen Modus:");
            System.out.println("1 - Alle Vokabeln abfragen");
            System.out.println("2 - Bekannte Vokabeln abfragen");
            System.out.println("3 - Falsche Vokabeln abfragen");
            System.out.println("4 - Neue Vokabeln abfragen");
            System.out.println("5 - Richtung umkehren");
            System.out.println("0 - Beenden");
            int modus = scanner.nextInt();
            if (modus == 0) {
                break;
            }
            List<Vokabel> vokabeln;
            switch (modus) {
                case 1:
                    vokabeln = alleVokabeln;
                    break;
                case 2:
                    vokabeln = bekannteVokabeln;
                    break;
                case 3:
                    vokabeln = falscheVokabeln;
                    break;
                case 4:
                    vokabeln = neueVokabeln;
                    break;
                default:
                    System.out.println("Ungültiger Modus.");
                    continue;
            }
            if (vokabeln.isEmpty()) {
                System.out.println("Keine Vokabeln vorhanden.");
                continue;
            }
            System.out.println("Bitte geben Sie 'start' ein, um die Abfrage zu beginnen.");
            scanner.nextLine();
            String input = scanner.nextLine().trim();
            if (!input.equalsIgnoreCase("start")) {
                System.out.println("Ungültige Eingabe.");
                continue;
            }
            Collections.shuffle(vokabeln);
            int numCorrect = 0;
            for (Vokabel v : vokabeln) {
                System.out.println("Was ist die Übersetzung von "" + (deNachEn ? v.getDeutsch() : v.getEnglisch()) + ""?");
                String antwort = scanner.nextLine().trim();
                boolean korrekt = (deNachEn ? v.getEnglisch() : v.getDeutsch()).equalsIgnoreCase(antwort);
                if (korrekt) {
                    System.out.println("Richtig!");
                    numCorrect++;
                    if (modus == 4) {
                        neueVokabeln.remove(v);
                        bekannteVokabeln.add(v);
                        v.setBekannt(true);
                    }
                } else {
                    System.out.println("Falsch! Die korrekte Antwort wäre "" + (deNachEn ? v.getEnglisch() : v.getDeutsch()) + "" gewesen.");
                    if (modus == 4) {
                        neueVokabeln.remove(v);
                        falscheVokabeln.add(v);
                        v.setFalsch(true);
                    }
                }
            }
            System.out.println("Abfrage beendet. Sie haben " + numCorrect + " von " + vokabeln.size() + " Vokabeln korrekt beantwortet.");
        }
        scanner.close();
    }

    System.out.println("5 - Richtung umkehren");
System.out.println("0 - Beenden");
    int modus = scanner.nextInt();
if (modus == 0) {
        break;
    }
    List<Vokabel> vokabeln;
switch (modus) {
        case 1:
            vokabeln = alleVokabeln;
            break;
        case 2:
            vokabeln = bekannteVokabeln;
            break;
        case 3:
            vokabeln = falscheVokabeln;
            break;
        case 4:
            vokabeln = neueVokabeln;
            break;
        default:
            System.out.println("Ungültiger Modus.");
            continue;
    }
if (vokabeln.isEmpty()) {
        System.out.println("Keine Vokabeln vorhanden.");
        continue;
    }
System.out.println("Bitte geben Sie 'start' ein, um die Abfrage zu beginnen.");
scanner.nextLine();
    String input = scanner.nextLine().trim();
if (!input.equalsIgnoreCase("start")) {
        System.out.println("Ungültige Eingabe.");
        continue;
    }
Collections.shuffle(vokabeln);
    int numCorrect = 0;
for (Vokabel v : vokabeln) {
        System.out.println("Was ist die Übersetzung von "" + (deNachEn ? v.getDeutsch() : v.getEnglisch()) + ""?");
        String antwort = scanner.nextLine().trim();
        boolean korrekt = (deNachEn ? v.getEnglisch() : v.getDeutsch()).equalsIgnoreCase(antwort);
        if (korrekt) {
            System.out.println("Richtig!");
            numCorrect++;
            if (modus == 4) {
                neueVokabeln.remove(v);
                bekannteVokabeln.add(v);
                v.setBekannt(true);
            }
        } else {
            System.out.println("Falsch! Die korrekte Antwort wäre "" + (deNachEn ? v.getEnglisch() : v.getDeutsch()) + "" gewesen.");
            if (modus == 4) {
                neueVokabeln.remove(v);
                falscheVokabeln.add(v);
                v.setFalsch(true);
            }
        }
    }
System.out.println("Abfrage beendet. Sie haben " + numCorrect + " von " + vokabeln.size() + " Vokabeln korrekt beantwortet.");
}
scanner.close();
        }
