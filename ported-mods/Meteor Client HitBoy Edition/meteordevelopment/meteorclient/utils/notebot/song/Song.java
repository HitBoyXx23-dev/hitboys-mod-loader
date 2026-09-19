/*    */ package meteordevelopment.meteorclient.utils.notebot.song;
/*    */ 
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Song
/*    */ {
/*    */   private final Multimap<Integer, Note> notesMap;
/*    */   private int lastTick;
/*    */   private final String title;
/*    */   private final String author;
/* 20 */   private final Set<Note> requirements = new HashSet<>();
/*    */   
/*    */   private boolean finishedLoading = false;
/*    */ 
/*    */   
/*    */   public Song(Multimap<Integer, Note> notesMap, String title, String author) {
/* 26 */     this.notesMap = notesMap;
/* 27 */     this.title = title;
/* 28 */     this.author = author;
/*    */   }
/*    */   
/*    */   public void finishLoading() {
/* 32 */     if (this.finishedLoading) throw new IllegalStateException("Song has already finished loading!");
/*    */     
/* 34 */     this.lastTick = ((Integer)Collections.<Integer>max(this.notesMap.keySet())).intValue();
/* 35 */     Objects.requireNonNull(this.requirements); this.notesMap.values().stream().distinct().forEach(this.requirements::add);
/*    */     
/* 37 */     this.finishedLoading = true;
/*    */   }
/*    */   
/*    */   public Multimap<Integer, Note> getNotesMap() {
/* 41 */     return this.notesMap;
/*    */   }
/*    */   
/*    */   public Set<Note> getRequirements() {
/* 45 */     if (!this.finishedLoading) throw new IllegalStateException("Song is still loading!"); 
/* 46 */     return this.requirements;
/*    */   }
/*    */   
/*    */   public int getLastTick() {
/* 50 */     if (!this.finishedLoading) throw new IllegalStateException("Song is still loading!"); 
/* 51 */     return this.lastTick;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 55 */     return this.title;
/*    */   }
/*    */   
/*    */   public String getAuthor() {
/* 59 */     return this.author;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\notebot\song\Song.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */