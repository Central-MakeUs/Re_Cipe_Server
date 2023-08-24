package com.re_cipe.ingredient.ui

import com.re_cipe.global.response.ApiResponse
import com.re_cipe.ingredient.service.IngredientService
import com.re_cipe.ingredient.ui.dto.IngredientRequest
import com.re_cipe.ingredient.ui.dto.IngredientSimpleResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/ingredients")
@RestController
@Api(tags = ["ingredient api"], description = "Re:cipe API")
class IngredientController(
    val ingredientService: IngredientService
) {
    @ApiOperation(value = "재료 목록을 조회한다.")
    @SecurityRequirement(name = "Authorization")
    @GetMapping
    fun getIngredientLists(): ApiResponse<List<IngredientSimpleResponse>> {
        return ApiResponse.success(ingredientService.findAllIngredients())
    }

    @ApiOperation(value = "재료를 생성한다.")
    @SecurityRequirement(name = "Authorization")
    @PostMapping
    fun createIngredient(@RequestBody ingredientRequest: IngredientRequest): ApiResponse<Long> {
        return ApiResponse.success(ingredientService.createIngredient(ingredientRequest))
    }
}