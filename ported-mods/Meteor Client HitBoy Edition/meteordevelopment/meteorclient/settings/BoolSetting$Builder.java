/*    */ package meteordevelopment.meteorclient.settings;
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
/*    */   extends Setting.SettingBuilder<BoolSetting.Builder, Boolean, BoolSetting>
/*    */ {
/*    */   public Builder() {
/* 54 */     super(Boolean.valueOf(false));
/*    */   }
/*    */ 
/*    */   
/*    */   public BoolSetting build() {
/* 59 */     return new BoolSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\BoolSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */