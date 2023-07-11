package com.re_cipe.member.service


import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.member.domain.repository.MemberRepository
import com.re_cipe.member.ui.dto.NicknameDuplicationRequest
import com.re_cipe.member.ui.dto.NicknameDuplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(private val memberRepository: MemberRepository) {
    fun existsByNickname(nicknameDuplicationRequest: NicknameDuplicationRequest): NicknameDuplicationResponse {
        if (!isValid(nicknameDuplicationRequest.nickname)) {
            throw BusinessException(ErrorCode.INVALID_NICKNAME)
        }
        return NicknameDuplicationResponse(memberRepository.existsByNickname(nicknameDuplicationRequest.nickname))
    }

    fun isValid(nickname: String): Boolean {
        return nickname.length <= NICKNAME_MAX_LENGTH
    }

    companion object {
        const val NICKNAME_MAX_LENGTH = 10
    }
}