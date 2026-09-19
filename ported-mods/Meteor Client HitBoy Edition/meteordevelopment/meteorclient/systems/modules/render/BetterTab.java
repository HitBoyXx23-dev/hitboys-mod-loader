/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friend;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1934;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5251;
/*     */ import net.minecraft.class_640;
/*     */ 
/*     */ public class BetterTab
/*     */   extends Module {
/*  25 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  27 */   public final Setting<Integer> tabSize = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  28 */       .name("tablist-size"))
/*  29 */       .description("How many players in total to display in the tablist."))
/*  30 */       .defaultValue(Integer.valueOf(100)))
/*  31 */       .min(1)
/*  32 */       .sliderRange(1, 1000)
/*  33 */       .build());
/*     */ 
/*     */   
/*  36 */   public final Setting<Integer> tabHeight = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  37 */       .name("column-height"))
/*  38 */       .description("How many players to display in each column."))
/*  39 */       .defaultValue(Integer.valueOf(20)))
/*  40 */       .min(1)
/*  41 */       .sliderRange(1, 1000)
/*  42 */       .build());
/*     */ 
/*     */   
/*  45 */   private final Setting<Boolean> self = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  46 */       .name("highlight-self"))
/*  47 */       .description("Highlights yourself in the tablist."))
/*  48 */       .defaultValue(Boolean.valueOf(true)))
/*  49 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> selfColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> friends;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> accurateLatency;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> gamemode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BetterTab() {
/*  83 */     super(Categories.Render, "better-tab", "Various improvements to the tab list."); Objects.requireNonNull(this.self);
/*     */     this.selfColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("self-color")).description("The color to highlight your name with.")).defaultValue(new SettingColor(250, 130, 30)).visible(this.self::get)).build());
/*     */     this.friends = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("highlight-friends")).description("Highlights friends in the tablist.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.accurateLatency = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("accurate-latency")).description("Shows latency as a number in the tablist.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.gamemode = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("gamemode")).description("Display gamemode next to the nick.")).defaultValue(Boolean.valueOf(false))).build()); } public class_2561 getPlayerName(class_640 playerListEntry) { class_5250 class_5250;
/*  88 */     Color color = null;
/*     */     
/*  90 */     class_2561 name = playerListEntry.method_2971();
/*  91 */     if (name == null) class_5250 = class_2561.method_43470(playerListEntry.method_2966().name());
/*     */     
/*  93 */     if (playerListEntry.method_2966().id().toString().equals(this.mc.field_1724.method_7334().id().toString()) && ((Boolean)this.self.get()).booleanValue()) {
/*  94 */       color = (Color)this.selfColor.get();
/*     */     }
/*  96 */     else if (((Boolean)this.friends.get()).booleanValue() && Friends.get().isFriend(playerListEntry)) {
/*  97 */       Friend friend = Friends.get().get(playerListEntry);
/*  98 */       if (friend != null) color = (Color)(Config.get()).friendColor.get();
/*     */     
/*     */     } 
/* 101 */     if (color != null) {
/* 102 */       String nameString = class_5250.getString();
/*     */       
/* 104 */       for (class_124 format : class_124.values()) {
/* 105 */         if (format.method_543()) nameString = nameString.replace(format.toString(), "");
/*     */       
/*     */       } 
/* 108 */       class_5250 = class_2561.method_43470(nameString).method_10862(class_5250.method_10866().method_27703(class_5251.method_27717(color.getPacked())));
/*     */     } 
/*     */     
/* 111 */     if (((Boolean)this.gamemode.get()).booleanValue()) {
/* 112 */       class_1934 gm = playerListEntry.method_2958();
/* 113 */       String gmText = "?";
/* 114 */       if (gm != null) {
/* 115 */         switch (gm) { default: throw new MatchException(null, null);
/*     */           case field_9219: 
/*     */           case field_9215: 
/*     */           case field_9220: 
/* 119 */           case field_9216: break; }  gmText = "A";
/*     */       } 
/*     */       
/* 122 */       class_5250 text = class_2561.method_43470("");
/* 123 */       text.method_10852((class_2561)class_5250);
/* 124 */       text.method_27693(" [" + gmText + "]");
/* 125 */       class_5250 = text;
/*     */     } 
/*     */     
/* 128 */     return (class_2561)class_5250; }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\BetterTab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */