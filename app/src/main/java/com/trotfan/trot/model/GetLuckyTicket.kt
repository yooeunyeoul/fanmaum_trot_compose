package com.trotfan.trot.model

import kotlinx.serialization.Serializable

@Serializable
data class GetLuckyTicket(
    override val next_reward: Int,
    override val rewarded_at: String?,
    override val today: Today,

    ) : BaseTicket() {
    @Serializable
    data class Today(
        val max: Int,
        val remaining: Int
    )
}

@Serializable
data class PostLuckyTicket(
    override val next_reward: Int,
    override val rewarded_at: String?,
    override val today: GetLuckyTicket.Today,
) : BaseTicket() {
    val tickets: Tickets? = null

}
@Serializable
sealed class BaseTicket {
    abstract val next_reward: Int
    abstract val rewarded_at: String?
    abstract val today: GetLuckyTicket.Today
}



