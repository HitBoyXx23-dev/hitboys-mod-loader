/*     */ package meteordevelopment.meteorclient.gui.screens;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.util.regex.Matcher;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxies;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxy;
/*     */ import meteordevelopment.meteorclient.systems.proxies.ProxyType;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ 
/*     */ public class ProxiesImportScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   private final File file;
/*     */   
/*     */   public ProxiesImportScreen(GuiTheme theme, File file) {
/*  28 */     super(theme, "Import Proxies");
/*  29 */     this.file = file;
/*  30 */     onClosed(() -> {
/*     */           class_437 patt0$temp = this.parent;
/*     */           if (patt0$temp instanceof ProxiesScreen) {
/*     */             ProxiesScreen screen = (ProxiesScreen)patt0$temp;
/*     */             screen.reload();
/*     */           } 
/*     */         });
/*     */   }
/*     */   public void initWidgets() {
/*  39 */     if (this.file.exists() && this.file.isFile()) {
/*  40 */       add((WWidget)this.theme.label("Importing proxies from " + this.file.getName() + "...").color(Color.GREEN));
/*  41 */       WVerticalList list = (WVerticalList)((WSection)add((WWidget)this.theme.section("Log", false)).widget()).add((WWidget)this.theme.verticalList()).expandX().widget();
/*  42 */       Proxies proxies = Proxies.get();
/*     */       try {
/*  44 */         int success = 0, fail = 0;
/*  45 */         for (String line : Files.readAllLines(this.file.toPath())) {
/*     */           
/*  47 */           Proxy proxy = null;
/*     */           
/*  49 */           Matcher matcher = Proxies.PROXY_PATTERN.matcher(line);
/*  50 */           if (matcher.matches()) {
/*  51 */             String address = matcher.group(2).replaceAll("\\b0+\\B", "");
/*  52 */             int port = Integer.parseInt(matcher.group(3));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  59 */             proxy = (new Proxy.Builder()).address(address).port(port).name((matcher.group(1) != null) ? matcher.group(1) : (address + ":" + address)).type((matcher.group(4) != null) ? ProxyType.parse(matcher.group(4)) : ProxyType.Socks4).build();
/*     */           } 
/*     */           
/*  62 */           matcher = Proxies.PROXY_PATTERN_WEBSHARE.matcher(line);
/*  63 */           if (proxy == null && matcher.matches()) {
/*  64 */             String address = matcher.group(1).replaceAll("\\b0+\\B", "");
/*  65 */             int port = Integer.parseInt(matcher.group(2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  74 */             proxy = (new Proxy.Builder()).address(address).port(port).name(address + ":" + address).username((matcher.group(3) != null) ? matcher.group(3) : "").password((matcher.group(4) != null) ? matcher.group(4) : "").type(ProxyType.Socks5).build();
/*     */           } 
/*     */           
/*  77 */           matcher = Proxies.PROXY_PATTERN_URI.matcher(line);
/*  78 */           if (proxy == null && matcher.matches()) {
/*  79 */             String address = matcher.group("addr").replaceAll("\\b0+\\B", "");
/*  80 */             int port = Integer.parseInt(matcher.group("port"));
/*     */             
/*  82 */             ProxyType type = ProxyType.parse(matcher.group(1));
/*  83 */             if (type == null) {
/*  84 */               if (matcher.group(1) != null && matcher.group(1).equals("socks")) { type = ProxyType.Socks5; }
/*     */               
/*  86 */               else if (matcher.group("pass") != null) { type = ProxyType.Socks5; }
/*  87 */               else { type = ProxyType.Socks4; }
/*     */             
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  97 */             proxy = (new Proxy.Builder()).address(address).port(port).name(address + ":" + address).username((matcher.group("user") != null) ? matcher.group("user") : "").password((matcher.group("pass") != null) ? matcher.group("pass") : "").type(type).build();
/*     */           } 
/*     */           
/* 100 */           if (proxy == null) {
/* 101 */             list.add((WWidget)this.theme.label("Unrecognised proxy format: " + line).color(Color.RED));
/* 102 */             fail++; continue;
/*     */           } 
/* 104 */           if (proxies.add(proxy)) {
/* 105 */             list.add((WWidget)this.theme.label("Imported proxy: " + (String)proxy.name.get()).color(Color.GREEN));
/* 106 */             success++;
/*     */             continue;
/*     */           } 
/* 109 */           list.add((WWidget)this.theme.label("Proxy already exists: " + (String)proxy.name.get()).color(Color.ORANGE));
/* 110 */           fail++;
/*     */         } 
/*     */ 
/*     */         
/* 114 */         add((WWidget)this.theme
/* 115 */             .label("Successfully imported " + success + "/" + fail + success + " proxies.")
/* 116 */             .color(Utils.lerp(Color.RED, Color.GREEN, success / (success + fail))));
/*     */       }
/* 118 */       catch (IOException e) {
/* 119 */         MeteorClient.LOG.error("An error occurred while importing the proxy file", e);
/*     */       } 
/*     */     } else {
/* 122 */       add((WWidget)this.theme.label("Invalid File!"));
/*     */     } 
/* 124 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/* 125 */     WButton refresh = (WButton)add((WWidget)this.theme.button("Check proxies")).expandX().widget();
/* 126 */     refresh.action = (() -> {
/*     */         Proxies.get().checkProxies(false);
/*     */         
/*     */         method_25419();
/*     */       });
/* 131 */     WButton btnBack = (WButton)add((WWidget)this.theme.button("Back")).expandX().widget();
/* 132 */     btnBack.action = this::method_25419;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\ProxiesImportScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */