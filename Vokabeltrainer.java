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
  private File neu;
  private File bekannt;
  private File falsch;

  private String path;

  public Vokabeltrainer(String path) {

    this.path = path;

    alleVokabeln = new List<Vokabel>();
    bekannteVokabeln = new List<Vokabel>();
    falscheVokabeln = new List<Vokabel>();
    neueVokabeln = new List<Vokabel>();
  }

  public void ladeVokabeln() {
    alleVokabeln = new List<Vokabel>();
    bekannteVokabeln = new List<Vokabel>();
    falscheVokabeln = new List<Vokabel>();
    neueVokabeln = new List<Vokabel>();

    datei = new File(path + "Alle.txt");
    neu = new File(path + "Neu.txt");
    bekannt = new File(path + "Bekannt.txt");
    falsch = new File(path + "Falsch.txt");

    try {
      Scanner alle = new Scanner(datei);
      while (alle.hasNextLine()) {
        String line = alle.nextLine();
        String[] parts = line.split(";");
        if (parts.length == 2) {
          Vokabel v = new Vokabel(parts[0], parts[1]);
          alleVokabeln.append(v);
        }
      }
      alle.close();
    } catch (FileNotFoundException e) {
      System.out.println("Fehler beim Lesen der Datei " + datei.getName() + ": " + e.getMessage());
    }

    try {
      Scanner neue = new Scanner(neu);
      while (neue.hasNextLine()) {
        String line = neue.nextLine();
        String[] parts = line.split(";");
        if (parts.length == 2) {
          Vokabel v = new Vokabel(parts[0], parts[1]);
          neueVokabeln.append(v);
        }
      }
      neue.close();
    } catch (FileNotFoundException e) {
      System.out.println("Fehler beim Lesen der Datei " + neu.getName() + ": " + e.getMessage());
    }

    try {
      Scanner bekannte = new Scanner(bekannt);
      while (bekannte.hasNextLine()) {
        String line = bekannte.nextLine();
        String[] parts = line.split(";");
        if (parts.length == 2) {
          Vokabel v = new Vokabel(parts[0], parts[1]);
          bekannteVokabeln.append(v);
        }
      }
      bekannte.close();
    } catch (FileNotFoundException e) {
      System.out.println(
          "Fehler beim Lesen der Datei " + bekannt.getName() + ": " + e.getMessage());
    }

    try {
      Scanner falsche = new Scanner(falsch);
      while (falsche.hasNextLine()) {
        String line = falsche.nextLine();
        String[] parts = line.split(";");
        if (parts.length == 2) {
          Vokabel v = new Vokabel(parts[0], parts[1]);
          falscheVokabeln.append(v);
        }
      }
      falsche.close();
    } catch (FileNotFoundException e) {
      System.out.println("Fehler beim Lesen der Datei " + falsch.getName() + ": " + e.getMessage());
    }
  }

  public List<Vokabel> getAlleVokabeln() {
    return alleVokabeln;
  }

  // Gibt einen Klon zurück, um Probleme mit Referenzen zu vermeiden
  public List<Vokabel> getBekannteVokabeln() {
    List<Vokabel> returnList = new List<Vokabel>();

    bekannteVokabeln.toFirst();
    while (bekannteVokabeln.hasAccess()) {
      returnList.append(bekannteVokabeln.getContent());
      bekannteVokabeln.next();
    }
    return returnList;
  }

  // Gibt einen Klon zurück, um Probleme mit Referenzen zu vermeiden
  public List<Vokabel> getFalscheVokabeln() {
    List<Vokabel> returnList = new List<Vokabel>();

    falscheVokabeln.toFirst();
    while (falscheVokabeln.hasAccess()) {
      returnList.append(falscheVokabeln.getContent());
      falscheVokabeln.next();
    }
    return returnList;
  }

  // Gibt einen Klon zurück, um Probleme mit Referenzen zu vermeiden
  public List<Vokabel> getNeueVokabeln() {
    List<Vokabel> returnList = new List<Vokabel>();

    neueVokabeln.toFirst();
    while (neueVokabeln.hasAccess()) {
      returnList.append(neueVokabeln.getContent());
      neueVokabeln.next();
    }
    return returnList;
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

      // Entferne Vokabel aus falscher Liste
      falscheVokabeln.toFirst();
      while (falscheVokabeln.hasAccess()) {
        if (falscheVokabeln.getContent() == vokabel) {
          falscheVokabeln.remove();
        }
        falscheVokabeln.next();
      }

    }
    else {
      // Füge Vokabel zu falschen Vokabeln hinzu
      boolean contains = false;

      while (falscheVokabeln.hasAccess()) {
        if (falscheVokabeln.getContent() == vokabel) contains = true;
        falscheVokabeln.next();
      }

      if (!(contains)) falscheVokabeln.append(vokabel);


      // Entferne Vokabel aus bekannter Liste
      bekannteVokabeln.toFirst();

      while (bekannteVokabeln.hasAccess()) {
        if (bekannteVokabeln.getContent() == vokabel) {
          bekannteVokabeln.remove();
        }
        bekannteVokabeln.next();
      }
    }
  }

  public void fuegeHinzu(String deutsch, String englisch) {
    Vokabel v = new Vokabel(deutsch, englisch);
    alleVokabeln.append(v);
    neueVokabeln.append(v);
  }

    public void entferneVokabel(Vokabel vokabel) {
      // Entferne Vokabel aus allen Listen
      alleVokabeln.toFirst();
      while (alleVokabeln.hasAccess()) {
        if (alleVokabeln.getContent() == vokabel) {
          alleVokabeln.remove();
        }
        alleVokabeln.next();
      }

      neueVokabeln.toFirst();
      while (neueVokabeln.hasAccess()) {
        if (neueVokabeln.getContent() == vokabel) {
          neueVokabeln.remove();
        }
        neueVokabeln.next();
      }

      bekannteVokabeln.toFirst();
      while (bekannteVokabeln.hasAccess()) {
        if (bekannteVokabeln.getContent() == vokabel) {
          bekannteVokabeln.remove();
        }
        bekannteVokabeln.next();
      }

      falscheVokabeln.toFirst();
      while (falscheVokabeln.hasAccess()) {
        if (falscheVokabeln.getContent() == vokabel) {
          falscheVokabeln.remove();
        }
        falscheVokabeln.next();
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
    } catch (IOException e) {
      System.out.println(
          "Fehler beim Schreiben der Datei " + datei.getName() + ": " + e.getMessage());
    }

    // Speichere bekannte Vokabeln
    try {
      FileWriter bekanntewriter = new FileWriter(bekannt);

      bekannteVokabeln.toFirst();
      while (bekannteVokabeln.hasAccess()) {
        Vokabel v = bekannteVokabeln.getContent();
        bekanntewriter.write(v.getDeutsch() + ";" + v.getEnglisch() + "\n");
        bekannteVokabeln.next();
      }

      bekanntewriter.close();
    } catch (IOException e) {
      System.out.println(
          "Fehler beim Schreiben der Datei " + bekannt.getName() + ": " + e.getMessage());
    }

    // Speichere neue Vokabeln
    try {
      FileWriter neuewriter = new FileWriter(neu);

      neueVokabeln.toFirst();
      while (neueVokabeln.hasAccess()) {
        Vokabel v = neueVokabeln.getContent();
        neuewriter.write(v.getDeutsch() + ";" + v.getEnglisch() + "\n");
        neueVokabeln.next();
      }
      neuewriter.close();
    } catch (IOException e) {
      System.out.println(
          "Fehler beim Schreiben der Datei " + neu.getName() + ": " + e.getMessage());
    }

    // Speichere falsche Vokabeln
    try {
      FileWriter falschwriter = new FileWriter(falsch);

      falscheVokabeln.toFirst();
      while (falscheVokabeln.hasAccess()) {
        Vokabel v = falscheVokabeln.getContent();
        falschwriter.write(v.getDeutsch() + ";" + v.getEnglisch() + "\n");
        falscheVokabeln.next();
      }
      falschwriter.close();
    } catch (IOException e) {
      System.out.println(
          "Fehler beim Schreiben der Datei " + falsch.getName() + ": " + e.getMessage());
    }
  }
}
