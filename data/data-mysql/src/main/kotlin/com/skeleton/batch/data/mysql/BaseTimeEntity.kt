package com.skeleton.batch.data.mysql

import org.hibernate.envers.Audited
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import org.springframework.data.annotation.LastModifiedDate
import javax.persistence.*

@Audited
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity : BaseEntity() {
    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
}
