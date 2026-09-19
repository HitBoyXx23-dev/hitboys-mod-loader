/*    */ package meteordevelopment.meteorclient.systems.modules;
/*    */ 
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1802;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Category
/*    */ {
/*    */   public final String name;
/*    */   public final class_1799 icon;
/*    */   private final int nameHash;
/*    */   
/*    */   public Category(String name, class_1799 icon) {
/* 17 */     this.name = name;
/* 18 */     this.nameHash = name.hashCode();
/* 19 */     this.icon = (icon == null) ? class_1802.field_8162.method_7854() : icon;
/*    */   }
/*    */   public Category(String name) {
/* 22 */     this(name, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 27 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 32 */     if (this == o) return true; 
/* 33 */     if (o == null || getClass() != o.getClass()) return false; 
/* 34 */     Category category = (Category)o;
/* 35 */     return (this.nameHash == category.nameHash);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 40 */     return this.nameHash;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\Category.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */