package ayupitsali.pioneers.network;

import ayupitsali.pioneers.Pioneers;
import ayupitsali.pioneers.util.PioneerStatus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record PioneerStatusPayload(int pioneerEntityId, int status) implements CustomPayload {
    public static final Id<PioneerStatusPayload> ID = new Id<>(Identifier.of(Pioneers.MOD_ID, "pioneer_status"));
    public static final PacketCodec<RegistryByteBuf, PioneerStatusPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, PioneerStatusPayload::pioneerEntityId,
            PacketCodecs.INTEGER, PioneerStatusPayload::status,
            PioneerStatusPayload::new
    );

    public PioneerStatusPayload(PlayerEntity pioneerEntity, PioneerStatus status) {
        this(pioneerEntity.getId(), status.ordinal());
    }

    @Override
    public Id<PioneerStatusPayload> getId() {
        return ID;
    }

    public PlayerEntity getPioneerEntity(World world) {
        return (PlayerEntity) world.getEntityById(pioneerEntityId);
    }

    public PioneerStatus getStatus() {
        return PioneerStatus.values()[status];
    }
}
