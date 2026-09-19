package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_640;
import net.minecraft.class_742;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_742.class})
public interface AbstractClientPlayerEntityAccessor {
  @Accessor("field_3901")
  void meteor$setPlayerListEntry(class_640 paramclass_640);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\AbstractClientPlayerEntityAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */