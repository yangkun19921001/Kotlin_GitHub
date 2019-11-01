
import com.bennyhuo.common.log.logger
import com.devyk.common.App
import com.devyk.common.ext.deviceId

object Configs {

    object Account{
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        const val clientId = "978939bf3ceadc720e5e"
        const val clientSecret = "4aa3d83510fb20800e97c55144ccc451f5eaba90"
        const val note = "kotlin"
        const val noteUrl = "https://github.com/yangkun19921001"

        val fingerPrint by lazy {
            (App.getInstance().deviceId + clientId).also { logger.info("fingerPrint: "+it) }
        }
    }

}