/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MixinPlugin;
/*     */ import meteordevelopment.meteorclient.events.render.RenderBlockEntityEvent;
/*     */ import meteordevelopment.meteorclient.events.world.AmbientOcclusionEvent;
/*     */ import meteordevelopment.meteorclient.events.world.ChunkOcclusionEvent;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.settings.BlockListSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.irisshaders.iris.api.v0.IrisApi;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_259;
/*     */ import net.minecraft.class_2680;
/*     */ 
/*     */ public class Xray
/*     */   extends Module
/*     */ {
/*  32 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  34 */   public static final List<class_2248> ORES = List.of(new class_2248[] { class_2246.field_10418, class_2246.field_29219, class_2246.field_10212, class_2246.field_29027, class_2246.field_10571, class_2246.field_29026, class_2246.field_10090, class_2246.field_29028, class_2246.field_10080, class_2246.field_29030, class_2246.field_10442, class_2246.field_29029, class_2246.field_10013, class_2246.field_29220, class_2246.field_27120, class_2246.field_29221, class_2246.field_23077, class_2246.field_10213, class_2246.field_22109 });
/*     */   
/*  36 */   private final Setting<List<class_2248>> blocks = this.sgGeneral.add((Setting)((BlockListSetting.Builder)((BlockListSetting.Builder)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder())
/*  37 */       .name("whitelist"))
/*  38 */       .description("Which blocks to show x-rayed."))
/*  39 */       .defaultValue(ORES))
/*  40 */       .onChanged(v -> {
/*     */           if (isActive())
/*     */             this.mc.field_1769.method_3279(); 
/*  43 */         })).build());
/*     */ 
/*     */   
/*  46 */   public final Setting<Integer> opacity = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  47 */       .name("opacity"))
/*  48 */       .description("The opacity for all other blocks."))
/*  49 */       .defaultValue(Integer.valueOf(25)))
/*  50 */       .range(0, 255)
/*  51 */       .sliderMax(255)
/*  52 */       .onChanged(onChanged -> {
/*     */           if (isActive())
/*     */             this.mc.field_1769.method_3279(); 
/*  55 */         })).build());
/*     */ 
/*     */   
/*  58 */   private final Setting<Boolean> exposedOnly = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  59 */       .name("exposed-only"))
/*  60 */       .description("Show only exposed ores."))
/*  61 */       .defaultValue(Boolean.valueOf(false)))
/*  62 */       .onChanged(onChanged -> {
/*     */           if (isActive())
/*     */             this.mc.field_1769.method_3279(); 
/*  65 */         })).build());
/*     */   
/*     */   public Xray() {
/*  68 */     super(Categories.Render, "xray", "Only renders specified blocks. Good for mining.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  73 */     this.mc.field_1769.method_3279();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  78 */     this.mc.field_1769.method_3279();
/*     */   }
/*     */ 
/*     */   
/*     */   public WWidget getWidget(GuiTheme theme) {
/*  83 */     if (MixinPlugin.isIrisPresent && IrisApi.getInstance().isShaderPackInUse()) return (WWidget)theme.label("Warning: Due to shaders in use, opacity is overridden to 0.");
/*     */     
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRenderBlockEntity(RenderBlockEntityEvent event) {
/*  90 */     if (isBlocked(event.blockEntityState.field_62674.method_26204(), event.blockEntityState.field_62673)) event.cancel(); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onChunkOcclusion(ChunkOcclusionEvent event) {
/*  95 */     event.cancel();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onAmbientOcclusion(AmbientOcclusionEvent event) {
/* 100 */     event.lightLevel = 1.0F;
/*     */   }
/*     */   
/*     */   public boolean modifyDrawSide(class_2680 state, class_1922 view, class_2338 pos, class_2350 facing, boolean returns) {
/* 104 */     if (!returns && !isBlocked(state.method_26204(), pos)) {
/* 105 */       class_2338 adjPos = pos.method_10093(facing);
/* 106 */       class_2680 adjState = view.method_8320(adjPos);
/* 107 */       return (adjState.method_26173(facing.method_10153()) != class_259.method_1077() || adjState.method_26204() != state.method_26204() || !adjState.method_26216() || isBlocked(adjState.method_26204(), adjPos));
/*     */     } 
/*     */     
/* 110 */     return returns;
/*     */   }
/*     */   
/*     */   public boolean isBlocked(class_2248 block, class_2338 blockPos) {
/* 114 */     return (!((List)this.blocks.get()).contains(block) || (((Boolean)this.exposedOnly.get()).booleanValue() && blockPos != null && !BlockUtils.isExposed(blockPos)));
/*     */   }
/*     */   
/*     */   public static int getAlpha(class_2680 state, class_2338 pos) {
/* 118 */     WallHack wallHack = (WallHack)Modules.get().get(WallHack.class);
/* 119 */     Xray xray = (Xray)Modules.get().get(Xray.class);
/*     */     
/* 121 */     if (wallHack.isActive() && ((List)wallHack.blocks.get()).contains(state.method_26204())) {
/* 122 */       int alpha; if (MixinPlugin.isIrisPresent && IrisApi.getInstance().isShaderPackInUse()) return 0;
/*     */ 
/*     */ 
/*     */       
/* 126 */       if (xray.isActive()) { alpha = ((Integer)xray.opacity.get()).intValue(); }
/* 127 */       else { alpha = ((Integer)wallHack.opacity.get()).intValue(); }
/*     */       
/* 129 */       return alpha;
/*     */     } 
/* 131 */     if (xray.isActive() && !wallHack.isActive() && xray.isBlocked(state.method_26204(), pos)) {
/* 132 */       return (MixinPlugin.isIrisPresent && IrisApi.getInstance().isShaderPackInUse()) ? 0 : ((Integer)xray.opacity.get()).intValue();
/*     */     }
/*     */     
/* 135 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\Xray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */