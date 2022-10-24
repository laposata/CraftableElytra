package com.dreamtea.mixins;

import com.dreamtea.recipes.FragileWingRecipe;
import com.dreamtea.recipes.PairOfWingsRecipe;
import com.dreamtea.recipes.WingRecipe;
import com.google.gson.JsonObject;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.dreamtea.CraftableElytra.specialRecipes;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

  @Inject(method = "<init>", at = @At("TAIL"))
  public void init(CallbackInfo ci){
    FragileWingRecipe.createAll(specialRecipes);
    PairOfWingsRecipe.createAll(specialRecipes);
    WingRecipe.create(specialRecipes);
  }

  @Redirect(
    at = @At(
      value ="INVOKE",
      target = "Lnet/minecraft/recipe/RecipeManager;deserialize(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lnet/minecraft/recipe/Recipe;"
    ),
    method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V"
  )
  public Recipe<?> addRecipes(Identifier id, JsonObject json){
    Recipe<?> special;
    if((special = specialRecipes.get(id)) != null){
      return special;
    }
    return RecipeManager.deserialize(id, json);
  }
}
