package ayupitsali.pioneers.network;

import ayupitsali.pioneers.Pioneers;
import ayupitsali.pioneers.util.PioneerStatus;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record PioneerStatusPacket(int pioneerEntityId, int status) implements FabricPacket {
    public static final PacketType<PioneerStatusPacket> TYPE = PacketType.create(
            Identifier.of(Pioneers.MOD_ID, "pioneer_status"), PioneerStatusPacket::new);

    public PioneerStatusPacket(PlayerEntity pioneerEntity, PioneerStatus status) {
        this(pioneerEntity.getId(), status.ordinal());
    }

    public PioneerStatusPacket(PacketByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(pioneerEntityId);
        buf.writeInt(status);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public PlayerEntity getPioneerEntity(World world) {
        return (PlayerEntity) world.getEntityById(pioneerEntityId);
    }

    public PioneerStatus getStatus() {
        return PioneerStatus.values()[status];
    }
}
