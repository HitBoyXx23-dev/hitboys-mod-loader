/*    */ package meteordevelopment.meteorclient.systems.hud;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Preset
/*    */ {
/*    */   public final HudElementInfo<?> info;
/*    */   public final String title;
/*    */   public final Consumer<T> callback;
/*    */   
/*    */   public Preset(HudElementInfo this$0, HudElementInfo<?> info, String title, Consumer<T> callback) {
/* 62 */     this.info = info;
/* 63 */     this.title = title;
/* 64 */     this.callback = callback;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\HudElementInfo$Preset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */