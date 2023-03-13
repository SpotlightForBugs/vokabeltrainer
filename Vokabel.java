public class Vokabel {
  private String deutsch;
  private String englisch;
  private boolean bekannt;
  private boolean falsch;
  private boolean neu;

  public Vokabel(String deutsch, String englisch) {
    this.deutsch = deutsch;
    this.englisch = englisch;
    bekannt = false;
    falsch = false;
    neu = true;
  }

  public String getDeutsch() {
    return deutsch;
  }

  public String getEnglisch() {
    return englisch;
  }

  public boolean istBekannt() {
    return bekannt;
  }

  public void setBekannt(boolean bekannt) {
    this.bekannt = bekannt;
    falsch = false;
    neu = false;
  }

  public boolean istFalsch() {
    return falsch;
  }

  public void setFalsch(boolean falsch) {
    this.falsch = falsch;
    bekannt = false;
    neu = false;
  }

  public boolean istNeu() {
    return neu;
  }

  public void setNeu(boolean neu) {
    this.neu = neu;
    bekannt = false;
    falsch = false;
  }

  public void setDeutsch(String deutsch) {
    this.deutsch = deutsch;
  }

  public void setEnglisch(String englisch) {
    this.englisch = englisch;
  }

  public String toString() {
    return deutsch + " -> " + englisch;
  }
}
