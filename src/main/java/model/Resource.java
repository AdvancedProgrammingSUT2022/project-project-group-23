package model;

import java.util.ArrayList;

public class Resource {
    private String name;
    private int gold;
    private int food;
    private int production;

    private String neededImprovement;
    private String neededTechnology;

    public Resource(String name, int gold, int food, int production, String neededImprovement, String neededTechnology) {
        this.name = name;
        this.gold = gold;
        this.food = food;
        this.production = production;
        this.neededImprovement = neededImprovement;
        this.neededTechnology = neededTechnology;
    }

    public String getName () {
        return name;
    }

    public int getGold () {
        return gold;
    }

    public int getFood () {
        return food;
    }

    public int getProduction () {
        return production;
    }

    public void setGold (int gold) {
        this.gold = gold;
    }

    public void setFood (int food) {
        this.food = food;
    }

    public void setProduction (int production) {
        this.production = production;
    }
}
