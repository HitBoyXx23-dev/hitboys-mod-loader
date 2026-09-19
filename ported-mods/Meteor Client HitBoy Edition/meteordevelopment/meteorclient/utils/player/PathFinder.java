/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_2183;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2374;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2680;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathFinder
/*     */ {
/*     */   private static final int PATH_AHEAD = 3;
/*     */   private static final int QUAD_1 = 1;
/*     */   private static final int QUAD_2 = 2;
/*     */   private static final int SOUTH = 0;
/*     */   private static final int NORTH = 180;
/*  26 */   private final ArrayList<PathBlock> path = new ArrayList<>(3);
/*     */   private class_1297 target;
/*     */   private PathBlock currentPathBlock;
/*     */   
/*     */   public PathBlock getNextPathBlock() {
/*  31 */     PathBlock nextBlock = new PathBlock(this, class_2338.method_49638((class_2374)getNextStraightPos()));
/*  32 */     if (isSolidFloor(nextBlock.blockPos) && isAirAbove(nextBlock.blockPos))
/*  33 */       return nextBlock; 
/*  34 */     if (!isSolidFloor(nextBlock.blockPos) && isAirAbove(nextBlock.blockPos)) {
/*  35 */       int drop = getDrop(nextBlock.blockPos);
/*  36 */       if (getDrop(nextBlock.blockPos) < 3) {
/*  37 */         nextBlock = new PathBlock(this, new class_2338(nextBlock.blockPos.method_10263(), nextBlock.blockPos.method_10264() - drop, nextBlock.blockPos.method_10260()));
/*     */       }
/*     */     } 
/*     */     
/*  41 */     return nextBlock;
/*     */   }
/*     */   
/*     */   public int getDrop(class_2338 pos) {
/*  45 */     int drop = 0;
/*  46 */     while (!isSolidFloor(pos) && drop < 3) {
/*  47 */       drop++;
/*  48 */       pos = new class_2338(pos.method_10263(), pos.method_10264() - 1, pos.method_10260());
/*     */     } 
/*  50 */     return drop;
/*     */   }
/*     */   
/*     */   public boolean isAirAbove(class_2338 blockPos) {
/*  54 */     if (!getBlockStateAtPos(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260()).method_26215())
/*  55 */       return false; 
/*  56 */     return getBlockStateAtPos(blockPos.method_10263(), blockPos.method_10264() + 1, blockPos.method_10260()).method_26215();
/*     */   }
/*     */   
/*     */   public class_243 getNextStraightPos() {
/*  60 */     class_243 nextPos = new class_243(MeteorClient.mc.field_1724.method_23317(), MeteorClient.mc.field_1724.method_23318(), MeteorClient.mc.field_1724.method_23321());
/*  61 */     double multiplier = 1.0D;
/*  62 */     while (nextPos == MeteorClient.mc.field_1724.method_73189()) {
/*  63 */       nextPos = new class_243((int)(MeteorClient.mc.field_1724.method_23317() + multiplier * Math.cos(Math.toRadians(MeteorClient.mc.field_1724.method_36454()))), (int)MeteorClient.mc.field_1724.method_23318(), (int)(MeteorClient.mc.field_1724.method_23321() + multiplier * Math.sin(Math.toRadians(MeteorClient.mc.field_1724.method_36454()))));
/*  64 */       multiplier += 0.1D;
/*     */     } 
/*  66 */     return nextPos;
/*     */   }
/*     */   public int getYawToTarget() {
/*     */     int yaw;
/*  70 */     if (this.target == null || MeteorClient.mc.field_1724 == null) return Integer.MAX_VALUE; 
/*  71 */     class_243 tPos = this.target.method_73189();
/*  72 */     class_243 pPos = MeteorClient.mc.field_1724.method_73189();
/*     */     
/*  74 */     int direction = getDirection();
/*  75 */     double tan = (tPos.field_1350 - pPos.field_1350) / (tPos.field_1352 - pPos.field_1352);
/*  76 */     if (direction == 1)
/*  77 */     { yaw = (int)(1.5707963267948966D - Math.atan(tan)); }
/*  78 */     else if (direction == 2)
/*  79 */     { yaw = (int)(-1.5707963267948966D - Math.atan(tan)); }
/*  80 */     else { return direction; }
/*  81 */      return yaw;
/*     */   }
/*     */   
/*     */   public int getDirection() {
/*  85 */     if (this.target == null || MeteorClient.mc.field_1724 == null) return 0; 
/*  86 */     class_243 targetPos = this.target.method_73189();
/*  87 */     class_243 playerPos = MeteorClient.mc.field_1724.method_73189();
/*  88 */     if (targetPos.field_1352 == playerPos.field_1352 && targetPos.field_1350 > playerPos.field_1350)
/*  89 */       return 0; 
/*  90 */     if (targetPos.field_1352 == playerPos.field_1352 && targetPos.field_1350 < playerPos.field_1350)
/*  91 */       return 180; 
/*  92 */     if (targetPos.field_1352 < playerPos.field_1352)
/*  93 */       return 1; 
/*  94 */     if (targetPos.field_1352 > playerPos.field_1352)
/*  95 */       return 2; 
/*  96 */     return 0;
/*     */   }
/*     */   
/*     */   public class_2680 getBlockStateAtPos(class_2338 pos) {
/* 100 */     if (MeteorClient.mc.field_1687 != null)
/* 101 */       return MeteorClient.mc.field_1687.method_8320(pos); 
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public class_2680 getBlockStateAtPos(int x, int y, int z) {
/* 106 */     if (MeteorClient.mc.field_1687 != null)
/* 107 */       return MeteorClient.mc.field_1687.method_8320(new class_2338(x, y, z)); 
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public class_2248 getBlockAtPos(class_2338 pos) {
/* 112 */     if (MeteorClient.mc.field_1687 != null)
/* 113 */       return MeteorClient.mc.field_1687.method_8320(pos).method_26204(); 
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isSolidFloor(class_2338 blockPos) {
/* 118 */     return isAir(getBlockAtPos(blockPos));
/*     */   }
/*     */   
/*     */   public boolean isAir(class_2248 block) {
/* 122 */     return (block == class_2246.field_10124);
/*     */   }
/*     */   
/*     */   public boolean isWater(class_2248 block) {
/* 126 */     return (block == class_2246.field_10382);
/*     */   }
/*     */   
/*     */   public void lookAtDestination(PathBlock pathBlock) {
/* 130 */     if (MeteorClient.mc.field_1724 != null) {
/* 131 */       MeteorClient.mc.field_1724.method_5702(class_2183.class_2184.field_9851, new class_243(pathBlock.blockPos.method_10263(), (pathBlock.blockPos.method_10264() + MeteorClient.mc.field_1724.method_5751()), pathBlock.blockPos.method_10260()));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void moveEventListener(PlayerMoveEvent event) {
/* 137 */     if (this.target != null && MeteorClient.mc.field_1724 != null) {
/* 138 */       if (!PlayerUtils.isWithin(this.target, 3.0D)) {
/* 139 */         if (this.currentPathBlock == null) this.currentPathBlock = getNextPathBlock(); 
/* 140 */         if (MeteorClient.mc.field_1724.method_73189().method_1025(new class_243(this.currentPathBlock.blockPos.method_10263(), this.currentPathBlock.blockPos.method_10264(), this.currentPathBlock.blockPos.method_10260())) < 0.01D)
/* 141 */           this.currentPathBlock = getNextPathBlock(); 
/* 142 */         lookAtDestination(this.currentPathBlock);
/* 143 */         if (!MeteorClient.mc.field_1690.field_1894.method_1434())
/* 144 */           MeteorClient.mc.field_1690.field_1894.method_23481(true); 
/*     */       } else {
/* 146 */         if (MeteorClient.mc.field_1690.field_1894.method_1434())
/* 147 */           MeteorClient.mc.field_1690.field_1894.method_23481(false); 
/* 148 */         this.path.clear();
/* 149 */         this.currentPathBlock = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void initiate(class_1297 entity) {
/* 155 */     this.target = entity;
/* 156 */     if (this.target != null) this.currentPathBlock = getNextPathBlock(); 
/* 157 */     MeteorClient.EVENT_BUS.subscribe(this);
/*     */   }
/*     */   
/*     */   public void disable() {
/* 161 */     this.target = null;
/* 162 */     this.path.clear();
/* 163 */     if (MeteorClient.mc.field_1690.field_1894.method_1434()) MeteorClient.mc.field_1690.field_1894.method_23481(false); 
/* 164 */     MeteorClient.EVENT_BUS.unsubscribe(this);
/*     */   }
/*     */   
/*     */   public class PathBlock {
/*     */     public final class_2248 block;
/*     */     public final class_2338 blockPos;
/*     */     public final class_2680 blockState;
/*     */     public double yaw;
/*     */     
/*     */     public PathBlock(PathFinder this$0, class_2248 b, class_2338 pos, class_2680 state) {
/* 174 */       this.block = b;
/* 175 */       this.blockPos = pos;
/* 176 */       this.blockState = state;
/*     */     }
/*     */     
/*     */     public PathBlock(PathFinder this$0, class_2248 b, class_2338 pos) {
/* 180 */       this.block = b;
/* 181 */       this.blockPos = pos;
/* 182 */       this.blockState = this$0.getBlockStateAtPos(this.blockPos);
/*     */     }
/*     */     
/*     */     public PathBlock(PathFinder this$0, class_2338 pos) {
/* 186 */       this.blockPos = pos;
/* 187 */       this.block = this$0.getBlockAtPos(pos);
/* 188 */       this.blockState = this$0.getBlockStateAtPos(this.blockPos);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\PathFinder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */