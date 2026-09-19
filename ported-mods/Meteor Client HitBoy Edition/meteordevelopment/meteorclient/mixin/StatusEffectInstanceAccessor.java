package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1293;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1293.class})
public interface StatusEffectInstanceAccessor {
  @Accessor("field_5895")
  void meteor$setDuration(int paramInt);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\StatusEffectInstanceAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */