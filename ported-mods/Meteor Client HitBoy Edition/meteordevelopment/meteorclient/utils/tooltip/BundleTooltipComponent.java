/*     */ package meteordevelopment.meteorclient.utils.tooltip;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.class_10799;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_5684;
/*     */ import net.minecraft.class_8001;
/*     */ import net.minecraft.class_9276;
/*     */ import net.minecraft.class_9334;
/*     */ import org.apache.commons.lang3.math.Fraction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BundleTooltipComponent
/*     */   implements class_5684, MeteorTooltipData
/*     */ {
/*  25 */   private static final class_2960 BUNDLE_SLOT_BACKGROUND_TEXTURE = class_2960.method_60656("container/bundle/slot_background");
/*  26 */   private static final class_2960 BUNDLE_PROGRESS_BAR_BORDER_TEXTURE = class_2960.method_60656("container/bundle/bundle_progressbar_border");
/*  27 */   private static final class_2960 BUNDLE_PROGRESS_BAR_FILL_TEXTURE = class_2960.method_60656("container/bundle/bundle_progressbar_fill");
/*  28 */   private static final class_2960 BUNDLE_PROGRESS_BAR_FULL_TEXTURE = class_2960.method_60656("container/bundle/bundle_progressbar_full");
/*  29 */   private static final class_2960 BUNDLE_SLOT_HIGHLIGHT_BACK_TEXTURE = class_2960.method_60656("container/bundle/slot_highlight_back");
/*  30 */   private static final class_2960 BUNDLE_SLOT_HIGHLIGHT_FRONT_TEXTURE = class_2960.method_60656("container/bundle/slot_highlight_front");
/*     */   
/*     */   private static final int SLOTS_PER_ROW = 8;
/*     */   private static final int SLOT_DIMENSION = 24;
/*     */   private static final int ROW_WIDTH = 208;
/*     */   private static final int PROGRESS_BAR_WIDTH = 94;
/*     */   private static final int PROGRESS_BAR_HEIGHT = 13;
/*  37 */   private static final class_2561 BUNDLE_FULL = (class_2561)class_2561.method_43471("item.minecraft.bundle.full");
/*     */   
/*     */   private final class_1799[] items;
/*     */   private final class_9276 bundleContents;
/*     */   private final int width;
/*     */   private final int height;
/*     */   
/*     */   public BundleTooltipComponent(class_1799[] items, class_9276 bundleContents) {
/*  45 */     this.items = items;
/*  46 */     this.bundleContents = bundleContents;
/*     */     
/*  48 */     int rows = (items.length + 8 - 1) / 8;
/*  49 */     this.width = 208;
/*  50 */     this.height = 8 + rows * 24 + 8 + 13 + 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_5684 getComponent() {
/*  55 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int method_32661(class_327 textRenderer) {
/*  60 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int method_32664(class_327 textRenderer) {
/*  65 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_62003() {
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_32666(class_327 textRenderer, int x, int y, int width, int height, class_332 context) {
/*  75 */     int row = 0;
/*  76 */     int col = 0;
/*     */     
/*  78 */     for (class_1799 itemStack : this.items) {
/*  79 */       if (!itemStack.method_7960()) {
/*  80 */         int slotX = x + 8 + col * 24;
/*  81 */         int slotY = y + 8 + row * 24;
/*     */         
/*  83 */         context.method_52706(class_10799.field_56883, BUNDLE_SLOT_BACKGROUND_TEXTURE, slotX, slotY, 24, 24);
/*  84 */         drawItem(itemStack, row * 8 + col, slotX, slotY, textRenderer, context);
/*  85 */         context.method_51431(textRenderer, itemStack, slotX + 4, slotY + 4);
/*     */       } 
/*     */       
/*  88 */       col++;
/*  89 */       if (col >= 8) {
/*  90 */         col = 0;
/*  91 */         row++;
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     drawSelectedItemTooltip(textRenderer, context, x, y, width);
/*     */     
/*  97 */     int progressBarX = x + (this.width - 94) / 2;
/*  98 */     int progressBarY = y + this.height - 13 - 4;
/*  99 */     drawProgressBar(progressBarX, progressBarY, textRenderer, context);
/*     */   }
/*     */   
/*     */   private void drawItem(class_1799 itemStack, int index, int x, int y, class_327 textRenderer, class_332 drawContext) {
/* 103 */     boolean bl = (this.bundleContents.method_61668() == index);
/* 104 */     if (bl) {
/* 105 */       drawContext.method_52706(class_10799.field_56883, BUNDLE_SLOT_HIGHLIGHT_BACK_TEXTURE, x, y, 24, 24);
/*     */     } else {
/* 107 */       drawContext.method_52706(class_10799.field_56883, BUNDLE_SLOT_BACKGROUND_TEXTURE, x, y, 24, 24);
/*     */     } 
/*     */     
/* 110 */     drawContext.method_51428(itemStack, x + 4, y + 4, 0);
/* 111 */     drawContext.method_51431(textRenderer, itemStack, x + 4, y + 4);
/* 112 */     if (bl) {
/* 113 */       drawContext.method_52706(class_10799.field_56883, BUNDLE_SLOT_HIGHLIGHT_FRONT_TEXTURE, x, y, 24, 24);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawSelectedItemTooltip(class_327 textRenderer, class_332 drawContext, int x, int y, int width) {
/* 118 */     if (this.bundleContents.method_61669()) {
/* 119 */       class_1799 itemStack = this.bundleContents.method_57422(this.bundleContents.method_61668());
/* 120 */       class_2561 text = itemStack.method_63015();
/* 121 */       int i = textRenderer.method_30880(text.method_30937());
/* 122 */       int j = x + width / 2 - 12;
/* 123 */       class_5684 tooltipComponent = class_5684.method_32662(text.method_30937());
/* 124 */       drawContext.method_51435(textRenderer, 
/* 125 */           List.of(tooltipComponent), j - i / 2, y - 37, class_8001.field_41687, (class_2960)itemStack.method_58694(class_9334.field_54198));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawProgressBar(int x, int y, class_327 textRenderer, class_332 context) {
/* 131 */     int fillAmount = class_3532.method_15340(class_3532.method_59515(this.bundleContents.method_57428(), 94), 0, 94);
/*     */ 
/*     */ 
/*     */     
/* 135 */     class_2960 fillTexture = (this.bundleContents.method_57428().compareTo(Fraction.ONE) >= 0) ? BUNDLE_PROGRESS_BAR_FULL_TEXTURE : BUNDLE_PROGRESS_BAR_FILL_TEXTURE;
/*     */     
/* 137 */     context.method_52706(class_10799.field_56883, fillTexture, x + 1, y, fillAmount, 13);
/* 138 */     context.method_52706(class_10799.field_56883, BUNDLE_PROGRESS_BAR_BORDER_TEXTURE, x, y, 94, 13);
/*     */     
/* 140 */     class_2561 label = getProgressBarLabel();
/* 141 */     if (label != null) {
/* 142 */       context.method_27534(textRenderer, label, x + 47, y + 3, -1);
/*     */     }
/*     */   }
/*     */   
/*     */   private class_2561 getProgressBarLabel() {
/* 147 */     return (this.bundleContents.method_57428().compareTo(Fraction.ONE) >= 0) ? BUNDLE_FULL : (class_2561)class_2561.method_43470(String.format("%.2f%%", new Object[] { Float.valueOf(this.bundleContents.method_57428().floatValue() * 100.0F) }));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\tooltip\BundleTooltipComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */