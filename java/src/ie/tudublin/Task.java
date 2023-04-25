package ie.tudublin;

import processing.data.TableRow;

public class Task {

    private String name;
    private int start;
    private int end;

    public Task(String name, int start, int end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public Task(TableRow row) {
        this.name = row.getString("name");
        this.start = row.getInt("start");
        this.end = row.getInt("end");
    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    // Setters
    public void setStart(int start) {
        this.start = start;
    }
    
    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Task{" +
                ", Name='" + name + '\'' +
                ", Start='" + start + '\'' +
                ", End=" + end +
                '}';
    }
}