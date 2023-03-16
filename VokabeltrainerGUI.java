import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
// import Collections; // Import der java.util.Collections-Klasse
// Import der java.util.Collections-Klasse

public class VokabeltrainerGUI extends JFrame {

  /**
   * Der Vokabeltrainer, der von dieser GUI verwaltet wird.
   *
   * <p>Die GUI kann den Vokabeltrainer nur über die Methoden der Klasse Vokabeltrainer steuern.
   */
  private Vokabeltrainer trainer;
  private JTextArea ausgabe;
  private JComboBox<String> modusAuswahl;
  private JButton abfrageButton, bearbeitenButton;
  private JRadioButton deNachEnRadio, enNachDeRadio;

    /**
     * Erzeugt eine neue GUI für den übergebenen Vokabeltrainer.
     * @param trainer Der Vokabeltrainer, der von dieser GUI verwaltet wird.
     */

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

    /**
     * Event-Handler für den Abfrage-Button.
     *
     * <p>Der Abfrage-Button startet eine Abfrage mit den Vokabeln, die derzeit im Modus ausgewählt
     * sind.
     */

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
      vokabeln = Util.shuffle(vokabeln);
      int numCorrect = 0;
      vokabeln.toFirst();
      while (vokabeln.hasAccess()) {
        Vokabel v = vokabeln.getContent();
        vokabeln.next();

        String frage = deNachEn ? v.getDeutsch() : v.getEnglisch();
        String antwort = JOptionPane.showInputDialog(null, frage);
        if (antwort == null) {
          // Abbruch durch Benutzer
          ausgabe.setText("Abbruch durch Benutzer.");
          return;
        }
        if (antwort.equalsIgnoreCase(deNachEn ? v.getEnglisch() : v.getDeutsch())) {
          ausgabe.append("Richtig!\n");
          numCorrect++;
          trainer.beantworteVokabel(v, true);
        } else {
          ausgabe.append(
              "Falsch! Richtig wäre "
                  + (deNachEn ? v.getEnglisch() : v.getDeutsch())
                  + " gewesen.\n");
          trainer.beantworteVokabel(v, false);
        }
      }
      ausgabe.append(
          "\nErgebnis: " + numCorrect + "/" + Util.size(vokabeln) + " richtig beantwortet.");
    }
  }

    /**
         * Event-Handler für den Bearbeiten-Button.
         *
         * <p>Der Bearbeiten-Button öffnet ein Dialogfenster, in dem die Vokabeln bearbeitet werden können.
         */

  private class BearbeitenButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JDialog dialog = new JDialog(VokabeltrainerGUI.this, "Vokabeln bearbeiten", true);
      JPanel panel = new JPanel(new GridLayout(0, 2));
      List<Vokabel> alleVokabeln = trainer.getAlleVokabeln();

      alleVokabeln.toFirst();
      while (alleVokabeln.hasAccess()) {
        Vokabel v = alleVokabeln.getContent();
        panel.add(new JLabel(v.getDeutsch() + ":"));
        JTextField englischField = new JTextField(v.getEnglisch());
        panel.add(englischField);
        JButton speichernButton = new JButton("Speichern");
        speichernButton.addActionListener(new SpeichernButtonHandler(v, englischField));
        panel.add(speichernButton);
        panel.add(new JLabel(""));
        alleVokabeln.next();
      }

      JScrollPane scrollPane = new JScrollPane(panel);
      dialog.add(scrollPane);
      dialog.pack();
      dialog.setVisible(true);
    }
  }

    /**
     * Event-Handler für den Speichern-Button.
     *
     * <p>Der Speichern-Button speichert die Änderungen an einer Vokabel.
     */

  private class SpeichernButtonHandler implements ActionListener {
    private Vokabel vokabel;
    private JTextField englischField;

    public SpeichernButtonHandler(Vokabel vokabel, JTextField englischField) {
      this.vokabel = vokabel;
      this.englischField = englischField;
    }

    /**
     * Speichert die Änderungen an der Vokabel.
     *
     * @param e das ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
      String neuesEnglisch = englischField.getText();
      vokabel.setEnglisch(neuesEnglisch);
      trainer.speichereVokabeln();
    }
  }

    /**
     * Startet den Vokabeltrainer.
     *
     * @param args Kommandozeilenargumente
     */
  public static void main(String[] args) {
    Vokabeltrainer trainer = new Vokabeltrainer();
    trainer.ladeVokabeln("vok1.txt");
    new VokabeltrainerGUI(trainer);
  }
}
