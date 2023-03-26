package com.example.android.codelabs.paging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android.codelabs.paging.api.RickAndMortyService
import com.example.android.codelabs.paging.data.db.LocationsDatabase
import com.example.android.codelabs.paging.data.db.LocationsRemoteKeys
import com.example.android.codelabs.paging.model.location.LocationEntity
import com.example.android.codelabs.paging.model.location.mapToEntity
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class LocationsRemoteMediator(
    private val query: String,
    private val service: RickAndMortyService,
    private val locationsDatabase: LocationsDatabase
) : RemoteMediator<Int, LocationEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                // If remoteKeys is null, that means the refresh result is not in the database yet.
//                val prevKey = remoteKeys?.prevKey
//                if (prevKey == null) {
//                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                }
//                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }
        val apiQuery = query //+ IN_QUALIFIER

        try {
            val apiResponse = service.searchLocations(
                name = apiQuery,
                type = "",
                dimension = "",
                page = page,
            )
            //.searchRepos(apiQuery, page, state.config.pageSize)

            val locations = apiResponse.results
            val endOfPaginationReached = locations.isEmpty()
            locationsDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    locationsDatabase.locationsRemoteKeysDao().clearRemoteKeys()
                    locationsDatabase.locationDao().clearLocations()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = locations.map {
                    LocationsRemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                locationsDatabase.locationsRemoteKeysDao().insertAll(keys)
                locationsDatabase.locationDao().insertAll(locations.map { it.mapToEntity(it) })
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
                locationsDatabase.locationsRemoteKeysDao().remoteKeysLocId(loc.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, LocationEntity>): LocationsRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { loc ->
                // Get the remote keys of the first items retrieved
                locationsDatabase.locationsRemoteKeysDao().remoteKeysLocId(loc.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocationEntity>
    ): LocationsRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { locId ->
                locationsDatabase.locationsRemoteKeysDao().remoteKeysLocId(locId)
            }
        }
    }
}