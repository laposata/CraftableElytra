package com.dreamtea.imixin;

import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

import java.util.Objects;

import static net.minecraft.item.ItemStack.DISPLAY_KEY;
import static net.minecraft.item.ItemStack.LORE_KEY;
import static net.minecraft.item.ItemStack.NAME_KEY;

public interface IFragile {
  public static Item pressItem = Items.PHANTOM_MEMBRANE;
  public static int pressQty = 16;
  public static Item repairItem = Items.PHANTOM_MEMBRANE;
  public static int repairQty = 16;
  public static int repairCost = 30;
  public static String ELYTRA_FRAGILE = "fragile";
  public static String FRAGILE_LORE = "Fragile";
  public static String FRAGILE_NAME = "Fragile Elytra";

  public static boolean hasCustomName(ItemStack stack){
    if(stack.hasCustomName()){
      String name = stack.getSubNbt(DISPLAY_KEY).getString(NAME_KEY);
      return name.contains(FRAGILE_NAME);
    }
    return false;
  }
  public static boolean isFragile(ItemStack stack){
    if(stack.isOf(Items.ELYTRA) && stack.hasNbt()){
      NbtCompound nbt = stack.getNbt();
      if(nbt.contains(ELYTRA_FRAGILE)){
        return nbt.getBoolean(ELYTRA_FRAGILE);
      }
    }
    return false;
  }

  public static ItemStack restore(ItemStack fragileElytra){
    if(!isFragile(fragileElytra)){
      return fragileElytra;
    }
    NbtCompound nbt = fragileElytra.getOrCreateNbt();
    nbt.putBoolean(ELYTRA_FRAGILE, false);
    fragileElytra.setDamage(fragileElytra.getMaxDamage() - 1);
    NbtList lore = nbt.getList(LORE_KEY, NbtElement.STRING_TYPE);
    NbtList newLore = new NbtList();
    for(int i = 0; i < lore.size(); i++){
      String l = lore.getString(i);
      if(!Objects.equals(l, FRAGILE_LORE)){
        newLore.add(NbtString.of(l));
      }
    }
    nbt.put(LORE_KEY, newLore);
    fragileElytra.setRepairCost(0);
    if(!hasCustomName(fragileElytra)){
      fragileElytra.removeCustomName();
    }
    return fragileElytra;
  }

  public static ItemStack create(){
    ItemStack item = new ItemStack(Items.ELYTRA);
    NbtCompound nbt = item.getOrCreateNbt();
    NbtList lore = new NbtList();
    lore.add(NbtString.of(FRAGILE_LORE));
    nbt.put(LORE_KEY, lore);
    item.setDamage(Items.ELYTRA.getMaxDamage());
    item.setRepairCost(Integer.MAX_VALUE);
    item.setCustomName(Text.of(FRAGILE_NAME));
    nbt.putBoolean(ELYTRA_FRAGILE, true);
    return item;
  }
}
