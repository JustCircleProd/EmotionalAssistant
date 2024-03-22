package com.example.bd.core.domain.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class User : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var name: String = ""
    var age: Int = 0
    var gender: Gender
        get() {
            return Gender.valueOf(genderDescription)
        }
        set(value) {
            genderDescription = value.name
        }
    private var genderDescription: String = Gender.MALE.name
    var emotions: RealmList<Emotion> = realmListOf()
    var emotionalStateTestResults: RealmList<EmotionalStateTestResult> = realmListOf()
}