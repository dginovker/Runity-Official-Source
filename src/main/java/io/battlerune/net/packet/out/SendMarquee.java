package io.battlerune.net.packet.out;

import io.battlerune.game.world.World;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.net.codec.ByteModification;
import io.battlerune.net.packet.OutgoingPacket;
import io.battlerune.net.packet.PacketType;

public class SendMarquee extends OutgoingPacket {

	private final String[] strings;
	private final int id;

	public SendMarquee(int id, String... strings) {
		super(205, PacketType.VAR_SHORT);
		this.strings = strings;
		this.id = id;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeShort(id, ByteModification.ADD);
		for (int index = 0; index < 5; index++) {
			builder.writeString(
					index >= strings.length ? "" : strings[index].replace("#players", World.getPlayerCount() + ""));
		}
		return true;
	}
}
