/*    */ package meteordevelopment.meteorclient.utils.render;
/*    */ 
/*    */ import net.minecraft.class_1113;
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2583;
/*    */ import net.minecraft.class_5251;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/*    */   @NotNull
/*    */   private final class_2561 title;
/*    */   @Nullable
/*    */   private class_2561 text;
/*    */   @Nullable
/*    */   private class_1799 icon;
/*    */   @Nullable
/* 59 */   private class_1113 customSound = MeteorToast.DEFAULT_SOUND;
/* 60 */   private long duration = 6000L;
/*    */   
/*    */   public Builder(@NotNull String title) {
/* 63 */     this.title = (class_2561)class_2561.method_43470(title).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717(MeteorToast.TITLE_COLOR)));
/*    */   }
/*    */   
/*    */   public Builder text(@Nullable String text) {
/* 67 */     this
/*    */       
/* 69 */       .text = (text != null && !text.trim().isEmpty()) ? (class_2561)class_2561.method_43470(text).method_10862(class_2583.field_24360.method_27703(class_5251.method_27717(MeteorToast.TEXT_COLOR))) : null;
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   public Builder icon(@Nullable class_1792 item) {
/* 74 */     this.icon = (item != null) ? item.method_7854() : null;
/* 75 */     return this;
/*    */   }
/*    */   
/*    */   public Builder sound(@Nullable class_1113 sound) {
/* 79 */     this.customSound = sound;
/* 80 */     return this;
/*    */   }
/*    */   
/*    */   public Builder duration(long duration) {
/* 84 */     this.duration = Math.max(0L, duration);
/* 85 */     return this;
/*    */   }
/*    */   
/*    */   public MeteorToast build() {
/* 89 */     return new MeteorToast(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\MeteorToast$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */