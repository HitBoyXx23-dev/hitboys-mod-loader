/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WMultiLabel
/*    */   extends WLabel
/*    */ {
/* 12 */   protected List<String> lines = new ArrayList<>(2);
/*    */   
/*    */   protected double maxWidth;
/*    */   
/*    */   public WMultiLabel(String text, boolean title, double maxWidth) {
/* 17 */     super(text, title);
/*    */     
/* 19 */     this.maxWidth = maxWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 24 */     this.lines.clear();
/*    */     
/* 26 */     String[] textLines = this.text.split("\n");
/* 27 */     double maxLineWidth = 0.0D;
/*    */     
/* 29 */     if (this.maxWidth == 0.0D) {
/* 30 */       for (String line : textLines) {
/* 31 */         this.lines.add(line);
/* 32 */         double lineWidth = this.theme.textWidth(line, line.length(), this.title);
/* 33 */         maxLineWidth = Math.max(maxLineWidth, lineWidth);
/*    */       } 
/*    */     } else {
/* 36 */       StringBuilder sb = new StringBuilder();
/*    */       
/* 38 */       double lineWidth = 0.0D;
/* 39 */       double spaceWidth = this.theme.textWidth(" ", 1, this.title);
/* 40 */       double maxWidth = this.theme.scale(this.maxWidth);
/*    */       
/* 42 */       int iInLine = 0;
/*    */       
/* 44 */       for (String line : textLines) {
/* 45 */         for (String word : line.split(" ")) {
/* 46 */           double wordWidth = this.theme.textWidth(word, word.length(), this.title);
/*    */           
/* 48 */           double toAdd = wordWidth;
/* 49 */           if (iInLine > 0) toAdd += spaceWidth;
/*    */           
/* 51 */           if (lineWidth + toAdd > maxWidth) {
/* 52 */             this.lines.add(sb.toString());
/* 53 */             sb.setLength(0);
/*    */             
/* 55 */             sb.append(word);
/* 56 */             lineWidth = wordWidth;
/* 57 */             iInLine = 1;
/*    */           } else {
/*    */             
/* 60 */             if (iInLine > 0) {
/* 61 */               sb.append(' ');
/* 62 */               lineWidth += spaceWidth;
/*    */             } 
/*    */             
/* 65 */             sb.append(word);
/* 66 */             lineWidth += wordWidth;
/* 67 */             iInLine++;
/*    */           } 
/*    */           
/* 70 */           maxLineWidth = Math.max(maxLineWidth, lineWidth);
/*    */         } 
/* 72 */         this.lines.add(sb.toString());
/* 73 */         sb.setLength(0);
/* 74 */         lineWidth = 0.0D;
/* 75 */         iInLine = 0;
/*    */       } 
/*    */       
/* 78 */       if (!sb.isEmpty()) this.lines.add(sb.toString());
/*    */     
/*    */     } 
/* 81 */     this.width = maxLineWidth;
/* 82 */     this.height = this.theme.textHeight(this.title) * this.lines.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(String text) {
/* 87 */     if (!text.equals(this.text)) invalidate();
/*    */     
/* 89 */     this.text = text;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WMultiLabel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */