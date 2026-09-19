/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.utils.PreInit;
/*     */ import meteordevelopment.meteorclient.utils.entity.Target;
/*     */ import meteordevelopment.meteorclient.utils.misc.Pool;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2828;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rotations
/*     */ {
/*  28 */   private static final Pool<Rotation> rotationPool = new Pool(Rotation::new);
/*  29 */   private static final List<Rotation> rotations = new ArrayList<>();
/*     */   
/*     */   public static float serverYaw;
/*     */   
/*     */   public static float serverPitch;
/*  34 */   private static int i = 0;
/*     */   
/*     */   public static int rotationTimer;
/*     */   private static float preYaw;
/*     */   private static float prePitch;
/*     */   private static Rotation lastRotation;
/*     */   private static int lastRotationTimer;
/*     */   private static boolean sentLastRotation;
/*     */   public static boolean rotating = false;
/*     */   
/*     */   @PreInit
/*     */   public static void init() {
/*  46 */     MeteorClient.EVENT_BUS.subscribe(Rotations.class);
/*     */   }
/*     */   
/*     */   public static void rotate(double yaw, double pitch, int priority, boolean clientSide, Runnable callback) {
/*  50 */     Rotation rotation = (Rotation)rotationPool.get();
/*  51 */     rotation.set(yaw, pitch, priority, clientSide, callback);
/*     */     
/*  53 */     int i = 0;
/*  54 */     for (; i < rotations.size() && 
/*  55 */       priority <= ((Rotation)rotations.get(i)).priority; i++);
/*     */ 
/*     */     
/*  58 */     rotations.add(i, rotation);
/*     */   }
/*     */   
/*     */   public static void rotate(double yaw, double pitch, int priority, Runnable callback) {
/*  62 */     rotate(yaw, pitch, priority, false, callback);
/*     */   }
/*     */   
/*     */   public static void rotate(double yaw, double pitch, Runnable callback) {
/*  66 */     rotate(yaw, pitch, 0, callback);
/*     */   }
/*     */   
/*     */   public static void rotate(double yaw, double pitch, int priority) {
/*  70 */     rotate(yaw, pitch, priority, null);
/*     */   }
/*     */   
/*     */   public static void rotate(double yaw, double pitch) {
/*  74 */     rotate(yaw, pitch, 0, null);
/*     */   }
/*     */   
/*     */   private static void resetLastRotation() {
/*  78 */     if (lastRotation != null) {
/*  79 */       rotationPool.free(lastRotation);
/*     */       
/*  81 */       lastRotation = null;
/*  82 */       lastRotationTimer = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private static void onSendMovementPacketsPre(SendMovementPacketsEvent.Pre event) {
/*  88 */     if (MeteorClient.mc.method_1560() != MeteorClient.mc.field_1724)
/*  89 */       return;  sentLastRotation = false;
/*     */     
/*  91 */     if (!rotations.isEmpty()) {
/*  92 */       rotating = true;
/*  93 */       resetLastRotation();
/*     */       
/*  95 */       Rotation rotation = rotations.get(i);
/*  96 */       setupMovementPacketRotation(rotation);
/*     */       
/*  98 */       if (rotations.size() > 1) rotationPool.free(rotation);
/*     */       
/* 100 */       i++;
/* 101 */     } else if (lastRotation != null) {
/* 102 */       if (lastRotationTimer >= ((Integer)(Config.get()).rotationHoldTicks.get()).intValue()) {
/* 103 */         resetLastRotation();
/* 104 */         rotating = false;
/*     */       } else {
/* 106 */         setupMovementPacketRotation(lastRotation);
/* 107 */         sentLastRotation = true;
/*     */         
/* 109 */         lastRotationTimer++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setupMovementPacketRotation(Rotation rotation) {
/* 115 */     setClientRotation(rotation);
/* 116 */     setCamRotation(rotation.yaw, rotation.pitch);
/*     */   }
/*     */   
/*     */   private static void setClientRotation(Rotation rotation) {
/* 120 */     preYaw = MeteorClient.mc.field_1724.method_36454();
/* 121 */     prePitch = MeteorClient.mc.field_1724.method_36455();
/*     */     
/* 123 */     MeteorClient.mc.field_1724.method_36456((float)rotation.yaw);
/* 124 */     MeteorClient.mc.field_1724.method_36457((float)rotation.pitch);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private static void onSendMovementPacketsPost(SendMovementPacketsEvent.Post event) {
/* 129 */     if (!rotations.isEmpty()) {
/* 130 */       if (MeteorClient.mc.method_1560() == MeteorClient.mc.field_1724) {
/* 131 */         ((Rotation)rotations.get(i - 1)).runCallback();
/*     */         
/* 133 */         if (rotations.size() == 1) lastRotation = rotations.get(i - 1);
/*     */         
/* 135 */         resetPreRotation();
/*     */       } 
/*     */       
/* 138 */       for (; i < rotations.size(); i++) {
/* 139 */         Rotation rotation = rotations.get(i);
/*     */         
/* 141 */         setCamRotation(rotation.yaw, rotation.pitch);
/* 142 */         if (rotation.clientSide) setClientRotation(rotation); 
/* 143 */         rotation.sendPacket();
/* 144 */         if (rotation.clientSide) resetPreRotation();
/*     */         
/* 146 */         if (i == rotations.size() - 1) { lastRotation = rotation; }
/* 147 */         else { rotationPool.free(rotation); }
/*     */       
/*     */       } 
/* 150 */       rotations.clear();
/* 151 */       i = 0;
/* 152 */     } else if (sentLastRotation) {
/* 153 */       resetPreRotation();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void resetPreRotation() {
/* 158 */     MeteorClient.mc.field_1724.method_36456(preYaw);
/* 159 */     MeteorClient.mc.field_1724.method_36457(prePitch);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private static void onTick(TickEvent.Pre event) {
/* 164 */     rotationTimer++;
/*     */   }
/*     */   
/*     */   public static double getYaw(class_1297 entity) {
/* 168 */     return (MeteorClient.mc.field_1724.method_36454() + class_3532.method_15393((float)Math.toDegrees(Math.atan2(entity.method_23321() - MeteorClient.mc.field_1724.method_23321(), entity.method_23317() - MeteorClient.mc.field_1724.method_23317())) - 90.0F - MeteorClient.mc.field_1724.method_36454()));
/*     */   }
/*     */   
/*     */   public static double getYaw(class_243 pos) {
/* 172 */     return (MeteorClient.mc.field_1724.method_36454() + class_3532.method_15393((float)Math.toDegrees(Math.atan2(pos.method_10215() - MeteorClient.mc.field_1724.method_23321(), pos.method_10216() - MeteorClient.mc.field_1724.method_23317())) - 90.0F - MeteorClient.mc.field_1724.method_36454()));
/*     */   }
/*     */   
/*     */   public static double getPitch(class_243 pos) {
/* 176 */     double diffX = pos.method_10216() - MeteorClient.mc.field_1724.method_23317();
/* 177 */     double diffY = pos.method_10214() - MeteorClient.mc.field_1724.method_23318() + MeteorClient.mc.field_1724.method_18381(MeteorClient.mc.field_1724.method_18376());
/* 178 */     double diffZ = pos.method_10215() - MeteorClient.mc.field_1724.method_23321();
/*     */     
/* 180 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*     */     
/* 182 */     return (MeteorClient.mc.field_1724.method_36455() + class_3532.method_15393((float)-Math.toDegrees(Math.atan2(diffY, diffXZ)) - MeteorClient.mc.field_1724.method_36455()));
/*     */   }
/*     */   
/*     */   public static double getPitch(class_1297 entity, Target target) {
/*     */     double y;
/* 187 */     if (target == Target.Head) { y = entity.method_23320(); }
/* 188 */     else if (target == Target.Body) { y = entity.method_23318() + (entity.method_17682() / 2.0F); }
/* 189 */     else { y = entity.method_23318(); }
/*     */     
/* 191 */     double diffX = entity.method_23317() - MeteorClient.mc.field_1724.method_23317();
/* 192 */     double diffY = y - MeteorClient.mc.field_1724.method_23318() + MeteorClient.mc.field_1724.method_18381(MeteorClient.mc.field_1724.method_18376());
/* 193 */     double diffZ = entity.method_23321() - MeteorClient.mc.field_1724.method_23321();
/*     */     
/* 195 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*     */     
/* 197 */     return (MeteorClient.mc.field_1724.method_36455() + class_3532.method_15393((float)-Math.toDegrees(Math.atan2(diffY, diffXZ)) - MeteorClient.mc.field_1724.method_36455()));
/*     */   }
/*     */   
/*     */   public static double getPitch(class_1297 entity) {
/* 201 */     return getPitch(entity, Target.Body);
/*     */   }
/*     */   
/*     */   public static double getYaw(class_2338 pos) {
/* 205 */     return (MeteorClient.mc.field_1724.method_36454() + class_3532.method_15393((float)Math.toDegrees(Math.atan2(pos.method_10260() + 0.5D - MeteorClient.mc.field_1724.method_23321(), pos.method_10263() + 0.5D - MeteorClient.mc.field_1724.method_23317())) - 90.0F - MeteorClient.mc.field_1724.method_36454()));
/*     */   }
/*     */   
/*     */   public static double getPitch(class_2338 pos) {
/* 209 */     double diffX = pos.method_10263() + 0.5D - MeteorClient.mc.field_1724.method_23317();
/* 210 */     double diffY = pos.method_10264() + 0.5D - MeteorClient.mc.field_1724.method_23318() + MeteorClient.mc.field_1724.method_18381(MeteorClient.mc.field_1724.method_18376());
/* 211 */     double diffZ = pos.method_10260() + 0.5D - MeteorClient.mc.field_1724.method_23321();
/*     */     
/* 213 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*     */     
/* 215 */     return (MeteorClient.mc.field_1724.method_36455() + class_3532.method_15393((float)-Math.toDegrees(Math.atan2(diffY, diffXZ)) - MeteorClient.mc.field_1724.method_36455()));
/*     */   }
/*     */   
/*     */   public static void setCamRotation(double yaw, double pitch) {
/* 219 */     serverYaw = (float)yaw;
/* 220 */     serverPitch = (float)pitch;
/* 221 */     rotationTimer = 0;
/*     */   }
/*     */   
/*     */   private static class Rotation { public double yaw;
/*     */     public double pitch;
/*     */     public int priority;
/*     */     public boolean clientSide;
/*     */     public Runnable callback;
/*     */     
/*     */     public void set(double yaw, double pitch, int priority, boolean clientSide, Runnable callback) {
/* 231 */       this.yaw = yaw;
/* 232 */       this.pitch = pitch;
/* 233 */       this.priority = priority;
/* 234 */       this.clientSide = clientSide;
/* 235 */       this.callback = callback;
/*     */     }
/*     */     
/*     */     public void sendPacket() {
/* 239 */       MeteorClient.mc.method_1562().method_52787((class_2596)new class_2828.class_2831((float)this.yaw, (float)this.pitch, MeteorClient.mc.field_1724.method_24828(), MeteorClient.mc.field_1724.field_5976));
/* 240 */       runCallback();
/*     */     }
/*     */     
/*     */     public void runCallback() {
/* 244 */       if (this.callback != null) this.callback.run(); 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\Rotations.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */