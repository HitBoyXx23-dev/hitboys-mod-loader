/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Base64;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2499;
/*     */ import net.minecraft.class_2505;
/*     */ import net.minecraft.class_2507;
/*     */ import net.minecraft.class_2519;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_473;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_473.class})
/*     */ public abstract class BookEditScreenMixin
/*     */   extends class_437
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   private List<String> field_17116;
/*     */   @Shadow
/*     */   private int field_2840;
/*     */   
/*     */   public BookEditScreenMixin(class_2561 title) {
/*  47 */     super(title);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_25426"}, at = {@At("TAIL")})
/*     */   private void onInit(CallbackInfo info) {
/*  52 */     method_37063((class_364)(new class_4185.class_7840(
/*  53 */           (class_2561)class_2561.method_43470("Copy"), button -> {
/*     */             class_2499 listTag = new class_2499();
/*     */             
/*     */             Objects.requireNonNull(listTag);
/*     */             this.field_17116.stream().map(class_2519::method_23256).forEach(listTag::add);
/*     */             class_2487 tag = new class_2487();
/*     */             tag.method_10566("pages", (class_2520)listTag);
/*     */             tag.method_10569("currentPage", this.field_2840);
/*     */             FastByteArrayOutputStream bytes = new FastByteArrayOutputStream();
/*     */             DataOutputStream out = new DataOutputStream((OutputStream)bytes);
/*     */             try {
/*     */               class_2507.method_55324((class_2520)tag, out);
/*  65 */             } catch (IOException e) {
/*     */               MeteorClient.LOG.error("Error writing the book to the output stream", e);
/*     */             } 
/*     */             
/*     */             try {
/*     */               GLFW.glfwSetClipboardString(MeteorClient.mc.method_22683().method_4490(), Base64.getEncoder().encodeToString(bytes.array));
/*  71 */             } catch (OutOfMemoryError exception) {
/*     */               
/*     */               GLFW.glfwSetClipboardString(MeteorClient.mc.method_22683().method_4490(), exception.toString());
/*     */             } 
/*  75 */           })).method_46433(4, 4)
/*  76 */         .method_46437(120, 20)
/*  77 */         .method_46431());
/*     */ 
/*     */     
/*  80 */     method_37063((class_364)(new class_4185.class_7840(
/*  81 */           (class_2561)class_2561.method_43470("Paste"), button -> {
/*     */             byte[] bytes;
/*     */             String clipboard = GLFW.glfwGetClipboardString(MeteorClient.mc.method_22683().method_4490());
/*     */             if (clipboard == null)
/*     */               return; 
/*     */             try {
/*     */               bytes = Base64.getDecoder().decode(clipboard);
/*  88 */             } catch (IllegalArgumentException ignored) {
/*     */               return;
/*     */             } 
/*     */             
/*     */             DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
/*     */             
/*     */             try {
/*     */               class_2487 tag = class_2507.method_10629(in, class_2505.method_53898());
/*     */               
/*     */               class_2499 listTag = tag.method_68569("pages").method_10612();
/*     */               
/*     */               this.field_17116.clear();
/*     */               
/*     */               for (int i = 0; i < listTag.size(); i++) {
/*     */                 this.field_17116.add(listTag.method_68577(i, ""));
/*     */               }
/*     */               
/*     */               if (this.field_17116.isEmpty()) {
/*     */                 this.field_17116.add("");
/*     */               }
/*     */               this.field_2840 = tag.method_68083("currentPage", 0);
/*     */               method_71537();
/* 110 */             } catch (IOException e) {
/*     */               
/*     */               MeteorClient.LOG.error("Error reading the data from your clipboard", e);
/*     */             } 
/* 114 */           })).method_46433(4, 26)
/* 115 */         .method_46437(120, 20)
/* 116 */         .method_46431());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
/* 122 */     if (verticalAmount == 0.0D) return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
/*     */     
/* 124 */     if (verticalAmount < 0.0D) { method_2444(); }
/* 125 */     else { method_2437(); }
/* 126 */      return true;
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_71537();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_2444();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_2437();
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BookEditScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */