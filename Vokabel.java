public class Vokabel {
  private String deutsch;
  private String englisch;

  public Vokabel(String deutsch, String englisch) {
    this.deutsch = deutsch;
    this.englisch = englisch;
  }

  public String getDeutsch() {
    return deutsch;
  }

  public String getEnglisch() {
    return englisch;
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
