package io.battlerune.game.world.entity.combat.attack.listener.item;

import io.battlerune.game.Graphic;
import io.battlerune.game.UpdatePriority;
import io.battlerune.game.world.entity.combat.CombatType;
import io.battlerune.game.world.entity.combat.attack.listener.ItemCombatListenerSignature;
import io.battlerune.game.world.entity.combat.attack.listener.SimplifiedListener;
import io.battlerune.game.world.entity.combat.hit.Hit;
import io.battlerune.game.world.entity.mob.Mob;
import io.battlerune.game.world.entity.mob.prayer.Prayer;

/**
 * Handles the Default armour effects, which if steals 10% of the mob's life for
 * the player themselves.
 * 
 * @author Adam_#6723
 */
@ItemCombatListenerSignature(requireAll = false, items = { 13738, 13739, 13740, 13741,
		13742, 13743, 13744, 13745, 13746, 13747,
		13748, 13749, 16628, 16629, 16630, 16631,
		16647, 16648, 16649, 16650, 16651, 16653,
		16654, 16655, 16656, 19923, 16657, 16658,
		16659, 16660, 16661, 16662, 16663, 16664,
		16665, 16666, 16667, 16668, 16669, 13717,
		13718, 13719, 13720, 13721, 13722, 13723,
		13724, 13725, 13726, 13727, 13728, 13729,
		4062, 17161, 11642, 17153, 17154, 17155,
		17156, 17157, 17158, 17159, 17160, 17162,
		17163, 17164, 17165, 17166, 17167, 17168,
		17172, 17173, 17174, 17175, 17176, 17177,
		17178, 17179, 17180, 17181, 19914, 19917,
		19920, 13689, 20251, 20252, 13690, 13686,
		13687, 11609, 20690, 14581, 14582, 14583,
		14584, 14585, 14586, 14589, 13207, 13209,
		13210, 13212, 13213, 13214, 13661, 13662,
		13189, 10028, 21777, 13208, 22123, 21954,
		22099, 22078, 22301, 22304, 22307, 15011,
		3273, 3274, 3275, 15300, 15307, 10860,
		10861, 15301, 15302, 15303, 15304, 3929,
		15308, 15309, 15310, 13703, 13704, 13705,
		13695, 13692, 13693, 13696, 13697, 13698,
		13699, 13700, 13701, 13702, 22317, 22280,
		21225, 1, 20035, 20047, 20044, 20035, 20038
				  }) // TODO UNCOMMENT THIS ON RELEASE, ITS COMMENTED DUE TO THE STAARTUP TIME BEING
						// INCREASED DRASTICALLY.
public class DefaultCustomListener extends SimplifiedListener<Mob> {	
	
	
public int healingGraphic = 398; //1296

public void method(Mob attacker) {
	for (Prayer prayer : Prayer.values()) {
		prayer.setdrainRate(prayer.getDrainRate() / 2);
	}
}

@Override
public void hit(Mob attacker, Mob defender, Hit hit) {
	if (Math.random() > 0.25) {
		attacker.heal(hit.getDamage() / 5);
		attacker.graphic(new Graphic(healingGraphic, UpdatePriority.HIGH));
	}
}

@Override
public void block(Mob attacker, Mob defender, Hit hit, CombatType combatType) {
	method(attacker);
}
}