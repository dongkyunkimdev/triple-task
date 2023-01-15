package com.example.triple.travel.infrastructure.persistence.querydsl

import com.example.triple.city.domain.QCity
import com.example.triple.travel.domain.QTravel
import com.example.triple.travel.domain.Travel
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class TravelRepositorySupport(
    private val queryFactory: JPAQueryFactory
) {
    fun findTravelById(id: String): Travel? =
        queryFactory.selectFrom(QTravel.travel)
            .where(QTravel.travel.id.eq(id))
            .innerJoin(QTravel.travel.city, QCity.city)
            .fetchJoin()
            .fetchOne()
}