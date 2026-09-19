/*    */ package meteordevelopment.meteorclient.settings;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import meteordevelopment.meteorclient.utils.misc.IChangeable;
/*    */ import meteordevelopment.meteorclient.utils.misc.ICopyable;
/*    */ import meteordevelopment.meteorclient.utils.misc.IGetter;
/*    */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
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
/*    */ public class Builder<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>>
/*    */   extends Setting.SettingBuilder<BlockDataSetting.Builder<T>, Map<class_2248, T>, BlockDataSetting<T>>
/*    */ {
/*    */   private IGetter<T> defaultData;
/*    */   
/*    */   public Builder() {
/* 72 */     super(new HashMap<>(0));
/*    */   }
/*    */   
/*    */   public Builder<T> defaultData(IGetter<T> defaultData) {
/* 76 */     this.defaultData = defaultData;
/* 77 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockDataSetting<T> build() {
/* 82 */     return new BlockDataSetting<>(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.defaultData, this.visible);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\BlockDataSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */