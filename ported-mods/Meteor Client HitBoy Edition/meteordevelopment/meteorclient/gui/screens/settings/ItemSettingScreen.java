/*    */ package meteordevelopment.meteorclient.gui.screens.settings;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WItemWithLabel;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.settings.ItemSetting;
/*    */ import meteordevelopment.meteorclient.utils.misc.Names;
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_7923;
/*    */ import org.apache.commons.lang3.Strings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSettingScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   private final ItemSetting setting;
/*    */   private WTable table;
/*    */   private WTextBox filter;
/* 27 */   private String filterText = "";
/*    */   
/*    */   public ItemSettingScreen(GuiTheme theme, ItemSetting setting) {
/* 30 */     super(theme, "Select item");
/*    */     
/* 32 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 37 */     this.filter = (WTextBox)add((WWidget)this.theme.textBox("")).minWidth(400.0D).expandX().widget();
/* 38 */     this.filter.setFocused(true);
/* 39 */     this.filter.action = (() -> {
/*    */         this.filterText = this.filter.get().trim();
/*    */         
/*    */         this.table.clear();
/*    */         
/*    */         initTable();
/*    */       });
/* 46 */     this.table = (WTable)add((WWidget)this.theme.table()).expandX().widget();
/* 47 */     initTable();
/*    */   }
/*    */   
/*    */   public void initTable() {
/* 51 */     for (Iterator<class_1792> iterator = class_7923.field_41178.iterator(); iterator.hasNext(); ) { class_1792 item = iterator.next();
/* 52 */       if ((this.setting.filter != null && !this.setting.filter.test(item)) || 
/* 53 */         item == class_1802.field_8162)
/*    */         continue; 
/* 55 */       WItemWithLabel itemLabel = this.theme.itemWithLabel(item.method_7854(), Names.get(item));
/* 56 */       if (!this.filterText.isEmpty() && !Strings.CI.contains(itemLabel.getLabelText(), this.filterText))
/* 57 */         continue;  this.table.add((WWidget)itemLabel);
/*    */       
/* 59 */       WButton select = (WButton)this.table.add((WWidget)this.theme.button("Select")).expandCellX().right().widget();
/* 60 */       select.action = (() -> {
/*    */           this.setting.set(item);
/*    */           
/*    */           method_25419();
/*    */         });
/* 65 */       this.table.row(); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\ItemSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */