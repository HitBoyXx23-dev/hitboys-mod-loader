package meteordevelopment.meteorclient.mixin;

import net.minecraft.class_5225;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_5225.class})
public interface TextHandlerAccessor {
  @Accessor("field_24216")
  class_5225.class_5231 meteor$getWidthRetriever();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TextHandlerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */