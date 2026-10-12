/*package main.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import main.ForgottenShadows;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.function.Consumer;

public class CustomConfirmScreen extends Screen {

    private final Screen parentScreen;
    private final ITextComponent titleComponent;
    private final ITextComponent descriptionComponent;
    private final List<ItemStack> previewItems;
    private final Consumer<Boolean> callback;

    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(ForgottenShadows.MOD_ID, "textures/gui/container/dispenser.png");

    public CustomConfirmScreen(Screen parentScreen, ITextComponent title, ITextComponent description, List<ItemStack> previewItems, Consumer<Boolean> callback) {
        super(title);
        this.parentScreen = parentScreen;
        this.titleComponent = title;
        this.descriptionComponent = description;
        this.previewItems = previewItems;
        this.callback = callback;
    }

    @Override
    protected void init() {
        super.init();
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Кнопка "Да" справа
        this.addButton(new Button(centerX + 5, centerY + 40, 100, 20,
                new TranslationTextComponent("gui.yes"),
                button -> {
                    this.callback.accept(true);
                    this.minecraft.setScreen(this.parentScreen);
                }));

        // Кнопка "Нет" слева
        this.addButton(new Button(centerX - 105, centerY + 40, 100, 20,
                new TranslationTextComponent("gui.no"),
                button -> {
                    this.callback.accept(false);
                    this.minecraft.setScreen(this.parentScreen);
                }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Рендер затемнения заднего мира
        this.renderBackground(matrixStack);

        // Заголовок окна
        drawCenteredString(matrixStack, this.font, this.titleComponent, this.width / 2, this.height / 2 - 50, 0xFF5555);

        // Описание предупреждение
        drawCenteredString(matrixStack, this.font, this.descriptionComponent, this.width / 2, this.height / 2 - 30, 0xFFFFFF);

        // Если переданы предметы для демонстрации - рисуем для них фон-слоты и сами предметы
        if (previewItems != null && !previewItems.isEmpty()) {
            int startX = this.width / 2 - (previewItems.size() * 18) / 2;
            int startY = this.height / 2 - 5;

            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            // Рендерим слоты для каждого предмета
            for (int i = 0; i < previewItems.size(); i++) {
                int slotX = startX + (i * 18);
                int slotY = startY;

                // Рисуем ванильный слот инвентаря
                this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
                this.blit(matrixStack, slotX, slotY, 7, 16, 18, 18);
            }

            // ItemStack поверх слотов
            for (int i = 0; i < previewItems.size(); i++) {
                int slotX = startX + (i * 18) + 1;
                int slotY = startY + 1;
                this.itemRenderer.renderAndDecorateItem(previewItems.get(i), slotX, slotY);
            }
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        this.callback.accept(false);
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parentScreen);
        }
    }
    }*/
