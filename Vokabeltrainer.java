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

  /**
   * Der Konstruktor erzeugt leere Listen für alle Vokabeln, bekannte Vokabeln, falsche Vokabeln und neue Vokabeln.
   */
  public Vokabeltrainer() {
    alleVokabeln = new List<Vokabel>();
    bekannteVokabeln = new List<Vokabel>();
    falscheVokabeln = new List<Vokabel>();
    neueVokabeln = new List<Vokabel>();
  }

  /**
   * Lädt die Vokabeln aus der Datei mit dem übergebenen Dateinamen. Die Datei muss im CSV-Format vorliegen, d.h. die Vokabeln müssen in der Form "deutsch;englisch" gespeichert sein.
   * @param dateiname
   */
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
          alleVokabeln.append(v);
          neueVokabeln.append(v);
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("Fehler beim Lesen der Datei " + dateiname + ": " + e.getMessage());
    }
  }


    /**
     * Gibt die Liste aller Vokabeln zurück.
     */
  public List<Vokabel> getAlleVokabeln() {
    return alleVokabeln;
  }

    /**
     * Gibt die Liste der bekannten Vokabeln zurück.
     */
  public List<Vokabel> getBekannteVokabeln() {
    return bekannteVokabeln;
  }

    /**
     * Gibt die Liste der falsch beantworteten Vokabeln zurück.
     */
  public List<Vokabel> getFalscheVokabeln() {
    return falscheVokabeln;
  }

        /**
        * Gibt die Liste der neuen Vokabeln zurück.
        */
  public List<Vokabel> getNeueVokabeln() {
    return neueVokabeln;
  }
  /**
   * Fügt eine neue Vokabel hinzu.
   * @param deutsch ist der deutsche Begriff der Vokabel
   * @param englisch ist der englische Begriff der Vokabel
   * Die neue Vokabel wird in die Liste aller Vokabeln eingefügt und in die Liste der neuen Vokabeln.
   */

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

    } else {

      boolean contains = false;

      while (falscheVokabeln.hasAccess()) {
        if (falscheVokabeln.getContent() == vokabel) contains = true;
        falscheVokabeln.next();
      }

      if (!(contains)) falscheVokabeln.append(vokabel);
    }
  }

    /**
     * Speichert die Vokabeln in die Datei, die beim Laden der Vokabeln verwendet wurde.
     */
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
    } catch (IOException e) {
      System.out.println(
          "Fehler beim Schreiben der Datei " + datei.getName() + ": " + e.getMessage());
    }
  }
}
