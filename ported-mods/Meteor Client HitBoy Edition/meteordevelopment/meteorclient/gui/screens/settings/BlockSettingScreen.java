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
/*    */ import meteordevelopment.meteorclient.settings.BlockSetting;
/*    */ import meteordevelopment.meteorclient.utils.misc.Names;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_7923;
/*    */ import org.apache.commons.lang3.Strings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSettingScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   private final BlockSetting setting;
/*    */   private WTable table;
/*    */   private WTextBox filter;
/* 27 */   private String filterText = "";
/*    */   
/*    */   public BlockSettingScreen(GuiTheme theme, BlockSetting setting) {
/* 30 */     super(theme, "Select Block");
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
/*    */     
/* 48 */     initTable();
/*    */   }
/*    */   
/*    */   private void initTable() {
/* 52 */     for (Iterator<class_2248> iterator = class_7923.field_41175.iterator(); iterator.hasNext(); ) { class_2248 block = iterator.next();
/* 53 */       if ((this.setting.filter != null && !this.setting.filter.test(block)) || 
/* 54 */         skipValue(block))
/*    */         continue; 
/* 56 */       WItemWithLabel item = this.theme.itemWithLabel(block.method_8389().method_7854(), Names.get(block));
/* 57 */       if (!this.filterText.isEmpty() && !Strings.CI.contains(item.getLabelText(), this.filterText))
/* 58 */         continue;  this.table.add((WWidget)item);
/*    */       
/* 60 */       WButton select = (WButton)this.table.add((WWidget)this.theme.button("Select")).expandCellX().right().widget();
/* 61 */       select.action = (() -> {
/*    */           this.setting.set(block);
/*    */           
/*    */           method_25419();
/*    */         });
/* 66 */       this.table.row(); }
/*    */   
/*    */   }
/*    */   
/*    */   protected boolean skipValue(class_2248 value) {
/* 71 */     return (value == class_2246.field_10124 || class_7923.field_41175.method_10221(value).method_12832().endsWith("_wall_banner"));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\BlockSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */