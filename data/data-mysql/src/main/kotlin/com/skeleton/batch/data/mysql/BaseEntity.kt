package com.skeleton.batch.data.mysql

import org.hibernate.envers.Audited
import javax.persistence.*

@Audited
@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
