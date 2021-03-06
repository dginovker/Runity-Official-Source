package plugin.command.impl.player;

import io.battlerune.content.command.Command;
import io.battlerune.game.world.entity.mob.player.Player;

public class EventsCommand implements Command {

	@Override
	public void execute(Player player, String command, String[] parts) {
		player.sendMessage("The current active bonuses/events are listed below..");
		player.sendEventInfo();
	}

	@Override
	public boolean canUse(Player player) {
		return true;
	}

}
