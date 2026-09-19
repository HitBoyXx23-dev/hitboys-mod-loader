/*    */ package meteordevelopment.meteorclient.utils.network;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.net.http.HttpClient;
/*    */ import java.net.http.HttpHeaders;
/*    */ import java.net.http.HttpRequest;
/*    */ import java.net.http.HttpResponse;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import javax.net.ssl.SSLSession;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FailedHttpResponse<T>
/*    */   extends Record
/*    */   implements HttpResponse<T>
/*    */ {
/*    */   private final HttpRequest request;
/*    */   private final Exception exception;
/*    */   
/*    */   public FailedHttpResponse(HttpRequest request, Exception exception) {
/* 26 */     this.request = request; this.exception = exception; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse<TT;>; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse<TT;>; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 26 */     //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/network/FailedHttpResponse<TT;>; } public HttpRequest request() { return this.request; } public Exception exception() { return this.exception; }
/*    */   
/*    */   public int statusCode() {
/* 29 */     return 400;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<HttpResponse<T>> previousResponse() {
/* 34 */     return Optional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpHeaders headers() {
/* 39 */     return HttpHeaders.of(Map.of(), (s1, s2) -> true);
/*    */   }
/*    */ 
/*    */   
/*    */   public T body() {
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<SSLSession> sslSession() {
/* 49 */     return Optional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public URI uri() {
/* 54 */     return this.request.uri();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public HttpClient.Version version() {
/* 60 */     return this.request.version().orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\network\FailedHttpResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */