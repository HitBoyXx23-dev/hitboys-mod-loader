/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.world.BlockActivateEvent;
/*    */ import net.minecraft.class_1269;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_2769;
/*    */ import net.minecraft.class_3965;
/*    */ import net.minecraft.class_4970;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_2680.class})
/*    */ public abstract class BlockStateMixin
/*    */   extends class_4970.class_4971
/*    */ {
/*    */   public BlockStateMixin(class_2248 block, Reference2ObjectArrayMap<class_2769<?>, Comparable<?>> propertyMap, MapCodec<class_2680> mapCodec) {
/* 25 */     super(block, propertyMap, mapCodec);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_1269 method_55781(class_1937 world, class_1657 player, class_3965 hit) {
/* 30 */     MeteorClient.EVENT_BUS.post(BlockActivateEvent.get((class_2680)this));
/* 31 */     return super.method_55781(world, player, hit);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockStateMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */