/*     */ package meteordevelopment.meteorclient.gui.widgets.input;
/*     */ 
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.entity.player.InteractBlockEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.StartBreakingBlockEvent;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.marker.Marker;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WBlockPosEdit
/*     */   extends WHorizontalList
/*     */ {
/*     */   public Runnable action;
/*     */   public Runnable actionOnRelease;
/*     */   private WTextBox textBoxX;
/*     */   private WTextBox textBoxY;
/*     */   private WTextBox textBoxZ;
/*     */   private class_437 previousScreen;
/*     */   private class_2338 value;
/*     */   private class_2338 lastValue;
/*     */   private boolean clicking;
/*     */   
/*     */   public WBlockPosEdit(class_2338 value) {
/*  37 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  42 */     addTextBox();
/*     */     
/*  44 */     if (Utils.canUpdate()) {
/*  45 */       WButton click = (WButton)add((WWidget)this.theme.button("Click")).expandX().widget();
/*  46 */       click.action = (() -> {
/*     */           String sb = "Click!\nRight click to pick a new position.\nLeft click to cancel.";
/*     */           
/*     */           ((Marker)Modules.get().get(Marker.class)).info(sb, new Object[0]);
/*     */           
/*     */           this.clicking = true;
/*     */           MeteorClient.EVENT_BUS.subscribe(this);
/*     */           this.previousScreen = MeteorClient.mc.field_1755;
/*     */           MeteorClient.mc.method_1507(null);
/*     */         });
/*  56 */       WButton here = (WButton)add((WWidget)this.theme.button("Set Here")).expandX().widget();
/*  57 */       here.action = (() -> {
/*     */           this.lastValue = this.value;
/*     */           set(new class_2338((class_2382)MeteorClient.mc.field_1724.method_24515()));
/*     */           newValueCheck();
/*     */           clear();
/*     */           init();
/*     */         });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onStartBreakingBlock(StartBreakingBlockEvent event) {
/*  70 */     if (this.clicking) {
/*  71 */       this.clicking = false;
/*  72 */       event.cancel();
/*  73 */       MeteorClient.EVENT_BUS.unsubscribe(this);
/*  74 */       MeteorClient.mc.method_1507(this.previousScreen);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onInteractBlock(InteractBlockEvent event) {
/*  80 */     if (this.clicking) {
/*  81 */       if (event.result.method_17783() == class_239.class_240.field_1333)
/*  82 */         return;  this.lastValue = this.value;
/*  83 */       set(event.result.method_17777());
/*  84 */       newValueCheck();
/*     */       
/*  86 */       clear();
/*  87 */       init();
/*     */       
/*  89 */       this.clicking = false;
/*  90 */       event.cancel();
/*  91 */       MeteorClient.EVENT_BUS.unsubscribe(this);
/*  92 */       MeteorClient.mc.method_1507(this.previousScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean filter(String text, char c) {
/*  98 */     boolean good, validate = true;
/*     */     
/* 100 */     if (c == '-' && text.isEmpty()) {
/* 101 */       good = true;
/* 102 */       validate = false;
/*     */     } else {
/* 104 */       good = Character.isDigit(c);
/*     */     } 
/* 106 */     if (good && validate) {
/*     */       try {
/* 108 */         Integer.parseInt(text + text);
/* 109 */       } catch (NumberFormatException ignored) {
/* 110 */         good = false;
/*     */       } 
/*     */     }
/*     */     
/* 114 */     return good;
/*     */   }
/*     */   
/*     */   public class_2338 get() {
/* 118 */     return this.value;
/*     */   }
/*     */   
/*     */   public void set(class_2338 value) {
/* 122 */     this.value = value;
/*     */   }
/*     */   
/*     */   private void addTextBox() {
/* 126 */     this.textBoxX = (WTextBox)add(this.theme.textBox(Integer.toString(this.value.method_10263()), this::filter)).minWidth(75.0D).widget();
/* 127 */     this.textBoxY = (WTextBox)add(this.theme.textBox(Integer.toString(this.value.method_10264()), this::filter)).minWidth(75.0D).widget();
/* 128 */     this.textBoxZ = (WTextBox)add(this.theme.textBox(Integer.toString(this.value.method_10260()), this::filter)).minWidth(75.0D).widget();
/*     */     
/* 130 */     this.textBoxX.actionOnUnfocused = (() -> {
/*     */         this.lastValue = this.value; if (this.textBoxX.get().isEmpty()) {
/*     */           set(new class_2338(0, 0, 0));
/*     */         } else {
/*     */           try {
/*     */             set(new class_2338(Integer.parseInt(this.textBoxX.get()), this.value.method_10264(), this.value.method_10260()));
/* 136 */           } catch (NumberFormatException numberFormatException) {}
/*     */         } 
/*     */         
/*     */         newValueCheck();
/*     */       });
/* 141 */     this.textBoxY.actionOnUnfocused = (() -> {
/*     */         this.lastValue = this.value; if (this.textBoxY.get().isEmpty()) {
/*     */           set(new class_2338(0, 0, 0));
/*     */         } else {
/*     */           try {
/*     */             set(new class_2338(this.value.method_10263(), Integer.parseInt(this.textBoxY.get()), this.value.method_10260()));
/* 147 */           } catch (NumberFormatException numberFormatException) {}
/*     */         } 
/*     */         
/*     */         newValueCheck();
/*     */       });
/* 152 */     this.textBoxZ.actionOnUnfocused = (() -> {
/*     */         this.lastValue = this.value; if (this.textBoxZ.get().isEmpty()) {
/*     */           set(new class_2338(0, 0, 0));
/*     */         } else {
/*     */           try {
/*     */             set(new class_2338(this.value.method_10263(), this.value.method_10264(), Integer.parseInt(this.textBoxZ.get())));
/* 158 */           } catch (NumberFormatException numberFormatException) {}
/*     */         } 
/*     */         newValueCheck();
/*     */       });
/*     */   }
/*     */   
/*     */   private void newValueCheck() {
/* 165 */     if (this.value != this.lastValue) {
/* 166 */       if (this.action != null) this.action.run(); 
/* 167 */       if (this.actionOnRelease != null) this.actionOnRelease.run(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\input\WBlockPosEdit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */