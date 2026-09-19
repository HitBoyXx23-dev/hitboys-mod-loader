/*     */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*     */ 
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.screens.EditSystemScreen;
/*     */ import meteordevelopment.meteorclient.settings.Settings;
/*     */ import meteordevelopment.meteorclient.systems.macros.Macro;
/*     */ import meteordevelopment.meteorclient.systems.macros.Macros;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EditMacroScreen
/*     */   extends EditSystemScreen<Macro>
/*     */ {
/*     */   public EditMacroScreen(GuiTheme theme, Macro value, Runnable reload) {
/*  89 */     super(theme, value, reload);
/*     */   }
/*     */ 
/*     */   
/*     */   public Macro create() {
/*  94 */     return new Macro();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean save() {
/*  99 */     if (((String)((Macro)this.value).name.get()).isBlank() || ((List)((Macro)this.value).messages
/* 100 */       .get()).isEmpty()) {
/* 101 */       return false;
/*     */     }
/* 103 */     if (this.isNew) {
/* 104 */       for (Macro m : Macros.get()) {
/* 105 */         if (((Macro)this.value).equals(m)) return false;
/*     */       
/*     */       } 
/*     */     }
/* 109 */     if (this.isNew) { Macros.get().add((Macro)this.value); }
/* 110 */     else { Macros.get().save(); }
/*     */     
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Settings getSettings() {
/* 117 */     return ((Macro)this.value).settings;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\MacrosTab$EditMacroScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */