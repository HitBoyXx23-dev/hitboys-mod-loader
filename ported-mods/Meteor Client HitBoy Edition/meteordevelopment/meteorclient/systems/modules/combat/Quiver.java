/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.mixin.AbstractBlockAccessor;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.StatusEffectListSetting;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1291;
/*     */ import net.minecraft.class_1293;
/*     */ import net.minecraft.class_1294;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1753;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2828;
/*     */ import net.minecraft.class_9334;
/*     */ 
/*     */ public class Quiver extends Module {
/*  37 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  38 */   private final SettingGroup sgSafety = this.settings.createGroup("Safety");
/*     */ 
/*     */   
/*  41 */   private final Setting<List<class_1291>> effects = this.sgGeneral.add((Setting)((StatusEffectListSetting.Builder)((StatusEffectListSetting.Builder)(new StatusEffectListSetting.Builder())
/*  42 */       .name("effects"))
/*  43 */       .description("Which effects to shoot you with."))
/*  44 */       .defaultValue(new class_1291[] { (class_1291)class_1294.field_5910.comp_349()
/*  45 */         }).build());
/*     */ 
/*     */   
/*  48 */   private final Setting<Integer> cooldown = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  49 */       .name("cooldown"))
/*  50 */       .description("How many ticks between shooting effects (19 minimum for NCP)."))
/*  51 */       .defaultValue(Integer.valueOf(10)))
/*  52 */       .range(0, 40)
/*  53 */       .sliderRange(0, 40)
/*  54 */       .build());
/*     */ 
/*     */   
/*  57 */   private final Setting<Boolean> checkEffects = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  58 */       .name("check-effects"))
/*  59 */       .description("Won't shoot you with effects you already have."))
/*  60 */       .defaultValue(Boolean.valueOf(true)))
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final Setting<Boolean> silentBow = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  65 */       .name("silent-bow"))
/*  66 */       .description("Takes a bow from your inventory to quiver."))
/*  67 */       .defaultValue(Boolean.valueOf(true)))
/*  68 */       .build());
/*     */ 
/*     */   
/*  71 */   private final Setting<Boolean> chatInfo = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  72 */       .name("chat-info"))
/*  73 */       .description("Sends info about quiver checks in chat."))
/*  74 */       .defaultValue(Boolean.valueOf(false)))
/*  75 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private final Setting<Boolean> onlyInHoles = this.sgSafety.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  81 */       .name("only-in-holes"))
/*  82 */       .description("Only quiver when you're in a hole."))
/*  83 */       .defaultValue(Boolean.valueOf(true)))
/*  84 */       .build());
/*     */ 
/*     */   
/*  87 */   private final Setting<Boolean> onlyOnGround = this.sgSafety.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  88 */       .name("only-on-ground"))
/*  89 */       .description("Only quiver when you're on the ground."))
/*  90 */       .defaultValue(Boolean.valueOf(true)))
/*  91 */       .build());
/*     */ 
/*     */   
/*  94 */   private final Setting<Double> minHealth = this.sgSafety.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  95 */       .name("min-health"))
/*  96 */       .description("How much health you must have to quiver."))
/*  97 */       .defaultValue(10.0D)
/*  98 */       .range(0.0D, 36.0D)
/*  99 */       .sliderRange(0.0D, 36.0D)
/* 100 */       .build());
/*     */ 
/*     */   
/* 103 */   private final List<Integer> arrowSlots = new ArrayList<>(); private FindItemResult bow; private boolean wasMainhand;
/*     */   private boolean wasHotbar;
/*     */   private int timer;
/*     */   private int prevSlot;
/* 107 */   private final class_2338.class_2339 testPos = new class_2338.class_2339();
/*     */   
/*     */   public Quiver() {
/* 110 */     super(Categories.Combat, "quiver", "Shoots arrows at yourself.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/* 115 */     this.bow = InvUtils.find(new class_1792[] { class_1802.field_8102 });
/* 116 */     if (!shouldQuiver())
/*     */       return; 
/* 118 */     this.mc.field_1690.field_1904.method_23481(false);
/* 119 */     this.mc.field_1761.method_2897((class_1657)this.mc.field_1724);
/*     */     
/* 121 */     this.prevSlot = this.bow.slot();
/* 122 */     this.wasHotbar = this.bow.isHotbar();
/* 123 */     this.timer = 0;
/*     */     
/* 125 */     if (!this.bow.isMainHand())
/* 126 */     { if (this.wasHotbar) { InvUtils.swap(this.bow.slot(), true); }
/* 127 */       else { InvUtils.move().from(this.mc.field_1724.method_31548().method_67532()).to(this.prevSlot); }  }
/* 128 */     else { this.wasMainhand = true; }
/*     */     
/* 130 */     this.arrowSlots.clear();
/* 131 */     List<class_1291> usedEffects = new ArrayList<>();
/*     */     
/* 133 */     for (int i = this.mc.field_1724.method_31548().method_5439(); i > 0; i--) {
/* 134 */       if (i != this.mc.field_1724.method_31548().method_67532()) {
/*     */         
/* 136 */         class_1799 item = this.mc.field_1724.method_31548().method_5438(i);
/*     */         
/* 138 */         if (item.method_7909() == class_1802.field_8087) {
/*     */           
/* 140 */           Iterator<class_1293> effects = ((class_1844)item.method_7909().method_57347().method_58694(class_9334.field_49651)).method_57397().iterator();
/*     */           
/* 142 */           if (effects.hasNext()) {
/*     */             
/* 144 */             class_1291 effect = (class_1291)((class_1293)effects.next()).method_5579().comp_349();
/*     */             
/* 146 */             if (((List)this.effects.get()).contains(effect) && 
/* 147 */               !usedEffects.contains(effect) && (
/* 148 */               !hasEffect(effect) || !((Boolean)this.checkEffects.get()).booleanValue())) {
/* 149 */               usedEffects.add(effect);
/* 150 */               this.arrowSlots.add(Integer.valueOf(i));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public void onDeactivate() {
/* 157 */     if (!this.wasMainhand)
/* 158 */       if (this.wasHotbar) { InvUtils.swapBack(); }
/* 159 */       else { InvUtils.move().from(this.mc.field_1724.method_31548().method_67532()).to(this.prevSlot); }
/*     */        
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 165 */     this.bow = InvUtils.find(new class_1792[] { class_1802.field_8102 });
/* 166 */     if (!shouldQuiver())
/* 167 */       return;  if (this.arrowSlots.isEmpty()) {
/* 168 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/* 172 */     if (this.timer > 0) {
/* 173 */       this.timer--;
/*     */       
/*     */       return;
/*     */     } 
/* 177 */     boolean charging = this.mc.field_1690.field_1904.method_1434();
/*     */     
/* 179 */     if (!charging) {
/* 180 */       InvUtils.move().from(((Integer)this.arrowSlots.getFirst()).intValue()).to(9);
/* 181 */       this.mc.field_1690.field_1904.method_23481(true);
/*     */     }
/* 183 */     else if (class_1753.method_7722(this.mc.field_1724.method_6048()) >= 0.12D) {
/* 184 */       int targetSlot = ((Integer)this.arrowSlots.getFirst()).intValue();
/* 185 */       this.arrowSlots.removeFirst();
/*     */       
/* 187 */       this.mc.method_1562().method_52787((class_2596)new class_2828.class_2831(this.mc.field_1724.method_36454(), -90.0F, this.mc.field_1724.method_24828(), this.mc.field_1724.field_5976));
/* 188 */       this.mc.field_1690.field_1904.method_23481(false);
/* 189 */       this.mc.field_1761.method_2897((class_1657)this.mc.field_1724);
/* 190 */       if (targetSlot != 9) InvUtils.move().from(9).to(targetSlot);
/*     */       
/* 192 */       this.timer = ((Integer)this.cooldown.get()).intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldQuiver() {
/* 198 */     if (!this.bow.found() || (!this.bow.isHotbar() && !((Boolean)this.silentBow.get()).booleanValue())) {
/* 199 */       if (((Boolean)this.chatInfo.get()).booleanValue()) error("Couldn't find a usable bow, disabling.", new Object[0]); 
/* 200 */       toggle();
/* 201 */       return false;
/*     */     } 
/*     */     
/* 204 */     if (!headIsOpen()) {
/* 205 */       if (((Boolean)this.chatInfo.get()).booleanValue()) error("Not enough space to quiver, disabling.", new Object[0]); 
/* 206 */       toggle();
/* 207 */       return false;
/*     */     } 
/*     */     
/* 210 */     if (EntityUtils.getTotalHealth((class_1309)this.mc.field_1724) < ((Double)this.minHealth.get()).doubleValue()) {
/* 211 */       if (((Boolean)this.chatInfo.get()).booleanValue()) error("Not enough health to quiver, disabling.", new Object[0]); 
/* 212 */       toggle();
/* 213 */       return false;
/*     */     } 
/*     */     
/* 216 */     if (((Boolean)this.onlyOnGround.get()).booleanValue() && !this.mc.field_1724.method_24828()) {
/* 217 */       if (((Boolean)this.chatInfo.get()).booleanValue()) error("You are not on the ground, disabling.", new Object[0]); 
/* 218 */       toggle();
/* 219 */       return false;
/*     */     } 
/*     */     
/* 222 */     if (((Boolean)this.onlyInHoles.get()).booleanValue() && !isSurrounded((class_1657)this.mc.field_1724)) {
/* 223 */       if (((Boolean)this.chatInfo.get()).booleanValue()) error("You are not in a hole, disabling.", new Object[0]); 
/* 224 */       toggle();
/* 225 */       return false;
/*     */     } 
/*     */     
/* 228 */     return true;
/*     */   }
/*     */   
/*     */   private boolean headIsOpen() {
/* 232 */     this.testPos.method_10101((class_2382)this.mc.field_1724.method_24515().method_10069(0, 1, 0));
/* 233 */     class_2680 pos1 = this.mc.field_1687.method_8320((class_2338)this.testPos);
/* 234 */     if (((AbstractBlockAccessor)pos1.method_26204()).meteor$isCollidable()) return false;
/*     */     
/* 236 */     this.testPos.method_10069(0, 1, 0);
/* 237 */     class_2680 pos2 = this.mc.field_1687.method_8320((class_2338)this.testPos);
/* 238 */     return !((AbstractBlockAccessor)pos2.method_26204()).meteor$isCollidable();
/*     */   }
/*     */   
/*     */   private boolean hasEffect(class_1291 effect) {
/* 242 */     for (class_1293 statusEffect : this.mc.field_1724.method_6026()) {
/* 243 */       if (((class_1291)statusEffect.method_5579().comp_349()).equals(effect)) return true;
/*     */     
/*     */     } 
/* 246 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isSurrounded(class_1657 target) {
/* 250 */     for (class_2350 dir : class_2350.values()) {
/* 251 */       if (dir != class_2350.field_11036 && dir != class_2350.field_11033) {
/*     */         
/* 253 */         this.testPos.method_10101((class_2382)target.method_24515()).method_10093(dir);
/* 254 */         class_2248 block = this.mc.field_1687.method_8320((class_2338)this.testPos).method_26204();
/*     */         
/* 256 */         if (block != class_2246.field_10540 && block != class_2246.field_9987 && block != class_2246.field_23152 && block != class_2246.field_22423 && block != class_2246.field_22108)
/*     */         {
/* 258 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 262 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\Quiver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */