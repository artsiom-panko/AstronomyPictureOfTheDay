package com.panko.apod.util;

import com.panko.apod.entity.Picture;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

/**
 * This is the core class, which updates wallpaper image on Windows OS.
 *
 * <p> Uses Java Native Access (JNA), which provider easy access to Windows native shared libraries.
 *
 * <p> Was Tested on Windows 8, 10, 11.
 *
 * <p> <a href="https://github.com/java-native-access/jna">JNA docs</a>
 */
public class WallpaperChanger {
    private interface User32 extends Library {
        User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean SystemParametersInfo(int one, int two, String s, int three);
    }

    public static void setScreenImage(Picture picture) {
        User32.INSTANCE.SystemParametersInfo(0x0014, 0, picture.getLocalPath(), 1);
    }
}