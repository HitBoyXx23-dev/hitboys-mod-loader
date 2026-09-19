/*    */ package meteordevelopment.meteorclient.systems.accounts;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UuidToProfileResponse
/*    */ {
/*    */   public Property[] properties;
/*    */   
/*    */   public String getPropertyValue(String name) {
/* 12 */     for (Property property : this.properties) {
/* 13 */       if (property.name.equals(name)) return property.value;
/*    */     
/*    */     } 
/* 16 */     return null;
/*    */   }
/*    */   
/*    */   public static class Property {
/*    */     public String name;
/*    */     public String value;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\accounts\UuidToProfileResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */