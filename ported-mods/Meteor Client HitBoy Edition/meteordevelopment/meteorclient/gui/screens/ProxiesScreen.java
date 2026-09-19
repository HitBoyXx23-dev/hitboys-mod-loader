/*     */ package meteordevelopment.meteorclient.gui.screens;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WMinus;
/*     */ import meteordevelopment.meteorclient.settings.Settings;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxies;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxy;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*     */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*     */ import net.minecraft.class_437;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.PointerBuffer;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.lwjgl.util.tinyfd.TinyFileDialogs;
/*     */ 
/*     */ public class ProxiesScreen
/*     */   extends WindowScreen
/*     */ {
/*  39 */   private final List<WCheckbox> checkboxes = new ArrayList<>();
/*  40 */   private final WButton refreshButton = this.theme.button("Refresh");
/*  41 */   private final WConfirmedButton cleanButton = this.theme.confirmedButton("Cleanup", "Confirm");
/*  42 */   private Map<Proxy, WLabel> statuses = new HashMap<>();
/*  43 */   private int timer = 0;
/*     */   
/*     */   public ProxiesScreen(GuiTheme theme) {
/*  46 */     super(theme, "Proxies");
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  51 */     WTable table = (WTable)add((WWidget)this.theme.table()).expandX().minWidth(400.0D).widget();
/*  52 */     initTable(table);
/*     */     
/*  54 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */     
/*  56 */     WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */ 
/*     */     
/*  59 */     WButton newBtn = (WButton)l.add((WWidget)this.theme.button("New")).expandX().widget();
/*  60 */     newBtn.action = (() -> MeteorClient.mc.method_1507((class_437)new EditProxyScreen(this.theme, null, this::reload)));
/*     */ 
/*     */     
/*  63 */     PointerBuffer filters = BufferUtils.createPointerBuffer(1);
/*     */     
/*  65 */     ByteBuffer txtFilter = MemoryUtil.memASCII("*.txt");
/*     */     
/*  67 */     filters.put(txtFilter);
/*  68 */     filters.rewind();
/*     */     
/*  70 */     WButton importBtn = (WButton)l.add((WWidget)this.theme.button("Import")).expandX().widget();
/*  71 */     importBtn.action = (() -> {
/*     */         String selectedFile = TinyFileDialogs.tinyfd_openFileDialog("Import Proxies", null, filters, null, false);
/*     */         
/*     */         if (selectedFile != null) {
/*     */           File file = new File(selectedFile);
/*     */           MeteorClient.mc.method_1507((class_437)new ProxiesImportScreen(this.theme, file));
/*     */         } 
/*     */       });
/*  79 */     l.add((WWidget)this.refreshButton).expandX();
/*  80 */     this.refreshButton.action = (() -> Proxies.get().checkProxies(true));
/*     */     
/*  82 */     l.add((WWidget)this.cleanButton).expandX();
/*  83 */     this.cleanButton.action = (() -> {
/*     */         if ((Proxies.get()).refreshing)
/*     */           return; 
/*     */         Proxies.get().clean();
/*     */         initTable(table);
/*     */       });
/*  89 */     WButton configButton = (WButton)l.add((WWidget)this.theme.button(GuiRenderer.EDIT)).widget();
/*  90 */     configButton.action = (() -> MeteorClient.mc.method_1507((class_437)new ConfigScreen(this.theme)));
/*  91 */     configButton.tooltip = "Proxies Config";
/*     */   }
/*     */   
/*     */   private void initTable(WTable table) {
/*  95 */     table.clear();
/*  96 */     if (Proxies.get().isEmpty())
/*     */       return; 
/*  98 */     this.statuses = new HashMap<>(Proxies.get().size(), 1.0F);
/*  99 */     for (Iterator<Proxy> iterator = Proxies.get().iterator(); iterator.hasNext(); ) { Proxy proxy = iterator.next();
/* 100 */       WCheckbox enabled = (WCheckbox)table.add((WWidget)this.theme.checkbox(((Boolean)proxy.enabled.get()).booleanValue())).widget();
/* 101 */       this.checkboxes.add(enabled);
/* 102 */       enabled.action = (() -> {
/*     */           boolean checked = enabled.checked;
/*     */           Proxies.get().setEnabled(proxy, checked);
/*     */           for (WCheckbox checkbox : this.checkboxes) {
/*     */             checkbox.checked = false;
/*     */           }
/*     */           enabled.checked = checked;
/*     */         });
/* 110 */       WLabel name = (WLabel)table.add((WWidget)this.theme.label((String)proxy.name.get())).widget();
/* 111 */       name.color = this.theme.textColor();
/*     */       
/* 113 */       WLabel type = (WLabel)table.add((WWidget)this.theme.label("(" + String.valueOf(proxy.type.get()) + ")")).widget();
/* 114 */       type.color = this.theme.textSecondaryColor();
/*     */       
/* 116 */       WHorizontalList ipList = (WHorizontalList)table.add((WWidget)this.theme.horizontalList()).expandCellX().widget();
/* 117 */       ipList.spacing = 0.0D;
/*     */       
/* 119 */       ipList.add((WWidget)this.theme.label((String)proxy.address.get()));
/* 120 */       ((WLabel)ipList.add((WWidget)this.theme.label(":")).widget()).color = this.theme.textSecondaryColor();
/* 121 */       ipList.add((WWidget)this.theme.label(Integer.toString(((Integer)proxy.port.get()).intValue())));
/*     */       
/* 123 */       String s = (proxy.status == Proxy.Status.ALIVE) ? ("" + proxy.latency + "ms") : proxy.status.toString();
/* 124 */       WLabel status = (WLabel)table.add((WWidget)this.theme.label(s)).widget();
/* 125 */       status.color = proxy.status.getColor();
/* 126 */       this.statuses.put(proxy, status);
/*     */       
/* 128 */       WButton refresh = (WButton)table.add((WWidget)this.theme.button(GuiRenderer.RESET)).widget();
/* 129 */       refresh.action = (() -> { Objects.requireNonNull(proxy); MeteorExecutor.execute(proxy::checkStatus);
/* 130 */         }); refresh.tooltip = "Refresh";
/*     */       
/* 132 */       WButton edit = (WButton)table.add((WWidget)this.theme.button(GuiRenderer.EDIT)).widget();
/* 133 */       edit.action = (() -> MeteorClient.mc.method_1507((class_437)new EditProxyScreen(this.theme, proxy, this::reload)));
/*     */       
/* 135 */       WMinus remove = (WMinus)table.add((WWidget)this.theme.minus()).widget();
/* 136 */       remove.action = (() -> {
/*     */           Proxies.get().remove(proxy);
/*     */           
/*     */           reload();
/*     */         });
/* 141 */       table.row(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25393() {
/* 147 */     if ((Proxies.get()).refreshing) {
/* 148 */       if (this.cleanButton.getText().equals("Cleanup")) {
/* 149 */         this.cleanButton.set("---", "---");
/*     */       }
/* 151 */       if (this.timer > 2) {
/* 152 */         this.refreshButton.set(getNext(this.refreshButton));
/* 153 */         this.timer = 0;
/*     */       } else {
/* 155 */         this.timer++;
/*     */       } 
/*     */     } else {
/* 158 */       if (!this.refreshButton.getText().equals("Refresh")) {
/* 159 */         this.refreshButton.set("Refresh");
/*     */       }
/* 161 */       if (!this.cleanButton.getText().equals("Cleanup")) {
/* 162 */         this.cleanButton.set("Cleanup", "Confirm");
/*     */       }
/*     */     } 
/*     */     
/* 166 */     for (Map.Entry<Proxy, WLabel> entry : this.statuses.entrySet()) {
/* 167 */       Proxy proxy = entry.getKey();
/* 168 */       WLabel label = entry.getValue();
/*     */ 
/*     */       
/* 171 */       if (label.get().equals(proxy.status.toString()))
/*     */         continue; 
/* 173 */       label.set((proxy.status == Proxy.Status.ALIVE) ? ("" + proxy.latency + "ms") : proxy.status.toString());
/* 174 */       label.color = proxy.status.getColor();
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getNext(WButton b) {
/* 179 */     switch (b.getText()) { case "Refresh": case "oo0": case "ooo": case "0oo": case "o0o":  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       "Refresh";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean toClipboard() {
/* 190 */     return NbtUtils.toClipboard((ISerializable)Proxies.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fromClipboard() {
/* 195 */     return NbtUtils.fromClipboard((ISerializable)Proxies.get());
/*     */   }
/*     */   
/*     */   protected static class EditProxyScreen extends EditSystemScreen<Proxy> {
/*     */     public EditProxyScreen(GuiTheme theme, Proxy value, Runnable reload) {
/* 200 */       super(theme, value, reload);
/*     */     }
/*     */ 
/*     */     
/*     */     public Proxy create() {
/* 205 */       return (new Proxy.Builder()).build();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean save() {
/* 210 */       Objects.requireNonNull(this.value); MeteorExecutor.execute(this.value::checkStatus);
/* 211 */       return (this.value.resolveAddress() && (!this.isNew || Proxies.get().add(this.value)));
/*     */     }
/*     */ 
/*     */     
/*     */     public Settings getSettings() {
/* 216 */       return this.value.settings;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ConfigScreen extends WindowScreen {
/*     */     private WContainer settingsContainer;
/*     */     
/*     */     public ConfigScreen(GuiTheme theme) {
/* 224 */       super(theme, "Proxies Config");
/*     */     }
/*     */ 
/*     */     
/*     */     public void initWidgets() {
/* 229 */       this.settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().minWidth(400.0D).widget();
/* 230 */       this.settingsContainer.add(this.theme.settings((Proxies.get()).settings)).expandX();
/*     */     }
/*     */ 
/*     */     
/*     */     public void method_25393() {
/* 235 */       (Proxies.get()).settings.tick(this.settingsContainer, this.theme);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\ProxiesScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */