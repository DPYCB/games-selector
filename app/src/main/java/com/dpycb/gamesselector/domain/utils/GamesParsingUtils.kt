package com.dpycb.gamesselector.domain

import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.google.protobuf.Timestamp
import proto.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

fun parseReleaseDate(date: Timestamp): String = SimpleDateFormat("dd.MM.yyyy")
    .format(Date(date.seconds * 1000))

fun parsePlatforms(platforms: List<Platform>): String {
    var result = ""
    platforms.forEachIndexed { index, platform ->
        result += when (platforms.lastIndex == index) {
            true -> platform.name
            else -> "${platform.name}, "
        }
    }
    return result
}

fun parseImageRef(rawGame: Game) = when {
    rawGame.hasCover() -> imageBuilder(rawGame.cover.imageId, ImageSize.COVER_BIG, ImageType.WEBP)
    else -> ""
}

fun parseGenre(genres: List<Genre>) = try {
    genres[0].name ?: ""
}
catch (th: Throwable) {
    ""
}

fun parseScreenshots(screenshots: List<Screenshot>) = screenshots
    .map { imageBuilder(it.imageId, ImageSize.SCREENSHOT_MEDIUM, ImageType.WEBP) }

fun parseVideoUrl(videos: List<GameVideo>): String = try {
    videos[0].videoId
}
catch (th: Throwable) {
    ""
}

fun parseGameIdsToRequestString(gameIds: List<Long>): String {
    var requestString = "("
    gameIds.forEachIndexed{ index, gameId ->
        requestString += when (index) {
            gameIds.lastIndex -> "$gameId)"
            else -> "$gameId, "
        }
    }
    return requestString
}