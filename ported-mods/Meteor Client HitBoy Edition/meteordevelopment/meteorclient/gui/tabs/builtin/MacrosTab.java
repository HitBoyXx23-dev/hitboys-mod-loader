/*     */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*     */ 
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.screens.EditSystemScreen;
/*     */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*     */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*     */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
/*     */ import meteordevelopment.meteorclient.settings.Settings;
/*     */ import meteordevelopment.meteorclient.systems.macros.Macro;
/*     */ import meteordevelopment.meteorclient.systems.macros.Macros;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ 
/*     */ public class MacrosTab
/*     */   extends Tab
/*     */ {
/*     */   public MacrosTab() {
/*  27 */     super("Macros");
/*     */   }
/*     */ 
/*     */   
/*     */   public TabScreen createScreen(GuiTheme theme) {
/*  32 */     return (TabScreen)new MacrosScreen(theme, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isScreen(class_437 screen) {
/*  37 */     return screen instanceof MacrosScreen;
/*     */   }
/*     */   
/*     */   private static class MacrosScreen extends WindowTabScreen {
/*     */     public MacrosScreen(GuiTheme theme, Tab tab) {
/*  42 */       super(theme, tab);
/*     */     }
/*     */ 
/*     */     
/*     */     public void initWidgets() {
/*  47 */       WTable table = (WTable)add((WWidget)this.theme.table()).expandX().minWidth(400.0D).widget();
/*  48 */       initTable(table);
/*     */       
/*  50 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */       
/*  52 */       WButton create = (WButton)add((WWidget)this.theme.button("Create")).expandX().widget();
/*  53 */       create.action = (() -> MeteorClient.mc.method_1507((class_437)new MacrosTab.EditMacroScreen(this.theme, null, this::reload)));
/*     */     }
/*     */     
/*     */     private void initTable(WTable table) {
/*  57 */       table.clear();
/*  58 */       if (Macros.get().isEmpty())
/*     */         return; 
/*  60 */       for (Macro macro : Macros.get()) {
/*  61 */         table.add((WWidget)this.theme.label((String)macro.name.get() + " (" + (String)macro.name.get() + ")"));
/*     */         
/*  63 */         WButton edit = (WButton)table.add((WWidget)this.theme.button(GuiRenderer.EDIT)).expandCellX().right().widget();
/*  64 */         edit.action = (() -> MeteorClient.mc.method_1507((class_437)new MacrosTab.EditMacroScreen(this.theme, macro, this::reload)));
/*     */         
/*  66 */         WConfirmedMinus remove = (WConfirmedMinus)table.add((WWidget)this.theme.confirmedMinus()).widget();
/*  67 */         remove.action = (() -> {
/*     */             Macros.get().remove(macro);
/*     */             
/*     */             reload();
/*     */           });
/*  72 */         table.row();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean toClipboard() {
/*  78 */       return NbtUtils.toClipboard((ISerializable)Macros.get());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean fromClipboard() {
/*  83 */       return NbtUtils.fromClipboard((ISerializable)Macros.get());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EditMacroScreen extends EditSystemScreen<Macro> {
/*     */     public EditMacroScreen(GuiTheme theme, Macro value, Runnable reload) {
/*  89 */       super(theme, value, reload);
/*     */     }
/*     */ 
/*     */     
/*     */     public Macro create() {
/*  94 */       return new Macro();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean save() {
/*  99 */       if (((String)((Macro)this.value).name.get()).isBlank() || ((List)((Macro)this.value).messages
/* 100 */         .get()).isEmpty()) {
/* 101 */         return false;
/*     */       }
/* 103 */       if (this.isNew) {
/* 104 */         for (Macro m : Macros.get()) {
/* 105 */           if (((Macro)this.value).equals(m)) return false;
/*     */         
/*     */         } 
/*     */       }
/* 109 */       if (this.isNew) { Macros.get().add((Macro)this.value); }
/* 110 */       else { Macros.get().save(); }
/*     */       
/* 112 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public Settings getSettings() {
/* 117 */       return ((Macro)this.value).settings;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\MacrosTab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */