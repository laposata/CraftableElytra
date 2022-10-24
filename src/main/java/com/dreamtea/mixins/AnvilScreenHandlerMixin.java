package com.dreamtea.mixins;

import com.dreamtea.item.AlternateItemStack;
import com.dreamtea.recipes.ElytraRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
  @Shadow @Final private Property levelCost;
  @Shadow private int repairItemUsage;
  @Shadow private String newItemName;
  private boolean repairingFragileElytra = false;

  public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
    super(type, syncId, playerInventory, context);
  }

  @Inject(method = "updateResult", at = @At("RETURN"), cancellable = true)
  public void pressElytra(CallbackInfo ci){
    if(ElytraRecipe.canCraft(this.input.getStack(0), this.input.getStack(1))) {
      ItemStack result = ElytraRecipe.BROKEN_ELYTRA.copy();
      this.output.setStack(0, result);
      this.repairingFragileElytra = true;
      this.levelCost.set(ElytraRecipe.levels);
      this.repairItemUsage = ElytraRecipe.secondary_count;
      String customName = AlternateItemStack.getCustomName(this.input.getStack(0));
      if(customName != null){
        this.newItemName = customName;
      }
      if (StringUtils.isBlank(this.newItemName)) {
        if (customName != null) {
          result.removeCustomName();
        }
      } else if(!this.newItemName.equals(result.getName().toString())){
        result.setCustomName(Text.literal(this.newItemName));
      }
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
  @Inject(method = "onTakeOutput", at = @At("TAIL"), cancellable = true)
  public void onTakePressedElytra(PlayerEntity player, ItemStack stack, CallbackInfo ci){
    repairingFragileElytra = false;
  }
}
