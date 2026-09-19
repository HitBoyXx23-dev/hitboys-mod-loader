/*     */ package meteordevelopment.meteorclient.utils.misc;
/*     */ 
/*     */ import com.mojang.brigadier.ImmutableStringReader;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2509;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_2522;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_7157;
/*     */ import net.minecraft.class_7923;
/*     */ import net.minecraft.class_9323;
/*     */ import net.minecraft.class_9331;
/*     */ 
/*     */ public class ComponentMapReader {
/*     */   private static final DynamicCommandExceptionType UNKNOWN_COMPONENT_EXCEPTION;
/*     */   
/*     */   static {
/*  35 */     UNKNOWN_COMPONENT_EXCEPTION = new DynamicCommandExceptionType(id -> class_2561.method_54159("arguments.item.component.unknown", new Object[] { id }));
/*     */   }
/*     */   
/*  38 */   private static final SimpleCommandExceptionType COMPONENT_EXPECTED_EXCEPTION = new SimpleCommandExceptionType((Message)class_2561.method_43471("arguments.item.component.expected")); private static final DynamicCommandExceptionType REPEATED_COMPONENT_EXCEPTION; static {
/*  39 */     REPEATED_COMPONENT_EXCEPTION = new DynamicCommandExceptionType(type -> class_2561.method_54159("arguments.item.component.repeated", new Object[] { type }));
/*     */ 
/*     */     
/*  42 */     MALFORMED_COMPONENT_EXCEPTION = new Dynamic2CommandExceptionType((type, error) -> class_2561.method_54159("arguments.item.component.malformed", new Object[] { type, error }));
/*     */   }
/*     */   private static final Dynamic2CommandExceptionType MALFORMED_COMPONENT_EXCEPTION;
/*  45 */   private static final class_2522<class_2520> SNBT_READER = class_2522.method_68662((DynamicOps)class_2509.field_11560);
/*     */   private final DynamicOps<class_2520> nbtOps;
/*     */   
/*     */   public ComponentMapReader(class_7157 commandRegistryAccess) {
/*  49 */     this.nbtOps = (DynamicOps<class_2520>)commandRegistryAccess.method_57093((DynamicOps)class_2509.field_11560);
/*     */   }
/*     */   
/*     */   public class_9323 consume(StringReader reader) throws CommandSyntaxException {
/*  53 */     int cursor = reader.getCursor();
/*     */     
/*     */     try {
/*  56 */       return (new Reader(reader, this.nbtOps)).read();
/*  57 */     } catch (CommandSyntaxException e) {
/*  58 */       reader.setCursor(cursor);
/*  59 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public CompletableFuture<Suggestions> getSuggestions(SuggestionsBuilder builder) {
/*  64 */     StringReader stringReader = new StringReader(builder.getInput());
/*  65 */     stringReader.setCursor(builder.getStart());
/*  66 */     Reader reader = new Reader(stringReader, this.nbtOps);
/*     */     
/*     */     try {
/*  69 */       reader.read();
/*  70 */     } catch (CommandSyntaxException commandSyntaxException) {}
/*     */ 
/*     */     
/*  73 */     return reader.suggestor.apply(builder.createOffset(stringReader.getCursor()));
/*     */   }
/*     */   
/*     */   private static class Reader {
/*  77 */     private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> SUGGEST_DEFAULT = SuggestionsBuilder::buildFuture;
/*     */     private final StringReader reader;
/*     */     private final DynamicOps<class_2520> nbtOps;
/*  80 */     public Function<SuggestionsBuilder, CompletableFuture<Suggestions>> suggestor = this::suggestBracket;
/*     */     
/*     */     public Reader(StringReader reader, DynamicOps<class_2520> nbtOps) {
/*  83 */       this.reader = reader;
/*  84 */       this.nbtOps = nbtOps;
/*     */     }
/*     */     
/*     */     public class_9323 read() throws CommandSyntaxException {
/*  88 */       class_9323.class_9324 builder = class_9323.method_57827();
/*     */       
/*  90 */       this.reader.expect('[');
/*  91 */       this.suggestor = this::suggestComponentType;
/*  92 */       ReferenceArraySet<class_9331<?>> referenceArraySet = new ReferenceArraySet();
/*     */       
/*  94 */       while (this.reader.canRead() && this.reader.peek() != ']') {
/*  95 */         this.reader.skipWhitespace();
/*  96 */         class_9331<?> dataComponentType = readComponentType(this.reader);
/*  97 */         if (!referenceArraySet.add(dataComponentType)) {
/*  98 */           throw ComponentMapReader.REPEATED_COMPONENT_EXCEPTION.create(dataComponentType);
/*     */         }
/*     */         
/* 101 */         this.suggestor = this::suggestEqual;
/* 102 */         this.reader.skipWhitespace();
/* 103 */         this.reader.expect('=');
/* 104 */         this.suggestor = SUGGEST_DEFAULT;
/* 105 */         this.reader.skipWhitespace();
/* 106 */         readComponentValue(this.reader, builder, dataComponentType);
/* 107 */         this.reader.skipWhitespace();
/* 108 */         this.suggestor = this::suggestEndOfComponent;
/* 109 */         if (!this.reader.canRead() || this.reader.peek() != ',') {
/*     */           break;
/*     */         }
/*     */         
/* 113 */         this.reader.skip();
/* 114 */         this.reader.skipWhitespace();
/* 115 */         this.suggestor = this::suggestComponentType;
/* 116 */         if (!this.reader.canRead()) {
/* 117 */           throw ComponentMapReader.COMPONENT_EXPECTED_EXCEPTION.createWithContext(this.reader);
/*     */         }
/*     */       } 
/*     */       
/* 121 */       this.reader.expect(']');
/* 122 */       this.suggestor = SUGGEST_DEFAULT;
/*     */       
/* 124 */       return builder.method_57838();
/*     */     }
/*     */     
/*     */     public static class_9331<?> readComponentType(StringReader reader) throws CommandSyntaxException {
/* 128 */       if (!reader.canRead()) {
/* 129 */         throw ComponentMapReader.COMPONENT_EXPECTED_EXCEPTION.createWithContext(reader);
/*     */       }
/* 131 */       int i = reader.getCursor();
/* 132 */       class_2960 identifier = class_2960.method_12835(reader);
/* 133 */       class_9331<?> dataComponentType = (class_9331)class_7923.field_49658.method_63535(identifier);
/* 134 */       if (dataComponentType != null && !dataComponentType.method_57877()) {
/* 135 */         return dataComponentType;
/*     */       }
/* 137 */       reader.setCursor(i);
/* 138 */       throw ComponentMapReader.UNKNOWN_COMPONENT_EXCEPTION.createWithContext(reader, identifier);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private CompletableFuture<Suggestions> suggestComponentType(SuggestionsBuilder builder) {
/* 144 */       String string = builder.getRemaining().toLowerCase(Locale.ROOT);
/* 145 */       class_2172.method_9268(class_7923.field_49658.method_29722(), string, entry -> ((class_5321)entry.getKey()).method_29177(), entry -> {
/*     */             class_9331<?> dataComponentType = (class_9331)entry.getValue();
/*     */             if (dataComponentType.method_57875() != null) {
/*     */               class_2960 identifier = ((class_5321)entry.getKey()).method_29177();
/*     */               builder.suggest(identifier.toString() + "=");
/*     */             } 
/*     */           });
/* 152 */       return builder.buildFuture();
/*     */     }
/*     */     
/*     */     private <T> void readComponentValue(StringReader reader, class_9323.class_9324 builder, class_9331<T> type) throws CommandSyntaxException {
/* 156 */       int i = reader.getCursor();
/* 157 */       class_2520 nbtElement = (class_2520)ComponentMapReader.SNBT_READER.method_67319(reader);
/* 158 */       DataResult<T> dataResult = type.method_57876().parse(this.nbtOps, nbtElement);
/* 159 */       builder.method_57840(type, dataResult.getOrThrow(error -> {
/*     */               reader.setCursor(i);
/*     */               return ComponentMapReader.MALFORMED_COMPONENT_EXCEPTION.createWithContext((ImmutableStringReader)reader, type.toString(), error);
/*     */             }));
/*     */     }
/*     */     
/*     */     private CompletableFuture<Suggestions> suggestBracket(SuggestionsBuilder builder) {
/* 166 */       if (builder.getRemaining().isEmpty()) {
/* 167 */         builder.suggest(String.valueOf('['));
/*     */       }
/*     */       
/* 170 */       return builder.buildFuture();
/*     */     }
/*     */     
/*     */     private CompletableFuture<Suggestions> suggestEndOfComponent(SuggestionsBuilder builder) {
/* 174 */       if (builder.getRemaining().isEmpty()) {
/* 175 */         builder.suggest(String.valueOf(','));
/* 176 */         builder.suggest(String.valueOf(']'));
/*     */       } 
/*     */       
/* 179 */       return builder.buildFuture();
/*     */     }
/*     */     
/*     */     private CompletableFuture<Suggestions> suggestEqual(SuggestionsBuilder builder) {
/* 183 */       if (builder.getRemaining().isEmpty()) {
/* 184 */         builder.suggest(String.valueOf('='));
/*     */       }
/*     */       
/* 187 */       return builder.buildFuture();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\ComponentMapReader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */