/*    */ package meteordevelopment.meteorclient.asm.transformers;
/*    */ 
/*    */ import java.util.ListIterator;
/*    */ import meteordevelopment.meteorclient.asm.AsmTransformer;
/*    */ import meteordevelopment.meteorclient.asm.Descriptor;
/*    */ import meteordevelopment.meteorclient.asm.MethodInfo;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.AntiPacketKick;
/*    */ import org.objectweb.asm.Label;
/*    */ import org.objectweb.asm.Type;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.tree.InsnList;
/*    */ import org.objectweb.asm.tree.JumpInsnNode;
/*    */ import org.objectweb.asm.tree.LabelNode;
/*    */ import org.objectweb.asm.tree.LdcInsnNode;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.TypeInsnNode;
/*    */ 
/*    */ public class PacketInflaterTransformer extends AsmTransformer {
/*    */   private final MethodInfo decodeMethod;
/*    */   
/*    */   public PacketInflaterTransformer() {
/* 24 */     super(mapClassName("net/minecraft/class_2532"));
/*    */     
/* 26 */     this.decodeMethod = new MethodInfo("net/minecraft/class_2532", "decode", new Descriptor(new String[] { "Lio/netty/channel/ChannelHandlerContext;", "Lio/netty/buffer/ByteBuf;", "Ljava/util/List;", "V" }, ), true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void transform(ClassNode klass) {
/* 31 */     MethodNode method = getMethod(klass, this.decodeMethod);
/* 32 */     if (method == null) error("[Meteor Client] Could not find method PacketInflater.decode()");
/*    */     
/* 34 */     int newCount = 0;
/* 35 */     LabelNode label = new LabelNode(new Label());
/*    */ 
/*    */     
/* 38 */     for (ListIterator<AbstractInsnNode> listIterator = method.instructions.iterator(); listIterator.hasNext(); ) { AbstractInsnNode insn = listIterator.next();
/* 39 */       if (insn instanceof TypeInsnNode) { TypeInsnNode typeInsn = (TypeInsnNode)insn; if (typeInsn.getOpcode() == 187 && typeInsn.desc.equals("io/netty/handler/codec/DecoderException")) {
/* 40 */           newCount++;
/*    */           
/* 42 */           if (newCount == 2) {
/* 43 */             InsnList list = new InsnList();
/*    */             
/* 45 */             list.add((AbstractInsnNode)new MethodInsnNode(184, "meteordevelopment/meteorclient/systems/modules/Modules", "get", "()Lmeteordevelopment/meteorclient/systems/modules/Modules;", false));
/* 46 */             list.add((AbstractInsnNode)new LdcInsnNode(Type.getType(AntiPacketKick.class)));
/* 47 */             list.add((AbstractInsnNode)new MethodInsnNode(182, "meteordevelopment/meteorclient/systems/modules/Modules", "isActive", "(Ljava/lang/Class;)Z", false));
/*    */             
/* 49 */             list.add((AbstractInsnNode)new JumpInsnNode(154, label));
/*    */             
/* 51 */             method.instructions.insertBefore(insn, list);
/*    */           }  continue;
/*    */         }  }
/* 54 */        if (newCount == 2 && insn.getOpcode() == 191) {
/* 55 */         method.instructions.insert(insn, (AbstractInsnNode)label);
/*    */         
/*    */         return;
/*    */       }  }
/*    */     
/* 60 */     error("[Meteor Client] Failed to modify PacketInflater.decode()");
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\asm\transformers\PacketInflaterTransformer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */