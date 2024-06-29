package me.levansj01.verus.data.transaction.effects;

public interface Effectable {
   EffectUpdate toEffect();

   int getId();
}
