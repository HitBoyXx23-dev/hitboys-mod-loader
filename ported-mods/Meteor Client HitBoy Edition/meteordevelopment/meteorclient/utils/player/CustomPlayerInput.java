/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import net.minecraft.class_10185;
/*     */ import net.minecraft.class_241;
/*     */ import net.minecraft.class_744;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomPlayerInput
/*     */   extends class_744
/*     */ {
/*     */   public void method_3129() {
/*  15 */     float f = (this.field_54155.comp_3159() == this.field_54155.comp_3160()) ? 0.0F : (this.field_54155.comp_3159() ? 1.0F : -1.0F);
/*  16 */     float g = (this.field_54155.comp_3161() == this.field_54155.comp_3162()) ? 0.0F : (this.field_54155.comp_3161() ? 1.0F : -1.0F);
/*  17 */     this.field_55868 = (new class_241(g, f)).method_35581();
/*     */   }
/*     */   
/*     */   public void stop() {
/*  21 */     this.field_54155 = class_10185.field_54098;
/*     */   }
/*     */   
/*     */   public void forward(boolean bool) {
/*  25 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  32 */       .field_54155 = new class_10185(bool, this.field_54155.comp_3160(), this.field_54155.comp_3161(), this.field_54155.comp_3162(), this.field_54155.comp_3163(), this.field_54155.comp_3164(), this.field_54155.comp_3165());
/*     */   }
/*     */ 
/*     */   
/*     */   public void backward(boolean bool) {
/*  37 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  44 */       .field_54155 = new class_10185(this.field_54155.comp_3159(), bool, this.field_54155.comp_3161(), this.field_54155.comp_3162(), this.field_54155.comp_3163(), this.field_54155.comp_3164(), this.field_54155.comp_3165());
/*     */   }
/*     */ 
/*     */   
/*     */   public void left(boolean bool) {
/*  49 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  56 */       .field_54155 = new class_10185(this.field_54155.comp_3159(), this.field_54155.comp_3160(), bool, this.field_54155.comp_3162(), this.field_54155.comp_3163(), this.field_54155.comp_3164(), this.field_54155.comp_3165());
/*     */   }
/*     */ 
/*     */   
/*     */   public void right(boolean bool) {
/*  61 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  68 */       .field_54155 = new class_10185(this.field_54155.comp_3159(), this.field_54155.comp_3160(), this.field_54155.comp_3161(), bool, this.field_54155.comp_3163(), this.field_54155.comp_3164(), this.field_54155.comp_3165());
/*     */   }
/*     */ 
/*     */   
/*     */   public void jump(boolean bool) {
/*  73 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       .field_54155 = new class_10185(this.field_54155.comp_3159(), this.field_54155.comp_3160(), this.field_54155.comp_3161(), this.field_54155.comp_3162(), bool, this.field_54155.comp_3164(), this.field_54155.comp_3165());
/*     */   }
/*     */ 
/*     */   
/*     */   public void sneak(boolean bool) {
/*  85 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       .field_54155 = new class_10185(this.field_54155.comp_3159(), this.field_54155.comp_3160(), this.field_54155.comp_3161(), this.field_54155.comp_3162(), this.field_54155.comp_3163(), bool, this.field_54155.comp_3165());
/*     */   }
/*     */ 
/*     */   
/*     */   public void sprint(boolean bool) {
/*  97 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       .field_54155 = new class_10185(this.field_54155.comp_3159(), this.field_54155.comp_3160(), this.field_54155.comp_3161(), this.field_54155.comp_3162(), this.field_54155.comp_3163(), this.field_54155.comp_3164(), bool);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\CustomPlayerInput.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */