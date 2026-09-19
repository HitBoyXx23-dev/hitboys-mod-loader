/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1713;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Action
/*     */ {
/* 221 */   private class_1713 type = null;
/*     */   private boolean two = false;
/* 223 */   private int from = -1;
/* 224 */   private int to = -1;
/* 225 */   private int data = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRecursive = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action fromId(int id) {
/* 235 */     this.from = id;
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action from(int index) {
/* 243 */     return fromId(SlotUtils.indexToId(index));
/*     */   }
/*     */   
/*     */   public Action fromHotbar(int i) {
/* 247 */     return from(0 + i);
/*     */   }
/*     */   
/*     */   public Action fromOffhand() {
/* 251 */     return from(40);
/*     */   }
/*     */   
/*     */   public Action fromMain(int i) {
/* 255 */     return from(9 + i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action fromArmor(int i) {
/* 262 */     return from(36 + 3 - i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void toId(int id) {
/* 268 */     this.to = id;
/* 269 */     run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void to(int index) {
/* 276 */     toId(SlotUtils.indexToId(index));
/*     */   }
/*     */   
/*     */   public void toHotbar(int i) {
/* 280 */     to(0 + i);
/*     */   }
/*     */   
/*     */   public void toOffhand() {
/* 284 */     to(40);
/*     */   }
/*     */   
/*     */   public void toMain(int i) {
/* 288 */     to(9 + i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toArmor(int i) {
/* 295 */     to(36 + 3 - i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void slotId(int id) {
/* 301 */     this.from = this.to = id;
/* 302 */     run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slot(int index) {
/* 309 */     slotId(SlotUtils.indexToId(index));
/*     */   }
/*     */   
/*     */   public void slotHotbar(int i) {
/* 313 */     slot(0 + i);
/*     */   }
/*     */   
/*     */   public void slotOffhand() {
/* 317 */     slot(40);
/*     */   }
/*     */   
/*     */   public void slotMain(int i) {
/* 321 */     slot(9 + i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slotArmor(int i) {
/* 328 */     slot(36 + 3 - i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void run() {
/* 334 */     boolean hadEmptyCursor = MeteorClient.mc.field_1724.field_7512.method_34255().method_7960();
/*     */     
/* 336 */     if (this.type == class_1713.field_7791) {
/* 337 */       this.data = this.from;
/* 338 */       this.from = this.to;
/*     */     } 
/*     */     
/* 341 */     if (this.type != null && this.from != -1 && this.to != -1) {
/* 342 */       click(this.from);
/* 343 */       if (this.two) click(this.to);
/*     */     
/*     */     } 
/* 346 */     class_1713 preType = this.type;
/* 347 */     boolean preTwo = this.two;
/* 348 */     int preFrom = this.from;
/* 349 */     int preTo = this.to;
/*     */     
/* 351 */     this.type = null;
/* 352 */     this.two = false;
/* 353 */     this.from = -1;
/* 354 */     this.to = -1;
/* 355 */     this.data = 0;
/*     */     
/* 357 */     if (!this.isRecursive && hadEmptyCursor && preType == class_1713.field_7790 && preTwo && preFrom != -1 && preTo != -1 && !MeteorClient.mc.field_1724.field_7512.method_34255().method_7960()) {
/* 358 */       this.isRecursive = true;
/* 359 */       InvUtils.click().slotId(preFrom);
/* 360 */       this.isRecursive = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void click(int id) {
/* 365 */     MeteorClient.mc.field_1761.method_2906(MeteorClient.mc.field_1724.field_7512.field_7763, id, this.data, this.type, (class_1657)MeteorClient.mc.field_1724);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\InvUtils$Action.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */