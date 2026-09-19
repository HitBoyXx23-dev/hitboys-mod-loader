/*     */ package meteordevelopment.meteorclient.systems.modules.render.blockesp;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.utils.misc.UnorderedArrayList;
/*     */ import meteordevelopment.meteorclient.utils.render.RenderUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_2248;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ESPGroup
/*     */ {
/*  20 */   private static final BlockESP blockEsp = (BlockESP)Modules.get().get(BlockESP.class);
/*     */   
/*     */   private final class_2248 block;
/*     */   
/*  24 */   public final UnorderedArrayList<ESPBlock> blocks = new UnorderedArrayList(); private double sumX;
/*     */   private double sumY;
/*     */   private double sumZ;
/*     */   
/*     */   public ESPGroup(class_2248 block) {
/*  29 */     this.block = block;
/*     */   }
/*     */   
/*     */   public void add(ESPBlock block, boolean removeFromOld, boolean splitGroup) {
/*  33 */     this.blocks.add(block);
/*  34 */     this.sumX += block.x;
/*  35 */     this.sumY += block.y;
/*  36 */     this.sumZ += block.z;
/*     */     
/*  38 */     if (block.group != null && removeFromOld) block.group.remove(block, splitGroup); 
/*  39 */     block.group = this;
/*     */   }
/*     */   
/*     */   public void add(ESPBlock block) {
/*  43 */     add(block, true, true);
/*     */   }
/*     */   
/*     */   public void remove(ESPBlock block, boolean splitGroup) {
/*  47 */     this.blocks.remove(block);
/*  48 */     this.sumX -= block.x;
/*  49 */     this.sumY -= block.y;
/*  50 */     this.sumZ -= block.z;
/*     */     
/*  52 */     if (this.blocks.isEmpty()) { blockEsp.removeGroup(block.group); }
/*  53 */     else if (splitGroup)
/*  54 */     { trySplit(block); }
/*     */   
/*     */   }
/*     */   
/*     */   public void remove(ESPBlock block) {
/*  59 */     remove(block, true);
/*     */   }
/*     */   
/*     */   private void trySplit(ESPBlock block) {
/*  63 */     ObjectOpenHashSet<ESPBlock> objectOpenHashSet = new ObjectOpenHashSet(6);
/*     */     
/*  65 */     for (int side : ESPBlock.SIDES) {
/*  66 */       if ((block.neighbours & side) == side) {
/*  67 */         ESPBlock neighbour = block.getSideBlock(side);
/*  68 */         if (neighbour != null) objectOpenHashSet.add(neighbour); 
/*     */       } 
/*     */     } 
/*  71 */     if (objectOpenHashSet.size() <= 1)
/*     */       return; 
/*  73 */     ObjectOpenHashSet objectOpenHashSet1 = new ObjectOpenHashSet((Collection)this.blocks);
/*  74 */     Queue<ESPBlock> blocksToCheck = new ArrayDeque<>();
/*     */     
/*  76 */     blocksToCheck.offer((ESPBlock)this.blocks.getFirst());
/*  77 */     objectOpenHashSet1.remove(this.blocks.getFirst());
/*  78 */     objectOpenHashSet.remove(this.blocks.getFirst());
/*     */ 
/*     */     
/*  81 */     label61: while (!blocksToCheck.isEmpty()) {
/*  82 */       ESPBlock b = blocksToCheck.poll();
/*     */       
/*  84 */       for (int side : ESPBlock.SIDES) {
/*  85 */         if ((b.neighbours & side) == side) {
/*  86 */           ESPBlock neighbour = b.getSideBlock(side);
/*     */           
/*  88 */           if (neighbour != null && objectOpenHashSet1.contains(neighbour)) {
/*  89 */             blocksToCheck.offer(neighbour);
/*  90 */             objectOpenHashSet1.remove(neighbour);
/*     */             
/*  92 */             objectOpenHashSet.remove(neighbour);
/*  93 */             if (objectOpenHashSet.isEmpty())
/*     */               break label61; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  99 */     if (!objectOpenHashSet.isEmpty()) {
/* 100 */       ESPGroup group = blockEsp.newGroup(this.block);
/* 101 */       group.blocks.ensureCapacity(objectOpenHashSet1.size());
/*     */       
/* 103 */       Objects.requireNonNull(objectOpenHashSet1); this.blocks.removeIf(objectOpenHashSet1::contains);
/*     */       
/* 105 */       for (ESPBlock b : objectOpenHashSet1) {
/* 106 */         group.add(b, false, false);
/*     */         
/* 108 */         this.sumX -= b.x;
/* 109 */         this.sumY -= b.y;
/* 110 */         this.sumZ -= b.z;
/*     */       } 
/*     */       
/* 113 */       if (objectOpenHashSet.size() > 1) {
/* 114 */         block.neighbours = 0;
/*     */         
/* 116 */         for (ESPBlock b : objectOpenHashSet) {
/* 117 */           int x = b.x - block.x;
/* 118 */           if (x == 1) { block.neighbours |= 0x8; }
/* 119 */           else if (x == -1) { block.neighbours |= 0x80; }
/*     */           
/* 121 */           int y = b.y - block.y;
/* 122 */           if (y == 1) { block.neighbours |= 0x200; }
/* 123 */           else if (y == -1) { block.neighbours |= 0x4000; }
/*     */           
/* 125 */           int z = b.z - block.z;
/* 126 */           if (z == 1) { block.neighbours |= 0x2; continue; }
/* 127 */            if (z == -1) block.neighbours |= 0x20;
/*     */         
/*     */         } 
/* 130 */         group.trySplit(block);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void merge(ESPGroup group) {
/* 136 */     this.blocks.ensureCapacity(this.blocks.size() + group.blocks.size());
/* 137 */     for (ESPBlock block : group.blocks) add(block, false, false); 
/* 138 */     blockEsp.removeGroup(group);
/*     */   }
/*     */   
/*     */   public void render(Render3DEvent event) {
/* 142 */     ESPBlockData blockData = blockEsp.getBlockData(this.block);
/*     */     
/* 144 */     if (blockData.tracer)
/* 145 */       event.renderer.line(RenderUtils.center.field_1352, RenderUtils.center.field_1351, RenderUtils.center.field_1350, this.sumX / this.blocks.size() + 0.5D, this.sumY / this.blocks.size() + 0.5D, this.sumZ / this.blocks.size() + 0.5D, (Color)blockData.tracerColor); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\blockesp\ESPGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */