package meteordevelopment.meteorclient.utils.misc;

import net.minecraft.class_2487;

public interface ISerializable<T> {
  class_2487 toTag();
  
  T fromTag(class_2487 paramclass_2487);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\ISerializable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */