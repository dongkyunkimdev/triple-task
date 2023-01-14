package com.example.triple.city.domain

import com.example.triple.common.entity.JpaAuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "travel_statistics")
class CityStatistic(
    cityId: String,
    cityName: String,
    userId: String
) : JpaAuditEntity() {
    @Column(name = "city_id", nullable = false)
    var cityId: String = cityId
        protected set

    @Column(name = "city_name", nullable = false)
    var cityName: String = cityName
        protected set

    @Column(name = "user_id", nullable = false)
    var userId: String = userId
        protected set

    @Column(name = "view", nullable = false)
    var view: Long = 0
        protected set

    fun view() {
        view++
    }
}