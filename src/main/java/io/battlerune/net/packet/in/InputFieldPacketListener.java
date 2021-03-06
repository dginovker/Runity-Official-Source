package io.battlerune.net.packet.in;

import io.battlerune.content.DropDisplay;
import io.battlerune.content.DropDisplay.DropType;
import io.battlerune.content.DropSimulator;
import io.battlerune.content.ProfileViewer;
import io.battlerune.content.staff.StaffPanel;
import io.battlerune.game.world.World;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.mob.player.PlayerRight;
import io.battlerune.net.packet.GamePacket;
import io.battlerune.net.packet.PacketListener;
import io.battlerune.net.packet.PacketListenerMeta;
import io.battlerune.net.packet.out.SendMessage;
import io.battlerune.util.MessageColor;
import io.battlerune.content.store.impl.PersonalStore;

@PacketListenerMeta(142)
public class InputFieldPacketListener implements PacketListener {

	@Override
	public void handlePacket(Player player, GamePacket packet) {
		final int component = packet.readInt();
		final String context = packet.getRS2String();

		if (component < 0) 
			return;
		
		if (PlayerRight.isDeveloper(player)) {
			player.send(new SendMessage("[InputField] - Text: " + context + " Component: " + component,
					MessageColor.DEVELOPER));
		}

		switch (component) {

		/* Personal Store */
		case 38307:
			PersonalStore.changeName(player, context, false);
			break;
		case 38309:
			PersonalStore.changeName(player, context, true);
			break;

		
		/* Clan chat */
		case 42102:
			player.forClan(clan -> {
				if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
					clan.setName(player, context);
				}
			});
			break;
		case 42104:
			player.forClan(clan -> {
				if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
					clan.setTag(player, context);
				}
			});
			break;
		case 42106:
			player.forClan(clan -> {
				if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
					clan.setSlogan(player, context);
					player.message("The new clan slogan is: " + context + ".");
				}
			});
			break;

		case 42108: {
			player.forClan(clan -> {
				if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
					clan.getManagement().password = context;
					if (context.isEmpty()) {
						player.message("Your clan will no longer use a password.");
					} else {
						player.message("The new clan password is: " + context + ".");
					}
				}
			});
			break;
		}

		/* Drop simulator */
		case 26810:
			DropSimulator.drawList(player, context);
			break;

		/* Price checker */
		case 48508:
			player.priceChecker.searchItem(context);
			break;


		/* Drop display */
		case 54506:
			DropDisplay.search(player, context, DropType.ITEM);
			break;
		case 54507:
			DropDisplay.search(player, context, DropType.NPC);
			break;

		/* Staff panel */
		case 36706:
			StaffPanel.search(player, context);
			break;

		/* Friend's Profile view */
		case 353:
			if (World.search(context).isPresent()) {
				ProfileViewer.open(player, World.search(context).get());
				return;
			}

			player.send(new SendMessage("You can not view " + context + "'s profile as they are currently offline."));
			break;

		/* Friend's manage */
		case 354:
			if (PlayerRight.isManagement(player)) {
				if (World.search(context).isPresent()) {
					StaffPanel.search(player, context);
					return;
				}
				player.send(new SendMessage("You can not manage " + context + " as they are currently offline."));
			}
			break;
		}
	}
}
