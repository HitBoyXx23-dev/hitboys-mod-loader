/*     */ package meteordevelopment.meteorclient.systems.modules.render.blockesp;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.events.world.BlockUpdateEvent;
/*     */ import meteordevelopment.meteorclient.events.world.ChunkDataEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BlockDataSetting;
/*     */ import meteordevelopment.meteorclient.settings.BlockListSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.GenericSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.IGetter;
/*     */ import meteordevelopment.meteorclient.utils.render.color.RainbowColors;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2791;
/*     */ import net.minecraft.class_2874;
/*     */ 
/*     */ public class BlockESP extends Module {
/*  37 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */ 
/*     */ 
/*     */   
/*  41 */   private final Setting<List<class_2248>> blocks = this.sgGeneral.add((Setting)((BlockListSetting.Builder)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder())
/*  42 */       .name("blocks"))
/*  43 */       .description("Blocks to search for."))
/*  44 */       .onChanged(blocks1 -> {
/*     */           if (isActive() && Utils.canUpdate())
/*     */             onActivate(); 
/*  47 */         })).build());
/*     */ 
/*     */   
/*  50 */   private final Setting<ESPBlockData> defaultBlockConfig = this.sgGeneral.add((Setting)((GenericSetting.Builder)((GenericSetting.Builder)((GenericSetting.Builder)(new GenericSetting.Builder())
/*  51 */       .name("default-block-config"))
/*  52 */       .description("Default block config."))
/*  53 */       .defaultValue(new ESPBlockData(ShapeMode.Lines, new SettingColor(0, 255, 200), new SettingColor(0, 255, 200, 25), true, new SettingColor(0, 255, 200, 125))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  62 */       .build());
/*     */ 
/*     */   
/*  65 */   private final Setting<Map<class_2248, ESPBlockData>> blockConfigs = this.sgGeneral.add((Setting)((BlockDataSetting.Builder)((BlockDataSetting.Builder)(new BlockDataSetting.Builder())
/*  66 */       .name("block-configs"))
/*  67 */       .description("Config for each block."))
/*  68 */       .defaultData((IGetter)this.defaultBlockConfig)
/*  69 */       .build());
/*     */ 
/*     */   
/*  72 */   private final Setting<Boolean> tracers = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  73 */       .name("tracers"))
/*  74 */       .description("Render tracer lines."))
/*  75 */       .defaultValue(Boolean.valueOf(false)))
/*  76 */       .build());
/*     */ 
/*     */   
/*  79 */   private final class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */   
/*  81 */   private final Long2ObjectMap<ESPChunk> chunks = (Long2ObjectMap<ESPChunk>)new Long2ObjectOpenHashMap();
/*  82 */   private final Set<ESPGroup> groups = (Set<ESPGroup>)new ReferenceOpenHashSet();
/*  83 */   private final ExecutorService workerThread = Executors.newSingleThreadExecutor();
/*     */   
/*     */   private class_2874 lastDimension;
/*     */   
/*     */   public BlockESP() {
/*  88 */     super(Categories.Render, "block-esp", "Renders specified blocks through walls.", new String[] { "search" });
/*     */     
/*  90 */     RainbowColors.register(this::onTickRainbow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  95 */     synchronized (this.chunks) {
/*  96 */       this.chunks.clear();
/*  97 */       this.groups.clear();
/*     */     } 
/*     */     
/* 100 */     for (class_2791 chunk : Utils.chunks()) {
/* 101 */       searchChunk(chunk);
/*     */     }
/*     */     
/* 104 */     this.lastDimension = this.mc.field_1687.method_8597();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 109 */     synchronized (this.chunks) {
/* 110 */       this.chunks.clear();
/* 111 */       this.groups.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onTickRainbow() {
/* 116 */     if (!isActive())
/*     */       return; 
/* 118 */     ((ESPBlockData)this.defaultBlockConfig.get()).tickRainbow();
/* 119 */     for (ESPBlockData blockData : ((Map)this.blockConfigs.get()).values()) blockData.tickRainbow(); 
/*     */   }
/*     */   
/*     */   ESPBlockData getBlockData(class_2248 block) {
/* 123 */     ESPBlockData blockData = (ESPBlockData)((Map)this.blockConfigs.get()).get(block);
/* 124 */     return (blockData == null) ? (ESPBlockData)this.defaultBlockConfig.get() : blockData;
/*     */   }
/*     */   
/*     */   private void updateChunk(int x, int z) {
/* 128 */     ESPChunk chunk = (ESPChunk)this.chunks.get(class_1923.method_8331(x, z));
/* 129 */     if (chunk != null) chunk.update(); 
/*     */   }
/*     */   
/*     */   private void updateBlock(int x, int y, int z) {
/* 133 */     ESPChunk chunk = (ESPChunk)this.chunks.get(class_1923.method_8331(x >> 4, z >> 4));
/* 134 */     if (chunk != null) chunk.update(x, y, z); 
/*     */   }
/*     */   
/*     */   public ESPBlock getBlock(int x, int y, int z) {
/* 138 */     ESPChunk chunk = (ESPChunk)this.chunks.get(class_1923.method_8331(x >> 4, z >> 4));
/* 139 */     return (chunk == null) ? null : chunk.get(x, y, z);
/*     */   }
/*     */   
/*     */   public ESPGroup newGroup(class_2248 block) {
/* 143 */     synchronized (this.chunks) {
/* 144 */       ESPGroup group = new ESPGroup(block);
/* 145 */       this.groups.add(group);
/* 146 */       return group;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeGroup(ESPGroup group) {
/* 151 */     synchronized (this.chunks) {
/* 152 */       this.groups.remove(group);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onChunkData(ChunkDataEvent event) {
/* 158 */     searchChunk((class_2791)event.chunk());
/*     */   }
/*     */   
/*     */   private void searchChunk(class_2791 chunk) {
/* 162 */     this.workerThread.submit(() -> {
/*     */           if (!isActive()) {
/*     */             return;
/*     */           }
/*     */           ESPChunk schunk = ESPChunk.searchChunk(chunk, (List<class_2248>)this.blocks.get());
/*     */           if (schunk.size() > 0) {
/*     */             synchronized (this.chunks) {
/*     */               this.chunks.put(chunk.method_12004().method_8324(), schunk);
/*     */               schunk.update();
/*     */               updateChunk((chunk.method_12004()).field_9181 - 1, (chunk.method_12004()).field_9180);
/*     */               updateChunk((chunk.method_12004()).field_9181 + 1, (chunk.method_12004()).field_9180);
/*     */               updateChunk((chunk.method_12004()).field_9181, (chunk.method_12004()).field_9180 - 1);
/*     */               updateChunk((chunk.method_12004()).field_9181, (chunk.method_12004()).field_9180 + 1);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onBlockUpdate(BlockUpdateEvent event) {
/* 184 */     int bx = event.pos.method_10263();
/* 185 */     int by = event.pos.method_10264();
/* 186 */     int bz = event.pos.method_10260();
/*     */     
/* 188 */     int chunkX = bx >> 4;
/* 189 */     int chunkZ = bz >> 4;
/* 190 */     long key = class_1923.method_8331(chunkX, chunkZ);
/*     */     
/* 192 */     boolean added = (((List)this.blocks.get()).contains(event.newState.method_26204()) && !((List)this.blocks.get()).contains(event.oldState.method_26204()));
/* 193 */     boolean removed = (!added && !((List)this.blocks.get()).contains(event.newState.method_26204()) && ((List)this.blocks.get()).contains(event.oldState.method_26204()));
/*     */     
/* 195 */     if (added || removed) {
/* 196 */       this.workerThread.submit(() -> {
/*     */             synchronized (this.chunks) {
/*     */               ESPChunk chunk = (ESPChunk)this.chunks.get(key);
/*     */               if (chunk == null) {
/*     */                 chunk = new ESPChunk(chunkX, chunkZ);
/*     */                 if (chunk.shouldBeDeleted()) {
/*     */                   return;
/*     */                 }
/*     */                 this.chunks.put(key, chunk);
/*     */               } 
/*     */               this.blockPos.method_10103(bx, by, bz);
/*     */               if (added) {
/*     */                 chunk.add((class_2338)this.blockPos);
/*     */               } else {
/*     */                 chunk.remove((class_2338)this.blockPos);
/*     */               } 
/*     */               for (int x = -1; x < 2; x++) {
/*     */                 for (int z = -1; z < 2; z++) {
/*     */                   for (int y = -1; y < 2; y++) {
/*     */                     if (x != 0 || y != 0 || z != 0) {
/*     */                       updateBlock(bx + x, by + y, bz + z);
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onPostTick(TickEvent.Post event) {
/* 229 */     class_2874 dimension = this.mc.field_1687.method_8597();
/*     */     
/* 231 */     if (this.lastDimension != dimension) onActivate(); 
/* 232 */     this.lastDimension = dimension;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 237 */     synchronized (this.chunks) {
/* 238 */       for (ObjectIterator<ESPChunk> objectIterator = this.chunks.values().iterator(); objectIterator.hasNext(); ) {
/* 239 */         ESPChunk chunk = objectIterator.next();
/*     */         
/* 241 */         if (chunk.shouldBeDeleted()) {
/* 242 */           this.workerThread.submit(() -> {
/*     */                 ObjectIterator<ESPBlock> objectIterator = chunk.blocks.values().iterator(); while (objectIterator.hasNext()) {
/*     */                   ESPBlock block = objectIterator.next();
/*     */                   block.group.remove(block, false);
/*     */                   block.loaded = false;
/*     */                 } 
/*     */               });
/* 249 */           objectIterator.remove(); continue;
/*     */         } 
/* 251 */         chunk.render(event);
/*     */       } 
/*     */       
/* 254 */       if (((Boolean)this.tracers.get()).booleanValue()) {
/* 255 */         for (ESPGroup group : this.groups) {
/* 256 */           group.render(event);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 264 */     return "%s groups".formatted(new Object[] { Integer.valueOf(this.groups.size()) });
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\blockesp\BlockESP.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */