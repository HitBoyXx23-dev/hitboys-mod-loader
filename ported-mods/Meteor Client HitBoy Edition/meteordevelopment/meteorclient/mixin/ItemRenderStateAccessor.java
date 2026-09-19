package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_10444;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_10444.class})
public interface ItemRenderStateAccessor {
  @Accessor("field_55339")
  int meteor$getLayerCount();
  
  @Accessor("field_55340")
  class_10444.class_10446[] meteor$getLayers();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ItemRenderStateAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */