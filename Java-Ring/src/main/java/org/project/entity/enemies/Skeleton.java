package org.project.entity.enemies;



public class Skeleton extends Enemy implements Entity {
    private boolean hasResurrected;
    
    public Skeleton(int health, int expReward) {
        super("Skeleton", health, new RustySword(), expReward);
        this.hasResurrected = false;
    }
    
    @Override
    public void takeDamage(int amount) {
        super.takeDamage(amount);
        if (!isAlive() && !hasResurrected) {
            resurrect();
        }
    }
    
    private void resurrect() {
        health = maxHealth / 2;
        hasResurrected = true;
        System.out.println("The skeleton reassembles itself!");
    }

    // @Override
    // Void string getDescription() {
    //     return "A reanimated skeleton with a rusty sword. (Resurrects once)";
    // }
}