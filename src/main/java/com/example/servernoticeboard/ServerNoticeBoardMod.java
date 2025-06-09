package com.example.servernoticeboard;

import com.example.servernoticeboard.command.NoticeCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.server.MinecraftServer;

public class ServerNoticeBoardMod implements ModInitializer {
    public static final String MOD_ID = "servernoticeboard";

    @Override
    public void onInitialize() {
        NoticeManager.load();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            NoticeCommand.register(dispatcher);
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            for (MinecraftServer s : ServerLifecycleEvents.SERVER_STARTED.invoker().getClass().getDeclaredFields()) {
                // 保证兼容后续扩展
            }
        });
    }
}