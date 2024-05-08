package com.justcircleprod.emotionalassistant.core.presentation.compontents

import com.google.gson.GsonBuilder
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateName
import org.mongodb.kbson.ObjectId
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate

enum class Screen {
    WELCOME,
    REGISTRATION,
    HOME,
    HISTORY,
    EMOTION_RECOGNITION_METHOD_SELECTION,
    EMOTION_RECOGNITION_BY_PHOTO,
    EMOTION_SELECTION_FROM_LIST,
    EMOTION_ADDITIONAL_INFO,
    EMOTION_RECOMMENDATION,
    EMOTION_DETAIL,
    EMOTIONAL_STATE_TEST,
    EMOTIONAL_STATE_TEST_RESULT,
    EMOTIONAL_STATE_RECOMMENDATION
}

sealed class NavigationItem(val route: String) {
    data object Welcome : NavigationItem(Screen.WELCOME.name)

    data object Registration : NavigationItem(Screen.REGISTRATION.name)

    data object Home : NavigationItem(Screen.HOME.name)

    data object History : NavigationItem(Screen.HISTORY.name)

    /**
     * Date to add emotion for the specific date.
     * emotionId for changing a specific emotion.
     */
    data object EmotionRecognitionMethodSelection : NavigationItem(
        "${Screen.EMOTION_RECOGNITION_METHOD_SELECTION.name}/" +
                "{returnRoute}?" +
                "date={date}&" +
                "emotionIdToUpdate={emotionIdToUpdate}"
    ) {
        const val RETURN_ROUTE_ARGUMENT_NAME = "returnRoute"
        const val DATE_ARGUMENT_NAME = "date"
        const val EMOTION_ID_TO_UPDATE_ARGUMENT_NAME = "emotionIdToUpdate"

        fun getRouteWithArguments(
            returnRoute: String,
            date: LocalDate? = null,
            emotionId: ObjectId? = null
        ): String {
            var routeWithArguments = Screen.EMOTION_RECOGNITION_METHOD_SELECTION.name

            routeWithArguments += "/${
                URLEncoder.encode(
                    returnRoute,
                    StandardCharsets.UTF_8.toString()
                )
            }"

            if (date != null) {
                routeWithArguments += "?$DATE_ARGUMENT_NAME=$date"
            }

            if (emotionId != null) {
                routeWithArguments += if (date != null) "&" else "?"

                val emotionIdJson = with(GsonBuilder().create()) {
                    toJson(emotionId)
                }

                routeWithArguments += "$EMOTION_ID_TO_UPDATE_ARGUMENT_NAME=$emotionIdJson"
            }

            return routeWithArguments
        }
    }

    data object EmotionRecognitionByPhoto : NavigationItem(
        "${Screen.EMOTION_RECOGNITION_BY_PHOTO.name}/{returnRoute}"
    ) {
        const val RETURN_ROUTE_ARGUMENT_NAME = "returnRoute"

        fun getRouteWithArguments(returnRoute: String): String {
            return "${Screen.EMOTION_RECOGNITION_BY_PHOTO.name}/" +
                    URLEncoder.encode(returnRoute, StandardCharsets.UTF_8.toString())
        }
    }

    data object EmotionSelectionFromList : NavigationItem(
        "${Screen.EMOTION_SELECTION_FROM_LIST.name}/{returnRoute}"
    ) {
        const val RETURN_ROUTE_ARGUMENT_NAME = "returnRoute"

        fun getRouteWithArguments(returnRoute: String): String {
            return "${Screen.EMOTION_SELECTION_FROM_LIST.name}/" +
                    URLEncoder.encode(returnRoute, StandardCharsets.UTF_8.toString())
        }
    }

    data object EmotionAdditionalInfo : NavigationItem(
        "${Screen.EMOTION_ADDITIONAL_INFO.name}/{returnRoute}/{emotionId}"
    ) {
        const val RETURN_ROUTE_ARGUMENT_NAME = "returnRoute"
        const val EMOTION_ID_ARGUMENT_NAME = "emotionId"

        fun getRouteWithArguments(returnRoute: String, emotionId: ObjectId): String {
            var routeWithArguments = Screen.EMOTION_ADDITIONAL_INFO.name

            routeWithArguments += "/${
                URLEncoder.encode(
                    returnRoute,
                    StandardCharsets.UTF_8.toString()
                )
            }"

            val emotionIdJson = with(GsonBuilder().create()) {
                toJson(emotionId)
            }

            routeWithArguments += "/$emotionIdJson"

            return routeWithArguments
        }
    }

    data object EmotionRecommendation : NavigationItem(
        "${Screen.EMOTION_RECOMMENDATION.name}/{emotionId}"
    ) {
        const val EMOTION_ID_ARGUMENT_NAME = "emotionId"

        fun getRouteWithArguments(emotionId: ObjectId): String {
            val emotionIdJson = with(GsonBuilder().create()) {
                toJson(emotionId)
            }

            return "${Screen.EMOTION_RECOMMENDATION.name}/$emotionIdJson"
        }
    }

    data object EmotionDetail : NavigationItem(
        "${Screen.EMOTION_DETAIL.name}/{emotionId}/{inEditMode}"
    ) {
        const val EMOTION_ID_ARGUMENT_NAME = "emotionId"
        const val IN_EDIT_MODE_ARGUMENT_NAME = "inEditMode"

        fun getRouteWithArguments(emotionId: ObjectId, inEditMode: Boolean = false): String {
            val emotionIdJson = with(GsonBuilder().create()) {
                toJson(emotionId)
            }

            return "${Screen.EMOTION_DETAIL.name}/$emotionIdJson/$inEditMode"
        }
    }

    data object EmotionalStateTest : NavigationItem(
        "${Screen.EMOTIONAL_STATE_TEST.name}/{returnRoute}"
    ) {
        const val RETURN_ROUTE_ARGUMENT_NAME = "returnRoute"

        fun getRouteWithArguments(returnRoute: String): String {
            return "${Screen.EMOTIONAL_STATE_TEST.name}/" +
                    URLEncoder.encode(returnRoute, StandardCharsets.UTF_8.toString())
        }
    }

    data object EmotionalStateTestResult : NavigationItem(
        "${Screen.EMOTIONAL_STATE_TEST_RESULT.name}/{date}"
    ) {
        const val DATE_ARGUMENT_NAME = "date"

        fun getRouteWithArguments(date: LocalDate): String {
            return "${Screen.EMOTIONAL_STATE_TEST_RESULT.name}/$date"
        }
    }

    data object EmotionalStateRecommendation : NavigationItem(
        "${Screen.EMOTIONAL_STATE_RECOMMENDATION.name}/{emotionalStateName}"
    ) {
        const val EMOTIONAL_STATE_NAME_ARGUMENT_NAME = "emotionalStateName"

        fun getRouteWithArguments(emotionalStateName: EmotionalStateName): String {
            return "${Screen.EMOTIONAL_STATE_RECOMMENDATION.name}/${emotionalStateName.name}"
        }
    }
}