/*    */ package meteordevelopment.meteorclient.settings;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProvidedStringSetting
/*    */   extends StringSetting
/*    */ {
/*    */   public final Supplier<String[]> supplier;
/*    */   
/*    */   public ProvidedStringSetting(String name, String description, String defaultValue, Consumer<String> onChanged, Consumer<Setting<String>> onModuleActivated, IVisible visible, Class<? extends WTextBox.Renderer> renderer, boolean wide, Supplier<String[]> supplier) {
/* 17 */     super(name, description, defaultValue, onChanged, onModuleActivated, visible, "", renderer, null, wide);
/*    */     
/* 19 */     this.supplier = supplier;
/*    */   }
/*    */   
/*    */   public static class Builder extends Setting.SettingBuilder<Builder, String, ProvidedStringSetting> {
/*    */     private Class<? extends WTextBox.Renderer> renderer;
/*    */     private Supplier<String[]> supplier;
/*    */     private boolean wide;
/*    */     
/*    */     public Builder() {
/* 28 */       super(null);
/*    */     }
/*    */     
/*    */     public Builder renderer(Class<? extends WTextBox.Renderer> renderer) {
/* 32 */       this.renderer = renderer;
/* 33 */       return this;
/*    */     }
/*    */     
/*    */     public Builder supplier(Supplier<String[]> supplier) {
/* 37 */       this.supplier = supplier;
/* 38 */       return this;
/*    */     }
/*    */     
/*    */     public Builder wide() {
/* 42 */       this.wide = true;
/* 43 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public ProvidedStringSetting build() {
/* 48 */       return new ProvidedStringSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible, this.renderer, this.wide, this.supplier);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\ProvidedStringSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */