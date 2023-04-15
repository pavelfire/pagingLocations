package com.example.android.codelabs.paging.model.character

import androidx.room.Entity

const val CHARACTERS_TABLE_NAME = "characters"

@Entity(tableName = CHARACTERS_TABLE_NAME)
data class CharacterEntity(
    val created: String,
    val episode: String,
    val gender: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val origin: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

fun CharacterEntity.mapToDto(): CharacterDto {
    return CharacterDto(
        created = created,
        episode = episode.lines(),
        gender = gender,
        id = id,
        image = image,
        location = Location(location, ""),
        name = name,
        origin = Origin(origin, ""),
        species = species,
        status = status,
        type = type,
        url = url
    )
}
