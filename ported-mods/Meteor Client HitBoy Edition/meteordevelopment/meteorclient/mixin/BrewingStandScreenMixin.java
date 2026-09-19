/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.AutoBrewer;
/*    */ import net.minecraft.class_1661;
/*    */ import net.minecraft.class_1703;
/*    */ import net.minecraft.class_1708;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_465;
/*    */ import net.minecraft.class_472;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_472.class})
/*    */ public abstract class BrewingStandScreenMixin
/*    */   extends class_465<class_1708>
/*    */ {
/*    */   public BrewingStandScreenMixin(class_1708 container, class_1661 playerInventory, class_2561 name) {
/* 20 */     super((class_1703)container, playerInventory, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_37432() {
/* 25 */     super.method_37432();
/*    */     
/* 27 */     if (Modules.get().isActive(AutoBrewer.class)) ((AutoBrewer)Modules.get().get(AutoBrewer.class)).tick((class_1708)this.field_2797);
/*    */   
/*    */   }
/*    */   
/*    */   public void method_25419() {
/* 32 */     if (Modules.get().isActive(AutoBrewer.class)) ((AutoBrewer)Modules.get().get(AutoBrewer.class)).onBrewingStandClose();
/*    */     
/* 34 */     super.method_25419();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BrewingStandScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */