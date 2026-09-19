/*    */ package meteordevelopment.meteorclient.systems.hud;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HudElementInfo<T extends HudElement>
/*    */ {
/*    */   public final HudGroup group;
/*    */   public final String name;
/*    */   public final String title;
/*    */   public final String description;
/*    */   public final Supplier<T> factory;
/*    */   public final List<Preset> presets;
/*    */   
/*    */   public HudElementInfo(HudGroup group, String name, String title, String description, Supplier<T> factory) {
/* 26 */     this.group = group;
/* 27 */     this.name = name;
/* 28 */     this.title = title;
/* 29 */     this.description = description;
/*    */     
/* 31 */     this.factory = factory;
/* 32 */     this.presets = new ArrayList<>();
/*    */   }
/*    */   
/*    */   public HudElementInfo(HudGroup group, String name, String description, Supplier<T> factory) {
/* 36 */     this(group, name, Utils.nameToTitle(name), description, factory);
/*    */   }
/*    */   
/*    */   public Preset addPreset(String title, Consumer<T> callback) {
/* 40 */     Preset preset = new Preset(this, this, title, callback);
/*    */     
/* 42 */     this.presets.add(preset);
/* 43 */     this.presets.sort(Comparator.comparing(p -> p.title));
/*    */     
/* 45 */     return preset;
/*    */   }
/*    */   
/*    */   public boolean hasPresets() {
/* 49 */     return !this.presets.isEmpty();
/*    */   }
/*    */   
/*    */   public HudElement create() {
/* 53 */     return (HudElement)this.factory.get();
/*    */   }
/*    */   
/*    */   public class Preset {
/*    */     public final HudElementInfo<?> info;
/*    */     public final String title;
/*    */     public final Consumer<T> callback;
/*    */     
/*    */     public Preset(HudElementInfo this$0, HudElementInfo<?> info, String title, Consumer<T> callback) {
/* 62 */       this.info = info;
/* 63 */       this.title = title;
/* 64 */       this.callback = callback;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\HudElementInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */