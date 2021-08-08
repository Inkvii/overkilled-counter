package verzich.overkill.backend.counter.redis

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("counter")
class TemporaryCounter(
    val name: String,
    var count: Long = 0
) : Serializable {

}
