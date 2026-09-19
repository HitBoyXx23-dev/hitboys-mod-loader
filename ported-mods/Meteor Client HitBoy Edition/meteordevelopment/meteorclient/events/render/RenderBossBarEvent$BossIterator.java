/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.class_345;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BossIterator
/*    */ {
/* 39 */   private static final BossIterator INSTANCE = new BossIterator();
/*    */   
/*    */   public Iterator<class_345> iterator;
/*    */   
/*    */   public static BossIterator get(Iterator<class_345> iterator) {
/* 44 */     INSTANCE.iterator = iterator;
/* 45 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\RenderBossBarEvent$BossIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */