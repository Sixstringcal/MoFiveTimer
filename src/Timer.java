import javafx.scene.input.KeyCode;
import net.gnehzr.tnoodle.puzzle.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class Timer extends JFrame {


    private JPanel mainPanel;
    private JComboBox eventListBox;
    private JTextField enterTimeField;
    private JLabel scrambleLabel;
    private JScrollPane scrambleAreaScrollable;
    private JTextPane scramblePane;
    private String currentScramble;
    private String scrambleType;
    private double[] mo5 = new double[5];
    private String[] eventNames = {"Square-1", "3x3", "2x2", "4x4", "5x5", "6x6", "7x7", "Skewb", "Pyraminx", "Megaminx"};

    public Timer() {
        add(mainPanel);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel.setBackground(Color.DARK_GRAY);
        setSize(900, 900);
        enterTimeField.setBackground(Color.DARK_GRAY);

        setVisible(true);
        scrambleType = "Square-1";
        generateScramble();
        eventListBox.setModel(new DefaultComboBoxModel(eventNames));
        eventListBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!eventNames[eventListBox.getSelectedIndex()].equals(scrambleType)) {
                    scrambleType = eventNames[eventListBox.getSelectedIndex()];
                    generateScramble();
                }
            }
        });
        enterTimeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 13) {

                }
            }
        });
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
                if (i < charredScramble.length-1) {
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
