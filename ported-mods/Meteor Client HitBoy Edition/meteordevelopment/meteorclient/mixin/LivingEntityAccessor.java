package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1309;
import net.minecraft.class_3611;
import net.minecraft.class_6862;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_1309.class})
public interface LivingEntityAccessor {
  @Invoker("method_6010")
  void meteor$swimUpwards(class_6862<class_3611> paramclass_6862);
  
  @Accessor("field_6282")
  boolean meteor$isJumping();
  
  @Accessor("field_6228")
  int meteor$getJumpCooldown();
  
  @Accessor("field_6228")
  void meteor$setJumpCooldown(int paramInt);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\LivingEntityAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */