/*    */ package meteordevelopment.meteorclient.settings;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.class_1792;
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
/*    */   extends Setting.SettingBuilder<ItemSetting.Builder, class_1792, ItemSetting>
/*    */ {
/*    */   private Predicate<class_1792> filter;
/*    */   
/*    */   public Builder() {
/* 67 */     super(null);
/*    */   }
/*    */   
/*    */   public Builder filter(Predicate<class_1792> filter) {
/* 71 */     this.filter = filter;
/* 72 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemSetting build() {
/* 77 */     return new ItemSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible, this.filter);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\ItemSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */