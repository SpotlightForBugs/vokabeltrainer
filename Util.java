import java.util.Random;
import jdk.jfr.ContentType;

public class Util {



    /**
     * Gibt die Größe der Liste zurück.
     *
     * @param list Die Liste, deren Größe ermittelt werden soll.
     * @return Die Größe der Liste.
     */
  public static int size(List<Vokabel> list) {
    int counter = 0;
    list.toFirst();
    while (list.hasAccess()) {
      counter++;
      list.next();
    }
    return counter;
  }

  /**
   * Mischt die Vokabeln in der Liste.
   *
   * @param list Die Liste, die gemischt werden soll.
   * @return Die gemischte Liste.
   */
  public static List<Vokabel> shuffle(List<Vokabel> list) {
    Random random = new Random();
    int size = size(list);
    for (int i = 0; i < size; i++) {
      int randomIndex = random.nextInt(size);
      list.toFirst();
      for (int j = 0; j < randomIndex; j++) {
        list.next();
      }
      Vokabel temp = list.getContent();
      list.remove();
      list.toFirst();
      for (int j = 0; j < i; j++) {
        list.next();
      }
      list.insert(temp);
    }
    return list;
  }

    /**
     * Gibt zurück, ob die Liste den Inhalt enthält.
     *
     * @param list Die Liste, die durchsucht werden soll.
     * @param content Der Inhalt, der gesucht wird.
     * @return true, wenn die Liste den Inhalt enthält, sonst false.
     */
  public boolean contains(List<ContentType> list, ContentType content) {
    list.toFirst();
    while (list.hasAccess()) {
      if (list.getContent() == content) {
        return true;
      }
      list.next();
    }
    return false;
  }
}
