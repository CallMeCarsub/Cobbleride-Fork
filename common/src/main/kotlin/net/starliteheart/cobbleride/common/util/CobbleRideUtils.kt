package net.starliteheart.cobbleride.common.util

import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import net.starliteheart.cobbleride.common.CobbleRideMod
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun rideableResource(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(CobbleRideMod.MOD_ID, path)

fun averageOfTwoRots(f1: Float, f2: Float): Float {
    val r1 = f1 * PI / 180
    val r2 = f2 * PI / 180
    val list = doubleArrayOf(r1, r2)
    val xSum = list.sumOf { cos(it) }
    val ySum = list.sumOf { sin(it) }
    return (atan2(ySum, xSum) / PI * 180).toFloat()
}

fun rotateVec3(offset: Vec3, angle: Float): Vec3 {
    val r = angle * PI / 180
    val x = offset.x * cos(r) - offset.z * sin(r)
    val z = offset.x * sin(r) + offset.z * cos(r)
    return Vec3(x, offset.y, z)
}

fun emitParticle(entity: Entity, particle: SimpleParticleType) {
    fun getRandomAngle(): Double {
        return entity.random.nextDouble() * 2 * Math.PI
    }

    val particleSpeed = entity.random.nextDouble()
    val particleAngle = getRandomAngle()
    val particleXSpeed = cos(particleAngle) * particleSpeed
    val particleYSpeed = sin(particleAngle) * particleSpeed

    if (entity.level() is ServerLevel) {
        (entity.level() as ServerLevel).sendParticles(
            particle,
            entity.position().x + cos(getRandomAngle()) * entity.boundingBox.xsize,
            entity.boundingBox.maxY,
            entity.position().z + cos(getRandomAngle()) * entity.boundingBox.zsize,
            1,  //Amount?
            particleXSpeed, 0.5, particleYSpeed,
            1.0
        ) //Scale?
    } else {
        entity.level().addParticle(
            particle,
            entity.position().x + cos(getRandomAngle()) * entity.boundingBox.xsize,
            entity.boundingBox.maxY,
            entity.position().z + cos(getRandomAngle()) * entity.boundingBox.zsize,
            particleXSpeed, 0.5, particleYSpeed
        )
    }
}