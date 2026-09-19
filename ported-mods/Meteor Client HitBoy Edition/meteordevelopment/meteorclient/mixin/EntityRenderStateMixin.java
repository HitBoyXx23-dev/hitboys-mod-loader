/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.IEntityRenderState;
/*    */ import net.minecraft.class_10017;
/*    */ import net.minecraft.class_1297;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_10017.class})
/*    */ public abstract class EntityRenderStateMixin
/*    */   implements IEntityRenderState
/*    */ {
/*    */   @Unique
/*    */   private class_1297 entity;
/*    */   
/*    */   @Nullable("EntityCulling mod can prevent the code that sets the entity from running")
/*    */   public class_1297 meteor$getEntity() {
/* 23 */     return this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setEntity(class_1297 entity) {
/* 28 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EntityRenderStateMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */