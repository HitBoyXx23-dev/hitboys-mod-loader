/*     */ package meteordevelopment.meteorclient.gui.screens;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.meteor.ActiveModulesChangedEvent;
/*     */ import meteordevelopment.meteorclient.events.meteor.ModuleBindChangedEvent;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WKeybind;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WFavorite;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.prompts.OkPrompt;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   private final Module module;
/*     */   private WContainer settingsContainer;
/*     */   private WKeybind keybind;
/*     */   private WCheckbox active;
/*     */   
/*     */   public ModuleScreen(GuiTheme theme, Module module) {
/*  43 */     super(theme, (WWidget)theme.favorite(module.favorite), module.title);
/*  44 */     ((WFavorite)this.window.icon).action = (() -> module.favorite = ((WFavorite)this.window.icon).checked);
/*     */     
/*  46 */     this.module = module;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  52 */     add((WWidget)this.theme.label(this.module.description, Utils.getWindowWidth() / 2.0D));
/*     */     
/*  54 */     if (this.module.addon != null && this.module.addon != MeteorClient.ADDON) {
/*  55 */       WHorizontalList addon = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*  56 */       addon.add((WWidget)this.theme.label("From: ").color(this.theme.textSecondaryColor())).widget();
/*  57 */       addon.add((WWidget)this.theme.label(this.module.addon.name).color(this.module.addon.color)).widget();
/*     */     } 
/*     */ 
/*     */     
/*  61 */     if (!this.module.settings.groups.isEmpty()) {
/*  62 */       this.settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().widget();
/*  63 */       this.settingsContainer.add(this.theme.settings(this.module.settings)).expandX();
/*     */     } 
/*     */ 
/*     */     
/*  67 */     WWidget widget = this.module.getWidget(this.theme);
/*     */     
/*  69 */     if (widget != null) {
/*  70 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*  71 */       Cell<WWidget> cell = add(widget);
/*  72 */       if (widget instanceof WContainer) cell.expandX();
/*     */     
/*     */     } 
/*     */     
/*  76 */     WSection section = (WSection)add((WWidget)this.theme.section("Bind", true)).expandX().widget();
/*     */ 
/*     */     
/*  79 */     WHorizontalList bind = (WHorizontalList)section.add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */     
/*  81 */     bind.add((WWidget)this.theme.label("Bind: "));
/*  82 */     this.keybind = (WKeybind)bind.add((WWidget)this.theme.keybind(this.module.keybind)).expandX().widget();
/*  83 */     this.keybind.actionOnSet = (() -> Modules.get().setModuleToBind(this.module));
/*     */     
/*  85 */     WButton reset = (WButton)bind.add((WWidget)this.theme.button(GuiRenderer.RESET)).expandCellX().right().widget();
/*  86 */     Objects.requireNonNull(this.keybind); reset.action = this.keybind::resetBind;
/*  87 */     reset.tooltip = "Reset";
/*     */ 
/*     */     
/*  90 */     WHorizontalList tobr = (WHorizontalList)section.add((WWidget)this.theme.horizontalList()).widget();
/*     */     
/*  92 */     tobr.add((WWidget)this.theme.label("Toggle on bind release: "));
/*  93 */     WCheckbox tobrC = (WCheckbox)tobr.add((WWidget)this.theme.checkbox(this.module.toggleOnBindRelease)).widget();
/*  94 */     tobrC.action = (() -> this.module.toggleOnBindRelease = tobrC.checked);
/*     */ 
/*     */     
/*  97 */     WHorizontalList cf = (WHorizontalList)section.add((WWidget)this.theme.horizontalList()).widget();
/*     */     
/*  99 */     cf.add((WWidget)this.theme.label("Chat Feedback: "));
/* 100 */     WCheckbox cfC = (WCheckbox)cf.add((WWidget)this.theme.checkbox(this.module.chatFeedback)).widget();
/* 101 */     cfC.action = (() -> this.module.chatFeedback = cfC.checked);
/*     */     
/* 103 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */ 
/*     */     
/* 106 */     WHorizontalList bottom = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */ 
/*     */     
/* 109 */     bottom.add((WWidget)this.theme.label("Active: "));
/* 110 */     this.active = (WCheckbox)bottom.add((WWidget)this.theme.checkbox(this.module.isActive())).expandCellX().widget();
/* 111 */     this.active.action = (() -> {
/*     */         if (this.module.isActive() != this.active.checked) {
/*     */           this.module.toggle();
/*     */         }
/*     */       });
/* 116 */     WHorizontalList sharing = (WHorizontalList)bottom.add((WWidget)this.theme.horizontalList()).right().widget();
/* 117 */     WButton copy = (WButton)sharing.add((WWidget)this.theme.button(GuiRenderer.COPY)).widget();
/* 118 */     copy.action = (() -> {
/*     */         if (toClipboard()) {
/*     */           ((OkPrompt)((OkPrompt)((OkPrompt)((OkPrompt)((OkPrompt)OkPrompt.create().title("Module copied!")).message("The settings for this module are now in your clipboard.")).message("You can also copy settings using Ctrl+C.")).message("Settings can be imported using Ctrl+V or the paste button.")).id("config-sharing-guide")).show();
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     copy.tooltip = "Copy config";
/*     */     
/* 131 */     WButton paste = (WButton)sharing.add((WWidget)this.theme.button(GuiRenderer.PASTE)).widget();
/* 132 */     paste.action = this::fromClipboard;
/* 133 */     paste.tooltip = "Paste config";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25422() {
/* 138 */     return !Modules.get().isBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25393() {
/* 143 */     super.method_25393();
/*     */     
/* 145 */     this.module.settings.tick(this.settingsContainer, this.theme);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onModuleBindChanged(ModuleBindChangedEvent event) {
/* 150 */     this.keybind.reset();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onActiveModulesChanged(ActiveModulesChangedEvent event) {
/* 155 */     this.active.checked = this.module.isActive();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean toClipboard() {
/* 160 */     class_2487 tag = new class_2487();
/*     */     
/* 162 */     tag.method_10582("name", this.module.name);
/*     */     
/* 164 */     class_2487 settingsTag = this.module.settings.toTag();
/* 165 */     if (!settingsTag.method_33133()) tag.method_10566("settings", (class_2520)settingsTag);
/*     */     
/* 167 */     return NbtUtils.toClipboard(tag);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fromClipboard() {
/* 172 */     class_2487 tag = NbtUtils.fromClipboard();
/* 173 */     if (tag == null) return false; 
/* 174 */     if (!tag.method_68564("name", "").equals(this.module.name)) return false;
/*     */     
/* 176 */     Optional<class_2487> settings = tag.method_10562("settings");
/*     */     
/* 178 */     if (settings.isPresent()) { this.module.settings.fromTag(settings.get()); }
/* 179 */     else { this.module.settings.reset(); }
/*     */     
/* 181 */     class_437 class_437 = this.parent; if (class_437 instanceof WidgetScreen) { WidgetScreen p = (WidgetScreen)class_437; p.reload(); }
/* 182 */      reload();
/*     */     
/* 184 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\ModuleScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */