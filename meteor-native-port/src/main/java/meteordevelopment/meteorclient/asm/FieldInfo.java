/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.asm;

import com.hitboy.loader.api.HitBoyPlatform;
import org.objectweb.asm.tree.FieldInsnNode;

public class FieldInfo {
    private String owner, name, descriptor;

    public FieldInfo(String owner, String name, Descriptor descriptor, boolean map) {
        if (map) {
            HitBoyPlatform.MappingService mappings = HitBoyPlatform.getMappingService();
            String ownerDot = owner.replace('/', '.');

            if (owner != null) this.owner = mappings.mapClassName("intermediary", ownerDot).replace('.', '/');
            if (name != null && descriptor != null) this.name = mappings.mapFieldName("intermediary", ownerDot, name, descriptor.toString(false, false));
        }
        else {
            this.owner = owner;
            this.name = name;
        }

        if (descriptor != null) this.descriptor = descriptor.toString(false, map);
    }

    public boolean equals(FieldInsnNode insn) {
        return (owner == null || insn.owner.equals(owner)) && (name == null || insn.name.equals(name)) && (descriptor == null || insn.desc.equals(descriptor));
    }
}
