package plugin.command.impl.donator;

import io.battlerune.content.command.Command;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.mob.player.PlayerRight;
import io.battlerune.game.world.position.Area;

/**
 * @author Adam_#6723
 */

public class DonatorBankCommand implements Command {

	@Override
	public void execute(Player player, String command, String[] parts) {
		if (Area.inWilderness(player) && (!PlayerRight.isDeveloper(player))) {
			player.message("You cannot open the bank in the wilderness.");
			return;
		} 
		if(player.getCombat().inCombat()) {
			player.message("You cannot open the bank whilst in combat.");
			return;
		}
			player.bank.open();
	}

	@Override
	public boolean canUse(Player player) {
		return (PlayerRight.isDonator(player) || PlayerRight.isKing(player) || PlayerRight.isSupreme(player) || PlayerRight.isExtreme(player)
				|| PlayerRight.isElite(player) || PlayerRight.isDeveloper(player));

	}

}
