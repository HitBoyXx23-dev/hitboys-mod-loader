/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.class_304;
/*    */ import net.minecraft.class_3675;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ import org.spongepowered.asm.mixin.gen.Invoker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_304.class})
/*    */ public interface KeyBindingAccessor
/*    */ {
/*    */   @Accessor("field_1657")
/*    */   static Map<String, class_304> getKeysById() {
/* 19 */     return null;
/*    */   }
/*    */   
/*    */   @Accessor("field_1655")
/*    */   class_3675.class_306 meteor$getKey();
/*    */   
/*    */   @Accessor("field_1661")
/*    */   int meteor$getTimesPressed();
/*    */   
/*    */   @Accessor("field_1661")
/*    */   void meteor$setTimesPressed(int paramInt);
/*    */   
/*    */   @Invoker("method_1425")
/*    */   void meteor$invokeReset();
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\KeyBindingAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */