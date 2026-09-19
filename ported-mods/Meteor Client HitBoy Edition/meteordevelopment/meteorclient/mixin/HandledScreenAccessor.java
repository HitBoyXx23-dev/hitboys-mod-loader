package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1735;
import net.minecraft.class_465;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_465.class})
public interface HandledScreenAccessor {
  @Accessor("field_2787")
  class_1735 meteor$getFocusedSlot();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\HandledScreenAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */