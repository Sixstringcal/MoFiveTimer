import net.gnehzr.tnoodle.puzzle.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Timer extends JFrame {


    /**
     * The panel that all components live on.
     */
    private JPanel mainPanel;

    /**
     * The dropdown box that lists all scramble types.
     */
    private JComboBox scrambleListBox;

    /**
     * The text field where you enter your time of your solve you just completed using the displayed scramble.
     */
    private JTextField enterTimeField;

    /**
     * The label that contains the scramble for the current attempt.
     */
    private JLabel scrambleLabel;

    /**
     * The labels that contain the solves for the current average of 5 solves.
     */
    private JLabel ao5Time1;
    private JLabel ao5Time2;
    private JLabel ao5Time3;
    private JLabel ao5Time4;
    private JLabel ao5Time5;

    /**
     * The storage of the labels for the average of 5.
     */
    private JLabel[] ao5Labels = {ao5Time1, ao5Time2, ao5Time3, ao5Time4, ao5Time5};

    /**
     * The dropdown box for the sessions.
     */
    private JComboBox sessionsDropdown;

    /**
     * Contains the key for the current type of scramble for the current scramble.
     * TODO: make a datatype for this.
     */
    private LinkedList<Integer> sessionsListScrambleTypes;

    /**
     * This is the label that says "Current Ao5".  This should never change.
     */
    private JLabel currentAo5StaticLabel;

    /**
     * The current scramble for the attempt.  This is in plaintext.
     */
    private String currentScramble;

    /**
     * The type of scramble the user should be seeing on the screen.
     */
    private String scrambleType;

    /**
     * The name of the current session.
     */
    private String currentSession;

    /**
     * Denotes what solve in the average of five it is currently.
     */
    private int ao5Index;

    /**
     * The list of all sessions that have been created by the user.
     */
    private LinkedList<String> sessionsList;

    /**
     * Stores the actual times of the average of 5.
     */
    private double[] ao5 = new double[5];

    /**
     * Stores the names of the scramble types.
     */
    private String[] eventNames = {"Square-1", "3x3", "2x2", "4x4", "5x5", "6x6", "7x7", "Skewb", "Pyraminx",
            "Megaminx"};


    /**
     * The main screen the user interfaces with.
     */
    public Timer() {
        add(mainPanel);

        clearLabels();

        //Dark Mode
        setBackground(Color.DARK_GRAY);
        mainPanel.setBackground(Color.DARK_GRAY);
        enterTimeField.setBackground(Color.DARK_GRAY);

        //Setting program defaults.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 900);
        setVisible(true);

        //Filling the scramble type selector dropdown box.
        scrambleListBox.setModel(new DefaultComboBoxModel(eventNames));

        // TODO: read scramble index from file.
        ao5Index = 0;

        readSessions();
        generateScramble();

        //ActionListeners
        scrambleListBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!eventNames[scrambleListBox.getSelectedIndex()].equals(scrambleType)) {
                    scrambleType = eventNames[scrambleListBox.getSelectedIndex()];
                    generateScramble();
                }
            }
        });
         sessionsDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sessionsList.get(sessionsDropdown.getSelectedIndex()).equals(currentSession)) {
                    scrambleType = eventNames[sessionsListScrambleTypes.get(sessionsDropdown.getSelectedIndex())];
                    scrambleListBox.setSelectedIndex(sessionsListScrambleTypes.get(sessionsDropdown.getSelectedIndex()));
                    generateScramble();
                }
            }
        });
        enterTimeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String rawInput = enterTimeField.getText();
                    try {
                        //Interprets the decimals.
                        ao5[ao5Index] = (double)Integer.parseInt(rawInput) / (double)100;

                        //Happens if the new solve is now the first solve in the average of 5.  It resets the labels.
                        if (ao5Index == 0) {
                            clearLabels();
                        }

                        //Gets the number of minutes from the "hundreds place" and further left.
                        int numMinutes = (int) Math.floor(ao5[ao5Index] / 100);

                        //A minute is 60% of 100 minutes.
                        ao5[ao5Index] = ao5[ao5Index] - numMinutes * 40;
                        ao5Labels[ao5Index].setText("" + ao5[ao5Index]);
                        ao5Index = (ao5Index + 1) % 5;

                        //Clears the text field the user typed the time into.
                        enterTimeField.setText("");

                    } catch(Exception exception) {

                    }
                }
            }
        });
    }

    private void readSessions() {
        File sessionsNamesFile = new File("src/Sessions.txt");
        sessionsList = new LinkedList<>();
        int currentSessionID = 0;
        sessionsListScrambleTypes = new LinkedList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(sessionsNamesFile);
            if(!scanner.hasNextLine()){
                createNewSession();
            }
            currentSessionID = scanner.nextInt();
            while(scanner.hasNextLine()){
                sessionsListScrambleTypes.add(scanner.nextInt());
                sessionsList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sessionsDropdown.setModel(new DefaultComboBoxModel(sessionsList.toArray()));
        System.out.println(currentSessionID);
        currentSession = sessionsList.getLast();
        scrambleType = eventNames[sessionsListScrambleTypes.get(currentSessionID)];
        sessionsDropdown.setSelectedIndex(sessionsListScrambleTypes.get(currentSessionID));
        scrambleListBox.setSelectedIndex(sessionsListScrambleTypes.get(currentSessionID));

    }

    private void createNewSession(){

    }

    public void generateScramble() {
        switch (scrambleType) {
            case "Square-1":
                SquareOnePuzzle squareOnePuzzle = new SquareOnePuzzle();
                currentScramble = squareOnePuzzle.generateScramble();
                break;
            case "3x3":
                ThreeByThreeCubePuzzle threeByThreeCubePuzzle = new ThreeByThreeCubePuzzle();
                currentScramble = threeByThreeCubePuzzle.generateScramble();
                break;
            case "2x2":
                TwoByTwoCubePuzzle twoByTwoCubePuzzle = new TwoByTwoCubePuzzle();
                currentScramble = twoByTwoCubePuzzle.generateScramble();
                break;
            case "4x4":
                FourByFourCubePuzzle fourByFourCubePuzzle = new FourByFourCubePuzzle();
                currentScramble = fourByFourCubePuzzle.generateScramble();
                break;
            case "5x5":
                NoInspectionFiveByFiveCubePuzzle fiveByFiveCubePuzzle = new NoInspectionFiveByFiveCubePuzzle();
                currentScramble = fiveByFiveCubePuzzle.generateScramble();
                break;
            case "6x6":
                CubePuzzle sixBySix = new CubePuzzle(6);
                currentScramble = sixBySix.generateScramble();
                break;
            case "7x7":
                CubePuzzle sevenBySeven = new CubePuzzle(7);
                currentScramble = sevenBySeven.generateScramble();
                break;
        }
        int screenWidth = getWidth() / 10;
        textWrap(screenWidth);


    }

    public void textWrap(int screenWidth) {
        String displayedScramble = "";
        int j = 1;
        char[] charredScramble = currentScramble.toCharArray();

        for (int i = 0; i < charredScramble.length; i++) {
            displayedScramble += charredScramble[i];
            if (j % screenWidth == 0) {
                if (i < charredScramble.length - 1) {
                    if (charredScramble[i] != 'w' && charredScramble[i + 1] != '\'' && charredScramble[i + 1] != 'w' && (charredScramble[i] != '3' && !scrambleType.equals("Square-1"))) {
                        displayedScramble += "<br>";
                    } else {
                        j--;
                    }
                }
            }
            j++;
        }
        scrambleLabel.setText("<html><center>Scramble: " + displayedScramble + "</center></html>");
    }

    private void clearLabels(){
        for(JLabel temp : ao5Labels){
            temp.setText("\n");
        }
    }

}
