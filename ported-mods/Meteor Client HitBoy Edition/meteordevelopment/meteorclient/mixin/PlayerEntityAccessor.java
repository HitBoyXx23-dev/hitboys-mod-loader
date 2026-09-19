package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1657;
import net.minecraft.class_4050;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_1657.class})
public interface PlayerEntityAccessor {
  @Invoker("method_52558")
  boolean meteor$canChangeIntoPose(class_4050 paramclass_4050);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\PlayerEntityAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */