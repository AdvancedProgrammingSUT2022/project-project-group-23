package database;

import model.Resource;

import java.util.ArrayList;

public class ResourceDatabase {
    private static ArrayList<Resource> resources = new ArrayList<>();
    static {
        resources.add(new Resource("Banana", 0, 1, 0, "Plantation",null));
        resources.add(new Resource("Cattle", 0, 1, 0, "Pasture",null));
        resources.add(new Resource("Deer", 0, 1, 0, "Camp",null));
        resources.add(new Resource("Sheep", 0, 2, 0, "Pasture",null));
        resources.add(new Resource("Wheat", 0, 1, 0, "Farm",null));
        resources.add(new Resource("Coal", 0, 0, 1, "Mine","Scientific Theory"));
        resources.add(new Resource("Horses", 0, 0, 1, "Pasture","Animal Husbandry"));
        resources.add(new Resource("Iron", 0, 0, 1, "Mine","Iron Working"));
        resources.add(new Resource("Cotton", 2, 0, 0, "Plantation",null));
        resources.add(new Resource("Dyes", 2, 0, 0, "Plantation",null));
        resources.add(new Resource("Furs", 2, 0, 0, "Camp",null));
        resources.add(new Resource("Gems", 3, 0, 0, "Mine",null));
        resources.add(new Resource("Gold", 2, 0, 0, "Mine",null));
        resources.add(new Resource("Incense", 2, 0, 0, "Plantation",null));
        resources.add(new Resource("Ivory", 2, 0, 0, "Camp",null));
        resources.add(new Resource("Marble", 2, 0, 0, "Quarry",null));
        resources.add(new Resource("Silk", 2, 0, 0, "Plantation",null));
        resources.add(new Resource("Silver", 2, 0, 0, "Mine",null));
        resources.add(new Resource("Sugar", 2, 0, 0, "Plantation",null));
    }

    public static ArrayList<Resource> getResources() {
        return resources;
    }
}