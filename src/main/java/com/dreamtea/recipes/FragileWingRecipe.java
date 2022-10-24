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
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.dreamtea.CraftableElytra.NAMESPACE;

public class FragileWingRecipe extends AbstractCustomShapedCrafting {
  public static final List<Item> DEFAULT_RECIPE = List.of(
    Items.SHULKER_SHELL, Items.PHANTOM_MEMBRANE, Items.SHULKER_SHELL,
    Items.PHANTOM_MEMBRANE, Items.POPPED_CHORUS_FRUIT, Items.PHANTOM_MEMBRANE,
    Items.POPPED_CHORUS_FRUIT, Items.SHULKER_SHELL, Items.POPPED_CHORUS_FRUIT
  );
  public static final List<Item> ALT_DEFAULT_RECIPE = List.of(
    Items.SHULKER_SHELL, Items.POPPED_CHORUS_FRUIT, Items.SHULKER_SHELL,
    Items.POPPED_CHORUS_FRUIT, Items.PHANTOM_MEMBRANE, Items.POPPED_CHORUS_FRUIT,
    Items.PHANTOM_MEMBRANE, Items.SHULKER_SHELL, Items.PHANTOM_MEMBRANE
  );
  public static final String ALT_ID = "fragile_wing";
  public static final Item standinItem = Items.RABBIT_HIDE;
  public static final ItemStack OUTPUT;
  public static final String group = "fragile_wings";

  private FragileWingRecipe(Identifier id,  List<ItemStack> recipe) {
    super(id, recipe, OUTPUT,3, 3, group);
  }


  public static void createAll(Map<Identifier, net.minecraft.recipe.Recipe<?>> specials){
    Identifier fwrId = new Identifier(NAMESPACE, ALT_ID);
    FragileWingRecipe fwr = new FragileWingRecipe(fwrId, itemsToItemStacks(DEFAULT_RECIPE));
    fwr.create(fwrId, specials);
    Identifier fwrIdAlt = new Identifier(NAMESPACE, ALT_ID+"_alt");
    FragileWingRecipe fwrAlt = new FragileWingRecipe(fwrIdAlt, itemsToItemStacks(ALT_DEFAULT_RECIPE));
    fwrAlt.create(fwrIdAlt, specials);
  }

  public static void exportAll(Consumer<RecipeJsonProvider> exporter) {
    Identifier fwrId = new Identifier(NAMESPACE, ALT_ID);
    FragileWingRecipe fwr = new FragileWingRecipe(fwrId, itemsToItemStacks(DEFAULT_RECIPE));
    fwr.export(fwrId, exporter);
    Identifier fwrIdAlt = new Identifier(NAMESPACE, ALT_ID + "_alt");
    FragileWingRecipe fwrAlt = new FragileWingRecipe(fwrIdAlt, itemsToItemStacks(ALT_DEFAULT_RECIPE));
    fwrAlt.export(fwrIdAlt, exporter);
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return RecipeSerializer.SHAPED;
  }

  static {
    OUTPUT = new AlternateItemStack(standinItem, ALT_ID)
      .name(Text.of("Fragile Wing"))
      .lore(Text.of("Needs to smoke to get more solid"))
      .create();
  }

}
