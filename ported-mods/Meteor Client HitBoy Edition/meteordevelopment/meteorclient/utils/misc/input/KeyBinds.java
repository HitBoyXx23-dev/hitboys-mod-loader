/*    */ package meteordevelopment.meteorclient.utils.misc.input;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import net.minecraft.class_304;
/*    */ import net.minecraft.class_3675;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyBinds
/*    */ {
/* 14 */   private static final class_304.class_11900 CATEGORY = class_304.class_11900.method_74698(MeteorClient.identifier("meteor-client"));
/*    */   
/* 16 */   public static class_304 OPEN_GUI = new class_304("key.meteor-client.open-gui", class_3675.class_307.field_1668, 344, CATEGORY);
/* 17 */   public static class_304 OPEN_COMMANDS = new class_304("key.meteor-client.open-commands", class_3675.class_307.field_1668, 46, CATEGORY);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class_304[] apply(class_304[] binds) {
/* 24 */     class_304[] newBinds = new class_304[binds.length + 2];
/*    */     
/* 26 */     System.arraycopy(binds, 0, newBinds, 0, binds.length);
/* 27 */     newBinds[binds.length] = OPEN_GUI;
/* 28 */     newBinds[binds.length + 1] = OPEN_COMMANDS;
/*    */     
/* 30 */     return newBinds;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\input\KeyBinds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */