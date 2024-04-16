public class TaskByDeadline extends Task {

    public TaskByDeadline(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task otherTask) {
        if(this.deadline < otherTask.deadline){
            return -1;
        }
        else if (this.deadline > otherTask.deadline){
            return 1;
        }
        else{
            return 0;
        }
    }
}
