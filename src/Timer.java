import net.gnehzr.tnoodle.puzzle.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Timer extends JFrame {


    private JPanel mainPanel;
    private JLabel scrambleLabel;
    private JComboBox eventListBox;
    private String currentScramble;
    private String scrambleType;
    private String[] eventNames = {"Square-1", "3x3", "2x2", "4x4", "5x5", "6x6", "7x7", "Skewb", "Pyraminx", "Megaminx"};

    public Timer() {
        add(mainPanel);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel.setBackground(Color.DARK_GRAY);
        setSize(900, 900);
        setVisible(true);
        scrambleType = "Square-1";
        generateScramble();
        eventListBox.setModel(new DefaultComboBoxModel(eventNames));
        eventListBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!eventNames[eventListBox.getSelectedIndex()].equals(scrambleType)){
                    scrambleType = eventNames[eventListBox.getSelectedIndex()];
                    generateScramble();
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
        }
        scrambleLabel.setText("Scramble: " + currentScramble);
    }
}
