/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ import java.util.ArrayList;
/*     */ import meteordevelopment.meteorclient.events.entity.player.StartBreakingBlockEvent;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.BreakIndicators;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.Pool;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_265;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2846;
/*     */ import net.minecraft.class_2868;
/*     */ import net.minecraft.class_310;
/*     */ 
/*     */ public class PacketMine extends Module {
/*  37 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  38 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */ 
/*     */ 
/*     */   
/*  42 */   private final Setting<Integer> delay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  43 */       .name("delay"))
/*  44 */       .description("Delay between mining blocks in ticks."))
/*  45 */       .defaultValue(Integer.valueOf(1)))
/*  46 */       .min(0)
/*  47 */       .build());
/*     */ 
/*     */   
/*  50 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  51 */       .name("rotate"))
/*  52 */       .description("Sends rotation packets to the server when mining."))
/*  53 */       .defaultValue(Boolean.valueOf(true)))
/*  54 */       .build());
/*     */ 
/*     */   
/*  57 */   private final Setting<Boolean> autoSwitch = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  58 */       .name("auto-switch"))
/*  59 */       .description("Automatically switches to the best tool when the block is ready to be mined instantly."))
/*  60 */       .defaultValue(Boolean.valueOf(false)))
/*  61 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> notOnUse;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> obscureBreakingProgress;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> render;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<ShapeMode> shapeMode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> readySideColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> readyLineColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> sideColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> lineColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Pool<MyBlock> blockPool;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<MyBlock> blocks;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean swapped;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldUpdateSlot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketMine() {
/* 129 */     super(Categories.World, "packet-mine", "Sends packets to mine blocks without the mining animation."); Objects.requireNonNull(this.autoSwitch); this.notOnUse = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("not-on-use")).description("Won't auto switch if you're using an item.")).defaultValue(Boolean.valueOf(true))).visible(this.autoSwitch::get)).build()); this.obscureBreakingProgress = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("obscure-breaking-progress")).description("Spams abort breaking packets to obscure the block mining progress from other players. Does not hide it perfectly.")).defaultValue(Boolean.valueOf(false))).build()); this.render = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("render")).description("Whether or not to render the block being mined.")).defaultValue(Boolean.valueOf(true))).build()); this.shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("shape-mode")).description("How the shapes are rendered.")).defaultValue(ShapeMode.Both)).build()); this.readySideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("ready-side-color")).description("The color of the sides of the blocks that can be broken.")).defaultValue(new SettingColor(0, 204, 0, 10)).build());
/*     */     this.readyLineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("ready-line-color")).description("The color of the lines of the blocks that can be broken.")).defaultValue(new SettingColor(0, 204, 0, 255)).build());
/*     */     this.sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("side-color")).description("The color of the sides of the blocks being rendered.")).defaultValue(new SettingColor(204, 0, 0, 10)).build());
/*     */     this.lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("line-color")).description("The color of the lines of the blocks being rendered.")).defaultValue(new SettingColor(204, 0, 0, 255)).build());
/*     */     this.blockPool = new Pool(() -> new MyBlock());
/* 134 */     this.blocks = new ArrayList<>(); } public void onActivate() { this.swapped = false; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 139 */     this.blockPool.freeAll(this.blocks);
/* 140 */     this.blocks.clear();
/*     */     
/* 142 */     if (this.shouldUpdateSlot) {
/* 143 */       this.mc.field_1724.field_3944.method_52787((class_2596)new class_2868(this.mc.field_1724.method_31548().method_67532()));
/* 144 */       this.shouldUpdateSlot = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onStartBreakingBlock(StartBreakingBlockEvent event) {
/* 150 */     if (!BlockUtils.canBreak(event.blockPos))
/* 151 */       return;  event.cancel();
/*     */     
/* 153 */     this.swapped = false;
/*     */     
/* 155 */     if (!isMiningBlock(event.blockPos)) {
/* 156 */       this.blocks.add(((MyBlock)this.blockPool.get()).set(event));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isMiningBlock(class_2338 pos) {
/* 161 */     for (MyBlock block : this.blocks) {
/* 162 */       if (block.blockPos.equals(pos)) return true;
/*     */     
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 170 */     this.blocks.removeIf(MyBlock::shouldRemove);
/*     */     
/* 172 */     if (this.shouldUpdateSlot) {
/* 173 */       this.mc.field_1724.field_3944.method_52787((class_2596)new class_2868(this.mc.field_1724.method_31548().method_67532()));
/* 174 */       this.shouldUpdateSlot = false;
/* 175 */       this.swapped = false;
/*     */     } 
/*     */     
/* 178 */     if (!this.blocks.isEmpty()) {
/* 179 */       MyBlock block = this.blocks.getFirst();
/* 180 */       block.mine();
/*     */       
/* 182 */       if (block.isReady() && !this.swapped && ((Boolean)this.autoSwitch.get()).booleanValue() && (!this.mc.field_1724.method_6115() || !((Boolean)this.notOnUse.get()).booleanValue())) {
/* 183 */         FindItemResult slot = InvUtils.findFastestTool(block.blockState);
/* 184 */         if (!slot.found() || this.mc.field_1724.method_31548().method_67532() == slot.slot())
/* 185 */           return;  this.mc.field_1724.field_3944.method_52787((class_2596)new class_2868(slot.slot()));
/* 186 */         this.swapped = true;
/* 187 */         this.shouldUpdateSlot = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 194 */     if (!((Boolean)this.render.get()).booleanValue())
/*     */       return; 
/* 196 */     for (MyBlock block : this.blocks) {
/* 197 */       if (!((BreakIndicators)Modules.get().get(BreakIndicators.class)).isActive() || !((Boolean)((BreakIndicators)Modules.get().get(BreakIndicators.class)).packetMine.get()).booleanValue() || !block.mining) {
/* 198 */         block.render(event);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public class MyBlock
/*     */   {
/*     */     public class_2338 blockPos;
/*     */     public class_2680 blockState;
/*     */     public class_2248 block;
/*     */     public class_2350 direction;
/*     */     public int timer;
/*     */     public int startTime;
/*     */     public boolean mining;
/*     */     
/*     */     public MyBlock set(StartBreakingBlockEvent event) {
/* 214 */       this.blockPos = event.blockPos;
/* 215 */       this.direction = event.direction;
/* 216 */       this.blockState = PacketMine.this.mc.field_1687.method_8320(this.blockPos);
/* 217 */       this.block = this.blockState.method_26204();
/* 218 */       this.timer = ((Integer)PacketMine.this.delay.get()).intValue();
/* 219 */       this.mining = false;
/*     */       
/* 221 */       return this;
/*     */     }
/*     */     
/*     */     public boolean shouldRemove() {
/* 225 */       boolean broken = (PacketMine.this.mc.field_1687.method_8320(this.blockPos).method_26204() != this.block);
/* 226 */       boolean timeout = (progress() > 2.0D && PacketMine.this.mc.field_1724.field_6012 - this.startTime > 50);
/* 227 */       boolean distance = (Utils.distance((PacketMine.this.mc.field_1724.method_33571()).field_1352, (PacketMine.this.mc.field_1724.method_33571()).field_1351, (PacketMine.this.mc.field_1724.method_33571()).field_1350, (this.blockPos.method_10263() + this.direction.method_10148()), (this.blockPos.method_10264() + this.direction.method_10164()), (this.blockPos.method_10260() + this.direction.method_10165())) > PacketMine.this.mc.field_1724.method_55754());
/*     */       
/* 229 */       return (broken || timeout || distance);
/*     */     }
/*     */     
/*     */     public boolean isReady() {
/* 233 */       return (progress() >= 1.0D);
/*     */     }
/*     */     
/*     */     public double progress() {
/* 237 */       if (!this.mining) return 0.0D;
/*     */       
/* 239 */       FindItemResult fir = InvUtils.findFastestTool(this.blockState);
/* 240 */       return BlockUtils.getBreakDelta(fir.found() ? fir.slot() : PacketMine.this.mc.field_1724.method_31548().method_67532(), this.blockState) * (PacketMine.this.mc.field_1724.field_6012 - this.startTime + 1);
/*     */     }
/*     */     
/*     */     public void mine() {
/* 244 */       if (((Boolean)PacketMine.this.rotate.get()).booleanValue()) { Rotations.rotate(Rotations.getYaw(this.blockPos), Rotations.getPitch(this.blockPos), 50, this::sendMinePackets); }
/* 245 */       else { sendMinePackets(); }
/*     */     
/*     */     }
/*     */     private void sendMinePackets() {
/* 249 */       if (this.timer <= 0) {
/* 250 */         if (!this.mining) {
/* 251 */           PacketMine.this.mc.field_1761.method_41931(PacketMine.this.mc.field_1687, sequence -> new class_2846(class_2846.class_2847.field_12968, this.blockPos, this.direction, sequence));
/* 252 */           PacketMine.this.mc.field_1761.method_41931(PacketMine.this.mc.field_1687, sequence -> new class_2846(class_2846.class_2847.field_12973, this.blockPos, this.direction, sequence));
/*     */           
/* 254 */           this.mining = true;
/* 255 */           this.startTime = PacketMine.this.mc.field_1724.field_6012;
/*     */         } 
/*     */       } else {
/*     */         
/* 259 */         this.timer--;
/*     */       } 
/*     */       
/* 262 */       if (this.mining && ((Boolean)PacketMine.this.obscureBreakingProgress.get()).booleanValue()) PacketMine.this.mc.method_1562().method_52787((class_2596)new class_2846(class_2846.class_2847.field_12971, this.blockPos, this.direction)); 
/*     */     }
/*     */     
/*     */     public void render(Render3DEvent event) {
/* 266 */       class_265 shape = PacketMine.this.mc.field_1687.method_8320(this.blockPos).method_26218((class_1922)PacketMine.this.mc.field_1687, this.blockPos);
/*     */       
/* 268 */       double x1 = this.blockPos.method_10263();
/* 269 */       double y1 = this.blockPos.method_10264();
/* 270 */       double z1 = this.blockPos.method_10260();
/* 271 */       double x2 = (this.blockPos.method_10263() + 1);
/* 272 */       double y2 = (this.blockPos.method_10264() + 1);
/* 273 */       double z2 = (this.blockPos.method_10260() + 1);
/*     */       
/* 275 */       if (!shape.method_1110()) {
/* 276 */         x1 = this.blockPos.method_10263() + shape.method_1091(class_2350.class_2351.field_11048);
/* 277 */         y1 = this.blockPos.method_10264() + shape.method_1091(class_2350.class_2351.field_11052);
/* 278 */         z1 = this.blockPos.method_10260() + shape.method_1091(class_2350.class_2351.field_11051);
/* 279 */         x2 = this.blockPos.method_10263() + shape.method_1105(class_2350.class_2351.field_11048);
/* 280 */         y2 = this.blockPos.method_10264() + shape.method_1105(class_2350.class_2351.field_11052);
/* 281 */         z2 = this.blockPos.method_10260() + shape.method_1105(class_2350.class_2351.field_11051);
/*     */       } 
/*     */       
/* 284 */       if (isReady()) {
/* 285 */         event.renderer.box(x1, y1, z1, x2, y2, z2, (Color)PacketMine.this.readySideColor.get(), (Color)PacketMine.this.readyLineColor.get(), (ShapeMode)PacketMine.this.shapeMode.get(), 0);
/*     */       } else {
/* 287 */         event.renderer.box(x1, y1, z1, x2, y2, z2, (Color)PacketMine.this.sideColor.get(), (Color)PacketMine.this.lineColor.get(), (ShapeMode)PacketMine.this.shapeMode.get(), 0);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\PacketMine.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */