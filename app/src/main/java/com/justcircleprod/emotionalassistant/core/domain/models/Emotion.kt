package com.justcircleprod.emotionalassistant.core.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime

class Emotion : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()

    var name: EmotionName
        get() {
            return EmotionName.valueOf(nameDescription)
        }
        set(value) {
            nameDescription = value.name
        }
    private var nameDescription: String = EmotionName.NEUTRAL.name

    var dateTime: LocalDateTime
        get() {
            return LocalDateTime.parse(dateDescription)
        }
        set(value) {
            dateDescription = value.toString()
        }
    private var dateDescription: String = LocalDateTime.now().toString()

    var note: String? = null
    var imageFileName: String? = null
}