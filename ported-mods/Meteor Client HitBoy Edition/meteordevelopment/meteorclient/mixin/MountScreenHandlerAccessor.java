package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_12343;
import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_12343.class})
public interface MountScreenHandlerAccessor {
  @Accessor("field_64486")
  class_1309 meteor$getMount();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MountScreenHandlerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */