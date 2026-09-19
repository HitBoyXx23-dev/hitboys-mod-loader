/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ 
/*     */ import meteordevelopment.meteorclient.events.entity.player.StartBreakingBlockEvent;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_265;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2846;
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
/*     */ public class MyBlock
/*     */ {
/*     */   public class_2338 blockPos;
/*     */   public class_2680 blockState;
/*     */   public class_2248 block;
/*     */   public class_2350 direction;
/*     */   public int timer;
/*     */   public int startTime;
/*     */   public boolean mining;
/*     */   
/*     */   public MyBlock set(StartBreakingBlockEvent event) {
/* 214 */     this.blockPos = event.blockPos;
/* 215 */     this.direction = event.direction;
/* 216 */     this.blockState = (PacketMine.access$000(PacketMine.this)).field_1687.method_8320(this.blockPos);
/* 217 */     this.block = this.blockState.method_26204();
/* 218 */     this.timer = ((Integer)PacketMine.this.delay.get()).intValue();
/* 219 */     this.mining = false;
/*     */     
/* 221 */     return this;
/*     */   }
/*     */   
/*     */   public boolean shouldRemove() {
/* 225 */     boolean broken = ((PacketMine.access$100(PacketMine.this)).field_1687.method_8320(this.blockPos).method_26204() != this.block);
/* 226 */     boolean timeout = (progress() > 2.0D && (PacketMine.access$200(PacketMine.this)).field_1724.field_6012 - this.startTime > 50);
/* 227 */     boolean distance = (Utils.distance(((PacketMine.access$300(PacketMine.this)).field_1724.method_33571()).field_1352, ((PacketMine.access$400(PacketMine.this)).field_1724.method_33571()).field_1351, ((PacketMine.access$500(PacketMine.this)).field_1724.method_33571()).field_1350, (this.blockPos.method_10263() + this.direction.method_10148()), (this.blockPos.method_10264() + this.direction.method_10164()), (this.blockPos.method_10260() + this.direction.method_10165())) > (PacketMine.access$600(PacketMine.this)).field_1724.method_55754());
/*     */     
/* 229 */     return (broken || timeout || distance);
/*     */   }
/*     */   
/*     */   public boolean isReady() {
/* 233 */     return (progress() >= 1.0D);
/*     */   }
/*     */   
/*     */   public double progress() {
/* 237 */     if (!this.mining) return 0.0D;
/*     */     
/* 239 */     FindItemResult fir = InvUtils.findFastestTool(this.blockState);
/* 240 */     return BlockUtils.getBreakDelta(fir.found() ? fir.slot() : (PacketMine.access$700(PacketMine.this)).field_1724.method_31548().method_67532(), this.blockState) * ((PacketMine.access$800(PacketMine.this)).field_1724.field_6012 - this.startTime + 1);
/*     */   }
/*     */   
/*     */   public void mine() {
/* 244 */     if (((Boolean)PacketMine.this.rotate.get()).booleanValue()) { Rotations.rotate(Rotations.getYaw(this.blockPos), Rotations.getPitch(this.blockPos), 50, this::sendMinePackets); }
/* 245 */     else { sendMinePackets(); }
/*     */   
/*     */   }
/*     */   private void sendMinePackets() {
/* 249 */     if (this.timer <= 0) {
/* 250 */       if (!this.mining) {
/* 251 */         (PacketMine.access$1000(PacketMine.this)).field_1761.method_41931((PacketMine.access$900(PacketMine.this)).field_1687, sequence -> new class_2846(class_2846.class_2847.field_12968, this.blockPos, this.direction, sequence));
/* 252 */         (PacketMine.access$1200(PacketMine.this)).field_1761.method_41931((PacketMine.access$1100(PacketMine.this)).field_1687, sequence -> new class_2846(class_2846.class_2847.field_12973, this.blockPos, this.direction, sequence));
/*     */         
/* 254 */         this.mining = true;
/* 255 */         this.startTime = (PacketMine.access$1300(PacketMine.this)).field_1724.field_6012;
/*     */       } 
/*     */     } else {
/*     */       
/* 259 */       this.timer--;
/*     */     } 
/*     */     
/* 262 */     if (this.mining && ((Boolean)PacketMine.this.obscureBreakingProgress.get()).booleanValue()) PacketMine.access$1400(PacketMine.this).method_1562().method_52787((class_2596)new class_2846(class_2846.class_2847.field_12971, this.blockPos, this.direction)); 
/*     */   }
/*     */   
/*     */   public void render(Render3DEvent event) {
/* 266 */     class_265 shape = (PacketMine.access$1600(PacketMine.this)).field_1687.method_8320(this.blockPos).method_26218((class_1922)(PacketMine.access$1500(PacketMine.this)).field_1687, this.blockPos);
/*     */     
/* 268 */     double x1 = this.blockPos.method_10263();
/* 269 */     double y1 = this.blockPos.method_10264();
/* 270 */     double z1 = this.blockPos.method_10260();
/* 271 */     double x2 = (this.blockPos.method_10263() + 1);
/* 272 */     double y2 = (this.blockPos.method_10264() + 1);
/* 273 */     double z2 = (this.blockPos.method_10260() + 1);
/*     */     
/* 275 */     if (!shape.method_1110()) {
/* 276 */       x1 = this.blockPos.method_10263() + shape.method_1091(class_2350.class_2351.field_11048);
/* 277 */       y1 = this.blockPos.method_10264() + shape.method_1091(class_2350.class_2351.field_11052);
/* 278 */       z1 = this.blockPos.method_10260() + shape.method_1091(class_2350.class_2351.field_11051);
/* 279 */       x2 = this.blockPos.method_10263() + shape.method_1105(class_2350.class_2351.field_11048);
/* 280 */       y2 = this.blockPos.method_10264() + shape.method_1105(class_2350.class_2351.field_11052);
/* 281 */       z2 = this.blockPos.method_10260() + shape.method_1105(class_2350.class_2351.field_11051);
/*     */     } 
/*     */     
/* 284 */     if (isReady()) {
/* 285 */       event.renderer.box(x1, y1, z1, x2, y2, z2, (Color)PacketMine.this.readySideColor.get(), (Color)PacketMine.this.readyLineColor.get(), (ShapeMode)PacketMine.this.shapeMode.get(), 0);
/*     */     } else {
/* 287 */       event.renderer.box(x1, y1, z1, x2, y2, z2, (Color)PacketMine.this.sideColor.get(), (Color)PacketMine.this.lineColor.get(), (ShapeMode)PacketMine.this.shapeMode.get(), 0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\PacketMine$MyBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */