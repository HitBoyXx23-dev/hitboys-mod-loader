package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_746.class})
public interface ClientPlayerEntityAccessor {
  @Accessor("field_3923")
  void meteor$setTicksSinceLastPositionPacketSent(int paramInt);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientPlayerEntityAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */