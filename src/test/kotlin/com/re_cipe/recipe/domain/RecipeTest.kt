package com.re_cipe.recipe.domain

import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.Provider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class RecipeTest {

    private lateinit var member: Member

    @BeforeEach
    fun setUp() {
        member = Member(nickname = "user", email = "test@naver.com", provider = Provider.GOOGLE)
    }

    @Test
    @DisplayName("레시피 생성에 성공한다.")
    fun recipeTest() {
        // given
        val recipe = Recipe(name = "참치 볶음밥", description = "...", cook_time = 1, serving_size = 1, writtenBy = member)

        // when&then
        assertThat(recipe.cook_time).isEqualTo(1)
    }
}