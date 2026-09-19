/*     */ package meteordevelopment.meteorclient.pathing;
/*     */ 
/*     */ import baritone.api.BaritoneAPI;
/*     */ import baritone.api.pathing.goals.Goal;
/*     */ import baritone.api.utils.SettingsUtil;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3532;
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
/*     */ class GoalDirection
/*     */   implements Goal
/*     */ {
/* 126 */   private static final double SQRT_2 = Math.sqrt(2.0D);
/*     */   
/*     */   private final float yaw;
/*     */   
/*     */   private int x;
/*     */   private int z;
/*     */   private int timer;
/*     */   
/*     */   public GoalDirection(float yaw) {
/* 135 */     this.yaw = yaw;
/* 136 */     tick();
/*     */   }
/*     */   
/*     */   public static double calculate(double xDiff, double zDiff) {
/* 140 */     double straight, diagonal, x = Math.abs(xDiff);
/* 141 */     double z = Math.abs(zDiff);
/*     */ 
/*     */     
/* 144 */     if (x < z) {
/* 145 */       straight = z - x;
/* 146 */       diagonal = x;
/*     */     } else {
/* 148 */       straight = x - z;
/* 149 */       diagonal = z;
/*     */     } 
/*     */     
/* 152 */     diagonal *= SQRT_2;
/* 153 */     return (diagonal + straight) * ((Double)(BaritoneAPI.getSettings()).costHeuristic.value).doubleValue();
/*     */   }
/*     */   
/*     */   public void tick() {
/* 157 */     if (this.timer <= 0) {
/* 158 */       this.timer = 20;
/*     */       
/* 160 */       class_243 pos = MeteorClient.mc.field_1724.method_73189();
/* 161 */       float theta = (float)Math.toRadians(this.yaw);
/*     */       
/* 163 */       this.x = (int)Math.floor(pos.field_1352 - class_3532.method_15374(theta) * 100.0D);
/* 164 */       this.z = (int)Math.floor(pos.field_1350 + class_3532.method_15362(theta) * 100.0D);
/*     */     } 
/*     */     
/* 167 */     this.timer--;
/*     */   }
/*     */   
/*     */   public boolean isInGoal(int x, int y, int z) {
/* 171 */     return (x == this.x && z == this.z);
/*     */   }
/*     */   
/*     */   public double heuristic(int x, int y, int z) {
/* 175 */     int xDiff = x - this.x;
/* 176 */     int zDiff = z - this.z;
/* 177 */     return calculate(xDiff, zDiff);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 181 */     return String.format("GoalXZ{x=%s,z=%s}", new Object[] { SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.z) });
/*     */   }
/*     */   
/*     */   public int getX() {
/* 185 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 189 */     return this.z;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\pathing\BaritonePathManager$GoalDirection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */