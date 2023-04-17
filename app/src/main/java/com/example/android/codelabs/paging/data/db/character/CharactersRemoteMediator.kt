package com.example.android.codelabs.paging.data.db.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android.codelabs.paging.api.RickAndMortyService
import com.example.android.codelabs.paging.data.db.LocationsRemoteKeys
import com.example.android.codelabs.paging.model.character.CharacterEntity
import com.example.android.codelabs.paging.model.character.mapToEntity
import com.example.android.codelabs.paging.model.location.LocationEntity
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val query: String,
    private val service: RickAndMortyService,
    private val charactersDatabase: CharactersDatabase
) : RemoteMediator<Int, CharacterEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val apiQuery = query

        try {
            val apiResponse = service.getCharacters(
                name = apiQuery,
                gender = "",
                status = "",
                pageNumber = pageIndex,
            )

            val locations = apiResponse.results
            val endOfPaginationReached = apiResponse.info.pages == pageIndex
            charactersDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    charactersDatabase.locationsRemoteKeysDao().clearRemoteKeys()
                    charactersDatabase.characterDao().clearCharacters()
                }
                val prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1
                val nextKey = if (endOfPaginationReached) null else pageIndex + 1
                val keys = locations.map {
                    LocationsRemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                charactersDatabase.locationsRemoteKeysDao().insertAll(keys)
                charactersDatabase.characterDao().insertAll(locations.map { it.mapToEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocationEntity>): LocationsRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { loc ->
                // Get the remote keys of the last item retrieved
                charactersDatabase.locationsRemoteKeysDao().remoteKeysLocId(loc.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, LocationEntity>): LocationsRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { loc ->
                // Get the remote keys of the first items retrieved
                charactersDatabase.locationsRemoteKeysDao().remoteKeysLocId(loc.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocationEntity>
    ): LocationsRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { locId ->
                charactersDatabase.locationsRemoteKeysDao().remoteKeysLocId(locId)
            }
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when(loadType){
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }
}