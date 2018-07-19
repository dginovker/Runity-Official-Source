package io.battlerune.content.skill.impl.crafting.impl;

import io.battlerune.Config;
import io.battlerune.content.dialogue.ChatBoxItemDialogue;
import io.battlerune.content.dialogue.DialogueFactory;
import io.battlerune.game.Animation;
import io.battlerune.game.action.Action;
import io.battlerune.game.action.policy.WalkablePolicy;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.skill.Skill;
import io.battlerune.game.world.items.Item;
import io.battlerune.net.packet.out.SendInputAmount;
import io.battlerune.net.packet.out.SendMessage;
import io.battlerune.util.Utility;

/**
 * Handles spinning items on the spinning wheel.
 *
 * @author Daniel
 */
public class Spinning {
	/**
	 * The spinnable data.
	 */
	public enum Spinnable {
	BOWSTRING(new Item(1779), new Item(1777), 15.0D, 10), WOOL(new Item(1737), new Item(1759), 2.5D, 1),
	ROPE(new Item(10814), new Item(954), 25.0D, 30), MAGIC_STRING(new Item(6051), new Item(6038), 30.0D, 19),
	YEW_STRING(new Item(6049), new Item(9438), 15.0D, 10), SINEW_STRING(new Item(9436), new Item(9438), 15.0D, 10);

		/**
		 * The spinnable item.
		 */
		public Item item;

		/**
		 * The spinnable outcome item.
		 */
		public Item outcome;

		/**
		 * The spinnable experience.
		 */
		public double experience;

		/**
		 * The level required to spin.
		 */
		public int requiredLevel;

		/**
		 * Constructs a new <code>Spinnable</code>.
		 *
		 * @param item          The item required.
		 * @param outcome       The outcome item.
		 * @param experience    The experience rewarded.
		 * @param requiredLevel The level required.
		 */
		Spinnable(Item item, Item outcome, double experience, int requiredLevel) {
			this.item = item;
			this.outcome = outcome;
			this.experience = experience;
			this.requiredLevel = requiredLevel;
		}
	}

	/**
	 * Handles opening the spinning dialogue.
	 *
	 * @param player The player instance.
	 */
	public static void open(Player player) {
		DialogueFactory factory = player.dialogueFactory;
		factory.sendOption("Ball of wool (wool)", () -> {
			factory.onAction(() -> {
				click(player, Spinnable.WOOL);
			});
		}, "Bow string (flax)", () -> {
			factory.onAction(() -> {
				click(player, Spinnable.BOWSTRING);
			});
		}, "Rope (yak hair)", () -> {
			factory.onAction(() -> {
				click(player, Spinnable.ROPE);
			});
		}, "Nevermind", () -> {
			player.interfaceManager.close();
		}).execute();
	}

	/**
	 * Opens the spinnable dialogue.
	 *
	 * @param player    The player instance.
	 * @param spinnable The spinnable data.
	 */
	public static void click(Player player, Spinnable spinnable) {
		player.dialogueFactory.clear();

		if (player.skills.getMaxLevel(Skill.CRAFTING) < spinnable.requiredLevel) {
			player.dialogueFactory
					.sendStatement("You need a crafting level of " + spinnable.requiredLevel + " to spin this!")
					.execute();
			return;
		}

		if (!player.inventory.contains(spinnable.item)) {
			player.dialogueFactory.sendStatement("You do not have the required items to do this!").execute();
			return;
		}

		ChatBoxItemDialogue.sendInterface(player, 1746, spinnable.item, 170);
		player.chatBoxItemDialogue = new ChatBoxItemDialogue(player) {
			@Override
			public void firstOption(Player player) {
				player.action.execute(spin(player, spinnable, 1), true);
			}

			@Override
			public void secondOption(Player player) {
				player.action.execute(spin(player, spinnable, 5), true);
			}

			@Override
			public void thirdOption(Player player) {
				player.send(new SendInputAmount("Enter amount", 2,
						input -> player.action.execute(spin(player, spinnable, Integer.parseInt(input)), true)));
			}

			@Override
			public void fourthOption(Player player) {
				player.action.execute(spin(player, spinnable, 28), true);
			}
		};
	}

	/**
	 * The spinnable action.
	 *
	 * @param player    The player instance.
	 * @param spinnable The spinnable data.
	 * @param amount    The amount beeing spun.
	 * @return The spinnable action.
	 */
	private static Action<Player> spin(Player player, Spinnable spinnable, int amount) {
		return new Action<Player>(player, 2, true) {

			int ticks = 0;

			@Override
			public void execute() {
				if (!player.inventory.contains(spinnable.item)) {
					player.dialogueFactory.sendStatement("You have run out of material!").execute();
					cancel();
					return;
				}

				player.animate(new Animation(896));
				player.inventory.remove(spinnable.item);
				player.inventory.add(spinnable.outcome);
				player.skills.addExperience(Skill.CRAFTING, spinnable.experience * Config.CRAFTING_MODIFICATION);
				player.send(new SendMessage("You spin the " + spinnable.item.getName() + " into "
						+ Utility.getAOrAn(spinnable.outcome.getName()) + " " + spinnable.outcome.getName() + "."));

				if (++ticks == amount) {
					cancel();
					return;
				}
			}

			@Override
			public String getName() {
				return "Spinning";
			}

			@Override
			public WalkablePolicy getWalkablePolicy() {
				return WalkablePolicy.NON_WALKABLE;
			}
		};
	}
}
