package verzich.overkill.backend.counter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import verzich.overkill.backend.counter.dto.CounterDto
import verzich.overkill.backend.counter.entity.CounterEntity
import javax.transaction.Transactional

@Service
class CounterService(private val counterRepository: CounterRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Returns all counters persisted in the database
     */
    fun getAllCounters(): List<CounterDto> {
        return counterRepository.findAll().map {
            CounterDto(name = it.name, count = it.count)
        }.toList()
    }

    @Transactional
    fun persistCounterToDatabase(counter: CounterDto) {
        log.debug("> persistCounterToDatabase")

        // get or create
        val counterEntity: CounterEntity = counterRepository.findByName(counter.name).orElse(CounterEntity(counter.name, counter.count))

        if (counterEntity.count != counter.count) {
            throw IllegalStateException("Could not increment value of the count because request is not synchronised")
        }

        counterEntity.count++
        counterRepository.save(counterEntity)

        log.debug("< persistCounterToDatabase")
    }
}
