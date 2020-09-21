package nz.ac.vuw.ecs.swen225.gp20.Record;

public class Replay {
    Recording recording;
    int playSpeed =1;
    public Replay(Recording recording){
        this.recording = recording;
    }

    public void viewReplay(){
        for(TickEvent t : recording.getTickEvents()){

        }
    }
}
