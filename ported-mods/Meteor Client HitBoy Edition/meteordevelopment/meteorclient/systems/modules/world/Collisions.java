/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ 
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.CollisionShapeEvent;
/*     */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*     */ import meteordevelopment.meteorclient.settings.BlockListSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_259;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2828;
/*     */ import net.minecraft.class_2833;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Collisions
/*     */   extends Module
/*     */ {
/*  27 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  29 */   public final Setting<List<class_2248>> blocks = this.sgGeneral.add((Setting)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder())
/*  30 */       .name("blocks"))
/*  31 */       .description("What blocks should be added collision box."))
/*  32 */       .filter(this::blockFilter)
/*  33 */       .build());
/*     */ 
/*     */   
/*  36 */   private final Setting<Boolean> magma = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  37 */       .name("magma"))
/*  38 */       .description("Prevents you from walking over magma blocks."))
/*  39 */       .defaultValue(Boolean.valueOf(false)))
/*  40 */       .build());
/*     */ 
/*     */   
/*  43 */   private final Setting<Boolean> unloadedChunks = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  44 */       .name("unloaded-chunks"))
/*  45 */       .description("Stops you from going into unloaded chunks."))
/*  46 */       .defaultValue(Boolean.valueOf(false)))
/*  47 */       .build());
/*     */ 
/*     */   
/*  50 */   private final Setting<Boolean> ignoreBorder = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  51 */       .name("ignore-border"))
/*  52 */       .description("Removes world border collision."))
/*  53 */       .defaultValue(Boolean.valueOf(false)))
/*  54 */       .build());
/*     */ 
/*     */   
/*     */   public Collisions() {
/*  58 */     super(Categories.World, "collisions", "Adds collision boxes to certain blocks/areas.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onCollisionShape(CollisionShapeEvent event) {
/*  63 */     if (this.mc.field_1687 == null || this.mc.field_1724 == null)
/*  64 */       return;  if (!event.state.method_26227().method_15769())
/*  65 */       return;  if (((List)this.blocks.get()).contains(event.state.method_26204())) {
/*  66 */       event.shape = class_259.method_1077();
/*  67 */     } else if (((Boolean)this.magma.get()).booleanValue() && !this.mc.field_1724.method_5715() && event.state
/*  68 */       .method_26215() && this.mc.field_1687
/*  69 */       .method_8320(event.pos.method_10074()).method_26204() == class_2246.field_10092) {
/*  70 */       event.shape = class_259.method_1077();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPlayerMove(PlayerMoveEvent event) {
/*  76 */     int x = (int)(this.mc.field_1724.method_23317() + event.movement.field_1352) >> 4;
/*  77 */     int z = (int)(this.mc.field_1724.method_23321() + event.movement.field_1350) >> 4;
/*  78 */     if (((Boolean)this.unloadedChunks.get()).booleanValue() && !this.mc.field_1687.method_2935().method_12123(x, z)) {
/*  79 */       ((IVec3d)event.movement).meteor$set(0.0D, event.movement.field_1351, 0.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacketSend(PacketEvent.Send event) {
/*  85 */     if (!((Boolean)this.unloadedChunks.get()).booleanValue())
/*  86 */       return;  class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2833) { class_2833 packet = (class_2833)class_2596;
/*  87 */       if (!this.mc.field_1687.method_2935().method_12123((int)packet.comp_3350().method_10216() >> 4, (int)packet.comp_3350().method_10215() >> 4)) {
/*  88 */         this.mc.field_1724.method_5854().method_30634((this.mc.field_1724.method_5854()).field_6014, (this.mc.field_1724.method_5854()).field_6036, (this.mc.field_1724.method_5854()).field_5969);
/*  89 */         event.cancel();
/*     */       }  }
/*  91 */     else { class_2596 = event.packet; if (class_2596 instanceof class_2828) { class_2828 packet = (class_2828)class_2596;
/*  92 */         if (!this.mc.field_1687.method_2935().method_12123((int)packet.method_12269(this.mc.field_1724.method_23317()) >> 4, (int)packet.method_12274(this.mc.field_1724.method_23321()) >> 4))
/*  93 */           event.cancel();  }
/*     */        }
/*     */   
/*     */   }
/*     */   
/*     */   private boolean blockFilter(class_2248 block) {
/*  99 */     return (block instanceof net.minecraft.class_4770 || block instanceof net.minecraft.class_2231 || block instanceof net.minecraft.class_2538 || block instanceof net.minecraft.class_2537 || block instanceof net.minecraft.class_2560 || block instanceof net.minecraft.class_3922 || block instanceof net.minecraft.class_3830 || block instanceof net.minecraft.class_2266 || block instanceof net.minecraft.class_2241 || block instanceof net.minecraft.class_2533 || block instanceof net.minecraft.class_5635 || block instanceof net.minecraft.class_2275 || block instanceof net.minecraft.class_4622);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean ignoreBorder() {
/* 116 */     return (isActive() && ((Boolean)this.ignoreBorder.get()).booleanValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\Collisions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */