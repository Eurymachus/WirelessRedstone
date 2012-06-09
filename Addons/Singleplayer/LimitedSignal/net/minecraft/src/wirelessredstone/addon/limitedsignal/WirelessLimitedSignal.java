package net.minecraft.src.wirelessredstone.addon.limitedsignal;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class WirelessLimitedSignal {
	private static Map<String, Float> matSignalMultipliers;
	private static float maxRange;
	private static float rangeMultiplier;

	public static boolean initialize() {
		mod_WirelessRedstone
				.addOverrideToReceiver(new BlockRedstoneWirelessROverrideLSR());
		matSignalMultipliers = new HashMap<String, Float>();
		loadConfig();
		return true;
	}

	public static float getMaxRange() {
		return maxRange;
	}

	public static float getRangeMultiplier() {
		return rangeMultiplier;
	}

	public static float getSNRMultiplier(int[] a, int[] b, World world) {
		float multi = 0.0f;

		if (a.length == 3 && b.length == 3) {
			float[] vector = { b[0] - a[0], b[1] - a[1], b[2] - a[2] };
			float hyp = RedstoneEther.pythagoras(a, b);
			float stepLen = 1f / hyp;
			float[] stepVec = { vector[0] * stepLen, vector[1] * stepLen,
					vector[2] * stepLen };
			for (int step = 1; step < hyp; step++) {
				int i = Math.round((step * stepVec[0]) + a[0]);
				int j = Math.round((step * stepVec[1]) + a[1]);
				int k = Math.round((step * stepVec[2]) + a[2]);
				int id = world.getBlockId(i, j, k);
				if (id != 0) {
					multi += Block.blocksList[id].getHardness();
				}
			}
		}

		return multi;
	}

	public static float getRange(int[] a, int[] b) {
		return RedstoneEther.pythagoras(a, b);
	}

	private static String matToString(Material mat) {

		if (mat == Material.air) {
			return "Air";
		} else if (mat == Material.grass) {
			return "Grass";
		} else if (mat == Material.ground) {
			return "Ground";
		} else if (mat == Material.wood) {
			return "Wood";
		} else if (mat == Material.rock) {
			return "Rock";
		} else if (mat == Material.iron) {
			return "Iron";
		} else if (mat == Material.water) {
			return "Water";
		} else if (mat == Material.lava) {
			return "Lava";
		} else if (mat == Material.leaves) {
			return "Leaves";
		} else if (mat == Material.plants) {
			return "Plants";
		} else if (mat == Material.sponge) {
			return "Sponge";
		} else if (mat == Material.cloth) {
			return "Cloth";
		} else if (mat == Material.fire) {
			return "Fire";
		} else if (mat == Material.sand) {
			return "Sand";
		} else if (mat == Material.circuits) {
			return "Circuits";
		} else if (mat == Material.glass) {
			return "Glass";
		} else if (mat == Material.tnt) {
			return "Dynamite";
		} else if (mat == Material.ice) {
			return "Ice";
		} else if (mat == Material.snow) {
			return "Snow";
		} else if (mat == Material.craftedSnow) {
			return "Snow";
		} else if (mat == Material.cactus) {
			return "Cactus";
		} else if (mat == Material.pumpkin) {
			return "Pumpkin";
		} else if (mat == Material.portal) {
			return "Portal";
		} else if (mat == Material.cake) {
			return "Cake";
		} else if (mat == Material.web) {
			return "Web";
		} else if (mat == Material.piston) {
			return "Piston";
		} else {
			return null;
		}
	}

	private static float getMatSignalMultiplier(Material mat) {
		String matStr = matToString(mat);
		if (matStr != null)
			return matSignalMultipliers.get(matStr);
		else
			return 0;
	}

	private static void loadConfig() {
		loadConfigMultiplier(Material.air, 0.0f);
		loadConfigMultiplier(Material.grass, 0.4f);
		loadConfigMultiplier(Material.ground, 0.5f);
		loadConfigMultiplier(Material.wood, 0.6f);
		loadConfigMultiplier(Material.rock, 1.0f);
		loadConfigMultiplier(Material.iron, 4.0f);
		loadConfigMultiplier(Material.water, 0.6f);
		loadConfigMultiplier(Material.lava, 0.7f);
		loadConfigMultiplier(Material.leaves, 0.2f);
		loadConfigMultiplier(Material.plants, 0.2f);
		loadConfigMultiplier(Material.sponge, 0.3f);
		loadConfigMultiplier(Material.cloth, 0.4f);
		loadConfigMultiplier(Material.fire, 0.2f);
		loadConfigMultiplier(Material.sand, 0.7f);
		loadConfigMultiplier(Material.circuits, 0.5f);
		loadConfigMultiplier(Material.glass, 1.0f);
		loadConfigMultiplier(Material.tnt, 0.8f);
		loadConfigMultiplier(Material.ice, 0.6f);
		loadConfigMultiplier(Material.snow, 0.6f);
		loadConfigMultiplier(Material.craftedSnow, 0.6f);
		loadConfigMultiplier(Material.cactus, 0.7f);
		loadConfigMultiplier(Material.pumpkin, 0.5f);
		loadConfigMultiplier(Material.portal, 10.0f);
		loadConfigMultiplier(Material.cake, 0.3f);
		loadConfigMultiplier(Material.web, 0.2f);
		loadConfigMultiplier(Material.piston, 0.8f);

		rangeMultiplier = (Float) ConfigStoreRedstoneWireless.getInstance(
				"LimitedSignal").get("Range.Multiplier", Float.class, 20.0f);

		maxRange = (Float) ConfigStoreRedstoneWireless.getInstance(
				"LimitedSignal").get("Range.Max", Float.class, 50.0f);
	}

	private static void loadConfigMultiplier(Material mat, float def) {
		matSignalMultipliers.put(
				matToString(mat),
				(Float) ConfigStoreRedstoneWireless
						.getInstance("LimitedSignal").get(
								"Multiplier." + matToString(mat), Float.class,
								def));
	}
}
