/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WMinus;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.IntSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.settings.StringSetting;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
/*    */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerManager;
/*    */ 
/*    */ public class FakePlayer extends Module {
/* 20 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 22 */   public final Setting<String> name = this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)(new StringSetting.Builder())
/* 23 */       .name("name"))
/* 24 */       .description("The name of the fake player."))
/* 25 */       .defaultValue("seasnail8169"))
/* 26 */       .build());
/*    */ 
/*    */   
/* 29 */   public final Setting<Boolean> copyInv = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 30 */       .name("copy-inv"))
/* 31 */       .description("Copies your inventory to the fake player."))
/* 32 */       .defaultValue(Boolean.valueOf(true)))
/* 33 */       .build());
/*    */ 
/*    */   
/* 36 */   public final Setting<Integer> health = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 37 */       .name("health"))
/* 38 */       .description("The fake player's default health."))
/* 39 */       .defaultValue(Integer.valueOf(20)))
/* 40 */       .min(1)
/* 41 */       .sliderRange(1, 100)
/* 42 */       .build());
/*    */   
/*    */   private WTable table;
/*    */ 
/*    */   
/*    */   public FakePlayer() {
/* 48 */     super(Categories.Player, "fake-player", "Spawns a client-side fake player for testing usages. No need to be active.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDeactivate() {
/* 53 */     FakePlayerManager.clear();
/* 54 */     if (this.table != null) this.table.clear();
/*    */   
/*    */   }
/*    */   
/*    */   public WWidget getWidget(GuiTheme theme) {
/* 59 */     this.table = theme.table();
/* 60 */     fillTable(theme, this.table);
/*    */     
/* 62 */     return (WWidget)this.table;
/*    */   }
/*    */   
/*    */   private void fillTable(GuiTheme theme, WTable table) {
/* 66 */     for (Iterator<FakePlayerEntity> iterator = FakePlayerManager.getFakePlayers().iterator(); iterator.hasNext(); ) { FakePlayerEntity fakePlayer = iterator.next();
/* 67 */       table.add((WWidget)theme.label(fakePlayer.method_5477().getString()));
/* 68 */       WMinus delete = (WMinus)table.add((WWidget)theme.minus()).expandCellX().right().widget();
/* 69 */       delete.action = (() -> {
/*    */           FakePlayerManager.remove(fakePlayer);
/*    */           table.clear();
/*    */           fillTable(theme, table);
/*    */         });
/* 74 */       table.row(); }
/*    */ 
/*    */     
/* 77 */     WButton spawn = (WButton)table.add((WWidget)theme.button("Spawn")).expandCellX().right().widget();
/* 78 */     spawn.action = (() -> {
/*    */         FakePlayerManager.add((String)this.name.get(), ((Integer)this.health.get()).intValue(), ((Boolean)this.copyInv.get()).booleanValue());
/*    */         
/*    */         table.clear();
/*    */         fillTable(theme, table);
/*    */       });
/* 84 */     WButton clear = (WButton)table.add((WWidget)theme.button("Clear All")).right().widget();
/* 85 */     clear.action = (() -> {
/*    */         FakePlayerManager.clear();
/*    */         table.clear();
/*    */         fillTable(theme, table);
/*    */       });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getInfoString() {
/* 94 */     return String.valueOf(FakePlayerManager.count());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\FakePlayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */