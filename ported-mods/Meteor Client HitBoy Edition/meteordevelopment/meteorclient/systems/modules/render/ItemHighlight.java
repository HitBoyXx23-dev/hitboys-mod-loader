/*    */ package meteordevelopment.meteorclient.systems.modules.render;
/*    */ 
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*    */ import meteordevelopment.meteorclient.settings.ItemListSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1799;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemHighlight
/*    */   extends Module
/*    */ {
/* 21 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 23 */   private final Setting<List<class_1792>> items = this.sgGeneral.add((Setting)((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder())
/* 24 */       .name("items"))
/* 25 */       .description("Items to highlight."))
/* 26 */       .build());
/*    */ 
/*    */   
/* 29 */   private final Setting<SettingColor> color = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/* 30 */       .name("color"))
/* 31 */       .description("The color to highlight the items with."))
/* 32 */       .defaultValue(new SettingColor(225, 25, 255, 50))
/* 33 */       .build());
/*    */ 
/*    */   
/*    */   public ItemHighlight() {
/* 37 */     super(Categories.Render, "item-highlight", "Highlights selected items when in guis");
/*    */   }
/*    */   
/*    */   public int getColor(class_1799 stack) {
/* 41 */     if (stack != null && ((List)this.items.get()).contains(stack.method_7909()) && isActive()) return ((SettingColor)this.color.get()).getPacked(); 
/* 42 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\ItemHighlight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */