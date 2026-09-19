package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1799;
import net.minecraft.class_2371;
import net.minecraft.class_9288;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_9288.class})
public interface ContainerComponentAccessor {
  @Accessor("field_49338")
  class_2371<class_1799> meteor$getStacks();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ContainerComponentAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */