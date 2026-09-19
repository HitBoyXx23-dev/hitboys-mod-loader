/*     */ package meteordevelopment.meteorclient.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.pipeline.BlendFunction;
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.platform.DepthTestFunction;
/*     */ import com.mojang.blaze3d.shaders.ShaderType;
/*     */ import com.mojang.blaze3d.systems.GpuDevice;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import net.minecraft.class_10789;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3298;
/*     */ import net.minecraft.class_3300;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ public abstract class MeteorRenderPipelines
/*     */ {
/*  27 */   private static final List<RenderPipeline> PIPELINES = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final RenderPipeline.Snippet MESH_UNIFORMS = RenderPipeline.builder(new RenderPipeline.Snippet[0])
/*  32 */     .withUniform("MeshData", class_10789.field_60031)
/*  33 */     .buildSnippet();
/*     */ 
/*     */ 
/*     */   
/*  37 */   public static final RenderPipeline WORLD_COLORED = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/*  38 */         })).withLocation(MeteorClient.identifier("pipeline/world_colored"))
/*  39 */       .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_27379)
/*  40 */       .withVertexShader(MeteorClient.identifier("shaders/pos_color.vert"))
/*  41 */       .withFragmentShader(MeteorClient.identifier("shaders/pos_color.frag"))
/*  42 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/*  43 */       .withDepthWrite(false)
/*  44 */       .withBlend(BlendFunction.TRANSLUCENT)
/*  45 */       .withCull(false)
/*  46 */       .build());
/*     */ 
/*     */   
/*  49 */   public static final RenderPipeline WORLD_COLORED_LINES = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/*  50 */         })).withLineSmooth()
/*  51 */       .withLocation(MeteorClient.identifier("pipeline/world_colored_lines"))
/*  52 */       .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_29344)
/*  53 */       .withVertexShader(MeteorClient.identifier("shaders/pos_color.vert"))
/*  54 */       .withFragmentShader(MeteorClient.identifier("shaders/pos_color.frag"))
/*  55 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/*  56 */       .withDepthWrite(false)
/*  57 */       .withBlend(BlendFunction.TRANSLUCENT)
/*  58 */       .withCull(false)
/*  59 */       .build());
/*     */ 
/*     */   
/*  62 */   public static final RenderPipeline WORLD_COLORED_DEPTH = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/*  63 */         })).withLocation(MeteorClient.identifier("pipeline/world_colored_depth"))
/*  64 */       .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_27379)
/*  65 */       .withVertexShader(MeteorClient.identifier("shaders/pos_color.vert"))
/*  66 */       .withFragmentShader(MeteorClient.identifier("shaders/pos_color.frag"))
/*  67 */       .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/*  68 */       .withDepthWrite(false)
/*  69 */       .withBlend(BlendFunction.TRANSLUCENT)
/*  70 */       .withCull(false)
/*  71 */       .build());
/*     */ 
/*     */   
/*  74 */   public static final RenderPipeline WORLD_COLORED_LINES_DEPTH = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/*  75 */         })).withLineSmooth()
/*  76 */       .withLocation(MeteorClient.identifier("pipeline/world_colored_lines_depth"))
/*  77 */       .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_29344)
/*  78 */       .withVertexShader(MeteorClient.identifier("shaders/pos_color.vert"))
/*  79 */       .withFragmentShader(MeteorClient.identifier("shaders/pos_color.frag"))
/*  80 */       .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/*  81 */       .withDepthWrite(false)
/*  82 */       .withBlend(BlendFunction.TRANSLUCENT)
/*  83 */       .withCull(false)
/*  84 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final RenderPipeline UI_COLORED = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/*  90 */         })).withLocation(MeteorClient.identifier("pipeline/ui_colored"))
/*  91 */       .withVertexFormat(MeteorVertexFormats.POS2_COLOR, VertexFormat.class_5596.field_27379)
/*  92 */       .withVertexShader(MeteorClient.identifier("shaders/pos_color.vert"))
/*  93 */       .withFragmentShader(MeteorClient.identifier("shaders/pos_color.frag"))
/*  94 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/*  95 */       .withDepthWrite(false)
/*  96 */       .withBlend(BlendFunction.TRANSLUCENT)
/*  97 */       .withCull(true)
/*  98 */       .build());
/*     */ 
/*     */   
/* 101 */   public static final RenderPipeline UI_COLORED_LINES = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/* 102 */         })).withLocation(MeteorClient.identifier("pipeline/ui_colored_lines"))
/* 103 */       .withVertexFormat(MeteorVertexFormats.POS2_COLOR, VertexFormat.class_5596.field_29344)
/* 104 */       .withVertexShader(MeteorClient.identifier("shaders/pos_color.vert"))
/* 105 */       .withFragmentShader(MeteorClient.identifier("shaders/pos_color.frag"))
/* 106 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 107 */       .withDepthWrite(false)
/* 108 */       .withBlend(BlendFunction.TRANSLUCENT)
/* 109 */       .withCull(true)
/* 110 */       .build());
/*     */ 
/*     */   
/* 113 */   public static final RenderPipeline UI_TEXTURED = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/* 114 */         })).withLocation(MeteorClient.identifier("pipeline/ui_textured"))
/* 115 */       .withVertexFormat(MeteorVertexFormats.POS2_TEXTURE_COLOR, VertexFormat.class_5596.field_27379)
/* 116 */       .withVertexShader(MeteorClient.identifier("shaders/pos_tex_color.vert"))
/* 117 */       .withFragmentShader(MeteorClient.identifier("shaders/pos_tex_color.frag"))
/* 118 */       .withSampler("u_Texture")
/* 119 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 120 */       .withDepthWrite(false)
/* 121 */       .withBlend(BlendFunction.TRANSLUCENT)
/* 122 */       .withCull(true)
/* 123 */       .build());
/*     */ 
/*     */   
/* 126 */   public static final RenderPipeline UI_TEXT = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/* 127 */         })).withLocation(MeteorClient.identifier("pipeline/ui_text"))
/* 128 */       .withVertexFormat(MeteorVertexFormats.POS2_TEXTURE_COLOR, VertexFormat.class_5596.field_27379)
/* 129 */       .withVertexShader(MeteorClient.identifier("shaders/text.vert"))
/* 130 */       .withFragmentShader(MeteorClient.identifier("shaders/text.frag"))
/* 131 */       .withSampler("u_Texture")
/* 132 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 133 */       .withDepthWrite(false)
/* 134 */       .withBlend(BlendFunction.TRANSLUCENT)
/* 135 */       .withCull(true)
/* 136 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public static final RenderPipeline POST_OUTLINE = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[0]))
/* 142 */       .withLocation(MeteorClient.identifier("pipeline/post/outline"))
/* 143 */       .withVertexFormat(MeteorVertexFormats.POS2, VertexFormat.class_5596.field_27379)
/* 144 */       .withVertexShader(MeteorClient.identifier("shaders/post-process/base.vert"))
/* 145 */       .withFragmentShader(MeteorClient.identifier("shaders/post-process/outline.frag"))
/* 146 */       .withSampler("u_Texture")
/* 147 */       .withUniform("PostData", class_10789.field_60031)
/* 148 */       .withUniform("OutlineData", class_10789.field_60031)
/* 149 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 150 */       .withDepthWrite(false)
/* 151 */       .withBlend(BlendFunction.TRANSLUCENT)
/* 152 */       .withCull(false)
/* 153 */       .build());
/*     */ 
/*     */   
/* 156 */   public static final RenderPipeline POST_IMAGE = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/* 157 */         })).withLocation(MeteorClient.identifier("pipeline/post/image"))
/* 158 */       .withVertexFormat(MeteorVertexFormats.POS2, VertexFormat.class_5596.field_27379)
/* 159 */       .withVertexShader(MeteorClient.identifier("shaders/post-process/base.vert"))
/* 160 */       .withFragmentShader(MeteorClient.identifier("shaders/post-process/image.frag"))
/* 161 */       .withSampler("u_Texture")
/* 162 */       .withSampler("u_TextureI")
/* 163 */       .withUniform("PostData", class_10789.field_60031)
/* 164 */       .withUniform("ImageData", class_10789.field_60031)
/* 165 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 166 */       .withDepthWrite(false)
/* 167 */       .withBlend(BlendFunction.TRANSLUCENT)
/* 168 */       .withCull(false)
/* 169 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public static final RenderPipeline BLUR_DOWN = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/* 175 */         })).withLocation(MeteorClient.identifier("pipeline/blur/down"))
/* 176 */       .withVertexFormat(MeteorVertexFormats.POS2, VertexFormat.class_5596.field_27379)
/* 177 */       .withVertexShader(MeteorClient.identifier("shaders/blur.vert"))
/* 178 */       .withFragmentShader(MeteorClient.identifier("shaders/blur_down.frag"))
/* 179 */       .withSampler("u_Texture")
/* 180 */       .withUniform("BlurData", class_10789.field_60031)
/* 181 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 182 */       .withDepthWrite(false)
/* 183 */       .withBlend(BlendFunction.TRANSLUCENT)
/* 184 */       .withCull(false)
/* 185 */       .build());
/*     */ 
/*     */   
/* 188 */   public static final RenderPipeline BLUR_UP = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/* 189 */         })).withLocation(MeteorClient.identifier("pipeline/blur/up"))
/* 190 */       .withVertexFormat(MeteorVertexFormats.POS2, VertexFormat.class_5596.field_27379)
/* 191 */       .withVertexShader(MeteorClient.identifier("shaders/blur.vert"))
/* 192 */       .withFragmentShader(MeteorClient.identifier("shaders/blur_up.frag"))
/* 193 */       .withSampler("u_Texture")
/* 194 */       .withUniform("BlurData", class_10789.field_60031)
/* 195 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 196 */       .withDepthWrite(false)
/* 197 */       .withBlend(BlendFunction.TRANSLUCENT)
/* 198 */       .withCull(false)
/* 199 */       .build());
/*     */ 
/*     */   
/* 202 */   public static final RenderPipeline BLUR_PASSTHROUGH = add((new ExtendedRenderPipelineBuilder(new RenderPipeline.Snippet[] { MESH_UNIFORMS
/* 203 */         })).withLocation(MeteorClient.identifier("pipeline/blur/up"))
/* 204 */       .withVertexFormat(MeteorVertexFormats.POS2, VertexFormat.class_5596.field_27379)
/* 205 */       .withVertexShader(MeteorClient.identifier("shaders/passthrough.vert"))
/* 206 */       .withFragmentShader(MeteorClient.identifier("shaders/passthrough.frag"))
/* 207 */       .withSampler("u_Texture")
/* 208 */       .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 209 */       .withDepthWrite(false)
/* 210 */       .withBlend(BlendFunction.TRANSLUCENT)
/* 211 */       .withCull(false)
/* 212 */       .build());
/*     */ 
/*     */   
/*     */   private static RenderPipeline add(RenderPipeline pipeline) {
/* 216 */     PIPELINES.add(pipeline);
/* 217 */     return pipeline;
/*     */   }
/*     */   
/*     */   public static void precompile() {
/* 221 */     GpuDevice device = RenderSystem.getDevice();
/* 222 */     class_3300 resources = class_310.method_1551().method_1478();
/*     */     
/* 224 */     for (RenderPipeline pipeline : PIPELINES) {
/* 225 */       device.precompilePipeline(pipeline, (identifier, shaderType) -> { class_3298 resource = resources.method_14486(identifier).get(); try { InputStream in = resource.method_14482(); 
/*     */               try { String str = IOUtils.toString(in, StandardCharsets.UTF_8); if (in != null)
/*     */                   in.close();  return str; }
/* 228 */               catch (Throwable throwable) { if (in != null) try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */                }
/* 230 */             catch (IOException e)
/*     */             { throw new RuntimeException(e); }
/*     */           
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\MeteorRenderPipelines.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */