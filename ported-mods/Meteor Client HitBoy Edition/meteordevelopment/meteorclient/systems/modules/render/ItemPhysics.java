/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.events.render.ApplyTransformationEvent;
/*     */ import meteordevelopment.meteorclient.events.render.RenderItemEntityEvent;
/*     */ import meteordevelopment.meteorclient.mixin.ItemRenderStateAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.LayerRenderStateAccessor;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_10444;
/*     */ import net.minecraft.class_1542;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4608;
/*     */ import net.minecraft.class_5819;
/*     */ import net.minecraft.class_777;
/*     */ import net.minecraft.class_7833;
/*     */ import net.minecraft.class_804;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemPhysics
/*     */   extends Module
/*     */ {
/*     */   private static final float PIXEL_SIZE = 0.0625F;
/*  34 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  36 */   private final Setting<Boolean> randomRotation = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  37 */       .name("random-rotation"))
/*  38 */       .description("Adds a random rotation to every item."))
/*  39 */       .defaultValue(Boolean.valueOf(true)))
/*  40 */       .build());
/*     */ 
/*     */   
/*  43 */   private final class_5819 random = class_5819.method_43053();
/*     */   private boolean skipTransformation;
/*     */   
/*     */   public ItemPhysics() {
/*  47 */     super(Categories.Render, "item-physics", "Applies physics to items on the ground.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRenderItemEntity(RenderItemEntityEvent event) {
/*  52 */     event.cancel();
/*     */     
/*  54 */     if (event.renderState.field_55310.method_65606() || event.itemEntity == null) {
/*     */       return;
/*     */     }
/*  57 */     class_4587 matrices = event.matrixStack;
/*     */     
/*  59 */     this.random.method_43052(event.itemEntity.method_5628() * 89748956L);
/*     */     
/*  61 */     for (int i = 0; i < ((ItemRenderStateAccessor)event.renderState.field_55310).meteor$getLayerCount(); i++) {
/*  62 */       class_10444.class_10446 layer = ((ItemRenderStateAccessor)event.renderState.field_55310).meteor$getLayers()[i];
/*  63 */       ModelInfo info = getInfo(layer.method_67997());
/*     */       
/*  65 */       matrices.method_22903();
/*  66 */       applyTransformation(matrices, ((LayerRenderStateAccessor)layer).meteor$getTransform());
/*  67 */       matrices.method_46416(0.0F, info.offsetY, 0.0F);
/*  68 */       offsetInWater(matrices, event.itemEntity);
/*     */       
/*  70 */       if (info.flat) {
/*  71 */         matrices.method_22907((Quaternionfc)class_7833.field_40714.rotationDegrees(90.0F));
/*  72 */         matrices.method_46416(0.0F, 0.0F, info.offsetZ);
/*     */       } 
/*     */       
/*  75 */       if (((Boolean)this.randomRotation.get()).booleanValue()) {
/*  76 */         class_7833 axis = class_7833.field_40716;
/*  77 */         float x = 0.5F;
/*  78 */         float y = 0.0F;
/*  79 */         float z = 0.5F;
/*     */         
/*  81 */         if (info.flat) {
/*  82 */           axis = class_7833.field_40718;
/*  83 */           y = 0.5F;
/*  84 */           z = 0.0F;
/*     */         } 
/*     */         
/*  87 */         float degrees = (this.random.method_43057() * 2.0F - 1.0F) * 90.0F;
/*     */         
/*  89 */         matrices.method_46416(x, y, z);
/*  90 */         matrices.method_22907((Quaternionfc)axis.rotationDegrees(degrees));
/*  91 */         matrices.method_46416(-x, -y, -z);
/*     */       } 
/*     */       
/*  94 */       renderLayer(event, info);
/*     */       
/*  96 */       matrices.method_22909();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onApplyTransformation(ApplyTransformationEvent event) {
/* 102 */     if (this.skipTransformation)
/* 103 */       event.cancel(); 
/*     */   }
/*     */   
/*     */   private void renderLayer(RenderItemEntityEvent event, ModelInfo info) {
/* 107 */     class_4587 matrices = event.matrixStack;
/* 108 */     this.skipTransformation = true;
/*     */     
/* 110 */     for (int j = 0; j < event.renderState.field_55311; j++) {
/* 111 */       matrices.method_22903();
/*     */       
/* 113 */       if (j > 0) {
/* 114 */         float x = (this.random.method_43057() * 2.0F - 1.0F) * 0.25F;
/* 115 */         float z = (this.random.method_43057() * 2.0F - 1.0F) * 0.25F;
/* 116 */         translate(matrices, info, x, 0.0F, z);
/*     */       } 
/*     */       
/* 119 */       event.renderState.field_55310.method_65604(matrices, event.renderCommandQueue, event.light, class_4608.field_21444, event.renderState.field_61821);
/*     */       
/* 121 */       matrices.method_22909();
/*     */       
/* 123 */       float y = Math.max(this.random.method_43057() * 0.0625F, 0.03125F);
/* 124 */       translate(matrices, info, 0.0F, y, 0.0F);
/*     */     } 
/*     */     
/* 127 */     this.skipTransformation = false;
/*     */   }
/*     */   
/*     */   private void translate(class_4587 matrices, ModelInfo info, float x, float y, float z) {
/* 131 */     if (info.flat) {
/* 132 */       float temp = y;
/* 133 */       y = z;
/* 134 */       z = -temp;
/*     */     } 
/*     */     
/* 137 */     matrices.method_46416(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyTransformation(class_4587 matrices, class_804 transform) {
/* 144 */     transform = new class_804(transform.comp_3747(), (Vector3fc)new Vector3f(transform.comp_3748().x(), 0.0F, transform.comp_3748().z()), transform.comp_3749());
/*     */ 
/*     */     
/* 147 */     transform.method_23075(false, matrices.method_23760());
/*     */   }
/*     */   
/*     */   private void offsetInWater(class_4587 matrices, class_1542 entity) {
/* 151 */     if (entity.method_5799()) {
/* 152 */       matrices.method_46416(0.0F, 0.333F, 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   private ModelInfo getInfo(List<class_777> quads) {
/* 157 */     float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
/* 158 */     float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;
/* 159 */     float minZ = Float.MAX_VALUE, maxZ = Float.MIN_VALUE;
/*     */     
/* 161 */     for (class_777 quad : quads) {
/* 162 */       for (int i = 0; i < 4; i++) {
/* 163 */         Vector3fc vec = quad.method_76648(i);
/* 164 */         minY = Math.min(minY, vec.y());
/* 165 */         maxY = Math.max(maxY, vec.y());
/* 166 */         minZ = Math.min(minZ, vec.z());
/* 167 */         maxZ = Math.max(maxZ, vec.z());
/* 168 */         minX = Math.min(minX, vec.x());
/* 169 */         maxX = Math.max(maxX, vec.x());
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     if (minX == Float.MAX_VALUE) minX = 0.0F; 
/* 174 */     if (minY == Float.MAX_VALUE) minY = 0.0F; 
/* 175 */     if (minZ == Float.MAX_VALUE) minZ = 0.0F;
/*     */     
/* 177 */     if (maxX == Float.MIN_VALUE) maxX = 1.0F; 
/* 178 */     if (maxY == Float.MIN_VALUE) maxY = 1.0F; 
/* 179 */     if (maxZ == Float.MIN_VALUE) maxZ = 1.0F;
/*     */     
/* 181 */     float x = maxX - minX;
/* 182 */     float y = maxY - minY;
/* 183 */     float z = maxZ - minZ;
/*     */     
/* 185 */     boolean flat = (x > 0.0625F && y > 0.0625F && z <= 0.0625F);
/*     */     
/* 187 */     return new ModelInfo(flat, 0.5F - minY, -maxZ);
/*     */   }
/*     */   static final class ModelInfo extends Record { private final boolean flat; private final float offsetY; private final float offsetZ;
/* 190 */     ModelInfo(boolean flat, float offsetY, float offsetZ) { this.flat = flat; this.offsetY = offsetY; this.offsetZ = offsetZ; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/systems/modules/render/ItemPhysics$ModelInfo;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #190	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 190 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/modules/render/ItemPhysics$ModelInfo; } public boolean flat() { return this.flat; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/systems/modules/render/ItemPhysics$ModelInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #190	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/modules/render/ItemPhysics$ModelInfo; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/systems/modules/render/ItemPhysics$ModelInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #190	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/systems/modules/render/ItemPhysics$ModelInfo;
/* 190 */       //   0	8	1	o	Ljava/lang/Object; } public float offsetY() { return this.offsetY; } public float offsetZ() { return this.offsetZ; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\ItemPhysics.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */