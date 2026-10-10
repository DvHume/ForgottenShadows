package main.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import main.config.ModConfig;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfigScreen extends Screen {

    private final Screen lastScreen;

    public ModConfigScreen(Screen lastScreen) {
        super(new TranslationTextComponent("gui.frs.config.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 6;

        this.addButton(createToggleButton(
                centerX - 100, startY, 200, 20,
                "gui.frs.config.desert_sunburn",
                ModConfig.ENABLE_DESERT_SUNBURN,
                null
        ));

        this.addButton(createToggleButton(
                centerX - 100, startY + 25, 200, 20,
                "gui.frs.config.enable_meteors",
                ModConfig.ENABLE_METEORS,
                button -> {
                    boolean currentState = ModConfig.ENABLE_METEORS.get();

                    // Если метеориты включены -> запрашиваем подтверждение перед выключением
                    if (currentState) {
                        this.minecraft.setScreen(new ConfirmScreen(
                                (confirmed) -> {
                                    if (confirmed) {
                                        ModConfig.ENABLE_METEORS.set(false);
                                        ModConfig.SPEC.save();
                                    }
                                    // Возвращение на экран настроек
                                    this.minecraft.setScreen(this);
                                },
                                new TranslationTextComponent("gui.frs.config.meteor_warn.title"),
                                new TranslationTextComponent("gui.frs.config.meteor_warn.desc")
                        ));
                    } else {
                        // Если они были выключены просто включаем
                        ModConfig.ENABLE_METEORS.set(true);
                        ModConfig.SPEC.save();
                        button.setMessage(getToggleText("gui.frs.config.enable_meteors", true));
                    }
                }
        ));

        // Изменение фатальной дозы
        this.addButton(createStepperButton(centerX - 100, startY + 55, 95, 20, "-0.5", -0.5D));
        this.addButton(createStepperButton(centerX + 5, startY + 55, 95, 20, "+0.5", 0.5D));

        //Кнопка "Готово"
        this.addButton(new Button(centerX - 100, this.height - 30, 200, 20,
                new TranslationTextComponent("gui.done"),
                btn -> this.onClose()));
    }

    private Button createToggleButton(int x, int y, int width, int height,
                                      String translationKey,
                                      ForgeConfigSpec.BooleanValue configValue,
                                      Button.IPressable customPressAction) {
        return new Button(
                x, y, width, height,
                getToggleText(translationKey, configValue.get()),
                button -> {
                    if (customPressAction != null) {
                        customPressAction.onPress(button);
                    } else {
                        boolean newValue = !configValue.get();
                        configValue.set(newValue);
                        ModConfig.SPEC.save();
                        button.setMessage(getToggleText(translationKey, newValue));
                    }
                }
        );
    }

    private IFormattableTextComponent getToggleText(String translationKey, boolean state) {
        String stateKey = state ? "gui.frs.config.enabled" : "gui.frs.config.disabled";
        return new TranslationTextComponent(translationKey)
                .append(": ")
                .append(new TranslationTextComponent(stateKey));
    }

    private Button createStepperButton(int x, int y, int width, int height, String label, double delta) {
        return new Button(x, y, width, height, new StringTextComponent(label), button -> {
            double current = ModConfig.FATAL_RADIATION_DOSE.get();
            double updated = Math.max(0.5D, Math.min(50.0D, current + delta));
            ModConfig.FATAL_RADIATION_DOSE.set(updated);
            ModConfig.SPEC.save();
        });
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 15, 0xFFFFFF);

        String doseFormatted = String.format(java.util.Locale.US, "%.1f Rad", ModConfig.FATAL_RADIATION_DOSE.get());
        TranslationTextComponent doseText = new TranslationTextComponent("gui.frs.config.fatal_dose");
        drawCenteredString(matrixStack, this.font, doseText.getString() + ": " + doseFormatted, this.width / 2, this.height / 6 + 85, 0xAAAAAA);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.lastScreen);
        }
    }
}
