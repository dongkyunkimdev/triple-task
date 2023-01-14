package com.example.triple.city.domain

import com.example.triple.common.entity.JpaAuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "cities")
class City(
    name: String
) : JpaAuditEntity() {
    @Column(name = "name", unique = true, nullable = false)
    var name: String = name
        protected set
}