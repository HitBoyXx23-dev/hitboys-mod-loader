package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1299;
import net.minecraft.class_1785;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1785.class})
public interface EntityBucketItemAccessor {
  @Accessor("field_7991")
  class_1299<?> meteor$getEntityType();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EntityBucketItemAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */