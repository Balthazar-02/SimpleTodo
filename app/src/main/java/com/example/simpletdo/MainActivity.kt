 package com.example.simpletdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.ButtonBarLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

import java.io.IOException
import java.nio.charset.Charset


 class MainActivity : AppCompatActivity() {

     var  listoftask = mutableListOf<String>()
     lateinit var adapter : taskitemadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongclicklistener = object : taskitemadapter.onlongclicklistener{
            override fun onItemlongclicked(position: Int) {
                // 1. remove the item from the list
                listoftask.removeAt(position)
                // 2. notify the adaoter that somethings has change
                adapter.notifyDataSetChanged()

                saveItems()
            }


        }

        // 1. let's detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//           // code in here is going to be executed when the user cliks on a button
//            Log.i("nowsky", "User cliked on button")
//        }

        loadItems()


        // look up recyclerview in the layout
       val recyclerView =  findViewById<RecyclerView>(R.id.recycleview)
        // Create adapter passing in the sample user data
        adapter = taskitemadapter(listoftask, onLongclicklistener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set up the button and inpout field, so the user will be able to enter her task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Get a reference to the button
        // and then set an onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener {

            // 1. Grab the text that the user  has inputted into @+id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to our list of tasks: listoftask
            listoftask.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listoftask.size - 1)

            // 3. reset the text field
            inputTextField.setText("")

            saveItems()
        }
    }
     // save the data that the user has inputted
     // save data by writing and reading from a file

     //  get the data field we need
     fun getDatafile() : File {

         //Every line is going to represent a specific task in our list of tasks
         return File(filesDir, "hello.txt")
     }

     // load the items by reading every line in the data file
     fun loadItems() {
         try {
             listoftask =org.apache.commons.io.FileUtils.readLines(getDatafile(), Charset.defaultCharset())
         } catch (ioExeption: IOException) {
             ioExeption.printStackTrace()
         }

     }

     // save items by writing them into our data file
     fun saveItems() {
         try {
             org.apache.commons.io.FileUtils.writeLines(getDatafile(), listoftask)
         } catch (ioException: IOException) {
             ioException.printStackTrace()
         }


     }

}