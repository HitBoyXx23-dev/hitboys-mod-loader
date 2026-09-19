/*    */ package meteordevelopment.meteorclient.gui.screens.settings;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.settings.PotionSetting;
/*    */ import meteordevelopment.meteorclient.utils.misc.MyPotion;
/*    */ import net.minecraft.class_1074;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PotionSettingScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   private final PotionSetting setting;
/*    */   
/*    */   public PotionSettingScreen(GuiTheme theme, PotionSetting setting) {
/* 20 */     super(theme, "Select Potion");
/*    */     
/* 22 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 27 */     WTable table = (WTable)add((WWidget)this.theme.table()).expandX().widget();
/*    */     
/* 29 */     for (MyPotion potion : MyPotion.values()) {
/* 30 */       table.add((WWidget)this.theme.itemWithLabel(potion.potion, class_1074.method_4662(potion.potion.method_7909().method_7876(), new Object[0])));
/*    */       
/* 32 */       WButton select = (WButton)table.add((WWidget)this.theme.button("Select")).widget();
/* 33 */       select.action = (() -> {
/*    */           this.setting.set(potion);
/*    */           
/*    */           method_25419();
/*    */         });
/* 38 */       table.row();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\PotionSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */