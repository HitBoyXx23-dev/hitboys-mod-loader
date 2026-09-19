/*    */ package meteordevelopment.meteorclient.systems.modules.render;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*    */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*    */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*    */ import meteordevelopment.meteorclient.utils.entity.SortPriority;
/*    */ import meteordevelopment.meteorclient.utils.entity.TargetUtils;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CityESP
/*    */   extends Module
/*    */ {
/* 26 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*    */ 
/*    */ 
/*    */   
/* 30 */   private final Setting<ShapeMode> shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/* 31 */       .name("shape-mode"))
/* 32 */       .description("How the shapes are rendered."))
/* 33 */       .defaultValue(ShapeMode.Both))
/* 34 */       .build());
/*    */ 
/*    */   
/* 37 */   private final Setting<SettingColor> sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/* 38 */       .name("side-color"))
/* 39 */       .description("The side color of the rendering."))
/* 40 */       .defaultValue(new SettingColor(225, 0, 0, 75))
/* 41 */       .build());
/*    */ 
/*    */   
/* 44 */   private final Setting<SettingColor> lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/* 45 */       .name("line-color"))
/* 46 */       .description("The line color of the rendering."))
/* 47 */       .defaultValue(new SettingColor(225, 0, 0, 255))
/* 48 */       .build());
/*    */   
/*    */   private class_2338 target;
/*    */ 
/*    */   
/*    */   public CityESP() {
/* 54 */     super(Categories.Render, "city-esp", "Displays blocks that can be broken in order to city another player.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 59 */     class_1657 targetEntity = TargetUtils.getPlayerTarget(this.mc.field_1724.method_55754() + 2.0D, SortPriority.LowestDistance);
/*    */     
/* 61 */     if (TargetUtils.isBadTarget(targetEntity, this.mc.field_1724.method_55754() + 2.0D)) {
/* 62 */       this.target = null;
/*    */     } else {
/* 64 */       this.target = EntityUtils.getCityBlock(targetEntity);
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onRender(Render3DEvent event) {
/* 70 */     if (this.target == null)
/*    */       return; 
/* 72 */     event.renderer.box(this.target, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\CityESP.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */