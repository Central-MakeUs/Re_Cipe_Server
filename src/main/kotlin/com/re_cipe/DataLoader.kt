package com.re_cipe

import com.re_cipe.comments.domain.ShortFormComments
import com.re_cipe.comments.domain.repository.ShortFormCommentRepository
import com.re_cipe.ingredient.domain.*
import com.re_cipe.ingredient.domain.repository.IngredientRepository
import com.re_cipe.ingredient.domain.repository.RecipeIngredientRepository
import com.re_cipe.ingredient.domain.repository.ShortFormIngredientsRepository
import com.re_cipe.member.domain.Member
import com.re_cipe.member.domain.Provider
import com.re_cipe.member.domain.repository.MemberRepository
import com.re_cipe.notice.domain.Notice
import com.re_cipe.notice.domain.QnA
import com.re_cipe.notice.domain.repository.NoticeRepository
import com.re_cipe.notice.domain.repository.QnARepository
import com.re_cipe.recipe.domain.Recipe
import com.re_cipe.recipe.domain.ShortFormRecipe
import com.re_cipe.recipe.domain.repository.RecipeRepository
import com.re_cipe.recipe.domain.repository.ShortFormRecipeRepository
import com.re_cipe.reviews.service.ReviewsService
import com.re_cipe.reviews.ui.dto.ReviewCreateRequest
import com.re_cipe.search.domain.Keyword
import com.re_cipe.search.domain.KeywordRepository
import com.re_cipe.stage.domain.RecipeStage
import com.re_cipe.stage.domain.repository.StageRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
@RequiredArgsConstructor
@Slf4j
class DataLoader(
    val ingredientRepository: IngredientRepository,
    val recipeRepository: RecipeRepository,
    val shortFormRecipeRepository: ShortFormRecipeRepository,
    val shortFormCommentRepository: ShortFormCommentRepository,
    val memberRepository: MemberRepository,
    val shortFormIngredientsRepository: ShortFormIngredientsRepository,
    val recipeIngredientRepository: RecipeIngredientRepository,
    val stageRepository: StageRepository,
    val reviewsService: ReviewsService,
    val keywordRepository: KeywordRepository,
    val qnARepository: QnARepository,
    val noticeRepository: NoticeRepository
) : ApplicationRunner {
    @Value("\${dataloader.push_data}")
    private var dataLoaderOn: Boolean? = null;
    override fun run(args: ApplicationArguments) {
        if (dataLoaderOn == true)
            runDataLoader()
    }

    @Throws(InterruptedException::class)
    private fun runDataLoader() {
        var ingredients = listOf(
            Ingredient(
                name = "당근",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "당근 상품명",
                coupangProductPrice = 1000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "감자",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "감자 상품명",
                coupangProductPrice = 1200,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "고구마",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "고구마 상품명",
                coupangProductPrice = 1500,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "양파",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "양파 상품명",
                coupangProductPrice = 500,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "대파",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "대파 상품명",
                coupangProductPrice = 700,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "상추",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "상추 상품명",
                coupangProductPrice = 800,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "시금치",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "시금치 상품명",
                coupangProductPrice = 600,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "케일",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "케일 상품명",
                coupangProductPrice = 900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "브로콜리",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "브로콜리 상품명",
                coupangProductPrice = 1200,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "콜리플라워",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "콜리플라워 상품명",
                coupangProductPrice = 1100,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "딸기",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "딸기 상품명",
                coupangProductPrice = 3000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "체리",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "체리 상품명",
                coupangProductPrice = 2500,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "라즈베리",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "라즈베리 상품명",
                coupangProductPrice = 2800,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "사과",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "사과 상품명",
                coupangProductPrice = 1500,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "배",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "배 상품명",
                coupangProductPrice = 2000,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "수박",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "수박 상품명",
                coupangProductPrice = 3000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "오렌지",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "오렌지 상품명",
                coupangProductPrice = 1200,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "바나나",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "바나나 상품명",
                coupangProductPrice = 800,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "토마토",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "토마토 상품명",
                coupangProductPrice = 600,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "오이",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "오이 상품명",
                coupangProductPrice = 400,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "소고기",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "소고기 상품명",
                coupangProductPrice = 5000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "돼지고기",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "돼지고기 상품명",
                coupangProductPrice = 4000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "등심",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "돼지고기 등심 상품명",
                coupangProductPrice = 5500,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "베이컨",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "돼지고기 베이컨 상품명",
                coupangProductPrice = 7000,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "닭가슴살",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "닭 가슴살 상품명",
                coupangProductPrice = 6000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "닭다리살",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "닭 다리살 상품명",
                coupangProductPrice = 4500,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "연어",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "연어 상품명",
                coupangProductPrice = 7000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "참치",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "참치 상품명",
                coupangProductPrice = 6000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "광어",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "광어 상품명",
                coupangProductPrice = 8500,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "새우",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "새우 상품명",
                coupangProductPrice = 12000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "게",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "게 상품명",
                coupangProductPrice = 5000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "조개",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "조개 상품명",
                coupangProductPrice = 6000,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "우유",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "우유 상품명",
                coupangProductPrice = 1500,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "저지방 우유",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "저지방 우유 상품명",
                coupangProductPrice = 1200,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "계란",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "쓰지않은 계란 상품명",
                coupangProductPrice = 2000,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "그리스 요거트",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "그리스 요거트 상품명",
                coupangProductPrice = 2500,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "프로바이오틱스 요거트",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "프로바이오틱스 요거트 상품명",
                coupangProductPrice = 1800,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "체다 치즈",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "체다 치즈 상품명",
                coupangProductPrice = 3000,
                isRocketDelivery = false
            ),
            Ingredient(
                name = "모짜렐라 치즈",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "모짜렐라 치즈 상품명",
                coupangProductPrice = 2800,
                isRocketDelivery = true
            ),

            Ingredient(
                name = "소금",
                ingredientType = IngredientType.SAUCE,
                ingredientUnitEnum = UnitEnum.T,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "소금 상품명",
                coupangProductPrice = 100,
                isRocketDelivery = true
            ),

            Ingredient(
                name = "간장",
                ingredientType = IngredientType.SAUCE,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "간장 상품명",
                coupangProductPrice = 100,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "참기름",
                ingredientType = IngredientType.SAUCE,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "참기름 상품명",
                coupangProductPrice = 100,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "식용유",
                ingredientType = IngredientType.OIL,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "식용유 상품명",
                coupangProductPrice = 2000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "밀가루",
                ingredientType = IngredientType.POWDER,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/218520566?itemId=676622340&vendorItemId=4743644012&pickType=COU_PICK&q=%EB%B0%94%EB%82%98%EB%82%98&itemsCount=36&searchId=ab3bc3b0e9ce40b186ce36b6c01e9c0e&rank=1&isAddedCart=",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2019/05/08/16/9/89441172-dc5e-49b6-8fb9-3b5b4216aabe.jpg",
                coupangProductName = "밀가루 상품명",
                coupangProductPrice = 1500,
                isRocketDelivery = false
            )
        )
        ingredients = ingredientRepository.saveAll(ingredients)

        keywordRepository.saveAll(
            mutableListOf(
                Keyword(word = "간장", count = 2),
                Keyword(word = "간장계란밥"),
                Keyword(word = "당근"),
                Keyword(word = "감자"),
                Keyword(word = "초코칩존잘"),
                Keyword(word = "계란"),
                Keyword(word = "자취"),
                Keyword(word = "음식"),
                Keyword(word = "궁극"),
                Keyword(word = "JMT"),
                Keyword(word = "초코칩")
            )
        )

        var member1 = Member(
            email = "Cheachea@gmail.com",
            nickname = "채채",
            provider = Provider.GOOGLE
        )
        member1 = memberRepository.save(member1)

        var member2 = Member(
            email = "dev.kiho@gmail.com",
            nickname = "초코칩",
            provider = Provider.GOOGLE
        )
        member2 = memberRepository.save(member2)

        var recipe1 = Recipe(
            name = "내가 만든 극강의 JMT 간장 계란밥",
            description = "안 먹어본 사람은 있지만, 한 번 먹어본 사람은 없는 궁극의 초간단 간장 계란밥 레시피!",
            cook_time = 10,
            serving_size = 1,
            thumbnail_img = "https://d1jg55wkcrciwu.cloudfront.net/images/a5838556-94f1-4602-aa83-3035fa340b94.jpeg",
            writtenBy = member2
        )
        recipe1 = recipeRepository.save(recipe1)

        val stageList = mutableListOf(
            RecipeStage(stage_description = "밥을 한 주걱 넣습니당", recipe = recipe1),
            RecipeStage(stage_description = "간장을 넣어줍니다!", recipe = recipe1),
            RecipeStage(stage_description = "기호에 맞게 참깨와 참기름을 넣습니다!", recipe = recipe1),
            RecipeStage(stage_description = "냠냠😁", recipe = recipe1),
        )
        stageRepository.saveAll(stageList)

        reviewsService.createReview(recipe1.id, member1, ReviewCreateRequest("간장 계란밥은 ㅇㅈ이지...", 5, listOf()))

        for (stage in stageList) {
            recipe1.stages.add(stage)
        }

        var recipeIngredients = mutableListOf(
            RecipeIngredients(
                recipe = recipe1,
                ingredient = ingredients[34],
                ingredientSize = 2.0
            ),
            RecipeIngredients(
                recipe = recipe1,
                ingredient = ingredients[40],
                ingredientSize = 30.0
            ),
            RecipeIngredients(
                recipe = recipe1,
                ingredient = ingredients[41],
                ingredientSize = 10.0
            ),
        )
        recipeIngredientRepository.saveAll(recipeIngredients)

        for (ingredient in recipeIngredients) {
            recipe1.ingredientList.add(ingredient)
        }

        var shortFormRecipe1 = ShortFormRecipe(
            name = "나의 음식 모음기!",
            description = "제가 자취하면서 만들어 먹은 음식들입니다!",
            video_url = "https://d1jg55wkcrciwu.cloudfront.net/videos/testvideo.mp4",
            writtenBy = member1,
            video_time = "00:06"
        )
        shortFormRecipe1 = shortFormRecipeRepository.save(shortFormRecipe1)

        val shortFormIngredients = mutableListOf(
            ShortFormIngredients(shortFormRecipe = shortFormRecipe1, ingredient = ingredients[0]),
            ShortFormIngredients(shortFormRecipe = shortFormRecipe1, ingredient = ingredients[1]),
            ShortFormIngredients(shortFormRecipe = shortFormRecipe1, ingredient = ingredients[2])
        )
        shortFormIngredientsRepository.saveAll(shortFormIngredients)

        for (shorformIngredient in shortFormIngredients) {
            shortFormRecipe1.ingredients.add(shorformIngredient)
        }

        var shortFormComments = ShortFormComments(
            content = "너무 유익해요!",
            writtenBy = member2,
            shortFormRecipe = shortFormRecipe1
        )
        shortFormComments = shortFormCommentRepository.save(shortFormComments)

        shortFormRecipe1.commentList.add(shortFormComments)

        qnARepository.save(
            QnA(
                question = "계정 탈퇴가 안될 때는 어떻게 해야 하나요? ",
                answer = "계정 탈퇴는 마이페이지>설정>회원탈퇴를 통해 이루어집니다. 해당 방법으로 재시도 한 경우에도 계정 탈퇴 방법을 찾지 못한 경우 고객센터로 연락 바랍니다. "
            )
        )

        noticeRepository.save(Notice(title = "Plate 출시 🥳🎉", content = "Plate가 출시되었습니다!"))
    }
}