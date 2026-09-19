package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_6360;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_6360.class})
public interface ResourceReloadLoggerAccessor {
  @Accessor("field_33699")
  class_6360.class_6363 meteor$getReloadState();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ResourceReloadLoggerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */