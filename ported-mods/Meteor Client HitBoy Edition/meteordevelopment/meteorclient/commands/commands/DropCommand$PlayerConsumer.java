package meteordevelopment.meteorclient.commands.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.class_746;

@FunctionalInterface
interface PlayerConsumer {
  void accept(class_746 paramclass_746) throws CommandSyntaxException;
}


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\DropCommand$PlayerConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */