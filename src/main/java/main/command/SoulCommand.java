package main.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import main.capability.SoulCapability;
import main.network.ModNetwork;
import main.network.SoulSyncPacket;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber
public class SoulCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("soul")
                        ///soul set
                        .then(Commands.literal("set")
                                .then(Commands.argument("amount", FloatArgumentType.floatArg(-100, 100))
                                        .executes(ctx -> {
                                            float amount = FloatArgumentType.getFloat(ctx, "amount");
                                            ServerPlayerEntity player = ctx.getSource().getPlayerOrException();

                                            player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
                                                souls.setSouls(amount);
                                            });

                                            ctx.getSource().sendSuccess(
                                                    new StringTextComponent("Soul set to: " + amount)
                                                            .withStyle(TextFormatting.AQUA), false
                                            );

                                            player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
                                                souls.setSouls(amount);

                                                ModNetwork.CHANNEL.sendTo(
                                                        new SoulSyncPacket(souls.getSouls()),
                                                        player.connection.connection,
                                                        NetworkDirection.PLAY_TO_CLIENT
                                                );
                                            });
                                            return 1;
                                        })
                                )
                        )
                        ///soul add
                        .then(Commands.literal("add")
                                .then(Commands.argument("amount", FloatArgumentType.floatArg(-100, 100))
                                        .executes(ctx -> {
                                            float amount = FloatArgumentType.getFloat(ctx, "amount");
                                            ServerPlayerEntity player = ctx.getSource().getPlayerOrException();

                                            player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
                                                souls.addSouls(amount);
                                            });

                                            ctx.getSource().sendSuccess(
                                                    new StringTextComponent("Soul added: " + amount)
                                                            .withStyle(TextFormatting.AQUA), false
                                            );

                                            player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
                                                souls.setSouls(amount);

                                                ModNetwork.CHANNEL.sendTo(
                                                        new SoulSyncPacket(souls.getSouls()),
                                                        player.connection.connection,
                                                        NetworkDirection.PLAY_TO_CLIENT
                                                );
                                            });
                                            return 1;
                                        })
                                )
                        )
                        // /soul get — узнать текущее значение
                        .then(Commands.literal("get")
                                .executes(ctx -> {
                                    ServerPlayerEntity player = ctx.getSource().getPlayerOrException();

                                    player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
                                        ctx.getSource().sendSuccess(
                                                new StringTextComponent("Soul: " + souls.getSouls())
                                                        .withStyle(TextFormatting.AQUA), false
                                        );
                                    });
                                    return 1;
                                })
                        )
        );
    }
}