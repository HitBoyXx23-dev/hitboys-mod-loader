/*    */ package meteordevelopment.meteorclient.gui.screens;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.Notebot;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import meteordevelopment.meteorclient.utils.notebot.decoder.SongDecoders;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotebotSongsScreen
/*    */   extends WindowScreen
/*    */ {
/* 26 */   private static final Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*    */   
/*    */   private WTextBox filter;
/* 29 */   private String filterText = "";
/*    */   
/*    */   private WTable table;
/*    */   
/*    */   public NotebotSongsScreen(GuiTheme theme) {
/* 34 */     super(theme, "Notebot Songs");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 40 */     WButton randomSong = (WButton)add((WWidget)this.theme.button("Random Song")).minWidth(400.0D).expandX().widget();
/* 41 */     Objects.requireNonNull(notebot); randomSong.action = notebot::playRandomSong;
/*    */ 
/*    */     
/* 44 */     this.filter = (WTextBox)add((WWidget)this.theme.textBox("", "Search for the songs...")).minWidth(400.0D).expandX().widget();
/* 45 */     this.filter.setFocused(true);
/* 46 */     this.filter.action = (() -> {
/*    */         this.filterText = this.filter.get().trim();
/*    */         
/*    */         this.table.clear();
/*    */         
/*    */         initSongsTable();
/*    */       });
/* 53 */     this.table = (WTable)add((WWidget)this.theme.table()).widget();
/*    */     
/* 55 */     initSongsTable();
/*    */   }
/*    */   
/*    */   private void initSongsTable() {
/* 59 */     AtomicBoolean noSongsFound = new AtomicBoolean(true);
/*    */     try {
/* 61 */       Files.list(MeteorClient.FOLDER.toPath().resolve("notebot")).forEach(path -> {
/*    */             if (SongDecoders.hasDecoder(path)) {
/*    */               String name = path.getFileName().toString();
/*    */               
/*    */               if (Utils.searchTextDefault(name, this.filterText, false)) {
/*    */                 addPath(path);
/*    */                 noSongsFound.set(false);
/*    */               } 
/*    */             } 
/*    */           });
/* 71 */     } catch (IOException e) {
/* 72 */       this.table.add((WWidget)this.theme.label("Missing meteor-client/notebot folder.")).expandCellX();
/* 73 */       this.table.row();
/*    */     } 
/*    */     
/* 76 */     if (noSongsFound.get()) {
/* 77 */       this.table.add((WWidget)this.theme.label("No songs found.")).expandCellX().center();
/*    */     }
/*    */   }
/*    */   
/*    */   private void addPath(Path path) {
/* 82 */     this.table.add((WWidget)this.theme.horizontalSeparator()).expandX().minWidth(400.0D);
/* 83 */     this.table.row();
/*    */     
/* 85 */     this.table.add((WWidget)this.theme.label(FilenameUtils.getBaseName(path.getFileName().toString()))).expandCellX();
/* 86 */     WButton load = (WButton)this.table.add((WWidget)this.theme.button("Load")).right().widget();
/* 87 */     load.action = (() -> notebot.loadSong(path.toFile()));
/* 88 */     WButton preview = (WButton)this.table.add((WWidget)this.theme.button("Preview")).right().widget();
/* 89 */     preview.action = (() -> notebot.previewSong(path.toFile()));
/*    */     
/* 91 */     this.table.row();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\NotebotSongsScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */