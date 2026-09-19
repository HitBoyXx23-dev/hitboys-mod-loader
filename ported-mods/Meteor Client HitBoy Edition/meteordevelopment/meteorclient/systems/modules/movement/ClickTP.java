/*    */ package meteordevelopment.meteorclient.systems.modules.movement;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_1269;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1839;
/*    */ import net.minecraft.class_1922;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_239;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_265;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_2828;
/*    */ import net.minecraft.class_3959;
/*    */ import net.minecraft.class_3965;
/*    */ import net.minecraft.class_3966;
/*    */ import net.minecraft.class_4184;
/*    */ 
/*    */ public class ClickTP
/*    */   extends Module
/*    */ {
/*    */   public ClickTP() {
/* 31 */     super(Categories.Movement, "click-tp", "Teleports you to the block you click on.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 36 */     if (this.mc.field_1724.method_31548().method_7391().method_7976() != class_1839.field_8952)
/* 37 */       return;  if (!this.mc.field_1690.field_1904.method_1434())
/*    */       return; 
/* 39 */     if (this.mc.field_1765 != null) {
/* 40 */       if (this.mc.field_1765.method_17783() == class_239.class_240.field_1331 && this.mc.field_1724.method_7287(((class_3966)this.mc.field_1765).method_17782(), class_1268.field_5808) != class_1269.field_5811)
/* 41 */         return;  if (this.mc.field_1765.method_17783() == class_239.class_240.field_1332 && this.mc.field_1724.method_6047().method_7909() instanceof net.minecraft.class_1747)
/*    */         return; 
/*    */     } 
/* 44 */     class_4184 camera = this.mc.field_1773.method_19418();
/* 45 */     class_243 cameraPos = camera.method_71156();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 50 */     class_243 direction = class_243.method_1030(camera.method_19329(), camera.method_19330()).method_1021(210.0D);
/* 51 */     class_243 targetPos = cameraPos.method_1019(direction);
/*    */     
/* 53 */     class_3959 context = new class_3959(cameraPos, targetPos, class_3959.class_3960.field_17559, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     class_3965 hitResult = this.mc.field_1687.method_17742(context);
/*    */     
/* 63 */     if (hitResult.method_17783() == class_239.class_240.field_1332) {
/* 64 */       class_2338 pos = hitResult.method_17777();
/* 65 */       class_2350 side = hitResult.method_17780();
/*    */       
/* 67 */       if (this.mc.field_1687.method_8320(pos).method_55781((class_1937)this.mc.field_1687, (class_1657)this.mc.field_1724, hitResult) != class_1269.field_5811)
/*    */         return; 
/* 69 */       class_2680 state = this.mc.field_1687.method_8320(pos);
/*    */       
/* 71 */       class_265 shape = state.method_26220((class_1922)this.mc.field_1687, pos);
/* 72 */       if (shape.method_1110()) shape = state.method_26218((class_1922)this.mc.field_1687, pos);
/*    */       
/* 74 */       double height = shape.method_1110() ? 1.0D : shape.method_1105(class_2350.class_2351.field_11052);
/*    */       
/* 76 */       class_243 newPos = new class_243(pos.method_10263() + 0.5D + side.method_10148(), pos.method_10264() + height, pos.method_10260() + 0.5D + side.method_10165());
/* 77 */       int packetsRequired = (int)Math.ceil(this.mc.field_1724.method_73189().method_1022(newPos) / 10.0D) - 1;
/* 78 */       if (packetsRequired > 19) packetsRequired = 0;
/*    */       
/* 80 */       for (int packetNumber = 0; packetNumber < packetsRequired; packetNumber++) {
/* 81 */         this.mc.field_1724.field_3944.method_52787((class_2596)new class_2828.class_5911(true, true));
/*    */       }
/*    */       
/* 84 */       this.mc.field_1724.field_3944.method_52787((class_2596)new class_2828.class_2829(newPos.field_1352, newPos.field_1351, newPos.field_1350, true, true));
/* 85 */       this.mc.field_1724.method_33574(newPos);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\ClickTP.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */