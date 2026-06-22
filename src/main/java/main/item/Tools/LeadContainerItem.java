package main.item.Tools;

import main.container.LeadContainer;
import main.init.ModItemGroups;
import main.init.ModItems;
import main.item.other.RadioactiveItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;


/**
 * @author DvHume
 *
 * Контейнер хранит ТОЛЬКО радиоактивные элементы
 * @see RadioactiveItem
 * Не принимает никакие другие предметы, в том числе и "заражённые" радиацией
 * Использует раствор для работы.
 * Если раствор заканчивается - отсчитывает время до уничтожения, после чего все предметы внутри начнут влиять на игрока,
 * а потом выпадут т.к контейнер будет разрушен
 * Контейнер НЕ принимает предметы с радиацией >= 10 тыс. И моментально взрывается как только игрок попытается положить такой предмет
 */

public class LeadContainerItem extends Item {

    public LeadContainerItem() {
        super(new Item.Properties().tab(ModItemGroups.MATERIALS).stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            // Загружаем инвентарь из NBT предмета
            ItemStackHandler internalInventory = new ItemStackHandler(10);
            CompoundNBT nbt = stack.getOrCreateTag();
            if (nbt.contains("Inventory")) {
                internalInventory.deserializeNBT(nbt.getCompound("Inventory"));
            }

            // Проверка на критическую массу
            if (hasCriticalMassInside(internalInventory)) {
                world.explode(player, player.getX(), player.getY() + 1.0D, player.getZ(), 6.0F, Explosion.Mode.BREAK);
                player.inventory.setItem(slot, ItemStack.EMPTY);
                return;
            }

            // Трата раствора или обычное разрушение
            if (checkContainerHasRadiation(internalInventory)) {
                float fluid = nbt.getFloat("FluidBuffer");

                if (fluid > 0.0F) {
                    nbt.putFloat("FluidBuffer", Math.max(0.0F, fluid - 0.2F));
                } else {
                    int damage = nbt.getInt("ContainerDamage") + 1;
                    nbt.putInt("ContainerDamage", damage);

                    // Стандартная поломка(без раствора)
                    if (damage >= 70) {
                        dropContainerItems(player, internalInventory);
                        player.inventory.setItem(slot, ItemStack.EMPTY);
                        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && player instanceof ServerPlayerEntity) {
            CompoundNBT nbt = stack.getOrCreateTag();
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent("container.frs.lead_container");
                }

                @Nullable
                @Override
                public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    ItemStackHandler handler = new ItemStackHandler(10);
                    if (nbt.contains("Inventory")) {
                        handler.deserializeNBT(nbt.getCompound("Inventory"));
                    }
                        return new LeadContainer(i, playerInventory, stack, handler) {
                        @Override
                            public void broadcastChanges() {
                            super.broadcastChanges();
                            stack.getOrCreateTag().put("Inventory", handler.serializeNBT());
                        }
                    };
                }
            }, buffer -> buffer.writeItem(stack));

        }
        return ActionResult.success(stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack stack, ItemStack stack2, boolean slotChanged) {
        return slotChanged;
    }

    // Метод: Ищет внутри контейнера критический элемент >= 10k Rad
    private boolean hasCriticalMassInside(ItemStackHandler inventory) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof RadioactiveItem) {
                RadioactiveItem radItem = (RadioactiveItem) stack.getItem();
                if (radItem.getRadiationPerSec() >= 10000.0F) {
                    return true; // Опаа..
                }
            }
        }
        return false;
    }

    private boolean checkContainerHasRadiation(ItemStackHandler inv) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof RadioactiveItem) {
                return true;
            }
        }
        return false;
    }

    // Дроп вещей(не при взрыве)
    private void dropContainerItems(PlayerEntity player, ItemStackHandler inv) {
        World world = player.level;
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack stack = inv. getStackInSlot(i);
            if (!stack.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(world, player.getX(), player.getY() + 0.5D, player.getZ(), stack.copy());
                itemEntity.setPickUpDelay(40);
                world.addFreshEntity(itemEntity);
            }
        }
    }
}
