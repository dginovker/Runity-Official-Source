package plugin.itemon.npc;

import io.battlerune.content.dialogue.DialogueFactory;
import io.battlerune.content.dialogue.Expression;
import io.battlerune.content.pet.Pets;
import io.battlerune.game.event.impl.DropItemEvent;
import io.battlerune.game.event.impl.ItemOnNpcEvent;
import io.battlerune.game.event.impl.NpcClickEvent;
import io.battlerune.game.plugin.PluginContext;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.util.Utility;

/**
 * The Pet insurance plugin.
 *
 * @author Daniel
 */
public class PetPlugin extends PluginContext {

	@Override
	protected boolean itemOnNpc(Player player, ItemOnNpcEvent event) {
		if (event.getNpc().id != 7601) {
			return false;
		}
		Pets.buyInsurance(player, event.getUsed());
		return true;
	}

	@Override
	protected boolean onDropItem(Player player, DropItemEvent event) {
		return Pets.onSpawn(player, event.getItem().getId(), true);
	}

	@Override
	protected boolean firstClickNpc(Player player, NpcClickEvent event) {
		if (Pets.dialogue(player, event.getNpc())) {
			return true;
		}
		if (event.getNpc().id != 7601) {
			return false;
		}
		DialogueFactory factory = player.dialogueFactory;
		factory.sendNpcChat(7601, Expression.EVIL, "Ehh", "Whatchu need?", "Pet insurance?",
				"Fo sho man, I gotchu with that insurance.");
		factory.sendOption("How it works ", () -> message(factory), "View Insurance Information",
				() -> Pets.openInsurance(player), "View Lost Pets", () -> Pets.openLostPets(player), "Claim Lost Pets",
				() -> Pets.claimLostPets(player), "Nevermind", factory::clear);
		factory.execute();
		return true;
	}

	@Override
	protected boolean secondClickNpc(Player player, NpcClickEvent event) {
		if (event.getNpc().id != 7601) {
			return false;
		}
		Pets.openInsurance(player);
		return true;
	}

	private void message(DialogueFactory factory) {
		factory.sendNpcChat(7601, Expression.EVIL, "Just use your pet on me and I will insure it",
				"for " + Utility.formatDigits(Pets.INSRUANCE_COST) + " gold pieces.");
	}

}
