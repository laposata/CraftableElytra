package com.dreamtea.mixins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.dreamtea.imixin.IFragile.ELYTRA_FRAGILE;
import static com.dreamtea.imixin.IFragile.isFragile;
import static com.dreamtea.imixin.IFragile.repairItem;
import static com.dreamtea.imixin.IFragile.repairQty;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {

  @Inject(method = "isUsable", at = @At("RETURN"), cancellable = true)
  private static void isUsable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
    if(cir.getReturnValue()){
      NbtCompound nbt = stack.getOrCreateNbt();
      if(nbt.contains(ELYTRA_FRAGILE)){
       cir.setReturnValue(!nbt.getBoolean(ELYTRA_FRAGILE));
      }
    }
  }

  @Inject(method = "canRepair", at = @At("HEAD"), cancellable = true)
  public void needForRepair(ItemStack stack, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir){
    if(isFragile(stack)){
      cir.setReturnValue(ingredient.isOf(repairItem) && ingredient.getCount() == repairQty);
    }
  }

  @Inject(method = "use", at = @At("HEAD"), cancellable = true)
  public void dontUseFragile(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
    ItemStack itemStack = user.getStackInHand(hand);
    if(isFragile(itemStack)){
      cir.setReturnValue(TypedActionResult.fail(itemStack));
    }
  }


}
