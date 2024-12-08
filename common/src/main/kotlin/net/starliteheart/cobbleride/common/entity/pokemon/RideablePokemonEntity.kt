package net.starliteheart.cobbleride.common.entity.pokemon

import com.bedrockk.molang.runtime.MoLangRuntime
import com.cobblemon.mod.common.api.pokemon.status.Statuses
import com.cobblemon.mod.common.api.reactive.SimpleObservable
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonBehaviourFlag
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.util.DataKeys
import com.cobblemon.mod.common.util.getIsSubmerged
import com.cobblemon.mod.common.util.resolveFloat
import com.google.common.collect.UnmodifiableIterator
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.server.level.ServerEntity
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.FluidTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.vehicle.DismountHelper
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.phys.Vec2
import net.minecraft.world.phys.Vec3
import net.starliteheart.cobbleride.common.CobbleRideMod
import net.starliteheart.cobbleride.common.api.pokemon.RideablePokemonSpecies
import net.starliteheart.cobbleride.common.config.CobbleRideConfig
import net.starliteheart.cobbleride.common.net.messages.RideState
import net.starliteheart.cobbleride.common.net.messages.client.pokemon.ai.ClientMoveBehaviour
import net.starliteheart.cobbleride.common.net.messages.client.pokemon.update.RidePokemonStateUpdatePacket
import net.starliteheart.cobbleride.common.net.messages.client.spawn.SpawnRidePokemonPacket
import net.starliteheart.cobbleride.common.pokemon.RideableFormData
import net.starliteheart.cobbleride.common.pokemon.RiderOffsetType
import net.starliteheart.cobbleride.common.pokemon.RiderOffsetType.*
import net.starliteheart.cobbleride.common.util.averageOfTwoRots
import net.starliteheart.cobbleride.common.util.emitParticle
import net.starliteheart.cobbleride.common.util.rotateVec3
import kotlin.math.max
import kotlin.math.min

class RideablePokemonEntity : PokemonEntity, PlayerRideable {
    constructor(world: Level) : super(world)
    constructor(
        world: Level,
        pokemon: Pokemon
    ) : super(world, pokemon)

    // Ride data from RideablePokemonSpecies, is null if there is no data file for the species or form
    private val rideData: RideableFormData?
        get() = RideablePokemonSpecies.getByName(exposedSpecies.showdownId())?.getForm(exposedForm.name)

    // Used clientside to ensure that we can still access move behaviour for resolving ride logic
    var moveBehaviour: ClientMoveBehaviour = ClientMoveBehaviour(exposedForm.behaviour.moving)

    private val config: CobbleRideConfig
        get() = CobbleRideMod.config

    private var lastRiderPosition: Vec3? = null
    private var shouldSinkInWater = false
    private var sprintCooldownScale = 0F
    var sprintStaminaScale = 0F
    val canSprint: Boolean
        get() = config.sprinting.canSprint
    val canExhaust: Boolean
        get() = config.sprinting.canExhaust
    var isExhausted = false

    var isRideAscending: Boolean = false
        set(value) {
            field = value
            this._isRideAscending.emit(value)
        }

    var isRideDescending: Boolean = false
        set(value) {
            field = value
            this._isRideDescending.emit(value)
        }

    var isRideSprinting: Boolean = false
        set(value) {
            field = value
            this._isRideSprinting.emit(value)
        }

    private fun isRideable(player: Player): Boolean =
        rideData != null && (canBeControlledBy(player) || !this.isBattling)

    override fun isImmobile(): Boolean =
        pokemon.status?.status == Statuses.SLEEP || super.isImmobile()

    override fun canSitOnShoulder(): Boolean =
        passengers.isEmpty() && super.canSitOnShoulder()

    override fun canStandOnFluid(state: FluidState): Boolean =
        !shouldSinkInWater &&
                if (state.`is`(FluidTags.WATER) && !isEyeInFluid(FluidTags.WATER)) {
                    moveBehaviour.swim.canWalkOnWater
                } else if (state.`is`(FluidTags.LAVA) && !isEyeInFluid(FluidTags.LAVA)) {
                    moveBehaviour.swim.canWalkOnLava
                } else {
                    false
                }

    private fun isOnWaterSurface(): Boolean =
        isInWater && !getIsSubmerged()

    private fun isAbleToDive(): Boolean =
        moveBehaviour.swim.canSwimInWater && moveBehaviour.swim.canBreatheUnderwater

    private fun isInPoseOfType(poseType: PoseType): Boolean =
        this.getCurrentPoseType() == poseType

    override fun getPassengerAttachmentPoint(entity: Entity, dimensions: EntityDimensions, f: Float): Vec3 {
        val offsets = rideData?.offsets ?: hashMapOf()
        var offset = offsets[DEFAULT] ?: Vec3.ZERO
        fun hasOffset(name: RiderOffsetType): Boolean = offsets[name] != null

        if (isFlying() && isInPoseOfType(PoseType.HOVER) && hasOffset(HOVERING)) {
            offsets[HOVERING]?.let { offset = offset.add(it) }
        } else if (isFlying() && hasOffset(FLYING)) {
            offsets[FLYING]?.let { offset = offset.add(it) }
        } else if (getIsSubmerged() && isInPoseOfType(PoseType.FLOAT) && hasOffset(SUSPENDED)) {
            offsets[SUSPENDED]?.let { offset = offset.add(it) }
        } else if (getIsSubmerged() && hasOffset(DIVING)) {
            offsets[DIVING]?.let { offset = offset.add(it) }
        } else if (isOnWaterSurface() && isInPoseOfType(PoseType.STAND) && hasOffset(FLOATING)) {
            offsets[FLOATING]?.let { offset = offset.add(it) }
        } else if (isOnWaterSurface() && hasOffset(SWIMMING)) {
            offsets[SWIMMING]?.let { offset = offset.add(it) }
        } else if (isInPoseOfType(PoseType.WALK) && hasOffset(WALKING)) {
            offsets[WALKING]?.let { offset = offset.add(it) }
        }

        if (aspects.any { it.contains(DataKeys.HAS_BEEN_SHEARED) } && hasOffset(SHEARED)) {
            offsets[SHEARED]?.let { offset = offset.add(it) }
        }

        var attachmentPoint = super.getPassengerAttachmentPoint(entity, dimensions, f)
            .add(offset)

        val lastPos = lastRiderPosition
        if (lastPos != null && lastPos.distanceTo(attachmentPoint) > 0.05) {
            attachmentPoint = attachmentPoint.multiply(0.2, 0.2, 0.2)
                .add(lastPos.multiply(0.8, 0.8, 0.8))
        }
        lastRiderPosition = attachmentPoint

        val rotatedOffset = rotateVec3(attachmentPoint, yRotO)

        return rotatedOffset
    }

    override fun getDismountLocationForPassenger(entity: LivingEntity): Vec3 {
        val vec3 = getCollisionHorizontalEscapeVector(
            this.bbWidth.toDouble(), entity.bbWidth.toDouble(),
            this.yRot + (if (entity.mainArm == HumanoidArm.RIGHT) 90.0f else -90.0f)
        )
        val vec31: Vec3? = this.getDismountLocationInDirection(vec3, entity)
        if (vec31 != null) {
            return vec31
        } else {
            val vec32 = getCollisionHorizontalEscapeVector(
                this.bbWidth.toDouble(), entity.bbWidth.toDouble(),
                this.yRot + (if (entity.mainArm == HumanoidArm.LEFT) 90.0f else -90.0f)
            )
            val vec33: Vec3? = this.getDismountLocationInDirection(vec32, entity)
            return vec33 ?: this.position()
        }
    }

    private fun getDismountLocationInDirection(arg: Vec3, arg2: LivingEntity): Vec3? {
        val d0 = this.x + arg.x
        val d1 = this.boundingBox.minY
        val d2 = this.z + arg.z
        val mutableBlockPos = MutableBlockPos()
        val var10: UnmodifiableIterator<*> = arg2.dismountPoses.iterator()

        while (var10.hasNext()) {
            val pose = var10.next() as Pose
            mutableBlockPos[d0, d1] = d2
            val d3 = this.boundingBox.maxY + 0.75

            while (true) {
                val d4 = level().getBlockFloorHeight(mutableBlockPos)
                if (mutableBlockPos.y.toDouble() + d4 > d3) {
                    break
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    val aabb = arg2.getLocalBoundsForPose(pose)
                    val vec3 = Vec3(d0, mutableBlockPos.y.toDouble() + d4, d2)
                    if (DismountHelper.canDismountTo(this.level(), arg2, aabb.move(vec3))) {
                        arg2.pose = pose
                        return vec3
                    }
                }

                mutableBlockPos.move(Direction.UP)
                if (mutableBlockPos.y.toDouble() < d3) {
                    break
                }
            }
        }

        return null
    }

    override fun getControllingPassenger(): LivingEntity? {
        val entity = this.firstPassenger
        if (entity is LivingEntity) {
            return if (canBeControlledBy(entity)) entity else null
        }
        return super.getControllingPassenger()
    }

    private fun canBeControlledBy(entity: LivingEntity): Boolean =
        entity is Player && entity.uuid == this.ownerUUID

    override fun causeFallDamage(fallDistance: Float, damageMultiplier: Float, damageSource: DamageSource): Boolean {
        val flag = super.causeFallDamage(fallDistance, damageMultiplier, damageSource)
        if (flag && this.isVehicle) {
            val i = this.calculateFallDamage(fallDistance, damageMultiplier)
            val var5: Iterator<*> = this.indirectPassengers.iterator()
            while (var5.hasNext()) {
                val entity = var5.next() as Entity
                entity.hurt(damageSource, i.toFloat())
            }
        }
        return flag
    }

    private fun doPlayerRide(player: Player) {
        if (!level().isClientSide) {
            player.yRot = yRot
            player.xRot = xRot
            player.startRiding(this)
        }
    }

    override fun mobInteract(player: Player, hand: InteractionHand): InteractionResult {
        val itemStack = player.getItemInHand(hand)
        if (player is ServerPlayer && !player.isShiftKeyDown
            && hand == InteractionHand.MAIN_HAND && itemStack.isEmpty
        ) {
            if (!this.isVehicle && this.isRideable(player)) {
                this.doPlayerRide(player)
                return InteractionResult.sidedSuccess(level().isClientSide)
            } else if (this.isVehicle && this.isOwnedBy(player)
                && this.passengers.none { it.uuid == player.uuid }
            ) {
                this.ejectPassengers()
                return InteractionResult.sidedSuccess(level().isClientSide)
            }
        }
        return super.mobInteract(player, hand)
    }

    fun shouldRiderSit(): Boolean = rideData?.shouldRiderSit ?: true

    override fun tickRidden(player: Player, vec3: Vec3) {
        super.tickRidden(player, vec3)
        val vec2 = this.getRiddenRotation(player)

        // Some lerping applied, to provide more realistic "weight" to rotation
        this.setRot(averageOfTwoRots(vec2.y, this.yRotO), averageOfTwoRots(vec2.x, this.xRotO))
        this.yHeadRot = vec2.y
        this.yBodyRot = averageOfTwoRots(this.yHeadRot, this.yRotO)
        this.yRotO = averageOfTwoRots(this.yBodyRot, this.yRotO)

        if (!shouldRiderSit()) {
            player.yBodyRot = this.yBodyRot
            player.yRotO = this.yRotO
            player.calculateEntityAnimation(false)
        }

        // Carry over logic from PokemonMoveControl, where non-diving Pokemon that can swim will tread water
        if ((this.isInLava && !moveBehaviour.swim.canBreatheUnderlava) ||
            (moveBehaviour.swim.canSwimInWater && !moveBehaviour.swim.canBreatheUnderwater
                    && this.isInWater && this.getFluidHeight(
                FluidTags.WATER
            ) > this.fluidJumpThreshold)
        ) {
            if (this.random.nextFloat() < 0.8f) {
                this.jumpControl.jump()
            }
        }

        // If shared water breathing is allowed, check mount and apply if it has the effect
        if (moveBehaviour.swim.canBreatheUnderwater && config.general.isWaterBreathingShared) {
            player.addEffect(MobEffectInstance(MobEffects.WATER_BREATHING, 60, 0, false, false, false))
        }

        // If the Pokemon is flying and can land, land
        if (this.isFlying() && (this.onGround() || isInWater) && this.couldStopFlying()) {
            this.setBehaviourFlag(PokemonBehaviourFlag.FLYING, false)
        }

        // When descending, disable water walking for diving Pokemon
        shouldSinkInWater = isRideDescending && isInWater && isAbleToDive()

        // When ascending, disable water walking if applicable, or start flying
        if (isRideAscending) {
            if (isOnWaterSurface() && isAbleToDive() && this.getFluidHeight(FluidTags.WATER) > this.fluidJumpThreshold) {
                // Because being able to stand on water means being affected by normal "ground" gravity,
                //   We need to make sure we aren't TRYING to stand on it before we're fully out
                shouldSinkInWater = true
                this.jumpControl.jump()
            } else if (moveBehaviour.fly.canFly && !this.isFlying() && !getIsSubmerged()) {
                this.setBehaviourFlag(PokemonBehaviourFlag.FLYING, true)
            } else {
                shouldSinkInWater = false
            }
        }

        // Sprint control logic
        if (canSprint && (!isInWater || config.sprinting.canSprintInWater) && (!isFlying() || config.sprinting.canSprintInAir)
            && isRideSprinting && vec3.horizontalDistance() > 0 && !isExhausted
        ) {
            sprintCooldownScale = 0F
            if (sprintStaminaScale > 0) {
                if (!level().isClientSide) {
                    this.isSprinting = true
                    // This may not be the best way to get an FOV change, but this will suffice until the first bugs inevitably happen
                    player.isSprinting = true
                }
                if (canExhaust) {
                    sprintStaminaScale = max(sprintStaminaScale - (1F / config.sprinting.maxStamina), 0F)
                }
            } else {
                isExhausted = true
            }
        } else {
            if (!level().isClientSide) {
                this.isSprinting = false
                // Be sure to remove FOV change here
                player.isSprinting = false
            }
        }

        // Activate any jumps that have been queued up this tick
        this.jumpControl.tick()
    }

    override fun tick() {
        super.tick()

        // Resolve stamina recovery and exhaustion effects
        if (canSprint && !this.isSprinting) {
            if (sprintStaminaScale < 1F) {
                if (config.sprinting.recoveryDelay > 0 && sprintCooldownScale < 1F) {
                    sprintCooldownScale = min(sprintCooldownScale + (1F / config.sprinting.recoveryDelay), 1F)
                } else {
                    sprintStaminaScale = min(sprintStaminaScale + (1F / config.sprinting.recoveryTime), 1F)
                }

                // Emit particles if exhausted, every X ticks
                if (isExhausted && tickCount % 4 == 0) {
                    emitParticle(this, ParticleTypes.FALLING_WATER)
                }
            } else {
                isExhausted = false
            }
        }
    }

    private fun getRiddenRotation(entity: LivingEntity): Vec2 {
        return Vec2(entity.xRot * 0.5f, entity.yRot)
    }

    override fun getRiddenInput(player: Player, v: Vec3): Vec3 {
        var yya = if (isEyeInFluid(FluidTags.WATER) && moveBehaviour.swim.canSwimInWater) {
            0.2f
        } else {
            0f
        }
        if (isImmobile) {
            return Vec3(0.0, yya.toDouble(), 0.0)
        }

        val xxa = player.xxa * 0.5f
        var zza = player.zza
        if (zza <= 0.0f) {
            zza *= 0.25f
        }

        val isFlyingOrSwimming = (isInWater && isAbleToDive()) || isFlying()
        if (isFlyingOrSwimming) {
            val verticalSpeed = (
                    if (isFlying()) {
                        config.general.airVerticalClimbSpeed
                    } else {
                        config.general.waterVerticalClimbSpeed
                    }
                    ).toFloat()
            if (isRideAscending && !isRideDescending) {
                yya += verticalSpeed
            } else if (isRideDescending && !isRideAscending) {
                yya -= verticalSpeed
            }
        }

        return Vec3(xxa.toDouble(), yya.toDouble(), zza.toDouble())
    }

    override fun getRiddenSpeed(player: Player): Float {
        val speedModifier = if (config.speedStat.affectsSpeed) {
            val minSpeedStat = config.speedStat.minStatThreshold
            val maxSpeedStat = config.speedStat.maxStatThreshold
            val minSpeedModifier = config.speedStat.minSpeedModifier
            val maxSpeedModifier = config.speedStat.maxSpeedModifier
            val clampedSpeedStat = pokemon.speed.coerceIn(minSpeedStat, maxSpeedStat).toDouble()
            val scaledSpeedStat = (clampedSpeedStat - minSpeedStat) / (maxSpeedStat - minSpeedStat)
            minSpeedModifier + scaledSpeedStat * (maxSpeedModifier - minSpeedModifier)
        } else {
            1.0
        }

        // Get land, water, air speed modifiers based on behaviour settings and data
        val mediumSpeed = MoLangRuntime().resolveFloat(
            if (getCurrentPoseType() in setOf(PoseType.FLY, PoseType.HOVER)) {
                moveBehaviour.fly.flySpeedHorizontal
            } else if (isEyeInFluid(FluidTags.WATER) || isEyeInFluid(FluidTags.LAVA)) {
                moveBehaviour.swim.swimSpeed
            } else {
                moveBehaviour.walk.walkSpeed
            }
        )
        val mediumModifier = if (getCurrentPoseType() in setOf(PoseType.FLY, PoseType.HOVER)) {
            (rideData?.airSpeedModifier ?: 1.0F) * config.general.globalAirSpeedModifier
        } else if (isInWater || isInLava) {
            // Adds a little speed to submerged Pokemon for better ride feel
            (rideData?.waterSpeedModifier ?: 1.0F) * config.general.globalWaterSpeedModifier * if (getIsSubmerged()) {
                config.general.underwaterSpeedModifier
            } else {
                1.0
            }
        } else {
            (rideData?.landSpeedModifier ?: 1.0F) * config.general.globalLandSpeedModifier
        }

        val sprintModifier = if (this.isSprinting) {
            config.sprinting.rideSprintSpeed / 1.3
        } else if (this.isExhausted) {
            config.sprinting.exhaustionSpeed
        } else {
            1.0
        }

        // Calculate final adjusted speed
        val baseSpeed =
            getAttributeValue(Attributes.MOVEMENT_SPEED) * config.general.globalBaseSpeedModifier * (rideData?.baseSpeedModifier
                ?: 1.0F)
        val adjustedSpeed = baseSpeed * mediumSpeed * mediumModifier * speedModifier * sprintModifier

        return (
                if (config.general.rideSpeedLimit > 0) {
                    min(adjustedSpeed, config.general.rideSpeedLimit / 43.17)
                } else {
                    adjustedSpeed
                }
                ).toFloat()
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAddEntityPacket(entityTrackerEntry: ServerEntity): Packet<ClientGamePacketListener> =
        ClientboundCustomPayloadPacket(
            SpawnRidePokemonPacket(
                this,
                ClientboundAddEntityPacket(this, entityTrackerEntry)
            )
        ) as Packet<ClientGamePacketListener>

    // State sync observables
    private val _isRideAscending = pokemon.registerObservable(SimpleObservable<Boolean>()) {
        RidePokemonStateUpdatePacket(
            { pokemon },
            RideState.ASCENDING,
            it
        )
    }
    private val _isRideDescending = pokemon.registerObservable(SimpleObservable<Boolean>()) {
        RidePokemonStateUpdatePacket(
            { pokemon },
            RideState.DESCENDING,
            it
        )
    }
    private val _isRideSprinting = pokemon.registerObservable(SimpleObservable<Boolean>()) {
        RidePokemonStateUpdatePacket(
            { pokemon },
            RideState.SPRINTING,
            it
        )
    }

}