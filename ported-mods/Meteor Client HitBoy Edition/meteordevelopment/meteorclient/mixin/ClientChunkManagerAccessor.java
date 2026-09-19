package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_631;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_631.class})
public interface ClientChunkManagerAccessor {
  @Accessor("field_16246")
  class_631.class_3681 meteor$getChunks();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientChunkManagerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */