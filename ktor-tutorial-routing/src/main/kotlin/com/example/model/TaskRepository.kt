package com.example.model

object TaskRepository {
    private val tasks = mutableListOf(
        Task("cleaning", "Clean the house", Priority.Low),
        Task("gardening", "Mow the lawn", Priority.Medium),
        Task("shopping", "Buy the groceries", Priority.High)
    )

    fun allTasks(): List<Task> = tasks
    fun taskByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }
    fun addTask(task: Task) {
        if(taskByName(task.name) != null) {
            throw IllegalStateException("Cannot duplicate task names!")
        }
        tasks.add(task)
    }
}