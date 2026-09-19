/*     */ package meteordevelopment.meteorclient.utils.network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.http.HttpRequest;
/*     */ import java.net.http.HttpResponse;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Request
/*     */ {
/*     */   private final HttpRequest.Builder builder;
/*     */   private Http.Method method;
/*  49 */   private Consumer<Exception> exceptionHandler = Throwable::printStackTrace;
/*     */   
/*     */   private Request(Http.Method method, String url) {
/*     */     try {
/*  53 */       this.builder = HttpRequest.newBuilder().uri(new URI(url)).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
/*  54 */       this.method = method;
/*  55 */     } catch (URISyntaxException e) {
/*  56 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Request header(String name, String value) {
/*  61 */     this.builder.header(name, value);
/*     */     
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public Request bearer(String token) {
/*  67 */     this.builder.header("Authorization", "Bearer " + token);
/*     */     
/*  69 */     return this;
/*     */   }
/*     */   
/*     */   public Request bodyString(String string) {
/*  73 */     this.builder.header("Content-Type", "text/plain");
/*  74 */     this.builder.method(this.method.name(), HttpRequest.BodyPublishers.ofString(string));
/*  75 */     this.method = null;
/*     */     
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public Request bodyForm(String string) {
/*  81 */     this.builder.header("Content-Type", "application/x-www-form-urlencoded");
/*  82 */     this.builder.method(this.method.name(), HttpRequest.BodyPublishers.ofString(string));
/*  83 */     this.method = null;
/*     */     
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public Request bodyJson(String string) {
/*  89 */     this.builder.header("Content-Type", "application/json");
/*  90 */     this.builder.method(this.method.name(), HttpRequest.BodyPublishers.ofString(string));
/*  91 */     this.method = null;
/*     */     
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public Request bodyJson(Object object) {
/*  97 */     this.builder.header("Content-Type", "application/json");
/*  98 */     this.builder.method(this.method.name(), HttpRequest.BodyPublishers.ofString(Http.GSON.toJson(object)));
/*  99 */     this.method = null;
/*     */     
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public Request ignoreExceptions() {
/* 105 */     this.exceptionHandler = (e -> { 
/* 106 */       }); return this;
/*     */   }
/*     */   
/*     */   public Request exceptionHandler(Consumer<Exception> exceptionHandler) {
/* 110 */     this.exceptionHandler = exceptionHandler;
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   private <T> HttpResponse<T> _sendResponse(String accept, HttpResponse.BodyHandler<T> responseBodyHandler) {
/* 115 */     this.builder.header("Accept", accept);
/* 116 */     if (this.method != null) this.builder.method(this.method.name(), HttpRequest.BodyPublishers.noBody());
/*     */     
/* 118 */     HttpRequest request = this.builder.build();
/*     */     
/*     */     try {
/* 121 */       return Http.CLIENT.send(request, responseBodyHandler);
/* 122 */     } catch (IOException|InterruptedException e) {
/* 123 */       this.exceptionHandler.accept(e);
/* 124 */       return new FailedHttpResponse<>(request, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private <T> T _send(String accept, HttpResponse.BodyHandler<T> responseBodyHandler) {
/* 130 */     HttpResponse<T> res = _sendResponse(accept, responseBodyHandler);
/* 131 */     return (res.statusCode() == 200) ? res.body() : null;
/*     */   }
/*     */   
/*     */   public void send() {
/* 135 */     _send("*/*", HttpResponse.BodyHandlers.discarding());
/*     */   }
/*     */   
/*     */   public HttpResponse<Void> sendResponse() {
/* 139 */     return _sendResponse("*/*", HttpResponse.BodyHandlers.discarding());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InputStream sendInputStream() {
/* 144 */     return _send("*/*", HttpResponse.BodyHandlers.ofInputStream());
/*     */   }
/*     */   
/*     */   public HttpResponse<InputStream> sendInputStreamResponse() {
/* 148 */     return _sendResponse("*/*", HttpResponse.BodyHandlers.ofInputStream());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String sendString() {
/* 153 */     return _send("*/*", HttpResponse.BodyHandlers.ofString());
/*     */   }
/*     */   
/*     */   public HttpResponse<String> sendStringResponse() {
/* 157 */     return _sendResponse("*/*", HttpResponse.BodyHandlers.ofString());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Stream<String> sendLines() {
/* 162 */     return _send("*/*", HttpResponse.BodyHandlers.ofLines());
/*     */   }
/*     */   
/*     */   public HttpResponse<Stream<String>> sendLinesResponse() {
/* 166 */     return _sendResponse("*/*", HttpResponse.BodyHandlers.ofLines());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T> T sendJson(Type type) {
/* 171 */     return _send("application/json", JsonBodyHandler.ofJson(Http.GSON, type));
/*     */   }
/*     */   
/*     */   public <T> HttpResponse<T> sendJsonResponse(Type type) {
/* 175 */     return _sendResponse("*/*", JsonBodyHandler.ofJson(Http.GSON, type));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\network\Http$Request.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */