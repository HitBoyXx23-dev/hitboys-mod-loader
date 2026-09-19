/*    */ package meteordevelopment.meteorclient.utils.notebot.song;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.minecraft.class_2766;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Note
/*    */ {
/*    */   private class_2766 instrument;
/*    */   private int noteLevel;
/*    */   
/*    */   public Note(class_2766 instrument, int noteLevel) {
/* 18 */     this.instrument = instrument;
/* 19 */     this.noteLevel = noteLevel;
/*    */   }
/*    */   
/*    */   public class_2766 getInstrument() {
/* 23 */     return this.instrument;
/*    */   }
/*    */   
/*    */   public void setInstrument(class_2766 instrument) {
/* 27 */     this.instrument = instrument;
/*    */   }
/*    */   
/*    */   public int getNoteLevel() {
/* 31 */     return this.noteLevel;
/*    */   }
/*    */   
/*    */   public void setNoteLevel(int noteLevel) {
/* 35 */     this.noteLevel = noteLevel;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 40 */     if (this == o) return true; 
/* 41 */     if (o == null || getClass() != o.getClass()) return false; 
/* 42 */     Note note = (Note)o;
/* 43 */     return (this.instrument == note.instrument && this.noteLevel == note.noteLevel);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 48 */     return Objects.hash(new Object[] { this.instrument, Integer.valueOf(this.noteLevel) });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return "Note{instrument=" + String.valueOf(getInstrument()) + ", noteLevel=" + 
/* 55 */       getNoteLevel() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\notebot\song\Note.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */