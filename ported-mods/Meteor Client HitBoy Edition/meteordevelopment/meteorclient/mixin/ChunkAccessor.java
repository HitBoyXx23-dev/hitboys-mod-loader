package meteordevelopment.meteorclient.mixin;

import java.util.Map;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2791;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2791.class})
public interface ChunkAccessor {
  @Accessor("field_34543")
  Map<class_2338, class_2586> getBlockEntities();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChunkAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */