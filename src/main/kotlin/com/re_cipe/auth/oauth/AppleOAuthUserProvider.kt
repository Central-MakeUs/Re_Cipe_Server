package com.re_cipe.auth.oauth

import com.re_cipe.auth.oauth.util.AppleClaimsValidator
import com.re_cipe.auth.oauth.util.AppleJwtParser
import com.re_cipe.auth.oauth.util.ApplePlatformMemberResponse
import com.re_cipe.auth.oauth.util.PublicKeyGenerator
import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Component

@Component
class AppleOAuthUserProvider(
    private val appleJwtParser: AppleJwtParser,
    private val appleClient: AppleClient,
    private val publicKeyGenerator: PublicKeyGenerator,
    private val appleClaimsValidator: AppleClaimsValidator
) {
    fun getApplePlatformMember(identityToken: String): ApplePlatformMemberResponse {
        val headers = appleJwtParser.parseHeaders(identityToken)
        val applePublicKeys = appleClient.getApplePublicKeys()

        val publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys)

        val claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey)
        validateClaims(claims)
        return ApplePlatformMemberResponse(claims.subject, claims.get("email", String::class.java))
    }

    private fun validateClaims(claims: Claims) {
        if (!appleClaimsValidator.isValid(claims)) {
            throw BusinessException(ErrorCode.OAUTH2_FAIL_EXCEPTION)
        }
    }
}
