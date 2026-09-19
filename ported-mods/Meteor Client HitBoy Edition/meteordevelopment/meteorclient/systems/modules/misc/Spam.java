/*     */ package meteordevelopment.meteorclient.systems.modules.misc;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.game.GameLeftEvent;
/*     */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.StringListSetting;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import org.apache.commons.lang3.RandomStringUtils;
/*     */ 
/*     */ public class Spam
/*     */   extends Module
/*     */ {
/*  23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  25 */   private final Setting<List<String>> messages = this.sgGeneral.add((Setting)((StringListSetting.Builder)((StringListSetting.Builder)((StringListSetting.Builder)(new StringListSetting.Builder())
/*  26 */       .name("messages"))
/*  27 */       .description("Messages to use for spam."))
/*  28 */       .defaultValue(List.of("Meteor on Crack!")))
/*  29 */       .build());
/*     */ 
/*     */   
/*  32 */   private final Setting<Integer> delay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  33 */       .name("delay"))
/*  34 */       .description("The delay between specified messages in ticks."))
/*  35 */       .defaultValue(Integer.valueOf(20)))
/*  36 */       .min(0)
/*  37 */       .sliderMax(200)
/*  38 */       .build());
/*     */ 
/*     */   
/*  41 */   private final Setting<Boolean> disableOnLeave = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  42 */       .name("disable-on-leave"))
/*  43 */       .description("Disables spam when you leave a server."))
/*  44 */       .defaultValue(Boolean.valueOf(true)))
/*  45 */       .build());
/*     */ 
/*     */   
/*  48 */   private final Setting<Boolean> disableOnDisconnect = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  49 */       .name("disable-on-disconnect"))
/*  50 */       .description("Disables spam when you are disconnected from a server."))
/*  51 */       .defaultValue(Boolean.valueOf(true)))
/*  52 */       .build());
/*     */ 
/*     */   
/*  55 */   private final Setting<Boolean> random = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  56 */       .name("randomise"))
/*  57 */       .description("Selects a random message from your spam message list."))
/*  58 */       .defaultValue(Boolean.valueOf(false)))
/*  59 */       .build());
/*     */ 
/*     */   
/*  62 */   private final Setting<Boolean> autoSplitMessages = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  63 */       .name("auto-split-messages"))
/*  64 */       .description("Automatically split up large messages after a certain length"))
/*  65 */       .defaultValue(Boolean.valueOf(false)))
/*  66 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> splitLength;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> autoSplitDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> bypass;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> uppercase;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> length;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int messageI;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int timer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int splitNum;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String text;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spam() {
/* 117 */     super(Categories.Misc, "spam", "Spams specified messages in chat."); Objects.requireNonNull(this.autoSplitMessages); this.splitLength = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("split-length")).description("The length after which to split messages in chat")).visible(this.autoSplitMessages::get)).defaultValue(Integer.valueOf(256))).min(1).sliderMax(256).build()); Objects.requireNonNull(this.autoSplitMessages); this.autoSplitDelay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("split-delay")).description("The delay between split messages in ticks.")).visible(this.autoSplitMessages::get)).defaultValue(Integer.valueOf(20))).min(0).sliderMax(200).build());
/*     */     this.bypass = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("bypass")).description("Add random text at the end of the message to try to bypass anti spams.")).defaultValue(Boolean.valueOf(false))).build());
/*     */     Objects.requireNonNull(this.bypass);
/*     */     this.uppercase = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("include-uppercase-characters")).description("Whether the bypass text should include uppercase characters.")).visible(this.bypass::get)).defaultValue(Boolean.valueOf(true))).build());
/*     */     Objects.requireNonNull(this.bypass);
/* 122 */     this.length = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("length")).description("Number of characters used to bypass anti spam.")).visible(this.bypass::get)).defaultValue(Integer.valueOf(16))).sliderRange(1, 256).build()); } public void onActivate() { this.timer = ((Integer)this.delay.get()).intValue();
/* 123 */     this.messageI = 0;
/* 124 */     this.splitNum = 0; }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onScreenOpen(OpenScreenEvent event) {
/* 129 */     if (((Boolean)this.disableOnDisconnect.get()).booleanValue() && event.screen instanceof net.minecraft.class_419) {
/* 130 */       toggle();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onGameLeft(GameLeftEvent event) {
/* 136 */     if (((Boolean)this.disableOnLeave.get()).booleanValue()) toggle(); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 141 */     if (((List)this.messages.get()).isEmpty())
/*     */       return; 
/* 143 */     if (this.timer <= 0) {
/* 144 */       if (this.text == null) {
/*     */         int i;
/* 146 */         if (((Boolean)this.random.get()).booleanValue()) {
/* 147 */           i = Utils.random(0, ((List)this.messages.get()).size());
/*     */         } else {
/* 149 */           if (this.messageI >= ((List)this.messages.get()).size()) this.messageI = 0; 
/* 150 */           i = this.messageI++;
/*     */         } 
/*     */         
/* 153 */         this.text = ((List<String>)this.messages.get()).get(i);
/* 154 */         if (((Boolean)this.bypass.get()).booleanValue()) {
/* 155 */           String bypass = RandomStringUtils.insecure().nextAlphabetic(((Integer)this.length.get()).intValue());
/* 156 */           if (!((Boolean)this.uppercase.get()).booleanValue()) bypass = bypass.toLowerCase();
/*     */           
/* 158 */           this.text = this.text + " " + this.text;
/*     */         } 
/*     */       } 
/*     */       
/* 162 */       if (((Boolean)this.autoSplitMessages.get()).booleanValue() && this.text.length() > ((Integer)this.splitLength.get()).intValue()) {
/*     */         
/* 164 */         double length = this.text.length();
/* 165 */         int splits = (int)Math.ceil(length / ((Integer)this.splitLength.get()).intValue());
/*     */ 
/*     */         
/* 168 */         int start = this.splitNum * ((Integer)this.splitLength.get()).intValue();
/* 169 */         int end = Math.min(start + ((Integer)this.splitLength.get()).intValue(), this.text.length());
/* 170 */         ChatUtils.sendPlayerMsg(this.text.substring(start, end));
/*     */         
/* 172 */         this.splitNum = ++this.splitNum % splits;
/* 173 */         this.timer = ((Integer)this.autoSplitDelay.get()).intValue();
/* 174 */         if (this.splitNum == 0) {
/* 175 */           this.timer = ((Integer)this.delay.get()).intValue();
/* 176 */           this.text = null;
/*     */         } 
/*     */       } else {
/* 179 */         if (this.text.length() > 256) this.text = this.text.substring(0, 256); 
/* 180 */         ChatUtils.sendPlayerMsg(this.text);
/* 181 */         this.timer = ((Integer)this.delay.get()).intValue();
/* 182 */         this.text = null;
/*     */       } 
/*     */     } else {
/* 185 */       this.timer--;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\Spam.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */