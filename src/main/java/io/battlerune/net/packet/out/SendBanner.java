package io.battlerune.net.packet.out;

import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.net.packet.OutgoingPacket;
import io.battlerune.net.packet.PacketType;

public class SendBanner extends OutgoingPacket {

	private final String title;
	private final String message;
	private final int color;

	public SendBanner(Object title, Object message, int color) {
		super(202, PacketType.VAR_BYTE);
		this.title = String.valueOf(title);
		this.message = String.valueOf(message);
		this.color = color;
	}

	@Override
	public boolean encode(Player player) {
		builder.writeString(title).writeString(message).writeInt(color);
		return true;
	}

}
