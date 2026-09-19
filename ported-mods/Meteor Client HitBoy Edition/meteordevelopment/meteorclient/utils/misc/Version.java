/*    */ package meteordevelopment.meteorclient.utils.misc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Version
/*    */ {
/*    */   private final String string;
/*    */   private final int[] numbers;
/*    */   
/*    */   public Version(String string) {
/* 13 */     this.string = string;
/* 14 */     this.numbers = new int[3];
/*    */     
/* 16 */     String[] split = string.split("\\.");
/* 17 */     if (split.length != 3) throw new IllegalArgumentException("Version string needs to have 3 numbers.");
/*    */     
/* 19 */     for (int i = 0; i < 3; i++) {
/*    */       try {
/* 21 */         this.numbers[i] = Integer.parseInt(split[i]);
/* 22 */       } catch (NumberFormatException e) {
/* 23 */         throw new IllegalArgumentException("Failed to parse version string.");
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isZero() {
/* 29 */     return (this.numbers[0] == 0 && this.numbers[1] == 0 && this.numbers[2] == 0);
/*    */   }
/*    */   
/*    */   public boolean isHigherThan(Version version) {
/* 33 */     for (int i = 0; i < 3; i++) {
/* 34 */       if (this.numbers[i] > version.numbers[i]) return true; 
/* 35 */       if (this.numbers[i] < version.numbers[i]) return false;
/*    */     
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 43 */     return this.string;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\Version.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */