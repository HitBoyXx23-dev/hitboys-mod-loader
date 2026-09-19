/*     */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.input;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*     */ import meteordevelopment.meteorclient.gui.themes.meteor.widgets.WMeteorLabel;
/*     */ import meteordevelopment.meteorclient.gui.utils.CharFilter;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WMeteorTextBox
/*     */   extends WTextBox
/*     */   implements MeteorWidget
/*     */ {
/*     */   private boolean cursorVisible;
/*     */   private double cursorTimer;
/*     */   private double animProgress;
/*     */   
/*     */   public WMeteorTextBox(String text, String placeholder, CharFilter filter, Class<? extends WTextBox.Renderer> renderer) {
/*  27 */     super(text, placeholder, filter, renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WContainer createCompletionsRootWidget() {
/*  32 */     return (WContainer)new WVerticalList()
/*     */       {
/*     */         protected void onRender(GuiRenderer renderer1, double mouseX, double mouseY, double delta) {
/*  35 */           MeteorGuiTheme theme1 = WMeteorTextBox.this.theme();
/*  36 */           double s = theme1.scale(2.0D);
/*  37 */           SettingColor settingColor1 = theme1.outlineColor.get();
/*     */           
/*  39 */           SettingColor settingColor2 = theme1.backgroundColor.get();
/*  40 */           int preA = ((Color)settingColor2).a;
/*  41 */           ((Color)settingColor2).a += ((Color)settingColor2).a / 2;
/*  42 */           settingColor2.validate();
/*  43 */           renderer1.quad((WWidget)this, (Color)settingColor2);
/*  44 */           ((Color)settingColor2).a = preA;
/*     */           
/*  46 */           renderer1.quad(this.x, this.y + this.height - s, this.width, s, (Color)settingColor1);
/*  47 */           renderer1.quad(this.x, this.y, s, this.height - s, (Color)settingColor1);
/*  48 */           renderer1.quad(this.x + this.width - s, this.y, s, this.height - s, (Color)settingColor1);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T extends WWidget & WTextBox.ICompletionItem> T createCompletionsValueWidth(String completion, boolean selected) {
/*  56 */     return (T)new CompletionItem(completion, false, selected);
/*     */   }
/*     */   
/*     */   private static class CompletionItem extends WMeteorLabel implements WTextBox.ICompletionItem {
/*  60 */     private static final Color SELECTED_COLOR = new Color(255, 255, 255, 15);
/*     */     
/*     */     private boolean selected;
/*     */     
/*     */     public CompletionItem(String text, boolean title, boolean selected) {
/*  65 */       super(text, title);
/*  66 */       this.selected = selected;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/*  71 */       super.onRender(renderer, mouseX, mouseY, delta);
/*     */       
/*  73 */       if (this.selected) renderer.quad((WWidget)this, SELECTED_COLOR);
/*     */     
/*     */     }
/*     */     
/*     */     public boolean isSelected() {
/*  78 */       return this.selected;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(boolean selected) {
/*  83 */       this.selected = selected;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getCompletion() {
/*  88 */       return this.text;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCursorChanged() {
/*  94 */     this.cursorVisible = true;
/*  95 */     this.cursorTimer = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 100 */     if (this.cursorTimer >= 1.0D) {
/* 101 */       this.cursorVisible = !this.cursorVisible;
/* 102 */       this.cursorTimer = 0.0D;
/*     */     } else {
/*     */       
/* 105 */       this.cursorTimer += delta * 1.75D;
/*     */     } 
/*     */     
/* 108 */     renderBackground(renderer, (WWidget)this, false, false);
/*     */     
/* 110 */     MeteorGuiTheme theme = theme();
/* 111 */     double pad = pad();
/* 112 */     double overflowWidth = getOverflowWidthForRender();
/*     */     
/* 114 */     renderer.scissorStart(this.x + pad, this.y + pad, this.width - pad * 2.0D, this.height - pad * 2.0D);
/*     */ 
/*     */     
/* 117 */     if (!this.text.isEmpty()) {
/* 118 */       this.renderer.render(renderer, this.x + pad - overflowWidth, this.y + pad, this.text, (Color)theme.textColor.get());
/*     */     }
/* 120 */     else if (this.placeholder != null) {
/* 121 */       this.renderer.render(renderer, this.x + pad - overflowWidth, this.y + pad, this.placeholder, (Color)theme.placeholderColor.get());
/*     */     } 
/*     */ 
/*     */     
/* 125 */     if (this.focused && (this.cursor != this.selectionStart || this.cursor != this.selectionEnd)) {
/* 126 */       double selStart = this.x + pad + getTextWidth(this.selectionStart) - overflowWidth;
/* 127 */       double selEnd = this.x + pad + getTextWidth(this.selectionEnd) - overflowWidth;
/*     */       
/* 129 */       renderer.quad(selStart, this.y + pad, selEnd - selStart, theme.textHeight(), (Color)theme.textHighlightColor.get());
/*     */     } 
/*     */ 
/*     */     
/* 133 */     this.animProgress += delta * 10.0D * ((this.focused && this.cursorVisible) ? true : -1);
/* 134 */     this.animProgress = class_3532.method_15350(this.animProgress, 0.0D, 1.0D);
/*     */     
/* 136 */     if ((this.focused && this.cursorVisible) || this.animProgress > 0.0D) {
/* 137 */       renderer.setAlpha(this.animProgress);
/* 138 */       renderer.quad(this.x + pad + getTextWidth(this.cursor) - overflowWidth, this.y + pad, theme.scale(1.0D), theme.textHeight(), (Color)theme.textColor.get());
/* 139 */       renderer.setAlpha(1.0D);
/*     */     } 
/*     */     
/* 142 */     renderer.scissorEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\input\WMeteorTextBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */