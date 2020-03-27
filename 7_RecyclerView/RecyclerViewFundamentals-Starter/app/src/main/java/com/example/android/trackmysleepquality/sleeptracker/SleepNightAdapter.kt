package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

/**
 * Created by Martin Mallet on 2020-03-26
 */

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }
}

class SleepNightAdapter : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    /*
    * The onBindViewHolder() function is called by RecyclerView to display the data for one list
    * item at the specified position. So the onBindViewHolder() method takes two arguments: a view holder,
    * and a position of the data to bind. For this app, the holder is the TextItemViewHolder,
    * and the position is the position in the list.
    * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    //this is called when the RecyclerView needs a view holder to represent an item.
    //The viewType parameter is used when there are multiple views in the same RecycledView.
    //For example, if you put a list of text views, an image, and a video all in the same RecyclerView,
    // the onCreateViewHolder() function would need to know what type of view to use.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //the layoutinflater knows how to create views from XML layout
        //The context contains information on how to correctly inflate the view.
        return ViewHolder.from(parent)
    }

    class ViewHolder(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepNight) {
            binding.sleep = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =
                        LayoutInflater.from(parent.context)

                val binding =
                        ListItemSleepNightBinding.inflate(layoutInflater, parent, false)

                //then, I return the created view.
                return ViewHolder(binding)
            }
        }
    }


}

