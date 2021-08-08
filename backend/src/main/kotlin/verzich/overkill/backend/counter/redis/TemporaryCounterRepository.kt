package verzich.overkill.backend.counter.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TemporaryCounterRepository(private val redisTemplate: RedisTemplate<String, Any>) {

    fun save(value: TemporaryCounter): TemporaryCounter {
        redisTemplate.opsForValue().set(value.name, value.count)
        return value
    }

    fun findByName(name: String): Optional<TemporaryCounter> {
        val result = redisTemplate.opsForValue().get(name) ?: return Optional.empty()

        return Optional.of(TemporaryCounter(name = name, count = (result as Long)))
    }

    fun findAll(): List<TemporaryCounter> {

        return redisTemplate.keys("*").map {
            TemporaryCounter(name = it, count = redisTemplate.opsForValue().get(it) as Long)
        }.toList()
    }
}
