import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.*;
// import Collections; // Import der java.util.Collections-Klasse
// Import der java.util.Collections-Klasse

public class VokabeltrainerGUI extends JFrame {

  private Vokabeltrainer trainer;
  private JTextArea ausgabe;
  private JComboBox<String> modusAuswahl;
  private JButton abfrageButton, bearbeitenButton;
  private JRadioButton deNachEnRadio, enNachDeRadio;

  private static String path = "";

  public VokabeltrainerGUI(Vokabeltrainer trainer) {
    super("Vokabeltrainer");
    this.trainer = trainer;

    // Oberfläche erstellen
    JPanel obenPanel = new JPanel(new BorderLayout());
    modusAuswahl =
        new JComboBox<String>(
            new String[] {
              "Alle Vokabeln", "Bekannte Vokabeln", "Falsche Vokabeln", "Neue Vokabeln"
            });
    obenPanel.add(modusAuswahl, BorderLayout.CENTER);
    abfrageButton = new JButton("Abfrage starten");
    obenPanel.add(abfrageButton, BorderLayout.EAST);
    add(obenPanel, BorderLayout.NORTH);

    JPanel mittePanel = new JPanel(new BorderLayout());
    ausgabe = new JTextArea();
    ausgabe.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(ausgabe);
    mittePanel.add(scrollPane, BorderLayout.CENTER);
    JPanel radioPanel = new JPanel(new GridLayout(1, 2));
    deNachEnRadio = new JRadioButton("Deutsch -> Englisch");
    enNachDeRadio = new JRadioButton("Englisch -> Deutsch");
    ButtonGroup richtungsgruppe = new ButtonGroup();
    richtungsgruppe.add(deNachEnRadio);
    richtungsgruppe.add(enNachDeRadio);
    deNachEnRadio.setSelected(true);
    radioPanel.add(deNachEnRadio);
    radioPanel.add(enNachDeRadio);
    mittePanel.add(radioPanel, BorderLayout.SOUTH);
    add(mittePanel, BorderLayout.CENTER);

    bearbeitenButton = new JButton("Vokabeln bearbeiten");
    add(bearbeitenButton, BorderLayout.SOUTH);

    // Event-Handler hinzufügen
    abfrageButton.addActionListener(new AbfrageButtonHandler());
    bearbeitenButton.addActionListener(new BearbeitenButtonHandler());

    // Fenster konfigurieren
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);
    setVisible(true);
  }

  private class AbfrageButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int modusIndex = modusAuswahl.getSelectedIndex();
      boolean deNachEn = deNachEnRadio.isSelected();
      List<Vokabel> vokabeln;
      switch (modusIndex) {
        case 0:
          vokabeln = trainer.getAlleVokabeln();
          break;
        case 1:
          vokabeln = trainer.getBekannteVokabeln();
          break;
        case 2:
          vokabeln = trainer.getFalscheVokabeln();
          break;
        case 3:
          vokabeln = trainer.getNeueVokabeln();
          break;
        default:
          return;
      }
      if (vokabeln.isEmpty()) {
        ausgabe.setText("Keine Vokabeln vorhanden.");
        return;
      }
      ausgabe.setText("");
      // vokabeln = Util.shuffle(vokabeln);
      int numCorrect = 0;
      vokabeln.toFirst();
      while (vokabeln.hasAccess()) {
        Vokabel v = vokabeln.getContent();

        String frage = deNachEn ? v.getDeutsch() : v.getEnglisch();
        String antwort = JOptionPane.showInputDialog(null, frage);

        if (antwort == null) {
          // Abbruch durch Benutzer
          ausgabe.setText("Abbruch durch Benutzer.");
          trainer.speichereVokabeln();
          return;
        }

        if (antwort.equalsIgnoreCase(deNachEn ? v.getEnglisch() : v.getDeutsch())) {
          ausgabe.append("Richtig!\n");
          numCorrect++;
          trainer.beantworteVokabel(v, true);
        } else {
          ausgabe.append("Falsch! Richtig wäre " + (deNachEn ? v.getEnglisch() : v.getDeutsch()) + " gewesen.\n");
          trainer.beantworteVokabel(v, false);
        }

        vokabeln.next();
      }

      ausgabe.append("\nErgebnis: " + numCorrect + "/" + Util.size(vokabeln) + " richtig beantwortet.");

      // Speichern der Vokabeln nach Abfrage
      trainer.speichereVokabeln();
    }
  }

  private class BearbeitenButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        editGUI();
    }
  }

  private void editGUI() {
    // Dialog erstellen
    JDialog dialog = new JDialog(VokabeltrainerGUI.this, "Vokabeln bearbeiten", true);

    // Tabellen basierte Oberfläche erstellen
    JPanel panel = new JPanel(new GridLayout(0, 2));

    // Vokabeln auslesen
    List<Vokabel> alleVokabeln = trainer.getAlleVokabeln();

    // Vokabeln in die Oberfläche einfügen
    alleVokabeln.toFirst();
    while (alleVokabeln.hasAccess()) {

      Vokabel v = alleVokabeln.getContent();

      JTextField germanField = new JTextField(v.getDeutsch());
      panel.add(germanField);
      JTextField englischField = new JTextField(v.getEnglisch());
      panel.add(englischField);

      // Knöpfe für Speichern und Entfernen
      JButton speichernButton = new JButton("Speichern");
      speichernButton.addActionListener(
              new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  v.setDeutsch(germanField.getText());
                  v.setEnglisch(englischField.getText());
                  trainer.speichereVokabeln();
                }
              });

      JButton delButton = new JButton("Entfernen");
      delButton.addActionListener(
              new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  trainer.entferneVokabel(v);
                  trainer.speichereVokabeln();

                }
              });

      panel.add(speichernButton);
      panel.add(delButton);

      alleVokabeln.next();
    }

    // Spacer im panel zur visuellen Trennung
    panel.add(new JSeparator());
    panel.add(new JSeparator());

    // Knopf zum Hinzufügen von Vokabeln
    JButton addButton = new JButton("Neue Vokabel");
    addButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Erfrage deutsches und englisches Wort in einem Dialog
        String deutsch = JOptionPane.showInputDialog(null, "Deutsches Wort:").toLowerCase();
        String englisch = JOptionPane.showInputDialog(null, "Englisches Wort:").toLowerCase();

        // Vokabel hinzufügen
        trainer.fuegeHinzu(deutsch, englisch);
        trainer.speichereVokabeln();
      }
    });

    // Knopf zum Aktualisieren der Oberfläche
    JButton refreshButton = new JButton("Aktualisieren");
    refreshButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dialog.dispose();
        editGUI();
      }
    });

    // Knöpfe in die Oberfläche einfügen
    panel.add(addButton);
    panel.add(refreshButton);

    // Tabellen basierte Oberfläche in einen ScrollPane packen
    JScrollPane scrollPane = new JScrollPane(panel);

    // ScrollPane in den Dialog einfügen
    dialog.add(scrollPane);
    dialog.pack();
    dialog.setVisible(true);
  }

  public static void main(String[] args) {
    Vokabeltrainer trainer = new Vokabeltrainer(path);
    trainer.ladeVokabeln();
    new VokabeltrainerGUI(trainer);
  }
}
