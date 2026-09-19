package meteordevelopment.meteorclient.pathing;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.Settings;

public interface ISettings {
  Settings get();
  
  Setting<Boolean> getWalkOnWater();
  
  Setting<Boolean> getWalkOnLava();
  
  Setting<Boolean> getStep();
  
  Setting<Boolean> getNoFall();
  
  void save();
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\pathing\IPathManager$ISettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */