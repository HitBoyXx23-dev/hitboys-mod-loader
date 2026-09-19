package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_2338;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_636.class})
public interface ClientPlayerInteractionManagerAccessor {
  @Accessor("field_3715")
  float meteor$getBreakingProgress();
  
  @Accessor("field_3715")
  void meteor$setCurrentBreakingProgress(float paramFloat);
  
  @Accessor("field_3714")
  class_2338 meteor$getCurrentBreakingBlockPos();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientPlayerInteractionManagerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */