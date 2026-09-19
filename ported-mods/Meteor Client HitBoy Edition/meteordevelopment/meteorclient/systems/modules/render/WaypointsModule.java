/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*     */ import meteordevelopment.meteorclient.events.render.Render2DEvent;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.screens.EditSystemScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
/*     */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*     */ import meteordevelopment.meteorclient.renderer.text.TextRenderer;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.Settings;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.waypoints.Waypoint;
/*     */ import meteordevelopment.meteorclient.systems.waypoints.Waypoints;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.NametagUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2374;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_5250;
/*     */ import org.joml.Vector3d;
/*     */ import org.joml.Vector3dc;
/*     */ 
/*     */ public class WaypointsModule extends Module {
/*  47 */   private static final Color GRAY = new Color(200, 200, 200);
/*  48 */   private static final Color TEXT = new Color(255, 255, 255);
/*     */   
/*  50 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  51 */   private final SettingGroup sgDeathPosition = this.settings.createGroup("Death Position");
/*     */   
/*  53 */   public final Setting<Integer> textRenderDistance = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  54 */       .name("text-render-distance"))
/*  55 */       .description("Maximum distance from the center of the screen at which text will be rendered."))
/*  56 */       .defaultValue(Integer.valueOf(100)))
/*  57 */       .min(0)
/*  58 */       .sliderMax(200)
/*  59 */       .build());
/*     */ 
/*     */   
/*  62 */   private final Setting<Integer> waypointFadeDistance = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  63 */       .name("waypoint-fade-distance"))
/*  64 */       .description("The distance to a waypoint at which it begins to start fading."))
/*  65 */       .defaultValue(Integer.valueOf(20)))
/*  66 */       .sliderRange(0, 100)
/*  67 */       .min(0)
/*  68 */       .build());
/*     */ 
/*     */   
/*  71 */   private final Setting<Integer> maxDeathPositions = this.sgDeathPosition.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  72 */       .name("max-death-positions"))
/*  73 */       .description("The amount of death positions to save, 0 to disable"))
/*  74 */       .defaultValue(Integer.valueOf(0)))
/*  75 */       .min(0)
/*  76 */       .sliderMax(20)
/*  77 */       .onChanged(this::cleanDeathWPs))
/*  78 */       .build());
/*     */ 
/*     */   
/*  81 */   private final Setting<Boolean> dpChat = this.sgDeathPosition.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  82 */       .name("chat"))
/*  83 */       .description("Send a chat message with your position once you die"))
/*  84 */       .defaultValue(Boolean.valueOf(false)))
/*  85 */       .build());
/*     */   private final SimpleDateFormat dateFormat;
/*     */   
/*     */   public WaypointsModule() {
/*  89 */     super(Categories.Render, "waypoints", "Allows you to create waypoints.");
/*     */ 
/*     */     
/*  92 */     this.dateFormat = new SimpleDateFormat("HH:mm:ss");
/*     */   }
/*     */   @EventHandler
/*     */   private void onRender2D(Render2DEvent event) {
/*  96 */     TextRenderer text = TextRenderer.get();
/*  97 */     Vector3d center = new Vector3d(this.mc.method_22683().method_4489() / 2.0D, this.mc.method_22683().method_4506() / 2.0D, 0.0D);
/*  98 */     int textRenderDist = ((Integer)this.textRenderDistance.get()).intValue();
/*     */     
/* 100 */     List<Waypoint> toRemove = new ArrayList<>();
/* 101 */     for (Waypoint waypoint : Waypoints.get()) {
/*     */       
/* 103 */       if (!((Boolean)waypoint.visible.get()).booleanValue() || !Waypoints.checkDimension(waypoint)) {
/*     */         continue;
/*     */       }
/* 106 */       class_2338 blockPos = waypoint.getPos();
/* 107 */       Vector3d pos = new Vector3d(blockPos.method_10263() + 0.5D, blockPos.method_10264(), blockPos.method_10260() + 0.5D);
/* 108 */       double dist = PlayerUtils.distanceToCamera(pos.x, pos.y, pos.z);
/*     */ 
/*     */ 
/*     */       
/* 112 */       boolean playerAlive = (this.mc.field_1724 != null && !this.mc.field_1724.method_29504());
/* 113 */       boolean waypointIsNear = waypoint.actionWhenNearCheck((int)Math.floor(dist));
/* 114 */       if (playerAlive && waypointIsNear) {
/* 115 */         switch ((Waypoint.NearAction)waypoint.actionWhenNear.get()) { case Hide:
/* 116 */             waypoint.visible.set(Boolean.valueOf(false)); break;
/*     */           case Delete:
/* 118 */             toRemove.add(waypoint);
/*     */             continue; }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 125 */       if (dist > ((Integer)waypoint.maxVisible.get()).intValue() || 
/* 126 */         !NametagUtils.to2D(pos, ((Double)waypoint.scale.get()).doubleValue() - 0.2D)) {
/*     */         continue;
/*     */       }
/* 129 */       double distToCenter = pos.distance((Vector3dc)center);
/* 130 */       double a = 1.0D;
/*     */       
/* 132 */       if (dist < ((Integer)this.waypointFadeDistance.get()).intValue()) {
/* 133 */         a = (dist - ((Integer)this.waypointFadeDistance.get()).intValue() / 2.0D) / ((Integer)this.waypointFadeDistance.get()).intValue() / 2.0D;
/* 134 */         if (a < 0.01D) {
/*     */           continue;
/*     */         }
/*     */       } 
/* 138 */       NametagUtils.begin(pos);
/*     */ 
/*     */       
/* 141 */       waypoint.renderIcon(-16.0D, -16.0D, a, 32.0D);
/*     */ 
/*     */       
/* 144 */       if (distToCenter <= textRenderDist) {
/*     */         
/* 146 */         int preTextA = TEXT.a;
/* 147 */         TEXT.a = (int)(TEXT.a * a);
/* 148 */         text.begin();
/*     */ 
/*     */         
/* 151 */         text.render((String)waypoint.name.get(), -text.getWidth((String)waypoint.name.get()) / 2.0D, -16.0D - text.getHeight(), TEXT, true);
/*     */ 
/*     */         
/* 154 */         String distText = String.format("%d blocks", new Object[] { Integer.valueOf((int)Math.round(dist)) });
/* 155 */         text.render(distText, -text.getWidth(distText) / 2.0D, 16.0D, TEXT, true);
/*     */ 
/*     */         
/* 158 */         text.end();
/* 159 */         TEXT.a = preTextA;
/*     */       } 
/*     */       
/* 162 */       NametagUtils.end();
/*     */     } 
/*     */     
/* 165 */     Waypoints.get().removeAll(toRemove);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onOpenScreen(OpenScreenEvent event) {
/* 170 */     if (!(event.screen instanceof net.minecraft.class_418))
/*     */       return; 
/* 172 */     if (!event.isCancelled()) addDeath(this.mc.field_1724.method_73189()); 
/*     */   }
/*     */   
/*     */   public void addDeath(class_243 deathPos) {
/* 176 */     String time = this.dateFormat.format(new Date());
/* 177 */     if (((Boolean)this.dpChat.get()).booleanValue()) {
/* 178 */       class_5250 text = class_2561.method_43470("Died at ");
/* 179 */       text.method_10852((class_2561)ChatUtils.formatCoords(deathPos));
/* 180 */       text.method_27693(String.format(" on %s.", new Object[] { time }));
/* 181 */       info((class_2561)text);
/*     */     } 
/*     */ 
/*     */     
/* 185 */     if (((Integer)this.maxDeathPositions.get()).intValue() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       Waypoint waypoint = (new Waypoint.Builder()).name("Death " + time).icon("skull").pos(class_2338.method_49638((class_2374)deathPos).method_10086(2)).dimension(PlayerUtils.getDimension()).build();
/*     */ 
/*     */       
/* 194 */       waypoint.actionWhenNear.set(Waypoint.NearAction.Delete);
/* 195 */       waypoint.actionWhenNearDistance.set(Integer.valueOf(4));
/*     */       
/* 197 */       Waypoints.get().add(waypoint);
/*     */     } 
/*     */     
/* 200 */     cleanDeathWPs(((Integer)this.maxDeathPositions.get()).intValue());
/*     */   }
/*     */   
/*     */   private void cleanDeathWPs(int max) {
/* 204 */     int oldWpC = 0;
/*     */     
/* 206 */     List<Waypoint> toRemove = new ArrayList<>();
/* 207 */     for (Waypoint wp : Waypoints.get()) {
/*     */       
/* 209 */       oldWpC++;
/*     */       
/* 211 */       if (((String)wp.name.get()).startsWith("Death ") && ((String)wp.icon.get()).equals("skull") && oldWpC > max) toRemove.add(wp);
/*     */     
/*     */     } 
/*     */     
/* 215 */     Waypoints.get().removeAll(toRemove);
/*     */   }
/*     */ 
/*     */   
/*     */   public WWidget getWidget(GuiTheme theme) {
/* 220 */     if (!Utils.canUpdate()) return (WWidget)theme.label("You need to be in a world.");
/*     */     
/* 222 */     WTable table = theme.table();
/* 223 */     initTable(theme, table);
/* 224 */     return (WWidget)table;
/*     */   }
/*     */   
/*     */   private void initTable(GuiTheme theme, WTable table) {
/* 228 */     table.clear();
/*     */     
/* 230 */     for (Iterator<Waypoint> iterator = Waypoints.get().iterator(); iterator.hasNext(); ) { Waypoint waypoint = iterator.next();
/* 231 */       boolean validDim = Waypoints.checkDimension(waypoint);
/*     */       
/* 233 */       table.add(new WIcon(waypoint));
/*     */       
/* 235 */       WLabel name = (WLabel)table.add((WWidget)theme.label((String)waypoint.name.get())).expandCellX().widget();
/* 236 */       if (!validDim) name.color = GRAY;
/*     */       
/* 238 */       WCheckbox visible = (WCheckbox)table.add((WWidget)theme.checkbox(((Boolean)waypoint.visible.get()).booleanValue())).widget();
/* 239 */       visible.action = (() -> {
/*     */           waypoint.visible.set(Boolean.valueOf(visible.checked));
/*     */           
/*     */           Waypoints.get().save();
/*     */         });
/* 244 */       WButton edit = (WButton)table.add((WWidget)theme.button(GuiRenderer.EDIT)).widget();
/* 245 */       edit.action = (() -> this.mc.method_1507((class_437)new EditWaypointScreen(theme, waypoint, ())));
/*     */ 
/*     */       
/* 248 */       if (validDim) {
/* 249 */         WButton gotoB = (WButton)table.add((WWidget)theme.button("Goto")).widget();
/* 250 */         gotoB.action = (() -> {
/*     */             if (PathManagers.get().isPathing()) {
/*     */               PathManagers.get().stop();
/*     */             }
/*     */             
/*     */             PathManagers.get().moveTo(waypoint.getPos());
/*     */           });
/*     */       } 
/* 258 */       WConfirmedMinus remove = (WConfirmedMinus)table.add((WWidget)theme.confirmedMinus()).widget();
/* 259 */       remove.action = (() -> {
/*     */           Waypoints.get().remove(waypoint);
/*     */           
/*     */           initTable(theme, table);
/*     */         });
/* 264 */       table.row(); }
/*     */ 
/*     */     
/* 267 */     table.add((WWidget)theme.horizontalSeparator()).expandX();
/* 268 */     table.row();
/*     */     
/* 270 */     WButton create = (WButton)table.add((WWidget)theme.button("Create")).expandX().widget();
/* 271 */     create.action = (() -> this.mc.method_1507((class_437)new EditWaypointScreen(theme, null, ())));
/*     */   }
/*     */   
/*     */   private static class EditWaypointScreen extends EditSystemScreen<Waypoint> {
/*     */     public EditWaypointScreen(GuiTheme theme, Waypoint value, Runnable reload) {
/* 276 */       super(theme, value, reload);
/*     */     }
/*     */ 
/*     */     
/*     */     public Waypoint create() {
/* 281 */       return (new Waypoint.Builder())
/* 282 */         .pos((class_310.method_1551()).field_1724.method_24515().method_10086(2))
/* 283 */         .dimension(PlayerUtils.getDimension())
/* 284 */         .build();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean save() {
/* 289 */       if (((String)((Waypoint)this.value).name.get()).isBlank()) return false;
/*     */       
/* 291 */       Waypoints.get().add((Waypoint)this.value);
/* 292 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public Settings getSettings() {
/* 297 */       return ((Waypoint)this.value).settings;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class WIcon extends WWidget {
/*     */     private final Waypoint waypoint;
/*     */     
/*     */     public WIcon(Waypoint waypoint) {
/* 305 */       this.waypoint = waypoint;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onCalculateSize() {
/* 310 */       double s = this.theme.scale(32.0D);
/*     */       
/* 312 */       this.width = s;
/* 313 */       this.height = s;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 318 */       renderer.post(() -> this.waypoint.renderIcon(this.x, this.y, 1.0D, this.width));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\WaypointsModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */