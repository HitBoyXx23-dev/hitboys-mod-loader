/*    */ package meteordevelopment.meteorclient.gui.screens.settings;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WView;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WDropdown;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.renderer.Fonts;
/*    */ import meteordevelopment.meteorclient.renderer.text.FontFamily;
/*    */ import meteordevelopment.meteorclient.renderer.text.FontInfo;
/*    */ import meteordevelopment.meteorclient.settings.FontFaceSetting;
/*    */ import org.apache.commons.lang3.Strings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontFaceSettingScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   private final FontFaceSetting setting;
/*    */   private WTable table;
/*    */   private WTextBox filter;
/* 32 */   private String filterText = "";
/*    */   
/*    */   public FontFaceSettingScreen(GuiTheme theme, FontFaceSetting setting) {
/* 35 */     super(theme, "Select Font");
/*    */     
/* 37 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 42 */     this.filter = (WTextBox)add((WWidget)this.theme.textBox("")).expandX().widget();
/* 43 */     this.filter.setFocused(true);
/* 44 */     this.filter.action = (() -> {
/*    */         this.filterText = this.filter.get().trim();
/*    */         
/*    */         this.table.clear();
/*    */         
/*    */         initTable();
/*    */       });
/* 51 */     this.window.view.hasScrollBar = false;
/*    */     
/* 53 */     this.enterAction = (() -> {
/*    */         List<Cell<?>> row = this.table.getRow(0);
/*    */         if (row == null)
/*    */           return; 
/*    */         WWidget widget = ((Cell)row.get(2)).widget();
/*    */         if (widget instanceof WButton) {
/*    */           WButton button = (WButton)widget;
/*    */           button.action.run();
/*    */         } 
/*    */       });
/* 63 */     WView view = (WView)add((WWidget)this.theme.view()).expandX().widget();
/*    */     
/* 65 */     this.window.view.maxHeight -= 128.0D;
/* 66 */     view.scrollOnlyWhenMouseOver = false;
/*    */     
/* 68 */     this.table = (WTable)view.add((WWidget)this.theme.table()).expandX().widget();
/*    */     
/* 70 */     initTable();
/*    */   }
/*    */   
/*    */   private void initTable() {
/* 74 */     for (Iterator<FontFamily> iterator = Fonts.FONT_FAMILIES.iterator(); iterator.hasNext(); ) { FontFamily fontFamily = iterator.next();
/* 75 */       String name = fontFamily.getName();
/*    */       
/* 77 */       WLabel item = this.theme.label(name);
/* 78 */       if (!this.filterText.isEmpty() && !Strings.CI.contains(name, this.filterText))
/* 79 */         continue;  this.table.add((WWidget)item);
/*    */       
/* 81 */       WDropdown<FontInfo.Type> dropdown = (WDropdown<FontInfo.Type>)this.table.add((WWidget)this.theme.dropdown((Enum)FontInfo.Type.Regular)).right().widget();
/*    */       
/* 83 */       WButton select = (WButton)this.table.add((WWidget)this.theme.button("Select")).expandCellX().right().widget();
/* 84 */       select.action = (() -> {
/*    */           this.setting.set(fontFamily.get((FontInfo.Type)dropdown.get()));
/*    */           
/*    */           method_25419();
/*    */         });
/* 89 */       this.table.row(); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\FontFaceSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */