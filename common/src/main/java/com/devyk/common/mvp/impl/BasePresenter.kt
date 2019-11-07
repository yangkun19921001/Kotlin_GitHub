
import android.content.res.Configuration
import android.os.Bundle

abstract class BasePresenter<out V: IMvpView<BasePresenter<V>>>: IPresenter<V> {

    override lateinit var v: @UnsafeVariance V

    override fun onCreate(savedInstanceState: Bundle?) = Unit
    override fun onSaveInstanceState(outState: Bundle) = Unit
    override fun onViewStateRestored(savedInstanceState: Bundle?) = Unit
    override fun onConfigurationChanged(newConfig: Configuration) = Unit
    override fun onDestroy() = Unit
    override fun onStart() = Unit
    override fun onStop() = Unit
    override fun onResume() = Unit
    override fun onPause() = Unit


    protected val TAG = javaClass.simpleName
}