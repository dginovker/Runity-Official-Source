package plugin.itemon.object;

import io.battlerune.game.event.impl.ItemOnObjectEvent;
import io.battlerune.game.plugin.PluginContext;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.items.Item;

public class UltimateIronmanPlugin extends PluginContext {

	@Override
	protected boolean itemOnObject(Player player, ItemOnObjectEvent event) {
		final Item used = event.getUsed();
		if (/*
			 * player.right.equals(PlayerRight.ULTIMATE_IRONMAN) &&
			 */event.getObject().getDefinition().name.equals("Bank booth")
				|| event.getObject().getDefinition().name.equals("")) {
			if (!used.isTradeable()) {
				player.dialogueFactory.sendStatement("This item can not be noted!").execute();
				return true;
			}

			int free = player.inventory.getFreeSlots();
			int amount = player.inventory.computeAmountForId(used.getId());

			if (used.isNoted()) {
				if (free == 0) {
					player.dialogueFactory.sendStatement("You have no free inventory spaces to do this!").execute();
					return true;
				}

				if (amount > free)
					amount = free;

				player.inventory.remove(used.getId(), amount);
				player.inventory.add(new Item(used.getUnnotedId(), amount));
				player.dialogueFactory.sendStatement("You have un-noted " + amount + " " + used.getName() + ".")
						.execute();
				return true;
			}

			if (used.isNoteable()) {
				player.inventory.remove(used.getId(), amount);
				player.inventory.add(new Item(used.getNotedId(), amount));
				player.dialogueFactory.sendStatement("You have noted " + amount + " " + used.getName() + ".").execute();
				return true;
			}
		}

		return false;
	}

}
