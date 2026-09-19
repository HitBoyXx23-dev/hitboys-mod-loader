/*    */ package meteordevelopment.meteorclient.utils.world;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.mixin.ClientChunkManagerAccessor;
/*    */ import meteordevelopment.meteorclient.mixin.ClientChunkMapAccessor;
/*    */ import net.minecraft.class_2791;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkIterator
/*    */   implements Iterator<class_2791>
/*    */ {
/* 17 */   private final ClientChunkMapAccessor map = (ClientChunkMapAccessor)((ClientChunkManagerAccessor)MeteorClient.mc.field_1687.method_2935()).meteor$getChunks();
/*    */   
/*    */   private final boolean onlyWithLoadedNeighbours;
/* 20 */   private int i = 0;
/*    */   private class_2791 chunk;
/*    */   
/*    */   public ChunkIterator(boolean onlyWithLoadedNeighbours) {
/* 24 */     this.onlyWithLoadedNeighbours = onlyWithLoadedNeighbours;
/*    */     
/* 26 */     getNext();
/*    */   }
/*    */   
/*    */   private class_2791 getNext() {
/* 30 */     class_2791 prev = this.chunk;
/* 31 */     this.chunk = null;
/*    */     
/* 33 */     while (this.i < this.map.meteor$getChunks().length()) {
/* 34 */       this.chunk = this.map.meteor$getChunks().get(this.i++);
/* 35 */       if (this.chunk != null && (!this.onlyWithLoadedNeighbours || isInRadius(this.chunk)))
/*    */         break; 
/*    */     } 
/* 38 */     return prev;
/*    */   }
/*    */   
/*    */   private boolean isInRadius(class_2791 chunk) {
/* 42 */     int x = (chunk.method_12004()).field_9181;
/* 43 */     int z = (chunk.method_12004()).field_9180;
/*    */     
/* 45 */     return (MeteorClient.mc.field_1687.method_2935().method_12123(x + 1, z) && MeteorClient.mc.field_1687.method_2935().method_12123(x - 1, z) && MeteorClient.mc.field_1687.method_2935().method_12123(x, z + 1) && MeteorClient.mc.field_1687.method_2935().method_12123(x, z - 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 50 */     return (this.chunk != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2791 next() {
/* 55 */     return getNext();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\world\ChunkIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */