/*    */ package meteordevelopment.meteorclient.events.meteor;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModuleBindChangedEvent
/*    */ {
/* 11 */   private static final ModuleBindChangedEvent INSTANCE = new ModuleBindChangedEvent();
/*    */   
/*    */   public Module module;
/*    */   
/*    */   public static ModuleBindChangedEvent get(Module module) {
/* 16 */     INSTANCE.module = module;
/* 17 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\meteor\ModuleBindChangedEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */