package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1536;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1536.class})
public interface FishingBobberEntityAccessor {
  @Accessor("field_23232")
  boolean meteor$hasCaughtFish();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\FishingBobberEntityAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */