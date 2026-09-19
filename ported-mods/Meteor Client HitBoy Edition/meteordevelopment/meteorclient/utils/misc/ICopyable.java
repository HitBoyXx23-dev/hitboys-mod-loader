package meteordevelopment.meteorclient.utils.misc;

public interface ICopyable<T extends ICopyable<T>> {
  T set(T paramT);
  
  T copy();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\ICopyable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */