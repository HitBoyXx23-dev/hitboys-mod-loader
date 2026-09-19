/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.InventoryTweaks;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.BetterTooltips;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.ItemHighlight;
/*     */ import net.minecraft.class_11907;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_1703;
/*     */ import net.minecraft.class_1713;
/*     */ import net.minecraft.class_1735;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_3936;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_465;
/*     */ import net.minecraft.class_5684;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
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
/*     */ @Mixin({class_465.class})
/*     */ public abstract class HandledScreenMixin<T extends class_1703>
/*     */   extends class_437
/*     */   implements class_3936<T>
/*     */ {
/*     */   @Shadow
/*     */   protected class_1735 field_2787;
/*     */   @Shadow
/*     */   protected int field_2776;
/*     */   @Shadow
/*     */   protected int field_2800;
/*     */   @Shadow
/*     */   private boolean field_2783;
/*     */   
/*     */   public HandledScreenMixin(class_2561 title) {
/*  64 */     super(title);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_25426"}, at = {@At("TAIL")})
/*     */   private void onInit(CallbackInfo info) {
/*  69 */     InventoryTweaks invTweaks = (InventoryTweaks)Modules.get().get(InventoryTweaks.class);
/*     */     
/*  71 */     if (invTweaks.isActive() && invTweaks.showButtons() && invTweaks.canSteal((class_1703)method_17577())) {
/*  72 */       method_37063((class_364)(new class_4185.class_7840(
/*  73 */             (class_2561)class_2561.method_43470("Steal"), button -> invTweaks.steal((class_1703)method_17577())))
/*  74 */           .method_46433(this.field_2776, this.field_2800 - 22)
/*  75 */           .method_46437(40, 20)
/*  76 */           .method_46431());
/*     */ 
/*     */       
/*  79 */       method_37063((class_364)(new class_4185.class_7840(
/*  80 */             (class_2561)class_2561.method_43470("Dump"), button -> invTweaks.dump((class_1703)method_17577())))
/*  81 */           .method_46433(this.field_2776 + 42, this.field_2800 - 22)
/*  82 */           .method_46437(40, 20)
/*  83 */           .method_46431());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_25403"}, at = {@At("TAIL")})
/*     */   private void onMouseDragged(class_11909 click, double offsetX, double offsetY, CallbackInfoReturnable<Boolean> cir) {
/*  91 */     if (click.method_74245() != 0 || this.field_2783 || !((InventoryTweaks)Modules.get().get(InventoryTweaks.class)).mouseDragItemMove())
/*     */       return; 
/*  93 */     class_1735 slot = method_64240(click.comp_4798(), click.comp_4799());
/*  94 */     if (slot != null && slot.method_7681() && MeteorClient.mc.method_74187()) method_2383(slot, slot.field_7874, click.method_74245(), class_1713.field_7794);
/*     */   
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_25402"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void mouseClicked(class_11909 click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
/* 100 */     BetterTooltips tooltips = (BetterTooltips)Modules.get().get(BetterTooltips.class);
/*     */     
/* 102 */     if (tooltips.shouldOpenContents((class_11907)click) && this.field_2787 != null && !this.field_2787.method_7677().method_7960() && method_17577().method_34255().method_7960() && 
/* 103 */       tooltips.openContent(this.field_2787.method_7677())) {
/* 104 */       cir.setReturnValue(Boolean.valueOf(true));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_25404"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void keyPressed(class_11908 input, CallbackInfoReturnable<Boolean> cir) {
/* 112 */     BetterTooltips tooltips = (BetterTooltips)Modules.get().get(BetterTooltips.class);
/*     */     
/* 114 */     if (tooltips.shouldOpenContents((class_11907)input) && this.field_2787 != null && !this.field_2787.method_7677().method_7960() && method_17577().method_34255().method_7960() && 
/* 115 */       tooltips.openContent(this.field_2787.method_7677())) {
/* 116 */       cir.setReturnValue(Boolean.valueOf(true));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_2385"}, at = {@At("HEAD")})
/*     */   private void onDrawSlot(class_332 context, class_1735 slot, int mouseX, int mouseY, CallbackInfo ci) {
/* 124 */     int color = ((ItemHighlight)Modules.get().get(ItemHighlight.class)).getColor(slot.method_7677());
/* 125 */     if (color != -1) context.method_25294(slot.field_7873, slot.field_7872, slot.field_7873 + 16, slot.field_7872 + 16, color); 
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_62001"}, at = {@At("RETURN")})
/*     */   private boolean isTooltipSticky(boolean original, class_1799 item) {
/* 130 */     class_5684 class_5684 = (class_5684)item.method_32347().orElse(null); if (class_5684 instanceof class_5684) { class_5684 component = class_5684;
/* 131 */       return (original || component.method_62003()); }
/*     */ 
/*     */     
/* 134 */     return original;
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   @Nullable
/*     */   protected abstract class_1735 method_64240(double paramDouble1, double paramDouble2);
/*     */   
/*     */   @Shadow
/*     */   public abstract T method_17577();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_2383(class_1735 paramclass_1735, int paramInt1, int paramInt2, class_1713 paramclass_1713);
/*     */   
/*     */   @Shadow
/*     */   public abstract void method_25419();
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\HandledScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */