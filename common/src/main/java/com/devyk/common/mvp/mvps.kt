
interface IPresenter<out View : IMvpView<IPresenter<View>>> : ILifecycle {
    val v: View
}

interface IMvpView<out Presenter : IPresenter<IMvpView<Presenter>>> : ILifecycle {
    val p: Presenter

    fun onRequest()

    fun onSuccess()

    fun onError(error: Throwable)
}