/*    */ package meteordevelopment.meteorclient.systems.hud.screens;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPlus;
/*    */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*    */ import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import net.minecraft.class_332;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ public class HudElementPresetsScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   private final HudElementInfo<?> info;
/*    */   private final int x;
/*    */   private final int y;
/*    */   private final WTextBox searchBar;
/*    */   @Nullable
/*    */   private HudElementInfo<?>.Preset firstPreset;
/*    */   
/*    */   public HudElementPresetsScreen(GuiTheme theme, HudElementInfo<?> info, int x, int y) {
/* 28 */     super(theme, "Select preset for " + info.title);
/*    */     
/* 30 */     this.info = info;
/* 31 */     this.x = x + 9;
/* 32 */     this.y = y;
/*    */     
/* 34 */     this.searchBar = theme.textBox("");
/* 35 */     this.searchBar.action = (() -> {
/*    */         clear();
/*    */         
/*    */         initWidgets();
/*    */       });
/* 40 */     this.enterAction = (() -> {
/*    */         if (this.firstPreset == null)
/*    */           return; 
/*    */         Hud.get().add(this.firstPreset, x, y);
/*    */         method_25419();
/*    */       });
/*    */   }
/*    */   
/*    */   public void initWidgets() {
/* 49 */     this.firstPreset = null;
/*    */ 
/*    */     
/* 52 */     add((WWidget)this.searchBar).expandX();
/* 53 */     this.searchBar.setFocused(true);
/*    */ 
/*    */     
/* 56 */     for (Iterator<HudElementInfo.Preset> iterator = this.info.presets.iterator(); iterator.hasNext(); ) { HudElementInfo<?>.Preset preset = iterator.next();
/* 57 */       if (!Utils.searchTextDefault(preset.title, this.searchBar.get(), false))
/*    */         continue; 
/* 59 */       WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*    */       
/* 61 */       l.add((WWidget)this.theme.label(preset.title));
/*    */       
/* 63 */       WPlus add = (WPlus)l.add((WWidget)this.theme.plus()).expandCellX().right().widget();
/* 64 */       add.action = (() -> {
/*    */           Hud.get().add(preset, this.x, this.y);
/*    */           
/*    */           method_25419();
/*    */         });
/* 69 */       if (this.firstPreset == null) this.firstPreset = preset;
/*    */        }
/*    */   
/*    */   }
/*    */   
/*    */   protected void onRenderBefore(class_332 drawContext, float delta) {
/* 75 */     HudEditorScreen.renderElements(drawContext);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\screens\HudElementPresetsScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */