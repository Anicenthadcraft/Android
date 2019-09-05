package com.ancient.ancient_handcraft.base
import io.reactivex.disposables.Disposable


interface BaseView<T> {

    fun setPresenter(presenter: T)
    fun showLoader()
    fun hideLoader()
    fun addDisposable(disposable: Disposable)

}