/*     */ package meteordevelopment.meteorclient.systems.modules.render.blockesp;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2791;
/*     */ import net.minecraft.class_2902;
/*     */ import net.minecraft.class_4076;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ESPChunk
/*     */ {
/*     */   private final int x;
/*     */   private final int z;
/*     */   public Long2ObjectMap<ESPBlock> blocks;
/*     */   
/*     */   public ESPChunk(int x, int z) {
/*  29 */     this.x = x;
/*  30 */     this.z = z;
/*     */   }
/*     */   
/*     */   public ESPBlock get(int x, int y, int z) {
/*  34 */     return (this.blocks == null) ? null : (ESPBlock)this.blocks.get(ESPBlock.getKey(x, y, z));
/*     */   }
/*     */   
/*     */   public void add(class_2338 blockPos, boolean update) {
/*  38 */     ESPBlock block = new ESPBlock(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260());
/*     */     
/*  40 */     if (this.blocks == null) this.blocks = (Long2ObjectMap<ESPBlock>)new Long2ObjectOpenHashMap(64); 
/*  41 */     this.blocks.put(ESPBlock.getKey(blockPos), block);
/*     */     
/*  43 */     if (update) block.update(); 
/*     */   }
/*     */   
/*     */   public void add(class_2338 blockPos) {
/*  47 */     add(blockPos, true);
/*     */   }
/*     */   
/*     */   public void remove(class_2338 blockPos) {
/*  51 */     if (this.blocks != null) {
/*  52 */       ESPBlock block = (ESPBlock)this.blocks.remove(ESPBlock.getKey(blockPos));
/*  53 */       if (block != null) block.group.remove(block); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update() {
/*  58 */     if (this.blocks != null)
/*  59 */       for (ObjectIterator<ESPBlock> objectIterator = this.blocks.values().iterator(); objectIterator.hasNext(); ) { ESPBlock block = objectIterator.next(); block.update(); }
/*     */        
/*     */   }
/*     */   
/*     */   public void update(int x, int y, int z) {
/*  64 */     if (this.blocks != null) {
/*  65 */       ESPBlock block = (ESPBlock)this.blocks.get(ESPBlock.getKey(x, y, z));
/*  66 */       if (block != null) block.update(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/*  71 */     return (this.blocks == null) ? 0 : this.blocks.size();
/*     */   }
/*     */   
/*     */   public boolean shouldBeDeleted() {
/*  75 */     int viewDist = Utils.getRenderDistance() + 1;
/*  76 */     int chunkX = class_4076.method_18675(MeteorClient.mc.field_1724.method_24515().method_10263());
/*  77 */     int chunkZ = class_4076.method_18675(MeteorClient.mc.field_1724.method_24515().method_10260());
/*     */     
/*  79 */     return (this.x > chunkX + viewDist || this.x < chunkX - viewDist || this.z > chunkZ + viewDist || this.z < chunkZ - viewDist);
/*     */   }
/*     */   
/*     */   public void render(Render3DEvent event) {
/*  83 */     if (this.blocks != null) {
/*  84 */       for (ObjectIterator<ESPBlock> objectIterator = this.blocks.values().iterator(); objectIterator.hasNext(); ) { ESPBlock block = objectIterator.next(); block.render(event); }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   public static ESPChunk searchChunk(class_2791 chunk, List<class_2248> blocks) {
/*  90 */     ESPChunk schunk = new ESPChunk((chunk.method_12004()).field_9181, (chunk.method_12004()).field_9180);
/*  91 */     if (schunk.shouldBeDeleted()) return schunk;
/*     */     
/*  93 */     class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */     
/*  95 */     for (int x = chunk.method_12004().method_8326(); x <= chunk.method_12004().method_8327(); x++) {
/*  96 */       for (int z = chunk.method_12004().method_8328(); z <= chunk.method_12004().method_8329(); z++) {
/*  97 */         int height = chunk.method_12032(class_2902.class_2903.field_13202).method_12603(x - chunk.method_12004().method_8326(), z - chunk.method_12004().method_8328());
/*     */         
/*  99 */         for (int y = MeteorClient.mc.field_1687.method_31607(); y < height; y++) {
/* 100 */           blockPos.method_10103(x, y, z);
/* 101 */           class_2680 bs = chunk.method_8320((class_2338)blockPos);
/*     */           
/* 103 */           if (blocks.contains(bs.method_26204())) schunk.add((class_2338)blockPos, false);
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/* 108 */     return schunk;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\blockesp\ESPChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */