package com.justcircleprod.emotionalassistant.core.domain.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class User : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var emotions: RealmList<Emotion> = realmListOf()
    var emotionalStateTestResults: RealmList<EmotionalStateTestResult> = realmListOf()
}