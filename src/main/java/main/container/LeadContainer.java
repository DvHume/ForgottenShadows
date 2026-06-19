package main.container;

import main.init.ModContainers;
import main.init.ModItems;
import main.item.other.RadioactiveItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class LeadContainer extends Container {

    private final ItemStack containerStack;
    private final ItemStackHandler itemHandler;

    public LeadContainer(int id, PlayerInventory playerInv, PacketBuffer buf) {
        this(id, playerInv, playerInv.player.getMainHandItem(), new ItemStackHandler(10));
    }

    public LeadContainer(int id, PlayerInventory playerInv, ItemStack stack, ItemStackHandler handler) {
        super(ModContainers.LEAD_CONTAINER.get(), id);
        this.containerStack = stack;
        this.itemHandler = handler;

        int slotIndex = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = 30 + col * 18;
                int y = 17 + row * 18;

                this.addSlot(new SlotItemHandler(handler, slotIndex, x, y) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        // Фильтр: Слот примет предмет ТОЛЬКО если это чистый радиоактивный элемент.
                        // Обычные заражённые вещи (с тегом InfectedRad) проверку не пройдут.
                        // Проверка только на предметы из класса радиоактивных,
                        return stack.getItem() instanceof RadioactiveItem;
                    }
                });
                slotIndex++;
            }
        }

        this.addSlot(new SlotItemHandler(handler, 9, 134, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                // В этот слот можно класть только банки с раствором
                return stack.getItem() == ModItems.BOTTLE_SOLUTION.get();
            }

            @Override
            public void setChanged() {
                super.setChanged();

                ItemStack fluidBottle = this.getItem();
                if (!containerStack.isEmpty() && fluidBottle.getItem() == ModItems.BOTTLE_SOLUTION.get()) {
                    CompoundNBT nbt = containerStack.getOrCreateTag();
                    float currentFluid = nbt.getFloat("FluidBuffer");

                    if (currentFluid <= 800.0F) {
                        nbt.putFloat("FluidBuffer", currentFluid + 200.0F);
                        this.set(new ItemStack(ModItems.EMPTY_BOTTLE_SOLUTION.get(), 1));
                        nbt.put("Inventory", handler.serializeNBT());
                        broadcastChanges();
                    }
                }
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        // Контейнер валиден, пока предмет лежит в руке у игрока и не сломался
        return !this.containerStack.isEmpty() && (player.getMainHandItem() == this.containerStack || player.getOffhandItem() == this.containerStack);
    }

    // Стандартный метод майнкрафта для шифт-клика
    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            // Если Из контейнера в инв игрока:
            if (index < 10) {
                if (!this.moveItemStackTo(itemstack1, 10, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            // Если Из инв игрока в контейнер:
            else {
                // Если это раствор — пытаемся засунуть в 9 слот
                if (itemstack1.getItem() == ModItems.BOTTLE_SOLUTION.get()) {
                    if (!this.moveItemStackTo(itemstack1, 9, 10, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // Если это радиоактивный элемент — пытаемся засунуть в сетку 3х3
                else if (itemstack1.getItem() instanceof RadioactiveItem) {
                    if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // Если игрок пытается шифтануть обычную вещь — ничего не делает
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }
}