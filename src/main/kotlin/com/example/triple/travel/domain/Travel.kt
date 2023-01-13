package com.example.triple.travel.domain

import com.example.triple.city.domain.City
import com.example.triple.common.entity.JpaAuditEntity
import com.example.triple.travel.application.UpdateTravelService
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "travels")
class Travel(
    city: City,
    startedAt: LocalDateTime,
    endedAt: LocalDateTime
) : JpaAuditEntity() {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var city: City = city
        protected set

    @Column(name = "started_at", nullable = false)
    var startedAt: LocalDateTime = startedAt
        protected set

    @Column(name = "ended_at", nullable = false)
    var endedAt: LocalDateTime = endedAt
        protected set

    init {
        require(LocalDateTime.now().isBefore(endedAt) && startedAt.isBefore(endedAt)) {
            "The end date of the trip must be a future date"
        }
    }

    fun update(command: UpdateTravelService.UpdateTravelCommand, city: City) {
        if (LocalDateTime.now().isBefore(command.endedAt) && command.startedAt.isBefore(command.endedAt)) {
            throw IllegalArgumentException("The end date of the trip must be a future date")
        }
        this.city = city
        startedAt = command.startedAt
        endedAt = command.endedAt
    }
}