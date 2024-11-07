package se.biplob.game.model;

public class Resident extends Entity {
    public boolean fryingPanFound=false;

    public Resident(String name, int health, int damage) {
        super(name, health, damage);
    }
}