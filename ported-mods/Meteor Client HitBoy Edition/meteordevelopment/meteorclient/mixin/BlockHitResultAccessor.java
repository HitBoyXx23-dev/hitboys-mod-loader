package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_2350;
import net.minecraft.class_3965;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_3965.class})
public interface BlockHitResultAccessor {
  @Mutable
  @Accessor("field_17588")
  void meteor$setSide(class_2350 paramclass_2350);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockHitResultAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */