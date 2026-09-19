/*     */ package meteordevelopment.meteorclient.systems.modules.movement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.game.GameJoinedEvent;
/*     */ import meteordevelopment.meteorclient.events.game.GameLeftEvent;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.KeybindSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
/*     */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2828;
/*     */ import org.joml.Vector3d;
/*     */ 
/*     */ public class Blink extends Module {
/*  27 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  29 */   private final Setting<Boolean> renderOriginal = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  30 */       .name("render-original"))
/*  31 */       .description("Renders your player model at the original position."))
/*  32 */       .defaultValue(Boolean.valueOf(true)))
/*  33 */       .build());
/*     */ 
/*     */   
/*  36 */   private final Setting<Integer> delay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  37 */       .name("pulse-delay"))
/*  38 */       .description("After the duration in ticks has elapsed, send all packets and start blinking again. 0 to disable."))
/*  39 */       .defaultValue(Integer.valueOf(0)))
/*  40 */       .min(0)
/*  41 */       .sliderMax(60)
/*  42 */       .build());
/*     */ 
/*     */   
/*  45 */   private final Setting<Keybind> cancelBlink = this.sgGeneral
/*  46 */     .add((Setting)((KeybindSetting.Builder)((KeybindSetting.Builder)((KeybindSetting.Builder)(new KeybindSetting.Builder())
/*  47 */       .name("cancel-blink"))
/*  48 */       .description("Cancels sending packets and sends you back to your original position."))
/*  49 */       .defaultValue(Keybind.none()))
/*  50 */       .action(() -> {
/*     */           this.cancelled = true;
/*     */           
/*     */           disable();
/*  54 */         }).build());
/*     */ 
/*     */   
/*  57 */   private final List<class_2828> packets = new ArrayList<>();
/*     */   private FakePlayerEntity model;
/*  59 */   private final Vector3d start = new Vector3d();
/*     */   private boolean cancelled;
/*     */   private boolean sending;
/*  62 */   private int timer = 0;
/*     */   
/*     */   public Blink() {
/*  65 */     super(Categories.Movement, "blink", "Allows you to essentially teleport while suspending motion updates.");
/*     */     
/*  67 */     this.runInMainMenu = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  72 */     if (!Utils.canUpdate())
/*     */       return; 
/*  74 */     if (((Boolean)this.renderOriginal.get()).booleanValue()) {
/*  75 */       this.model = new FakePlayerEntity((class_1657)this.mc.field_1724, this.mc.field_1724.method_7334().name(), 20.0F, true);
/*  76 */       this.model.doNotPush = true;
/*  77 */       this.model.hideWhenInsideCamera = true;
/*  78 */       this.model.noHit = true;
/*  79 */       this.model.spawn();
/*     */     } 
/*     */     
/*  82 */     Utils.set(this.start, this.mc.field_1724.method_73189());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  87 */     if (!Utils.canUpdate())
/*     */       return; 
/*  89 */     dumpPackets(!this.cancelled);
/*     */     
/*  91 */     if (this.cancelled) {
/*  92 */       this.mc.field_1724.method_23327(this.start.x, this.start.y, this.start.z);
/*  93 */       this.mc.field_1724.method_18799(class_243.field_1353);
/*     */     } 
/*     */     
/*  96 */     this.cancelled = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 101 */     if (!Utils.canUpdate())
/*     */       return; 
/* 103 */     this.timer++;
/*     */     
/* 105 */     if (((Integer)this.delay.get()).intValue() != 0 && ((Integer)this.delay.get()).intValue() <= this.timer) {
/* 106 */       onDeactivate();
/* 107 */       onActivate();
/*     */     } 
/*     */   }
/*     */   @EventHandler
/*     */   private void onSendPacket(PacketEvent.Send event) {
/*     */     class_2828 p;
/* 113 */     if (!Utils.canUpdate())
/*     */       return; 
/* 115 */     if (this.sending)
/* 116 */       return;  class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2828) { p = (class_2828)class_2596; } else { return; }
/* 117 */      event.cancel();
/*     */     
/* 119 */     class_2828 prev = this.packets.isEmpty() ? null : this.packets.getLast();
/*     */     
/* 121 */     if (prev != null && p
/* 122 */       .method_12273() == prev.method_12273() && p
/* 123 */       .method_12271(-1.0F) == prev.method_12271(-1.0F) && p
/* 124 */       .method_12270(-1.0F) == prev.method_12270(-1.0F) && p
/* 125 */       .method_12269(-1.0D) == prev.method_12269(-1.0D) && p
/* 126 */       .method_12268(-1.0D) == prev.method_12268(-1.0D) && p
/* 127 */       .method_12274(-1.0D) == prev.method_12274(-1.0D)) {
/*     */       return;
/*     */     }
/* 130 */     synchronized (this.packets) {
/* 131 */       this.packets.add(p);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onJoinGame(GameJoinedEvent event) {
/* 137 */     warning("Blink is currently enabled; you won't be able to interact with anything properly until you disable it!", new Object[0]);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onLeaveGame(GameLeftEvent event) {
/* 142 */     onDeactivate();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 147 */     return String.format("%.1f", new Object[] { Float.valueOf(this.timer / 20.0F) });
/*     */   }
/*     */   
/*     */   private void dumpPackets(boolean send) {
/* 151 */     this.sending = true;
/* 152 */     synchronized (this.packets) {
/* 153 */       if (send) { Objects.requireNonNull(this.mc.field_1724.field_3944); this.packets.forEach(this.mc.field_1724.field_3944::method_52787); }
/* 154 */        this.packets.clear();
/*     */     } 
/* 156 */     this.sending = false;
/*     */     
/* 158 */     if (this.model != null) {
/* 159 */       this.model.despawn();
/* 160 */       this.model = null;
/*     */     } 
/*     */     
/* 163 */     this.timer = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\Blink.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */