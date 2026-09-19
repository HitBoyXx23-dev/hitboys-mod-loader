package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_2625;
import net.minecraft.class_7743;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_7743.class})
public interface AbstractSignEditScreenAccessor {
  @Accessor("field_40424")
  class_2625 meteor$getSign();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\AbstractSignEditScreenAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */