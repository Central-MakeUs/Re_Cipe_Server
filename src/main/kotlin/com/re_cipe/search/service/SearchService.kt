package com.re_cipe.search.service

import com.re_cipe.recipe.domain.repository.RecipeRepository
import com.re_cipe.recipe.domain.repository.ShortFormRecipeRepository
import com.re_cipe.recipe.ui.dto.RecipeResponse
import com.re_cipe.recipe.ui.dto.ShortFormSimpleResponse
import com.re_cipe.search.domain.Keyword
import com.re_cipe.search.domain.KeywordRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SearchService(
    private val keywordRepository: KeywordRepository,
    private val recipeRepository: RecipeRepository,
    private val shortFormRecipeRepository: ShortFormRecipeRepository
) {
    fun findPopularKeywords(): List<String> {
        return keywordRepository.findAll().map { keyword -> keyword.word }.subList(0, 10)
    }

    fun findRecipeByKeyword(keyword: String, offset: Int, pageSize: Int): Slice<RecipeResponse> {
        updateKeyword(keyword)
        return recipeRepository.searchRecipeByKeyword(keyword = keyword, createPageable(offset, pageSize))
            .map { recipe -> RecipeResponse.of(recipe) }
    }

    fun findShortformByKeyword(keyword: String, offset: Int, pageSize: Int): Slice<ShortFormSimpleResponse> {
        updateKeyword(keyword)
        return shortFormRecipeRepository.searchRecipeByKeyword(keyword = keyword, createPageable(offset, pageSize))
            .map { shortform -> ShortFormSimpleResponse.of(shortform, false, false) }
    }

    private fun createPageable(offset: Int, pageSize: Int): Pageable {
        return PageRequest.of(offset, pageSize, Sort.Direction.DESC, "createdAt")
    }

    private fun updateKeyword(keyword: String) {
        val keywordList = keywordRepository.findAllByWord(keyword)
        if (keywordList.isEmpty()) {
            keywordRepository.save(Keyword(word = keyword))
        } else {
            for (word in keywordList) {
                word.count += 1
            }
        }
    }
}