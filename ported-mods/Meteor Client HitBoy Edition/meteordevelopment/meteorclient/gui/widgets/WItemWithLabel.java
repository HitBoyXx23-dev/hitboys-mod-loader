/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.utils.misc.Names;
/*    */ import net.minecraft.class_1293;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_1844;
/*    */ import net.minecraft.class_9334;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WItemWithLabel
/*    */   extends WHorizontalList
/*    */ {
/*    */   private class_1799 itemStack;
/*    */   private String name;
/*    */   private WItem item;
/*    */   private WLabel label;
/*    */   
/*    */   public WItemWithLabel(class_1799 itemStack, String name) {
/* 28 */     this.itemStack = itemStack;
/* 29 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 34 */     this.item = (WItem)add(this.theme.item(this.itemStack)).widget();
/* 35 */     this.label = (WLabel)add((WWidget)this.theme.label(this.name + this.name)).widget();
/*    */   }
/*    */   
/*    */   private String getStringToAppend() {
/* 39 */     String str = "";
/*    */     
/* 41 */     if (this.itemStack.method_7909() == class_1802.field_8574) {
/* 42 */       Iterator<class_1293> effects = ((class_1844)this.itemStack.method_7909().method_57347().method_58694(class_9334.field_49651)).method_57397().iterator();
/* 43 */       if (!effects.hasNext()) return str;
/*    */       
/* 45 */       str = str + " ";
/*    */       
/* 47 */       class_1293 effect = effects.next();
/* 48 */       if (effect.method_5578() > 0) str = str + str;
/*    */       
/* 50 */       str = str + str;
/*    */     } 
/*    */     
/* 53 */     return str;
/*    */   }
/*    */   
/*    */   public void set(class_1799 itemStack) {
/* 57 */     this.itemStack = itemStack;
/* 58 */     this.item.itemStack = itemStack;
/*    */     
/* 60 */     this.name = Names.get(itemStack);
/* 61 */     this.label.set(this.name + this.name);
/*    */   }
/*    */   
/*    */   public String getLabelText() {
/* 65 */     return (this.label == null) ? this.name : this.label.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WItemWithLabel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */