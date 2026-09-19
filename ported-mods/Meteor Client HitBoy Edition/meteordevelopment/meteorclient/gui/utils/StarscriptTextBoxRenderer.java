/*     */ package meteordevelopment.meteorclient.gui.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.systems.hud.elements.TextHud;
/*     */ import meteordevelopment.meteorclient.utils.misc.MeteorStarscript;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import org.meteordev.starscript.utils.SemanticToken;
/*     */ import org.meteordev.starscript.utils.SemanticTokenProvider;
/*     */ import org.meteordev.starscript.utils.SemanticTokenType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StarscriptTextBoxRenderer
/*     */   implements WTextBox.Renderer
/*     */ {
/*  22 */   private static final Color RED = new Color(225, 25, 25);
/*     */   
/*  24 */   private final List<SemanticToken> tokens = new ArrayList<>();
/*  25 */   private final List<Section> sections = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private String lastText;
/*     */ 
/*     */   
/*     */   public void render(GuiRenderer renderer, double x, double y, String text, Color color) {
/*  32 */     if (this.lastText == null || !this.lastText.equals(text)) {
/*  33 */       this.lastText = text;
/*     */       
/*  35 */       SemanticTokenProvider.get(text, this.tokens);
/*  36 */       convertTokensToSections(renderer.theme);
/*     */     } 
/*     */ 
/*     */     
/*  40 */     for (Section section : this.sections) {
/*  41 */       renderer.text(section.text, x, y, section.color, false);
/*  42 */       x += renderer.theme.textWidth(section.text);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getCompletions(String text, int position) {
/*  48 */     List<String> completions = new ArrayList<>();
/*     */     
/*  50 */     MeteorStarscript.ss.getCompletions(text, position, (completion, function) -> completions.add(function ? (completion + "(") : completion));
/*     */ 
/*     */ 
/*     */     
/*  54 */     completions.sort(String::compareToIgnoreCase);
/*     */     
/*  56 */     return completions;
/*     */   }
/*     */   
/*     */   private void convertTokensToSections(GuiTheme theme) {
/*  60 */     this.sections.clear();
/*     */     
/*  62 */     int start = 0;
/*     */     
/*  64 */     for (SemanticToken token : this.tokens) {
/*  65 */       if (start != token.start) {
/*  66 */         this.sections.add(new Section(this.lastText
/*  67 */               .substring(start, token.start), theme
/*  68 */               .starscriptTextColor()));
/*     */       }
/*     */ 
/*     */       
/*  72 */       String text = this.lastText.substring(token.start, token.end);
/*     */       
/*  74 */       this.sections.add(new Section(text, 
/*     */             
/*  76 */             getColorForToken(theme, token.type, text)));
/*     */ 
/*     */       
/*  79 */       start = token.end;
/*     */     } 
/*     */     
/*  82 */     if (start < this.lastText.length()) {
/*  83 */       this.sections.add(new Section(this.lastText
/*  84 */             .substring(start), theme
/*  85 */             .starscriptTextColor()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static Color getColorForToken(GuiTheme theme, SemanticTokenType type, String text) {
/*  91 */     switch (type) { default: throw new MatchException(null, null);
/*  92 */       case Dot: null = theme.starscriptDotColor();
/*  93 */       case Comma: null = theme.starscriptCommaColor();
/*  94 */       case Operator: null = theme.starscriptOperatorColor();
/*  95 */       case String: null = theme.starscriptStringColor();
/*  96 */       case Number: null = theme.starscriptNumberColor();
/*  97 */       case Keyword: null = theme.starscriptKeywordColor();
/*  98 */       case Paren: null = theme.starscriptParenthesisColor();
/*  99 */       case Brace: null = theme.starscriptBraceColor();
/* 100 */       case Identifier: null = theme.starscriptTextColor();
/* 101 */       case Map: null = theme.starscriptAccessedObjectColor();
/*     */       case Section:
/* 103 */         if (text.startsWith("#")) {
/* 104 */           text = text.substring(1);
/*     */         }
/*     */ 
/*     */         
/* 108 */         try { null = TextHud.getSectionColor(Integer.parseInt(text)); }
/* 109 */         catch (NumberFormatException numberFormatException)
/*     */         
/* 111 */         { null = theme.starscriptTextColor(); }  return null;
/*     */       case Error:
/* 113 */         break; }  return RED;
/*     */   }
/*     */   private static final class Section extends Record { private final String text; private final Color color;
/*     */     
/* 117 */     private Section(String text, Color color) { this.text = text; this.color = color; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/gui/utils/StarscriptTextBoxRenderer$Section;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #117	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 117 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/gui/utils/StarscriptTextBoxRenderer$Section; } public String text() { return this.text; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/gui/utils/StarscriptTextBoxRenderer$Section;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #117	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/gui/utils/StarscriptTextBoxRenderer$Section; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/gui/utils/StarscriptTextBoxRenderer$Section;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #117	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/gui/utils/StarscriptTextBoxRenderer$Section;
/* 117 */       //   0	8	1	o	Ljava/lang/Object; } public Color color() { return this.color; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gu\\utils\StarscriptTextBoxRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */