/*    */ package meteordevelopment.meteorclient.utils.entity.fakeplayer;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.mixin.AbstractClientPlayerEntityAccessor;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_640;
/*    */ import net.minecraft.class_745;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FakePlayerEntity
/*    */   extends class_745
/*    */ {
/*    */   public boolean doNotPush;
/*    */   public boolean hideWhenInsideCamera;
/*    */   public boolean noHit;
/*    */   
/*    */   public FakePlayerEntity(class_1657 player, String name, float health, boolean copyInv) {
/* 28 */     super(MeteorClient.mc.field_1687, new GameProfile(UUID.randomUUID(), name));
/*    */     
/* 30 */     method_5719((class_1297)player);
/*    */     
/* 32 */     this.field_5982 = method_36454();
/* 33 */     this.field_6004 = method_36455();
/* 34 */     this.field_6241 = player.field_6241;
/* 35 */     this.field_6259 = this.field_6241;
/* 36 */     this.field_6283 = player.field_6283;
/* 37 */     this.field_6220 = this.field_6283;
/*    */     
/* 39 */     method_6127().method_26846(player.method_6127());
/* 40 */     method_18380(player.method_18376());
/*    */     
/* 42 */     if (health <= 20.0F) {
/* 43 */       method_6033(health);
/*    */     } else {
/* 45 */       method_6033(health);
/* 46 */       method_6073(health - 20.0F);
/*    */     } 
/*    */     
/* 49 */     if (copyInv) method_31548().method_7377(player.method_31548()); 
/*    */   }
/*    */   
/*    */   public void spawn() {
/* 53 */     method_31482();
/* 54 */     MeteorClient.mc.field_1687.method_53875((class_1297)this);
/*    */   }
/*    */   
/*    */   public void despawn() {
/* 58 */     MeteorClient.mc.field_1687.method_2945(method_5628(), class_1297.class_5529.field_26999);
/* 59 */     method_31745(class_1297.class_5529.field_26999);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected class_640 method_3123() {
/* 65 */     class_640 entry = super.method_3123();
/*    */     
/* 67 */     if (entry == null) {
/* 68 */       ((AbstractClientPlayerEntityAccessor)this).meteor$setPlayerListEntry(MeteorClient.mc.method_1562().method_2871(MeteorClient.mc.field_1724.method_5667()));
/*    */     }
/*    */     
/* 71 */     return entry;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\entity\fakeplayer\FakePlayerEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */