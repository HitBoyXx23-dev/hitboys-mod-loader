/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.BetterBeacons;
/*    */ import net.minecraft.class_1291;
/*    */ import net.minecraft.class_1661;
/*    */ import net.minecraft.class_1703;
/*    */ import net.minecraft.class_1704;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2580;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_437;
/*    */ import net.minecraft.class_465;
/*    */ import net.minecraft.class_466;
/*    */ import net.minecraft.class_6880;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_466.class})
/*    */ public abstract class BeaconScreenMixin
/*    */   extends class_465<class_1704>
/*    */ {
/*    */   @Shadow
/*    */   protected abstract <T extends net.minecraft.class_339> void method_37076(T paramT);
/*    */   
/*    */   public BeaconScreenMixin(class_1704 handler, class_1661 inventory, class_2561 title) {
/* 36 */     super((class_1703)handler, inventory, title);
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_25426"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;clear()V", shift = At.Shift.AFTER)}, cancellable = true)
/*    */   private void changeButtons(CallbackInfo ci) {
/* 41 */     if (!((BetterBeacons)Modules.get().get(BetterBeacons.class)).isActive())
/* 42 */       return;  List<class_6880<class_1291>> effects = class_2580.field_11801.stream().flatMap(Collection::stream).toList();
/* 43 */     class_437 class_437 = (class_310.method_1551()).field_1755; if (class_437 instanceof class_466) { class_466 beaconScreen = (class_466)class_437;
/* 44 */       Objects.requireNonNull(beaconScreen); method_37076(new class_466.class_468(beaconScreen, this.field_2776 + 164, this.field_2800 + 107));
/* 45 */       Objects.requireNonNull(beaconScreen); method_37076(new class_466.class_467(beaconScreen, this.field_2776 + 190, this.field_2800 + 107));
/*    */       
/* 47 */       for (int x = 0; x < 3; x++) {
/* 48 */         for (int y = 0; y < 2; y++) {
/* 49 */           class_6880<class_1291> effect = effects.get(x * 2 + y);
/* 50 */           int xMin = this.field_2776 + x * 25;
/* 51 */           int yMin = this.field_2800 + y * 25;
/* 52 */           Objects.requireNonNull(beaconScreen); method_37076(new class_466.class_469(beaconScreen, xMin + 27, yMin + 32, effect, true, -1));
/* 53 */           Objects.requireNonNull(beaconScreen); class_466.class_469 secondaryWidget = new class_466.class_469(beaconScreen, xMin + 133, yMin + 32, effect, false, 3);
/* 54 */           if (((class_1704)method_17577()).method_17373() != 4) secondaryWidget.field_22763 = false; 
/* 55 */           method_37076(secondaryWidget);
/*    */         } 
/*    */       }  }
/*    */     
/* 59 */     ci.cancel();
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_2389"}, at = {@At("TAIL")})
/*    */   private void onDrawBackground(class_332 context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
/* 64 */     if (!((BetterBeacons)Modules.get().get(BetterBeacons.class)).isActive())
/*    */       return; 
/* 66 */     context.method_25294(this.field_2776 + 10, this.field_2800 + 7, this.field_2776 + 220, this.field_2800 + 98, -14606047);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BeaconScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */