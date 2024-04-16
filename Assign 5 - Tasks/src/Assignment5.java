import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Assignment5 {
    public static void main(String[] args) {
        simpleQueueTest();
//        scheduleTasks("taskset1.txt");
//        scheduleTasks("taskset2.txt");
//        scheduleTasks("taskset3.txt");
        scheduleTasks("taskset4.txt");
        //scheduleTasks("taskset5.txt");
    }

    public static void scheduleTasks(String taskFile) {
        Queue<Task> tasksByDeadline = new LinkedList<>();
        Queue<Task> tasksByStart = new LinkedList<>();
        Queue<Task> tasksByDuration = new LinkedList<>();

        readTasks(taskFile, tasksByDeadline, tasksByStart, tasksByDuration);

        schedule("Deadline Priority : "+ taskFile, tasksByDeadline);
        schedule("Startime Priority : " + taskFile, tasksByStart);
        schedule("Duration priority : " + taskFile, tasksByDuration);
    }

    public static void simpleQueueTest() {
//        PriorityQueue<Integer> queue = new PriorityQueue<>();
//        queue.enqueue(9);
//        queue.enqueue(7);
//        queue.enqueue(5);
//        queue.enqueue(3);
//        queue.enqueue(1);
//        queue.enqueue(10);
//
//        while (!queue.isEmpty()) {
//            System.out.println(queue.dequeue());
//        }
    }

    /**
     * Reads the task data from a file, and creates the three different sets of tasks for each
     */
    public static void readTasks(String filename,
                                 Queue<Task> tasksByDeadline,
                                 Queue<Task> tasksByStart,
                                 Queue<Task> tasksByDuration) {
        File file = new File(filename);
        int ID = 1;
        try(Scanner input = new Scanner(file)){
            while (input.hasNextLine()){
                String line = input.nextLine();
                if (!line.isEmpty()){
                    String[] numbers = line.split("\t");
                    tasksByStart.add(new TaskByStart(ID, Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2])));
                    tasksByDeadline.add(new TaskByDeadline(ID, Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2])));
                    tasksByDuration.add(new TaskByDuration(ID, Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2])));
                }
                ID++;
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the file: " + ex);
        }
    }

    /**
     * Given a set of tasks, schedules them and reports the scheduling results
     */
    public static void schedule(String label, Queue<Task> tasks) {
        System.out.print(label);
        PriorityQueue<Task> queue = new PriorityQueue<>();
        ArrayList<Task> taskArray = new ArrayList<>();
        for(Task task : tasks){
            taskArray.add(task);
        }
        int clock = 0;
        int numberLate = 0;
        int totalLate = 0;
        while(taskArray.size() != 0){
            clock++;
            for(int i = 0; i < taskArray.size(); i++){
                if(taskArray.get(i).start == clock && taskArray.get(i) != null){
                    queue.enqueue(taskArray.get(i));
                }
            }
            Task currentTask = queue.dequeue();
            System.out.println();
            if(currentTask != null){
                currentTask.duration--;
                System.out.print("Time   " + clock + ": " + currentTask.toString() + " ");
                if(currentTask.duration == 0){
                    System.out.print("** ");
                    taskArray.remove(currentTask);
                }
                else{
                    queue.enqueue(currentTask);
                }
                if(currentTask.deadline < clock){
                    System.out.print("Late " + (clock - currentTask.deadline));
                    numberLate++;
                    totalLate += (clock - currentTask.deadline);
                }


            }
            else{

                System.out.print("Time   " + clock + ": ---");
            }

        }

        System.out.println();
        System.out.println("Tasks late " + numberLate +  " Total Late " + totalLate);
        System.out.println();
    }
}
