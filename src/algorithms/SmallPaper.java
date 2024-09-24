package algorithms;

import java.util.Objects;

public class SmallPaper {
    String value;
    int height;
    int width;
    SmallPaper left;
    SmallPaper right;

    public SmallPaper() {
        this.value = null;
        this.width = 0;
        this.height = 0;
        this.left = null;
        this.right = null;
    }

    public SmallPaper(String value, int width, int height) {
        this.value = value;
        this.width = width;
        this.height = height;
        this.left = null;
        this.right = null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmallPaper that = (SmallPaper) o;
        return height == that.height && width == that.width && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, height, width);
    }

    @Override
    public String toString() {
        return "SmallPaper{" +
                "value='" + value + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}