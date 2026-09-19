package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_3509;
import net.minecraft.class_5572;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_5572.class})
public interface EntityTrackingSectionAccessor {
  @Accessor("field_27248")
  <T> class_3509<T> meteor$getCollection();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EntityTrackingSectionAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */