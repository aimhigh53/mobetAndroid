package maw.mobet.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item_home.view.*
import maw.mobet.R
import maw.mobet.api.HomeListItem
import maw.mobet.dpToPx
import maw.mobet.intToWon
import kotlin.math.absoluteValue

class MyAdapter(
    private val data: List<HomeListItem>, private val listener: OnClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onClick(view: View)
    }

    private val onClickListener = View.OnClickListener {
        listener?.onClick(it)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        Glide.with(holder.itemView).load(item.imgUrl)
            .apply(RequestOptions().circleCrop())
            .override(dpToPx(holder.itemView.context, 30f).toInt())
            .into(holder.profileImg)
        holder.profileTxt.text = item.name
        holder.titleImg.setImageResource(R.drawable.ic_launcher_background)
        val text = "[${when (item.category) {
            0 -> "패스트푸드"
            1 -> "편의점"
            else -> ""
        }}] ${item.startDate} ~ ${item.endDate}"
        holder.titleTopTxt.text = text
        holder.titleTxt.text = item.title
        holder.titleBottomTxt.text = intToWon(item.price.absoluteValue)
        holder.titleBottomImg.setImageResource(if (item.price > 0) {
            R.drawable.ic_arrow_upward_black_24dp
        } else {
            R.drawable.ic_arrow_downward_black_24dp
        })

        with (holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImg: ImageView = itemView.profile_img
        val profileTxt: TextView = itemView.profile_txt
        val titleImg: ImageView = itemView.title_img
        val titleTopTxt: TextView = itemView.title_top_txt
        val titleTxt: TextView = itemView.title_txt
        val titleBottomTxt: TextView = itemView.title_bottom_txt
        val titleBottomImg: ImageView = itemView.title_bottom_img
    }
}
