package verzich.overkill.backend.counter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import verzich.overkill.backend.counter.dto.CounterDto
import verzich.overkill.backend.counter.entity.CounterEntity
import verzich.overkill.backend.counter.redis.TemporaryCounter
import verzich.overkill.backend.counter.redis.TemporaryCounterRepository
import javax.transaction.Transactional

@Service
class CounterService(
    private val counterRepository: CounterRepository,
    private val temporaryCounterRepository: TemporaryCounterRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Returns all counters persisted in the database
     */
    fun getAllCounters(): List<CounterDto> {
        val persistedCounters = counterRepository.findAll().map {
            CounterDto(name = it.name, count = it.count)
        }.toList()

        val redisCounters = temporaryCounterRepository.findAll().map {
            CounterDto(name = it.name, count = it.count, isVirtualCounter = true)
        }.toList()

        return listOf(persistedCounters, redisCounters).flatten()
    }

    @Transactional
    fun incrementCounter(counter: CounterDto): CounterDto {
        val counterFromDb = getCounterByName(counter.name)

        val updatedCounterDto: CounterDto

        if (counterFromDb.isVirtualCounter) {
            updatedCounterDto = persistVirtualCounterToRedis(counterFromDb)
        } else {
            updatedCounterDto = persistCounterToDatabase(counterFromDb)
        }
        return CounterDto(name = updatedCounterDto.name, count = updatedCounterDto.count)

    }


    @Transactional
    fun persistCounterToDatabase(counter: CounterDto): CounterDto {
        log.debug("> persistCounterToDatabase")

        // get or create
        val counterEntity: CounterEntity = counterRepository.findByName(counter.name).orElse(CounterEntity(counter.name, counter.count))

        if (counterEntity.count != counter.count) {
            throw IllegalStateException("Could not increment value of the count because request is not synchronised")
        }

        counterEntity.count++
        val updatedCounterEntity = counterRepository.save(counterEntity)

        log.debug("< persistCounterToDatabase")
        return CounterDto(name = updatedCounterEntity.name, count = updatedCounterEntity.count)
    }

    fun getCounterByName(name: String): CounterDto {
        log.debug("> getCounterByName")

        var dto: CounterDto? = null
        temporaryCounterRepository.findByName(name).ifPresent {
            dto = CounterDto(it.name, it.count, isVirtualCounter = true)
        }

        if (dto == null) {
            val counterEntity: CounterEntity =
                counterRepository.findByName(name).orElseThrow { IllegalArgumentException("Counter with name doesnt exist") }
            dto = CounterDto(name = counterEntity.name, count = counterEntity.count)
        }

        log.debug("< getCounterByName")
        return dto!!
    }

    @Transactional
    fun persistVirtualCounterToRedis(counter: CounterDto): CounterDto {
        log.debug("> persistVirtualCounterToRedis")
        // get or create
        val counterEntity = temporaryCounterRepository.findByName(counter.name).orElse(TemporaryCounter(counter.name, counter.count))

        if (counterEntity.count != counter.count) {
            throw IllegalStateException("Could not increment value of virtual counter because request is not synchronised")
        }

        counterEntity.count++
        val updatedCounterEntity = temporaryCounterRepository.save(counterEntity)

        log.debug("< persistVirtualCounterToRedis")
        return CounterDto(name = updatedCounterEntity.name, count = updatedCounterEntity.count, isVirtualCounter = true)
    }
}
