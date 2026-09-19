/*     */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*     */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*     */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WMinus;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPlus;
/*     */ import meteordevelopment.meteorclient.renderer.Texture;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friend;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*     */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ public class FriendsTab
/*     */   extends Tab
/*     */ {
/*     */   public FriendsTab() {
/*  27 */     super("Friends");
/*     */   }
/*     */ 
/*     */   
/*     */   public TabScreen createScreen(GuiTheme theme) {
/*  32 */     return (TabScreen)new FriendsScreen(theme, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isScreen(class_437 screen) {
/*  37 */     return screen instanceof FriendsScreen;
/*     */   }
/*     */   
/*     */   private static class FriendsScreen extends WindowTabScreen {
/*     */     public FriendsScreen(GuiTheme theme, Tab tab) {
/*  42 */       super(theme, tab);
/*     */     }
/*     */ 
/*     */     
/*     */     public void initWidgets() {
/*  47 */       WTable table = (WTable)add((WWidget)this.theme.table()).expandX().minWidth(400.0D).widget();
/*  48 */       initTable(table);
/*     */       
/*  50 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */ 
/*     */       
/*  53 */       WHorizontalList list = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */       
/*  55 */       WTextBox nameW = (WTextBox)list.add((WWidget)this.theme.textBox("", (text, c) -> (c != ' '))).expandX().widget();
/*  56 */       nameW.setFocused(true);
/*     */       
/*  58 */       WPlus add = (WPlus)list.add((WWidget)this.theme.plus()).widget();
/*  59 */       add.action = (() -> {
/*     */           String name = nameW.get().trim();
/*     */ 
/*     */           
/*     */           Friend friend = new Friend(name);
/*     */ 
/*     */           
/*     */           if (Friends.get().add(friend)) {
/*     */             nameW.set("");
/*     */ 
/*     */             
/*     */             initTable(table);
/*     */             
/*     */             nameW.setFocused(true);
/*     */             
/*     */             MeteorExecutor.execute(());
/*     */           } 
/*     */         });
/*     */       
/*  78 */       this.enterAction = add.action;
/*     */     }
/*     */     
/*     */     private void initTable(WTable table) {
/*  82 */       table.clear();
/*  83 */       if (Friends.get().isEmpty())
/*     */         return; 
/*  85 */       Friends.get().forEach(friend -> MeteorExecutor.execute(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  93 */       for (Iterator<Friend> iterator = Friends.get().iterator(); iterator.hasNext(); ) { Friend friend = iterator.next();
/*  94 */         table.add((WWidget)this.theme.texture(32.0D, 32.0D, friend.getHead().needsRotate() ? 90.0D : 0.0D, (Texture)friend.getHead()));
/*  95 */         table.add((WWidget)this.theme.label(friend.getName()));
/*     */         
/*  97 */         WMinus remove = (WMinus)table.add((WWidget)this.theme.minus()).expandCellX().right().widget();
/*  98 */         remove.action = (() -> {
/*     */             Friends.get().remove(friend);
/*     */             
/*     */             initTable(table);
/*     */           });
/* 103 */         table.row(); }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean toClipboard() {
/* 109 */       return NbtUtils.toClipboard((ISerializable)Friends.get());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean fromClipboard() {
/* 114 */       return NbtUtils.fromClipboard((ISerializable)Friends.get());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\FriendsTab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */