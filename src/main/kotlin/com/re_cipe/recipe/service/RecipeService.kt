package com.re_cipe.recipe.service

import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.ingredient.domain.RecipeIngredients
import com.re_cipe.ingredient.domain.ShortFormIngredients
import com.re_cipe.ingredient.domain.repository.IngredientRepository
import com.re_cipe.ingredient.domain.repository.RecipeIngredientRepository
import com.re_cipe.ingredient.domain.repository.ShortFormIngredientsRepository
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.*
import com.re_cipe.recipe.domain.repository.*
import com.re_cipe.recipe.ui.dto.*
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
    val ingredientRepository: IngredientRepository,
    val shortFormRecipeRepository: ShortFormRecipeRepository,
    val shortFormIngredientsRepository: ShortFormIngredientsRepository,
    val likedRecipeRepository: LikedRecipeRepository,
    val savedShortFormRepository: SavedShortFormRepository,
    val likedShortFormRepository: LikedShortFormRepository
) {
    fun getRecipesByLatest(offset: Int, pageSize: Int, member: Member): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByLatest(createPageable(offset, pageSize))
            .map { recipe ->
                RecipeResponse.of(
                    recipe,
                    savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = member.id)
                )
            }
    }

    fun getRecipesByPopular(offset: Int, pageSize: Int, member: Member): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByPopular(createPageable(offset, pageSize))
            .map { recipe ->
                RecipeResponse.of(
                    recipe,
                    savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = member.id)
                )
            }
    }

    fun getRecipesByShortestTime(offset: Int, pageSize: Int, member: Member): Slice<RecipeResponse> {
        return recipeRepository.findRecipesByShortestTime(createPageable(offset, pageSize))
            .map { recipe ->
                RecipeResponse.of(
                    recipe,
                    savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = member.id)
                )
            }
    }

    private fun createPageable(offset: Int, pageSize: Int): Pageable {
        return PageRequest.of(offset, pageSize, Sort.Direction.DESC, "createdAt")
    }

    fun findUserSavedRecipe(memberId: Long): List<RecipeResponse> {
        return recipeRepository.findUserSavedRecipes(memberId).map { recipe: Recipe ->
            RecipeResponse.of(
                recipe,
                savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = memberId)
            )
        }
    }

    fun findMyRecipes(memberId: Long): List<RecipeResponse> {
        return recipeRepository.findUsersWrittenRecipe(memberId).map { recipe: Recipe ->
            RecipeResponse.of(
                recipe,
                savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = memberId)
            )
        }
    }

    fun getRecipeDetail(recipeId: Long, memberId: Long): RecipeDetailResponse {
        return RecipeDetailResponse.of(
            recipeRepository.findById(recipeId).orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) },
            stageRepository.findAllRecipeStages(recipeId).map { recipeStage -> StageResponse.of(recipeStage) },
            savedRecipeRepository.checkMemberSavedRecipe(recipeId, memberId),
            recipeRepository.recommendRecipe(recipeId)
        )
    }

    @Transactional
    fun deleteMyRecipe(memberId: Long, recipeId: Long): Boolean {
        val recipeFound = recipeRepository.findById(recipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
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
                ingredient = ingredientRepository.findById(ingredientRequest.ingredient_id)
                    .orElseThrow { BusinessException(ErrorCode.NO_INGREDIENT_FOUND) },
                ingredientSize = ingredientRequest.ingredient_size
            )
            recipeIngredientRepository.save(recipeIngredients)
            recipe.ingredientList.add(recipeIngredients)
        }
        return recipe.id
    }

    @Transactional
    fun saveRecipe(member: Member, recipeId: Long): Boolean {
        val recipe = recipeRepository.findById(recipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }

        if (savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipeId, memberId = member.id)) {
            throw BusinessException(ErrorCode.ALREADY_SAVED_RECIPE)
        }
        savedRecipeRepository.save(SavedRecipe(savedBy = member, recipe = recipe))
        return true
    }

    @Transactional
    fun likeRecipe(member: Member, recipeId: Long): Boolean {
        val recipe = recipeRepository.findById(recipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        if (likedRecipeRepository.existsByLikedByIdAndRecipeId(memberId = member.id, recipeId = recipeId)) {
            throw BusinessException(ErrorCode.ALREADY_LIKED_RECIPE)
        }
        likedRecipeRepository.save(LikedRecipe(likedBy = member, recipe = recipe))
        return true
    }

    @Transactional
    fun unsaveRecipe(member: Member, recipeId: Long): Boolean {
        val recipe = recipeRepository.findById(recipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        return savedRecipeRepository.unsaveRecipeAndMember(recipeId = recipeId, memberId = member.id)
    }

    @Transactional
    fun unlikeRecipe(member: Member, recipeId: Long): Boolean {
        val recipe = recipeRepository.findById(recipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        return likedRecipeRepository.unlikeRecipeAndMember(memberId = member.id, recipeId = recipeId)
    }

    @Transactional
    fun createShortFormRecipe(member: Member, shortFormRecipeCreateRequest: ShortFormRecipeCreateRequest): Long {
        val shortFormRecipe = ShortFormRecipe(
            name = shortFormRecipeCreateRequest.shortform_name,
            description = shortFormRecipeCreateRequest.description,
            video_url = shortFormRecipeCreateRequest.video_url,
            writtenBy = member,
            video_time = shortFormRecipeCreateRequest.video_time
        )
        shortFormRecipeRepository.save(shortFormRecipe)
        for (ingredientId: Long in shortFormRecipeCreateRequest.ingredients_ids) {
            val shortFormIngredients = ShortFormIngredients(
                shortFormRecipe = shortFormRecipe,
                ingredient = ingredientRepository.findById(ingredientId).orElseThrow {
                    BusinessException(ErrorCode.NO_INGREDIENT_FOUND)
                }
            )
            shortFormIngredientsRepository.save(shortFormIngredients)
        }
        return shortFormRecipe.id
    }

    fun getShortForms(member: Member, offset: Int, pageSize: Int): Slice<ShortFormSimpleResponse> {
        return shortFormRecipeRepository.findShortFormRecipes(createPageable(offset, pageSize))
            .map { shortFormRecipe ->
                ShortFormSimpleResponse.of(
                    shortFormRecipe,
                    likedShortFormRepository.existsByLikedByAndShortFormRecipe_Id(
                        shortFormRecipeId = shortFormRecipe.id,
                        likedBy = member
                    ),
                    savedShortFormRepository.existsBySavedByAndShortFormRecipe_Id(
                        shortFormRecipeId = shortFormRecipe.id,
                        savedBy = member
                    )
                )
            }
    }

    @Transactional
    fun saveShortFormRecipe(member: Member, shortFormRecipeId: Long): Boolean {
        val shortFormRecipe = shortFormRecipeRepository.findById(shortFormRecipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }

        if (savedShortFormRepository.existsBySavedByAndShortFormRecipe_Id(member, shortFormRecipeId)) {
            throw BusinessException(ErrorCode.ALREADY_SAVED_RECIPE)
        }
        savedShortFormRepository.save(SavedShortFormRecipe(savedBy = member, shortFormRecipe = shortFormRecipe))
        return true
    }

    @Transactional
    fun likeShortFormRecipe(member: Member, shortFormRecipeId: Long): Boolean {
        val shortFormRecipe = shortFormRecipeRepository.findById(shortFormRecipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        if (likedShortFormRepository.existsByLikedByAndShortFormRecipe_Id(member, shortFormRecipeId)) {
            throw BusinessException(ErrorCode.ALREADY_LIKED_RECIPE)
        }
        likedShortFormRepository.save(LikedShortFormRecipe(likedBy = member, shortFormRecipe = shortFormRecipe))
        return true
    }

    @Transactional
    fun unsaveShortFormRecipe(member: Member, shortFormRecipeId: Long): Boolean {
        val shortFormRecipe = shortFormRecipeRepository.findById(shortFormRecipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        return savedShortFormRepository.unsaveShortFormRecipe(member, shortFormRecipe.id)
    }

    @Transactional
    fun unlikeShortFormRecipe(member: Member, shortFormRecipeId: Long): Boolean {
        val shortFormRecipe = shortFormRecipeRepository.findById(shortFormRecipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        return likedShortFormRepository.unlikeShortFormRecipeAndMember(
            memberId = member.id,
            shortFormRecipeId = shortFormRecipe.id
        )
    }

    @Transactional
    fun deleteShortFormRecipe(member: Member, shortFormRecipeId: Long): Boolean {
        val shortFormRecipe = shortFormRecipeRepository.findById(shortFormRecipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        return shortFormRecipeRepository.deleteShortFormRecipe(
            shortFormId = shortFormRecipe.id
        )
    }

    fun findShortFormRecipeDetail(member: Member, shortFormRecipeId: Long): ShortFormDetailResponse {
        val shortformRecipe = shortFormRecipeRepository.findById(shortFormRecipeId)
            .orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        return ShortFormDetailResponse.of(
            shortformRecipe,
            savedShortFormRepository.existsBySavedByAndShortFormRecipe_Id(
                shortFormRecipeId = shortFormRecipeId,
                savedBy = member
            ),
            likedShortFormRepository.existsByLikedByAndShortFormRecipe_Id(
                likedBy = member,
                shortFormRecipeId = shortFormRecipeId
            )
        )
    }

    fun findRecipeByThemeLivingAlone(offset: Int, pageSize: Int, member: Member): Slice<RecipeResponse> {
        return recipeRepository.findRecipeByThemeLivingAlone(createPageable(offset, pageSize))
            .map { recipe ->
                RecipeResponse.of(
                    recipe,
                    savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = member.id)
                )
            }
    }

    fun findRecipeByThemeForDieting(offset: Int, pageSize: Int, member: Member): Slice<RecipeResponse> {
        return recipeRepository.findRecipeByThemeForDieting(createPageable(offset, pageSize))
            .map { recipe ->
                RecipeResponse.of(
                    recipe,
                    savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = member.id)
                )
            }
    }

    fun findRecipeByThemeBudgetHappiness(offset: Int, pageSize: Int, member: Member): Slice<RecipeResponse> {
        return recipeRepository.findRecipeByThemeBudgetHappiness(createPageable(offset, pageSize))
            .map { recipe ->
                RecipeResponse.of(
                    recipe,
                    savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = member.id)
                )
            }
    }

    fun findRecipeByThemeHousewarming(offset: Int, pageSize: Int, member: Member): Slice<RecipeResponse> {
        return recipeRepository.findRecipeByThemeHousewarming(createPageable(offset, pageSize))
            .map { recipe ->
                RecipeResponse.of(
                    recipe,
                    savedRecipeRepository.checkMemberSavedRecipe(recipeId = recipe.id, memberId = member.id)
                )
            }
    }
}