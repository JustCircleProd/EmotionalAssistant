package com.example.bd.core.domain.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList

class EmotionalStateTestQuestion : EmbeddedRealmObject {
    var question: String = ""
    var options: RealmList<String> = realmListOf()
    var points: Int = 0
}