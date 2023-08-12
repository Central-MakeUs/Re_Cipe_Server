package com.re_cipe.recipe.service

import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.ingredient.domain.RecipeIngredients
import com.re_cipe.ingredient.domain.repository.IngredientRepository
import com.re_cipe.ingredient.domain.repository.RecipeIngredientRepository
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.recipe.domain.repository.RecipeRepository
import com.re_cipe.recipe.domain.repository.SavedRecipeRepository
import com.re_cipe.recipe.ui.dto.RecipeCreateRequest
import com.re_cipe.recipe.ui.dto.RecipeDetailResponse
import com.re_cipe.recipe.ui.dto.RecipeResponse
import com.re_cipe.stage.domain.RecipeStage
import com.re_cipe.stage.domain.repository.StageRepository
import com.re_cipe.stage.ui.dto.StageResponse
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RecipeService(
    val recipeRepository: RecipeRepository,
    val stageRepository: StageRepository,
    val savedRecipeRepository: SavedRecipeRepository,
    val recipeIngredientRepository: RecipeIngredientRepository,
    val ingredientRepository: IngredientRepository
) {
    fun getRecipesByLatest(offset: Int, pageSize: Int): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByLatest(createPageable(offset, pageSize))
            .map { recipe -> RecipeResponse.of(recipe) }
    }

    fun getRecipesByPopular(offset: Int, pageSize: Int): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByPopular(createPageable(offset, pageSize))
            .map { recipe -> RecipeResponse.of(recipe) }
    }

    fun getRecipesByShortestTime(offset: Int, pageSize: Int): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByShortestTime(createPageable(offset, pageSize))
            .map { recipe -> RecipeResponse.of(recipe) }
    }

    private fun createPageable(offset: Int, pageSize: Int): Pageable {
        return PageRequest.of(offset, pageSize, Sort.Direction.DESC, "createdAt")
    }

    fun findUserSavedRecipe(memberId: Long): List<RecipeResponse> {
        return recipeRepository.findUserSavedRecipes(memberId).map { recipe: Recipe -> RecipeResponse.of(recipe) }
    }

    fun findMyRecipes(memberId: Long): List<RecipeResponse> {
        return recipeRepository.findUsersWrittenRecipe(memberId).map { recipe: Recipe -> RecipeResponse.of(recipe) }
    }

    fun getRecipeDetail(recipeId: Long, memberId: Long): RecipeDetailResponse {
        return RecipeDetailResponse.of(
            recipeRepository.findById(recipeId).get(),
            stageRepository.findAllRecipeStages(recipeId).map { recipeStage -> StageResponse.of(recipeStage) },
            savedRecipeRepository.checkMemberSavedRecipe(recipeId, memberId)
        )
    }

    @Transactional
    fun deleteMyRecipe(memberId: Long, recipeId: Long): Boolean {
        val recipeFound = recipeRepository.findById(recipeId).get()
        if (recipeFound.writtenBy.id != memberId) {
            throw BusinessException(ErrorCode.NO_AUTHENTICATION)
        }
        recipeRepository.deleteOneRecipe(recipeId)
        return true
    }

    @Transactional
    fun createRecipe(recipeCreateRequest: RecipeCreateRequest, member: Member): Long {
        val recipe = Recipe(
            name = recipeCreateRequest.recipe_name,
            description = recipeCreateRequest.recipe_description,
            cook_time = recipeCreateRequest.cook_time,
            serving_size = recipeCreateRequest.serving_size,
            rating = 0.0,
            thumbnail_img = recipeCreateRequest.recipe_thumbnail_img,
            writtenBy = member,
        )
        recipeRepository.save(recipe)

        for (recipeStageRequest in recipeCreateRequest.recipe_stages) {
            val stage = stageRepository.save(
                RecipeStage(
                    image_url = recipeStageRequest.image_url,
                    stage_description = recipeStageRequest.stage_description,
                    recipe = recipe
                )
            )
            recipe.stages.add(stage)
        }

        for (ingredientRequest in recipeCreateRequest.ingredients) {
            val recipeIngredients = RecipeIngredients(
                recipe = recipe,
                ingredient = ingredientRepository.findById(ingredientRequest.ingredient_id).get(),
                ingredientSize = ingredientRequest.ingredient_size
            )
            recipeIngredientRepository.save(recipeIngredients)
            recipe.ingredientList.add(recipeIngredients)
        }
        return recipe.id
    }
}