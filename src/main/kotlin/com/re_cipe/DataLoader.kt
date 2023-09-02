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
                coupangUrl = "https://www.coupang.com/vp/products/7281141390?itemId=18589852823&vendorItemId=71136852512&sourceType=srp_product_ads&clickEventId=9e2af240-4975-11ee-b44b-69bf9fa3b997&korePlacement=15&koreSubPlacement=1&clickEventId=9e2af240-4975-11ee-b44b-69bf9fa3b997&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/b3aa/cb9fc03de7eb49734a4d4bf8d51baf458be485fc1b002f12b5f7352c5907.jpg",
                coupangProductName = "조은먹거리 국내산 당근 흙당근 햇 당근 3kg 5kg 10kg",
                coupangProductPrice = 19800,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "감자",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/7523768459?itemId=19738684171&vendorItemId=86842438335&sourceType=srp_product_ads&clickEventId=9ed49fc0-4975-11ee-ad25-00a82df2264c&korePlacement=15&koreSubPlacement=1&clickEventId=9ed49fc0-4975-11ee-ad25-00a82df2264c&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/db7a/5f3a388ab5243d80c9c6d210fd17bb3f849370ade00e4a9da33ccc8d93d6.jpg",
                coupangProductName = "[맛통령] 국내산 햇감자 포슬포슬 수미감자",
                coupangProductPrice = 16910,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "고구마",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/1929079788?itemId=3275029773&vendorItemId=71262037534&sourceType=srp_product_ads&clickEventId=9f8419a0-4975-11ee-ad59-492cbc1c4baf&korePlacement=15&koreSubPlacement=1&clickEventId=9f8419a0-4975-11ee-ad59-492cbc1c4baf&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/2b96/fe7e61631cc663223543f2578b71872e6c8318bca2d685cd286bc7040aea.jpg",
                coupangProductName = "해남 꿀고구마 호박고구마",
                coupangProductPrice = 52900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "양파",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/7368889365?itemId=19003792846&vendorItemId=77935321441&sourceType=srp_product_ads&clickEventId=a0308640-4975-11ee-83a3-27c1083eacdd&korePlacement=15&koreSubPlacement=1&clickEventId=a0308640-4975-11ee-83a3-27c1083eacdd&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/831c/0a3960aa859e29a51b769962f2064794b8de6a00946711f969f696eaaa49.jpg",
                coupangProductName = "23년 무안 햇양파 3kg 5Kg 10Kg 소/중대혼합/특",
                coupangProductPrice = 14900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "대파",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/206268606?itemId=608434131&vendorItemId=4596075313&sourceType=srp_product_ads&clickEventId=a0cf8560-4975-11ee-ad49-0e6b132de924&korePlacement=15&koreSubPlacement=1&clickEventId=a0cf8560-4975-11ee-ad49-0e6b132de924&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/70b4/9b3555f9d16551ab848adb3095bc30926e1705e43f97d0917382ab03d62a.jpg",
                coupangProductName = "우주농원 신선한국내산 손질대파",
                coupangProductPrice = 4450,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "상추",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/7400090864?itemId=19151910646&vendorItemId=83116515402&sourceType=srp_product_ads&clickEventId=a1749f00-4975-11ee-8f98-7c9f0ed87cbd&korePlacement=15&koreSubPlacement=1&clickEventId=a1749f00-4975-11ee-8f98-7c9f0ed87cbd&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/1124/e6b9a2cb54156e7dcfe9cada357aac7c868c04d900935f47af8d243e8263.jpg",
                coupangProductName = "안산팜 샐러드상추 랜덤혼합(카이피라 이자벨 버터헤드)",
                coupangProductPrice = 14800,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "시금치",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/4906107917?itemId=6402143506&vendorItemId=73696938289&sourceType=srp_product_ads&clickEventId=a217bcd0-4975-11ee-b1e8-f06dcd1be8b4&korePlacement=15&koreSubPlacement=1&clickEventId=a217bcd0-4975-11ee-b1e8-f06dcd1be8b4&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/04d0/e83fc0246346d4ef2ba12b0ecbb1e8ae63545adb1b863668a2e35068e8d0.jpg",
                coupangProductName = "시금치나물 밥도둑믿음반찬",
                coupangProductPrice = 3800,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "케일",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/1509888565?itemId=3917187495&vendorItemId=70583040508&sourceType=srp_product_ads&clickEventId=a2bc8850-4975-11ee-90fc-2d4563759938&korePlacement=15&koreSubPlacement=1&clickEventId=a2bc8850-4975-11ee-90fc-2d4563759938&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/bf82/fea0589ef6e113ec326e7e839c6c9a510f60ceb06f7fed0ecc9045c5a537.jpg",
                coupangProductName = "FRESH 새벽에 수확한 국내산 유기농 무농약 케일 신선초 샐러리 생 파슬리",
                coupangProductPrice = 10900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "브로콜리",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/4874444452?itemId=6339533155&vendorItemId=73634892675&pickType=COU_PICK",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/84438627163531-704bd851-3a88-4969-b0be-8c37a6f040b8.jpg",
                coupangProductName = "국내산 브로콜리",
                coupangProductPrice = 2990,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "콜리플라워",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/1464080152?itemId=2518131299&vendorItemId=70511072191&sourceType=srp_product_ads&clickEventId=a3eddc60-4975-11ee-819b-2d1474dc9aa8&korePlacement=15&koreSubPlacement=1&clickEventId=a3eddc60-4975-11ee-819b-2d1474dc9aa8&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/bb19/5f9a71b329bc1e14f700ea589748359bfe50b060515d8fe466ddd36603f6.jpg",
                coupangProductName = "글로벌 냉동 컬리플라워 1kg",
                coupangProductPrice = 3400,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "딸기",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/7264238006?itemId=18510063528&vendorItemId=80935642202&sourceType=srp_product_ads&clickEventId=a4984d30-4975-11ee-abd2-226e8483bc0d&korePlacement=15&koreSubPlacement=1&clickEventId=a4984d30-4975-11ee-abd2-226e8483bc0d&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/1eb3/6967d905b7c0c0efb2e5a680adc5b5f03c03defee24cfc210ce6c27f3a54.jpg",
                coupangProductName = "감좋은날 23년산 국산 냉동딸기 / 아이스딸기",
                coupangProductPrice = 72000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "체리",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/2176242783?itemId=3703208779&vendorItemId=75426232248&sourceType=srp_product_ads&clickEventId=a541d3a0-4975-11ee-88f6-0240eb8b318d&korePlacement=15&koreSubPlacement=1&clickEventId=a541d3a0-4975-11ee-88f6-0240eb8b318d&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/350b/7c95960efd33af18d3e18b12d25eac7d90693d3740b1e3902c5e1985235b.png",
                coupangProductName = "몽모랑시 타트체리 유산균 효소",
                coupangProductPrice = 29570,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "라즈베리",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/6204311602?itemId=12329401065&vendorItemId=79599403714&sourceType=srp_product_ads&clickEventId=a5f34950-4975-11ee-9311-dc4a17183d99&korePlacement=15&koreSubPlacement=1&clickEventId=a5f34950-4975-11ee-9311-dc4a17183d99&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/5e5c/05644d53310a2dc84b09589212688edcec4dd6664443d18bef9c1d02c7b8.jpg",
                coupangProductName = "프리미엄로사 유기농 라즈베리 원액 100% 500ml",
                coupangProductPrice = 81000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "사과",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/7487616284?itemId=19573141252&vendorItemId=75585579631&sourceType=srp_product_ads&clickEventId=a69555b0-4975-11ee-91ac-77b68311d2fd&korePlacement=15&koreSubPlacement=1&clickEventId=a69555b0-4975-11ee-91ac-77b68311d2fd&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/b751/2cca67c2133efe701ac98c03dca1bcda59c941f35a1597d445d8e76551ef.jpg",
                coupangProductName = "햇 빨간 가정용 한입 사과 4.5KG (당일발송)",
                coupangProductPrice = 32950,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "배",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/7533775009?itemId=19788342089&vendorItemId=80095380797&sourceType=srp_product_ads&clickEventId=a749b190-4975-11ee-ae46-2e8bda73e2be&korePlacement=15&koreSubPlacement=1&clickEventId=a749b190-4975-11ee-ae46-2e8bda73e2be&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/70f6/c7e1a107abb9253f020862de3fd277417d1002615a8c0c7864bcbe05a2b1.jpg",
                coupangProductName = "[아리팜] 23년 햇배 나주원황배 3kg / 5kg",
                coupangProductPrice = 15900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "수박",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/5341974172?itemId=7833549308&vendorItemId=75123335503",
                coupangProductImage = "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2849495144999604-66105b9a-e18a-454b-a888-258f6e1b125b.png",
                coupangProductName = "곰곰 당도선별 수박",
                coupangProductPrice = 23840,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "오렌지",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/5685408757?itemId=9400569653&vendorItemId=3468129146&sourceType=srp_product_ads&clickEventId=a8d840d0-4975-11ee-984f-35e66b59a073&korePlacement=15&koreSubPlacement=1&clickEventId=a8d840d0-4975-11ee-984f-35e66b59a073&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/b2e6/6fa29c17391eb069d2b605a506f1e76f1d1eb110d0c329e73cba38f17c77.jpg",
                coupangProductName = "오렌지",
                coupangProductPrice = 36900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "바나나",
                ingredientType = IngredientType.FRUIT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/7231286654?itemId=18349310259&vendorItemId=85493315368&sourceType=srp_product_ads&clickEventId=a96eb470-4975-11ee-b9c4-1eec8b7c173b&korePlacement=15&koreSubPlacement=1&clickEventId=a96eb470-4975-11ee-b9c4-1eec8b7c173b&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/c06c/2a1021d3842fd00bbd0d1e7701a59affe7bab618d70e720751c1c8ca2c13.jpg",
                coupangProductName = "바나나꽃( Babana Blossom)-10kg",
                coupangProductPrice = 63000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "토마토",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/6867638074?itemId=16419036181&vendorItemId=83609949981&sourceType=srp_product_ads&clickEventId=aa2f6c60-4975-11ee-9bef-bfd82e35cdfc&korePlacement=15&koreSubPlacement=1&clickEventId=aa2f6c60-4975-11ee-9bef-bfd82e35cdfc&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/fde8/28b19c27f79eacde8894dc3711bd23fcddb820ea86eeeda07d86065ac60a.jpeg",
                coupangProductName = "2023년 햇 하우스 찰토마토 출시 완숙 토마토 가정용 혼합 5kg",
                coupangProductPrice = 34900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "오이",
                ingredientType = IngredientType.VEGETABLE,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/7551844285?itemId=19876019177&vendorItemId=86127919513&sourceType=srp_product_ads&clickEventId=aad54950-4975-11ee-b8dc-b33c363a6b29&korePlacement=15&koreSubPlacement=1&clickEventId=aad54950-4975-11ee-b8dc-b33c363a6b29&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/b6fe/ccd989c3f0fc255fae6750359a25ceb54f6dbe2a864a2d100dece49aae62.jpg",
                coupangProductName = "남도5일장 국내산 백다다기오이 4kg 8kg ( 못난이 믹스)",
                coupangProductPrice = 18500,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "소고기",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/186342429?itemId=532786869&vendorItemId=4388482361&sourceType=srp_product_ads&clickEventId=ab9739c0-4975-11ee-9391-45a730e12e2d&korePlacement=15&koreSubPlacement=1&clickEventId=ab9739c0-4975-11ee-9391-45a730e12e2d&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/images/2018/07/19/16/8/79a3f765-f0cd-4bc5-8f89-64e2e780c962.jpg",
                coupangProductName = "[닥터카우] 수입산 소고기 미국산 (초이스) 부채살 2kg(200gx10)",
                coupangProductPrice = 53900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "돼지고기",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/6355305418?itemId=13391391980&vendorItemId=80646219075&sourceType=srp_product_ads&clickEventId=ac346420-4975-11ee-9c0e-bf408bed0224&korePlacement=15&koreSubPlacement=1&clickEventId=ac346420-4975-11ee-9c0e-bf408bed0224&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/8f4a/67004f3176c9d414f0e258969a2e60d0913edcd194b4747c51b39ab6df9b.jpg",
                coupangProductName = "상상창고 국내산 한돈 돼지 뒷고기모듬",
                coupangProductPrice = 29910,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "등심",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/6885848371?itemId=16515689943&vendorItemId=83702936156&sourceType=srp_product_ads&clickEventId=ace27e70-4975-11ee-bfd7-2d822e9bf8fe&korePlacement=15&koreSubPlacement=1&clickEventId=ace27e70-4975-11ee-bfd7-2d822e9bf8fe&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/225c/49596658e0dcdc7d11d0ec4f0bb0bd01c81a39a847ff57450b3f92710541.jpg",
                coupangProductName = "특별한우리 1등급 한우 꽃등심 구이용 (냉장) 250g * 2팩 (총 500g)",
                coupangProductPrice = 49900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "베이컨",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/4550031580?itemId=5521431697&vendorItemId=72820976983&pickType=COU_PICK",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/3832669421753003-544ab6c0-8ce1-4f69-a12b-bc08a25268e8.jpg",
                coupangProductName = "곰곰 담백한 베이컨",
                coupangProductPrice = 5090,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "닭가슴살",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/1440599547?itemId=19152350404&vendorItemId=5356628328&sourceType=srp_product_ads&clickEventId=ae39f820-4975-11ee-bf36-badae02835c3&korePlacement=15&koreSubPlacement=1&clickEventId=ae39f820-4975-11ee-bf36-badae02835c3&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/4f9e/d46db960191ab792a5b2cf0ebbc6b9158c68db8fa81926cd2a05486c719c.jpg",
                coupangProductName = "[랭킹닭컴] 잇메이트 스팀 닭가슴살 100g",
                coupangProductPrice = 28950,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "닭다리살",
                ingredientType = IngredientType.MEAT,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/7299252265?itemId=18674724550&vendorItemId=81130211863&sourceType=srp_product_ads&clickEventId=aedfae00-4975-11ee-8eb8-acaebff0fb5d&korePlacement=15&koreSubPlacement=1&clickEventId=aedfae00-4975-11ee-8eb8-acaebff0fb5d&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/c8ab/0be9ff6e518098c9490e724505ab8868e101786bd502fe04eac69aa28819.jpg",
                coupangProductName = "킹닭 훈제 닭다리살 200g x 30팩 통닭다리살 통다리살 식단관리 닭다리 순살",
                coupangProductPrice = 87900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "연어",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/140037198?itemId=408259568&vendorItemId=4687570195&sourceType=srp_product_ads&clickEventId=af9c6e50-4975-11ee-9329-d7eca01b4b1a&korePlacement=15&koreSubPlacement=1&clickEventId=af9c6e50-4975-11ee-9329-d7eca01b4b1a&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/898b/fe5442ce8f5263e9c90a8b300275d88585dfa27ac1de8b69e3f0c8d19380.jpg",
                coupangProductName = "팸쿡 싱싱한 생연어 연어회 꼬릿살 500g 생선>>연어>>연어",
                coupangProductPrice = 26900,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "참치",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/6398710176?itemId=13673905676&vendorItemId=80925874940&pickType=COU_PICK",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/8558233514918916-3416c42b-0678-4660-a58b-54faa20e5375.jpg",
                coupangProductName = "동원 라이트 스탠다드 참치",
                coupangProductPrice = 12980,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "광어",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/339173249?itemId=1080557141&vendorItemId=5581529002&pickType=COU_PICK",
                coupangProductImage = "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/5382473304272202-f385a000-42c5-4377-b1c7-f7d5d2b5e32d.jpg",
                coupangProductName = "숙성 광어회",
                coupangProductPrice = 15690,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "새우",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/7558406131?itemId=19907550180&vendorItemId=83145560894&sourceType=srp_product_ads&clickEventId=b19d4760-4975-11ee-be8c-65b150bdf79c&korePlacement=15&koreSubPlacement=1&clickEventId=b19d4760-4975-11ee-be8c-65b150bdf79c&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/f8e8/94bafb37f3c57a80a58f1f190c9ff926940cd247bfaba4a454605f4f90e1.jpg",
                coupangProductName = "아빠가 키운 해남미남새우 왕새우 1키로 35미내외 버터제공",
                coupangProductPrice = 29000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "게",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/7311827806?itemId=18733354814&vendorItemId=85649318252&sourceType=srp_product_ads&clickEventId=b250dff0-4975-11ee-8e9d-069bfa7de653&korePlacement=15&koreSubPlacement=1&clickEventId=b250dff0-4975-11ee-8e9d-069bfa7de653&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/2f54/c4b6e14c35c4de27ab081e55bb0edeba7c2717cf7026c635279367a8e302.jpg",
                coupangProductName = "100% 선주직송 프리미엄 박달홍게 강원도 속초 홍게 자숙홍게 저도 어장 발송",
                coupangProductPrice = 52100,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "조개",
                ingredientType = IngredientType.SEAFOOD,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/4556771875?itemId=5541880297&vendorItemId=78489227171&sourceType=srp_product_ads&clickEventId=b3243580-4975-11ee-92a6-37183fac9494&korePlacement=15&koreSubPlacement=1&clickEventId=b3243580-4975-11ee-92a6-37183fac9494&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/04c2/f22ef443d598fd151f197bf66e46f48a079ce681d21e80605229b0455be7.jpg",
                coupangProductName = "자연산 백생합 10kg",
                coupangProductPrice = 44000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "우유",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/7035294855?itemId=17377826409&vendorItemId=84547553149&sourceType=srp_product_ads&clickEventId=b3f87570-4975-11ee-912c-21472f1273c3&korePlacement=15&koreSubPlacement=1&clickEventId=b3f87570-4975-11ee-912c-21472f1273c3&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/b612/29deed369f0448d03788fd31c3ee558ac3bd91ca975e32f18b296c78f302.jpg",
                coupangProductName = "[웰굿] 강훈목장 오리지날 목장우유 1000ml x 2",
                coupangProductPrice = 9860,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "저지방 우유",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/7232503029?itemId=383114177&vendorItemId=3930089870&pickType=COU_PICK",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/10/31/3930089870/6873370f-8dbb-4bff-a402-bf5bf89c40d5.jpg",
                coupangProductName = "서울우유 저지방우유",
                coupangProductPrice = 6620,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "계란",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.PIECE,
                coupangUrl = "https://www.coupang.com/vp/products/7464735307?itemId=19465005945&vendorItemId=78233728695&sourceType=srp_product_ads&clickEventId=b53eff30-4975-11ee-a502-f7b935a1bfd4&korePlacement=15&koreSubPlacement=1&clickEventId=b53eff30-4975-11ee-a502-f7b935a1bfd4&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/bcf1/debda2b012d0c40715cca60d9432b89268fb119252bcfa62027fd2388cab.jpg",
                coupangProductName = "[당일 생산] 닥터안스에그 무항생제 인증 수의사 계란",
                coupangProductPrice = 22000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "그리스 요거트",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/6509674101?itemId=19079271413&vendorItemId=84982668900&sourceType=srp_product_ads&clickEventId=b5ebb9f0-4975-11ee-b003-e43b2606f8ea&korePlacement=15&koreSubPlacement=1&clickEventId=b5ebb9f0-4975-11ee-b003-e43b2606f8ea&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/c659/c201a71cc1e55cbf1dabc8d1beb75d3334f6bd331fc5786cda01df0627fe.jpg",
                coupangProductName = "후디스 그릭요거트 달지않은 저지방 450g 3개",
                coupangProductPrice = 17700,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "프로바이오틱스 요거트",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/193581870?itemId=554217448&vendorItemId=4456339702",
                coupangProductImage = "https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/3118227770780919-2772330c-92b1-4842-ad37-d80079c1d7e9.jpg",
                coupangProductName = "남양 한번에 1000억 프로바이오틱스 사과",
                coupangProductPrice = 3480,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "체다 치즈",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/6870753790?itemId=16435147847&vendorItemId=83625908503&sourceType=srp_product_ads&clickEventId=b74863c0-4975-11ee-b03d-6f3f9aac7360&korePlacement=15&koreSubPlacement=1&clickEventId=b74863c0-4975-11ee-b03d-6f3f9aac7360&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/075d/a247e69b423eab5243188ba6cad4f035e6b14b76d1141949b2b819b1a4d3.jpg",
                coupangProductName = "[데어리랜드] 오렌지색 체다치즈 파우더 (체다치즈분말)",
                coupangProductPrice = 33000,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "모짜렐라 치즈",
                ingredientType = IngredientType.DAIRY,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/7006234958?itemId=17201265327&vendorItemId=84836099043&sourceType=srp_product_ads&clickEventId=b7eda470-4975-11ee-b4ab-79a9dc109737&korePlacement=15&koreSubPlacement=1&clickEventId=b7eda470-4975-11ee-b4ab-79a9dc109737&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/7d5d/e2de998ba7a2d3ea3c6ac01d09bbb7382301e667f6d591bb876e45ad5fcd.png",
                coupangProductName = "[인생건어물]코다노 모짜렐라치즈 DCM-F 1KG",
                coupangProductPrice = 11500,
                isRocketDelivery = true
            ),

            Ingredient(
                name = "소금",
                ingredientType = IngredientType.SAUCE,
                ingredientUnitEnum = UnitEnum.T,
                coupangUrl = "https://www.coupang.com/vp/products/7403119835?itemId=19165549102&vendorItemId=70092394656&sourceType=srp_product_ads&clickEventId=b8ac3980-4975-11ee-bea8-b4a86be8bc14&korePlacement=15&koreSubPlacement=1&clickEventId=b8ac3980-4975-11ee-bea8-b4a86be8bc14&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/191e/b8d5f3b7ddb1efaf030f9352108bce21687548f5d04973294fe0356151bd.png",
                coupangProductName = "자연담아 건강소금4종 선물셋트 웰빙소금 4종셋트",
                coupangProductPrice = 35000,
                isRocketDelivery = true
            ),

            Ingredient(
                name = "간장",
                ingredientType = IngredientType.SAUCE,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/6389043225?itemId=13610295710&vendorItemId=5054959833&sourceType=srp_product_ads&clickEventId=b958a620-4975-11ee-b9e4-9c02b4f9c70d&korePlacement=15&koreSubPlacement=1&clickEventId=b958a620-4975-11ee-b9e4-9c02b4f9c70d&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/bbcb/9112c9d88af329cb78c9a75a68aecacf48952927c483dcfd9198421d4e65.jpg",
                coupangProductName = "홍일식품 홍게간장 1.8리터2개+맛장700미리",
                coupangProductPrice = 29380,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "참기름",
                ingredientType = IngredientType.SAUCE,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/7370879808?itemId=19013266049&vendorItemId=79719899666&sourceType=srp_product_ads&clickEventId=ba014230-4975-11ee-b194-61fc5c9968af&korePlacement=15&koreSubPlacement=1&clickEventId=ba014230-4975-11ee-b194-61fc5c9968af&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/4418322466850141-a6bf0fe5-8548-4f3b-b175-bd2eca980f61.jpg",
                coupangProductName = "손손 참기름",
                coupangProductPrice = 9980,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "식용유",
                ingredientType = IngredientType.OIL,
                ingredientUnitEnum = UnitEnum.ML,
                coupangUrl = "https://www.coupang.com/vp/products/6680558424?itemId=15403790368&vendorItemId=82623789702&sourceType=srp_product_ads&clickEventId=bab5c520-4975-11ee-95ba-a9af200335a8&korePlacement=15&koreSubPlacement=1&clickEventId=bab5c520-4975-11ee-95ba-a9af200335a8&korePlacement=15&koreSubPlacement=1",
                coupangProductImage = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/7e03/a05e9badf865e55c296900732aee957db58acdc952c4a8f7d3f510e13df9.jpg",
                coupangProductName = "대두유 18L 콩기름100% 식용유 업소용 대용량",
                coupangProductPrice = 48910,
                isRocketDelivery = true
            ),
            Ingredient(
                name = "밀가루",
                ingredientType = IngredientType.POWDER,
                ingredientUnitEnum = UnitEnum.G,
                coupangUrl = "https://www.coupang.com/vp/products/5479650?itemId=438916638&vendorItemId=4085701597",
                coupangProductImage = "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2019/06/27/4085701597/512b515d-f132-4658-a7a0-7b278765dabb.jpg",
                coupangProductName = "곰표 밀가루",
                coupangProductPrice = 3320,
                isRocketDelivery = true
            ),

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

        var shortFormRecipe2 = ShortFormRecipe(
            name = "맛있는 레시피 Plate에 다 있다!",
            description = "맛있는 레시피 Plate에 다 있다! 9월 2일 대개봉.",
            video_url = "https://d1jg55wkcrciwu.cloudfront.net/videos/test2.mp4",
            writtenBy = member2,
            video_time = "00:25"
        )
        shortFormRecipe2 = shortFormRecipeRepository.save(shortFormRecipe2)


        val shortFormIngredients1 = mutableListOf(
            ShortFormIngredients(shortFormRecipe = shortFormRecipe1, ingredient = ingredients[0]),
            ShortFormIngredients(shortFormRecipe = shortFormRecipe1, ingredient = ingredients[1]),
            ShortFormIngredients(shortFormRecipe = shortFormRecipe1, ingredient = ingredients[2])
        )
        shortFormIngredientsRepository.saveAll(shortFormIngredients1)

        for (shorformIngredient in shortFormIngredients1) {
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