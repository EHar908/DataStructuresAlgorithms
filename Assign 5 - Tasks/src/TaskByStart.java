public class TaskByStart extends Task{

    public TaskByStart(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task otherTask) {
        if(this.start < otherTask.start){
            return -1;
        }
        else if(this.start == otherTask.start){
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
        else{
            return 1;
        }
    }
}
