/*    */ package meteordevelopment.meteorclient.settings;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.utils.CharFilter;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
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
/*    */   extends Setting.SettingBuilder<StringSetting.Builder, String, StringSetting>
/*    */ {
/*    */   private String placeholder;
/*    */   private Class<? extends WTextBox.Renderer> renderer;
/*    */   private CharFilter filter;
/*    */   private boolean wide;
/*    */   
/*    */   public Builder() {
/* 64 */     super("");
/*    */   }
/*    */   
/*    */   public Builder placeholder(String placeholder) {
/* 68 */     this.placeholder = placeholder;
/* 69 */     return this;
/*    */   }
/*    */   
/*    */   public Builder renderer(Class<? extends WTextBox.Renderer> renderer) {
/* 73 */     this.renderer = renderer;
/* 74 */     return this;
/*    */   }
/*    */   
/*    */   public Builder filter(CharFilter filter) {
/* 78 */     this.filter = filter;
/* 79 */     return this;
/*    */   }
/*    */   
/*    */   public Builder wide() {
/* 83 */     this.wide = true;
/* 84 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public StringSetting build() {
/* 89 */     return new StringSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible, this.placeholder, this.renderer, this.filter, this.wide);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\StringSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */