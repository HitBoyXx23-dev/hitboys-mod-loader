package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_10093;
import net.minecraft.class_22;
import net.minecraft.class_9209;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_10093.class})
public interface MapTextureManagerAccessor {
  @Invoker("method_62625")
  class_10093.class_331 meteor$invokeGetMapTexture(class_9209 paramclass_9209, class_22 paramclass_22);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MapTextureManagerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */