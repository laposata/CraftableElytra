package com.dreamtea.imixin;

import com.dreamtea.item.AlternateItemStack;

public interface IAlternateState {
  public void storeAlternate(AlternateItemStack alt);
  public AlternateItemStack getAlternate();
}
