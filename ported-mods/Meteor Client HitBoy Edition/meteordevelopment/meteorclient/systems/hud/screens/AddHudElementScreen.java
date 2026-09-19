/*     */ package meteordevelopment.meteorclient.systems.hud.screens;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPlus;
/*     */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudGroup;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddHudElementScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   private final int x;
/*     */   private final int y;
/*     */   private final WTextBox searchBar;
/*     */   private Object firstObject;
/*     */   
/*     */   public AddHudElementScreen(GuiTheme theme, int x, int y) {
/*  36 */     super(theme, "Add Hud element");
/*     */     
/*  38 */     this.x = x;
/*  39 */     this.y = y;
/*     */     
/*  41 */     this.searchBar = theme.textBox("");
/*  42 */     this.searchBar.action = (() -> {
/*     */         clear();
/*     */         
/*     */         initWidgets();
/*     */       });
/*  47 */     this.enterAction = (() -> runObject(this.firstObject));
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  52 */     this.firstObject = null;
/*     */ 
/*     */     
/*  55 */     add((WWidget)this.searchBar).expandX();
/*  56 */     this.searchBar.setFocused(true);
/*     */ 
/*     */     
/*  59 */     Hud hud = Hud.get();
/*  60 */     Map<HudGroup, List<Item>> grouped = new HashMap<>();
/*     */     
/*  62 */     for (HudElementInfo<?> info : (Iterable<HudElementInfo<?>>)hud.infos.values()) {
/*  63 */       if (info.hasPresets() && !this.searchBar.get().isEmpty()) {
/*  64 */         for (HudElementInfo<?>.Preset preset : info.presets) {
/*  65 */           String title = info.title + "  -  " + info.title;
/*  66 */           if (Utils.searchTextDefault(title, this.searchBar.get(), false)) ((List<Item>)grouped.computeIfAbsent(info.group, hudGroup -> new ArrayList())).add(new Item(title, info.description, preset)); 
/*     */         }  continue;
/*     */       } 
/*  69 */       if (Utils.searchTextDefault(info.title, this.searchBar.get(), false)) ((List<Item>)grouped.computeIfAbsent(info.group, hudGroup -> new ArrayList())).add(new Item(info.title, info.description, info));
/*     */     
/*     */     } 
/*     */     
/*  73 */     for (HudGroup group : grouped.keySet()) {
/*  74 */       WSection section = (WSection)add((WWidget)this.theme.section(group.title())).expandX().widget();
/*     */       
/*  76 */       for (Item item : grouped.get(group)) {
/*  77 */         WHorizontalList l = (WHorizontalList)section.add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */         
/*  79 */         WLabel title = (WLabel)l.add((WWidget)this.theme.label(item.title)).widget();
/*  80 */         title.tooltip = item.description;
/*     */         
/*  82 */         Object object = item.object; if (object instanceof HudElementInfo.Preset) { HudElementInfo<?>.Preset preset = (HudElementInfo.Preset)object;
/*  83 */           WPlus add = (WPlus)l.add((WWidget)this.theme.plus()).expandCellX().right().widget();
/*  84 */           add.action = (() -> runObject(preset));
/*     */           
/*  86 */           if (this.firstObject == null) this.firstObject = preset; 
/*     */           continue; }
/*     */         
/*  89 */         HudElementInfo<?> info = (HudElementInfo)item.object;
/*     */         
/*  91 */         if (info.hasPresets()) {
/*  92 */           WButton open = (WButton)l.add((WWidget)this.theme.button(" > ")).expandCellX().right().widget();
/*  93 */           open.action = (() -> runObject(info));
/*     */         } else {
/*     */           
/*  96 */           WPlus add = (WPlus)l.add((WWidget)this.theme.plus()).expandCellX().right().widget();
/*  97 */           add.action = (() -> runObject(info));
/*     */         } 
/*     */         
/* 100 */         if (this.firstObject == null) this.firstObject = info;
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runObject(Object object) {
/* 107 */     if (object == null)
/* 108 */       return;  if (object instanceof HudElementInfo.Preset) { HudElementInfo<?>.Preset preset = (HudElementInfo.Preset)object;
/* 109 */       Hud.get().add(preset, this.x, this.y);
/* 110 */       method_25419(); }
/*     */     else
/*     */     
/* 113 */     { HudElementInfo<?> info = (HudElementInfo)object;
/*     */       
/* 115 */       if (info.hasPresets()) {
/* 116 */         HudElementPresetsScreen screen = new HudElementPresetsScreen(this.theme, info, this.x, this.y);
/* 117 */         screen.parent = this.parent;
/*     */         
/* 119 */         MeteorClient.mc.method_1507((class_437)screen);
/*     */       } else {
/*     */         
/* 122 */         Hud.get().add(info, this.x, this.y);
/* 123 */         method_25419();
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onRenderBefore(class_332 drawContext, float delta) {
/* 130 */     HudEditorScreen.renderElements(drawContext);
/*     */   }
/*     */   private static final class Item extends Record { private final String title; private final String description; private final Object object;
/* 133 */     private Item(String title, String description, Object object) { this.title = title; this.description = description; this.object = object; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/systems/hud/screens/AddHudElementScreen$Item;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #133	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 133 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/hud/screens/AddHudElementScreen$Item; } public String title() { return this.title; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/systems/hud/screens/AddHudElementScreen$Item;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #133	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/hud/screens/AddHudElementScreen$Item; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/systems/hud/screens/AddHudElementScreen$Item;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #133	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/systems/hud/screens/AddHudElementScreen$Item;
/* 133 */       //   0	8	1	o	Ljava/lang/Object; } public String description() { return this.description; } public Object object() { return this.object; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\screens\AddHudElementScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */