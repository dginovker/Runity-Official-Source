package io.battlerune.content.tittle;

import java.util.Objects;

/**
 * The player title class.
 *
 * @author Daniel.
 */
public final class PlayerTitle {

	/** An empty title. */
	private static final PlayerTitle EMPTY = new PlayerTitle("", 0xC74C1C);

	/** The title string. */
	private final String title;

	/** The title color. */
	private final int color;

	/**
	 * Constructs a new <code>PlayerTitle</code>.
	 *
	 * @param title The title.
	 * @param color The color.
	 */
	private PlayerTitle(String title, int color) {
		this.title = title;
		this.color = color;
	}

	/**
	 * Creates a player title.
	 *
	 * @param title The title to set.
	 * @param color The color to set.
	 * @return The created player title.
	 */
	public static PlayerTitle create(String title, int color) {
		return new PlayerTitle(title, color);
	}

	/** Creates an empty player title. */
	public static PlayerTitle empty() {
		return EMPTY;
	}

	/** Gets the title. */
	public String getTitle() {
		return title;
	}

	/** Gets the color. */
	public int getColor() {
		return color;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PlayerTitle))
			return false;
		final PlayerTitle other = (PlayerTitle) obj;
		return title.equals(other.getTitle()) && other.getColor() == getColor();
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, color);
	}
}