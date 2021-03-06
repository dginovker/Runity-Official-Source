package plugin.command.impl.player;

import java.util.List;

import io.battlerune.content.command.Command;
import io.battlerune.game.world.World;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.mob.player.PlayerRight;
import io.battlerune.net.packet.out.SendItemOnInterface;
import io.battlerune.net.packet.out.SendScrollbar;
import io.battlerune.net.packet.out.SendString;

/**
 * @author Adam_#6723
 */

public class StaffCommand implements Command {

	@Override
	public void execute(Player player, String command, String[] parts) {
		List<Player> staffs = World.getStaff();
		int length = staffs.size() < 10 ? 10 : staffs.size();

		player.send(new SendString("", 37113));
		player.send(new SendString("", 37107));
		player.send(new SendString("Runity Online Staff", 37103));
		player.send(new SendScrollbar(37110, length * 20));

		for (int index = 0, string = 37112; index < length; index++, string++) {
			if (index < staffs.size()) {
				Player staff = staffs.get(index);
				player.send(new SendString(PlayerRight.getCrown(staff) + " " + staff.getName() + "    (<col=255>"
						+ staff.right.getName() + "</col>)", string));
			} else {
				player.send(new SendString("", string));
			}
		}

		player.send(new SendItemOnInterface(37199));
		player.interfaceManager.open(37100);
		player.message("If you are in need of assistance do ::help");
	}

	@Override
	public boolean canUse(Player player) {
		return true;
	}

}
