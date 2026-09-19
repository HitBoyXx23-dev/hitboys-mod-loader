/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ import java.util.Objects;
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
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1893;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_265;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class EChestFarmer extends Module {
/*  35 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  36 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */   
/*  38 */   private final Setting<Boolean> selfToggle = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  39 */       .name("self-toggle"))
/*  40 */       .description("Disables when you reach the desired amount of obsidian."))
/*  41 */       .defaultValue(Boolean.valueOf(false)))
/*  42 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> ignoreExisting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> amount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> swingHand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> render;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<ShapeMode> shapeMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> sideColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> lineColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class_265 SHAPE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_2338 target;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int startCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EChestFarmer() {
/* 107 */     super(Categories.World, "echest-farmer", "Places and breaks EChests to farm obsidian."); Objects.requireNonNull(this.selfToggle); this.ignoreExisting = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("ignore-existing")).description("Ignores existing obsidian in your inventory and mines the total target amount.")).defaultValue(Boolean.valueOf(true))).visible(this.selfToggle::get)).build()); Objects.requireNonNull(this.selfToggle); this.amount = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("amount")).description("The amount of obsidian to farm.")).defaultValue(Integer.valueOf(64))).sliderMax(128).range(8, 512).sliderRange(8, 128).visible(this.selfToggle::get)).build()); this.swingHand = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("swing-hand")).description("Swing hand client-side.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.render = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("render")).description("Renders a block overlay where the obsidian will be placed.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("shape-mode")).description("How the shapes are rendered.")).defaultValue(ShapeMode.Both)).build());
/*     */     this.sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("side-color")).description("The color of the sides of the blocks being rendered.")).defaultValue(new SettingColor(204, 0, 0, 50)).build());
/*     */     this.lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("line-color")).description("The color of the lines of the blocks being rendered.")).defaultValue(new SettingColor(204, 0, 0, 255)).build());
/* 112 */     this.SHAPE = class_2248.method_9541(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D); } public void onActivate() { this.target = null;
/* 113 */     this.startCount = InvUtils.find(new class_1792[] { class_1802.field_8281 }).count(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 118 */     InvUtils.swapBack();
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 124 */     if (this.target == null) {
/* 125 */       if (this.mc.field_1765 == null || this.mc.field_1765.method_17783() != class_239.class_240.field_1332)
/*     */         return; 
/* 127 */       class_2338 pos = ((class_3965)this.mc.field_1765).method_17777().method_10084();
/* 128 */       class_2680 state = this.mc.field_1687.method_8320(pos);
/*     */       
/* 130 */       if (state.method_45474() || state.method_26204() == class_2246.field_10443) {
/* 131 */         this.target = ((class_3965)this.mc.field_1765).method_17777().method_10084();
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/* 136 */     if (!PlayerUtils.isWithinReach(this.target)) {
/* 137 */       error("Target block pos out of reach.", new Object[0]);
/* 138 */       this.target = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 143 */     if (((Boolean)this.selfToggle.get()).booleanValue() && InvUtils.find(new class_1792[] { class_1802.field_8281 }).count() - (((Boolean)this.ignoreExisting.get()).booleanValue() ? this.startCount : 0) >= ((Integer)this.amount.get()).intValue()) {
/* 144 */       InvUtils.swapBack();
/* 145 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 150 */     if (this.mc.field_1687.method_8320(this.target).method_26204() == class_2246.field_10443) {
/* 151 */       double bestScore = -1.0D;
/* 152 */       int bestSlot = -1;
/*     */       
/* 154 */       for (int i = 0; i < 9; i++) {
/* 155 */         class_1799 itemStack = this.mc.field_1724.method_31548().method_5438(i);
/* 156 */         if (!Utils.hasEnchantment(itemStack, class_1893.field_9099)) {
/*     */           
/* 158 */           double score = itemStack.method_7924(class_2246.field_10443.method_9564());
/*     */           
/* 160 */           if (score > bestScore) {
/* 161 */             bestScore = score;
/* 162 */             bestSlot = i;
/*     */           } 
/*     */         } 
/*     */       } 
/* 166 */       if (bestSlot == -1)
/*     */         return; 
/* 168 */       InvUtils.swap(bestSlot, true);
/* 169 */       BlockUtils.breakBlock(this.target, ((Boolean)this.swingHand.get()).booleanValue());
/*     */     } 
/*     */ 
/*     */     
/* 173 */     if (this.mc.field_1687.method_8320(this.target).method_45474()) {
/* 174 */       FindItemResult echest = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8466 });
/*     */       
/* 176 */       if (!echest.found()) {
/* 177 */         error("No Echests in hotbar, disabling", new Object[0]);
/* 178 */         toggle();
/*     */         
/*     */         return;
/*     */       } 
/* 182 */       BlockUtils.place(this.target, echest, true, 0, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 188 */     if (this.target == null || !((Boolean)this.render.get()).booleanValue() || ((PacketMine)Modules.get().get(PacketMine.class)).isMiningBlock(this.target))
/*     */       return; 
/* 190 */     class_238 box = this.SHAPE.method_1090().getFirst();
/* 191 */     event.renderer.box(this.target.method_10263() + box.field_1323, this.target.method_10264() + box.field_1322, this.target.method_10260() + box.field_1321, this.target.method_10263() + box.field_1320, this.target.method_10264() + box.field_1325, this.target.method_10260() + box.field_1324, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\EChestFarmer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */