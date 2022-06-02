package com.example.simpletodo_phinguyen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.util.Log
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTask = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove item
                listOfTask.removeAt(position)

                //Notify the adapter that our data has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        loadItems()


        //Look up item in recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //create a adapter passing user data
         adapter = TaskItemAdapter(listOfTask, onLongClickListener)

        //Attach the adapter to the recycler view to populate item
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the input field, and button


        val inputTextField = findViewById<EditText>(R.id.addTaskFied)
        //set on click
        findViewById<Button>(R.id.button).setOnClickListener {
            //get input from user in the text field
            val userInputTask = findViewById<EditText>(R.id.addTaskFied).text.toString()

            //add to the list of tasks
            listOfTask.add(userInputTask)

            //Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTask.size - 1)

            //reset data field
            inputTextField.setText("")

            saveItems()

        }

    }

    //get the file we need
    fun getDataFile(): File{
        return File(filesDir, "data.txt")
    }

    //Save file
    fun saveItems() {
        try{
            FileUtils.writeLines(getDataFile(),listOfTask)
        }catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }

    //load file
    fun loadItems() {
        try{
            listOfTask =  FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }

}