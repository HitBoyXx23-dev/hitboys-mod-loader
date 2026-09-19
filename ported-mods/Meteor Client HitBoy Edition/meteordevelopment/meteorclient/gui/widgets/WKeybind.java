/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WKeybind
/*    */   extends WHorizontalList
/*    */ {
/*    */   public Runnable action;
/*    */   public Runnable actionOnSet;
/*    */   private WButton button;
/*    */   private final Keybind keybind;
/*    */   private final Keybind defaultValue;
/*    */   private boolean listening;
/*    */   
/*    */   public WKeybind(Keybind keybind, Keybind defaultValue) {
/* 24 */     this.keybind = keybind;
/* 25 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 30 */     this.button = (WButton)add((WWidget)this.theme.button("")).widget();
/* 31 */     this.button.action = (() -> {
/*    */         this.listening = true;
/*    */         this.button.set("...");
/*    */         if (this.actionOnSet != null) {
/*    */           this.actionOnSet.run();
/*    */         }
/*    */       });
/* 38 */     refreshLabel();
/*    */   }
/*    */   
/*    */   public boolean onClear() {
/* 42 */     if (this.listening) {
/* 43 */       this.keybind.reset();
/* 44 */       reset();
/*    */       
/* 46 */       return true;
/*    */     } 
/*    */     
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   public boolean onAction(boolean isKey, int value, int modifiers) {
/* 53 */     if (this.listening && this.keybind.canBindTo(isKey, value, modifiers)) {
/* 54 */       this.keybind.set(isKey, value, modifiers);
/* 55 */       reset();
/*    */       
/* 57 */       return true;
/*    */     } 
/*    */     
/* 60 */     return false;
/*    */   }
/*    */   
/*    */   public void resetBind() {
/* 64 */     this.keybind.set(this.defaultValue);
/* 65 */     reset();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 69 */     this.listening = false;
/* 70 */     refreshLabel();
/* 71 */     if (Modules.get().isBinding()) {
/* 72 */       Modules.get().setModuleToBind(null);
/*    */     }
/* 74 */     if (this.action != null) this.action.run(); 
/*    */   }
/*    */   
/*    */   private void refreshLabel() {
/* 78 */     this.button.set(this.keybind.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WKeybind.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */