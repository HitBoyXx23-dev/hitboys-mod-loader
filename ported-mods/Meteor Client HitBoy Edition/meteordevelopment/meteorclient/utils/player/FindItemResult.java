/*    */ package meteordevelopment.meteorclient.utils.player;
/*    */ public final class FindItemResult extends Record { private final int slot; private final int count;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/player/FindItemResult;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/player/FindItemResult;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/player/FindItemResult;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/player/FindItemResult;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/player/FindItemResult;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/player/FindItemResult;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/* 16 */   public FindItemResult(int slot, int count) { this.slot = slot; this.count = count; } public int slot() { return this.slot; } public int count() { return this.count; }
/*    */    public boolean found() {
/* 18 */     return (this.slot != -1);
/*    */   }
/*    */   
/*    */   public class_1268 getHand() {
/* 22 */     if (this.slot == 40) return class_1268.field_5810; 
/* 23 */     if (this.slot == MeteorClient.mc.field_1724.method_31548().method_67532()) return class_1268.field_5808; 
/* 24 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isMainHand() {
/* 28 */     return (getHand() == class_1268.field_5808);
/*    */   }
/*    */   
/*    */   public boolean isOffhand() {
/* 32 */     return (getHand() == class_1268.field_5810);
/*    */   }
/*    */   
/*    */   public boolean isHotbar() {
/* 36 */     return (this.slot >= 0 && this.slot <= 8);
/*    */   }
/*    */   
/*    */   public boolean isMain() {
/* 40 */     return (this.slot >= 9 && this.slot <= 35);
/*    */   }
/*    */   
/*    */   public boolean isArmor() {
/* 44 */     return (this.slot >= 36 && this.slot <= 39);
/*    */   } }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\FindItemResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */