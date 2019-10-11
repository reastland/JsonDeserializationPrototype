package com.example.jsondeserializationprototype.immutablesmodels

import org.immutables.gson.Gson
import org.immutables.value.Value

@Gson.TypeAdapters
@Value.Immutable
data class ImmutablesGameHighlightResponse(
    val highlights: Highlights
) {
    @Value.Immutable
    data class Highlights(val highlights: Highlight?, val live: Highlight?, val scoreboardPreview: Highlight?)
    @Value.Immutable
    data class Highlight(val items: List<HighlightItem>?)
    @Value.Immutable
    data class HighlightItem(
        val type: String, //video
        val state: String, //A
        val date: String, //"2018-09-14T19:05:00-0400"
        val id: String, //"2488480583",
        val topicId: String, //
        val noIndex: Boolean, //false
        val mediaPlaybackId: String, //"2488480583"
        val title: String, //"CG: CWS@BAL - 9/14/18"
        val headline: String, //"CG: CWS@BAL - 9/14/18"
        val kicker: String, //"CG: CWS@BAL - 9/14/18"
        val blurb: String, //"Condensed Game: CWS@BAL - 9/14/18",
        val description: String, //"Condensed Game: CWS@BAL - 9/14/18",
        val slug: String, //"cg-cwsbal-91418",
        val seoTitle: String,
        val authFlow: Boolean,
        val duration: String,
        val language: String,
        val mediaState: String, //MEDIA_ARCHIVE
        val guid: String, //"3776b991-10f5-49c6-b35d-43c78777f50e"
        val image: VideoImage,
        val keywordsDisplay: List<Keywords>?,
        val mediaPlaybackUrl: String,
        val playbacks: List<Playback>?
    )

    @Value.Immutable
    data class VideoImage(
        val title: String?,
        val altText: String?,
        val cuts: List<ImageCuts>?
    )

    /**
     * This might be somewhat generic, at the very least it is used for images related to videos
     */
    @Value.Immutable
    data class ImageCuts(
        val aspectRatio: String,    //"16:9"
        val width: Int,             //167
        val height: Int,            //94
        val src: String,            //"https://mediadownloads.mlb.com/mlbam/2018/09/15/images/mlbf_2488480583_th_1.jpg"
        val at2x: String,           //"https://mediadownloads.mlb.com/mlbam/2018/09/15/images/mlbf_2488480583_th_1.jpg",
        val at3x: String            //"https://mediadownloads.mlb.com/mlbam/2018/09/15/images/mlbf_2488480583_th_1.jpg",
    )

    @Value.Immutable
    data class Keywords(
        val type: String, //team_id, game_pk, mmtax, mlbtax, event_date, away_team_id,
        val value: String,
        val displayName: String
    )

    @Value.Immutable
    data class Playback(
        val name: String, //HTTP_CLOUD_TABLET
        val width: String,
        val height: String,
        val url: String //"https://mediadownloads.mlb.com/mlbam/hls/2018/09/15/2488457483/1536985822307/master_mobile.m3u8"
    )
}
