/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*    */ import java.util.Set;
/*    */ import meteordevelopment.meteorclient.events.entity.player.DoItemUseEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_1269;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2374;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_3965;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GhostHand
/*    */   extends Module
/*    */ {
/* 23 */   private final Set<class_2338> posList = (Set<class_2338>)new ObjectOpenHashSet();
/*    */   
/*    */   public GhostHand() {
/* 26 */     super(Categories.Player, "ghost-hand", "Opens containers through walls.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(DoItemUseEvent event) {
/* 31 */     if (!this.mc.field_1690.field_1904.method_1434() || this.mc.field_1724.method_5715())
/*    */       return; 
/* 33 */     if (this.mc.field_1687.method_8320(class_2338.method_49638((class_2374)this.mc.field_1724.method_5745(this.mc.field_1724.method_55754(), this.mc.method_61966().method_60637(true), false).method_17784())).method_31709()) {
/*    */       return;
/*    */     }
/*    */     
/* 37 */     class_243 direction = (new class_243(0.0D, 0.0D, 0.1D)).method_1037(-((float)Math.toRadians(this.mc.field_1724.method_36455()))).method_1024(-((float)Math.toRadians(this.mc.field_1724.method_36454())));
/*    */     
/* 39 */     this.posList.clear();
/*    */     
/* 41 */     for (int i = 1; i < this.mc.field_1724.method_55754() * 10.0D; i++) {
/* 42 */       class_2338 pos = class_2338.method_49638((class_2374)this.mc.field_1724.method_5836(this.mc.method_61966().method_60637(true)).method_1019(direction.method_1021(i)));
/*    */       
/* 44 */       if (!this.posList.contains(pos)) {
/* 45 */         this.posList.add(pos);
/*    */         
/* 47 */         if (this.mc.field_1687.method_8320(pos).method_31709())
/* 48 */           for (class_1268 hand : class_1268.values()) {
/* 49 */             class_1269 result = this.mc.field_1761.method_2896(this.mc.field_1724, hand, new class_3965(new class_243(pos.method_10263() + 0.5D, pos.method_10264() + 0.5D, pos.method_10260() + 0.5D), class_2350.field_11036, pos, true));
/* 50 */             if (result instanceof class_1269.class_9860 || result instanceof class_1269.class_9857) {
/* 51 */               this.mc.field_1724.method_6104(hand);
/* 52 */               event.cancel();
/*    */               return;
/*    */             } 
/*    */           }  
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\GhostHand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */