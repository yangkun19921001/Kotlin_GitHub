package com.devyk.kotlin_github.mvp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.devyk.kotlin_github.mvp.v.MainActivity

/**
 * <pre>
 *     author  : devyk on 2019-11-06 13:28
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CommonSinglePageFragment
 * </pre>
 */

abstract class CommonSinglePageFragment:Fragment(){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionBarController.setupWithViewPager(null)
    }
}