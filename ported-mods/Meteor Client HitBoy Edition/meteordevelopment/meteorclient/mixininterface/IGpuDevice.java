package meteordevelopment.meteorclient.mixininterface;

import com.mojang.blaze3d.systems.RenderPass;

public interface IGpuDevice {
  void meteor$pushScissor(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void meteor$popScissor();
  
  @Deprecated
  void meteor$onCreateRenderPass(RenderPass paramRenderPass);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixininterface\IGpuDevice.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */