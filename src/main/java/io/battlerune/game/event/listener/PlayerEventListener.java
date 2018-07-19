package io.battlerune.game.event.listener;

import io.battlerune.game.event.Event;
import io.battlerune.game.world.entity.mob.player.Player;

/**
 * The type of event listener that will listen for player-related events
 *
 * @author nshusa
 */
public interface PlayerEventListener extends EventListener {

	/**
	 * The method that allows an event listener to accept an event.
	 *
	 * @param player The player that is sending the event.
	 *
	 * @param event  The event that is being sent.
	 *
	 * @return {@code true} If the event was handled. Otherwise return
	 *         {@code false}.
	 */
	boolean accept(Player player, Event event);

}
