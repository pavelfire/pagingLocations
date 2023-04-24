package com.example.android.codelabs.paging.model.character

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

const val CHARACTERS_TABLE_NAME = "characters"

@Parcelize
@Entity(tableName = CHARACTERS_TABLE_NAME)
data class CharacterEntity(
    val created: String,
    val episode: String,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val origin: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
): Parcelable

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


