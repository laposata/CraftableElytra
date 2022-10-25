package com.dreamtea.recipes;

import com.dreamtea.item.AlternateItemStack;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Map;
import java.util.function.Consumer;

import static com.dreamtea.CraftableElytra.NAMESPACE;

public class WingRecipe extends SmokingRecipe {
  private static final int cookingMinutes = 60;
  public static final int cookingTime = cookingMinutes * 60 * 20;
  public static final String ALT_ID = "wing";
  private static final int xp = 160;
  public static final Item standinItem = Items.LEATHER;
  public static final ItemStack WING;
  public static final Identifier id = new Identifier(NAMESPACE, ALT_ID +"_smoking_recipe");

  public WingRecipe() {
    super(new Identifier(NAMESPACE, ALT_ID), "wings", Ingredient.ofStacks(FragileWingRecipe.OUTPUT), WING, xp, cookingTime);
  }

  @Override
  public boolean matches(Inventory inventory, World world) {
    return AlternateItemStack.match(inventory.getStack(0), FragileWingRecipe.OUTPUT);
  }

  public static void create(Map<Identifier, Recipe<?>> specials){
    SmokingRecipe recipe = new WingRecipe();
    specials.put(id,recipe);
  }

  public static void export(Consumer<RecipeJsonProvider> exporter){
    if (exporter != null) {
      CookingRecipeJsonBuilder
        .createSmoking(Ingredient.ofItems(standinItem), FragileWingRecipe.standinItem, xp, cookingTime)
        .group("Wing")
        .criterion("has_elytra", RecipeProvider.conditionsFromItem(Items.ELYTRA))
        .offerTo(exporter, id);
    }
  }

  static {
    WING = new AlternateItemStack(standinItem, ALT_ID)
      .name(Text.of("Wing"))
      .lore(Text.of("You'll need to connect 2 of these somehow"))
      .create();
  }

}
