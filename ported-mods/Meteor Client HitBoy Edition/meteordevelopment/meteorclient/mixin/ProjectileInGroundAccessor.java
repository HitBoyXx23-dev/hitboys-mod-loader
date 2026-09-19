package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_1665;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_1665.class})
public interface ProjectileInGroundAccessor {
  @Invoker("method_65059")
  boolean meteor$invokeIsInGround();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ProjectileInGroundAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */