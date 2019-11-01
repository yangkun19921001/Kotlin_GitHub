
import com.devyk.common.App
import com.devyk.common.config.UserInfo
import com.devyk.common.ext.pref
import com.devyk.kotlin_github.R


object Settings {
    var lastPage: Int
        get() = if(lastPageIdString.isEmpty()) 0 else App.getInstance().resources.getIdentifier(lastPageIdString, "id", App.getInstance().packageName)
        set(value) {
            lastPageIdString = App.getInstance().resources.getResourceEntryName(value)
        }

    val defaultPage
        get() = if(UserInfo.isLoginIn()) defaultPageForUser else defaultPageForVisitor

    private var defaultPageForUser by pref(R.id.navRepos)

    private var defaultPageForVisitor by pref(R.id.navRepos)

    private var lastPageIdString by pref("")

    var themeMode by pref("DAY")
    
    

}