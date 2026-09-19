/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_345;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BossText
/*    */ {
/* 15 */   private static final BossText INSTANCE = new BossText();
/*    */   
/*    */   public class_345 bossBar;
/*    */   public class_2561 name;
/*    */   
/*    */   public static BossText get(class_345 bossBar, class_2561 name) {
/* 21 */     INSTANCE.bossBar = bossBar;
/* 22 */     INSTANCE.name = name;
/* 23 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\RenderBossBarEvent$BossText.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */