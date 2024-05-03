package com.justcircleprod.emotionalassistant.core.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

class EmotionalStateTestResult : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var emotionalStateTest: EmotionalStateTest? = null
    var score: Int = 0

    var date: LocalDate
        get() {
            return LocalDate.parse(dateDescription)
        }
        set(value) {
            dateDescription = value.toString()
        }
    private var dateDescription: String = LocalDate.now().toString()
}