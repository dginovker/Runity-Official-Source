package io.battlerune.game.world.entity.combat.attack.listener.other.prayer.strength;

import io.battlerune.game.world.entity.combat.attack.listener.SimplifiedListener;
import io.battlerune.game.world.entity.mob.Mob;

public class SuperhumanStrengthListener extends SimplifiedListener<Mob> {

	@Override
	public int modifyStrengthLevel(Mob attacker, Mob defender, int damage) {
		return damage * 11 / 10;
	}

}
