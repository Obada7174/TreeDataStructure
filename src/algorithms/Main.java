package algorithms;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            //1
            SmallPaper s1 = new SmallPaper("A", 10, 5);
            SmallPaper s2 = new SmallPaper("|", 0, 0);
            SmallPaper s3 = new SmallPaper("B", 10, 5);
            SmallPaper s4 = new SmallPaper("|", 0, 0);
            SmallPaper s5 = new SmallPaper("C", 15, 5);
            SmallPaper s6 = new SmallPaper("_", 0, 0);
            SmallPaper s7 = new SmallPaper("D", 15, 25);
            SmallPaper s8 = new SmallPaper("|", 0, 0);
            SmallPaper s9 = new SmallPaper("E", 20, 15);
            SmallPaper s10 = new SmallPaper("_", 0, 0);
            SmallPaper s11 = new SmallPaper("F", 20, 10);


//            SmallPaper s1 = new SmallPaper("G", 5, 5);
//            SmallPaper s2 = new SmallPaper("|", 0, 0);
//            SmallPaper s3 = new SmallPaper("H", 30, 5);
//            SmallPaper s4 = new SmallPaper("|", 0, 0);
//            SmallPaper s5 = new SmallPaper("C", 10, 5);
//            SmallPaper s6 = new SmallPaper("_", 0, 0);
//            SmallPaper s7 = new SmallPaper("D", 15, 40);
//            SmallPaper s8 = new SmallPaper("|", 0, 0);
//            SmallPaper s9 = new SmallPaper("E", 30, 10);
//            SmallPaper s10 = new SmallPaper("_", 0, 0);
//            SmallPaper s11 = new SmallPaper("F", 30, 30);


//            SmallPaper s1 = new SmallPaper("A", 20, 10);
//            SmallPaper s2 = new SmallPaper("|", 0, 0);
//            SmallPaper s3 = new SmallPaper("B", 20, 10);
//            SmallPaper s4 = new SmallPaper("|", 0, 0);
//            SmallPaper s5 = new SmallPaper("C", 30, 10);
//            SmallPaper s6 = new SmallPaper("_", 0, 0);
//            SmallPaper s7 = new SmallPaper("D", 30, 50);
//            SmallPaper s8 = new SmallPaper("|", 0, 0);
//            SmallPaper s9 = new SmallPaper("E", 40, 30);
//            SmallPaper s10 = new SmallPaper("_", 0, 0);
//            SmallPaper s11 = new SmallPaper("F", 40, 20);

            SmallPaper[] preOrder = new SmallPaper[]{s6, s2, s1, s4, s3, s5, s8, s7, s10, s9, s11};
            SmallPaper[] inOrder = new SmallPaper[]{s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11};
            RectanglePaper paper = new RectanglePaper();
            paper.buildRectanglePaper(preOrder, inOrder);

            //   (A[20,10]|(B[20,10]|C[30,10]))
//_______________________________________________________________________________________________
//            //2
//             export
//            System.out.println(paper.inorderTraversal(paper.root));
//            RectanglePaper t = new RectanglePaper();
//            // import
//            t.buildTree("((A[20,10]|(B[20,10]|C[30,10]))_(D[30,50]|(E[40,30]_F[40,20])))");
//            System.out.println(t.printInorder());
//            System.out.println(t.inorderTraversal(t.root));
//_______________________________________________________________________________________________
//            System.out.println("________________________________________________________________");
//_______________________________________________________________________________________________
            //3
            // export
//            paper.drawRectanglePaper(inOrder, "src/algorithms/drawing");

//
//            // import
//            for (SmallPaper s : RectanglePaper.readRectangle2("src/algorithms/drawing")) {
//                System.out.print(s.value + "[" + s.width + "," + s.height + "],");
//            }
//            System.out.println();
//_______________________________________________________________________________________________

//            4
//            SmallPaper[] papers = new SmallPaper[]{s1, s3, s5, s7, s9, s11};
//            System.out.println(RectanglePaper.canFormRectangle(papers));

//            5
//            System.out.println(RectanglePaper.countAllPossibleRectangles(papers));

//            ________________________________________________________________
//            5
//            SmallPaper[] papers2 = new SmallPaper[]{s1, s5, s3, s7, s9, s11};
//            SmallPaper[] orderedPapers = RectanglePaper.arrangePapers(papers2);
//
//            for (SmallPaper p : orderedPapers) {
//                System.out.println(p);
//            }
//            RectanglePaper.drawRectanglePaper(orderedPapers, "src/algorithms/drawing");

//_______________________________________________________________________________________________
//            6
//            paper.swapDimension(preOrder,inOrder);

            SwingUtilities.invokeLater(() -> new DataStructureGUI().setVisible(true));
        }catch (Exception e){
            e.printStackTrace();
        }

        //________________________________________________________________
    }}
