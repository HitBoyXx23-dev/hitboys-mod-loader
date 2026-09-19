package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1263;
import net.minecraft.class_1733;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1733.class})
public interface ShulkerBoxScreenHandlerAccessor {
  @Accessor("field_7867")
  class_1263 meteor$getInventory();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ShulkerBoxScreenHandlerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */