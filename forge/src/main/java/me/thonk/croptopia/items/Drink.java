package me.thonk.croptopia.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;


public class Drink extends Item {


    public Drink(Properties settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return getDrinkSound();
    }


    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }

        if (playerEntity != null) {
            playerEntity.addStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.abilities.isCreativeMode) {
                if (isFood()) {
                    user.onFoodEaten(world, stack);
                }
            }
        }

        if (playerEntity == null || !playerEntity.abilities.isCreativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }
}
