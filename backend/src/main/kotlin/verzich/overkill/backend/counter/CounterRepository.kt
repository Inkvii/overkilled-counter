package verzich.overkill.backend.counter

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import verzich.overkill.backend.counter.entity.CounterEntity
import java.util.*

@Repository
interface CounterRepository : JpaRepository<CounterEntity, Long> {

    fun findByName(name: String): Optional<CounterEntity>
}

