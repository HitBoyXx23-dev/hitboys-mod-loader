package meteordevelopment.meteorclient.utils.other;

public interface Container {
  Iterable<Snapper.Element> getElements();
  
  boolean shouldNotSnapTo(Snapper.Element paramElement);
  
  int getSnappingRange();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\other\Snapper$Container.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */