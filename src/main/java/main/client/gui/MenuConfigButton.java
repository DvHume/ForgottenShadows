package main.handler;

import main.ForgottenShadows;
import main.client.gui.ModConfigScreen;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class MenuConfigButton {

    private static final ResourceLocation CONFIG_ICON = new ResourceLocation(ForgottenShadows.MOD_ID, "textures/gui/config.png");

    @SubscribeEvent
    public static void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof IngameMenuScreen) {
            IngameMenuScreen gui = (IngameMenuScreen) event.getGui();

            int x = gui.width / 2 + 104;
            int y = gui.height / 4 + 96 - 16;
            int width = 20;
            int height = 20;

            ImageButton iconButton = new ImageButton(
                    x, y, width, height,
                    0, -1,
                    19,
                    CONFIG_ICON,
                    20, 40,
                    button -> {
                        if (gui.getMinecraft() != null) {
                            gui.getMinecraft().setScreen(new ModConfigScreen(gui));
                        }
                    },
                    (button, matrixStack, mouseX, mouseY) -> {
                        gui.renderTooltip(matrixStack, new TranslationTextComponent("gui.frs.config.button_tooltip"), mouseX, mouseY);
                    },
                    new StringTextComponent("")
            );

            event.addWidget(iconButton);
        }
    }
}
