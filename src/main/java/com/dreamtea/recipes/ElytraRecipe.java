package com.dreamtea.recipes;

import com.dreamtea.item.AlternateItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


public class ElytraRecipe {
  public static final int input_count = 1;
  public static final ItemStack INPUT_ITEM = PairOfWingsRecipe.PAIR_OF_WINGS;
  public static final int secondary_count = 1;
  public static final ItemStack SECONDARY_ITEM = Items.DRAGON_EGG.getDefaultStack();
  public static final int levels = 30;

  public static final ItemStack BROKEN_ELYTRA;

  public static boolean canCraft(ItemStack primary, ItemStack secondary){
    if(AlternateItemStack.match(primary, INPUT_ITEM) && primary.getCount() == input_count){
      return AlternateItemStack.match(secondary, SECONDARY_ITEM) && secondary.getCount() >= secondary_count;
    }
    return false;
  }

  static {
    ItemStack elytra = Items.ELYTRA.getDefaultStack();
    elytra.setDamage(elytra.getMaxDamage() - 1);
    BROKEN_ELYTRA = elytra;
  }
}
