package com.jt17.neofilms.adapters
//
//import android.media.browse.MediaBrowser.ItemCallback
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.jt17.neofilms.ModalBottomSheet
//import com.jt17.neofilms.databinding.HomeItemLyBinding
//import com.jt17.neofilms.databinding.InthParentItemBinding
//import com.jt17.neofilms.models.HomeModel
//import com.jt17.neofilms.models.InTheatresModel
//import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
//import com.smarteist.autoimageslider.SliderAnimations
//import com.smarteist.autoimageslider.SliderView
//import com.smarteist.autoimageslider.SliderViewAdapter
//
//class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ItemHolder>() {
//    private val subSliderAdapter by lazy { SliderMPM_Adapter() }
//    private val subInThAdapter by lazy { InTheatersAdapter(object : com.jt17.neofilms.adapters.ItemCallback {
//        override fun putBottomSheet(itemData: InTheatresModel) {
//
//        }
//
//    }) }
//
//    inner class ItemHolder(val b: HomeItemLyBinding) : RecyclerView.ViewHolder(b.root) {
//
//
//        fun binder(result: HomeModel) {
//            b.popularMSlider.run {
//                setSliderAdapter(subSliderAdapter)
//                setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)
//                setSliderTransformAnimation(SliderAnimations.VERTICALSHUTTRANSFORMATION)
//                scrollTimeInSec = 3
//                autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
//                startAutoCycle()
//            }
//
//            b.nestedRecycHome.run {
//                layoutManager =
//                    LinearLayoutManager(b.root.context, LinearLayoutManager.HORIZONTAL, false)
//
//                adapter = subInThAdapter
//            }
//
//            subSliderAdapter.newList(result.mainModel)
//            subInThAdapter.newList(result.inThmodel)
//
//        }
//    }
//
//    var baseList: List<HomeModel> = emptyList()
//
//    fun newList(list: List<HomeModel>) {
//        baseList = list
//        notifyDataSetChanged()
//    }
//
////    var baseSliderList: List<HomeModel> = emptyList()
////    var baseInThList: List<HomeModel> = emptyList()
////
////    fun newSliderList(list: List<HomeModel>) {
////        baseSliderList = list
////        notifyDataSetChanged()
////    }
////
////    fun newInThList(list: List<HomeModel>) {
////        baseInThList = list
////        notifyDataSetChanged()
////    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ItemHolder {
//        return ItemHolder(
//            HomeItemLyBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: HomeAdapter.ItemHolder, position: Int) {
//        val itemData = baseList[position]
//        holder.binder(itemData)
//    }
//
//    override fun getItemCount(): Int = baseList.size
//
//}