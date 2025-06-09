package com.example.servernoticeboard;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class NoticeManager {
    private static final Path NOTICE_FILE = Paths.get("Server Notice Board", "Notice.log");
    private static final List<String> notices = new ArrayList<>();

    public static void load() {
        notices.clear();
        try {
            if (Files.exists(NOTICE_FILE)) {
                notices.addAll(Files.readAllLines(NOTICE_FILE));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(NOTICE_FILE.getParent());
            Files.write(NOTICE_FILE, notices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getNotices() {
        return notices;
    }

    public static void add(String msg) {
        notices.add(msg);
        save();
    }

    public static void clear() {
        notices.clear();
        save();
    }
}