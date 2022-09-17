package com.dreamtea.mixins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.dreamtea.imixin.IFragile.hasCustomName;
import static com.dreamtea.imixin.IFragile.isFragile;
import static com.dreamtea.imixin.IFragile.repairCost;
import static com.dreamtea.imixin.IFragile.repairItem;
import static com.dreamtea.imixin.IFragile.repairQty;
import static com.dreamtea.imixin.IFragile.restore;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
  @Shadow @Final private Property levelCost;
  @Shadow private int repairItemUsage;
  @Shadow private String newItemName;
  private boolean repairingFragileElytra = false;

  public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
    super(type, syncId, playerInventory, context);
  }

  @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
  public void pressElytra(CallbackInfo ci){
    if(isFragile(this.input.getStack(0)) && this.input.getStack(1).isOf(repairItem)){
      ItemStack ingredient = this.input.getStack(1);
      if(ingredient.getCount() >= repairQty){
        ItemStack result = restore(input.getStack(0).copy());
        this.output.setStack(0, result);
        this.repairingFragileElytra = true;
        this.levelCost.set(repairCost);
        this.repairItemUsage = repairQty;
        if (StringUtils.isBlank(this.newItemName)) {
          if (hasCustomName(this.input.getStack(0))) {
            result.removeCustomName();
          }
        } else if (!this.newItemName.equals(this.input.getStack(0).getName().getString())) {
          result.setCustomName(Text.literal(this.newItemName));
        }
        ci.cancel();
        return;
      }
    } else if(isFragile(this.input.getStack(0))) {
      ci.cancel();
    }
    repairingFragileElytra = false;
  }

  @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
  public void canTakePressedElytra(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cir){
    if(repairingFragileElytra && (player.getAbilities().creativeMode || player.experienceLevel >= this.levelCost.get())){
      cir.setReturnValue(true);
    }
  }

}
