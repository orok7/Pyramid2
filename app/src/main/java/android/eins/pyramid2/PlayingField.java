package android.eins.pyramid2;

class PlayingField {

    private Block leftTop;
    private Block centerTop;
    private Block rightTop;

    private int width;
    private int height;

    private int numBlocksInLeft;
    private int numBlocksInCenter;
    private int numBlocksInRight;

    public void setNumBlocks(int num, Position position) {
        switch (position) {
            case LEFT:
                numBlocksInLeft = num;
                break;
            case CENTER:
                numBlocksInCenter = num;
                break;
            case RIGHT:
                numBlocksInRight = num;
                break;
        }
    }

    public void decrementNumBlocks(Position position) {
        switch (position) {
            case LEFT:
                numBlocksInLeft--;
                break;
            case CENTER:
                numBlocksInCenter--;
                break;
            case RIGHT:
                numBlocksInRight--;
                break;
        }
    }

    public void incrementNumBlocks(Position position) {
        switch (position) {
            case LEFT:
                numBlocksInLeft++;
                break;
            case CENTER:
                numBlocksInCenter++;
                break;
            case RIGHT:
                numBlocksInRight++;
                break;
        }
    }

    public int getNumBlocks(Position position) {
        int num = 0;
        switch (position) {
            case LEFT:
                num = numBlocksInLeft;
                break;
            case CENTER:
                num =  numBlocksInCenter;
                break;
            case RIGHT:
                num =  numBlocksInRight;
                break;
        }
        return num;
    }

    public void setTop(Block top, Position position) {
        switch (position) {
            case LEFT:
                this.leftTop = top;
                break;
            case CENTER:
                this.centerTop = top;
                break;
            case RIGHT:
                this.rightTop = top;
                break;
        }
    }

    public Block getTop(Position position) {
        Block top = null;
        switch (position) {
            case LEFT:
                top = leftTop;
                break;
            case CENTER:
                top =  centerTop;
                break;
            case RIGHT:
                top =  rightTop;
                break;
        }
        return top;
    }

    PlayingField () {

    }

    enum Position {
        LEFT, CENTER, RIGHT;
    }

    public int getWidth() {
        return width;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "PlayingField{" +
                "leftTop=" + leftTop +
                ", centerTop=" + centerTop +
                ", rightTop=" + rightTop +
                ", numBlocksInLeft=" + numBlocksInLeft +
                ", numBlocksInCenter=" + numBlocksInCenter +
                ", numBlocksInRight=" + numBlocksInRight +
                '}';
    }
}
