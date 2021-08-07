package verzich.overkill.backend.counter

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import verzich.overkill.backend.counter.dto.CounterDto
import verzich.overkill.backend.counter.dto.io.AllCountersResponse
import verzich.overkill.backend.counter.dto.io.PersistCounterToDatabaseRequest

@RestController
class CounterController(val counterService: CounterService) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/counters")
    fun getAllCounters(): AllCountersResponse {
        log.debug("> getAllCounters")
        val allCounters = counterService.getAllCounters()
        val response = AllCountersResponse(allCounters)

        log.debug("< getAllCounters")
        return response
    }

    @PutMapping("/counters/add", consumes = ["application/json"], produces = ["application/json"])
    fun persistCounterToDatabase(@RequestBody request: PersistCounterToDatabaseRequest) {
        log.debug("> persistCounterToDatabase")

        val counterDto = CounterDto(name = request.name, count = request.count)
        counterService.persistCounterToDatabase(counterDto)

        log.debug("< persistCounterToDatabase")

    }
}
