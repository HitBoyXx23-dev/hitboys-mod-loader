package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_5577;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_1937.class})
public interface WorldAccessor {
  @Invoker("method_31592")
  class_5577<class_1297> meteor$getEntityLookup();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\WorldAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */