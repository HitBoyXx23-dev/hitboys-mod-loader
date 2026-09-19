/*    */ package meteordevelopment.meteorclient.gui.screens.settings.base;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.Map;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CollectionMapSettingScreen<K, V>
/*    */   extends WindowScreen
/*    */ {
/*    */   private final Setting<?> setting;
/*    */   protected final Map<K, V> map;
/*    */   private final Iterable<K> registry;
/*    */   private WTable table;
/* 28 */   private String filterText = "";
/*    */   
/*    */   public CollectionMapSettingScreen(GuiTheme theme, String title, Setting<?> setting, Map<K, V> map, Iterable<K> registry) {
/* 31 */     super(theme, title);
/*    */     
/* 33 */     this.setting = setting;
/* 34 */     this.map = map;
/* 35 */     this.registry = registry;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 41 */     WTextBox filter = (WTextBox)add((WWidget)this.theme.textBox("")).minWidth(400.0D).expandX().widget();
/* 42 */     filter.setFocused(true);
/* 43 */     filter.action = (() -> {
/*    */         this.filterText = filter.get().trim();
/*    */         
/*    */         this.table.clear();
/*    */         
/*    */         initTable();
/*    */       });
/* 50 */     this.table = (WTable)add((WWidget)this.theme.table()).expandX().widget();
/*    */     
/* 52 */     initTable();
/*    */   }
/*    */   
/*    */   private void initTable() {
/* 56 */     Comparator<K> prioritizeChanged = Comparator.comparing(key -> { // Byte code:
/*    */           //   0: aload_0
/*    */           //   1: getfield map : Ljava/util/Map;
/*    */           //   4: aload_1
/*    */           //   5: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*    */           //   10: astore_3
/*    */           //   11: aload_3
/*    */           //   12: instanceof meteordevelopment/meteorclient/utils/misc/IChangeable
/*    */           //   15: ifeq -> 36
/*    */           //   18: aload_3
/*    */           //   19: checkcast meteordevelopment/meteorclient/utils/misc/IChangeable
/*    */           //   22: astore_2
/*    */           //   23: aload_2
/*    */           //   24: invokeinterface isChanged : ()Z
/*    */           //   29: ifeq -> 36
/*    */           //   32: iconst_1
/*    */           //   33: goto -> 37
/*    */           //   36: iconst_0
/*    */           //   37: ifne -> 44
/*    */           //   40: iconst_1
/*    */           //   41: goto -> 45
/*    */           //   44: iconst_0
/*    */           //   45: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*    */           //   48: areturn
/*    */           // Line number table:
/*    */           //   Java source line number -> byte code offset
/*    */           //   #56	-> 0
/*    */           // Local variable table:
/*    */           //   start	length	slot	name	descriptor
/*    */           //   11	12	3	patt0$temp	Ljava/lang/Object;
/*    */           //   23	13	2	changeable	Lmeteordevelopment/meteorclient/utils/misc/IChangeable;
/*    */           //   0	49	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/CollectionMapSettingScreen;
/*    */           //   0	49	1	key	Ljava/lang/Object;
/*    */           // Local variable type table:
/*    */           //   start	length	slot	name	signature
/*    */           //   0	49	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/CollectionMapSettingScreen<TK;TV;>;
/* 57 */         }); Iterable<K> sorted = SortingHelper.sortWithPriority(this.registry, this::includeValue, this::getValueNames, this.filterText, prioritizeChanged);
/*    */     
/* 59 */     sorted.forEach(t -> {
/*    */           // Byte code:
/*    */           //   0: aload_0
/*    */           //   1: getfield map : Ljava/util/Map;
/*    */           //   4: aload_1
/*    */           //   5: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*    */           //   10: astore_2
/*    */           //   11: aload_2
/*    */           //   12: instanceof meteordevelopment/meteorclient/utils/misc/IChangeable
/*    */           //   15: ifeq -> 38
/*    */           //   18: aload_2
/*    */           //   19: checkcast meteordevelopment/meteorclient/utils/misc/IChangeable
/*    */           //   22: astore #4
/*    */           //   24: aload #4
/*    */           //   26: invokeinterface isChanged : ()Z
/*    */           //   31: ifeq -> 38
/*    */           //   34: iconst_1
/*    */           //   35: goto -> 39
/*    */           //   38: iconst_0
/*    */           //   39: istore_3
/*    */           //   40: aload_0
/*    */           //   41: getfield table : Lmeteordevelopment/meteorclient/gui/widgets/containers/WTable;
/*    */           //   44: aload_0
/*    */           //   45: aload_1
/*    */           //   46: invokevirtual getValueWidget : (Ljava/lang/Object;)Lmeteordevelopment/meteorclient/gui/widgets/WWidget;
/*    */           //   49: invokevirtual add : (Lmeteordevelopment/meteorclient/gui/widgets/WWidget;)Lmeteordevelopment/meteorclient/gui/utils/Cell;
/*    */           //   52: invokevirtual expandCellX : ()Lmeteordevelopment/meteorclient/gui/utils/Cell;
/*    */           //   55: pop
/*    */           //   56: aload_0
/*    */           //   57: getfield table : Lmeteordevelopment/meteorclient/gui/widgets/containers/WTable;
/*    */           //   60: aload_0
/*    */           //   61: getfield theme : Lmeteordevelopment/meteorclient/gui/GuiTheme;
/*    */           //   64: iload_3
/*    */           //   65: ifeq -> 73
/*    */           //   68: ldc '*'
/*    */           //   70: goto -> 75
/*    */           //   73: ldc ' '
/*    */           //   75: invokevirtual label : (Ljava/lang/String;)Lmeteordevelopment/meteorclient/gui/widgets/WLabel;
/*    */           //   78: invokevirtual add : (Lmeteordevelopment/meteorclient/gui/widgets/WWidget;)Lmeteordevelopment/meteorclient/gui/utils/Cell;
/*    */           //   81: pop
/*    */           //   82: aload_0
/*    */           //   83: getfield table : Lmeteordevelopment/meteorclient/gui/widgets/containers/WTable;
/*    */           //   86: aload_0
/*    */           //   87: aload_1
/*    */           //   88: aload_2
/*    */           //   89: invokevirtual getDataWidget : (Ljava/lang/Object;Ljava/lang/Object;)Lmeteordevelopment/meteorclient/gui/widgets/WWidget;
/*    */           //   92: invokevirtual add : (Lmeteordevelopment/meteorclient/gui/widgets/WWidget;)Lmeteordevelopment/meteorclient/gui/utils/Cell;
/*    */           //   95: pop
/*    */           //   96: aload_0
/*    */           //   97: getfield table : Lmeteordevelopment/meteorclient/gui/widgets/containers/WTable;
/*    */           //   100: aload_0
/*    */           //   101: getfield theme : Lmeteordevelopment/meteorclient/gui/GuiTheme;
/*    */           //   104: getstatic meteordevelopment/meteorclient/gui/renderer/GuiRenderer.RESET : Lmeteordevelopment/meteorclient/gui/renderer/packer/GuiTexture;
/*    */           //   107: invokevirtual button : (Lmeteordevelopment/meteorclient/gui/renderer/packer/GuiTexture;)Lmeteordevelopment/meteorclient/gui/widgets/pressable/WButton;
/*    */           //   110: invokevirtual add : (Lmeteordevelopment/meteorclient/gui/widgets/WWidget;)Lmeteordevelopment/meteorclient/gui/utils/Cell;
/*    */           //   113: invokevirtual widget : ()Lmeteordevelopment/meteorclient/gui/widgets/WWidget;
/*    */           //   116: checkcast meteordevelopment/meteorclient/gui/widgets/pressable/WButton
/*    */           //   119: astore #4
/*    */           //   121: aload #4
/*    */           //   123: aload_0
/*    */           //   124: aload_1
/*    */           //   125: <illegal opcode> run : (Lmeteordevelopment/meteorclient/gui/screens/settings/base/CollectionMapSettingScreen;Ljava/lang/Object;)Ljava/lang/Runnable;
/*    */           //   130: putfield action : Ljava/lang/Runnable;
/*    */           //   133: aload #4
/*    */           //   135: ldc_w 'Reset'
/*    */           //   138: putfield tooltip : Ljava/lang/String;
/*    */           //   141: aload_0
/*    */           //   142: getfield table : Lmeteordevelopment/meteorclient/gui/widgets/containers/WTable;
/*    */           //   145: invokevirtual row : ()V
/*    */           //   148: return
/*    */           // Line number table:
/*    */           //   Java source line number -> byte code offset
/*    */           //   #60	-> 0
/*    */           //   #61	-> 11
/*    */           //   #63	-> 40
/*    */           //   #64	-> 56
/*    */           //   #65	-> 82
/*    */           //   #67	-> 96
/*    */           //   #68	-> 121
/*    */           //   #69	-> 133
/*    */           //   #71	-> 141
/*    */           //   #72	-> 148
/*    */           // Local variable table:
/*    */           //   start	length	slot	name	descriptor
/*    */           //   24	14	4	changeable	Lmeteordevelopment/meteorclient/utils/misc/IChangeable;
/*    */           //   0	149	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/CollectionMapSettingScreen;
/*    */           //   0	149	1	t	Ljava/lang/Object;
/*    */           //   11	138	2	data	Ljava/lang/Object;
/*    */           //   40	109	3	isChanged	Z
/*    */           //   121	28	4	reset	Lmeteordevelopment/meteorclient/gui/widgets/pressable/WButton;
/*    */           // Local variable type table:
/*    */           //   start	length	slot	name	signature
/*    */           //   0	149	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/CollectionMapSettingScreen<TK;TV;>;
/*    */           //   11	138	2	data	TV;
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void invalidateTable() {
/* 76 */     this.table.clear();
/* 77 */     initTable();
/*    */   }
/*    */   
/*    */   protected void removeValue(K value) {
/* 81 */     if (this.map.remove(value) != null) {
/* 82 */       this.setting.onChanged();
/* 83 */       invalidateTable();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected boolean includeValue(K value) {
/* 88 */     return true;
/*    */   }
/*    */   
/*    */   protected abstract WWidget getValueWidget(K paramK);
/*    */   
/*    */   protected abstract WWidget getDataWidget(K paramK, @Nullable V paramV);
/*    */   
/*    */   protected abstract String[] getValueNames(K paramK);
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\base\CollectionMapSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */