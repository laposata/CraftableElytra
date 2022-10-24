package com.dreamtea;

import com.dreamtea.recipes.FragileWingRecipe;
import com.dreamtea.recipes.PairOfWingsRecipe;
import com.dreamtea.recipes.WingRecipe;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CraftableElytra implements DedicatedServerModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String NAMESPACE = "craft_elytra";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);
	public static final Map<Identifier, Recipe<?>> specialRecipes = new HashMap<>();
	@Override
	public void onInitializeServer() {

	}
}
