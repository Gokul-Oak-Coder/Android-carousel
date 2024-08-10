package com.example.studentdb

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.studentdb.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }

    private lateinit var listAdapter: ListAdapter
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var imageList: List<ImageItem>
    private lateinit var listItems: List<ListItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager2 = binding.viewPager
        val searchView = binding.searchView // Ensure correct ID
        val recyclerView = binding.listRecyclerView
        val fab = binding.fab

        // Initialize listItems with drawable resource names
        listItems = listOf(
            ListItem("Apple", "Fruit", "pg_1"),
            ListItem("Banana", "Fruit", "pg_2"),
            ListItem("Orange", "Fruit", "pg_3"),
            ListItem("Grapes", "Fruit", "pg_4"),
            ListItem("Peach", "Fruit", "pg_5"),
            ListItem("Cherry", "Fruit", "pg_6"),
            ListItem("Mango", "Fruit", "pg_7")
        )

        // Initialize imageList with drawable resource names
        imageList = listOf(
            ImageItem(UUID.randomUUID().toString(), "pg_1"),
            ImageItem(UUID.randomUUID().toString(), "pg_2"),
            ImageItem(UUID.randomUUID().toString(), "pg_3"),
            ImageItem(UUID.randomUUID().toString(), "pg_4"),
            ImageItem(UUID.randomUUID().toString(), "pg_5")
        )

        imageAdapter = ImageAdapter(imageList)
        viewPager2.adapter = imageAdapter

        val slideDotLL = binding.sliderDotLL
        val dotsImage = Array(imageList.size) { ImageView(this) }

        dotsImage.forEach {
            it.setImageResource(R.drawable.default_dot)
            slideDotLL.addView(it, params)
        }

        dotsImage[0].setImageResource(R.drawable.selected_dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.forEachIndexed { index, imageView ->
                    imageView.setImageResource(
                        if (position == index) R.drawable.selected_dot
                        else R.drawable.default_dot
                    )
                }
                super.onPageSelected(position)
            }
        }

        viewPager2.registerOnPageChangeCallback(pageChangeListener)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        listAdapter = ListAdapter(listItems)
        recyclerView.adapter = listAdapter

        // Set up SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Optionally handle submit action
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { listAdapter.filter(it) }
                return true
            }
        })

        fab.setOnClickListener {
            showStatisticsBottomSheet()
        }
    }

    private fun showStatisticsBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_statistics, null)
        val statisticsTextView: TextView = bottomSheetView.findViewById(R.id.statisticsTextView)

        val itemCounts = getItemCounts()
        val topCharacters = getTopCharacters()

        val statistics = buildString {
            append("Item Counts:\n")
            itemCounts.forEach { (page, count) ->
                append("Page $page: $count items\n")
            }
            append("\nTop Characters:\n")
            topCharacters.forEach { (char, count) ->
                append("$char: $count\n")
            }
        }

        statisticsTextView.text = statistics

        AlertDialog.Builder(this)
            .setView(bottomSheetView)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun getItemCounts(): Map<Int, Int> {
        return (0 until imageList.size).associateWith { listItems.size / imageList.size }
    }

    private fun getTopCharacters(): List<Pair<Char, Int>> {
        val text = listItems.flatMap { listOf(it.title, it.subtitle) }.joinToString("")
        val charCounts = text.groupingBy { it }.eachCount()
        return charCounts.entries.sortedByDescending { it.value }.take(3).map { it.toPair() }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
    }
}
















































//package com.example.studentdb
//
//import android.os.Bundle
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.SearchView
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.viewpager2.widget.ViewPager2
//import com.example.studentdb.databinding.ActivityMainBinding
//import java.util.UUID
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var viewPager2: ViewPager2
//    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
//
//    private val params = LinearLayout.LayoutParams(
//        LinearLayout.LayoutParams.WRAP_CONTENT,
//        LinearLayout.LayoutParams.WRAP_CONTENT
//    ).apply {
//        setMargins(8, 0, 8, 0)
//    }
//
//    private lateinit var listAdapter: ListAdapter
//    private lateinit var imageAdapter: ImageAdapter
//    private lateinit var imageList: List<ImageItem>
//    private lateinit var listItems: List<ListItem>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        viewPager2 = binding.viewPager
//        val searchView = binding.searchView // Ensure correct ID
//        val recyclerView = binding.listRecyclerView
//        val fab = binding.fab
//
//        listItems = listOf(
//            ListItem("Apple", "Fruit", "android.resource://${packageName}/${R.drawable.pg_1}"),
//            ListItem("Banana", "Fruit", "android.resource://${packageName}/${R.drawable.pg_2}"),
//            ListItem("Banana", "Fruit", "android.resource://${packageName}/${R.drawable.pg_3}"),
//            ListItem("Banana", "Fruit", "android.resource://${packageName}/${R.drawable.pg_4}"),
//            ListItem("Banana", "Fruit", "android.resource://${packageName}/${R.drawable.pg_5}"),
//            ListItem("Banana", "Fruit", "android.resource://${packageName}/${R.drawable.pg_6}"),
//            ListItem("Banana", "Fruit", "android.resource://${packageName}/${R.drawable.pg_7}")
//            // Add more items as needed
//        )
//
//        imageList = listOf(
//            ImageItem(UUID.randomUUID().toString(), "android.resource://${packageName}/${R.drawable.pg_1}"),
//            ImageItem(UUID.randomUUID().toString(), "android.resource://${packageName}/${R.drawable.pg_2}"),
//            ImageItem(UUID.randomUUID().toString(), "android.resource://${packageName}/${R.drawable.pg_3}"),
//            ImageItem(UUID.randomUUID().toString(), "android.resource://${packageName}/${R.drawable.pg_4}"),
//            ImageItem(UUID.randomUUID().toString(), "android.resource://${packageName}/${R.drawable.pg_5}")
//        )
//
//        imageAdapter = ImageAdapter(imageList.map { it.url })
//        viewPager2.adapter = imageAdapter
//
//        val slideDotLL = binding.sliderDotLL
//        val dotsImage = Array(imageList.size) { ImageView(this) }
//
//        dotsImage.forEach {
//            it.setImageResource(R.drawable.default_dot)
//            slideDotLL.addView(it, params)
//        }
//
//        dotsImage[0].setImageResource(R.drawable.selected_dot)
//
//        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                dotsImage.forEachIndexed { index, imageView ->
//                    imageView.setImageResource(
//                        if (position == index) R.drawable.selected_dot
//                        else R.drawable.default_dot
//                    )
//                }
//                super.onPageSelected(position)
//            }
//        }
//
//        viewPager2.registerOnPageChangeCallback(pageChangeListener)
//
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//
//        listAdapter = ListAdapter(listItems)
//        recyclerView.adapter = listAdapter
//
//        // Set up SearchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // Optionally handle submit action
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let { listAdapter.filter(it) }
//                return true
//            }
//        })
//
//        fab.setOnClickListener {
//            showStatisticsBottomSheet()
//        }
//    }
//
//    private fun showStatisticsBottomSheet() {
//        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_statistics, null)
//        val statisticsTextView: TextView = bottomSheetView.findViewById(R.id.statisticsTextView)
//
//        val itemCounts = getItemCounts()
//        val topCharacters = getTopCharacters()
//
//        val statistics = buildString {
//            append("Item Counts:\n")
//            itemCounts.forEach { (page, count) ->
//                append("Page $page: $count items\n")
//            }
//            append("\nTop Characters:\n")
//            topCharacters.forEach { (char, count) ->
//                append("$char: $count\n")
//            }
//        }
//
//        statisticsTextView.text = statistics
//
//        AlertDialog.Builder(this)
//            .setView(bottomSheetView)
//            .setPositiveButton("OK", null)
//            .show()
//    }
//
//    private fun getItemCounts(): Map<Int, Int> {
//        return (0 until imageList.size).associateWith { listItems.size / imageList.size }
//    }
//
//    private fun getTopCharacters(): List<Pair<Char, Int>> {
//        val text = listItems.flatMap { listOf(it.title, it.subtitle) }.joinToString("")
//        val charCounts = text.groupingBy { it }.eachCount()
//        return charCounts.entries.sortedByDescending { it.value }.take(3).map { it.toPair() }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
//    }
//}
