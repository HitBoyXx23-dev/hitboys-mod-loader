/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WHorizontalSeparator
/*    */   extends WWidget
/*    */ {
/*    */   protected String text;
/*    */   protected double textWidth;
/*    */   
/*    */   public WHorizontalSeparator(String text) {
/* 13 */     this.text = text;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 18 */     if (this.text != null) this.textWidth = this.theme.textWidth(this.text);
/*    */     
/* 20 */     this.width = 1.0D;
/* 21 */     this.height = (this.text != null) ? this.theme.textHeight() : this.theme.scale(3.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WHorizontalSeparator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */