package meteordevelopment.meteorclient.mixin;

import java.nio.file.Path;
import net.minecraft.class_1071;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1071.class_8687.class})
public interface FileCacheAccessor {
  @Accessor("field_45640")
  Path meteor$getDirectory();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\FileCacheAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */