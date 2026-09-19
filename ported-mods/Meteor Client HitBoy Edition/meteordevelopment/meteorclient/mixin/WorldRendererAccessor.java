package meteordevelopment.meteorclient.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.class_3191;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_761.class})
public interface WorldRendererAccessor {
  @Accessor("field_4058")
  Int2ObjectMap<class_3191> meteor$getBlockBreakingInfos();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\WorldRendererAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */