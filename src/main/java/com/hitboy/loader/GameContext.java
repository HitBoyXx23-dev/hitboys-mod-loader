package com.hitboy.loader;

public class GameContext {
    
    private static Object minecraft;
    private static int fps;
    private static double playerX, playerY, playerZ;
    
    public static void setMinecraft(Object mc) {
        minecraft = mc;
    }
    
    public static Object getMinecraft() {
        return minecraft;
    }
    
    public static void setFps(int fps) {
        GameContext.fps = fps;
    }
    
    public static int getFps() {
        return fps;
    }
    
    public static void setPlayerPosition(double x, double y, double z) {
        playerX = x;
        playerY = y;
        playerZ = z;
    }
    
    public static double getPlayerX() {
        return playerX;
    }
    
    public static double getPlayerY() {
        return playerY;
    }
    
    public static double getPlayerZ() {
        return playerZ;
    }
}
