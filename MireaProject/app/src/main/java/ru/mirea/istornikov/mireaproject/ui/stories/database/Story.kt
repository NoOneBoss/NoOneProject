package ru.mirea.istornikov.mireaproject.ui.stories.database

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Story {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var storyDate: String = ""
    var imagePath: String = ""
    var storyText: String = ""
}