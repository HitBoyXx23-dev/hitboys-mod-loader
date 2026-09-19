package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_11246;
import net.minecraft.class_332;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_332.class})
public interface DrawContextAccessor {
  @Accessor("field_59826")
  class_11246 getState();
  
  @Accessor("field_44659")
  class_332.class_8214 getScissorStack();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\DrawContextAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */