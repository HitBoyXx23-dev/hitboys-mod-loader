package meteordevelopment.meteorclient.mixin;

import java.util.List;
import net.minecraft.class_303;
import net.minecraft.class_338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_338.class})
public interface ChatHudAccessor {
  @Accessor("field_2064")
  List<class_303.class_7590> meteor$getVisibleMessages();
  
  @Accessor("field_2061")
  List<class_303> meteor$getMessages();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChatHudAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */