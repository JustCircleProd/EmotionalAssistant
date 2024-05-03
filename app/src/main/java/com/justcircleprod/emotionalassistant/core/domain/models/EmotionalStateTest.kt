package com.justcircleprod.emotionalassistant.core.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class EmotionalStateTest : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()

    var emotionalStateName: EmotionalStateName
        get() {
            return EmotionalStateName.valueOf(nameDescription)
        }
        set(value) {
            nameDescription = value.name
        }
    private var nameDescription: String = EmotionalStateName.DEPRESSION.name

    var questionsResourceEntryName: String = ""
    var numberOfQuestions: Int = 0
    var goalScore: Int = 0
}