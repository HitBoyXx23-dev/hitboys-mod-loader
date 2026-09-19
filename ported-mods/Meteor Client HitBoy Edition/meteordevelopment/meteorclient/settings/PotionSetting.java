/*    */ package meteordevelopment.meteorclient.settings;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import meteordevelopment.meteorclient.utils.misc.MyPotion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PotionSetting
/*    */   extends EnumSetting<MyPotion>
/*    */ {
/*    */   public PotionSetting(String name, String description, MyPotion defaultValue, Consumer<MyPotion> onChanged, Consumer<Setting<MyPotion>> onModuleActivated, IVisible visible) {
/* 14 */     super(name, description, defaultValue, onChanged, onModuleActivated, visible);
/*    */   }
/*    */   
/*    */   public static class Builder
/*    */     extends EnumSetting.Builder<MyPotion> {
/*    */     public EnumSetting<MyPotion> build() {
/* 20 */       return new PotionSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\PotionSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */