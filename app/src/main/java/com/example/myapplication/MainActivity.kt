package com.example.myapplication

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var list: ArrayList<UserBean>
    var isSlidingToLast: Boolean = false
    val TAG = "MainActivity"
    var loginViewModel: LoginViewModel? = null
    lateinit var binding: ActivityMainBinding
    lateinit var testAdapter: TestAdapter

    //    var loginViewModel:LoginViewModel by
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        testAdapter = TestAdapter(this)

        // 绑定Adapter
        binding!!.ad = testAdapter

        loginViewModel = LoginViewModel()
        val testClick = TestClick()
        binding?.lifecycleOwner = this
        binding?.loginClick = testClick
        binding?.loginModel = loginViewModel

        binding.rvList.setHasFixedSize(true)
        binding.rvList.isNestedScrollingEnabled = false


//        binding?.clickHH = ClickTest()
        loginViewModel?.name?.observe(this, {
            Log.d(TAG, "name = $it")
        })

        loginViewModel?.desc?.observe(this, {
            Log.d(TAG, "desc = $it")
        })

        list = ArrayList<UserBean>()
        val u1 = UserBean("张三1")
        val u2 = UserBean("张三2")
        val u3 = UserBean("张三3")
        val u4 = UserBean("张三4")
        val u5 = UserBean("张三5")
        val u6 = UserBean("张三6")
        val u7 = UserBean("张三7")
        val u8 = UserBean("张三8")
        val u9 = UserBean("张三9")

        list.add(u1)
        list.add(u2)
        list.add(u3)
        list.add(u4)
        list.add(u5)
        list.add(u6)
        list.add(u7)
        list.add(u8)
        list.add(u9)

        testAdapter.setDataUpdate(list)

        binding.rvList.addOnScrollListener(CusS())
        binding.rvList.setOnScrollChangeListener(cusCh())

        /**
         * Log.d(TAG, "v = $v")
        Log.d(TAG, "scrollX = $scrollX")
        Log.d(TAG, "scrollY = $scrollY")
        Log.d(TAG, "oldScrollX = $oldScrollX")
        Log.d(TAG, "oldScrollY = $oldScrollY")
         */
    }

    @RequiresApi(Build.VERSION_CODES.M)
    inner class cusCh : View.OnScrollChangeListener {
        override fun onScrollChange(
            v: View?,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
        ) {
//            Log.d(TAG, "scrollX = $scrollX")
//            Log.d(TAG, "scrollY = $scrollY")
//            Log.d(TAG, "oldScrollX = $oldScrollX")
//            Log.d(TAG, "oldScrollY = $oldScrollY")
        }

    }

    inner class CusS : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
//            Log.d(TAG, "recyclerView = $recyclerView, newState = $newState")
            Log.d(TAG, "newState = $newState")
//            val manager = CusLayoutmanager(recyclerView.context) as LinearLayoutManager?
            val manager = recyclerView.layoutManager as LinearLayoutManager?
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //获取最后一个完全显示的ItemPosition
                val lastCompletelyVisibleItem: Int =
                    manager!!.findLastCompletelyVisibleItemPosition()
                Log.d(TAG, "lastCompletelyVisibleItem = $lastCompletelyVisibleItem")
                val firstVisibleItem = manager!!.findFirstVisibleItemPosition()
                Log.d(TAG, "firstVisibleItem = $firstVisibleItem")
                val lastVisibleItem = manager!!.findLastVisibleItemPosition()
                Log.d(TAG, "lastVisibleItem = $lastVisibleItem")

                val talItemCount = manager.getItemCount();
                Log.d(TAG, "talItemCount = $talItemCount")


                // 判断是否滚动到底部，并且是向右滚动
                if (lastCompletelyVisibleItem == (talItemCount - 1) && isSlidingToLast) {
                    //加载更多功能的代码
                    Log.d(TAG, "滚动到底部，并且是向右滚动")
                    recyclerView.postDelayed({
                        if (testAdapter.itemCount < 30) {
                            list.clear()
                            val u10 = UserBean("张三10")
                            val u11 = UserBean("张三11")
                            val u12 = UserBean("张三12")
                            list.add(u10)
                            list.add(u11)
                            list.add(u12)

                            testAdapter.setDataUpdate(list)
//                        recyclerView.scrollToPosition(talItemCount)
//                            recyclerView.smoothScrollToPosition(talItemCount)
//                        recyclerView.scrollTo(recyclerView.offsetChildrenHorizontal(10), recyclerView.scrollY)
                        } else {
                            testAdapter.updateLoad(LoadMore("没有更多啦", false))
                        }

                    }, 1000)
                }

                if ((lastCompletelyVisibleItem + 1 == lastVisibleItem) && (lastVisibleItem == talItemCount - 1)) {
//                    recyclerView.smoothScrollToPosition(lastCompletelyVisibleItem)
//                    val top: Int = recyclerView.getChildAt(lastCompletelyVisibleItem).getTop()
//                    val left: Int = recyclerView.getChildAt(lastCompletelyVisibleItem).left
//                    recyclerView.smoothScrollBy(left, 0)

                    val childCount = recyclerView.childCount
                    Log.d(TAG, "childCount = $childCount")
                    val left: Int = recyclerView.getChildAt(0).left
                    Log.d(TAG, "childCount left = $left")
                    recyclerView.smoothScrollBy(left, 0)
//                    recyclerView.scrollX
//                    recyclerView.smoothScrollToPosition(lastCompletelyVisibleItem)
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            Log.d(TAG, "recyclerView = $recyclerView, dx = $dx, dy = $dy")
//            Log.d(TAG, "dx = $dx, dy = $dy")
            //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
            if (dx > 0) {
                //大于0表示正在向右滚动
                isSlidingToLast = true;
            } else {
                //小于等于0表示停止或向左滚动
                isSlidingToLast = false;
            }

        }
    }

    inner class CusSY : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
//            Log.d(TAG, "recyclerView = $recyclerView, newState = $newState")
            Log.d(TAG, "newState = $newState")
//            val manager = CusLayoutmanager(recyclerView.context) as LinearLayoutManager?
            val manager = recyclerView.layoutManager as LinearLayoutManager?
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //获取最后一个完全显示的ItemPosition
                val lastCompletelyVisibleItem: Int =
                    manager!!.findLastCompletelyVisibleItemPosition()
                Log.d(TAG, "lastCompletelyVisibleItem = $lastCompletelyVisibleItem")
                val firstVisibleItem = manager!!.findFirstVisibleItemPosition()
                Log.d(TAG, "firstVisibleItem = $firstVisibleItem")
                val lastVisibleItem = manager!!.findLastVisibleItemPosition()
                Log.d(TAG, "lastVisibleItem = $lastVisibleItem")

                val talItemCount = manager.getItemCount();
                Log.d(TAG, "talItemCount = $talItemCount")


                // 判断是否滚动到底部，并且是向右滚动
                if (lastCompletelyVisibleItem == (talItemCount - 1) && isSlidingToLast) {
                    //加载更多功能的代码
                    Log.d(TAG, "滚动到底部，并且是向右滚动")
                    recyclerView.postDelayed({
                        if (testAdapter.itemCount < 30) {
                            list.clear()
                            val u10 = UserBean("张三10")
                            val u11 = UserBean("张三11")
                            val u12 = UserBean("张三12")
                            list.add(u10)
                            list.add(u11)
                            list.add(u12)

                            testAdapter.setDataUpdate(list)
//                        recyclerView.scrollToPosition(talItemCount)
//                            recyclerView.smoothScrollToPosition(talItemCount)
//                        recyclerView.scrollTo(recyclerView.offsetChildrenHorizontal(10), recyclerView.scrollY)
                        } else {
                            testAdapter.updateLoad(LoadMore("没有更多啦", false))
                        }

                    }, 1000)
                }

                if ((lastCompletelyVisibleItem + 1 == lastVisibleItem) && (lastVisibleItem == talItemCount - 1)) {
//                    recyclerView.smoothScrollToPosition(lastCompletelyVisibleItem)
//                    val top: Int = recyclerView.getChildAt(lastCompletelyVisibleItem).getTop()
//                    val left: Int = recyclerView.getChildAt(lastCompletelyVisibleItem).left
//                    recyclerView.smoothScrollBy(left, 0)

                    val childCount = recyclerView.childCount
                    Log.d(TAG, "childCount = $childCount")
                    val top: Int = recyclerView.getChildAt(0).top
                    Log.d(TAG, "childCount top = $top")
                    recyclerView.smoothScrollBy(0, top)
//                    recyclerView.smoothScrollToPosition(lastCompletelyVisibleItem)
//                    recyclerView.smoothScrollToPosition(firstVisibleItem)
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            Log.d(TAG, "recyclerView = $recyclerView, dx = $dx, dy = $dy")
//            Log.d(TAG, "dx = $dx, dy = $dy")
            //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
            if (dy > 0) {
                //大于0表示正在向右滚动
                isSlidingToLast = true;
            } else {
                //小于等于0表示停止或向左滚动
                isSlidingToLast = false;
            }

        }
    }

    inner class CusLayoutmanager(context: Context?) : LinearLayoutManager(context) {
        var context1 = context
        override fun smoothScrollToPosition(
            recyclerView: RecyclerView?,
            state: RecyclerView.State?,
            position: Int
        ) {
//            super.startSmoothScroll(smoothScroller)
            val smoothScroller: LinearSmoothScroller =
                object : LinearSmoothScroller(context1) {
                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                        // 返回：滑过1px时经历的时间(ms)。
                        return 550f / displayMetrics.densityDpi
                    }

                    override fun calculateDtToFit(
                        viewStart: Int,
                        viewEnd: Int,
                        boxStart: Int,
                        boxEnd: Int,
                        snapPreference: Int
                    ): Int {
                        Log.d(
                            TAG,
                            "calculateDtToFit viewStart = $viewStart,viewEnd=$viewEnd,boxStart=$boxStart,boxEnd=$boxEnd,snapPreference=$snapPreference"
                        )
                        return boxStart - viewStart
                    }
                }
            Log.d(TAG, "calculateDtToFit state = $state,position = $position")
            smoothScroller.targetPosition = position
            startSmoothScroll(smoothScroller)
        }
    }

    inner class ClickTest {
        val TAG = "clickTest"
        fun testkt() {
            Log.d(TAG, "testkt")
            loginViewModel?.name?.value = "里斯"
        }
    }
}