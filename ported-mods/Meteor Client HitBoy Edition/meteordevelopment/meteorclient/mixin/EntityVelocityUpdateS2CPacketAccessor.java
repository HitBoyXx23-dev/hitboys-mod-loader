package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_243;
import net.minecraft.class_2743;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2743.class})
public interface EntityVelocityUpdateS2CPacketAccessor {
  @Mutable
  @Accessor("field_61887")
  void meteor$setVelocity(class_243 paramclass_243);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EntityVelocityUpdateS2CPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */