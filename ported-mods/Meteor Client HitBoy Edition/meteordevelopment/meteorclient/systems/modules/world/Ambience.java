/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.Dimension;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ambience
/*     */   extends Module
/*     */ {
/*  21 */   private final SettingGroup sgSky = this.settings.createGroup("Sky");
/*  22 */   private final SettingGroup sgWorld = this.settings.createGroup("World");
/*     */ 
/*     */ 
/*     */   
/*  26 */   public final Setting<Boolean> endSky = this.sgSky.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  27 */       .name("end-sky"))
/*  28 */       .description("Makes the sky like the end."))
/*  29 */       .defaultValue(Boolean.valueOf(false)))
/*  30 */       .build());
/*     */ 
/*     */   
/*  33 */   public final Setting<Boolean> customSkyColor = this.sgSky.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  34 */       .name("custom-sky-color"))
/*  35 */       .description("Whether the sky color should be changed."))
/*  36 */       .defaultValue(Boolean.valueOf(false)))
/*  37 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> overworldSkyColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> netherSkyColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> endSkyColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> customCloudColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> cloudColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> changeLightningColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> lightningColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> customGrassColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> grassColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> customFoliageColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> foliageColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> customWaterColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> waterColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> customLavaColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> lavaColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> customFogColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> fogColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ambience() {
/* 179 */     super(Categories.World, "ambience", "Change the color of various pieces of the environment."); Objects.requireNonNull(this.customSkyColor); this.overworldSkyColor = this.sgSky.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("overworld-sky-color")).description("The color of the overworld sky.")).defaultValue(new SettingColor(0, 125, 255)).visible(this.customSkyColor::get)).build()); Objects.requireNonNull(this.customSkyColor); this.netherSkyColor = this.sgSky.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("nether-sky-color")).description("The color of the nether sky.")).defaultValue(new SettingColor(102, 0, 0)).visible(this.customSkyColor::get)).build()); Objects.requireNonNull(this.customSkyColor); this.endSkyColor = this.sgSky.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("end-sky-color")).description("The color of the end sky.")).defaultValue(new SettingColor(65, 30, 90)).visible(this.customSkyColor::get)).build()); this.customCloudColor = this.sgSky.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("custom-cloud-color")).description("Whether the clouds color should be changed.")).defaultValue(Boolean.valueOf(false))).build()); Objects.requireNonNull(this.customCloudColor); this.cloudColor = this.sgSky.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("cloud-color")).description("The color of the clouds.")).defaultValue(new SettingColor(102, 0, 0)).visible(this.customCloudColor::get)).build()); this.changeLightningColor = this.sgSky.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("custom-lightning-color")).description("Whether the lightning color should be changed.")).defaultValue(Boolean.valueOf(false))).build()); Objects.requireNonNull(this.changeLightningColor); this.lightningColor = this.sgSky.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("lightning-color")).description("The color of the lightning.")).defaultValue(new SettingColor(102, 0, 0)).visible(this.changeLightningColor::get)).build()); this.customGrassColor = this.sgWorld.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("custom-grass-color")).description("Whether the grass color should be changed.")).defaultValue(Boolean.valueOf(false))).onChanged(val -> reload())).build()); Objects.requireNonNull(this.customGrassColor); this.grassColor = this.sgWorld.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("grass-color")).description("The color of the grass.")).defaultValue(new SettingColor(102, 0, 0)).visible(this.customGrassColor::get)).onChanged(val -> reload())).build()); this.customFoliageColor = this.sgWorld.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("custom-foliage-color")).description("Whether the foliage color should be changed.")).defaultValue(Boolean.valueOf(false))).onChanged(val -> reload())).build()); Objects.requireNonNull(this.customFoliageColor); this.foliageColor = this.sgWorld.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("foliage-color")).description("The color of the foliage.")).defaultValue(new SettingColor(102, 0, 0)).visible(this.customFoliageColor::get)).onChanged(val -> reload())).build()); this.customWaterColor = this.sgWorld.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("custom-water-color")).description("Whether the water color should be changed.")).defaultValue(Boolean.valueOf(false))).onChanged(val -> reload())).build()); Objects.requireNonNull(this.customWaterColor); this.waterColor = this.sgWorld.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("water-color")).description("The color of the water.")).defaultValue(new SettingColor(102, 0, 0)).visible(this.customWaterColor::get)).onChanged(val -> reload())).build()); this.customLavaColor = this.sgWorld.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("custom-lava-color")).description("Whether the lava color should be changed.")).defaultValue(Boolean.valueOf(false))).onChanged(val -> reload())).build());
/*     */     Objects.requireNonNull(this.customLavaColor);
/*     */     this.lavaColor = this.sgWorld.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("lava-color")).description("The color of the lava.")).defaultValue(new SettingColor(102, 0, 0)).visible(this.customLavaColor::get)).onChanged(val -> reload())).build());
/*     */     this.customFogColor = this.sgWorld.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("custom-fog-color")).description("Whether the fog color should be changed.")).defaultValue(Boolean.valueOf(false))).build());
/*     */     Objects.requireNonNull(this.customFogColor);
/* 184 */     this.fogColor = this.sgWorld.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("fog-color")).description("The color of the fog.")).defaultValue(new SettingColor(102, 0, 0)).visible(this.customFogColor::get)).build()); } public void onActivate() { reload(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 189 */     reload();
/*     */   }
/*     */   
/*     */   private void reload() {
/* 193 */     if (this.mc.field_1769 != null && isActive()) this.mc.field_1769.method_3279(); 
/*     */   }
/*     */   
/*     */   public SettingColor skyColor() {
/* 197 */     switch (PlayerUtils.getDimension()) {
/*     */       case Overworld:
/* 199 */         return (SettingColor)this.overworldSkyColor.get();
/*     */       
/*     */       case Nether:
/* 202 */         return (SettingColor)this.netherSkyColor.get();
/*     */       
/*     */       case End:
/* 205 */         return (SettingColor)this.endSkyColor.get();
/*     */     } 
/*     */ 
/*     */     
/* 209 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\Ambience.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */