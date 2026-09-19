package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_11278;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_11278.class})
public interface ProjectionMatrix2Accessor {
  @Invoker("method_71094")
  Matrix4f meteor$callGetMatrix(float paramFloat1, float paramFloat2);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ProjectionMatrix2Accessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */