/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import meteordevelopment.meteorclient.mixininterface.ISimpleOption;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_7172;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_7172.class})
/*    */ public abstract class SimpleOptionMixin
/*    */   implements ISimpleOption
/*    */ {
/*    */   @Shadow
/*    */   Object field_37868;
/*    */   @Shadow
/*    */   @Final
/*    */   private Consumer<Object> field_37867;
/*    */   
/*    */   public void meteor$set(Object value) {
/* 25 */     if (!class_310.method_1551().method_22108()) {
/* 26 */       this.field_37868 = value;
/*    */     }
/* 28 */     else if (!Objects.equals(this.field_37868, value)) {
/* 29 */       this.field_37868 = value;
/* 30 */       this.field_37867.accept(this.field_37868);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\SimpleOptionMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */