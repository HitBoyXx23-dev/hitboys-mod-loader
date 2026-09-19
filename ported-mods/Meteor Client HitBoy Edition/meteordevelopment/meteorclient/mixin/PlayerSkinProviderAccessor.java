package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1071;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1071.class})
public interface PlayerSkinProviderAccessor {
  @Accessor("field_45635")
  class_1071.class_8687 meteor$getSkinCache();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\PlayerSkinProviderAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */