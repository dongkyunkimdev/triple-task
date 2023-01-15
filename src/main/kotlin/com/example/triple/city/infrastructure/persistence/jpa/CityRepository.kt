package com.example.triple.city.infrastructure.persistence.jpa

import com.example.triple.city.domain.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CityRepository : JpaRepository<City, String> {
    fun findCityById(id: String): City?
    fun existsCityByName(name: String): Boolean

    //    @Query("select c" +
//            "from CITIES c" +
//            "  left outer join TRAVELS t" +
//            "    on c.ID = t.CITY_ID" +
//            "order by" +
//            "  case" +
//            "    when current_timestamp between t.STARTED_AT and t.ENDED_AT then t.STARTED_AT" +
//            "  end asc," +
//            "  case" +
//            "    when t.STARTED_AT >= current_timestamp then t.STARTED_AT" +
//            "  end asc," +
//            "  case" +
//            "    when c.CREATED_AT between (current_timestamp - '+1 00:00:00.000000000') and current_timestamp then t.CREATED_AT" +
//            "  end desc," +
//            "  rand()")
    @Query("select *\n" +
            "from cities\n" +
            "left outer join travels on cities.id = travels.city_id\n" +
            "order by\n" +
            "case when now() between travels.started_at and travels.ended_at then travels.started_at end asc,\n" +
            "case when travels.started_at >= now() then travels.started_at end asc,\n" +
            "case when cities.created_at between (now() - interval 1 day) and now() then travels.created_at end desc,\n" +
            "rand()\n" +
            "limit 10", nativeQuery = true)
    override fun findAll(): List<City>
}