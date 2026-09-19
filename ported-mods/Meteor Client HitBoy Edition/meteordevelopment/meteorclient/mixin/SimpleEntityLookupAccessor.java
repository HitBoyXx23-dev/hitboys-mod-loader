package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_5573;
import net.minecraft.class_5578;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_5578.class})
public interface SimpleEntityLookupAccessor {
  @Accessor("field_27259")
  <T extends net.minecraft.class_5568> class_5573<T> meteor$getCache();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\SimpleEntityLookupAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */