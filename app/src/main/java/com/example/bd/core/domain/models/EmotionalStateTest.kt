package com.example.bd.core.domain.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class EmotionalStateTest : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()

    var name: EmotionalStateName
        get() {
            return EmotionalStateName.valueOf(nameDescription)
        }
        set(value) {
            nameDescription = value.name
        }
    private var nameDescription: String = EmotionalStateName.DEPRESSION.name

    var goalScore: Int = 0
    var questions: RealmList<EmotionalStateTestQuestion> = realmListOf()
}