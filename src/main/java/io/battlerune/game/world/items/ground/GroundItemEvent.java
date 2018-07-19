package io.battlerune.game.world.items.ground;

import io.battlerune.game.task.Task;
import io.battlerune.game.world.World;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.region.Region;
import io.battlerune.net.packet.out.SendGroundItem;

/**
 * @author <a href="http://www.rune-server.org/members/stand+up/">Stand Up</a>
 * @since 27-12-2016.
 */
public final class GroundItemEvent extends Task {

	/** A variable which indicates how many ticks a minute is. */
	private static final int MINUTE = 100;

	/** The ground item this randomevent is running for. */
	private final GroundItem groundItem;

	/** The counter of this task. */
	private int minutes;

	/** Creates a new {@link Task} that doesn't execute instantly. */
	GroundItemEvent(GroundItem groundItem) {
		super(MINUTE);
		this.groundItem = groundItem;
	}

	@Override
	public void execute() {
		switch (groundItem.policy) {
		case GLOBAL:
			if (++minutes < 5) {
				return;
			}

			if (groundItem.item.getId() == 12791) {
				groundItem.player.runePouch.runes.clear();
			}

			cancel();
			break;
		case ONLY_OWNER:
			if (++minutes < 2) {
				return;
			}

			groundItem.policy = GroundItemPolicy.GLOBAL;
			if (!groundItem.item.isTradeable()) {
				return;
			}

			Region[] regions = World.getRegions().getSurroundingRegions(groundItem.getPosition());
			for (Region region : regions) {
				for (Player player : region.getPlayers(groundItem.getHeight())) {
					if (!groundItem.isRegistered())
						continue;

					if (!groundItem.canSee(player))
						continue;

					if (groundItem.player.usernameLong != player.usernameLong)
						player.send(new SendGroundItem(groundItem));
				}
			}
			break;
		}
	}

	@Override
	protected void onCancel(boolean logout) {
		groundItem.unregister();
	}

}
