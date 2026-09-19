/*    */ package meteordevelopment.meteorclient.utils.world;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import meteordevelopment.meteorclient.mixin.ChunkAccessor;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2586;
/*    */ import net.minecraft.class_2791;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockEntityIterator
/*    */   implements Iterator<class_2586>
/*    */ {
/*    */   private final Iterator<class_2791> chunks;
/*    */   private Iterator<class_2586> blockEntities;
/*    */   
/*    */   public BlockEntityIterator() {
/* 21 */     this.chunks = new ChunkIterator(false);
/*    */     
/* 23 */     nextChunk();
/*    */   }
/*    */ 
/*    */   
/*    */   private void nextChunk() {
/* 28 */     while (this.chunks.hasNext()) {
/*    */       
/* 30 */       Map<class_2338, class_2586> blockEntityMap = ((ChunkAccessor)this.chunks.next()).getBlockEntities();
/*    */       
/* 32 */       if (!blockEntityMap.isEmpty()) {
/* 33 */         this.blockEntities = blockEntityMap.values().iterator();
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 41 */     if (this.blockEntities == null) return false; 
/* 42 */     if (this.blockEntities.hasNext()) return true;
/*    */     
/* 44 */     nextChunk();
/*    */     
/* 46 */     return this.blockEntities.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2586 next() {
/* 51 */     return this.blockEntities.next();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\world\BlockEntityIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */