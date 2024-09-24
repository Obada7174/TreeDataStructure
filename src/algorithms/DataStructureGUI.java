package algorithms;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class TreePanel extends JPanel {
    protected RectanglePaper tree;

    public TreePanel(RectanglePaper tree) {
        this.tree = tree;
        setPreferredSize(new Dimension(600, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tree.drawTree(g, tree.root, 200, 50, 100, 50);
    }
}



class SecondPanel extends JPanel {
    JLabel instructionLabel;
    final List<SmallPaper> inorderList = new ArrayList<>();
    protected List<SmallPaper> preorderList = new ArrayList<>();
    final boolean[] done = {false};

    JTextArea nodesTextArea;  // TextArea to display entered nodes

    public SecondPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(50, 0, 600, 600);

        instructionLabel = new JLabel("Enter inorder data:");
        instructionLabel.setBounds(50, 10, 500, 30);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(instructionLabel);

        JLabel label1 = new JLabel("Value:");
        label1.setBounds(50, 50, 100, 30);
        add(label1);

        JTextField valueField = new JTextField();
        valueField.setBounds(150, 50, 150, 30);
        add(valueField);

        JLabel label2 = new JLabel("Width:");
        label2.setBounds(50, 100, 100, 30);
        add(label2);

        JTextField widthField = new JTextField();
        widthField.setBounds(150, 100, 150, 30);
        add(widthField);

        JLabel label3 = new JLabel("Height:");
        label3.setBounds(50, 150, 100, 30);
        add(label3);

        JTextField heightField = new JTextField();
        heightField.setBounds(150, 150, 150, 30);
        add(heightField);

        JButton clearButton = new JButton("Next Node");
        clearButton.setBounds(50, 200, 120, 30);
        clearButton.setBackground(Color.DARK_GRAY);
        clearButton.setForeground(Color.WHITE);
        add(clearButton);

        JButton doneButton = new JButton("Done");
        doneButton.setBounds(180, 200, 120, 30);
        doneButton.setBackground(Color.DARK_GRAY);
        doneButton.setForeground(Color.WHITE);
        add(doneButton);

        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.setBounds(310, 200, 120, 30);
        clearAllButton.setBackground(Color.DARK_GRAY);
        clearAllButton.setForeground(Color.WHITE);
        add(clearAllButton);

        JButton enterVer = new JButton("Enter |");
        enterVer.setBounds(350, 100, 80, 30);
        enterVer.setBackground(Color.DARK_GRAY);
        enterVer.setForeground(Color.WHITE);
        add(enterVer);

        JButton enterHor = new JButton("Enter _");
        enterHor.setBounds(350, 150, 80, 30);
        enterHor.setBackground(Color.DARK_GRAY);
        enterHor.setForeground(Color.WHITE);
        add(enterHor);

        JLabel doneLabel = new JLabel();
        doneLabel.setBounds(50, 250, 500, 30);
        doneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        doneLabel.setForeground(Color.DARK_GRAY);
        doneLabel.setVisible(false);
        add(doneLabel);

        nodesTextArea = new JTextArea();
        nodesTextArea.setBounds(460, 50, 200, 300);
        nodesTextArea.setFont(new Font("Arial", Font.PLAIN, 19));
        nodesTextArea.setForeground(Color.DARK_GRAY);
        nodesTextArea.setEditable(false);
        add(nodesTextArea);

        clearButton.addActionListener(e -> {
            try {
                SmallPaper s = new SmallPaper();
                s.value = valueField.getText();
                s.width = Integer.parseInt(widthField.getText());
                s.height = Integer.parseInt(heightField.getText());

                if (done[0]) {
                    preorderList.add(s);
                } else {
                    inorderList.add(s);
                }
                updateNodesTextArea();  // Update nodes text area
                valueField.setText("");
                widthField.setText("");
                heightField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for width and height.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        doneButton.addActionListener(e -> {
            if (done[0]) {
                RectanglePaper r = new RectanglePaper();
                SmallPaper[] inorder = inorderList.toArray(new SmallPaper[0]);
                SmallPaper[] preorder = preorderList.toArray(new SmallPaper[0]);
                r.buildRectanglePaper(preorder, inorder);
                doneLabel.setText("String Form Of Tree : " + r.inorderTraversal(r.root));
                doneLabel.setVisible(true);
            } else {
                done[0] = true;
                instructionLabel.setText("Enter preorder data:");
            }
        });

        clearAllButton.addActionListener(e -> {
            inorderList.clear();
            preorderList.clear();
            done[0] = false;
            instructionLabel.setText("Enter inorder data:");
            valueField.setText("");
            widthField.setText("");
            heightField.setText("");
            doneLabel.setVisible(false);
            updateNodesTextArea();
        });

        enterVer.addActionListener(e -> {
            SmallPaper s = new SmallPaper("|", 0, 0);
            if (done[0]) {
                preorderList.add(s);
            } else {
                inorderList.add(s);
            }
            updateNodesTextArea();  // Update nodes text area
        });

        enterHor.addActionListener(e -> {
            SmallPaper s = new SmallPaper("_", 0, 0);
            if (done[0]) {
                preorderList.add(s);
            } else {
                inorderList.add(s);
            }
            updateNodesTextArea();
        });
    }

    private void updateNodesTextArea() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entered Nodes : ").append("\n\n");
        if (!inorderList.isEmpty())
            sb.append("Inorder List : ").append("\n");
        for (SmallPaper node : inorderList) {
            sb.append(node.value).append("[").append(node.width).append(",").append(node.height).append("]").append("\n");
        }
        if (!preorderList.isEmpty())
            sb.append("\nPreorder List : ").append("\n");
        for (SmallPaper node : preorderList) {
            sb.append(node.value).append("[").append(node.width).append(",").append(node.height).append("]").append("\n");
        }
        nodesTextArea.setText(sb.toString());
    }
}

//________________________________________________________________________________
class ThirdPanel extends JPanel {
    JLabel instructionLabel;
    List<SmallPaper> inorderList = new ArrayList<>();
    String path = "src/algorithms/drawing";

    public ThirdPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(50, 0, 600, 600);

        instructionLabel = new JLabel("Enter inorder data:");
        instructionLabel.setBounds(50, 10, 500, 30);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(instructionLabel);

        JLabel label1 = new JLabel("Value:");
        label1.setBounds(50, 50, 100, 30);
        add(label1);

        JTextField valueField = new JTextField();
        valueField.setBounds(150, 50, 150, 30);
        add(valueField);

        JLabel label2 = new JLabel("Width:");
        label2.setBounds(50, 100, 100, 30);
        add(label2);

        JTextField widthField = new JTextField();
        widthField.setBounds(150, 100, 150, 30);
        add(widthField);

        JLabel label3 = new JLabel("Height:");
        label3.setBounds(50, 150, 100, 30);
        add(label3);

        JTextField heightField = new JTextField();
        heightField.setBounds(150, 150, 150, 30);
        add(heightField);

        JButton clearButton = new JButton("Next Node");
        clearButton.setBounds(50, 200, 120, 30);
        clearButton.setBackground(Color.DARK_GRAY);
        clearButton.setForeground(Color.WHITE);
        add(clearButton);

        JButton doneButton = new JButton("Done");
        doneButton.setBounds(180, 200, 120, 30);
        doneButton.setBackground(Color.DARK_GRAY);
        doneButton.setForeground(Color.WHITE);
        add(doneButton);

        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.setBounds(310, 200, 120, 30);
        clearAllButton.setBackground(Color.DARK_GRAY);
        clearAllButton.setForeground(Color.WHITE);
        add(clearAllButton);

        JButton enterVer = new JButton("Enter |");
        enterVer.setBounds(350, 100, 80, 30);
        enterVer.setBackground(Color.DARK_GRAY);
        enterVer.setForeground(Color.WHITE);
        add(enterVer);

        JButton enterHor = new JButton("Enter _");
        enterHor.setBounds(350, 150, 80, 30);
        enterHor.setBackground(Color.DARK_GRAY);
        enterHor.setForeground(Color.WHITE);
        add(enterHor);

        JTextArea fileContentArea = new JTextArea();
        fileContentArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        fileContentArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(fileContentArea);
        scrollPane.setBounds(50, 250, 500, 300);
        add(scrollPane);

        clearButton.addActionListener(e -> {
            SmallPaper s = new SmallPaper();
            s.value = valueField.getText();
            s.width = Integer.parseInt(widthField.getText());
            s.height = Integer.parseInt(heightField.getText());
            inorderList.add(s);
            valueField.setText("");
            widthField.setText("");
            heightField.setText("");
        });

        doneButton.addActionListener(e -> {
            SmallPaper[] inorder = inorderList.toArray(new SmallPaper[0]);
            try {
                RectanglePaper.drawRectanglePaper(inorder,"src/algorithms/drawing");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            instructionLabel.setText("Process completed!");

            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                fileContentArea.read(reader, null);
                reader.close();
            } catch (Exception ex) {
                fileContentArea.setText("Error reading file: " + ex.getMessage());
            }
        });

        JTextField filePathField = new JTextField();
        filePathField.setBounds(50, 570, 300, 30);
        add(filePathField);

        clearAllButton.addActionListener(e -> {
            inorderList.clear();
            instructionLabel.setText("Enter inorder data:");
            valueField.setText("");
            widthField.setText("");
            heightField.setText("");
            fileContentArea.setText("");
            path="src/algorithms/drawing";
            filePathField.setText("");
        });

        enterVer.addActionListener(e -> inorderList.add(new SmallPaper("|",0,0)));

        enterHor.addActionListener(e -> inorderList.add(new SmallPaper("_",0,0)));

        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.setBounds(360, 570, 120, 30);
        chooseFileButton.setBackground(Color.DARK_GRAY);
        chooseFileButton.setForeground(Color.WHITE);
        add(chooseFileButton);

        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(fileChooser.getSelectedFile().getPath());
                path=filePathField.getText();
            }
        });
    }
}

//________________________________________________________________________________
class FourthPanel extends JPanel {
     JTextField filePathField;
     JLabel resultLabel;

    public FourthPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(50, 0, 600, 600);

        JLabel instructionLabel = new JLabel("Enter the file path:");
        instructionLabel.setBounds(50, 10, 500, 30);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(instructionLabel);

        filePathField = new JTextField();
        filePathField.setBounds(50, 50, 400, 30);
        add(filePathField);

        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.setBounds(460, 50, 120, 30);
        chooseFileButton.setBackground(Color.DARK_GRAY);
        chooseFileButton.setForeground(Color.WHITE);
        add(chooseFileButton);

        JButton readButton = new JButton("Status 1");
        readButton.setBounds(50, 100, 190, 30);
        readButton.setBackground(Color.DARK_GRAY);
        readButton.setForeground(Color.WHITE);
        add(readButton);

        JButton readButton2 = new JButton("Status 2");
        readButton2.setBounds(260, 100, 190, 30);
        readButton2.setBackground(Color.DARK_GRAY);
        readButton2.setForeground(Color.WHITE);
        add(readButton2);

        JTextArea status1 = new JTextArea("---------------------------\n|        |        |        |\n---------------------------\n|           |              |\n|           |--------------\n|           |              |\n---------------------------");
        status1.setBounds(50, 150, 200, 120);
        status1.setFont(new Font("Monospaced", Font.PLAIN, 12));
        status1.setForeground(Color.DARK_GRAY);
        status1.setEditable(false);
        add(status1);

        JTextArea status2 = new JTextArea("---------------------------\n|           |              |\n|           |--------------\n|           |              |\n---------------------------\n|        |        |        |\n---------------------------");
        status2.setBounds(260, 150, 200, 120);
        status2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        status2.setForeground(Color.DARK_GRAY);
        status2.setEditable(false);
        add(status2);

        resultLabel = new JLabel("");
        resultLabel.setBounds(50, 280, 500, 300);
        resultLabel.setVerticalAlignment(SwingConstants.TOP);
        resultLabel.setForeground(Color.DARK_GRAY);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(resultLabel);

        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(fileChooser.getSelectedFile().getPath());
            }
        });

        readButton.addActionListener(e -> {
            try {

                String filePath = filePathField.getText();
                if (filePath.isEmpty()) {
                    throw new IOException("File path is empty");
                }

                File file = new File(filePath);
                if (!file.exists()) {
                    throw new FileNotFoundException("File not found at the specified path " );
                }

                SmallPaper[] inorder = RectanglePaper.readRectangle(filePath);
                StringBuilder result = new StringBuilder("<html>Inorder: <br/><br/>");
                for (SmallPaper paper : inorder) {
                    result.append(paper.value).append("[").append(paper.width).append(",").append(paper.height).append("]").append(" , ");
                }
                result.append("</html>");
                resultLabel.setText(result.toString());
            } catch (FileNotFoundException ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            } catch (IOException ex) {
                resultLabel.setText("I/O Error: " + ex.getMessage());
            } catch (Exception ex) {
                resultLabel.setText("An unexpected error occurred: " + ex.getMessage());
            }
        });

        readButton2.addActionListener(e -> {
            try {
                String filePath = filePathField.getText();
                if (filePath.isEmpty()) {
                    throw new IOException("File path is empty");
                }

                File file = new File(filePath);
                if (!file.exists()) {
                    throw new FileNotFoundException("File not found at the specified path " );
                }

                SmallPaper[] inorder = RectanglePaper.readRectangle2(filePath);
                StringBuilder result = new StringBuilder("<html>Inorder: <br/><br/>");
                for (SmallPaper paper : inorder) {
                    result.append(paper.value).append("[").append(paper.width).append(",").append(paper.height).append("]").append(" , ");
                }
                result.append("</html>");
                resultLabel.setText(result.toString());
            } catch (FileNotFoundException ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            } catch (IOException ex) {
                resultLabel.setText("I/O Error: " + ex.getMessage());
            } catch (Exception ex) {
                resultLabel.setText("An unexpected error occurred: " + ex.getMessage());
            }
        });
    }
}


class FifthPanel extends JPanel {
    JTextField valueField;
    JTextField widthField;
    JTextField heightField;
    JLabel resultLabel;
    JTextArea fileContentArea;
    List<SmallPaper> paperList = new ArrayList<>();

    public FifthPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(50, 0, 600, 600);
        setVisible(false);

        JLabel instructionLabel = new JLabel("Enter paper data:");
        instructionLabel.setBounds(50, 10, 200, 30);
        add(instructionLabel);

        JLabel valueLabel = new JLabel("Value:");
        valueLabel.setBounds(50, 50, 100, 30);
        add(valueLabel);

        valueField = new JTextField();
        valueField.setBounds(150, 50, 150, 30);
        add(valueField);

        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setBounds(50, 100, 100, 30);
        add(widthLabel);

        widthField = new JTextField();
        widthField.setBounds(150, 100, 150, 30);
        add(widthField);

        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setBounds(50, 150, 100, 30);
        add(heightLabel);

        heightField = new JTextField();
        heightField.setBounds(150, 150, 150, 30);
        add(heightField);

        JButton saveButton = new JButton("Save Paper");
        saveButton.setBounds(50, 200, 120, 30);
        saveButton.setBackground(Color.DARK_GRAY);
        saveButton.setForeground(Color.WHITE);
        add(saveButton);

        JButton countRectanglesButton = new JButton("Count Rectangles");
        countRectanglesButton.setBounds(180, 200, 150, 30);
        countRectanglesButton.setBackground(Color.DARK_GRAY);
        countRectanglesButton.setForeground(Color.WHITE);
        add(countRectanglesButton);

        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.setBounds(340, 200, 120, 30);
        clearAllButton.setBackground(Color.DARK_GRAY);
        clearAllButton.setForeground(Color.WHITE);
        add(clearAllButton);

        resultLabel = new JLabel("");
        resultLabel.setBounds(50, 250, 500, 30);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 13));
        resultLabel.setForeground(Color.DARK_GRAY);
        add(resultLabel);

        fileContentArea = new JTextArea();
        fileContentArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        fileContentArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fileContentArea);
        scrollPane.setBounds(50, 290, 500, 300);
        add(scrollPane);

        saveButton.addActionListener(e -> {
            SmallPaper paper = new SmallPaper();
            paper.value = valueField.getText();
            paper.width = Integer.parseInt(widthField.getText());
            paper.height = Integer.parseInt(heightField.getText());
            paperList.add(paper);
            valueField.setText("");
            widthField.setText("");
            heightField.setText("");
        });

        countRectanglesButton.addActionListener(e -> {
            SmallPaper[] papers = paperList.toArray(new SmallPaper[0]);
            boolean canFormRectangle = RectanglePaper.canFormRectangle(papers);
            if (canFormRectangle) {
                try {
                    resultLabel.setText("<html>All Nodes Can form rectangle <br/>The Nodes Can form "+RectanglePaper.countAllPossibleRectangles(papers)+" rectangles </html>");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                SmallPaper[] orderedPapers = RectanglePaper.arrangePapers(papers);
                try {
                    RectanglePaper.drawRectanglePaper(orderedPapers, "src/algorithms/drawing");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("src/algorithms/drawing"));
                    fileContentArea.read(reader, null);
                    reader.close();
                } catch (Exception ex) {
                    fileContentArea.setText("Error reading file: " + ex.getMessage());
                }

            } else {
                try {
                    resultLabel.setText("<html>All Nodes Cannot form rectangle <br/>The Nodes Can form "+RectanglePaper.countAllPossibleRectangles(papers)+" rectangles </html>");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        clearAllButton.addActionListener(e -> {
            paperList.clear();
            valueField.setText("");
            widthField.setText("");
            heightField.setText("");
            resultLabel.setText("");
            fileContentArea.setText("");
        });

    }
}

class SeventhPanel extends JPanel {
    JTextField filePathField;
    JTextArea fileContentArea;

    public SeventhPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(50, 0, 600, 600);

        JLabel instructionLabel = new JLabel("Choose a file to swap dimensions:");
        instructionLabel.setBounds(50, 10, 500, 30);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(instructionLabel);

        filePathField = new JTextField();
        filePathField.setBounds(50, 50, 300, 30);
        add(filePathField);

        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.setBounds(360, 50, 120, 30);
        chooseFileButton.setBackground(Color.DARK_GRAY);
        chooseFileButton.setForeground(Color.WHITE);
        add(chooseFileButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(490, 50, 80, 30);
        clearButton.setBackground(Color.DARK_GRAY);
        clearButton.setForeground(Color.WHITE);
        add(clearButton);

        fileContentArea = new JTextArea();
        fileContentArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        fileContentArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fileContentArea);
        scrollPane.setBounds(50, 100, 520, 400);
        add(scrollPane);

        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getPath();
                filePathField.setText(filePath);
                try {
                    RectanglePaper.swapDimension(filePath);
                    BufferedReader reader = new BufferedReader(new FileReader(filePath));
                    fileContentArea.read(reader, null);
                    reader.close();
                } catch (Exception ex) {
                    fileContentArea.setText("Error reading file: " + ex.getMessage());
                }
            }
        });

        clearButton.addActionListener(e -> {
            filePathField.setText("");
            fileContentArea.setText("");
        });
    }
}

class SixthPanel extends JPanel {
    JLabel instructionLabel;
    List<SmallPaper> inorderList = new ArrayList<>();

    public SixthPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(50, 0, 600, 600);

        instructionLabel = new JLabel("Enter inorder data:");
        instructionLabel.setBounds(50, 10, 500, 30);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(instructionLabel);

        JLabel label1 = new JLabel("Value:");
        label1.setBounds(50, 50, 100, 30);
        add(label1);

        JTextField valueField = new JTextField();
        valueField.setBounds(150, 50, 150, 30);
        add(valueField);

        JLabel label2 = new JLabel("Width:");
        label2.setBounds(50, 100, 100, 30);
        add(label2);

        JTextField widthField = new JTextField();
        widthField.setBounds(150, 100, 150, 30);
        add(widthField);

        JLabel label3 = new JLabel("Height:");
        label3.setBounds(50, 150, 100, 30);
        add(label3);

        JTextField heightField = new JTextField();
        heightField.setBounds(150, 150, 150, 30);
        add(heightField);

        JButton clearButton = new JButton("Next Node");
        clearButton.setBounds(50, 200, 120, 30);
        clearButton.setBackground(Color.DARK_GRAY);
        clearButton.setForeground(Color.WHITE);
        add(clearButton);

        JButton doneButton = new JButton("Done");
        doneButton.setBounds(180, 200, 120, 30);
        doneButton.setBackground(Color.DARK_GRAY);
        doneButton.setForeground(Color.WHITE);
        add(doneButton);

        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.setBounds(310, 200, 120, 30);
        clearAllButton.setBackground(Color.DARK_GRAY);
        clearAllButton.setForeground(Color.WHITE);
        add(clearAllButton);

        JButton enterVer = new JButton("Enter |");
        enterVer.setBounds(350, 100, 80, 30);
        enterVer.setBackground(Color.DARK_GRAY);
        enterVer.setForeground(Color.WHITE);
        add(enterVer);

        JButton enterHor = new JButton("Enter _");
        enterHor.setBounds(350, 150, 80, 30);
        enterHor.setBackground(Color.DARK_GRAY);
        enterHor.setForeground(Color.WHITE);
        add(enterHor);

        JTextArea fileContentArea = new JTextArea();
        fileContentArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        fileContentArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(fileContentArea);
        scrollPane.setBounds(50, 250, 500, 300);
        add(scrollPane);

        clearButton.addActionListener(e -> {
            SmallPaper s = new SmallPaper();
            s.value = valueField.getText();
            s.width = Integer.parseInt(widthField.getText());
            s.height = Integer.parseInt(heightField.getText());
            inorderList.add(s);
            valueField.setText("");
            widthField.setText("");
            heightField.setText("");
        });

        doneButton.addActionListener(e -> {
            SmallPaper[] inorder = inorderList.toArray(new SmallPaper[0]);
            RectanglePaper.swapDimension(inorder);
            instructionLabel.setText("Process completed!");
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/algorithms/drawing"));
                fileContentArea.read(reader, null);
                reader.close();
            } catch (Exception ex) {
                fileContentArea.setText("Error reading file: " + ex.getMessage());
            }
        });

        clearAllButton.addActionListener(e -> {
            inorderList.clear();
            instructionLabel.setText("Enter inorder data:");
            valueField.setText("");
            widthField.setText("");
            heightField.setText("");
            fileContentArea.setText("");
        });

        enterVer.addActionListener(e -> inorderList.add(new SmallPaper("|",0,0)));

        enterHor.addActionListener(e -> inorderList.add(new SmallPaper("_",0,0)));
        
    }
}


//________________________________________________________________________________
public class DataStructureGUI extends JFrame {
     JPanel mainPanel;
     JPanel sideBar;
     JPanel buildTreePanel;
     JPanel secondPanel;
     JPanel thirdPanel;
     JPanel fourthPanel;
     FifthPanel fifthPanel;
     SixthPanel sixthPanel;
     SeventhPanel SeventhPanel;

    private JTextField inputTextField;
    private JLabel inorderLabel;
    private JLabel preorderLabel;
    private TreePanel treePanel;
    private JPanel treePanelWrapper;

    public DataStructureGUI() {
        setTitle("Data Structure Manager");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        sideBar = new JPanel();
        sideBar.setLayout(null);
        sideBar.setBounds(0, 0, 150, 700);
        sideBar.setBackground(Color.LIGHT_GRAY);

        JButton buildTreeButton = new JButton("String to Tree");
        buildTreeButton.setBounds(10, 50, 130, 30);
        buildTreeButton.setBackground(Color.DARK_GRAY);
        buildTreeButton.setForeground(Color.WHITE);

        JButton inputDataButton = new JButton("Tree to String");
        inputDataButton.setBounds(10, 100, 130, 30);
        inputDataButton.setBackground(Color.DARK_GRAY);
        inputDataButton.setForeground(Color.WHITE);

        JButton thirdPanelButton = new JButton("Draw Rectangle");
        thirdPanelButton.setBounds(10, 150, 130, 30);
        thirdPanelButton.setBackground(Color.DARK_GRAY);
        thirdPanelButton.setForeground(Color.WHITE);

        JButton fourthPanelButton = new JButton("Read File");
        fourthPanelButton.setBounds(10, 200, 130, 30);
        fourthPanelButton.setBackground(Color.DARK_GRAY);
        fourthPanelButton.setForeground(Color.WHITE);

        JButton fifthPanelButton = new JButton("Nodes to Rect");
        fifthPanelButton.setBounds(10, 250, 130, 30);
        fifthPanelButton.setBackground(Color.DARK_GRAY);
        fifthPanelButton.setForeground(Color.WHITE);

        JButton SixthPanelButton = new JButton("Swap Rectangle");
        SixthPanelButton.setBounds(10, 300, 130, 30);
        SixthPanelButton.setBackground(Color.DARK_GRAY);
        SixthPanelButton.setForeground(Color.WHITE);

        JButton SeventhPanelButton = new JButton("Swap Rectangle2");
        SeventhPanelButton.setBounds(10, 350, 130, 30);
        SeventhPanelButton.setBackground(Color.DARK_GRAY);
        SeventhPanelButton.setForeground(Color.WHITE);

        sideBar.add(SixthPanelButton);
        sideBar.add(SeventhPanelButton);
        sideBar.add(fifthPanelButton);
        sideBar.add(buildTreeButton);
        sideBar.add(inputDataButton);
        sideBar.add(thirdPanelButton);
        sideBar.add(fourthPanelButton);

        mainPanel = new JPanel(null);
        mainPanel.setBounds(150, 0, 750, 700);
        mainPanel.setBackground(Color.WHITE);

        buildTreePanel = createFirstPanel();
        secondPanel = new SecondPanel();
        thirdPanel = new ThirdPanel();
        fourthPanel = new FourthPanel();
        fifthPanel = new FifthPanel();
        SeventhPanel = new SeventhPanel();
        sixthPanel = new SixthPanel();

        mainPanel.add(buildTreePanel);
        mainPanel.add(secondPanel);
        mainPanel.add(thirdPanel);
        mainPanel.add(fourthPanel);
        mainPanel.add(fifthPanel);
        mainPanel.add(SeventhPanel);
        mainPanel.add(sixthPanel);

        getContentPane().add(sideBar);
        getContentPane().add(mainPanel);

        buildTreeButton.addActionListener(e -> showPanel(buildTreePanel));

        inputDataButton.addActionListener(e -> showPanel(secondPanel));

        thirdPanelButton.addActionListener(e -> showPanel(thirdPanel));

        fourthPanelButton.addActionListener(e -> showPanel(fourthPanel));

        fifthPanelButton.addActionListener(e -> showPanel(fifthPanel));

        SixthPanelButton.addActionListener(e -> showPanel(sixthPanel));

        SeventhPanelButton.addActionListener(e -> showPanel(SeventhPanel));

        showPanel(buildTreePanel);
    }

    private JPanel createFirstPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(50, 0, 600, 600);


        JLabel inputLabel = new JLabel("Enter tree data:");
        inputLabel.setBounds(50, 50, 200, 30);

        inputTextField = new JTextField();
        inputTextField.setBounds(50, 100, 300, 30);

        JButton buildButton = new JButton("Build Tree");
        buildButton.setBounds(50, 150, 200, 30);
        buildButton.setBackground(Color.DARK_GRAY);
        buildButton.setForeground(Color.WHITE);

        inorderLabel = new JLabel("Inorder: ");
        inorderLabel.setBounds(50, 200, 550, 30);

        preorderLabel = new JLabel("Preorder: ");
        preorderLabel.setBounds(50, 250, 550, 30);

        buildButton.addActionListener(e -> {
            String inputData = inputTextField.getText();
            RectanglePaper t = new RectanglePaper();
            t.buildTree(inputData);
            inorderLabel.setText("Inorder: " + t.printInorder());
            preorderLabel.setText("Preorder: " + t.printPreorder());

            if (treePanel != null) {
                treePanelWrapper.remove(treePanel);
            }

            treePanel = new TreePanel(t);
            treePanel.setBounds(0, 0, 700, 400);
            treePanelWrapper.add(treePanel);
            treePanelWrapper.revalidate();
            treePanelWrapper.repaint();
        });

        panel.add(inputLabel);
        panel.add(inputTextField);
        panel.add(buildButton);
        panel.add(inorderLabel);
        panel.add(preorderLabel);

        treePanelWrapper = new JPanel();
        treePanelWrapper.setLayout(null);
        treePanelWrapper.setBounds(50, 300, 550, 320);
        treePanelWrapper.setBackground(Color.WHITE);
        panel.add(treePanelWrapper);

        return panel;
    }

    private void showPanel(JPanel panel) {
        buildTreePanel.setVisible(false);
        secondPanel.setVisible(false);
        thirdPanel.setVisible(false);
        fourthPanel.setVisible(false);
        fifthPanel.setVisible(false);
        sixthPanel.setVisible(false);
        SeventhPanel.setVisible(false);

        panel.setVisible(true);
    }
}
