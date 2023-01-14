package com.example.triple.travel.infrastructure.persistence.querydsl

import com.example.triple.city.domain.QCity
import com.example.triple.travel.domain.QTravel
import com.example.triple.travel.domain.Travel
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

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

    fun findTravelByUserId(userId: String): List<Travel> =
//        queryFactory.selectFrom(QTravel.travel)
//            .where(QTravel.travel.userId.eq(userId))
//            .innerJoin(QTravel.travel.city, QCity.city)
        emptyList()

    fun findTravelByUserIdAndStartedAtGreaterThanEqualAndEndedAtLessThanEqual(userId: String): List<Travel> =
        queryFactory.selectFrom(QTravel.travel)
            .where(
                QTravel.travel.userId.eq(userId)
                    .and(
                        QTravel.travel.startedAt.before(LocalDateTime.now())
                            .and(QTravel.travel.endedAt.after(LocalDateTime.now()))
                    )
            )
            .innerJoin(QTravel.travel.city, QCity.city)
            .fetchJoin()
            .fetch()
}