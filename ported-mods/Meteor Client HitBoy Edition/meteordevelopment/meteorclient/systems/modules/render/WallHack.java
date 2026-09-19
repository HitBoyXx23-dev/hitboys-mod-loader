/*    */ package meteordevelopment.meteorclient.systems.modules.render;
/*    */ 
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.MixinPlugin;
/*    */ import meteordevelopment.meteorclient.events.world.ChunkOcclusionEvent;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.settings.BlockListSetting;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.IntSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.irisshaders.iris.api.v0.IrisApi;
/*    */ import net.minecraft.class_2248;
/*    */ 
/*    */ public class WallHack
/*    */   extends Module
/*    */ {
/* 22 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 24 */   public final Setting<Integer> opacity = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 25 */       .name("opacity"))
/* 26 */       .description("The opacity for rendered blocks."))
/* 27 */       .defaultValue(Integer.valueOf(0)))
/* 28 */       .range(0, 255)
/* 29 */       .sliderMax(255)
/* 30 */       .onChanged(onChanged -> {
/*    */           
/*    */           if (isActive()) {
/*    */             this.mc.field_1769.method_3279();
/*    */           }
/* 35 */         })).build());
/*    */ 
/*    */   
/* 38 */   public final Setting<List<class_2248>> blocks = this.sgGeneral.add((Setting)((BlockListSetting.Builder)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder())
/* 39 */       .name("blocks"))
/* 40 */       .description("What blocks should be targeted for Wall Hack."))
/* 41 */       .defaultValue(new class_2248[0])
/* 42 */       .onChanged(onChanged -> {
/*    */           if (isActive())
/*    */             this.mc.field_1769.method_3279(); 
/* 45 */         })).build());
/*    */ 
/*    */   
/* 48 */   public final Setting<Boolean> occludeChunks = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 49 */       .name("occlude-chunks"))
/* 50 */       .description("Whether caves should occlude underground (may look wonky when on)."))
/* 51 */       .defaultValue(Boolean.valueOf(false)))
/* 52 */       .build());
/*    */ 
/*    */   
/*    */   public WallHack() {
/* 56 */     super(Categories.Render, "wall-hack", "Makes blocks translucent.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onActivate() {
/* 61 */     this.mc.field_1769.method_3279();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDeactivate() {
/* 66 */     this.mc.field_1769.method_3279();
/*    */   }
/*    */ 
/*    */   
/*    */   public WWidget getWidget(GuiTheme theme) {
/* 71 */     if (MixinPlugin.isIrisPresent && IrisApi.getInstance().isShaderPackInUse()) {
/* 72 */       return (WWidget)theme.label("Warning: Due to shaders in use, opacity is overridden to 0.");
/*    */     }
/* 74 */     return null;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onChunkOcclusion(ChunkOcclusionEvent event) {
/* 79 */     if (!((Boolean)this.occludeChunks.get()).booleanValue()) event.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\WallHack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */