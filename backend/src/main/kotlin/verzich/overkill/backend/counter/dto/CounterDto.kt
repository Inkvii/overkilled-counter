package verzich.overkill.backend.counter.dto

data class CounterDto(
    val name: String,
    val count: Long = 1
)
