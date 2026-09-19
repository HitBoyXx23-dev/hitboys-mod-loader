package meteordevelopment.meteorclient.systems.modules.world;

interface IBlockPosProvider {
  HighwayBuilder.MBPIterator getFront();
  
  HighwayBuilder.MBPIterator getFloor();
  
  HighwayBuilder.MBPIterator getRailings(int paramInt);
  
  HighwayBuilder.MBPIterator getLiquids();
  
  HighwayBuilder.MBPIterator getBlockade(boolean paramBoolean, HighwayBuilder.BlockadeType paramBlockadeType);
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\HighwayBuilder$IBlockPosProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */