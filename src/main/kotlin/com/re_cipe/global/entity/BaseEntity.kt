package com.re_cipe.global.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity {
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var modifiedAt: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    var status: EntityStatus = EntityStatus.ACTIVE

    fun softDelete() {
        this.status = EntityStatus.INACTIVE
    }
}