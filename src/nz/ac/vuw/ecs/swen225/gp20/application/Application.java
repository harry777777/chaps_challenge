package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.Record.Recorder;
import nz.ac.vuw.ecs.swen225.gp20.Record.Replay;
import nz.ac.vuw.ecs.swen225.gp20.Record.TickEvent;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Level;
import nz.ac.vuw.ecs.swen225.gp20.persistence.LevelManager;
import nz.ac.vuw.ecs.swen225.gp20.render.Renderer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Owen Application class, this runs the game loop,creates and manages the GUI
 */

public class Application {

  private static JFrame frame;
  private Maze maze;
  private Renderer renderer;
  private Recorder r;
  private TickEvent tickEvent;
  private int timer;
  private int currentTick = 0;
  private boolean paused;
  private Replay replay;
  private boolean replaying = false;
  private boolean ctrlPressed = false;
  private JLabel timerLabel;
  private JLabel levelLabel;
  private boolean gameOver = false;
  private boolean stepByStepReplay = false;
  private LevelManager manager;

  private double GAME_HERTZ = 60;
  private double TBU = 1000000000 / GAME_HERTZ; // Time before update

  /**
   * @param args default argument
   * @author Owen The main method used to run the game
   */

  public static void main(String[] args) {
    Application A = new Application();
  }

  /**
   * @author Owen Loads the game/level, creates the gui and begins the run loop
   */
  public Application() {
    loadGame(null);
    initialiseGui();
    paused = true;
    Object[] options = {"Ok"};
    int n = JOptionPane.showOptionDialog(frame,
        "Press Ok to start the level", "Level Loaded",
        JOptionPane.OK_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]);
    if (n == 0) {
      paused = false;
    }
    levelLabel.setText("Level: 1");
    run();
  }


  /**
   * @author Owen Initialises the Gui
   */
  private void initialiseGui() {
    frame = new JFrame("Chaps Challenge");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);

     //I followed this tutorial for the JMenuBar https://www.tutorialspoint.com/swing/swing_jmenubar_control.htm

    JMenu File = new JMenu("File");
    JMenu Game = new JMenu("Game");

    JMenuItem exit = new JMenuItem("Exit");
    JMenuItem saveAndExit = new JMenuItem("Save and Exit");
    JMenuItem recordAndExit = new JMenuItem("Record and Exit");
    JMenuItem loadSave = new JMenuItem("Load Save");
    JMenuItem startNew = new JMenuItem("Start New Game");
    JMenuItem startAtUnfinished = new JMenuItem("Start at last unfinished level");
    JMenuItem pause = new JMenuItem("Pause");
    JMenuItem replay = new JMenuItem("Replay");

    exit.setActionCommand("Exit");
    saveAndExit.setActionCommand("Save and Exit");
    recordAndExit.setActionCommand("Record and Exit");
    loadSave.setActionCommand("Load Save");
    startNew.setActionCommand("Start New Game");
    startAtUnfinished.setActionCommand("Start at last unfinished level");
    pause.setActionCommand("Pause");
    replay.setActionCommand("Replay");
    class MenuItemListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
          case "Exit":
            exit();
            break;
          case "Save and Exit":
            saveAndExit();
            break;
          case "Record and Exit":
            recordAndExit();
            break;
          case "Load Save":
            loadSave();
            break;
          case "Start New Game":
            startNew();
            break;
          case "Start at last unfinished level":
            startUnfin();
            break;
          case "Pause":
            pause();
            break;
          case "Replay":
            replay();
            break;
        }
      }
    }

    MenuItemListener listener = new MenuItemListener();

    exit.addActionListener(listener);
    saveAndExit.addActionListener(listener);
    recordAndExit.addActionListener(listener);
    loadSave.addActionListener(listener);
    startNew.addActionListener(listener);
    startAtUnfinished.addActionListener(listener);
    pause.addActionListener(listener);
    replay.addActionListener(listener);

    File.add(exit);
    File.add(saveAndExit);
    File.add(recordAndExit);
    File.add(loadSave);
    Game.add(startNew);
    Game.add(startAtUnfinished);
    Game.add(pause);
    Game.add(replay);

    menuBar.add(File);
    menuBar.add(Game);

    frame.pack();
    frame.setVisible(true);
    frame.setSize(new Dimension(700, 700));
    frame.setLayout(new BorderLayout());

    frame.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 17) { //ctrl pressed
          ctrlPressed = true;
        }
        if (e.getKeyCode() == 88 && ctrlPressed) { // X pressed - Exits the Game
          exit();
        }
        if (e.getKeyCode() == 83 && ctrlPressed) { //S pressed - Saves and Exists the Game
          saveAndExit();
        }
        if (e.getKeyCode() == 82 && ctrlPressed) { //R pressed - Resume a Saved Game
          loadSave();
        }
        if (e.getKeyCode() == 80
            && ctrlPressed) { //P pressed - Start a New Game at the last unfinished Level
          startUnfin();
        }
        if (e.getKeyCode() == 49 && ctrlPressed) { //1 pressed - Start Game at Level 1
          startNew();
        }
        if (e.getKeyCode() == 32) { //Space Pressed - Pause and open pause window
          pause();
        }
        if (e.getKeyCode() == 27) { //Esc Pressed - Unpause and close pause window
          paused = false;
        }
        if (!paused) {
          if ((e.getKeyCode() == 38) && !replaying && maze.getPlayer().isStationary()) {
            tickEvent = new TickEvent(currentTick, Direction.UP);
          }
          if ((e.getKeyCode() == 40) && !replaying && maze.getPlayer().isStationary()) {
            tickEvent = new TickEvent(currentTick, Direction.DOWN);
          }
          if ((e.getKeyCode() == 37) && !replaying && maze.getPlayer().isStationary()) {
            tickEvent = new TickEvent(currentTick, Direction.LEFT);
          }
          if ((e.getKeyCode() == 39) && !replaying && maze.getPlayer().isStationary()) {
            tickEvent = new TickEvent(currentTick, Direction.RIGHT);
          }
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 17) {
          ctrlPressed = false;
        }
      }
    });

    JComponent drawing = new JComponent() {
      @Override
      protected void paintComponent(Graphics g) {
        redraw(g);
      }
    };
    drawing.setVisible(true);
    frame.add(drawing, BorderLayout.CENTER);

    JLabel newTimer = new JLabel("Time:" + timer);
    newTimer.setFont(new Font("", Font.BOLD, 24));
    JLabel newLevel = new JLabel("Level:");
    newLevel.setFont(new Font("", Font.BOLD, 24));

    JPanel gameInfo = new JPanel();
    gameInfo.setLayout(new BorderLayout());

    gameInfo.add(newTimer, BorderLayout.PAGE_START);
    timerLabel = newTimer;
    gameInfo.add(newLevel, BorderLayout.PAGE_END);
    levelLabel = newLevel;

    frame.add(gameInfo, BorderLayout.EAST);

    JPanel buttons = new JPanel();
    JButton exitButton = new JButton("Exit");
    exitButton.addActionListener(e -> recordAndExit());
    exitButton.setFocusable(false);

    JButton pauseButton = new JButton("Pause");
    pauseButton.addActionListener(e -> pause());
    pauseButton.setFocusable(false);

    JButton unPauseButton = new JButton("UnPause");
    unPauseButton.addActionListener(e -> paused = false);
    unPauseButton.setFocusable(false);

    JButton replayButton = new JButton("Replay");
    replayButton.addActionListener(e -> replay());
    replayButton.setFocusable(false);

    buttons.add(replayButton);
    buttons.add(pauseButton);
    buttons.add(unPauseButton);
    buttons.add(exitButton);
    buttons.setVisible(true);
    frame.add(buttons, BorderLayout.PAGE_END);

  }

  /**
   * @param g the graphics object from the frame used by the renderer
   * @author Owen Gives the renderer the Graphics2d object
   */
  private void redraw(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    renderer.draw(g2);
  }

  /**
   * @author Owen Redraw with no param to redraw the frame
   */
  private void redraw() {
    frame.repaint();
  }

  /**
   * @author Owen Runs the game loop, it runs at 60hz and is created from a tutorial I followed here
   * https://www.youtube.com/watch?v=LhUN3EKZiio&list=PLvJM9qNXoUYUDaDo_yfSKgn5dYnMmdN8B&index=2&t=335s
   * It manages when to update and to render and does so at the right speed
   */
  private void run() {
    int timerFrameCounter = 0;

    final double MUBR = 1; //Most update before render

    double lastUpdateTime = System.nanoTime();
    double lastRenderTime;

    final double TARGET_FPS = 60;
    final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

    boolean running = true;
    while (running) {
      while (!paused && !gameOver) {
        double now = System.nanoTime();
        int updateCount = 0;
        while (((now - lastUpdateTime) > TBU) && updateCount
            < MUBR) {  //preform the update when its been long enough since last update
          lastUpdateTime += TBU;
          if (tickEvent != null) {
            if (replaying) {
              if (replay.isFinished() && currentTick > tickEvent.getTick() + 25) {
                GAME_HERTZ = 60;
                TBU = 1000000000 / GAME_HERTZ;
                JOptionPane.showMessageDialog(frame, "The recording is finished");
                paused = true;
                replaying = false;
              }
              if (tickEvent.getTick() == currentTick) {
                update();
                tickEvent = replay.getNextTick();
                if (stepByStepReplay && !replay.isFinished()) {
                  paused = true;
                }
              }
            } else {
              update();
            }
          }
          updateCount++;
        }

        if (now - lastUpdateTime > TBU) {
          lastUpdateTime = now - TBU;
        }

        lastRenderTime = now;

        if (!paused) {
          maze.tick();
          redraw();
          currentTick++;
          timerFrameCounter++;
        }
        if (maze.isLevelComplete()) {
          File file = new File("levels/l1c.txt");
          try {
            file.createNewFile();
          } catch (IOException e) {
            e.printStackTrace();
          }
          try {
            r.saveRecording("level1_Complete");
          } catch (IOException e) {
            e.printStackTrace();
          }
          loadGame("level2.json");
        }
        if(!maze.isAlive()){
          loadGame("level2.json");
        }

        if (timerFrameCounter == 60) {
          if (timer == 0) {
            gameOver = true;
            JOptionPane.showMessageDialog(frame, "Game Over, you ran out of time");
          }
          if (!gameOver) {
            timer -= 1;
          }
          timerLabel.setText("Time: " + timer);
          timerFrameCounter = 0;
        }

        while (now - lastRenderTime < TTBR
            && now - lastUpdateTime < TBU) {  // Sleep the thread to let the cpu rest
          Thread.yield();
          try {
            Thread.sleep(2);
          } catch (Exception e) {
            System.out.println("yield error: " + e.getMessage());
          }
          now = System.nanoTime();
        }
      }
    }
  }

  /**
   * @author Owen exits the application
   */
  private void exit() {
    System.exit(0);
  }

  /**
   * @author Owen saves the current game state and exits
   */
  private void saveAndExit() {
    try {
      Level newSave = new Level(maze, timer);
      manager.saveLevel("levels/savedGame.json", newSave);
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    System.exit(0);
  }

  /**
   * @author Owen saves the recording of player actions and exits
   */
  private void recordAndExit() {
    try {
      Object recordingName = JOptionPane.showInputDialog(frame, "Choose a recording name");
      r.saveRecording(recordingName.toString());
    } catch (Exception E) {
      System.out.println("Error saving recording:" + E.getMessage());
    }
    System.exit(0);
  }

  /**
   * @author Owen Loads a user selected save file
   */
  private void loadSave() {
    JFileChooser fc = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Json File", "json");
    fc.setFileFilter(filter);
    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fc.setCurrentDirectory(new File("."));
    int returnVal = fc.showOpenDialog(frame);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      loadGame(file.getName());
    }

  }

  /**
   * @author Owen starts the game again from level 1
   */
  private void startNew() {
    loadGame(null);
  }

  /**
   * @author Owen starts the game from the last unfinished level
   */
  private void startUnfin() {
    File file = new File("levels/l1c.txt");
    boolean exists = file.exists();
    if (exists) {
      loadGame("level2.json");
    } else {
      loadGame(null);
    }
  }

  /**
   * @author Owen pauses the game
   */
  private void pause() {
    paused = true;
    JOptionPane.showMessageDialog(null, "The Game is Paused", "", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * @author Owen, Harry
   * lets the user choose the type of replay and the replay file then begins the replay
   *
   */
  private void replay() {
    try {
      JFileChooser fc = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Json File", "json");
      fc.setFileFilter(filter);
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fc.setCurrentDirectory(new File("."));
      int returnVal = fc.showOpenDialog(frame);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();
        replay = new Replay(file.getName());
        replaying = true;
        paused = true;
        Object[] possibleValues = {"Normal Speed", "Half Speed", "Double Speed",
            "Step by Step"};
        Object replayChoice = JOptionPane.showInputDialog(null,
            "What type of replay", "Input",
            JOptionPane.INFORMATION_MESSAGE, null,
            possibleValues, possibleValues[0]);
        if (replayChoice == "Step by Step") {
          stepByStepReplay = true;
        }
        if (replayChoice == "Half Speed") {
          GAME_HERTZ = 40;
          TBU = 1000000000 / GAME_HERTZ;
        }
        if (replayChoice == "Double Speed") {
          GAME_HERTZ = 60 * 4;
          TBU = 1000000000 / GAME_HERTZ;
        }

        if (replay.recordingLevel() == 1) {
          loadGame("level1.json");
        } else if (replay.recordingLevel() == 2) {
          loadGame("level2.json");
        }
        currentTick = 0;
        timer = 60;
        paused = false;
        tickEvent = replay.getNextTick();
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  /**
   * @param fileName Name of file selected by user
   * @author Owen loads the game level again or uses fileName to load a user selected save or level
   */
  private void loadGame(String fileName) {
    Maze m = null;
    manager = new LevelManager();
    if (paused) {
      paused = false;
    }
    if (gameOver) {
      gameOver = false;
    }
    Level level;
    if (fileName != null) {
      try {
        level = manager.loadLevel("levels/" + fileName);
        m = level.getMaze();
        timer = level.getTimer();
        if (fileName.equals("level2.json")) {
          r = new Recorder(2);
          levelLabel.setText("Level: 2");
        }
        if (fileName.equals("level1.json")) {
          r = new Recorder(1);
          levelLabel.setText("Level: 1");
        }
      } catch (Exception E) {
        System.out.println("Error loading chosen level: " + E.getMessage());
      }
    } else {
      try {
        level = manager.loadLevel("levels/level1.json");
        m = level.getMaze();
        r = new Recorder(1);
      } catch (Exception E) {
        System.out.println("Error loading level 1: " + E.getMessage());
      }
    }
    this.maze = m;
    renderer = new Renderer(m);
    timer = 60;
  }

  /**
   * @author Owen Updates every tick to move the player and at the tickEvent to the recording, but
   * only if something occurred that tick
   */
  private void update() {
    if (tickEvent != null) {
      maze.movePlayer(tickEvent.getMoveDir());
      r.updateRecording(tickEvent);
      tickEvent = null;
    }
  }
}
