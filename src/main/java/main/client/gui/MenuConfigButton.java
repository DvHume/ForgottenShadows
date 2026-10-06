package main.client.gui;

import main.client.gui.ModConfigScreen;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class MenuConfigButton {

    @SubscribeEvent
    public static void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof IngameMenuScreen) {
            IngameMenuScreen gui = (IngameMenuScreen) event.getGui();

            int x = gui.width / 2 + 104;
            int y = gui.height / 4 + 96 -16;
            int width = 60;
            int height = 20;

            event.addWidget(new Button(x, y, width, height,
                    new StringTextComponent("FRS CONFIG"),
                    button -> {
                        if (gui.getMinecraft() != null) {
                             gui.getMinecraft().setScreen(new ModConfigScreen(gui));
                        }
                    }));
        }
    }
}
