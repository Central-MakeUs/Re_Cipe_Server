package com.re_cipe.ingredient.domain

enum class UnitEnum {
    // 부피 단위
    ML,
    L,
    T, // teaspoon

    // 무게 단위
    G,
    KG,
    OZ,

    // 개수 단위
    PIECE,
    PACK,
    BUNCH,
    CUP,
    SPOON;

    companion object {
        fun stringToEnum(unit: String): UnitEnum {
            return when (unit) {
                "ML" -> ML
                "L" -> L
                "T" -> T
                "G" -> G
                "KG" -> KG
                "OZ" -> OZ
                "PIECE" -> PIECE
                "PACK" -> PACK
                "BUNCH" -> BUNCH
                "CUP" -> CUP
                "SPOON" -> SPOON
                else -> throw IllegalArgumentException("Invalid unit: $unit")
            }
        }
    }
}
