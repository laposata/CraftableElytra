package com.dreamtea.mixins;

import com.dreamtea.item.AlternateItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {

  @Inject(method = "isUsable", at = @At("RETURN"), cancellable = true)
  private static void isUsable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
    if(cir.getReturnValue()){
      cir.setReturnValue(!AlternateItemStack.hasAlternate(stack));
    }
  }

  @Inject(method = "canRepair", at = @At("RETURN"), cancellable = true)
  public void needForRepair(ItemStack stack, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir){
    if(cir.getReturnValue()){
      cir.setReturnValue(!AlternateItemStack.hasAlternate(stack));
    }
  }

  @Inject(method = "use", at = @At("HEAD"), cancellable = true)
  public void dontUseFragile(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
    ItemStack itemStack = user.getStackInHand(hand);
    if(AlternateItemStack.hasAlternate(itemStack)){
      cir.setReturnValue(TypedActionResult.fail(itemStack));
    }
  }


}
