package org.project.object.armors;

public class KnightArmor extends Armor {

	public KnightArmor() {
        super(15, 100);
    }

    @Override
    public void repair() {
        super.repair();
        System.out.println("Knight armor is fully repaired!");
    }

}