import net.gnehzr.tnoodle.puzzle.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Timer extends JFrame {


    private JPanel mainPanel;
    private JComboBox eventListBox;
    private JTextField enterTimeField;
    private JLabel scrambleLabel;
    private JLabel ao5Time1;
    private JLabel ao5Time2;
    private JLabel ao5Time3;
    private JLabel ao5Time4;
    private JLabel ao5Time5;
    private JComboBox sessionsDropdown;
    private LinkedList<Integer> sessionsListScrambleTypes;
    private JLabel currentAo5StaticLabel;
    private JScrollPane scrambleAreaScrollable;
    private JTextPane scramblePane;
    private String currentScramble;
    private String scrambleType;
    private String currentSession;
    private int ao5Index;
    private LinkedList<String> sessionsList;
    private JLabel[] ao5Labels = {ao5Time1, ao5Time2, ao5Time3, ao5Time4, ao5Time5};
    private double[] ao5 = new double[5];
    private String[] eventNames = {"Square-1", "3x3", "2x2", "4x4", "5x5", "6x6", "7x7", "Skewb", "Pyraminx", "Megaminx"};

    public Timer() {
        add(mainPanel);
        for(JLabel temp : ao5Labels){
            temp.setText("\n");
        }
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel.setBackground(Color.DARK_GRAY);
        setSize(900, 900);
        enterTimeField.setBackground(Color.DARK_GRAY);
        ao5Index = 0;
        setVisible(true);
        eventListBox.setModel(new DefaultComboBoxModel(eventNames));
        readSessions();
        generateScramble();
        eventListBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!eventNames[eventListBox.getSelectedIndex()].equals(scrambleType)) {
                    scrambleType = eventNames[eventListBox.getSelectedIndex()];
                    generateScramble();
                }
            }
        });
         sessionsDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sessionsList.get(sessionsDropdown.getSelectedIndex()).equals(currentSession)) {
                    scrambleType = eventNames[sessionsListScrambleTypes.get(sessionsDropdown.getSelectedIndex())];
                    eventListBox.setSelectedIndex(sessionsListScrambleTypes.get(sessionsDropdown.getSelectedIndex()));
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
                        ao5[ao5Index] = Integer.parseInt(rawInput) / 100;
                        if (ao5Index == 0) {
                            for (JLabel temp : ao5Labels) {
                                temp.setText("\n");
                            }
                        }
                        int errorFromMinutes = (int) Math.floor(ao5[ao5Index] / 100);
                        ao5[ao5Index] = ao5[ao5Index] - errorFromMinutes * 40;
                        ao5Labels[ao5Index].setText("" + ao5[ao5Index]);
                        ao5Index = (ao5Index + 1) % 5;
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
        eventListBox.setSelectedIndex(sessionsListScrambleTypes.get(currentSessionID));

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

}
