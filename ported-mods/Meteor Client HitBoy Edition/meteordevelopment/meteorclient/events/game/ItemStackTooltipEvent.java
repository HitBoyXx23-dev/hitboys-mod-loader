/*    */ package meteordevelopment.meteorclient.events.game;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_2561;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemStackTooltipEvent
/*    */ {
/*    */   private final class_1799 itemStack;
/*    */   private List<class_2561> list;
/*    */   
/*    */   public ItemStackTooltipEvent(class_1799 itemStack, List<class_2561> list) {
/* 19 */     this.itemStack = itemStack;
/* 20 */     this.list = list;
/*    */   }
/*    */   
/*    */   public List<class_2561> list() {
/* 24 */     return this.list;
/*    */   }
/*    */   
/*    */   public class_1799 itemStack() {
/* 28 */     return this.itemStack;
/*    */   }
/*    */   
/*    */   public void appendStart(class_2561 text) {
/* 32 */     copyIfImmutable();
/* 33 */     int index = this.list.isEmpty() ? 0 : 1;
/* 34 */     this.list.add(index, text);
/*    */   }
/*    */   
/*    */   public void appendEnd(class_2561 text) {
/* 38 */     copyIfImmutable();
/* 39 */     this.list.add(text);
/*    */   }
/*    */   
/*    */   public void append(int index, class_2561 text) {
/* 43 */     copyIfImmutable();
/* 44 */     this.list.add(index, text);
/*    */   }
/*    */   
/*    */   public void set(int index, class_2561 text) {
/* 48 */     copyIfImmutable();
/* 49 */     this.list.set(index, text);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void copyIfImmutable() {
/* 55 */     if (List.of().getClass().getSuperclass().isInstance(this.list))
/* 56 */       this.list = (List<class_2561>)new ObjectArrayList(this.list); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\game\ItemStackTooltipEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */