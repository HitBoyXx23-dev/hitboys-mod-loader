/*    */ package meteordevelopment.meteorclient.systems.modules.render;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
/*    */ import meteordevelopment.meteorclient.events.render.RenderBossBarEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_345;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BossStack
/*    */   extends Module
/*    */ {
/* 24 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 26 */   public final Setting<Boolean> stack = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 27 */       .name("stack"))
/* 28 */       .description("Stacks boss bars and adds a counter to the text."))
/* 29 */       .defaultValue(Boolean.valueOf(true)))
/* 30 */       .build());
/*    */ 
/*    */   
/* 33 */   public final Setting<Boolean> hideName = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 34 */       .name("hide-name"))
/* 35 */       .description("Hides the names of boss bars."))
/* 36 */       .defaultValue(Boolean.valueOf(false)))
/* 37 */       .build());
/*    */ 
/*    */   
/* 40 */   private final Setting<Double> spacing = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 41 */       .name("bar-spacing"))
/* 42 */       .description("The spacing reduction between each boss bar."))
/* 43 */       .defaultValue(10.0D)
/* 44 */       .min(0.0D)
/* 45 */       .build());
/*    */ 
/*    */   
/* 48 */   public static final Map<class_345, Integer> barMap = new WeakHashMap<>();
/*    */   
/*    */   public BossStack() {
/* 51 */     super(Categories.Render, "boss-stack", "Stacks boss bars to make your HUD less cluttered.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onFetchText(RenderBossBarEvent.BossText event) {
/* 56 */     if (((Boolean)this.hideName.get()).booleanValue()) {
/* 57 */       event.name = (class_2561)class_2561.method_43473(); return;
/*    */     } 
/* 59 */     if (barMap.isEmpty() || !((Boolean)this.stack.get()).booleanValue())
/* 60 */       return;  class_345 bar = event.bossBar;
/* 61 */     Integer integer = barMap.get(bar);
/* 62 */     barMap.remove(bar);
/* 63 */     if (integer != null && !((Boolean)this.hideName.get()).booleanValue()) event.name = (class_2561)event.name.method_27661().method_27693(" x" + integer); 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onSpaceBars(RenderBossBarEvent.BossSpacing event) {
/* 68 */     event.spacing = ((Double)this.spacing.get()).intValue();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onGetBars(RenderBossBarEvent.BossIterator event) {
/* 73 */     if (((Boolean)this.stack.get()).booleanValue()) {
/* 74 */       HashMap<String, class_345> chosenBarMap = new HashMap<>();
/* 75 */       event.iterator.forEachRemaining(bar -> {
/*    */             String name = bar.method_5414().getString();
/*    */             if (chosenBarMap.containsKey(name)) {
/*    */               barMap.compute((class_345)chosenBarMap.get(name), ());
/*    */             } else {
/*    */               chosenBarMap.put(name, bar);
/*    */             } 
/*    */           });
/* 83 */       event.iterator = chosenBarMap.values().iterator();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\BossStack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */