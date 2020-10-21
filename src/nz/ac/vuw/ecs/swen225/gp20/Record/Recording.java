package nz.ac.vuw.ecs.swen225.gp20.Record;




import java.util.ArrayList;


/**
 * @author Harry A recording of a game, contains a list of tickEvent objects
 */
public class Recording {

  private ArrayList<TickEvent> tickEvents; // List of events that happening on each tick of a game.
  private String name;
  private int level;

  /**
   * @param name name of the recording
   * @author Harry constructor for a recording.
   */
  public Recording(String name,int level) {
    this.name = name;
    this.tickEvents = new ArrayList<TickEvent>();
    this.level =level;

  }

  /**
   * @param t the tickEvent being added to the recording of the game
   * @author Harry add a TickEvent to a recording of a game.
   */
  public void addEvent(TickEvent t) {
    this.tickEvents.add(t);
  }

  /**
   * @return ArrayList<tickEvent> all the tick events that have occured in a recording.
   * @author Harry getter for the tickEvents list.
   */
  public ArrayList<TickEvent> getTickEvents() {
    return tickEvents;
  }


  /**
   * @return String Representaiton of a tickEvent object.
   * @author Harry toString for tickEvent objects.
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (TickEvent t : this.tickEvents) {
      if (t.idle()) {
        s.append("Idle");
      } else {
        s.append(t.getMoveDir().toString());
      }
      s.append(" on tick number : " + t.getTick());
      s.append("\n");
    }
    return s.toString();
  }

  public int getLevel(){
    return this.level;
  }
}

