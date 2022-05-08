package ru.mirea.istornikov.mireaproject.ui.stories

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.istornikov.mireaproject.R
import ru.mirea.istornikov.mireaproject.ui.stories.database.Story


class StoryAdapter internal constructor(context: Context?, private val items: List<Story>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.story_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.storyImage.setImageURI(Uri.parse(item.imagePath))
        holder.storyDate.text = item.storyDate
        holder.storyText.text = item.storyText
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val storyImage: ImageView = view.findViewById(R.id.image)
        val storyDate: TextView = view.findViewById(R.id.storyDate)
        val storyText: TextView = view.findViewById(R.id.storyText)
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}