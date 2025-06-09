package com.example.servernoticeboard.command;

import com.example.servernoticeboard.NoticeManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.server.MinecraftServer;

import static net.minecraft.server.command.CommandManager.*;

public class NoticeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("notice")
            .then(literal("help").executes(ctx -> {
                ctx.getSource().sendFeedback(new LiteralText("用法: /notice set|list|clear|send|sendall|reload"), false);
                return 1;
            }))
            .then(literal("list").executes(ctx -> {
                for (String msg : NoticeManager.getNotices()) {
                    ctx.getSource().sendFeedback(new LiteralText(msg), false);
                }
                return 1;
            }))
            .then(literal("clear").requires(src -> src.hasPermissionLevel(2)).executes(ctx -> {
                NoticeManager.clear();
                ctx.getSource().sendFeedback(new LiteralText("公告已清空"), false);
                return 1;
            }))
            .then(literal("send")
                .then(argument("player", EntityArgumentType.player())
                .then(argument("message", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "player");
                        String msg = StringArgumentType.getString(ctx, "message");
                        player.sendMessage(new LiteralText(msg), false);
                        return 1;
                    }))))
            .then(literal("sendall")
                .then(argument("message", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        String msg = StringArgumentType.getString(ctx, "message");
                        MinecraftServer server = ctx.getSource().getServer();
                        server.getPlayerManager().broadcast(new LiteralText(msg), false);
                        return 1;
                    })))
            .then(literal("set")
                .then(argument("message", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        String msg = StringArgumentType.getString(ctx, "message");
                        NoticeManager.add(msg);
                        ctx.getSource().sendFeedback(new LiteralText("添加成功"), false);
                        return 1;
                    })))
            .then(literal("reload").requires(src -> src.hasPermissionLevel(2)).executes(ctx -> {
                NoticeManager.load();
                ctx.getSource().sendFeedback(new LiteralText("配置重新加载成功"), false);
                return 1;
            }))
        );
    }
}