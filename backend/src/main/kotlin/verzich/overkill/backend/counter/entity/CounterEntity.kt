package verzich.overkill.backend.counter.entity

import java.io.Serializable
import javax.persistence.*

@Entity(name = "counter")
@Table(name = "counter")
class CounterEntity(
    @Column(name = "name", unique = true)
    val name: String,
    @Column(name = "count")
    var count: Long
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
