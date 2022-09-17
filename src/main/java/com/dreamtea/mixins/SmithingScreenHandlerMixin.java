package com.dreamtea.mixins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

import static com.dreamtea.imixin.IFragile.create;
import static com.dreamtea.imixin.IFragile.pressItem;
import static com.dreamtea.imixin.IFragile.pressQty;

@Mixin(SmithingScreenHandler.class)
public abstract class SmithingScreenHandlerMixin extends ForgingScreenHandler {

  private boolean pressingElytra = false;
  public SmithingScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
    super(type, syncId, playerInventory, context);
  }

  @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
  public void pressElytra(CallbackInfo ci){
    if(this.input.getStack(0).isOf(Items.ELYTRA) && this.input.getStack(1).isOf(pressItem)){
      ItemStack ingredient = this.input.getStack(1);
      if(ingredient.getCount() >= pressQty){
        this.output.setStack(0, create());
        this.pressingElytra = true;
        ci.cancel();
        return;
      }
    }
    pressingElytra = false;
  }

  @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
  public void canTakePressedElytra(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cir){
    if(pressingElytra){
      cir.setReturnValue(true);
    }
  }

  @Inject(method = "onTakeOutput", at = @At("HEAD"), cancellable = true)
  public void takePressElytra(CallbackInfo ci){
    if(pressingElytra){
      ItemStack ingredient = this.input.getStack(1);
      ingredient.decrement(pressQty);
      this.context.run((world, pos) -> world.syncWorldEvent(WorldEvents.SMITHING_TABLE_USED, (BlockPos)pos, 0));
      ci.cancel();
    }
  }

}
