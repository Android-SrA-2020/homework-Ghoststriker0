package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

/**
 * Created by Martin Mallet on 2020-03-26
 */

class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {

    //this is to hold the data
    //that custom setter will reassign the data and also redraw the list with the new data since the data changed.
    var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //we need this so that the recycler view knows how many items to return
    override fun getItemCount() = data.size

    /*
    * The onBindViewHolder() function is called by RecyclerView to display the data for one list
    * item at the specified position. So the onBindViewHolder() method takes two arguments: a view holder,
    * and a position of the data to bind. For this app, the holder is the TextItemViewHolder,
    * and the position is the position in the list.
    * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

        fun bind(item: SleepNight) {
            val res = itemView.context.resources
            sleepLength.text = convertDurationToFormatted(
                    item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(
                    item.sleepQuality, res)
            qualityImage.setImageResource(when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            })
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =
                        LayoutInflater.from(parent.context)

                //Now I use the layout inflater to create my views.
                //If I had different types of view, I would have a checker to see which type of view to create. Switch of if statement
                val view = layoutInflater
                        .inflate(R.layout.list_item_sleep_night,
                                parent, false)


                //then, I return the created view.
                return ViewHolder(view)
            }
        }
    }


}

