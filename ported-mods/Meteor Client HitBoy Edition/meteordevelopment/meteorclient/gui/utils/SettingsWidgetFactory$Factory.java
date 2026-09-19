package meteordevelopment.meteorclient.gui.utils;

import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
import meteordevelopment.meteorclient.settings.Setting;

@FunctionalInterface
public interface Factory {
  void create(WTable paramWTable, Setting<?> paramSetting);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gu\\utils\SettingsWidgetFactory$Factory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */