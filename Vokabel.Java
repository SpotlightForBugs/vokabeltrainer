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

    @Override
    public String toString() {
        return deutsch + " - " + englisch;
    }
}
