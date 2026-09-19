/*     */ package meteordevelopment.meteorclient.systems.hud.elements;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIntPair;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.renderer.Renderer2D;
/*     */ import meteordevelopment.meteorclient.renderer.text.TextRenderer;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnchantmentListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*     */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudElement;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudRenderer;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*     */ import meteordevelopment.meteorclient.utils.entity.SortPriority;
/*     */ import meteordevelopment.meteorclient.utils.entity.TargetUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1887;
/*     */ import net.minecraft.class_1890;
/*     */ import net.minecraft.class_3489;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_9304;
/*     */ import net.minecraft.class_9636;
/*     */ import org.joml.Matrix4fStack;
/*     */ 
/*     */ public class CombatHud
/*     */   extends HudElement {
/*  51 */   private static final Color GREEN = new Color(15, 255, 15);
/*  52 */   private static final Color RED = new Color(255, 15, 15);
/*  53 */   private static final Color BLACK = new Color(0, 0, 0, 255);
/*     */   
/*  55 */   public static final HudElementInfo<CombatHud> INFO = new HudElementInfo(Hud.GROUP, "combat", "Displays information about your combat target.", CombatHud::new);
/*     */   
/*  57 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  58 */   private final SettingGroup sgEnchantments = this.settings.createGroup("Enchantments");
/*  59 */   private final SettingGroup sgHealth = this.settings.createGroup("Health");
/*  60 */   private final SettingGroup sgDistance = this.settings.createGroup("Distance");
/*  61 */   private final SettingGroup sgPing = this.settings.createGroup("Ping");
/*  62 */   private final SettingGroup sgScale = this.settings.createGroup("Scale");
/*  63 */   private final SettingGroup sgBackground = this.settings.createGroup("Background");
/*     */   
/*  65 */   private final Setting<Double> range = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  66 */       .name("range"))
/*  67 */       .description("The range to target players."))
/*  68 */       .defaultValue(100.0D)
/*  69 */       .min(1.0D)
/*  70 */       .sliderMax(200.0D)
/*  71 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private final Setting<SettingColor> healthColor1 = this.sgHealth.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  77 */       .name("health-stage-1"))
/*  78 */       .description("The color on the left of the health gradient."))
/*  79 */       .defaultValue(new SettingColor(255, 15, 15))
/*  80 */       .build());
/*     */ 
/*     */   
/*  83 */   private final Setting<SettingColor> healthColor2 = this.sgHealth.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  84 */       .name("health-stage-2"))
/*  85 */       .description("The color in the middle of the health gradient."))
/*  86 */       .defaultValue(new SettingColor(255, 150, 15))
/*  87 */       .build());
/*     */ 
/*     */   
/*  90 */   private final Setting<SettingColor> healthColor3 = this.sgHealth.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  91 */       .name("health-stage-3"))
/*  92 */       .description("The color on the right of the health gradient."))
/*  93 */       .defaultValue(new SettingColor(15, 255, 15))
/*  94 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private final Setting<Set<class_5321<class_1887>>> displayedEnchantments = this.sgEnchantments.add((Setting)((EnchantmentListSetting.Builder)((EnchantmentListSetting.Builder)(new EnchantmentListSetting.Builder())
/* 100 */       .name("displayed-enchantments"))
/* 101 */       .description("The enchantments that are shown on nametags."))
/* 102 */       .vanillaDefaults()
/* 103 */       .build());
/*     */ 
/*     */   
/* 106 */   private final Setting<SettingColor> enchantmentTextColor = this.sgEnchantments.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/* 107 */       .name("enchantment-color"))
/* 108 */       .description("Color of enchantment text."))
/* 109 */       .defaultValue(new SettingColor(255, 255, 255))
/* 110 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private final Setting<Boolean> displayPing = this.sgPing.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 116 */       .name("ping"))
/* 117 */       .description("Shows the player's ping."))
/* 118 */       .defaultValue(Boolean.valueOf(true)))
/* 119 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> pingColor1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> pingColor2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> pingColor3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> displayDistance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> distColor1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> distColor2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> distColor3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> customScale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> scale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> background;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> backgroundColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_1657 playerEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombatHud() {
/* 220 */     super(INFO); Objects.requireNonNull(this.displayPing); this.pingColor1 = this.sgPing.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("ping-stage-1")).description("Color of ping text when under 75.")).defaultValue(new SettingColor(15, 255, 15)).visible(this.displayPing::get)).build()); Objects.requireNonNull(this.displayPing); this.pingColor2 = this.sgPing.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("ping-stage-2")).description("Color of ping text when between 75 and 200.")).defaultValue(new SettingColor(255, 150, 15)).visible(this.displayPing::get)).build()); Objects.requireNonNull(this.displayPing); this.pingColor3 = this.sgPing.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("ping-stage-3")).description("Color of ping text when over 200.")).defaultValue(new SettingColor(255, 15, 15)).visible(this.displayPing::get)).build()); this.displayDistance = this.sgDistance.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("distance")).description("Shows the distance between you and the player.")).defaultValue(Boolean.valueOf(true))).build()); Objects.requireNonNull(this.displayDistance); this.distColor1 = this.sgDistance.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("distance-stage-1")).description("The color when a player is within 10 blocks of you.")).defaultValue(new SettingColor(255, 15, 15)).visible(this.displayDistance::get)).build()); Objects.requireNonNull(this.displayDistance); this.distColor2 = this.sgDistance.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("distance-stage-2")).description("The color when a player is within 50 blocks of you.")).defaultValue(new SettingColor(255, 150, 15)).visible(this.displayDistance::get)).build()); Objects.requireNonNull(this.displayDistance); this.distColor3 = this.sgDistance.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("distance-stage-3")).description("The color when a player is greater then 50 blocks away from you.")).defaultValue(new SettingColor(15, 255, 15)).visible(this.displayDistance::get)).build()); this.customScale = this.sgScale.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("custom-scale")).description("Applies a custom scale to this hud element.")).defaultValue(Boolean.valueOf(false))).onChanged(aBoolean -> calculateSize())).build()); Objects.requireNonNull(this.customScale); this.scale = this.sgScale.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("scale")).description("Custom scale.")).visible(this.customScale::get)).defaultValue(2.0D).onChanged(aDouble -> calculateSize())).min(0.5D).sliderRange(0.5D, 3.0D).build()); this.background = this.sgBackground.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("background")).description("Displays background.")).defaultValue(Boolean.valueOf(false))).build()); Objects.requireNonNull(this.background);
/*     */     this.backgroundColor = this.sgBackground.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("background-color")).description("Color used for the background.")).visible(this.background::get)).defaultValue(new SettingColor(25, 25, 25, 50)).build());
/* 222 */     calculateSize();
/*     */   }
/*     */   
/*     */   private void calculateSize() {
/* 226 */     setSize(175.0D * getScale(), 95.0D * getScale());
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(HudRenderer renderer) {
/* 231 */     renderer.post(() -> {
/*     */           Color pingColor;
/*     */           Color distColor;
/*     */           double x = this.x;
/*     */           double y = this.y;
/*     */           Color primaryColor = TextHud.getSectionColor(0);
/*     */           Color secondaryColor = TextHud.getSectionColor(1);
/*     */           if (isInEditor()) {
/*     */             this.playerEntity = (class_1657)MeteorClient.mc.field_1724;
/*     */           } else {
/*     */             this.playerEntity = TargetUtils.getPlayerTarget(((Double)this.range.get()).doubleValue(), SortPriority.LowestDistance);
/*     */           } 
/*     */           if (this.playerEntity == null && !isInEditor()) {
/*     */             return;
/*     */           }
/*     */           if (((Boolean)this.background.get()).booleanValue()) {
/*     */             Renderer2D.COLOR.begin();
/*     */             Renderer2D.COLOR.quad(x, y, getWidth(), getHeight(), (Color)this.backgroundColor.get());
/*     */           } 
/*     */           if (this.playerEntity == null) {
/*     */             if (isInEditor()) {
/*     */               renderer.line(x, y, x + getWidth(), y + getHeight(), Color.GRAY);
/*     */               renderer.line(x + getWidth(), y, x, y + getHeight(), Color.GRAY);
/*     */               Renderer2D.COLOR.render();
/*     */             } 
/*     */             return;
/*     */           } 
/*     */           Renderer2D.COLOR.render();
/*     */           renderer.entity((class_1309)this.playerEntity, (int)(x + 5.0D * getScale()), (int)(y + 10.0D * getScale()), (int)(50.0D * getScale()), (int)(60.0D * getScale()), -class_3532.method_15393(this.playerEntity.field_5982 + (this.playerEntity.method_36454() - this.playerEntity.field_5982) * MeteorClient.mc.method_61966().method_60637(true)), -this.playerEntity.method_36455());
/*     */           x += 50.0D * getScale();
/*     */           y += 5.0D * getScale();
/*     */           String breakText = " | ";
/*     */           String nameText = this.playerEntity.method_5477().getString();
/*     */           Color nameColor = PlayerUtils.getPlayerColor(this.playerEntity, primaryColor);
/*     */           int ping = EntityUtils.getPing(this.playerEntity);
/*     */           String pingText = "" + ping + "ms";
/*     */           if (ping <= 75) {
/*     */             pingColor = (Color)this.pingColor1.get();
/*     */           } else if (ping <= 200) {
/*     */             pingColor = (Color)this.pingColor2.get();
/*     */           } else {
/*     */             pingColor = (Color)this.pingColor3.get();
/*     */           } 
/*     */           double dist = 0.0D;
/*     */           if (!isInEditor()) {
/*     */             dist = Math.round(MeteorClient.mc.field_1724.method_5739((class_1297)this.playerEntity) * 100.0D) / 100.0D;
/*     */           }
/*     */           String distText = "" + dist + "m";
/*     */           if (dist <= 10.0D) {
/*     */             distColor = (Color)this.distColor1.get();
/*     */           } else if (dist <= 50.0D) {
/*     */             distColor = (Color)this.distColor2.get();
/*     */           } else {
/*     */             distColor = (Color)this.distColor3.get();
/*     */           } 
/*     */           String friendText = "Unknown";
/*     */           Color friendColor = primaryColor;
/*     */           if (Friends.get().isFriend(this.playerEntity)) {
/*     */             friendText = "Friend";
/*     */             friendColor = (Color)(Config.get()).friendColor.get();
/*     */           } else {
/*     */             boolean naked = true;
/*     */             for (int i = 3; i >= 0; i--) {
/*     */               class_1799 itemStack = getItem(i);
/*     */               if (!itemStack.method_7960()) {
/*     */                 naked = false;
/*     */               }
/*     */             } 
/*     */             if (naked) {
/*     */               friendText = "Naked";
/*     */               friendColor = GREEN;
/*     */             } else {
/*     */               boolean threat = false;
/*     */               for (int j = 5; j >= 0; j--) {
/*     */                 class_1799 itemStack = getItem(j);
/*     */                 if (itemStack.method_31573(class_3489.field_42611) || itemStack.method_7909() == class_1802.field_8301 || itemStack.method_7909() == class_1802.field_23141 || itemStack.method_7909() instanceof net.minecraft.class_1748) {
/*     */                   threat = true;
/*     */                 }
/*     */               } 
/*     */               if (threat) {
/*     */                 friendText = "Threat";
/*     */                 friendColor = RED;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           TextRenderer.get().begin(0.45D * getScale(), false, true);
/*     */           double breakWidth = TextRenderer.get().getWidth(breakText);
/*     */           double pingWidth = TextRenderer.get().getWidth(pingText);
/*     */           double friendWidth = TextRenderer.get().getWidth(friendText);
/*     */           TextRenderer.get().render(nameText, x, y, (nameColor != null) ? nameColor : primaryColor);
/*     */           y += TextRenderer.get().getHeight();
/*     */           TextRenderer.get().render(friendText, x, y, friendColor);
/*     */           if (((Boolean)this.displayPing.get()).booleanValue()) {
/*     */             TextRenderer.get().render(breakText, x + friendWidth, y, secondaryColor);
/*     */             TextRenderer.get().render(pingText, x + friendWidth + breakWidth, y, pingColor);
/*     */             if (((Boolean)this.displayDistance.get()).booleanValue()) {
/*     */               TextRenderer.get().render(breakText, x + friendWidth + breakWidth + pingWidth, y, secondaryColor);
/*     */               TextRenderer.get().render(distText, x + friendWidth + breakWidth + pingWidth + breakWidth, y, distColor);
/*     */             } 
/*     */           } else if (((Boolean)this.displayDistance.get()).booleanValue()) {
/*     */             TextRenderer.get().render(breakText, x + friendWidth, y, secondaryColor);
/*     */             TextRenderer.get().render(distText, x + friendWidth + breakWidth, y, distColor);
/*     */           } 
/*     */           TextRenderer.get().end();
/*     */           y += 10.0D * getScale();
/*     */           int slot = 5;
/*     */           Matrix4fStack matrices = RenderSystem.getModelViewStack();
/*     */           matrices.pushMatrix();
/*     */           matrices.scale((float)getScale(), (float)getScale(), 1.0F);
/*     */           TextRenderer.get().begin(0.35D, false, true);
/*     */           for (int position = 0; position < 6; position++) {
/*     */             double armorX = x + (position * 20) * getScale();
/*     */             double armorY = y;
/*     */             class_1799 itemStack = getItem(slot);
/*     */             renderer.item(itemStack, (int)armorX, (int)armorY, (float)getScale(), true);
/*     */             armorY = y / getScale() + 18.0D;
/*     */             class_9304 enchantments = class_1890.method_57532(itemStack);
/*     */             List<ObjectIntPair<class_6880<class_1887>>> enchantmentsToShow = new ArrayList<>();
/*     */             for (Object2IntMap.Entry<class_6880<class_1887>> entry : (Iterable<Object2IntMap.Entry<class_6880<class_1887>>>)enchantments.method_57539()) {
/*     */               Objects.requireNonNull((Set)this.displayedEnchantments.get());
/*     */               if (((class_6880)entry.getKey()).method_40224((Set)this.displayedEnchantments.get()::contains)) {
/*     */                 enchantmentsToShow.add(new ObjectIntImmutablePair(entry.getKey(), entry.getIntValue()));
/*     */               }
/*     */             } 
/*     */             for (ObjectIntPair<class_6880<class_1887>> entry : enchantmentsToShow) {
/*     */               String enchantName = Utils.getEnchantSimpleName((class_6880)entry.left(), 3) + " " + Utils.getEnchantSimpleName((class_6880)entry.left(), 3);
/*     */               double enchX = x / getScale() + (position * 20) + 8.0D - TextRenderer.get().getWidth(enchantName) / 2.0D;
/*     */               TextRenderer.get().render(enchantName, enchX, armorY, ((class_6880)entry.left()).method_40220(class_9636.field_51551) ? RED : (Color)this.enchantmentTextColor.get());
/*     */               armorY += TextRenderer.get().getHeight();
/*     */             } 
/*     */             slot--;
/*     */           } 
/*     */           TextRenderer.get().end();
/*     */           y = (int)(this.y + 75.0D * getScale());
/*     */           x = this.x;
/*     */           x /= getScale();
/*     */           y /= getScale();
/*     */           x += 5.0D;
/*     */           y += 5.0D;
/*     */           Renderer2D.COLOR.begin();
/*     */           Renderer2D.COLOR.boxLines(x, y, 165.0D, 11.0D, BLACK);
/*     */           Renderer2D.COLOR.render();
/*     */           x += 2.0D;
/*     */           y += 2.0D;
/*     */           float maxHealth = this.playerEntity.method_6063();
/*     */           int maxAbsorb = 16;
/*     */           int maxTotal = (int)(maxHealth + maxAbsorb);
/*     */           int totalHealthWidth = (int)(161.0F * maxHealth / maxTotal);
/*     */           int totalAbsorbWidth = 161 * maxAbsorb / maxTotal;
/*     */           float health = this.playerEntity.method_6032();
/*     */           float absorb = this.playerEntity.method_6067();
/*     */           double healthPercent = (health / maxHealth);
/*     */           double absorbPercent = (absorb / maxAbsorb);
/*     */           int healthWidth = (int)(totalHealthWidth * healthPercent);
/*     */           int absorbWidth = (int)(totalAbsorbWidth * absorbPercent);
/*     */           Renderer2D.COLOR.begin();
/*     */           Renderer2D.COLOR.quad(x, y, healthWidth, 7.0D, (Color)this.healthColor1.get(), (Color)this.healthColor2.get(), (Color)this.healthColor2.get(), (Color)this.healthColor1.get());
/*     */           Renderer2D.COLOR.quad(x + healthWidth, y, absorbWidth, 7.0D, (Color)this.healthColor2.get(), (Color)this.healthColor3.get(), (Color)this.healthColor3.get(), (Color)this.healthColor2.get());
/*     */           Renderer2D.COLOR.render();
/*     */           matrices.popMatrix();
/*     */         });
/*     */   }
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
/*     */   private class_1799 getItem(int i) {
/* 456 */     if (isInEditor()) {
/* 457 */       switch (i) { case 0: case 1: case 2: case 3: case 4: case 5:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 464 */         class_1799.field_8037;
/*     */     } 
/*     */ 
/*     */     
/* 468 */     if (this.playerEntity == null) return class_1799.field_8037;
/*     */     
/* 470 */     switch (i) { case 5: case 4: case 3: case 2: case 1: case 0:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 477 */       class_1799.field_8037;
/*     */   }
/*     */ 
/*     */   
/*     */   private double getScale() {
/* 482 */     return ((Boolean)this.customScale.get()).booleanValue() ? ((Double)this.scale.get()).doubleValue() : Hud.get().getTextScale();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\elements\CombatHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */