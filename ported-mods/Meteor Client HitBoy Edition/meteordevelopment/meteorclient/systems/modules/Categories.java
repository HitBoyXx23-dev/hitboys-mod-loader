/*    */ package meteordevelopment.meteorclient.systems.modules;
/*    */ 
/*    */ import meteordevelopment.meteorclient.addons.AddonManager;
/*    */ import meteordevelopment.meteorclient.addons.MeteorAddon;
/*    */ import net.minecraft.class_1802;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Categories
/*    */ {
/* 13 */   public static final Category Combat = new Category("Combat", class_1802.field_8845.method_7854());
/* 14 */   public static final Category Player = new Category("Player", class_1802.field_8694.method_7854());
/* 15 */   public static final Category Movement = new Category("Movement", class_1802.field_8285.method_7854());
/* 16 */   public static final Category Render = new Category("Render", class_1802.field_8280.method_7854());
/* 17 */   public static final Category World = new Category("World", class_1802.field_8270.method_7854());
/* 18 */   public static final Category Misc = new Category("Misc", class_1802.field_8187.method_7854());
/*    */   
/*    */   public static boolean REGISTERING;
/*    */   
/*    */   public static void init() {
/* 23 */     REGISTERING = true;
/*    */ 
/*    */     
/* 26 */     Modules.registerCategory(Combat);
/* 27 */     Modules.registerCategory(Player);
/* 28 */     Modules.registerCategory(Movement);
/* 29 */     Modules.registerCategory(Render);
/* 30 */     Modules.registerCategory(World);
/* 31 */     Modules.registerCategory(Misc);
/*    */ 
/*    */     
/* 34 */     AddonManager.ADDONS.forEach(MeteorAddon::onRegisterCategories);
/*    */     
/* 36 */     REGISTERING = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\Categories.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */