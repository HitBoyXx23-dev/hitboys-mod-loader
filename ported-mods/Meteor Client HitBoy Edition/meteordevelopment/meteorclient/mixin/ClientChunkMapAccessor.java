package meteordevelopment.meteorclient.mixin;

import java.util.concurrent.atomic.AtomicReferenceArray;
import net.minecraft.class_2818;
import net.minecraft.class_631;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_631.class_3681.class})
public interface ClientChunkMapAccessor {
  @Accessor("field_16251")
  AtomicReferenceArray<class_2818> meteor$getChunks();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientChunkMapAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */