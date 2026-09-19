/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EntityTypeListSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.entity.SortPriority;
/*     */ import meteordevelopment.meteorclient.utils.entity.TargetUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_3966;
/*     */ 
/*     */ public class AutoNametag extends Module {
/*  31 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  33 */   private final Setting<Set<class_1299<?>>> entities = this.sgGeneral.add((Setting)((EntityTypeListSetting.Builder)((EntityTypeListSetting.Builder)(new EntityTypeListSetting.Builder())
/*  34 */       .name("entities"))
/*  35 */       .description("Which entities to nametag."))
/*  36 */       .build());
/*     */ 
/*     */   
/*  39 */   private final Setting<Double> range = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  40 */       .name("range"))
/*  41 */       .description("The maximum range an entity can be to be nametagged."))
/*  42 */       .defaultValue(5.0D)
/*  43 */       .min(0.0D)
/*  44 */       .sliderMax(6.0D)
/*  45 */       .build());
/*     */ 
/*     */   
/*  48 */   private final Setting<SortPriority> priority = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  49 */       .name("priority"))
/*  50 */       .description("Priority sort"))
/*  51 */       .defaultValue(SortPriority.LowestDistance))
/*  52 */       .build());
/*     */ 
/*     */   
/*  55 */   private final Setting<Boolean> renametag = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  56 */       .name("renametag"))
/*  57 */       .description("Allows already nametagged entities to be renamed."))
/*  58 */       .defaultValue(Boolean.valueOf(true)))
/*  59 */       .build());
/*     */ 
/*     */   
/*  62 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  63 */       .name("rotate"))
/*  64 */       .description("Automatically faces towards the mob being nametagged."))
/*  65 */       .defaultValue(Boolean.valueOf(true)))
/*  66 */       .build());
/*     */ 
/*     */   
/*  69 */   private final Object2IntMap<class_1297> entityCooldowns = (Object2IntMap<class_1297>)new Object2IntOpenHashMap();
/*     */   
/*     */   private class_1297 target;
/*     */   private boolean offHand;
/*     */   
/*     */   public AutoNametag() {
/*  75 */     super(Categories.World, "auto-nametag", "Automatically uses nametags on entities without a nametag. WILL nametag ALL entities in the specified distance.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  80 */     this.entityCooldowns.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTickPre(TickEvent.Pre event) {
/*  86 */     FindItemResult findNametag = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8448 });
/*     */     
/*  88 */     if (!findNametag.found()) {
/*  89 */       error("No Nametag in Hotbar", new Object[0]);
/*  90 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  95 */     this.target = TargetUtils.get(entity -> !PlayerUtils.isWithin(entity, ((Double)this.range.get()).doubleValue()) ? false : (!((Set)this.entities.get()).contains(entity.method_5864()) ? false : (
/*     */ 
/*     */ 
/*     */         
/*  99 */         (entity.method_16914() && (!((Boolean)this.renametag.get()).booleanValue() || entity.method_5797().equals(this.mc.field_1724.method_31548().method_5438(findNametag.slot()).method_7964()))) ? false : ((this.entityCooldowns.getInt(entity) <= 0)))), (SortPriority)this.priority
/*     */ 
/*     */ 
/*     */         
/* 103 */         .get());
/*     */     
/* 105 */     if (this.target == null) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     InvUtils.swap(findNametag.slot(), true);
/*     */     
/* 111 */     this.offHand = findNametag.isOffhand();
/*     */ 
/*     */     
/* 114 */     if (((Boolean)this.rotate.get()).booleanValue()) { Rotations.rotate(Rotations.getYaw(this.target), Rotations.getPitch(this.target), -100, this::interact); }
/* 115 */     else { interact(); }
/*     */   
/*     */   }
/*     */   @EventHandler
/*     */   private void onTickPost(TickEvent.Post event) {
/* 120 */     for (ObjectIterator<class_1297> objectIterator = this.entityCooldowns.keySet().iterator(); objectIterator.hasNext(); ) {
/* 121 */       class_1297 entity = objectIterator.next();
/* 122 */       int cooldown = this.entityCooldowns.getInt(entity) - 1;
/*     */       
/* 124 */       if (cooldown <= 0) { objectIterator.remove(); continue; }
/* 125 */        this.entityCooldowns.put(entity, cooldown);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void interact() {
/* 130 */     class_1268 hand = this.offHand ? class_1268.field_5810 : class_1268.field_5808;
/* 131 */     class_3966 location = new class_3966(this.target, this.target.method_5829().method_1005());
/* 132 */     this.mc.field_1761.method_2917((class_1657)this.mc.field_1724, this.target, location, hand);
/* 133 */     this.mc.field_1761.method_2905((class_1657)this.mc.field_1724, this.target, hand);
/* 134 */     InvUtils.swapBack();
/*     */     
/* 136 */     this.entityCooldowns.put(this.target, 20);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\AutoNametag.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */