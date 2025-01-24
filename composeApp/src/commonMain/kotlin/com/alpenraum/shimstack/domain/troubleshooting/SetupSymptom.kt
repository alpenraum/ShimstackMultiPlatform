package com.alpenraum.shimstack.domain.troubleshooting

enum class SetupSymptom {
    UNDERSTEER, // front rear tire balance - reduce front tire pressure IF NOT TOO LOW
    // - if not better through pressure then bring more weight to front by lowering handlebar

    OVERSTEER, // front rear tire balance - reduce rear tire pressure IF NOT TOO LOW

    MUSH, // not enough pressure

    HARSH_OVER_SMALL_BUMPS, // too much pressure

    BRAKE_DIVE, // not enough front pressure

    STEEP_DIVE, // not enough front pressure

    FREQUENT_BOTTOM_OUT, // IF pressure is good then more tokens else less pressure

    RARE_FULL_TRAVEL, // IF pressure is good then less tokens else less pressure

    FRONT_FLIP_ON_TAKE_OFF, // Imbalance between F and R rebound, slow down rear rebound or speed up front

    BIKE_PACKING_DOWN, // rebound faster

   BIKE_FALLS_DEEP_INTO_TRAVEL // more compression
}

enum class SetupAnalysisResult {
    OK,
    TOO_LOW,
    TOO_HIGH
}