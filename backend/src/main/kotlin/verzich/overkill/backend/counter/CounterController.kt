package verzich.overkill.backend.counter

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import verzich.overkill.backend.counter.dto.CounterDto
import verzich.overkill.backend.counter.dto.io.AllCountersResponse
import verzich.overkill.backend.counter.dto.io.CounterRequestResponse

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

    @GetMapping("/counters/{name}", produces = ["application/json"])
    fun getCounter(@PathVariable("name") counterName: String): CounterRequestResponse {
        log.debug("> getCounter")
        val counterDto = counterService.getCounterByName(counterName)
        log.debug("< getCounter")
        return CounterRequestResponse(name = counterDto.name, count = counterDto.count)
    }

    @PutMapping("/counters/add", consumes = ["application/json"], produces = ["application/json"])
    fun persistCounterToDatabase(@RequestBody request: CounterRequestResponse): CounterRequestResponse {
        log.debug("> persistCounterToDatabase")

        val counterDto = CounterDto(name = request.name, count = request.count)
        val updatedCounterDto = counterService.persistCounterToDatabase(counterDto)

        log.debug("< persistCounterToDatabase")

        return CounterRequestResponse(name = updatedCounterDto.name, count = updatedCounterDto.count)
    }

    @PostMapping("/counters/increment", consumes = ["application/json"], produces = ["application/json"])
    fun incrementCounter(@RequestBody request: CounterRequestResponse): CounterRequestResponse {
        val counterDto = CounterDto(name = request.name, count = request.count)
        val updatedCounterDto = counterService.incrementCounter(counterDto)

        return CounterRequestResponse(name = updatedCounterDto.name, count = updatedCounterDto.count)

    }

    @PutMapping("counters/addVirtual", consumes = ["application/json"], produces = ["application/json"])
    fun persistVirtualCounterToRedis(@RequestBody request: CounterRequestResponse): CounterRequestResponse {
        log.debug("> persistVirtualCounterToRedis")

        val counterDto = CounterDto(name = request.name, count = request.count, isVirtualCounter = true)
        val updatedCounterDto = counterService.persistVirtualCounterToRedis(counterDto)

        log.debug("< persistVirtualCounterToRedis")

        return CounterRequestResponse(name = updatedCounterDto.name, count = updatedCounterDto.count)
    }
}
