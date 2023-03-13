
import jdk.jfr.ContentType;

import java.util.Random;

public class Util {


   //the shuffle list method shuffles the List (file List.java)
    //the remove method removes the element at the current position (file List.java)
    //we dont have a size method in List.java, so we use the hasAccess method
    //to check if we are at the end of the list
    //we also have to go to the first element of the list, so we use next() toFirst()
    //we have to determine the size ourselves, so we use a counter



    public static int size(List<Vokabel> list) {
        int counter = 0;
        list.toFirst();
        while (list.hasAccess()) {
            counter++;
            list.next();
        }
        return counter;
    }

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
