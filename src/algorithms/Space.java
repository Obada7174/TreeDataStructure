package algorithms;

class Space {
    int x, y, width, height;

    public Space(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean canFit(SmallPaper paper) {
        return paper.width <= width && paper.height <= height;
    }

    public Space split(SmallPaper paper) {
        return new Space(x + paper.width, y, width - paper.width, paper.height);
    }

    public Space remainder(SmallPaper paper) {
        return new Space(x, y + paper.height, width, height - paper.height);
    }
}
