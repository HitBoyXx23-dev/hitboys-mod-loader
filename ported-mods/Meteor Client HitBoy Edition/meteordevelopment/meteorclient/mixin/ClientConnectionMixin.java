/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.proxy.Socks4ProxyHandler;
/*     */ import io.netty.handler.proxy.Socks5ProxyHandler;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Iterator;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.ServerConnectEndEvent;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.AntiPacketKick;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.HighwayBuilder;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxies;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxy;
/*     */ import meteordevelopment.meteorclient.systems.proxies.ProxyType;
/*     */ import meteordevelopment.orbit.ICancellable;
/*     */ import net.minecraft.class_12239;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2535;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2598;
/*     */ import net.minecraft.class_2602;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_8042;
/*     */ import net.minecraft.class_8762;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_2535.class})
/*     */ public abstract class ClientConnectionMixin
/*     */ {
/*     */   @Inject(method = {"method_10770(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/class_2596;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_2535;method_10759(Lnet/minecraft/class_2596;Lnet/minecraft/class_2547;)V", shift = At.Shift.BEFORE)}, cancellable = true)
/*     */   private void onHandlePacket(ChannelHandlerContext channelHandlerContext, class_2596<?> packet, CallbackInfo ci) {
/*  49 */     if (packet instanceof class_8042) { class_8042 bundle = (class_8042)packet;
/*  50 */       for (Iterator<class_2596<? super class_2602>> it = bundle.method_48324().iterator(); it.hasNext();) {
/*  51 */         if (((PacketEvent.Receive)MeteorClient.EVENT_BUS.post((ICancellable)new PacketEvent.Receive(it.next(), (class_2535)this))).isCancelled()) it.remove(); 
/*     */       }  }
/*  53 */     else if (((PacketEvent.Receive)MeteorClient.EVENT_BUS.post((ICancellable)new PacketEvent.Receive(packet, (class_2535)this))).isCancelled()) { ci.cancel(); }
/*     */   
/*     */   }
/*     */   @Inject(method = {"method_10747(Lnet/minecraft/class_2561;)V"}, at = {@At("HEAD")})
/*     */   private void disconnect(class_2561 disconnectReason, CallbackInfo ci) {
/*  58 */     if (((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive()) {
/*  59 */       class_5250 text = class_2561.method_43470("%n%n%s[%sHighway Builder%s] Statistics:%n".formatted(new Object[] { class_124.field_1080, class_124.field_1078, class_124.field_1080 }));
/*  60 */       text.method_10852((class_2561)((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).getStatsText());
/*     */       
/*  62 */       ((class_5250)disconnectReason).method_10852((class_2561)text);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_52271(Ljava/net/InetSocketAddress;Lnet/minecraft/class_12239;Lnet/minecraft/class_2535;)Lio/netty/channel/ChannelFuture;"}, at = {@At("HEAD")})
/*     */   private static void onConnect(InetSocketAddress address, class_12239 backend, class_2535 connection, CallbackInfoReturnable<ChannelFuture> cir) {
/*  68 */     MeteorClient.EVENT_BUS.post(ServerConnectEndEvent.get(address));
/*     */   }
/*     */   
/*     */   @Inject(at = {@At("HEAD")}, method = {"method_10752(Lnet/minecraft/class_2596;Lio/netty/channel/ChannelFutureListener;)V"}, cancellable = true)
/*     */   private void onSendPacketHead(class_2596<?> packet, @Nullable ChannelFutureListener channelFutureListener, CallbackInfo ci) {
/*  73 */     if (((PacketEvent.Send)MeteorClient.EVENT_BUS.post((ICancellable)new PacketEvent.Send(packet, (class_2535)this))).isCancelled()) {
/*  74 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_10752(Lnet/minecraft/class_2596;Lio/netty/channel/ChannelFutureListener;)V"}, at = {@At("TAIL")})
/*     */   private void onSendPacketTail(class_2596<?> packet, @Nullable ChannelFutureListener channelFutureListener, CallbackInfo ci) {
/*  80 */     MeteorClient.EVENT_BUS.post(new PacketEvent.Sent(packet, (class_2535)this));
/*     */   }
/*     */   
/*     */   @Inject(method = {"exceptionCaught"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void exceptionCaught(ChannelHandlerContext context, Throwable throwable, CallbackInfo ci) {
/*  85 */     AntiPacketKick apk = (AntiPacketKick)Modules.get().get(AntiPacketKick.class);
/*  86 */     if (!(throwable instanceof io.netty.handler.timeout.TimeoutException) && !(throwable instanceof net.minecraft.class_2548) && apk.catchExceptions()) {
/*  87 */       if (((Boolean)apk.logExceptions.get()).booleanValue()) apk.warning("Caught exception: %s", new Object[] { throwable }); 
/*  88 */       ci.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_48311"}, at = {@At("RETURN")})
/*     */   private static void onAddHandlers(ChannelPipeline pipeline, class_2598 side, boolean local, class_8762 packetSizeLogger, CallbackInfo ci) {
/*  94 */     if (side != class_2598.field_11942 || local)
/*     */       return; 
/*  96 */     Proxy proxy = Proxies.get().getEnabled();
/*  97 */     if (proxy == null)
/*     */       return; 
/*  99 */     switch ((ProxyType)proxy.type.get()) { case Socks4:
/* 100 */         pipeline.addFirst(new ChannelHandler[] { (ChannelHandler)new Socks4ProxyHandler(new InetSocketAddress((String)proxy.address.get(), ((Integer)proxy.port.get()).intValue()), (String)proxy.username.get()) }); break;
/* 101 */       case Socks5: pipeline.addFirst(new ChannelHandler[] { (ChannelHandler)new Socks5ProxyHandler(new InetSocketAddress((String)proxy.address.get(), ((Integer)proxy.port.get()).intValue()), (String)proxy.username.get(), (String)proxy.password.get()) });
/*     */         break; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientConnectionMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */