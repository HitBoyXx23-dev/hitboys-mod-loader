package meteordevelopment.meteorclient.mixin;

import meteordevelopment.meteorclient.mixininterface.IText;
import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({class_2561.class})
public interface TextMixin extends IText {
  default void meteor$invalidateCache() {}
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TextMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */