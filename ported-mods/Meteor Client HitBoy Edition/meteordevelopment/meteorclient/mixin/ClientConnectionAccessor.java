package meteordevelopment.meteorclient.mixin;

import io.netty.channel.Channel;
import net.minecraft.class_2535;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2535.class})
public interface ClientConnectionAccessor {
  @Accessor("field_11651")
  Channel meteor$getChannel();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientConnectionAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */