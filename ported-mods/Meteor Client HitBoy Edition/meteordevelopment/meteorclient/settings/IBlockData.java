package meteordevelopment.meteorclient.settings;

import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.WidgetScreen;
import net.minecraft.class_2248;

public interface IBlockData<T extends meteordevelopment.meteorclient.utils.misc.ICopyable<T> & meteordevelopment.meteorclient.utils.misc.ISerializable<T> & meteordevelopment.meteorclient.utils.misc.IChangeable & IBlockData<T>> {
  WidgetScreen createScreen(GuiTheme paramGuiTheme, class_2248 paramclass_2248, BlockDataSetting<T> paramBlockDataSetting);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\IBlockData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */