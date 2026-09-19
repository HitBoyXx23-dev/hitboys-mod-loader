/*    */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
/*    */ import meteordevelopment.meteorclient.systems.macros.Macro;
/*    */ import meteordevelopment.meteorclient.systems.macros.Macros;
/*    */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*    */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*    */ import net.minecraft.class_437;
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
/*    */ class MacrosScreen
/*    */   extends WindowTabScreen
/*    */ {
/*    */   public MacrosScreen(GuiTheme theme, Tab tab) {
/* 42 */     super(theme, tab);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 47 */     WTable table = (WTable)add((WWidget)this.theme.table()).expandX().minWidth(400.0D).widget();
/* 48 */     initTable(table);
/*    */     
/* 50 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/*    */     
/* 52 */     WButton create = (WButton)add((WWidget)this.theme.button("Create")).expandX().widget();
/* 53 */     create.action = (() -> MeteorClient.mc.method_1507((class_437)new MacrosTab.EditMacroScreen(this.theme, null, this::reload)));
/*    */   }
/*    */   
/*    */   private void initTable(WTable table) {
/* 57 */     table.clear();
/* 58 */     if (Macros.get().isEmpty())
/*    */       return; 
/* 60 */     for (Macro macro : Macros.get()) {
/* 61 */       table.add((WWidget)this.theme.label((String)macro.name.get() + " (" + (String)macro.name.get() + ")"));
/*    */       
/* 63 */       WButton edit = (WButton)table.add((WWidget)this.theme.button(GuiRenderer.EDIT)).expandCellX().right().widget();
/* 64 */       edit.action = (() -> MeteorClient.mc.method_1507((class_437)new MacrosTab.EditMacroScreen(this.theme, macro, this::reload)));
/*    */       
/* 66 */       WConfirmedMinus remove = (WConfirmedMinus)table.add((WWidget)this.theme.confirmedMinus()).widget();
/* 67 */       remove.action = (() -> {
/*    */           Macros.get().remove(macro);
/*    */           
/*    */           reload();
/*    */         });
/* 72 */       table.row();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean toClipboard() {
/* 78 */     return NbtUtils.toClipboard((ISerializable)Macros.get());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean fromClipboard() {
/* 83 */     return NbtUtils.fromClipboard((ISerializable)Macros.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\MacrosTab$MacrosScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */