package com.alcantara.cafe_management_server.repository;

import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

public interface User32 extends com.sun.jna.platform.win32.User32 {
    User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

    int GetWindowText(HWND hWnd, char[] lpString, int nMaxCount);

    HWND GetForegroundWindow();
}
