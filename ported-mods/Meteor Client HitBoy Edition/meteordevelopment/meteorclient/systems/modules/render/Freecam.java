/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.game.GameLeftEvent;
/*     */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*     */ import meteordevelopment.meteorclient.events.meteor.KeyEvent;
/*     */ import meteordevelopment.meteorclient.events.meteor.MouseClickEvent;
/*     */ import meteordevelopment.meteorclient.events.meteor.MouseScrollEvent;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.ChunkOcclusionEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.GUIMove;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.input.Input;
/*     */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1675;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2749;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3726;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ import net.minecraft.class_3966;
/*     */ import net.minecraft.class_4184;
/*     */ import net.minecraft.class_5498;
/*     */ import net.minecraft.class_5892;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.joml.Vector3d;
/*     */ import org.joml.Vector3dc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Freecam
/*     */   extends Module
/*     */ {
/*  53 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  54 */   private final SettingGroup sgPathing = this.settings.createGroup("Pathing");
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> speed;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> speedScrollSensitivity;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> staySneaking;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> toggleOnDamage;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> toggleOnDeath;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> toggleOnLog;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> reloadChunks;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> renderHands;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> rotate;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> staticView;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> baritoneClick;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> requireDoubleClick;
/*     */ 
/*     */   
/*     */   public final Vector3d pos;
/*     */ 
/*     */   
/*     */   public final Vector3d prevPos;
/*     */ 
/*     */   
/*     */   private class_5498 perspective;
/*     */ 
/*     */   
/*     */   private double speedValue;
/*     */ 
/*     */   
/*     */   public float yaw;
/*     */ 
/*     */   
/*     */   public float pitch;
/*     */ 
/*     */   
/*     */   public float lastYaw;
/*     */ 
/*     */   
/*     */   public float lastPitch;
/*     */ 
/*     */   
/*     */   private double fovScale;
/*     */ 
/*     */   
/*     */   private boolean bobView;
/*     */ 
/*     */   
/*     */   private boolean forward;
/*     */ 
/*     */   
/*     */   private boolean backward;
/*     */ 
/*     */   
/*     */   private boolean right;
/*     */ 
/*     */   
/*     */   private boolean left;
/*     */ 
/*     */   
/*     */   private boolean up;
/*     */ 
/*     */   
/*     */   private boolean down;
/*     */ 
/*     */   
/*     */   private boolean isSneaking;
/*     */ 
/*     */   
/*     */   private long clickTs;
/*     */ 
/*     */ 
/*     */   
/*     */   public Freecam() {
/* 161 */     super(Categories.Render, "freecam", "Allows the camera to move away from the player."); this.speed = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("speed")).description("Your speed while in freecam.")).onChanged(aDouble -> this.speedValue = aDouble.doubleValue())).defaultValue(1.0D).min(0.0D).build()); this.speedScrollSensitivity = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("speed-scroll-sensitivity")).description("Allows you to change speed value using scroll wheel. 0 to disable.")).defaultValue(0.0D).min(0.0D).sliderMax(2.0D).build()); this.staySneaking = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("stay-sneaking")).description("If you are sneaking when you enter freecam, whether your player should remain sneaking.")).defaultValue(Boolean.valueOf(true))).build()); this.toggleOnDamage = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("toggle-on-damage")).description("Disables freecam when you take damage.")).defaultValue(Boolean.valueOf(false))).build()); this.toggleOnDeath = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("toggle-on-death")).description("Disables freecam when you die.")).defaultValue(Boolean.valueOf(false))).build()); this.toggleOnLog = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("toggle-on-log")).description("Disables freecam when you disconnect from a server.")).defaultValue(Boolean.valueOf(true))).build()); this.reloadChunks = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("reload-chunks")).description("Disables cave culling.")).defaultValue(Boolean.valueOf(true))).build()); this.renderHands = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("show-hands")).description("Whether or not to render your hands in freecam.")).defaultValue(Boolean.valueOf(true))).build()); this.rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("rotate")).description("Rotates to the block or entity you are looking at.")).defaultValue(Boolean.valueOf(false))).build()); this.staticView = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("static")).description("Disables settings that move the view.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.baritoneClick = this.sgPathing.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("click-to-path")).description("Sets a pathfinding goal to any block/entity you click at.")).defaultValue(Boolean.valueOf(false))).build());
/*     */     this.requireDoubleClick = this.sgPathing.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("double-click")).description("Require two clicks to start pathing.")).defaultValue(Boolean.valueOf(false))).build());
/*     */     this.pos = new Vector3d();
/*     */     this.prevPos = new Vector3d();
/* 166 */     this.clickTs = 0L; } public void onActivate() { this.fovScale = ((Double)this.mc.field_1690.method_42454().method_41753()).doubleValue();
/* 167 */     this.bobView = ((Boolean)this.mc.field_1690.method_42448().method_41753()).booleanValue();
/* 168 */     if (((Boolean)this.staticView.get()).booleanValue()) {
/* 169 */       this.mc.field_1690.method_42454().method_41748(Double.valueOf(0.0D));
/* 170 */       this.mc.field_1690.method_42448().method_41748(Boolean.valueOf(false));
/*     */     } 
/* 172 */     this.yaw = this.mc.field_1724.method_36454();
/* 173 */     this.pitch = this.mc.field_1724.method_36455();
/*     */     
/* 175 */     this.perspective = this.mc.field_1690.method_31044();
/* 176 */     this.speedValue = ((Double)this.speed.get()).doubleValue();
/*     */     
/* 178 */     Utils.set(this.pos, this.mc.field_1773.method_19418().method_71156());
/* 179 */     Utils.set(this.prevPos, this.mc.field_1773.method_19418().method_71156());
/*     */     
/* 181 */     if (this.mc.field_1690.method_31044() == class_5498.field_26666) {
/* 182 */       this.yaw += 180.0F;
/* 183 */       this.pitch *= -1.0F;
/*     */     } 
/*     */     
/* 186 */     this.lastYaw = this.yaw;
/* 187 */     this.lastPitch = this.pitch;
/*     */     
/* 189 */     this.isSneaking = this.mc.field_1690.field_1832.method_1434();
/*     */     
/* 191 */     this.forward = Input.isPressed(this.mc.field_1690.field_1894);
/* 192 */     this.backward = Input.isPressed(this.mc.field_1690.field_1881);
/* 193 */     this.right = Input.isPressed(this.mc.field_1690.field_1849);
/* 194 */     this.left = Input.isPressed(this.mc.field_1690.field_1913);
/* 195 */     this.up = Input.isPressed(this.mc.field_1690.field_1903);
/* 196 */     this.down = Input.isPressed(this.mc.field_1690.field_1832);
/*     */     
/* 198 */     unpress();
/* 199 */     if (((Boolean)this.reloadChunks.get()).booleanValue()) this.mc.field_1769.method_3279();
/*     */      }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 204 */     if (((Boolean)this.reloadChunks.get()).booleanValue()) {
/* 205 */       Objects.requireNonNull(this.mc.field_1769); this.mc.execute(this.mc.field_1769::method_3279);
/*     */     } 
/*     */     
/* 208 */     this.mc.field_1690.method_31043(this.perspective);
/*     */     
/* 210 */     if (((Boolean)this.staticView.get()).booleanValue()) {
/* 211 */       this.mc.field_1690.method_42454().method_41748(Double.valueOf(this.fovScale));
/* 212 */       this.mc.field_1690.method_42448().method_41748(Boolean.valueOf(this.bobView));
/*     */     } 
/*     */     
/* 215 */     this.isSneaking = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onOpenScreen(OpenScreenEvent event) {
/* 220 */     unpress();
/*     */     
/* 222 */     this.prevPos.set((Vector3dc)this.pos);
/* 223 */     this.lastYaw = this.yaw;
/* 224 */     this.lastPitch = this.pitch;
/*     */   }
/*     */   
/*     */   private void unpress() {
/* 228 */     this.mc.field_1690.field_1894.method_23481(false);
/* 229 */     this.mc.field_1690.field_1881.method_23481(false);
/* 230 */     this.mc.field_1690.field_1849.method_23481(false);
/* 231 */     this.mc.field_1690.field_1913.method_23481(false);
/* 232 */     this.mc.field_1690.field_1903.method_23481(false);
/* 233 */     this.mc.field_1690.field_1832.method_23481(false);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 238 */     if (this.mc.method_1560().method_5757()) (this.mc.method_1560()).field_5960 = true; 
/* 239 */     if (!this.perspective.method_31034()) this.mc.field_1690.method_31043(class_5498.field_26664);
/*     */     
/* 241 */     class_243 forward = class_243.method_1030(0.0F, this.yaw);
/* 242 */     class_243 right = class_243.method_1030(0.0F, this.yaw + 90.0F);
/* 243 */     double velX = 0.0D;
/* 244 */     double velY = 0.0D;
/* 245 */     double velZ = 0.0D;
/*     */     
/* 247 */     if (((Boolean)this.rotate.get()).booleanValue())
/*     */     {
/*     */ 
/*     */       
/* 251 */       if (this.mc.field_1765 instanceof class_3966) {
/* 252 */         class_2338 crossHairPos = ((class_3966)this.mc.field_1765).method_17782().method_24515();
/* 253 */         Rotations.rotate(Rotations.getYaw(crossHairPos), Rotations.getPitch(crossHairPos), 0, null);
/*     */       } else {
/* 255 */         class_243 crossHairPosition = this.mc.field_1765.method_17784();
/* 256 */         class_2338 crossHairPos = ((class_3965)this.mc.field_1765).method_17777();
/*     */         
/* 258 */         if (!this.mc.field_1687.method_8320(crossHairPos).method_26215()) {
/* 259 */           Rotations.rotate(Rotations.getYaw(crossHairPosition), Rotations.getPitch(crossHairPosition), 0, null);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 264 */     double s = 0.5D;
/* 265 */     if (Input.isPressed(this.mc.field_1690.field_1867)) s = 1.0D;
/*     */     
/* 267 */     boolean a = false;
/* 268 */     if (this.forward) {
/* 269 */       velX += forward.field_1352 * s * this.speedValue;
/* 270 */       velZ += forward.field_1350 * s * this.speedValue;
/* 271 */       a = true;
/*     */     } 
/* 273 */     if (this.backward) {
/* 274 */       velX -= forward.field_1352 * s * this.speedValue;
/* 275 */       velZ -= forward.field_1350 * s * this.speedValue;
/* 276 */       a = true;
/*     */     } 
/*     */     
/* 279 */     boolean b = false;
/* 280 */     if (this.right) {
/* 281 */       velX += right.field_1352 * s * this.speedValue;
/* 282 */       velZ += right.field_1350 * s * this.speedValue;
/* 283 */       b = true;
/*     */     } 
/* 285 */     if (this.left) {
/* 286 */       velX -= right.field_1352 * s * this.speedValue;
/* 287 */       velZ -= right.field_1350 * s * this.speedValue;
/* 288 */       b = true;
/*     */     } 
/*     */     
/* 291 */     if (a && b) {
/* 292 */       double diagonal = 1.0D / Math.sqrt(2.0D);
/* 293 */       velX *= diagonal;
/* 294 */       velZ *= diagonal;
/*     */     } 
/*     */     
/* 297 */     if (this.up) {
/* 298 */       velY += s * this.speedValue;
/*     */     }
/* 300 */     if (this.down) {
/* 301 */       velY -= s * this.speedValue;
/*     */     }
/*     */     
/* 304 */     this.prevPos.set((Vector3dc)this.pos);
/* 305 */     this.pos.set(this.pos.x + velX, this.pos.y + velY, this.pos.z + velZ);
/*     */   }
/*     */   
/*     */   @EventHandler(priority = 100)
/*     */   public void onKey(KeyEvent event) {
/* 310 */     if (Input.isKeyPressed(292))
/* 311 */       return;  if (checkGuiMove())
/*     */       return; 
/* 313 */     if (onInput(event.key(), event.action)) event.cancel(); 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private class_2338 rayCastEntity(class_243 posVec, class_243 max, short maxDist) {
/* 318 */     class_3966 res = class_1675.method_18075((class_1297)this.mc.field_1724, posVec, max, 
/*     */ 
/*     */ 
/*     */         
/* 322 */         class_238.method_54784(class_2338.method_49637(posVec.field_1352, posVec.field_1351, posVec.field_1350), class_2338.method_49637(max.field_1352, max.field_1351, max.field_1350)), entity -> true, maxDist);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 327 */     if (res == null) return null;
/*     */     
/* 329 */     class_243 vec = res.method_17784();
/*     */     
/* 331 */     return class_2338.method_49637(vec.field_1352, vec.field_1351, vec.field_1350);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private class_2338 rayCastBlock(class_243 posVec, class_243 max) {
/* 341 */     class_3959 ctx = new class_3959(posVec, max, class_3959.class_3960.field_23142, class_3959.class_242.field_1345, class_3726.method_16194());
/*     */ 
/*     */     
/* 344 */     class_3965 res = this.mc.field_1687.method_17742(ctx);
/* 345 */     if (res.method_17783() == class_239.class_240.field_1333) return null;
/*     */ 
/*     */     
/* 348 */     return res.method_17777().method_10081(res.method_17780().method_62675());
/*     */   }
/*     */   
/*     */   private void setGoal() {
/* 352 */     long prevClick = this.clickTs;
/* 353 */     this.clickTs = System.currentTimeMillis();
/*     */     
/* 355 */     if (((Boolean)this.requireDoubleClick.get()).booleanValue() && this.clickTs - prevClick > 500L)
/*     */       return; 
/* 357 */     class_4184 cam = this.mc.field_1773.method_19418();
/* 358 */     class_243 posVec = cam.method_71156();
/* 359 */     class_243 lookVec = class_243.method_1030(cam.method_19329(), cam.method_19330());
/* 360 */     short maxDist = 256;
/* 361 */     class_243 max = posVec.method_1019(lookVec.method_1021(maxDist));
/*     */     
/* 363 */     class_2338 pos = rayCastEntity(posVec, max, maxDist);
/* 364 */     if (pos == null) {
/* 365 */       pos = rayCastBlock(posVec, max);
/*     */     }
/*     */     
/* 368 */     if (pos == null)
/*     */       return; 
/* 370 */     PathManagers.get().moveTo(pos);
/*     */   }
/*     */   
/*     */   @EventHandler(priority = 100)
/*     */   private void onMouseClick(MouseClickEvent event) {
/* 375 */     if (checkGuiMove())
/*     */       return; 
/* 377 */     if (((Boolean)this.baritoneClick.get()).booleanValue() && event.action == KeyAction.Press && this.mc.field_1690.field_1886.method_1433(event.click)) {
/* 378 */       setGoal();
/*     */     }
/*     */     
/* 381 */     if (onInput(event.button(), event.action)) event.cancel(); 
/*     */   }
/*     */   
/*     */   private boolean onInput(int key, KeyAction action) {
/* 385 */     if (Input.getKey(this.mc.field_1690.field_1894) == key) {
/* 386 */       this.forward = (action != KeyAction.Release);
/* 387 */       this.mc.field_1690.field_1894.method_23481(false);
/*     */     }
/* 389 */     else if (Input.getKey(this.mc.field_1690.field_1881) == key) {
/* 390 */       this.backward = (action != KeyAction.Release);
/* 391 */       this.mc.field_1690.field_1881.method_23481(false);
/*     */     }
/* 393 */     else if (Input.getKey(this.mc.field_1690.field_1849) == key) {
/* 394 */       this.right = (action != KeyAction.Release);
/* 395 */       this.mc.field_1690.field_1849.method_23481(false);
/*     */     }
/* 397 */     else if (Input.getKey(this.mc.field_1690.field_1913) == key) {
/* 398 */       this.left = (action != KeyAction.Release);
/* 399 */       this.mc.field_1690.field_1913.method_23481(false);
/*     */     }
/* 401 */     else if (Input.getKey(this.mc.field_1690.field_1903) == key) {
/* 402 */       this.up = (action != KeyAction.Release);
/* 403 */       this.mc.field_1690.field_1903.method_23481(false);
/*     */     }
/* 405 */     else if (Input.getKey(this.mc.field_1690.field_1832) == key) {
/* 406 */       this.down = (action != KeyAction.Release);
/* 407 */       this.mc.field_1690.field_1832.method_23481(false);
/*     */     } else {
/*     */       
/* 410 */       return false;
/*     */     } 
/*     */     
/* 413 */     return true;
/*     */   }
/*     */   
/*     */   @EventHandler(priority = -100)
/*     */   private void onMouseScroll(MouseScrollEvent event) {
/* 418 */     if (((Double)this.speedScrollSensitivity.get()).doubleValue() > 0.0D && this.mc.field_1755 == null) {
/* 419 */       this.speedValue += event.value * 0.25D * ((Double)this.speedScrollSensitivity.get()).doubleValue() * this.speedValue;
/* 420 */       if (this.speedValue < 0.1D) this.speedValue = 0.1D;
/*     */       
/* 422 */       event.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onChunkOcclusion(ChunkOcclusionEvent event) {
/* 428 */     event.cancel();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onGameLeft(GameLeftEvent event) {
/* 433 */     if (!((Boolean)this.toggleOnLog.get()).booleanValue())
/*     */       return; 
/* 435 */     toggle();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacketReceive(PacketEvent.Receive event) {
/* 440 */     class_2596 class_2596 = event.packet; if (class_2596 instanceof class_5892) { class_5892 packet = (class_5892)class_2596;
/* 441 */       class_1297 entity = this.mc.field_1687.method_8469(packet.comp_2275());
/* 442 */       if (entity == this.mc.field_1724 && ((Boolean)this.toggleOnDeath.get()).booleanValue()) {
/* 443 */         toggle();
/* 444 */         info("Toggled off because you died.", new Object[0]);
/*     */       }  }
/*     */     else
/* 447 */     { class_2596 = event.packet; if (class_2596 instanceof class_2749) { class_2749 packet = (class_2749)class_2596;
/* 448 */         if (this.mc.field_1724.method_6032() - packet.method_11833() > 0.0F && ((Boolean)this.toggleOnDamage.get()).booleanValue()) {
/* 449 */           toggle();
/* 450 */           info("Toggled off because you took damage.", new Object[0]);
/*     */         }
/*     */          }
/* 453 */       else if (event.packet instanceof net.minecraft.class_2724 && 
/* 454 */         isActive())
/* 455 */       { toggle();
/* 456 */         info("Toggled off because you changed dimensions.", new Object[0]); }
/*     */        }
/*     */   
/*     */   }
/*     */   
/*     */   private boolean checkGuiMove() {
/* 462 */     GUIMove guiMove = (GUIMove)Modules.get().get(GUIMove.class);
/* 463 */     if (this.mc.field_1755 != null && !guiMove.isActive()) return true; 
/* 464 */     return (this.mc.field_1755 != null && guiMove.isActive() && guiMove.skip());
/*     */   }
/*     */   
/*     */   public void changeLookDirection(double deltaX, double deltaY) {
/* 468 */     this.lastYaw = this.yaw;
/* 469 */     this.lastPitch = this.pitch;
/*     */     
/* 471 */     this.yaw += (float)deltaX;
/* 472 */     this.pitch += (float)deltaY;
/*     */     
/* 474 */     this.pitch = class_3532.method_15363(this.pitch, -90.0F, 90.0F);
/*     */   }
/*     */   
/*     */   public boolean renderHands() {
/* 478 */     return (!isActive() || ((Boolean)this.renderHands.get()).booleanValue());
/*     */   }
/*     */   
/*     */   public boolean staySneaking() {
/* 482 */     return (isActive() && !(this.mc.field_1724.method_31549()).field_7479 && ((Boolean)this.staySneaking.get()).booleanValue() && this.isSneaking);
/*     */   }
/*     */   
/*     */   public double getX(float tickDelta) {
/* 486 */     return class_3532.method_16436(tickDelta, this.prevPos.x, this.pos.x);
/*     */   }
/*     */   public double getY(float tickDelta) {
/* 489 */     return class_3532.method_16436(tickDelta, this.prevPos.y, this.pos.y);
/*     */   }
/*     */   public double getZ(float tickDelta) {
/* 492 */     return class_3532.method_16436(tickDelta, this.prevPos.z, this.pos.z);
/*     */   }
/*     */   
/*     */   public double getYaw(float tickDelta) {
/* 496 */     return class_3532.method_16439(tickDelta, this.lastYaw, this.yaw);
/*     */   }
/*     */   public double getPitch(float tickDelta) {
/* 499 */     return class_3532.method_16439(tickDelta, this.lastPitch, this.pitch);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\Freecam.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */