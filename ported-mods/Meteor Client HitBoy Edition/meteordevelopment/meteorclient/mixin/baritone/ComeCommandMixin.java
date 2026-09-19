/*    */ package meteordevelopment.meteorclient.mixin.baritone;
/*    */ 
/*    */ import baritone.api.pathing.goals.GoalBlock;
/*    */ import baritone.command.defaults.ComeCommand;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({ComeCommand.class})
/*    */ public abstract class ComeCommandMixin
/*    */ {
/*    */   @ModifyArgs(method = {"execute"}, at = @At(value = "INVOKE", target = "Lbaritone/api/process/ICustomGoalProcess;setGoalAndPath(Lbaritone/api/pathing/goals/Goal;)V"), remap = false)
/*    */   private void getComeCommandTarget(Args args) {
/* 23 */     Freecam freecam = (Freecam)Modules.get().get(Freecam.class);
/* 24 */     if (freecam.isActive()) {
/* 25 */       float tickDelta = MeteorClient.mc.method_61966().method_60637(true);
/* 26 */       args.set(0, new GoalBlock((int)freecam.getX(tickDelta), (int)freecam.getY(tickDelta), (int)freecam.getZ(tickDelta)));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\baritone\ComeCommandMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */