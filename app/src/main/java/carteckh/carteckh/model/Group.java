package carteckh.carteckh.model;

import java.util.ArrayList;

/**
 * Created by Preet Saini on 22-01-2016.
 */
public class Group {
   private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Child> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Child> items) {
        Items = items;
    }

    private ArrayList<Child> Items;
}
