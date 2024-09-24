package algorithms;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class RectanglePaper {
    SmallPaper root;

    public RectanglePaper() {
        this.root = null;
    }

    public RectanglePaper(SmallPaper root) {
        this.root = root;
    }


    public  void buildRectanglePaper(SmallPaper[] preorder, SmallPaper[] inorder) {
        this.root = this.buildTreeRecursive(preorder, inorder, 0, 0, inorder.length - 1);
    }

    private SmallPaper buildTreeRecursive(SmallPaper[] preorder, SmallPaper[] inorder, int preIndex, int inStart, int inEnd) {
        if (inStart > inEnd) {
            return null;
        } else {
            SmallPaper root = preorder[preIndex];
            int rootIndex = 0;

            int i;
            for (i = inStart; i <= inEnd; ++i) {
                if (inorder[i].value .equals( root.value)) {
                    rootIndex = i;
                    break;
                }
            }

            i = rootIndex - inStart;
            root.left = this.buildTreeRecursive(preorder, inorder, preIndex + 1, inStart, rootIndex - 1);
            root.right = this.buildTreeRecursive(preorder, inorder, preIndex + i + 1, rootIndex + 1, inEnd);
            return root;
        }
    }

    public String printPreorder() {
        return this.getPreorderRecursive(this.root);
    }

    private String getPreorderRecursive(SmallPaper current) {
        StringBuilder sb = new StringBuilder();
        if (current != null) {
            sb.append(current.value).append(" ");
            sb.append(this.getPreorderRecursive(current.left));
            sb.append(this.getPreorderRecursive(current.right));
        }
        return sb.toString();
    }


    public String printInorder() {
        StringBuilder result = new StringBuilder();
        this.getInorderRecursive(this.root, result);
        return result.toString().trim();
    }

    private void getInorderRecursive(SmallPaper current, StringBuilder result) {
        if (current != null) {
            this.getInorderRecursive(current.left, result);
            result.append(current.value).append(" ");
            this.getInorderRecursive(current.right, result);
        }
    }

    public String inorderTraversal(SmallPaper root) {
        StringBuilder sb = new StringBuilder();
        return this.inorder(root, sb);
    }

    private String inorder(SmallPaper root, StringBuilder sb) {
        if (root == null) {
            return sb.toString();
        } else if (!(root.value.equals("|")) && !(root.value.equals("_"))) {
            sb.append(root.value);
            sb.append("[");
            sb.append(root.width);
            sb.append(",");
            sb.append(root.height);
            sb.append("]");
            return sb.toString();
        } else {
            sb.append("(");
            this.inorder(root.left, sb);
            sb.append(root.value);
            this.inorder(root.right, sb);
            sb.append(")");
            return sb.toString();
        }
    }

    public void buildTree(String s) {
        if (s == null || s.length() == 0)
            return;

        Stack<SmallPaper> stack = new Stack<SmallPaper>();

        StringBuilder value = new StringBuilder();
        StringBuilder dimension = new StringBuilder();
        int width = 0;
        int height = 0;
        boolean isParsingDimension = false;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                continue;
            } else if (c == ')') {
                SmallPaper right = stack.pop();
                SmallPaper operator = stack.pop();
                SmallPaper left = stack.pop();
                operator.left = left;
                operator.right = right;
                stack.push(operator);

            } else if (c == '[') {
                isParsingDimension = true;
            } else if (c == ']') {
                isParsingDimension = false;
                String[] dim = dimension.toString().split(",");
                width = Integer.parseInt(dim[0]);
                height = Integer.parseInt(dim[1]);
                dimension.setLength(0);
                stack.push(new SmallPaper(value.toString(), width, height));
                value.setLength(0);
            } else if (c == '|' || c == '_') {
                stack.push(new SmallPaper(String.valueOf(c), 0, 0));
            } else {
                if (isParsingDimension) {
                    dimension.append(c);
                } else {
                    value.append(c);
                }
            }
        }
        this.root = stack.isEmpty() ? null : stack.pop();
    }

    //________________________________________________________________________________________________________________________________
    public static void drawRectanglePaper(SmallPaper[] inOrder, String filePath) throws IOException {
        if (inOrder.length>1 && inOrder[1].value.equals("_")){
            drawSwapRectanglePaperToFile(inOrder,filePath);
        }else {
            drawRectanglePaperToFile(inOrder,filePath);
        }
    }



        //________________________________________________________________________________________________________________________________
    public static void drawSwapRectanglePaperToFile(SmallPaper[] inOrder, String filePath) throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int heightFirstColumn=0;
        int lineNumber = 0;
        int columnNumber = 1;
        boolean b=false;
        for (int i = 0; i < inOrder.length; i++) {
            if (inOrder[i].value.equals("|") || inOrder[i].value.equals("_")) {
                continue;
            } else if (i == 0) {
                drawSwapRectanglePaperToFile1(inOrder[i], filePath);
                lineNumber += 1;
                heightFirstColumn+=inOrder[i].height;
            } else if (inOrder[i - 1].value.equals("|") && columnNumber == 1) {
//                if (inOrder[i].height==heightFirstColumn || inOrder[i].height==heightFirstColumn-inOrder[i-2].height)
                inOrder[i].height+=1;
                drawRectanglePaperToFile2(inOrder[i], filePath, lineNumber);
                columnNumber++;
                lineNumber += inOrder[i].height + 2;
                if (inOrder[i].height-1==heightFirstColumn) {
                    b = true;
                    lineNumber=heightFirstColumn+4;
                }
            } else if (inOrder[i - 1].value.equals("_")) {
                if (columnNumber == 1 || b) {
                    drawSwapRectanglePaperToFile3(inOrder[i], filePath);
                    heightFirstColumn+=inOrder[i].height;
                    b=false;
                } else {
                    drawSwapRectanglePaperToFile4(inOrder[i], filePath, lineNumber);
                }
            } else {
//                if (inOrder[i-1].value.equals("|") && inOrder[i-1].height==inOrder[i].height) {
//                    lineNumber = heightFirstColumn;
//                }
                drawSwapRectanglePaperToFile4(inOrder[i], filePath, lineNumber);
            }
        }
    }

    //________________________________________________________________________________________________________________________________
    //drawSwapRectanglePaperToFile drawSwapRectanglePaperToFile drawSwapRectanglePaperToFile drawSwapRectanglePaperToFile
    //----------------------------------------------------------------
    private static void drawSwapRectanglePaperToFile3(SmallPaper paper, String filePath) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            //----------------------------------------------------------------
            for (int j = 0; j < paper.height ; j++) {
                if (j == paper.height / 2) {
                    writer.write("|");
                    for (int n = 0; n < paper.width; n++) {
                        if (n == paper.width / 2) {
                            writer.write(" " + paper.value + " ");
                        } else {
                            writer.write("   ");
                        }
                    }
                    writer.write("|");
                    writer.write("\n");
                } else {
                    writer.write("|");
                    for (int k = 0; k < paper.width; k++)
                        writer.write("   ");
                    writer.write("|");
                    writer.write("\n");
                }
            }

            //----------------------------------------------------------------
            writer.write(" ");
            for (int i = 0; i < paper.width; i++)
                writer.write("---");
            writer.write(" \n");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawSwapRectanglePaperToFile4(SmallPaper paper, String filePath, int lineN) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<StringBuilder> lines = new ArrayList<StringBuilder>();
            String line;
            String width = "";
            while ((line = reader.readLine()) != null) {
                lines.add(new StringBuilder(line));
            }
            reader.close();

            for (int i = lineN-1; i < lineN + paper.height ; i++) {
                if (i == (lineN + paper.height -1)) {
                    width += " ";
                    for (int j = 0; j < paper.width; j++)
                        width += "---";
                } else {

                    for (int j = 0; j < paper.width; j++) {
                        if (i == lineN + paper.height / 2) {
                            if (j == paper.width / 2) {
                                width += " " + paper.value + " ";
                            } else {
                                width += "   ";
                            }
                            if (j == (paper.width - 1))
                                width += " |";
                        } else {
                            width += "   ";
                            if (j == (paper.width - 1))
                                width += " |";
                        }

                    }
                }
                lines.get(i).append(width);
                width = "";
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (StringBuilder updatedLine : lines) {
                writer.write(String.valueOf(updatedLine));
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawSwapRectanglePaperToFile1(SmallPaper paper, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(" ");
            for (int i = 0; i < paper.width; i++)
                writer.write("---");
            writer.write("\n");
            //----------------------------------------------------------------
            for (int j = 0; j < paper.height; j++) {
                if (j == paper.height / 2) {
                    writer.write("|");
                    for (int n = 0; n < paper.width; n++) {
                        if (n == paper.width / 2) {
                            writer.write(" " + paper.value + " ");
                        } else {
                            writer.write("   ");
                        }
                    }
                    writer.write("|");
                    writer.write("\n");
                } else {
                    writer.write("|");
                    for (int k = 0; k < paper.width; k++)
                        writer.write("   ");
                    writer.write("|");
                    writer.write("\n");
                }
            }

            //----------------------------------------------------------------
            writer.write(" ");
            for (int i = 0; i < paper.width; i++)
                writer.write("---");
            writer.write(" \n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //________________________________________________________________________________________________________________________________
// Start drawing Start drawing Start drawing Start drawing Start drawing Start drawing Start drawing
    public static void drawRectanglePaperToFile(SmallPaper[] inOrder, String filePath) throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        boolean status2 = false;
        int lineNumber = 0;
        for (int i = 0; i < inOrder.length; i++) {
//            if (i > 5 && (inOrder[i-6].height+inOrder[i-4].height==inOrder[i].height && inOrder[i-5].value.equals("_") && inOrder[i-3].value.equals("_"))){
//                lineNumber=1;
//                status2=true;
//            }
            if (inOrder[i].value.equals("|") || inOrder[i].value.equals("_")) {
                continue;
            } else if (i == 0) {
                drawRectanglePaperToFile1(inOrder[i], filePath);
                lineNumber += 1;
            } else if (inOrder[i - 1].value.equals("|") && lineNumber == 1 ) {
//                if (status2)
//                    inOrder[i].height++;
                drawRectanglePaperToFile2(inOrder[i], filePath, lineNumber);
            } else if (inOrder[i - 1].value.equals("_")) {
                if ( inOrder[i - 2 - 2].height == (inOrder[i - 2].height + inOrder[i].height)) {
                    drawRectanglePaperToFile4(inOrder[i], filePath, lineNumber);
                    lineNumber += inOrder[i].height + 1;
                } else {
                    drawRectanglePaperToFile3(inOrder[i], filePath);
                    lineNumber += inOrder[i - 1].height + 5 * (inOrder[i].height / 25) + 1;
                }
            } else {
                drawRectanglePaperToFile4(inOrder[i], filePath, lineNumber);
                lineNumber += inOrder[i].height + 1;
            }
        }
    }
//________________________________________________________________________________________________________________________________
    // first draw

    private static void drawRectanglePaperToFile1(SmallPaper paper, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(" ");
            for (int i = 0; i < paper.width; i++)
                writer.write("---");
            writer.write("\n");
            //----------------------------------------------------------------
            for (int j = 0; j < paper.height; j++) {
                if (j == paper.height / 2) {
                    writer.write("|");
                    for (int n = 0; n < paper.width; n++) {
                        if (n == paper.width / 2) {
                            writer.write(" " + paper.value + " ");
                        } else {
                            writer.write("   ");
                        }
                    }
                    writer.write("|");
                    writer.write("\n");
                } else {
                    writer.write("|");
                    for (int k = 0; k < paper.width; k++)
                        writer.write("   ");
                    writer.write("|");
                    writer.write("\n");
                }
            }

            //----------------------------------------------------------------
            writer.write(" ");
            for (int i = 0; i < paper.width; i++)
                writer.write("---");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //________________________________________________________________________________
    //second function
    private static void drawRectanglePaperToFile2(SmallPaper paper, String filePath, int lineN) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<StringBuilder> lines = new ArrayList<StringBuilder>();
            String line;
            String width = "";
            while ((line = reader.readLine()) != null) {
                lines.add(new StringBuilder(line));
            }
            reader.close();

            for (int i = lineN - 1; i < (lineN + paper.height + 1); i++) {
                if (i == (lineN - 1) || i == (lineN + paper.height)) {
                    width += " ";
                    for (int j = 0; j < paper.width; j++)
                        width += "---";
                } else {
                    for (int j = 0; j < paper.width; j++) {
                        if (i == (paper.height / 2 + 1)) {
                            if (j == paper.width / 2) {
                                width += " " + paper.value + " ";
                            } else {
                                width += "   ";
                            }
                        } else {
                            width += "   ";
                        }
                        if (j == (paper.width - 1))
                            width += "|";

                    }
                }
                lines.get(i).append(width);
                width = "";
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (StringBuilder updatedLine : lines) {
                writer.write(String.valueOf(updatedLine));
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //________________________________________________________________
    //3rd function

    private static void drawRectanglePaperToFile3(SmallPaper paper, String filePath) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            //----------------------------------------------------------------
            for (int j = 0; j < paper.height + 1; j++) {
                if (j == paper.height / 2) {
                    writer.write("|");
                    for (int n = 0; n < paper.width; n++) {
                        if (n == paper.width / 2) {
                            writer.write(" " + paper.value + " ");
                        } else {
                            writer.write("   ");
                        }
                    }
                    writer.write("|");
                    writer.write("\n");
                } else {
                    writer.write("|");
                    for (int k = 0; k < paper.width; k++)
                        writer.write("   ");
                    writer.write("|");
                    writer.write("\n");
                }
            }

            //----------------------------------------------------------------
            writer.write(" ");
            for (int i = 0; i < paper.width; i++)
                writer.write("---");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //________________________________________________________________
// 4th function  4th function  4th function  4th function  4th function
    private static void drawRectanglePaperToFile4(SmallPaper paper, String filePath, int lineN) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<StringBuilder> lines = new ArrayList<StringBuilder>();
            String line;
            String width = "";
            while ((line = reader.readLine()) != null) {
                lines.add(new StringBuilder(line));
            }
            reader.close();

            for (int i = lineN; i < lineN + paper.height + 1; i++) {
                if (i == (lineN + paper.height)) {
                    width += " ";
                    for (int j = 0; j < paper.width; j++)
                        width += "---";
                } else {

                    for (int j = 0; j < paper.width; j++) {
                        if (i == lineN + paper.height / 2) {
                            if (j == paper.width / 2) {
                                width += " " + paper.value + " ";
                            } else {
                                width += "   ";
                            }
                            if (j == (paper.width - 1))
                                width += " |";
                        } else {
                            width += "   ";
                            if (j == (paper.width - 1))
                                width += " |";
                        }

                    }
                }
                lines.get(i).append(width);
                width = "";
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (StringBuilder updatedLine : lines) {
                writer.write(String.valueOf(updatedLine));
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////The end of drawing The end of drawing The end of drawing The end of drawing The end of drawing
//----------------------------------------------------------------------------------------
    public static SmallPaper[] readRectangle(String filePath) {

        List<SmallPaper> inorderList = new ArrayList<SmallPaper>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            String[] width_left = lines.get(0).split(" ");
            String[] values = new String[width_left.length+1];
            int height_left = 0;
            int i = 1;
            int j = 0;

            while (!(lines.get(i).contains("-"))) {
                lines.get(i).replace(" ", "");
                char[] returnValues = lines.get(i).toCharArray();
                height_left++;
                for (char c : returnValues) {
                    if (Character.isLetter(c)) {
                        values[j] = String.valueOf(c);
                        j++;
                    }
                }
                i++;
            }
            for (int k = 1; k < width_left.length; k++) {
                inorderList.add(new SmallPaper(values[k - 1], width_left[k].length() / 3, height_left));
                if (k < width_left.length - 1)
                    inorderList.add(new SmallPaper("|", 0, 0));
            }
            if (i== lines.size() - 1)
                return inorderList.toArray(new SmallPaper[0]);
            inorderList.add(new SmallPaper("_", 0, 0));
            i++;

            int[] height_right = {0, 0, 0};
            String letter0 = "";
            String letter2 = "";
            String letter1 = "";
            while (!(lines.get(i).contains("-"))) {
                lines.get(i).replace(" ", "");
                height_right[1]++;
                char[] letterFinder = lines.get(i).toCharArray();
                for (char c : letterFinder) {
                    if (Character.isLetter(c) && letter1.equals("")) {
                        letter1 = String.valueOf(c);
                        continue;
                    }
                    if (Character.isLetter(c) && !letter1.equals("")) {
                        letter0 = String.valueOf(c);
                        continue;
                    }
                }
                i++;
            }
            char[] width2 = lines.get(i).toCharArray();
            int width_2 = 0;
            for (char c : width2) {
                if (String.valueOf(c).equals("-"))
                    width_2++;
            }
            width_2 = width_2 / 3;
            i++;
            while (!(lines.get(i).contains("-"))) {
                lines.get(i).replace(" ", "");
                height_right[2]++;
                char[] letterFinder = lines.get(i).toCharArray();
                for (char c : letterFinder) {
                    if (Character.isLetter(c) && letter0.equals("")) {
                        letter0 = String.valueOf(c);
                        continue;
                    }
                    if (Character.isLetter(c) && !letter0.equals("")) {
                        letter2 = String.valueOf(c);
                        continue;
                    }
                }
                i++;
            }
            height_right[0] = height_right[1] + height_right[2];

            char[] width = lines.get(i).toCharArray();
            int totalWidth = 0;
            for (char c : width) {
                if (String.valueOf(c).equals("-"))
                    totalWidth++;
            }
            totalWidth = totalWidth / 3;
            inorderList.add(new SmallPaper(letter0, totalWidth - width_2, height_right[0]));
            inorderList.add(new SmallPaper("|", 0, 0));
            inorderList.add(new SmallPaper(letter1, width_2, height_right[1]));
            inorderList.add(new SmallPaper("_", 0, 0));
            inorderList.add(new SmallPaper(letter2, width_2, height_right[2]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SmallPaper[] smallPapersArray = inorderList.toArray(new SmallPaper[0]);
        return smallPapersArray;
    }

    public static SmallPaper[] readRectangle2(String filePath) {

        List<SmallPaper> inorderList = new ArrayList<SmallPaper>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            String[] width_left = lines.get(lines.size()-1).split(" ");
            String[] values = new String[width_left.length+1];
            int height_left = 0;
            int i = 1;
            int j = 0;

            //________________________________________________________________
            int[] height_right = {0, 0, 0};
            String letter0 = "";
            String letter2 = "";
            String letter1 = "";
            while (!(lines.get(i).contains("-"))) {
                lines.get(i).replace(" ", "");
                height_right[1]++;
                char[] letterFinder = lines.get(i).toCharArray();
                for (char c : letterFinder) {
                    if (Character.isLetter(c) && letter1.equals("")) {
                        letter1 = String.valueOf(c);
                        continue;
                    }
                    if (Character.isLetter(c) && !letter1.equals("")) {
                        letter0 = String.valueOf(c);
                        continue;
                    }
                }
                i++;
            }
            char[] width2 = lines.get(i).toCharArray();
            int width_2 = 0;
            for (char c : width2) {
                if (String.valueOf(c).equals("-"))
                    width_2++;
            }
            width_2 = width_2 / 3;
            i++;
            while (!(lines.get(i).contains("-"))) {
                lines.get(i).replace(" ", "");
                height_right[2]++;
                char[] letterFinder = lines.get(i).toCharArray();
                for (char c : letterFinder) {
                    if (Character.isLetter(c) && letter0.equals("")) {
                        letter0 = String.valueOf(c);
                        continue;
                    }
                    if (Character.isLetter(c) && !letter0.equals("")) {
                        letter2 = String.valueOf(c);
                        continue;
                    }
                }
                i++;
            }
            height_right[0] = height_right[1] + height_right[2];

            char[] width = lines.get(i).toCharArray();
            int totalWidth = 0;
            for (char c : width) {
                if (String.valueOf(c).equals("-"))
                    totalWidth++;
            }
            totalWidth = totalWidth / 3;
            inorderList.add(new SmallPaper(letter0, totalWidth - width_2, height_right[0]));
            inorderList.add(new SmallPaper("|", 0, 0));
            inorderList.add(new SmallPaper(letter1, width_2, height_right[1]));
            inorderList.add(new SmallPaper("_", 0, 0));
            inorderList.add(new SmallPaper(letter2, width_2, height_right[2]));
            //++++
            if (i== lines.size() - 1)
                return inorderList.toArray(new SmallPaper[0]);
            inorderList.add(new SmallPaper("_", 0, 0));
            i++;

            while (!(lines.get(i).contains("-"))) {
                lines.get(i).replace(" ", "");
                char[] returnValues = lines.get(i).toCharArray();
                height_left++;
                for (char c : returnValues) {
                    if (Character.isLetter(c)) {
                        values[j] = String.valueOf(c);
                        j++;
                    }
                }
                i++;
            }
            for (int k = 1; k < width_left.length-1; k++) {
                inorderList.add(new SmallPaper(values[k - 1], width_left[k].length() / 3, height_left));
                if (k < width_left.length - 2)
                    inorderList.add(new SmallPaper("|", 0, 0));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        SmallPaper[] smallPapersArray = inorderList.toArray(new SmallPaper[0]);
        return smallPapersArray;
    }

    public static void swapDimension(SmallPaper[] in) {
        for (SmallPaper s : in) {
            int swap = s.width;
            s.width = s.height;
            s.height = swap;
            if (s.value.equals("|")) {
                s.value = "_";
                continue;
            }
            if (s.value.equals("_")) {
                s.value = "|";
            }
        }
        try {
            RectanglePaper.drawSwapRectanglePaperToFile(in, "src/algorithms/drawing");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawTree(Graphics g, SmallPaper node, int x, int y, int xOffset, int yOffset) {
        if (node == null) return;

        g.drawRect(x, y, 50, 30);
        g.drawString(node.value + "[" + node.width + "," + node.height + "]", x + 5, y + 20);

        if (node.left != null) {
            g.drawLine(x + 25, y + 30, x - xOffset + 25, y + yOffset);
            drawTree(g, node.left, x - xOffset, y + yOffset, xOffset / 2, yOffset);
        }
        if (node.right != null) {
            g.drawLine(x + 25, y + 30, x + xOffset + 25, y + yOffset);
            drawTree(g, node.right, x + xOffset, y + yOffset, xOffset / 2, yOffset);
        }
    }

    //------------------------------------------------------------------------------------------------

    //2
    public static SmallPaper[] arrangePapers(SmallPaper[] papers) {
        Arrays.sort(papers, (a, b) -> {
            if (b.width != a.width) {
                return b.width - a.width;
            } else {
                return b.height - a.height;
            }
        });

        List<SmallPaper> orderedPapers = new ArrayList<>();
        Deque<Space> spaces = new ArrayDeque<>();
        spaces.push(new Space(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE));

        for (SmallPaper paper : papers) {
            Iterator<Space> iterator = spaces.iterator();
            while (iterator.hasNext()) {
                Space space = iterator.next();
                if (space.canFit(paper)) {
                    orderedPapers.add(paper);
                    iterator.remove();
                    spaces.push(space.split(paper));
                    spaces.push(space.remainder(paper));
                    break;
                }
            }
        }

        List<SmallPaper> finalPapers = new ArrayList<>();
        int currentHeight = 0;

        for (int i = 0; i < orderedPapers.size(); i++) {
            SmallPaper current = orderedPapers.get(i);
            if (i == 0) {
                finalPapers.add(current);
                currentHeight = current.height;
            } else {
                SmallPaper prev = orderedPapers.get(i - 1);
                if (current.height == prev.height) {
                    finalPapers.add(new SmallPaper("|", 0, 0));
                    finalPapers.add(current);
                } else if (current.width == prev.width || current.width+orderedPapers.get(i + 1).width==prev.width) {
                    finalPapers.add(new SmallPaper("_", 0, 0));
                    finalPapers.add(current);
                    currentHeight += current.height;
                } else if (currentHeight- prev.height==current.height){
                    finalPapers.add(new SmallPaper("|", 0, 0));
                    finalPapers.add(current);
                }else {
                    finalPapers.add(new SmallPaper("|", 0, 0));
                    finalPapers.add(current);
                }
            }
        }

        return finalPapers.toArray(new SmallPaper[0]);
    }

    //

    // 4 الطلب
    public static boolean canFormRectangle(SmallPaper[] papers) {
        int totalArea = 0;
        int maxHeight = 0;
        int maxWidth = 0;

        for (SmallPaper paper : papers) {
            totalArea += paper.height * paper.width;
            maxHeight = Math.max(maxHeight, paper.height);
            maxWidth = Math.max(maxWidth, paper.width);
        }

        for (int width = 1; width <= Math.sqrt(totalArea); width++) {
            if (totalArea % width == 0) {
                int height = totalArea / width;
                if (canFill(papers, width, height) || canFill(papers, height, width)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean canFill(SmallPaper[] papers, int width, int height) {
        int[] paperHeights = new int[width];
        Arrays.fill(paperHeights, height);

        return canPlacePapers(papers, paperHeights, 0);
    }

    private static boolean canPlacePapers(SmallPaper[] papers, int[] paperHeights, int index) {
        if (index == papers.length) {
            for (int h : paperHeights) {
                if (h != 0) {
                    return false;
                }
            }
            return true;
        }

        SmallPaper paper = papers[index];
        for (int i = 0; i <= paperHeights.length - paper.width; i++) {
            boolean canPlace = true;
            for (int j = i; j < i + paper.width; j++) {
                if (paperHeights[j] < paper.height) {
                    canPlace = false;
                    break;
                }
            }
            if (canPlace) {
                for (int j = i; j < i + paper.width; j++) {
                    paperHeights[j] -= paper.height;
                }
                if (canPlacePapers(papers, paperHeights, index + 1)) {
                    return true;
                }
                for (int j = i; j < i + paper.width; j++) {
                    paperHeights[j] += paper.height;
                }
            }
        }
        return false;
    }

    //5
    public static int countAllPossibleRectangles(SmallPaper[] papers) throws Exception {
        int n = papers.length;
        int count = 0;

        for (int i = 1; i < (1 << n); i++) {
            List<SmallPaper> combination = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    combination.add(papers[j]);
                }
            }
            SmallPaper[] comboArray = combination.toArray(new SmallPaper[0]);
            if (canFormRectangle(comboArray)) {
                count++;
            }
        }
        return count;
    }

    public static void swapDimension(String filepath) {
        SmallPaper[] in = RectanglePaper.readRectangle(filepath);
        for (SmallPaper s : in) {
            int swap = s.width;
            s.width = s.height;
            s.height = swap;
            if (s.value.equals("|")) {
                s.value = "_";
            }
            else if (s.value.equals("_")) {
                s.value = "|";
            }
        }
        try {
            RectanglePaper.drawRectanglePaper(in, filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
    //________________________________________________________________
