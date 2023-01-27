package com.example.myapplication

import java.io.File
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun mergeSort(list: List<Int>): List<Int> {
    if (list.size <= 1) {
        return list
    }

    val middle = list.size / 2
    var leftPart = list.subList(0,middle);
    var rightPart = list.subList(middle,list.size);

    return merge(mergeSort(leftPart), mergeSort(rightPart))
}

fun merge(leftPart: List<Int>, rightPart: List<Int>): List<Int>  {
    var indexLeft = 0
    var indexRight = 0
    var newList : MutableList<Int> = mutableListOf()

    while (indexLeft < leftPart.size && indexRight < rightPart.size) {
        if (leftPart[indexLeft] <= rightPart[indexRight]) {
            newList.add(leftPart[indexLeft])
            indexLeft++
        } else {
            newList.add(rightPart[indexRight])
            indexRight++
        }
    }

    while (indexLeft < leftPart.size) {
        newList.add(leftPart[indexLeft])
        indexLeft++
    }

    while (indexRight < rightPart.size) {
        newList.add(rightPart[indexRight])
        indexRight++
    }
    return newList
}

fun writeListToFile (file: File, size: Int){

    val numbers = List(size) { Random.nextInt(-10000000, 10000000) }

    file.printWriter().use { out ->
        numbers.forEach{out.println("$it")}
    }
}

fun readToListFromFile (file: File, readList: MutableList<Int>){
    File("somefile.txt").readLines().forEach {
        readList.add(it.toInt())
    }
}

fun getFIleSize (file: File, type: Int): Int{
    val fileSizeInKB = (file.length() / 1024).toString().toInt()
    val fileSizeInMb = (file.length() / (1024 * 1000)).toString().toInt()
    val fileSizeInGb = (file.length() / (1024*1000000)).toString().toInt()
    return when (type) {
        1 -> {
            fileSizeInMb
        }
        2 -> {
            fileSizeInGb
        }
        else -> {
            fileSizeInKB
        }
    }

}

fun main() {
    val file1 = File("somefile1.txt")
    val file2 = File("somefile2.txt")

    writeListToFile(file1, 25000000)
//    writeListToFile(file2, 20000000)

    val readList = mutableListOf<Int>()
    readToListFromFile(file1, readList)

    val executionTime = measureTimeMillis { mergeSort(readList) }
    val sortedList = mergeSort(readList)
    println("Unsorted: $readList")
    println("Sorted: $sortedList")
    println("The execution time of file with size = ${getFIleSize(file1, 1)}MB is ${executionTime} milisecs")
//    readList.clear()
//    readToListFromFile(file2, readList)
//    executionTime = measureTimeMillis { mergeSort(readList) }
//    println("The execution time of file with size = ${getFIleSize(file2, 2)}GB is ${executionTime / 60000} minutes")



}