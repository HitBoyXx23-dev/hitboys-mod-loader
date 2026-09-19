package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_2828;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2828.class})
public interface PlayerMoveC2SPacketAccessor {
  @Mutable
  @Accessor("field_12886")
  void meteor$setY(double paramDouble);
  
  @Mutable
  @Accessor("field_29179")
  void meteor$setOnGround(boolean paramBoolean);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\PlayerMoveC2SPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */