/*     */ package meteordevelopment.meteorclient.systems.hud.elements;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*     */ import meteordevelopment.meteorclient.systems.hud.Alignment;
/*     */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudRenderer;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_742;
/*     */ 
/*     */ public class PlayerRadarHud extends HudElement {
/*  24 */   public static final HudElementInfo<PlayerRadarHud> INFO = new HudElementInfo(Hud.GROUP, "player-radar", "Displays players in your visual range.", PlayerRadarHud::new);
/*     */   
/*  26 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  27 */   private final SettingGroup sgScale = this.settings.createGroup("Scale");
/*  28 */   private final SettingGroup sgBackground = this.settings.createGroup("Background");
/*     */ 
/*     */ 
/*     */   
/*  32 */   private final Setting<Integer> limit = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  33 */       .name("limit"))
/*  34 */       .description("The max number of players to show."))
/*  35 */       .defaultValue(Integer.valueOf(10)))
/*  36 */       .min(1)
/*  37 */       .sliderRange(1, 20)
/*  38 */       .build());
/*     */ 
/*     */   
/*  41 */   private final Setting<Boolean> distance = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  42 */       .name("distance"))
/*  43 */       .description("Shows the distance to the player next to their name."))
/*  44 */       .defaultValue(Boolean.valueOf(false)))
/*  45 */       .build());
/*     */ 
/*     */   
/*  48 */   private final Setting<Boolean> friends = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  49 */       .name("display-friends"))
/*  50 */       .description("Whether to show friends or not."))
/*  51 */       .defaultValue(Boolean.valueOf(true)))
/*  52 */       .build());
/*     */ 
/*     */   
/*  55 */   private final Setting<Boolean> shadow = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  56 */       .name("shadow"))
/*  57 */       .description("Renders shadow behind text."))
/*  58 */       .defaultValue(Boolean.valueOf(true)))
/*  59 */       .build());
/*     */ 
/*     */   
/*  62 */   private final Setting<SettingColor> primaryColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  63 */       .name("primary-color"))
/*  64 */       .description("Primary color."))
/*  65 */       .defaultValue(new SettingColor())
/*  66 */       .build());
/*     */ 
/*     */   
/*  69 */   private final Setting<SettingColor> secondaryColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  70 */       .name("secondary-color"))
/*  71 */       .description("Secondary color."))
/*  72 */       .defaultValue(new SettingColor(175, 175, 175))
/*  73 */       .build());
/*     */ 
/*     */   
/*  76 */   private final Setting<Alignment> alignment = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  77 */       .name("alignment"))
/*  78 */       .description("Horizontal alignment."))
/*  79 */       .defaultValue(Alignment.Auto))
/*  80 */       .build());
/*     */ 
/*     */   
/*  83 */   private final Setting<Integer> border = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  84 */       .name("border"))
/*  85 */       .description("How much space to add around the element."))
/*  86 */       .defaultValue(Integer.valueOf(0)))
/*  87 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private final Setting<Boolean> customScale = this.sgScale.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  93 */       .name("custom-scale"))
/*  94 */       .description("Applies a custom scale to this hud element."))
/*  95 */       .defaultValue(Boolean.valueOf(false)))
/*  96 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> scale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> background;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> backgroundColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<class_742> players;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerRadarHud() {
/* 129 */     super(INFO); Objects.requireNonNull(this.customScale);
/*     */     this.scale = this.sgScale.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("scale")).description("Custom scale.")).visible(this.customScale::get)).defaultValue(1.0D).min(0.5D).sliderRange(0.5D, 3.0D).build());
/*     */     this.background = this.sgBackground.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("background")).description("Displays background.")).defaultValue(Boolean.valueOf(false))).build());
/*     */     Objects.requireNonNull(this.background);
/*     */     this.backgroundColor = this.sgBackground.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("background-color")).description("Color used for the background.")).visible(this.background::get)).defaultValue(new SettingColor(25, 25, 25, 50)).build());
/* 134 */     this.players = new ArrayList<>(); } public void setSize(double width, double height) { super.setSize(width + (((Integer)this.border.get()).intValue() * 2), height + (((Integer)this.border.get()).intValue() * 2)); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double alignX(double width, Alignment alignment) {
/* 139 */     return this.box.alignX((getWidth() - ((Integer)this.border.get()).intValue() * 2), width, alignment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(HudRenderer renderer) {
/* 144 */     double width = renderer.textWidth("Players:", ((Boolean)this.shadow.get()).booleanValue(), getScale());
/* 145 */     double height = renderer.textHeight(((Boolean)this.shadow.get()).booleanValue(), getScale());
/*     */     
/* 147 */     if (MeteorClient.mc.field_1687 == null) {
/* 148 */       setSize(width, height);
/*     */       
/*     */       return;
/*     */     } 
/* 152 */     for (class_1657 entity : getPlayers()) {
/* 153 */       if (entity.equals(MeteorClient.mc.field_1724) || (
/* 154 */         !((Boolean)this.friends.get()).booleanValue() && Friends.get().isFriend(entity)))
/*     */         continue; 
/* 156 */       String text = entity.method_5477().getString();
/* 157 */       if (((Boolean)this.distance.get()).booleanValue()) text = text + text;
/*     */       
/* 159 */       width = Math.max(width, renderer.textWidth(text, ((Boolean)this.shadow.get()).booleanValue(), getScale()));
/* 160 */       height += renderer.textHeight(((Boolean)this.shadow.get()).booleanValue(), getScale()) + 2.0D;
/*     */     } 
/*     */     
/* 163 */     setSize(width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(HudRenderer renderer) {
/* 168 */     double y = (this.y + ((Integer)this.border.get()).intValue());
/*     */     
/* 170 */     if (((Boolean)this.background.get()).booleanValue()) {
/* 171 */       renderer.quad(this.x, this.y, getWidth(), getHeight(), (Color)this.backgroundColor.get());
/*     */     }
/*     */     
/* 174 */     renderer.text("Players:", (this.x + ((Integer)this.border.get()).intValue()) + alignX(renderer.textWidth("Players:", ((Boolean)this.shadow.get()).booleanValue(), getScale()), (Alignment)this.alignment.get()), y, (Color)this.secondaryColor.get(), ((Boolean)this.shadow.get()).booleanValue(), getScale());
/*     */     
/* 176 */     if (MeteorClient.mc.field_1687 == null)
/* 177 */       return;  double spaceWidth = renderer.textWidth(" ", ((Boolean)this.shadow.get()).booleanValue(), getScale());
/*     */     
/* 179 */     for (class_1657 entity : getPlayers()) {
/* 180 */       if (entity.equals(MeteorClient.mc.field_1724) || (
/* 181 */         !((Boolean)this.friends.get()).booleanValue() && Friends.get().isFriend(entity)))
/*     */         continue; 
/* 183 */       String text = entity.method_5477().getString();
/* 184 */       Color color = PlayerUtils.getPlayerColor(entity, (Color)this.primaryColor.get());
/* 185 */       String distanceText = null;
/*     */       
/* 187 */       double width = renderer.textWidth(text, ((Boolean)this.shadow.get()).booleanValue(), getScale());
/* 188 */       if (((Boolean)this.distance.get()).booleanValue()) width += spaceWidth;
/*     */       
/* 190 */       if (((Boolean)this.distance.get()).booleanValue()) {
/* 191 */         distanceText = String.format("(%sm)", new Object[] { Integer.valueOf(Math.round(MeteorClient.mc.method_1560().method_5739((class_1297)entity))) });
/* 192 */         width += renderer.textWidth(distanceText, ((Boolean)this.shadow.get()).booleanValue(), getScale());
/*     */       } 
/*     */       
/* 195 */       double x = (this.x + ((Integer)this.border.get()).intValue()) + alignX(width, (Alignment)this.alignment.get());
/* 196 */       y += renderer.textHeight(((Boolean)this.shadow.get()).booleanValue(), getScale()) + 2.0D;
/*     */       
/* 198 */       x = renderer.text(text, x, y, color, ((Boolean)this.shadow.get()).booleanValue());
/* 199 */       if (((Boolean)this.distance.get()).booleanValue()) renderer.text(distanceText, x + spaceWidth, y, (Color)this.secondaryColor.get(), ((Boolean)this.shadow.get()).booleanValue(), getScale()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<class_742> getPlayers() {
/* 204 */     this.players.clear();
/* 205 */     this.players.addAll(MeteorClient.mc.field_1687.method_18456());
/* 206 */     if (this.players.size() > ((Integer)this.limit.get()).intValue()) this.players.subList(((Integer)this.limit.get()).intValue() - 1, this.players.size() - 1).clear(); 
/* 207 */     this.players.sort(Comparator.comparingDouble(e -> e.method_5858(MeteorClient.mc.method_1560())));
/*     */     
/* 209 */     return this.players;
/*     */   }
/*     */   
/*     */   private double getScale() {
/* 213 */     return ((Boolean)this.customScale.get()).booleanValue() ? ((Double)this.scale.get()).doubleValue() : Hud.get().getTextScale();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\elements\PlayerRadarHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */