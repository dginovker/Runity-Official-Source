package io.battlerune.content.bot.objective;

import io.battlerune.content.bot.PlayerBot;
import io.battlerune.content.bot.objective.impl.BankObjective;
import io.battlerune.content.bot.objective.impl.CombatObjective;
import io.battlerune.content.bot.objective.impl.RestockObjective;
import io.battlerune.content.bot.objective.impl.WildernessDitchObjective;
import io.battlerune.content.bot.objective.impl.WildernessWalkObjective;

public enum BotObjective implements BotObjectiveListener {
	WALK_IN_WILDERNESS(new WildernessWalkObjective()), WALK_TO_DITCH(new WildernessDitchObjective()),
	WALK_TO_BANK(new BankObjective()), RESTOCK(new RestockObjective()), COMBAT(new CombatObjective());

	private final BotObjectiveListener listener;

	BotObjective(BotObjectiveListener listener) {
		this.listener = listener;
	}

	@Override
	public void init(PlayerBot bot) {
		listener.init(bot);
	}

	@Override
	public void finish(PlayerBot bot) {
		listener.finish(bot);
	}

}
