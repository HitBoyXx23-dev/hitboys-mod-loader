/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixininterface.IClientPlayerInteractionManager;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1713;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2680;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvUtils
/*     */ {
/*  26 */   private static final Action ACTION = new Action();
/*  27 */   public static int previousSlot = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean testInMainHand(Predicate<class_1799> predicate) {
/*  35 */     return predicate.test(MeteorClient.mc.field_1724.method_6047());
/*     */   }
/*     */   
/*     */   public static boolean testInMainHand(class_1792... items) {
/*  39 */     return testInMainHand(itemStack -> {
/*     */           for (class_1792 item : items) {
/*     */             if (itemStack.method_31574(item))
/*     */               return true; 
/*     */           } 
/*     */           return false;
/*     */         }); } public static boolean testInOffHand(Predicate<class_1799> predicate) {
/*  46 */     return predicate.test(MeteorClient.mc.field_1724.method_6079());
/*     */   }
/*     */   
/*     */   public static boolean testInOffHand(class_1792... items) {
/*  50 */     return testInOffHand(itemStack -> {
/*     */           for (class_1792 item : items) {
/*     */             if (itemStack.method_31574(item))
/*     */               return true; 
/*     */           } 
/*     */           return false;
/*     */         }); } public static boolean testInHands(Predicate<class_1799> predicate) {
/*  57 */     return (testInMainHand(predicate) || testInOffHand(predicate));
/*     */   }
/*     */   
/*     */   public static boolean testInHands(class_1792... items) {
/*  61 */     return (testInMainHand(items) || testInOffHand(items));
/*     */   }
/*     */   
/*     */   public static boolean testInHotbar(Predicate<class_1799> predicate) {
/*  65 */     if (testInHands(predicate)) return true;
/*     */     
/*  67 */     for (int i = 0; i <= 8; i++) {
/*  68 */       class_1799 stack = MeteorClient.mc.field_1724.method_31548().method_5438(i);
/*  69 */       if (predicate.test(stack)) return true;
/*     */     
/*     */     } 
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean testInHotbar(class_1792... items) {
/*  76 */     return testInHotbar(itemStack -> {
/*     */           for (class_1792 item : items) {
/*     */             if (itemStack.method_31574(item))
/*     */               return true; 
/*     */           } 
/*     */           return false;
/*     */         });
/*     */   }
/*     */   public static FindItemResult findEmpty() {
/*  85 */     return find(class_1799::method_7960);
/*     */   }
/*     */   
/*     */   public static FindItemResult findInHotbar(class_1792... items) {
/*  89 */     return findInHotbar(itemStack -> {
/*     */           for (class_1792 item : items) {
/*     */             if (itemStack.method_7909() == item)
/*     */               return true; 
/*     */           } 
/*     */           return false;
/*     */         });
/*     */   }
/*     */   public static FindItemResult findInHotbar(Predicate<class_1799> isGood) {
/*  98 */     if (testInOffHand(isGood)) {
/*  99 */       return new FindItemResult(40, MeteorClient.mc.field_1724.method_6079().method_7947());
/*     */     }
/*     */     
/* 102 */     if (testInMainHand(isGood)) {
/* 103 */       return new FindItemResult(MeteorClient.mc.field_1724.method_31548().method_67532(), MeteorClient.mc.field_1724.method_6047().method_7947());
/*     */     }
/*     */     
/* 106 */     return find(isGood, 0, 8);
/*     */   }
/*     */   
/*     */   public static FindItemResult find(class_1792... items) {
/* 110 */     return find(itemStack -> {
/*     */           for (class_1792 item : items) {
/*     */             if (itemStack.method_7909() == item)
/*     */               return true; 
/*     */           } 
/*     */           return false;
/*     */         });
/*     */   }
/*     */   public static FindItemResult find(Predicate<class_1799> isGood) {
/* 119 */     if (MeteorClient.mc.field_1724 == null) return new FindItemResult(0, 0); 
/* 120 */     return find(isGood, 0, MeteorClient.mc.field_1724.method_31548().method_5439());
/*     */   }
/*     */   
/*     */   public static FindItemResult find(Predicate<class_1799> isGood, int start, int end) {
/* 124 */     if (MeteorClient.mc.field_1724 == null) return new FindItemResult(0, 0);
/*     */     
/* 126 */     int slot = -1, count = 0;
/*     */     
/* 128 */     for (int i = start; i <= end; i++) {
/* 129 */       class_1799 stack = MeteorClient.mc.field_1724.method_31548().method_5438(i);
/*     */       
/* 131 */       if (isGood.test(stack)) {
/* 132 */         if (slot == -1) slot = i; 
/* 133 */         count += stack.method_7947();
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return new FindItemResult(slot, count);
/*     */   }
/*     */   
/*     */   public static FindItemResult findFastestTool(class_2680 state) {
/* 141 */     float bestScore = 1.0F;
/* 142 */     int slot = -1;
/*     */     
/* 144 */     for (int i = 0; i < 9; i++) {
/* 145 */       class_1799 stack = MeteorClient.mc.field_1724.method_31548().method_5438(i);
/* 146 */       if (stack.method_7951(state)) {
/*     */         
/* 148 */         float score = stack.method_7924(state);
/* 149 */         if (score > bestScore) {
/* 150 */           bestScore = score;
/* 151 */           slot = i;
/*     */         } 
/*     */       } 
/*     */     } 
/* 155 */     return new FindItemResult(slot, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean swap(int slot, boolean swapBack) {
/* 161 */     if (slot == 40) return true; 
/* 162 */     if (slot < 0 || slot > 8) return false; 
/* 163 */     if (swapBack && previousSlot == -1) { previousSlot = MeteorClient.mc.field_1724.method_31548().method_67532(); }
/* 164 */     else if (!swapBack) { previousSlot = -1; }
/*     */     
/* 166 */     MeteorClient.mc.field_1724.method_31548().method_61496(slot);
/* 167 */     ((IClientPlayerInteractionManager)MeteorClient.mc.field_1761).meteor$syncSelected();
/* 168 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean swapBack() {
/* 172 */     if (previousSlot == -1) return false;
/*     */     
/* 174 */     boolean return_ = swap(previousSlot, false);
/* 175 */     previousSlot = -1;
/* 176 */     return return_;
/*     */   }
/*     */   
/*     */   public static Action move() {
/* 180 */     ACTION.type = class_1713.field_7790;
/* 181 */     ACTION.two = true;
/* 182 */     return ACTION;
/*     */   }
/*     */   
/*     */   public static Action click() {
/* 186 */     ACTION.type = class_1713.field_7790;
/* 187 */     return ACTION;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Action quickSwap() {
/* 195 */     ACTION.type = class_1713.field_7791;
/* 196 */     return ACTION;
/*     */   }
/*     */   
/*     */   public static Action shiftClick() {
/* 200 */     ACTION.type = class_1713.field_7794;
/* 201 */     return ACTION;
/*     */   }
/*     */   
/*     */   public static Action drop() {
/* 205 */     ACTION.type = class_1713.field_7795;
/* 206 */     ACTION.data = 1;
/* 207 */     return ACTION;
/*     */   }
/*     */   
/*     */   public static Action dropOne() {
/* 211 */     ACTION.type = class_1713.field_7795;
/* 212 */     ACTION.data = 0;
/* 213 */     return ACTION;
/*     */   }
/*     */   
/*     */   public static void dropHand() {
/* 217 */     if (!MeteorClient.mc.field_1724.field_7512.method_34255().method_7960()) MeteorClient.mc.field_1761.method_2906(MeteorClient.mc.field_1724.field_7512.field_7763, -999, 0, class_1713.field_7790, (class_1657)MeteorClient.mc.field_1724); 
/*     */   }
/*     */   
/*     */   public static class Action {
/* 221 */     private class_1713 type = null;
/*     */     private boolean two = false;
/* 223 */     private int from = -1;
/* 224 */     private int to = -1;
/* 225 */     private int data = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isRecursive = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Action fromId(int id) {
/* 235 */       this.from = id;
/* 236 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Action from(int index) {
/* 243 */       return fromId(SlotUtils.indexToId(index));
/*     */     }
/*     */     
/*     */     public Action fromHotbar(int i) {
/* 247 */       return from(0 + i);
/*     */     }
/*     */     
/*     */     public Action fromOffhand() {
/* 251 */       return from(40);
/*     */     }
/*     */     
/*     */     public Action fromMain(int i) {
/* 255 */       return from(9 + i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Action fromArmor(int i) {
/* 262 */       return from(36 + 3 - i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void toId(int id) {
/* 268 */       this.to = id;
/* 269 */       run();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void to(int index) {
/* 276 */       toId(SlotUtils.indexToId(index));
/*     */     }
/*     */     
/*     */     public void toHotbar(int i) {
/* 280 */       to(0 + i);
/*     */     }
/*     */     
/*     */     public void toOffhand() {
/* 284 */       to(40);
/*     */     }
/*     */     
/*     */     public void toMain(int i) {
/* 288 */       to(9 + i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void toArmor(int i) {
/* 295 */       to(36 + 3 - i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void slotId(int id) {
/* 301 */       this.from = this.to = id;
/* 302 */       run();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void slot(int index) {
/* 309 */       slotId(SlotUtils.indexToId(index));
/*     */     }
/*     */     
/*     */     public void slotHotbar(int i) {
/* 313 */       slot(0 + i);
/*     */     }
/*     */     
/*     */     public void slotOffhand() {
/* 317 */       slot(40);
/*     */     }
/*     */     
/*     */     public void slotMain(int i) {
/* 321 */       slot(9 + i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void slotArmor(int i) {
/* 328 */       slot(36 + 3 - i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void run() {
/* 334 */       boolean hadEmptyCursor = MeteorClient.mc.field_1724.field_7512.method_34255().method_7960();
/*     */       
/* 336 */       if (this.type == class_1713.field_7791) {
/* 337 */         this.data = this.from;
/* 338 */         this.from = this.to;
/*     */       } 
/*     */       
/* 341 */       if (this.type != null && this.from != -1 && this.to != -1) {
/* 342 */         click(this.from);
/* 343 */         if (this.two) click(this.to);
/*     */       
/*     */       } 
/* 346 */       class_1713 preType = this.type;
/* 347 */       boolean preTwo = this.two;
/* 348 */       int preFrom = this.from;
/* 349 */       int preTo = this.to;
/*     */       
/* 351 */       this.type = null;
/* 352 */       this.two = false;
/* 353 */       this.from = -1;
/* 354 */       this.to = -1;
/* 355 */       this.data = 0;
/*     */       
/* 357 */       if (!this.isRecursive && hadEmptyCursor && preType == class_1713.field_7790 && preTwo && preFrom != -1 && preTo != -1 && !MeteorClient.mc.field_1724.field_7512.method_34255().method_7960()) {
/* 358 */         this.isRecursive = true;
/* 359 */         InvUtils.click().slotId(preFrom);
/* 360 */         this.isRecursive = false;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void click(int id) {
/* 365 */       MeteorClient.mc.field_1761.method_2906(MeteorClient.mc.field_1724.field_7512.field_7763, id, this.data, this.type, (class_1657)MeteorClient.mc.field_1724);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\InvUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */