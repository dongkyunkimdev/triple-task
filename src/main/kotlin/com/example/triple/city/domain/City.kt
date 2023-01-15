package com.example.triple.city.domain

import com.example.triple.city.application.UpdateCityService
import com.example.triple.common.entity.JpaAuditEntity
import com.example.triple.travel.domain.Travel
import javax.persistence.*

@Entity
@Table(name = "cities")
class City(
    name: String
) : JpaAuditEntity() {
    @Column(name = "name", unique = true, nullable = false)
    var name: String = name
        protected set

    @OneToMany(mappedBy = "city", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var travels: MutableList<Travel> = mutableListOf()
        protected set

    fun update(command: UpdateCityService.UpdateCityCommand) {
        name = command.name
    }
}