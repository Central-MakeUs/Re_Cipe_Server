package com.re_cipe.member.domain

import com.re_cipe.global.entity.BaseEntity
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "status = \'ACTIVE\'")
@Table(indexes = [Index(name = "i_member", columnList = "email")])
class Member constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0L,

    val email: String,

    val nickname: String,

    @Enumerated(EnumType.STRING)
    val provider: Provider,

    val system_notification: Boolean = true,

    val marketing_notification: Boolean = true
) : BaseEntity() {

}