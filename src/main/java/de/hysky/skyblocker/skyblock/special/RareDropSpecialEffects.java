package de.hysky.skyblocker.skyblock.special;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;

import de.hysky.skyblocker.annotations.Init;
import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.skyblock.itemlist.ItemRepository;
import de.hysky.skyblocker.utils.FlexibleItemStack;
import de.hysky.skyblocker.utils.SkyBlockIcons;
import de.hysky.skyblocker.utils.Utils;

public class RareDropSpecialEffects {
	private static final Logger LOGGER = LoggerFactory.getLogger(RareDropSpecialEffects.class);
	private static final Pattern MAGIC_FIND_PATTERN = Pattern.compile("^(?!.*:)(?:RARE|VERY RARE|CRAZY RARE|INSANE) DROP!\\s+\\(?(?<item>.+?)\\)?(?:\\s+\\(\\+\\d+%? "+SkyBlockIcons.MAGIC_FIND+" Magic Find\\))?$");

	@Init
	public static void init() {
		ClientReceiveMessageEvents.ALLOW_GAME.register(RareDropSpecialEffects::displayRareDropEffect);
	}

	private static boolean displayRareDropEffect(Component message, boolean overlay) {
		if (Utils.isOnSkyblock() && SkyblockerConfigManager.get().general.specialEffects.rareDropEffects && !overlay) {

			try {
				String stringForm = message.getString();
				Matcher magicFindMatcher = MAGIC_FIND_PATTERN.matcher(stringForm);

				if (magicFindMatcher.matches()) {
					triggerDropEffect(magicFindMatcher.group("item"));
				}
			} catch (Exception e) { //In case there's a regex failure or something else bad happens
				LOGGER.error("[Skyblocker Special Effects] An unexpected exception was encountered: ", e);
			}
		}

		return true;
	}

	private static void triggerDropEffect(String itemName) {
		FlexibleItemStack stack = getStackFromName(itemName);

		if (stack != null && stack.getStack() != null && !stack.getStack().isEmpty()) {
			SpecialEffects.displaySpecialEffect(stack.getStackOrThrow(), ParticleTypes.SCRAPE);
		}
	}

	private static @Nullable FlexibleItemStack getStackFromName(String itemName) {
		String itemId = switch (itemName) {
			//Mythological Ritual
			case "Enchanted Book (Chimera I)" -> "ULTIMATE_CHIMERA;1";
			case "Fateful Stinger" -> "FATEFUL_STINGER";
			case "Manti-core" -> "MANTI_CORE";
			case "Minos Relic" -> "MINOS_RELIC";
			case "Shimmering Wool" -> "SHIMMERING_WOOL";
			case "Daedalus Stick" -> "DAEDALUS_STICK";
			case "Crown of Greed" -> "CROWN_OF_GREED";
			case "Washed-up Souvenir" -> "WASHED_UP_SOUVENIR";
			case "Hilt of Revelations" -> "HILT_OF_REVELATIONS";
			case "Crochet Tiger Plushie" -> "CROCHET_TIGER_PLUSHIE";
			case "Cretan Urn" -> "CRETAN_URN";
			case "Antique Remedies" -> "ANTIQUE_REMEDIES";
			case "Dwarf Turtle Shelmet" -> "DWARF_TURTLE_SHELMET";
			case "Brain Food" -> "BRAIN_FOOD";

			//Slayer - Zombie (Revenant Horror)
			case "Scythe Blade" -> "SCYTHE_BLADE";
			case "Shredded Sinew" -> "SHARD_OF_THE_SHREDDED";
			case "Severed Hand" -> "SEVERED_HAND";
			case "Warden Heart" -> "WARDEN_HEART";
			case "Snake Rune I", "Snake Rune" -> "RUNE_SNAKE;1";
			case "Undead Catalyst" -> "UNDEAD_CATALYST";
			case "Revenant Shard" -> "REVENANT_SHARD";
			case "Revenant Catalyst" -> "REVENANT_CATALYST";
			case "Beheaded Horror" -> "BEHEADED_HORROR";
			case "Festering Maggot" -> "FESTERING_MAGGOT";

			//Slayer - Spider (Tarantula Broodfather
			case "Shriveled Wasp" -> "SHRIVELED_WASP";
			case "Digested Mosquito" -> "DIGESTED_MOSQUITO";
			case "Ensnared Snail" -> "ENSNARED_SNAIL";
			case "Primordial Eye" -> "PRIMORDIAL_EYE";
			case "Primordial Shard" -> "PRIMORDIAL_SHARD";
			case "Darkness Within Rune", "Darkness Within Rune I" -> "RUNE_DARKNESS_WITHIN;1";
			case "Tarantula Catalyst" -> "TARANTULA_CATALYST";
			case "Fly Swatter" -> "FLY_SWATTER";
			case "Tarantula Talisman" -> "TARANTULA_TALISMAN";
			case "Spider Catalyst" -> "SPIDER_CATALYST";
			case "Vial of Venom" -> "VIAL_OF_VENOM";
			case "Paragon Shard" -> "PARAGON_SHARD";

			//Slayer - Wolf (Sven Packmaster)
			case "Overflux Capacitor" -> "OVERFLUX_CAPACITOR";
			case "Red Claw Egg" -> "RED_CLAW_EGG";
			case "Couture Rune I", "Couture Rune" -> "RUNE_COUTURE;1";
			case "Grizzly Salmon" -> "GRIZZLY_SALMON";
			case "Furball" -> "FURBALL";
			case "Enchanted Book (Critical VI)", "Critical VI" -> "ENCHANTED_BOOK";

			//Slayer - Enderman (Voidgloom Seraph)
			case "End Stone Idol" -> "END_STONE_IDOL";
			case "Judgement Core" -> "JUDGEMENT_CORE";
			case "Hazmat Enderman" -> "HAZMAT_ENDERMAN";
			case "End Rune", "End Rune I" -> "RUNE_END;1";
			case "Summoning Eye" -> "SUMMONING_EYE";
			case "Sinful Dice" -> "SINFUL_DICE";
			case "Etherwarp Merger" -> "ETHERWARP_MERGER";
			case "Pocket Espresso Machine" -> "POCKET_ESPRESSO_MACHINE";
			case "Handy Blood Chalice" -> "HANDY_BLOOD_CHALICE";
			case "Void Conqueror Enderman Skin" -> "VOID_CONQUEROR_ENDERMAN_SKIN";
			case "Enchant Rune", "Enchant Rune I" -> "RUNE_ENCHANT;1";
			case "Exceedingly Rare Ender Artifact Upgrade" -> "EXCEEDINGLY_RARE_ENDER_ARTIFACT_UPGRADE";

			//Slayer - Blaze (Inferno Demonlord)
			case "High Class Archfiend Dice" -> "HIGH_CLASS_ARCHFIEND_DICE";
			case "Wilson's Engineering Plans" -> "WILSONS_ENGINEERING_PLANS";
			case "Subzero Inverter", "Sub-Zero Inverter" -> "SUBZERO_INVERTER";
			case "Enchanted Book (Duplex I)", "Duplex I" -> "ULTIMATE_DUPLEX;1";
			case "Fiery Burst Rune I", "Fiery Burst Rune" -> "RUNE_FIERY_BURST;1";
			case "Archfiend Dice" -> "ARCHFIEND_DICE";
			case "Lavatears Rune I", "Lavatears Rune" -> "RUNE_LAVATEARS;1";

			//Fishing
			case "Prince's Crown Jewel" -> "PRINCES_CROWN_JEWEL";
			case "Pocket-sized Igloo" -> "POCKET_SIZED_IGLOO";
			case "Radioactive Vial" -> "RADIOACTIVE_VIAL";
			case "Tiki Mask" -> "TIKI_MASK";
			case "Titanoboa Shed" -> "TITANOBOA_SHED";
			case "Water Hydra Head" -> "WATER_HYDRA_HEAD";
			case "Fish Affinity Talisman" -> "FISH_AFFINITY_TALISMAN";
			case "Condensed Lily Pad" -> "CONDENSED_LILY_PAD";
			case "Bobbin' Scriptures" -> "BOBBIN_SCRIPTURES";
			case "Snake Eyes" -> "SNAKE_EYES";
			case "Gold Lotus" -> "GOLD_LOTUS";
			case "Unlucky Doubloon" -> "UNLUCKY_DOUBLOON";
			case "Enchanted Gold Block" -> "ENCHANTED_GOLD_BLOCK";
			case "LEGENDARY Flying Fish Pet", "Flying Fish Pet" -> "PET_FLYING_FISH";
			case "Mangcore" -> "MANGCORE";
			case "Mound of Seagrass" -> "MOUND_OF_SEAGRASS";
			case "Vibrant Coral" -> "VIBRANT_CORAL";
			case "Helixis" -> "HELIXIS";
			case "Veilshroom Bunch" -> "VEILSHROOM_BUNCH";
			case "Water Hyacinth" -> "WATER_HYACINTH";
			case "Distant Echo" -> "DISTANT_ECHO";
			case "Isopod Husk" -> "ISOPOD_HUSK";
			case "Reinforced Netting" -> "REINFORCED_NETTING";

			//Fishing Events
			case "COMMON Baby Yeti Pet", "Baby Yeti Pet" -> "PET_BABY_YETI";
			case "True Ice" -> "TRUE_ICE";
			case "Deep Sea Orb" -> "DEEP_SEA_ORB";
			case "Spooky Hook" -> "PHANTOM_HOOK";
			case "Lucky Hoof" -> "LUCKY_HOOF";
			case "Edible Seaweed" -> "EDIBLE_SEAWEED";
			case "Shredded Line" -> "SHREDDED_LINE";
			case "EPIC Megalodon Pet", "LEGENDARY Megalodon Pet", "Megalodon Pet" -> "PET_MEGALODON";
			case "Carnival Ticket" -> "CARNIVAL_TICKET";

			//Crimson Isle Lava Fishing
			case "Octopus Tendril" -> "OCTOPUS_TENDRIL";
			case "Enchanted Clay Block" -> "ENCHANTED_CLAY_BLOCK";
			case "EPIC Fishing Exp Boost", "Fishing Exp Boost" -> "PET_ITEM_FISHING_SKILL_BOOST_EPIC";
			case "Troubled Bubble" -> "TROUBLED_BUBBLE";
			case "Enchanted Book (Pyroclasm VI)", "Pyroclasm VI" -> "ENCHANTED_BOOK";
			case "Severed Pincer" -> "SEVERED_PINCER";
			case "Scuttler Shell" -> "SCUTTLER_SHELL";
			case "Brimstone Handle" -> "BRIMSTONE_HANDLE";
			case "Burnt Texts" -> "BURNT_TEXTS";
			case "Chain of the End Times" -> "CHAIN_END_TIMES";

			//Spider's Den
			case "Travel Scroll to Spider's Den Top of Nest" -> "SPIDERS_DEN_TOP_TRAVEL_SCROLL";
			case "EPIC Tarantula", "LEGENDARY Tarantula" -> "PET_TARANTULA";

			//Howling Cave & Wolves
			case "Travel Scroll to Howling Cave" -> "PARK_CAVE_TRAVEL_SCROLL";
			case "EPIC Hound", "LEGENDARY Hound" -> "PET_HOUND";
			case "EPIC Foraging Exp Boost" -> "PET_ITEM_FORAGING_SKILL_BOOST_EPIC";
			case "Weak Wolf Catalyst" -> "WEAK_WOLF_CATALYST";

			//The End
			case "Ender Necklace" -> "ENDER_NECKLACE";
			case "COMMON Enderman", "UNCOMMON Enderman", "RARE Enderman", "EPIC Enderman", "LEGENDARY Enderman" -> "PET_ENDERMAN";
			case "RARE Combat Exp Boost" -> "PET_ITEM_COMBAT_SKILL_BOOST_RARE";
			case "EPIC Combat Exp Boost" -> "PET_ITEM_COMBAT_SKILL_BOOST_EPIC";
			case "Obsidian Chestplate" -> "OBSIDIAN_CHESTPLATE";
			case "End Stone Bow" -> "END_STONE_BOW";
			case "Enderman Cortex Rewriter" -> "ENDERMAN_CORTEX_REWRITER";

			//Mining / Deep Caverns & Dwarven Mines
			case "Exp Share Core" -> "EXP_SHARE_CORE";
			case "Glacite Jewel" -> "GLACITE_JEWEL";
			case "Ghostly Boots" -> "GHOSTLY_BOOTS";
			case "Plasma" -> "PLASMA";
			case "Volta" -> "VOLTA";
			case "Sorrow" -> "SORROW";
			case "Yellow Goblin Egg" -> "GOBLIN_EGG_YELLOW";
			case "Red Goblin Egg" -> "GOBLIN_EGG_RED";
			case "Blue Goblin Egg" -> "GOBLIN_EGG_BLUE";

			default -> "NONE";
		};

		return ItemRepository.getItemStack(itemId);
	}
}
