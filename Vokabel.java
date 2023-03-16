public class Vokabel {
  private String deutsch;
  private String englisch;
  private boolean bekannt;
  private boolean falsch;
  private boolean neu;

  /**
   * Erzeugt eine neue Vokabel.
   * @param deutsch ist der deutsche Begriff der Vokabel
   * @param englisch ist der englische Begriff der Vokabel
   */
  public Vokabel(String deutsch, String englisch) {
    this.deutsch = deutsch;
    this.englisch = englisch;
    bekannt = false;
    falsch = false;
    neu = true;
  }

  /**
   * Gibt den deutschen Begriff der Vokabel zurück.
   */

  public String getDeutsch() {
    return deutsch;
  }

    /**
     * Gibt den englischen Begriff der Vokabel zurück.
     */
  public String getEnglisch() {
    return englisch;
  }

  /**
   * Gibt zurück, ob die Vokabel bereits bekannt ist.
   */
  public boolean istBekannt() {
    return bekannt;
  }

  /**
   * Setzt den Status der Vokabel auf bekannt.
   */
  public void setBekannt(boolean bekannt) {
    this.bekannt = bekannt;
    falsch = false;
    neu = false;
  }

  /**
   * Gibt zurück, ob die Vokabel falsch beantwortet wurde.
   */

  public boolean istFalsch() {
    return falsch;
  }

  public void setFalsch(boolean falsch) {
    this.falsch = falsch;
    bekannt = false;
    neu = false;
  }

    /**
     * Gibt zurück, ob die Vokabel neu ist.
     */
  public boolean istNeu() {
    return neu;
  }

  /**
   * Setzt den Status der Vokabel auf neu.
   */

  public void setNeu(boolean neu) {
    this.neu = neu;
    bekannt = false;
    falsch = false;
  }



  /**
   * Setzt den deutschen Begriff der Vokabel.
   * @param deutsch ist der deutsche Begriff der Vokabel
   */

  public void setDeutsch(String deutsch) {
    this.deutsch = deutsch;
  }

    /**
     * Setzt den englischen Begriff der Vokabel.
     * @param englisch ist der englische Begriff der Vokabel
     */
  public void setEnglisch(String englisch) {
    this.englisch = englisch;
  }

    /**
     * Gibt die Vokabel als String zurück.
     * @return die Vokabel als String
     */
  public String toString() {
    return deutsch + " -> " + englisch;
  }
}
