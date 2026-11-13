package main.handler;

import main.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber

public class FlamingPickaxeHandler {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if ((event.getPlayer() != null)) return;
        PlayerEntity player = event.getPlayer();
        ItemStack held = player.getMainHandItem();

        // Checking that is this a FlamingPickaxe
        if (held.getItem() != ModItems.FLAMING_PICKAXE.get()) return;

        CompoundNBT nbt = held.getOrCreateTag();
        if (!nbt.getBoolean("smelting")) return;

        World world = (World) event.getWorld();
        if (world.isClientSide) return;

        BlockPos pos = event.getPos();
        BlockState state = event.getState();

        Block.getDrops(state, (ServerWorld) world, pos, world.getBlockEntity(pos))
                .forEach(drop -> {
            Inventory inv = new Inventory(1);
            inv.setItem(0, drop);

                    Optional<FurnaceRecipe> recipe = world.getRecipeManager().getRecipeFor(IRecipeType.SMELTING, inv, world);

                    if (recipe.isPresent()) {
                        ItemStack result = recipe.get().getResultItem().copy();
                        ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, result);
                        world.addFreshEntity(entity);
                    } else {
                        ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
                        world.addFreshEntity(entity);
                    }
        });

        world.removeBlock(pos, false);
    }
}
