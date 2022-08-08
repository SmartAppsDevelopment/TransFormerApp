package com.example.sofittasktranformer.utils

import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * @author Umer Bilal
 * Created 7/18/2022 at 7:23 PM
 */
object ConstantsMembers {
    var DATABASE_NAME = "TransformerDb"
    var BASE_URL = "https://transformers-api.firebaseapp.com/"
    var DEFAULT_TOKEN="Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0cmFuc2Zvcm1lcnNJZCI6Ii1OOGhyNVpBU0l1X0t2WlprNEVWIiwiaWF0IjoxNjU5NzAwNTM3fQ.2bi4VwMrVdRKP60sjuwteEbXY-7YWIWliFfeFVh3CGY"


    object SharedPrefKeys {
        val BEARER_TOKEN = stringPreferencesKey("BearerToken")
    }

}