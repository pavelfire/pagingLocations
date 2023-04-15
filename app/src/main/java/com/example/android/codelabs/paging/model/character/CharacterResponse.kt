package com.example.android.codelabs.paging.model.character

data class CharacterResponse(
    val info: Info,
    val results: List<CharacterDto>
)