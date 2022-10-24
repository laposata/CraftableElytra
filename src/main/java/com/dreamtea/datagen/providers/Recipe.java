package com.dreamtea.datagen.providers;

import com.dreamtea.recipes.FragileWingRecipe;
import com.dreamtea.recipes.PairOfWingsRecipe;
import com.dreamtea.recipes.WingRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Recipe extends FabricRecipeProvider {
  public static final Map<Identifier, net.minecraft.recipe.Recipe<?>> specials = new HashMap<>();
  public Recipe(FabricDataGenerator dataGenerator) {
    super(dataGenerator);
  }

  @Override
  protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
    FragileWingRecipe.exportAll(exporter);
    PairOfWingsRecipe.exportAll(exporter);
    WingRecipe.export(exporter);
  }

}
