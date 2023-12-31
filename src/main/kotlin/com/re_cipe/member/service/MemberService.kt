package com.re_cipe.member.service


import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.repository.MemberRepository
import com.re_cipe.member.ui.dto.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(private val memberRepository: MemberRepository) {
    fun findByEmail(email: String) = memberRepository.findByEmail(email)
    fun existsByNickname(nicknameDuplicationRequest: NicknameDuplicationRequest): NicknameDuplicationResponse {
        if (!isValid(nicknameDuplicationRequest.nickname)) {
            throw BusinessException(ErrorCode.INVALID_NICKNAME)
        }
        return NicknameDuplicationResponse(memberRepository.existsByNickname(nicknameDuplicationRequest.nickname))
    }

    fun getOneUser(member: Member): MemberResponse {
        return MemberResponse.of(member)
    }

    @Transactional
    fun setSystemNotification(member: Member, systemNotificationRequest: SystemNotificationRequest): Boolean {
        memberRepository.setSystemNotification(member.id, systemNotificationRequest.system_notification)
        return true
    }

    @Transactional
    fun setMarketingNotification(member: Member, marketingNotificationRequest: MarketingNotificationRequest): Boolean {
        memberRepository.setMarketingNotification(member.id, marketingNotificationRequest.marketing_notification)
        return true
    }

    @Transactional
    fun changeNickname(nicknameChangeRequest: NicknameChangeRequest, member: Member): Boolean {
        if (!isValid(nicknameChangeRequest.nickname)) {
            throw BusinessException(ErrorCode.INVALID_NICKNAME)
        }
        if (memberRepository.existsByNickname(nicknameChangeRequest.nickname)) {
            throw BusinessException(ErrorCode.NICKNAME_DUPLICATION)
        }
        memberRepository.setNickname(memberId = member.id, nickname = nicknameChangeRequest.nickname)
        return true
    }

    private fun isValid(nickname: String): Boolean {
        val regex = Regex("^[가-힣a-zA-Z]*\$") // 한글과 영어만 허용
        return nickname.matches(regex) && nickname.length <= NICKNAME_MAX_LENGTH
    }


    companion object {
        const val NICKNAME_MAX_LENGTH = 10
    }
}