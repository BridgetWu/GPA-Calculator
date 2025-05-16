/**
 * The Weighted GPA Calculator implements an application that calculates the
 * average weighted GPA, given the input letter grades and the course type.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class App extends JFrame{
    public static final String[] typesList = {"", "College Prep", "Honors", "AP/Dual Enrollment"};
    public static final String[] gradesList = {"", "A+", "A", "A-", "B+", "B", "B-",
                                                "C+", "C", "C-", "D+", "D", "D-", "F"};

    public static ArrayList<String> userInputGrades = new ArrayList<String>(12);
    public static ArrayList<String> userInputTypes = new ArrayList<String>(12);

    /**
     * This constructor sets up the basic components of the app.
     */
    public App(){
        super("Weighted GPA Calculator");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(true);
        getContentPane().setBackground(new java.awt.Color(4, 194, 89));
        addGuiComponents();
    }

    /**
     * This function adds the components, including labels, text fields, drop-down boxes, and buttons.
     */
    public void addGuiComponents(){
        // Add the title name of the app
        JLabel title = new JLabel("Weighted GPA Calculator");
        title.setFont(new Font("Monospace", Font.BOLD, 50));
        title.setBounds(350, 5, 850, 60);
        title.setForeground(Color.white);
        add(title);

        // Add the label that will later show the result output
        JLabel GPALabel = new JLabel("");
        add(GPALabel);

        // Add the course name label
        JLabel courseName = new JLabel("Course name");
        courseName.setFont(new Font("Dialog", Font.BOLD, 18));
        courseName.setBounds(0, 90, 450, 27);
        add(courseName);

        // Add the course grade label
        JLabel courseGrade = new JLabel("Grade");
        courseGrade.setFont(new Font("Dialog", Font.BOLD, 18));
        courseGrade.setBounds(200, 90, 450, 27);
        add(courseGrade);

        // Add the course type label
        JLabel courseType = new JLabel("Course type");
        courseType.setFont(new Font("Dialog", Font.BOLD, 18));
        courseType.setBounds(300, 90, 450, 27);
        add(courseType);

        int yCoordinate = 122;
        for (int i = 0; i < 12; i++) {
            userInputGrades.add("");
            userInputTypes.add("");

            int boxIndex = i;

            final JComboBox<String> courseTypeInput = new JComboBox<String>(typesList);
            courseTypeInput.setBounds(300, yCoordinate + i * 40, 450, 27);
            add(courseTypeInput);

            courseTypeInput.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String t = courseTypeInput.getItemAt(courseTypeInput.getSelectedIndex());
                    userInputTypes.set(boxIndex, t);
                }
            });

            final JComboBox<String> gradesInput = new JComboBox<String>(gradesList);
            gradesInput.setBounds(200, yCoordinate + i * 40, 80, 27);
            add(gradesInput);

            gradesInput.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String s = gradesInput.getItemAt(gradesInput.getSelectedIndex());
                    userInputGrades.set(boxIndex, s);
                }
            });

            JTextField nameInput = new JTextField();
            nameInput.setBounds(0, yCoordinate + i * 40, 160, 27);
            add(nameInput);
        }

        /* Add a button to calculate scores. Once clicked, the button will call the methods
         * getGradesInput() and calculateGPA() to create the output. If the user did not enter
         * any grades, a label will tell user to enter their grades. If either the grade or the
         * course type isn't entered, the program removes the score from calculation.
         */
        JButton calculateButton = new JButton();
        calculateButton.setBounds(600, 650, 160, 50);
        calculateButton.setText("Calculate!");
        calculateButton.setFocusable(false);
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Double> list = getGradesInput(userInputGrades, userInputTypes);
                double calculatedGPA = calculateGPA(list);

                if (list.size() == 0){
                    GPALabel.setText("Error: You must enter your grades");
                    GPALabel.setFont(new Font("Dialog", Font.BOLD, 20));
                    GPALabel.setBounds(800, 90, 850, 60);
                    GPALabel.setForeground(Color.red);
                }
                else {
                    GPALabel.setText("Your GPA: " + calculatedGPA);
                    GPALabel.setFont(new Font("Dialog", Font.BOLD, 40));
                    GPALabel.setBounds(800, 90, 850, 60);
                    GPALabel.setForeground(Color.white);
                }
                revalidate();
                repaint();
            }
        });
        add(calculateButton);
    }


    public ArrayList<Double> getGradesInput(ArrayList<String> letterGrades, ArrayList<String> types){
        ArrayList<Double> grades = new ArrayList<Double>();

        for (int i = 0; i < letterGrades.size(); i++){
            if (letterGrades.get(i) != "" && types.get(i) != "") {
                double convertedGrade = convertGPA(letterGrades.get(i), types.get(i));
                grades.add(convertedGrade);
            }
        }

        return grades;
    }

    public double convertGPA(String letterGrade, String type){
        String[] letterGrades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F"};

        double[][] prepGrades = {{4.0, 4.0, 3.7, 3.3, 3.0, 2.7, 2.3, 2.0, 1.7, 1.3, 1.0, 0.7, 0.0},
                                {4.5, 4.5, 4.2, 3.8, 3.5, 3.2, 2.8, 2.5, 2.2, 1.8, 1.5, 1.2, 0.0},
                                {5.0, 5.0, 4.7, 4.3, 4.0, 3.7, 3.3, 2.7, 2.7, 2.3, 1.7, 1.7, 0.0}};

        int row = -1;
        int col = -1;

        if (type.equals("College Prep")){
            row = 0;
        } else if (type.equals("Honors")){
            row = 1;
        } else if (type.equals("AP/Dual Enrollment")){
            row = 2;
        }

        for (int i = 0; i < letterGrades.length; i++){
            if (letterGrades[i].equals(letterGrade)){
                col = i;
            }
        }

        return prepGrades[row][col];
    }

    /**
     *
     * @param listOfScores This is a parameter containing a list of double scores.
     * @return double This returns the average of the scores after being rounded
     * to the nearest hundredth.
     */
    public double calculateGPA(ArrayList<Double> listOfScores){
        double total = 0;
        int length = listOfScores.size();

        for (int i = 0; i < length; i++){
            total += listOfScores.get(i);
        }
        return Math.round((total/length) * 100) / 100.0;
    }
}