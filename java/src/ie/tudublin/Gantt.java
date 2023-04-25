package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;

public class Gantt extends PApplet
{	
	
	ArrayList<Task> taskArray = new ArrayList<Task>();
	Task selectedTask = null; // Track the currently selected task
	int selectedTaskOffset = 0; // Track the offset between the mouse position and the start or end of the selected task
	
	public void settings()
	{
		size(800, 600);
	}

	public void loadTasks() {

		String[] rows = loadStrings("tasks.csv"); // Load the file as an array of strings
		for (String row : rows) {
		  String[] cols = row.split(","); // Split each row into an array of values
		  Task task = new Task(cols[0], Integer.parseInt(cols[1]), Integer.parseInt(cols[2])); // Create a new Task instance with the values from the row
		  taskArray.add(task); // Add the Task instance to the ArrayList
		}
	  }

	public void printTasks() {

		for (int i = 0; i < taskArray.size(); i++)
			System.out.println(taskArray.get(i));
	}
	
	public void mousePressed() {
		// Check if the mouse is over any task
		for (Task task : taskArray)
		{
			int taskStart = task.getStart();
			int taskEnd = task.getEnd();

			// Check if the mouse is over the start or end of the task
			if (mouseX > taskStart + 20 && mouseX < taskEnd - 20) continue;
			
			// If the mouse is over the start of the task, select it and store the offset between the mouse and the start time
			if (mouseX <= taskStart + 20)
			{
				selectedTask = task;
				selectedTaskOffset = taskStart - mouseX;
				break;
			}

			// If the mouse is over the end of the task, select it and store the offset between the mouse and the end time
			if (mouseX >= taskEnd - 20)
			{
				selectedTask = task;
				selectedTaskOffset = taskEnd - mouseX;
				break;
			}
		}
	}


	public void mouseDragged() {

		if (selectedTask == null) return;

		// Calculate the new start or end time of the selected task based on the mouse position and offset
		int newTime = mouseX + selectedTaskOffset;

		// Ensure that the new start or end time is within the valid range of 1-30
		if (newTime < 1) newTime = 1;
		if (newTime > 30) newTime = 30;

		// Calculate the new start and end times based on which side of the task was clicked
		if (mouseX <= selectedTask.getStart() + 20)
		{
			int newStart = newTime;
			int newEnd = selectedTask.getEnd();
			if (newEnd - newStart < 1) newEnd = newStart + 1;
			selectedTask.setStart(newStart);
			selectedTask.setEnd(newEnd);
		}
		else if (mouseX >= selectedTask.getEnd() - 20)
		{
			int newStart = selectedTask.getStart();
			int newEnd = newTime;
			if (newEnd - newStart < 1) newStart = newEnd - 1;
			selectedTask.setStart(newStart);
			selectedTask.setEnd(newEnd);
		}
	}

	public void setup() {
		// Load the tasks from the CSV file
		loadTasks();

		// Print the tasks to the console
		printTasks();
	}
	
	public void draw()
	{			
		background(0);
	}

	public void displayTasks() {
		colorMode(HSB); // Switch to HSB color mode

		int taskCount = taskArray.size();
		int taskHeight = height / (taskCount + 1);
		int taskMargin = taskHeight / 2;

		for (int i = 0; i < taskCount; i++)
		{
			Task task = taskArray.get(i);

			// Map the task start and end times to colors in the HSB color space
			float hue = map(task.getStart(), 0, width, 0, 255);
			float saturation = 255;
			float brightness = map(task.getEnd(), 0, width, 0, 255);
			fill(hue, saturation, brightness);

			// Draw a rectangle for the task
			rect(task.getStart(), i * taskHeight + taskMargin, task.getEnd() - task.getStart(), taskHeight - taskMargin);
		}
	}
}
