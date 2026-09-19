package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_10444;
import net.minecraft.class_804;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_10444.class_10446.class})
public interface LayerRenderStateAccessor {
  @Accessor("field_56967")
  class_804 meteor$getTransform();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\LayerRenderStateAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */