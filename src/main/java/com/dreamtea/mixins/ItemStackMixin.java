package com.dreamtea.mixins;

import com.dreamtea.imixin.IAlternateState;
import com.dreamtea.item.AlternateItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IAlternateState {
  AlternateItemStack alternateItemStack;

  @Inject(method = "isFood", at = @At("RETURN"), cancellable = true)
  public void isFood(CallbackInfoReturnable<Boolean> cir){
    if(alternateItemStack != null && alternateItemStack.getFood() != null){
      cir.setReturnValue(alternateItemStack.getFood());
    }
  }
  @Override
  public void storeAlternate(AlternateItemStack alt) {
    this.alternateItemStack = alt;
  }

  @Override
  public AlternateItemStack getAlternate() {
    return alternateItemStack;
  }
}
