package io.battlerune.game.world.entity.combat.attack.listener.npc;

import static io.battlerune.game.world.entity.combat.projectile.CombatProjectile.getDefinition;

import io.battlerune.game.Animation;
import io.battlerune.game.UpdatePriority;
import io.battlerune.game.world.entity.combat.CombatUtil;
import io.battlerune.game.world.entity.combat.attack.FightType;
import io.battlerune.game.world.entity.combat.attack.listener.NpcCombatListenerSignature;
import io.battlerune.game.world.entity.combat.attack.listener.SimplifiedListener;
import io.battlerune.game.world.entity.combat.hit.CombatHit;
import io.battlerune.game.world.entity.combat.hit.Hit;
import io.battlerune.game.world.entity.combat.strategy.npc.NpcMeleeStrategy;
import io.battlerune.game.world.entity.combat.strategy.npc.NpcRangedStrategy;
import io.battlerune.game.world.entity.mob.Mob;
import io.battlerune.game.world.entity.mob.npc.Npc;
import io.battlerune.game.world.position.Area;
import io.battlerune.util.RandomUtils;
import io.battlerune.util.Utility;

/**
 * @author Daniel
 */
@NpcCombatListenerSignature(npcs = { 2215 })
public class GeneralGraardor extends SimplifiedListener<Npc> {
	private static MeleeAttack MELEE;
	private static RangedAttack RANGED;

	private static final String[] SHOUTS = { "Brargh!", "Split their skulls!", "All glory to Bandos!", "GRRRAAAAAR!",
			"CHAAARGE!", "Crush them underfoot!", "Death to our enemies!", "Break their bones!",
			"For the glory of the Big High War God!", "We feast on the bones of our enemies tonight!" };

	static {
		try {
			MELEE = new MeleeAttack();
			RANGED = new RangedAttack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Npc attacker, Mob defender, Hit[] hits) {
		if (attacker.getStrategy().equals(NpcMeleeStrategy.get())) {
			attacker.setStrategy(MELEE);
		}

		if (RandomUtils.success(0.35)) {
			attacker.speak(Utility.randomElement(SHOUTS));
		}
	}

	private static class MeleeAttack extends NpcMeleeStrategy {
		@Override
		public Animation getAttackAnimation(Npc attacker, Mob defender) {
			return new Animation(7018, UpdatePriority.HIGH);
		}

		@Override
		public void finishOutgoing(Npc attacker, Mob defender) {
			if (RandomUtils.success(0.40)) {
				attacker.setStrategy(RANGED);
			}
		}
	}

	private static class RangedAttack extends NpcRangedStrategy {
		RangedAttack() {
			super(getDefinition("Graardor Ranged"));
		}

		@Override
		public Animation getAttackAnimation(Npc attacker, Mob defender) {
			return new Animation(7021, UpdatePriority.HIGH);
		}

		@Override
		public CombatHit[] getHits(Npc attacker, Mob defender) {
			return new CombatHit[] { nextRangedHit(attacker, defender, 35) };
		}

		@Override
		public void hitsplat(Npc attacker, Mob defender, Hit hit) {
			super.hitsplat(attacker, defender, hit);

			CombatUtil.areaAction(attacker, 64, 18, mob -> {
				if (mob.isPlayer() && Area.inGraardor(mob)) {
					mob.damage(nextRangedHit(attacker, defender, 35));
				}
			});
		}

		@Override
		public void finishOutgoing(Npc attacker, Mob defender) {
			if (attacker.getStrategy() != MELEE) {
				attacker.setStrategy(MELEE);
			}
		}

		@Override
		public int getAttackDistance(Npc attacker, FightType fightType) {
			return 32;
		}

	}

}
