package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_11701;
import net.minecraft.class_824;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_824.class})
public interface BlockEntityRenderManagerAccessor {
  @Accessor("field_61783")
  class_11701 getSpriteHolder();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockEntityRenderManagerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */