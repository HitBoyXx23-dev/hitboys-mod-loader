/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Base64;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*     */ import meteordevelopment.meteorclient.gui.screens.EditBookTitleAndAuthorScreen;
/*     */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2499;
/*     */ import net.minecraft.class_2507;
/*     */ import net.minecraft.class_2519;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_3872;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.lwjgl.system.MemoryUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_3872.class})
/*     */ public abstract class BookScreenMixin
/*     */   extends class_437
/*     */ {
/*     */   @Shadow
/*     */   private class_3872.class_3931 field_17418;
/*     */   @Shadow
/*     */   private int field_17119;
/*     */   
/*     */   public BookScreenMixin(class_2561 title) {
/*  54 */     super(title);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_25426"}, at = {@At("TAIL")})
/*     */   private void onInit(CallbackInfo info) {
/*  59 */     method_37063((class_364)(new class_4185.class_7840(
/*  60 */           (class_2561)class_2561.method_43470("Copy"), button -> {
/*     */             class_2499 listTag = new class_2499();
/*     */             for (int i = 0; i < this.field_17418.method_17560(); i++) {
/*     */               listTag.add(class_2519.method_23256(this.field_17418.method_17563(i).getString()));
/*     */             }
/*     */             class_2487 tag = new class_2487();
/*     */             tag.method_10566("pages", (class_2520)listTag);
/*     */             tag.method_10569("currentPage", this.field_17119);
/*     */             FastByteArrayOutputStream bytes = new FastByteArrayOutputStream();
/*     */             DataOutputStream out = new DataOutputStream((OutputStream)bytes);
/*     */             try {
/*     */               class_2507.method_55324((class_2520)tag, out);
/*  72 */             } catch (IOException e) {
/*     */               MeteorClient.LOG.error("Error writing the book to the output stream", e);
/*     */             } 
/*     */ 
/*     */             
/*     */             String encoded = Base64.getEncoder().encodeToString(bytes.array);
/*     */             
/*     */             long available = MemoryStack.stackGet().getPointer();
/*     */             
/*     */             long size = MemoryUtil.memLengthUTF8(encoded, true);
/*     */             
/*     */             if (size > available) {
/*     */               ChatUtils.error("Could not copy to clipboard: Out of memory.", new Object[0]);
/*     */             } else {
/*     */               GLFW.glfwSetClipboardString(MeteorClient.mc.method_22683().method_4490(), encoded);
/*     */             } 
/*  88 */           })).method_46433(4, 4)
/*  89 */         .method_46437(120, 20)
/*  90 */         .method_46431());
/*     */ 
/*     */ 
/*     */     
/*  94 */     class_1799 itemStack = MeteorClient.mc.field_1724.method_6047();
/*  95 */     class_1268 hand = class_1268.field_5808;
/*     */     
/*  97 */     if (itemStack.method_7909() != class_1802.field_8360) {
/*  98 */       itemStack = MeteorClient.mc.field_1724.method_6079();
/*  99 */       hand = class_1268.field_5810;
/*     */     } 
/* 101 */     if (itemStack.method_7909() != class_1802.field_8360)
/*     */       return; 
/* 103 */     class_1799 book = itemStack;
/* 104 */     class_1268 hand2 = hand;
/*     */     
/* 106 */     method_37063((class_364)(new class_4185.class_7840(
/* 107 */           (class_2561)class_2561.method_43470("Edit title & author"), button -> MeteorClient.mc.method_1507((class_437)new EditBookTitleAndAuthorScreen(GuiThemes.get(), book, hand2))))
/*     */ 
/*     */         
/* 110 */         .method_46433(4, 26)
/* 111 */         .method_46437(120, 20)
/* 112 */         .method_46431());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
/* 118 */     if (verticalAmount == 0.0D) return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
/*     */     
/* 120 */     if (verticalAmount < 0.0D) { method_17058(); }
/* 121 */     else { method_17057(); }
/* 122 */      return true;
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_17058();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_17057();
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BookScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */