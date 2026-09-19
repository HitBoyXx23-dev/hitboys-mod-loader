/*     */ package meteordevelopment.meteorclient.systems.proxies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/* 206 */   protected ProxyType type = ProxyType.Socks5;
/* 207 */   protected String address = "";
/* 208 */   protected int port = 0;
/* 209 */   protected String name = "";
/* 210 */   protected String username = "";
/* 211 */   protected String password = "";
/*     */   protected boolean enabled = false;
/*     */   
/*     */   public Builder type(ProxyType type) {
/* 215 */     this.type = type;
/* 216 */     return this;
/*     */   }
/*     */   
/*     */   public Builder address(String address) {
/* 220 */     this.address = address;
/* 221 */     return this;
/*     */   }
/*     */   
/*     */   public Builder port(int port) {
/* 225 */     this.port = port;
/* 226 */     return this;
/*     */   }
/*     */   
/*     */   public Builder name(String name) {
/* 230 */     this.name = name;
/* 231 */     return this;
/*     */   }
/*     */   
/*     */   public Builder username(String username) {
/* 235 */     this.username = username;
/* 236 */     return this;
/*     */   }
/*     */   
/*     */   public Builder password(String password) {
/* 240 */     this.password = password;
/* 241 */     return this;
/*     */   }
/*     */   
/*     */   public Builder enabled(boolean enabled) {
/* 245 */     this.enabled = enabled;
/* 246 */     return this;
/*     */   }
/*     */   
/*     */   public Proxy build() {
/* 250 */     Proxy proxy = new Proxy();
/*     */     
/* 252 */     if (!this.type.equals(proxy.type.getDefaultValue())) proxy.type.set(this.type); 
/* 253 */     if (!this.address.equals(proxy.address.getDefaultValue())) proxy.address.set(this.address); 
/* 254 */     if (this.port != ((Integer)proxy.port.getDefaultValue()).intValue()) proxy.port.set(Integer.valueOf(this.port)); 
/* 255 */     if (!this.name.equals(proxy.name.getDefaultValue())) proxy.name.set(this.name); 
/* 256 */     if (!this.username.equals(proxy.username.getDefaultValue())) proxy.username.set(this.username); 
/* 257 */     if (!this.password.equals(proxy.password.getDefaultValue())) proxy.password.set(this.password); 
/* 258 */     if (this.enabled != ((Boolean)proxy.enabled.getDefaultValue()).booleanValue()) proxy.enabled.set(Boolean.valueOf(this.enabled));
/*     */     
/* 260 */     return proxy;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\proxies\Proxy$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */