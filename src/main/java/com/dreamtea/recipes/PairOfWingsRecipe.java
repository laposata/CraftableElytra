package com.dreamtea.recipes;

import com.dreamtea.item.AlternateItemStack;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.dreamtea.CraftableElytra.NAMESPACE;

public class PairOfWingsRecipe extends AbstractCustomShapedCrafting {
  public static final String ALT_ID = "pair_of_wings";

  public static final ItemStack PAIR_OF_WINGS;
  public static final Item standinItem = Items.ELYTRA;
  private static final ItemStack end_rod = Items.END_ROD.getDefaultStack();
  private static final ItemStack diamond = Items.DIAMOND.getDefaultStack();

  public static List<ItemStack> DEFAULT_RECIPE = List.of(
    end_rod, diamond, end_rod,
    WingRecipe.WING, ItemStack.EMPTY, WingRecipe.WING
  );

  public static List<ItemStack> DEFAULT_RECIPE_ALT = List.of(
    ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
    end_rod, diamond, end_rod,
    WingRecipe.WING, ItemStack.EMPTY, WingRecipe.WING
  );

  private PairOfWingsRecipe(Identifier id,List<ItemStack> input) {
    super(id, input, PAIR_OF_WINGS,3, 3, ALT_ID);  }

  public static void createAll(Map<Identifier, net.minecraft.recipe.Recipe<?>> specials){
    Identifier powrId = new Identifier(NAMESPACE, ALT_ID);
    PairOfWingsRecipe powr = new PairOfWingsRecipe(powrId,(DEFAULT_RECIPE));
    powr.create(powrId, specials);
    Identifier powrAltId = new Identifier(NAMESPACE, ALT_ID+"_alt");
    PairOfWingsRecipe powrAlt = new PairOfWingsRecipe(powrAltId, DEFAULT_RECIPE_ALT);
    powrAlt.create(powrAltId, specials);
  }

  public static void exportAll(Consumer<RecipeJsonProvider> exporter){
    Identifier powrId = new Identifier(NAMESPACE, ALT_ID);
    PairOfWingsRecipe powr = new PairOfWingsRecipe(powrId,(DEFAULT_RECIPE));
    powr.export(powrId, exporter);
    Identifier powrAltId = new Identifier(NAMESPACE, ALT_ID+"_alt");
    PairOfWingsRecipe powrAlt = new PairOfWingsRecipe(powrAltId, DEFAULT_RECIPE_ALT);
    powrAlt.export(powrAltId, exporter);
  }

  @Override
  public boolean isIgnoredInRecipeBook() {
    return false;
  }

  @Override
  public ItemStack getOutput() {
    return PAIR_OF_WINGS;
  }

  @Override
  public ItemStack craft(CraftingInventory inventory) {
    return getOutput().copy();
  }

  static {
    PAIR_OF_WINGS = new AlternateItemStack(standinItem, ALT_ID)
      .name(Text.of("Pair of Wings"))
      .lore(Text.of("This needs to be imbued with dragon magic"))
      .create();
  }
}
