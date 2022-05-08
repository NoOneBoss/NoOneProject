package ru.mirea.istornikov.mireaproject.ui.stories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.istornikov.mireaproject.databinding.FragmentStoriesBinding
import ru.mirea.istornikov.mireaproject.ui.stories.database.App
import ru.mirea.istornikov.mireaproject.ui.stories.database.AppDatabase
import ru.mirea.istornikov.mireaproject.ui.stories.database.Story
import ru.mirea.istornikov.mireaproject.ui.stories.database.StoryDao


class StoriesFragment : Fragment() {

    private lateinit var binding : FragmentStoriesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setList()
        binding.floatingActionButton2.setOnClickListener(this::onClick)
        return root
    }

    private lateinit var list: RecyclerView
    private lateinit var stories: ArrayList<Story>
    private lateinit var storyDao: StoryDao
    private lateinit var appDatabase: AppDatabase
    private fun setList(){
        appDatabase = App.instance.database
        storyDao = appDatabase.storyDao()
        setInitialData()
        println(stories)

        list = binding.list
        val adapter = StoryAdapter(context, stories)
        binding.list.adapter = adapter
    }

    private fun setInitialData() {
        stories = storyDao.all as ArrayList<Story>
    }

    fun onClick(view: View) {
        val intent = Intent(view.context, StoryView::class.java)
        startActivity(intent)
    }

}