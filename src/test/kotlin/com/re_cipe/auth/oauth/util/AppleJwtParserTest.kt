package com.re_cipe.auth.oauth.util

import com.re_cipe.exception.BusinessException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Date
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class AppleJwtParserTest {

    private val appleJwtParser = AppleJwtParser()

    @Test
    @DisplayName("Apple identity token으로 헤더를 파싱한다")
    fun parseHeaders() {
        val now = Date()
        val keyPair: KeyPair = KeyPairGenerator.getInstance("RSA")
            .generateKeyPair()
        val privateKey: PrivateKey = keyPair.private

        val identityToken = Jwts.builder()
            .setHeaderParam("kid", "W2R4HXF3K")
            .claim("id", "12345678")
            .setIssuer("iss")
            .setIssuedAt(now)
            .setAudience("aud")
            .setExpiration(Date(now.time + 1000 * 60 * 60 * 24))
            .signWith(SignatureAlgorithm.RS256, privateKey)
            .compact()

        val actual = appleJwtParser.parseHeaders(identityToken)

        assertThat(actual).containsKeys("alg", "kid")
    }

//    @Test
//    @DisplayName("올바르지 않은 형식의 Apple identity token으로 헤더를 파싱하면 예외를 반환한다")
//    fun parseHeadersWithInvalidToken() {
//        assertThatThrownBy { appleJwtParser.parseHeaders("invalidToken") }
//            .isInstanceOf(BusinessException::class.java)
//    }

    @Test
    @DisplayName("Apple identity token, PublicKey를 받아 사용자 정보가 포함된 Claims를 반환한다")
    fun parsePublicKeyAndGetClaims() {
        val expected = "19281729"
        val now = Date()
        val keyPair: KeyPair = KeyPairGenerator.getInstance("RSA")
            .generateKeyPair()
        val publicKey: PublicKey = keyPair.public
        val privateKey: PrivateKey = keyPair.private
        val identityToken = Jwts.builder()
            .setHeaderParam("kid", "W2R4HXF3K")
            .claim("id", "12345678")
            .setIssuer("iss")
            .setIssuedAt(now)
            .setAudience("aud")
            .setSubject(expected)
            .setExpiration(Date(now.time + 1000 * 60 * 60 * 24))
            .signWith(SignatureAlgorithm.RS256, privateKey)
            .compact()

        val claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey)

        assertAll(
            { assertThat(claims).isNotEmpty() },
            { assertThat(claims.subject).isEqualTo(expected) }
        )
    }

    @Test
    @DisplayName("만료된 Apple identity token을 받으면 Claims 획득 시에 예외를 반환한다")
    fun parseExpiredTokenAndGetClaims() {
        val expected = "19281729"
        val now = Date()
        val keyPair: KeyPair = KeyPairGenerator.getInstance("RSA")
            .generateKeyPair()
        val publicKey: PublicKey = keyPair.public
        val privateKey: PrivateKey = keyPair.private
        val identityToken = Jwts.builder()
            .setHeaderParam("kid", "W2R4HXF3K")
            .claim("id", "12345678")
            .setIssuer("iss")
            .setIssuedAt(now)
            .setAudience("aud")
            .setSubject(expected)
            .setExpiration(Date(now.time - 1L))
            .signWith(SignatureAlgorithm.RS256, privateKey)
            .compact()

        assertThatThrownBy { appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey) }
            .isInstanceOf(BusinessException::class.java)
    }

//    @Test
//    @DisplayName("올바르지 않은 public Key로 Claims 획득 시에 예외를 반환한다")
//    fun parseInvalidPublicKeyAndGetClaims() {
//        val now = Date()
//        val privateKey: PrivateKey = KeyPairGenerator.getInstance("RSA")
//            .generateKeyPair()
//            .private
//        val differentPublicKey: PublicKey = KeyPairGenerator.getInstance("RSA")
//            .generateKeyPair()
//            .public
//        val identityToken = Jwts.builder()
//            .setHeaderParam("kid", "W2R4HXF3K")
//            .claim("id", "12345678")
//            .setIssuer("iss")
//            .setIssuedAt(now)
//            .setAudience("aud")
//            .setSubject("19281729")
//            .setExpiration(Date(now.time - 1L))
//            .signWith(SignatureAlgorithm.RS256, privateKey)
//            .compact()
//
//        assertThatThrownBy { appleJwtParser.parsePublicKeyAndGetClaims(identityToken, differentPublicKey) }
//            .isInstanceOf(BusinessException::class.java)
//    }
}
