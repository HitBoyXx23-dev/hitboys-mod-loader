/*    */ package meteordevelopment.meteorclient.settings;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.class_2248;
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
/*    */ public class Builder
/*    */   extends Setting.SettingBuilder<BlockSetting.Builder, class_2248, BlockSetting>
/*    */ {
/*    */   private Predicate<class_2248> filter;
/*    */   
/*    */   public Builder() {
/* 67 */     super(null);
/*    */   }
/*    */   
/*    */   public Builder filter(Predicate<class_2248> filter) {
/* 71 */     this.filter = filter;
/* 72 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockSetting build() {
/* 77 */     return new BlockSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible, this.filter);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\BlockSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */