package se.biplob.game.model;

public abstract class Entity {
    private String role;
    private int health;
    private int damage;

    public Entity(String role, int health, int damage) {
        this.role = role;
        this.health = health;
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage() {
        this.damage = this.getDamage() + 2;
    }

    public void punch(Entity target) {
        target.takeHit(this.damage);
    }

    public void takeHit(int damage) {
        this.health -= damage;
    }

    public boolean isConscious() {
        return health > 0;
    }

    @Override
    public String toString() {
        return this.role + " " + "health:" + this.health + " " + "Damage:" + this.damage;
    }
}