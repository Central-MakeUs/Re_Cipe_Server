package com.re_cipe.reviews.service

import com.re_cipe.exception.BusinessException
import com.re_cipe.exception.ErrorCode
import com.re_cipe.global.util.SlackUtil
import com.re_cipe.image.ReviewImages
import com.re_cipe.member.domain.Member
import com.re_cipe.recipe.domain.repository.RecipeRepository
import com.re_cipe.reviews.domain.Reviews
import com.re_cipe.reviews.domain.ReviewsLikes
import com.re_cipe.reviews.domain.repository.ReviewImagesRepository
import com.re_cipe.reviews.domain.repository.ReviewLikesRepository
import com.re_cipe.reviews.domain.repository.ReviewsRepository
import com.re_cipe.reviews.ui.dto.MyReviewResponse
import com.re_cipe.reviews.ui.dto.ReviewCreateRequest
import com.re_cipe.reviews.ui.dto.ReviewResponse
import com.re_cipe.reviews.ui.dto.ReviewScores
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReviewsService(
    private val reviewsRepository: ReviewsRepository,
    private val recipeRepository: RecipeRepository,
    private val reviewImagesRepository: ReviewImagesRepository,
    private val slackUtil: SlackUtil,
    private val reviewLikesRepository: ReviewLikesRepository
) {
    fun getReviewsByLatest(pageable: Pageable, recipeId: Long, member: Member): Slice<ReviewResponse> {

        return reviewsRepository.findReviewsByLatest(pageable, recipeId)
            .map { review -> ReviewResponse.of(review, checkLikedReview(review, member)) }
    }

    fun getReviewsByPopular(pageable: Pageable, recipeId: Long, member: Member): Slice<ReviewResponse> {
        return reviewsRepository.findReviewsByPopular(pageable, recipeId)
            .map { review -> ReviewResponse.of(review, checkLikedReview(review, member)) }
    }

    private fun checkLikedReview(reviews: Reviews, member: Member): Boolean {
        return reviews.likes.stream()
            .anyMatch { reviewLike -> reviewLike.likedBy.equals(member) }
    }

    fun getMyReviews(member: Member): List<MyReviewResponse> {
        return reviewsRepository.findMyReviews(memberId = member.id)
            .map { reviews: Reviews -> MyReviewResponse.of(reviews) }
    }

    fun getReviewScores(recipeId: Long): ReviewScores {
        val recipe = recipeRepository.findById(recipeId).orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }
        return ReviewScores(
            fivePoint = reviewsRepository.findReviewCountByRating(5, recipeId),
            fourPoint = reviewsRepository.findReviewCountByRating(4, recipeId),
            threePoint = reviewsRepository.findReviewCountByRating(3, recipeId),
            twoPoint = reviewsRepository.findReviewCountByRating(2, recipeId),
            onePoint = reviewsRepository.findReviewCountByRating(1, recipeId),
            recipe.rating
        )
    }

    @Transactional
    fun createReview(recipeId: Long, member: Member, reviewCreateRequest: ReviewCreateRequest): Long {
        val foundRecipe =
            recipeRepository.findById(recipeId).orElseThrow { BusinessException(ErrorCode.NO_RECIPE_FOUND) }

        val review = Reviews(
            rating = reviewCreateRequest.review_rating,
            content = reviewCreateRequest.review_content,
            recipe = foundRecipe,
            writtenBy = member
        )
        reviewsRepository.save(review)
        foundRecipe.addReview(review)
        for (img in reviewCreateRequest.review_imgs) {
            val reviewImages = ReviewImages(image_url = img, reviews = review)
            reviewImagesRepository.save(reviewImages)
            review.images.add(reviewImages)
        }

        return review.id
    }

    @Transactional
    fun deleteReview(reviewId: Long, member: Member): Boolean {
        val review = reviewsRepository.findById(reviewId).orElseThrow { BusinessException(ErrorCode.NO_REVIEW_FOUND) }
        if (review.writtenBy.id != member.id) {
            throw BusinessException(ErrorCode.NO_AUTHENTICATION)
        }
        return reviewsRepository.deleteReview(review.id)
    }

    @Transactional
    fun reportReview(reviewId: Long, member: Member): Boolean {
        slackUtil.sendReviewReport(
            member = member,
            review = reviewsRepository.findById(reviewId).orElseThrow { BusinessException(ErrorCode.NO_REVIEW_FOUND) })
        reviewsRepository.deleteReview(reviewId)
        return true
    }

    fun getAllReviewPhoto(recipeId: Long): List<String> {
        return reviewsRepository.findAllRecipePhoto(recipeId)
    }

    @Transactional
    fun likeReview(reviewId: Long, member: Member): Boolean {
        val review = reviewsRepository.findById(reviewId).orElseThrow { BusinessException(ErrorCode.NO_REVIEW_FOUND) }
        if (reviewLikesRepository.existsByLikedByIdAndReviewsId(memberId = member.id, reviewId = reviewId)) {
            throw BusinessException(ErrorCode.LIKED_ERROR)
        }
        var reviewsLikes = ReviewsLikes(
            likedBy = member,
            reviews = review
        )
        reviewsLikes = reviewLikesRepository.save(reviewsLikes)

        review.likes.add(reviewsLikes)
        return true
    }

    @Transactional
    fun unlikeReview(reviewId: Long, member: Member): Boolean {
        val review = reviewsRepository.findById(reviewId).orElseThrow { BusinessException(ErrorCode.NO_REVIEW_FOUND) }
        if (!reviewLikesRepository.existsByLikedByIdAndReviewsId(memberId = member.id, reviewId = reviewId)) {
            throw BusinessException(ErrorCode.LIKED_ERROR)
        }

        val reviewsLikes =
            reviewLikesRepository.findByLikedByIdAndReviewsId(memberId = member.id, reviewId = reviewId)
        review.likes.remove(reviewsLikes)
        reviewLikesRepository.delete(reviewsLikes)
        return true
    }
}