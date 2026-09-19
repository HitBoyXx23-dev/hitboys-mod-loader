/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.NameProtect;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxies;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxy;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_500;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_500.class})
/*     */ public abstract class MultiplayerScreenMixin
/*     */   extends class_437
/*     */ {
/*     */   @Unique
/*     */   private int textColor1;
/*     */   @Unique
/*     */   private int textColor2;
/*     */   @Unique
/*     */   private String loggedInAs;
/*     */   @Unique
/*     */   private int loggedInAsLength;
/*     */   @Unique
/*     */   private class_4185 accounts;
/*     */   @Unique
/*     */   private class_4185 proxies;
/*     */   @Unique
/*     */   private static final int BUTTON_WIDTH = 75;
/*     */   @Unique
/*     */   private static final int BUTTON_HEIGHT = 20;
/*     */   @Unique
/*     */   private static final int MARGIN = 3;
/*     */   @Unique
/*     */   private static final int GAP = 2;
/*     */   
/*     */   public MultiplayerScreenMixin(class_2561 title) {
/*  56 */     super(title);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_48640"}, at = {@At("TAIL")})
/*     */   private void onInit(CallbackInfo info) {
/*  61 */     this.textColor1 = Color.fromRGBA(255, 255, 255, 255);
/*  62 */     this.textColor2 = Color.fromRGBA(175, 175, 175, 255);
/*     */     
/*  64 */     this.loggedInAs = "Logged in as ";
/*  65 */     this.loggedInAsLength = this.field_22793.method_1727(this.loggedInAs);
/*     */     
/*  67 */     if (this.accounts == null) {
/*  68 */       this.accounts = (class_4185)method_37063((class_364)(new class_4185.class_7840(
/*  69 */             (class_2561)class_2561.method_43470("Accounts"), button -> this.field_22787.method_1507((class_437)GuiThemes.get().accountsScreen())))
/*  70 */           .method_46437(75, 20)
/*  71 */           .method_46431());
/*     */     }
/*     */ 
/*     */     
/*  75 */     if (this.proxies == null) {
/*  76 */       this.proxies = (class_4185)method_37063((class_364)(new class_4185.class_7840(
/*  77 */             (class_2561)class_2561.method_43470("Proxies"), button -> this.field_22787.method_1507((class_437)GuiThemes.get().proxiesScreen())))
/*  78 */           .method_46437(75, 20)
/*  79 */           .method_46431());
/*     */     }
/*     */ 
/*     */     
/*  83 */     Config config = Config.get();
/*  84 */     Config.ButtonPosition accountPos = (Config.ButtonPosition)config.accountButtonAnchor.get();
/*  85 */     Config.ButtonPosition proxiesPos = (Config.ButtonPosition)config.proxiesButtonAnchor.get();
/*  86 */     boolean accountsVisible = (accountPos != Config.ButtonPosition.Hidden);
/*  87 */     boolean proxiesVisible = (proxiesPos != Config.ButtonPosition.Hidden);
/*     */     
/*  89 */     this.accounts.field_22764 = accountsVisible;
/*  90 */     this.proxies.field_22764 = proxiesVisible;
/*     */     
/*  92 */     positionButton(this.accounts, accountPos, (proxiesVisible && proxiesPos == accountPos), true);
/*  93 */     positionButton(this.proxies, proxiesPos, (accountsVisible && accountPos == proxiesPos), false);
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private void positionButton(class_4185 button, Config.ButtonPosition anchor, boolean sharingCorner, boolean isAccounts) {
/*  98 */     int leftOffset = (sharingCorner && isAccounts) ? 77 : 0;
/*  99 */     int rightOffset = (sharingCorner && !isAccounts) ? 77 : 0;
/*     */     
/* 101 */     switch (anchor) { case TopRight:
/* 102 */         button.method_48229(this.field_22789 - 3 - 75 - rightOffset, 3); return;
/* 103 */       case TopLeft: button.method_48229(3 + leftOffset, 3); return;
/* 104 */       case BottomLeft: button.method_48229(3 + leftOffset, this.field_22790 - 3 - 20); return;
/* 105 */       case BottomRight: button.method_48229(this.field_22789 - 3 - 75 - rightOffset, this.field_22790 - 3 - 20); return; }
/* 106 */      button.method_48229(this.field_22789 - 3 - 75 - rightOffset, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25394(class_332 context, int mouseX, int mouseY, float deltaTicks) {
/* 112 */     super.method_25394(context, mouseX, mouseY, deltaTicks);
/*     */     
/* 114 */     Config config = Config.get();
/*     */     
/* 116 */     if (!((Boolean)config.showAccountStatus.get()).booleanValue() && !((Boolean)config.showProxiesStatus.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 121 */     int x = 3;
/* 122 */     if (config.proxiesButtonAnchor.get() != Config.ButtonPosition.Hidden && config.proxiesButtonAnchor.get() == Config.ButtonPosition.TopLeft) {
/* 123 */       x += 77;
/*     */     }
/* 125 */     if (config.accountButtonAnchor.get() != Config.ButtonPosition.Hidden && config.accountButtonAnchor.get() == Config.ButtonPosition.TopLeft) {
/* 126 */       x += 77;
/*     */     }
/*     */     
/* 129 */     int y = 3;
/*     */ 
/*     */     
/* 132 */     if (((Boolean)config.showAccountStatus.get()).booleanValue()) {
/* 133 */       context.method_25303(MeteorClient.mc.field_1772, this.loggedInAs, x, y, this.textColor1);
/* 134 */       context.method_25303(MeteorClient.mc.field_1772, ((NameProtect)Modules.get().get(NameProtect.class)).getName(this.field_22787.method_1548().method_1676()), x + this.loggedInAsLength, y, this.textColor2);
/*     */       
/* 136 */       Objects.requireNonNull(this.field_22793); y += 9 + 2;
/*     */     } 
/*     */     
/* 139 */     if (!((Boolean)config.showProxiesStatus.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 144 */     Proxy proxy = Proxies.get().getEnabled();
/*     */     
/* 146 */     String left = (proxy != null) ? "Using proxy " : "Not using a proxy";
/* 147 */     String right = (proxy != null) ? (((proxy.name.get() != null && !((String)proxy.name.get()).isEmpty()) ? ("(" + (String)proxy.name.get() + ") ") : "") + ((proxy.name.get() != null && !((String)proxy.name.get()).isEmpty()) ? ("(" + (String)proxy.name.get() + ") ") : "") + ":" + (String)proxy.address.get()) : null;
/*     */     
/* 149 */     context.method_25303(MeteorClient.mc.field_1772, left, x, y, this.textColor1);
/* 150 */     if (right != null)
/* 151 */       context.method_25303(MeteorClient.mc.field_1772, right, x + this.field_22793.method_1727(left), y, this.textColor2); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MultiplayerScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */