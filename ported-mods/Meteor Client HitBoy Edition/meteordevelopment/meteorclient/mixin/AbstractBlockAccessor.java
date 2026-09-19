package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_4970;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_4970.class})
public interface AbstractBlockAccessor {
  @Accessor("field_23159")
  boolean meteor$isCollidable();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\AbstractBlockAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */