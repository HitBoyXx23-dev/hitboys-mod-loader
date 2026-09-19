package meteordevelopment.meteorclient.mixininterface;

import com.mojang.authlib.GameProfile;

public interface IChatHudLine {
  String meteor$getText();
  
  int meteor$getId();
  
  void meteor$setId(int paramInt);
  
  GameProfile meteor$getSender();
  
  void meteor$setSender(GameProfile paramGameProfile);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixininterface\IChatHudLine.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */