/*    */ package meteordevelopment.meteorclient.settings;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Reference2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
/*    */ import net.minecraft.class_1291;
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
/*    */   extends Setting.SettingBuilder<StatusEffectAmplifierMapSetting.Builder, Reference2IntMap<class_1291>, StatusEffectAmplifierMapSetting>
/*    */ {
/*    */   public Builder() {
/* 89 */     super(new Reference2IntOpenHashMap(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public StatusEffectAmplifierMapSetting build() {
/* 94 */     return new StatusEffectAmplifierMapSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\StatusEffectAmplifierMapSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */