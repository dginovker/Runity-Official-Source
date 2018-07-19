package io.battlerune.content.skill.impl.runecrafting;

import java.util.Arrays;
import java.util.Optional;

import io.battlerune.game.world.position.Position;

/** Holds the runecrafting teleport data - @author Daniel */
public enum RunecraftTeleport {
	CHAOS(24976, new Position(2281, 4837)), LAW(25034, new Position(2464, 4818)),
	DEATH(25035, new Position(2208, 4830)), SOUL(25377, new Position(3494, 4832)), AIR(25378, new Position(2841, 4829)),
	MIND(25379, new Position(2793, 4828)), EARTH(24972, new Position(2655, 4830)),
	FIRE(24971, new Position(2577, 4846)), BLOOD(25380, new Position(1732, 3827)),
	COSMIC(24974, new Position(2162, 4833)), NATURE(24975, new Position(2400, 4835)),
	BODY(24973, new Position(2521, 4834));

	/** The object identification. */
	private final int object;

	/** The teleport position. */
	private final Position position;

	/** The runecrafting teleport. */
	RunecraftTeleport(int object, Position position) {
		this.object = object;
		this.position = position;
	}

	/** Gets the object identification. */
	public int getObject() {
		return object;
	}

	/** Gets the teleport position. */
	public Position getPosition() {
		return position;
	}

	/** Gets the runecrafting teleport data based on the object identification. */
	public static Optional<RunecraftTeleport> forId(int id) {
		return Arrays.stream(values()).filter(a -> a.object == id).findAny();
	}
}
