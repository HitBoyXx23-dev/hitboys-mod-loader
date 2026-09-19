/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_10799;
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3414;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_368;
/*     */ import net.minecraft.class_374;
/*     */ import net.minecraft.class_5251;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeteorToast
/*     */   implements class_368
/*     */ {
/*  29 */   private static final int TITLE_COLOR = Color.fromRGBA(145, 61, 226, 255);
/*  30 */   private static final int TEXT_COLOR = Color.fromRGBA(220, 220, 220, 255);
/*  31 */   private static final class_2960 TEXTURE = class_2960.method_60654("toast/advancement");
/*     */   private static final long DEFAULT_DURATION = 6000L;
/*  33 */   private static final class_1113 DEFAULT_SOUND = (class_1113)class_1109.method_4757((class_3414)class_3417.field_14725.comp_349(), 1.2F, 1.0F);
/*     */   @NotNull
/*     */   private final class_2561 title;
/*     */   @Nullable
/*     */   private final class_2561 text;
/*     */   @Nullable
/*     */   private final class_1799 icon;
/*     */   @Nullable
/*     */   private final class_1113 customSound;
/*     */   private final long duration;
/*     */   private boolean playedSound;
/*  44 */   private long start = -1L;
/*  45 */   private class_368.class_369 visibility = class_368.class_369.field_2209;
/*     */   
/*     */   private MeteorToast(Builder builder) {
/*  48 */     this.title = builder.title;
/*  49 */     this.text = builder.text;
/*  50 */     this.icon = builder.icon;
/*  51 */     this.customSound = builder.customSound;
/*  52 */     this.duration = builder.duration;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     @NotNull
/*     */     private final class_2561 title;
/*     */     @Nullable
/*  59 */     private class_1113 customSound = MeteorToast.DEFAULT_SOUND; @Nullable private class_2561 text; @Nullable
/*  60 */     private class_1799 icon; private long duration = 6000L;
/*     */     
/*     */     public Builder(@NotNull String title) {
/*  63 */       this.title = (class_2561)class_2561.method_43470(title).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717(MeteorToast.TITLE_COLOR)));
/*     */     }
/*     */     
/*     */     public Builder text(@Nullable String text) {
/*  67 */       this
/*     */         
/*  69 */         .text = (text != null && !text.trim().isEmpty()) ? (class_2561)class_2561.method_43470(text).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717(MeteorToast.TEXT_COLOR))) : null;
/*  70 */       return this;
/*     */     }
/*     */     
/*     */     public Builder icon(@Nullable class_1792 item) {
/*  74 */       this.icon = (item != null) ? item.method_7854() : null;
/*  75 */       return this;
/*     */     }
/*     */     
/*     */     public Builder sound(@Nullable class_1113 sound) {
/*  79 */       this.customSound = sound;
/*  80 */       return this;
/*     */     }
/*     */     
/*     */     public Builder duration(long duration) {
/*  84 */       this.duration = Math.max(0L, duration);
/*  85 */       return this;
/*     */     }
/*     */     
/*     */     public MeteorToast build() {
/*  89 */       return new MeteorToast(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class_368.class_369 method_61988() {
/*  95 */     return this.visibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_61989(class_374 manager, long time) {
/* 100 */     if (this.start == -1L) this.start = time;
/*     */     
/* 102 */     this.visibility = (time - this.start >= this.duration) ? class_368.class_369.field_2209 : class_368.class_369.field_2210;
/*     */     
/* 104 */     if (!this.playedSound) {
/* 105 */       MeteorClient.mc.method_1483().method_4873(this.customSound);
/* 106 */       this.playedSound = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_1986(class_332 context, class_327 textRenderer, long startTime) {
/* 112 */     context.method_52706(class_10799.field_56883, TEXTURE, 0, 0, method_29049(), method_29050());
/*     */     
/* 114 */     int textX = (this.icon != null) ? 28 : 12;
/* 115 */     int titleY = 12;
/*     */     
/* 117 */     if (this.text != null) {
/* 118 */       context.method_51439(textRenderer, this.text, textX, 18, TEXT_COLOR, false);
/* 119 */       titleY = 7;
/*     */     } 
/*     */     
/* 122 */     context.method_51439(textRenderer, this.title, textX, titleY, TITLE_COLOR, false);
/*     */     
/* 124 */     if (this.icon != null) context.method_51427(this.icon, 8, 8); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\MeteorToast.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */