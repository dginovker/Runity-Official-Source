package io.battlerune.game.world.entity.combat.attack.listener.other.prayer.defence;

import io.battlerune.game.world.entity.combat.attack.listener.SimplifiedListener;
import io.battlerune.game.world.entity.mob.Mob;

public class ThickSkinListener extends SimplifiedListener<Mob> {

	@Override
	public int modifyDefenceLevel(Mob attacker, Mob defender, int damage) {
		return damage * 21 / 20;
	}

}
