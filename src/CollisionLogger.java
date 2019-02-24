public class CollisionLogger {

    private int w; //800
    private int h; //800
    private int bucketW; // how many x and y in grid
    private int column;
    private int row;

    private int[][] grid;

    public CollisionLogger(int screenWidth, int screenHeight, int bucketWidth) {
        w = screenWidth;
        h = screenHeight;
        bucketW = bucketWidth;

        column = w/bucketW;
        row = h/bucketW;

        //grid = new int [column][row];
        grid = new int[row][column];

    }

    /**
     * This method records the collision event between two balls. Specifically, in increments the bucket corresponding to
     * their x and y positions to reflect that a collision occurred in that bucket.
     */
    public void collide(Ball one, Ball two) {
        /* update data members to reflect the collision event between ball "one" and ball "two" */
        if(one.intersect(two)) {

            // X and Y values of the collision

            int x = (int) ((one.getXPos() + two.getXPos()) / 2);
            int y = (int) ((one.getYPos() + two.getYPos()) / 2);
            int i=x;
            int j=y;

            // Check for boundaries

            if (x < 0) {
                x = 0;
            }
            else if (y < 0) {
                y = 0;
            }
            else if(x > getW() - 1) {
                x = getW() - 1;
            }
            else if(y > getH() - 1) {
                y = getH() - 1;
            }


            // Sets bucket width and height
            x = x / bucketW;
            y = y / bucketW;

            grid[y][x] +=1;

        }
    }

    public int getW(){
        return w;
    }

    public int getH() {
        return h;
    }

    /**
     * Returns the heat map scaled such that the bucket with the largest number of collisions has value 255,
     * and buckets without any collisions have value 0.
     */
    public int[][] getNormalizedHeatMap() {

        int[][] normalizedHeatMap = new int[row][column]; //NOTE-- these dimensions need to be updated properly!!
        /* implement your code to produce a normalized heat map of type int[][] here */

        // Finds max value at a given coordinate
        int max = 0;
        for(int i = 0; i < grid.length;i++) {
            for(int j = 0; j < grid[i].length;j++) {
                if(grid[i][j] >= max) {
                    max = grid[i][j];
                }
            }
        }

        // Normalizes heat map
        if (max != 0) {
            for(int i = 0; i < normalizedHeatMap.length;i++) {
                for (int j = 0; j < normalizedHeatMap[i].length; j++) {

                    normalizedHeatMap[i][j] = (int) (255 * (((double)grid[i][j] / max)));
                    //System.out.println("At Bucket" + i + "," + j + " Grid Value: " + grid[i][j] + "\n" + "Normalized value: " + normalizedHeatMap[i][j]);
                }
            }
        }
        // If max is 0, set that area on heat map to 0
        else if (max == 0) {
            for(int i = 0; i < normalizedHeatMap.length;i++) {
                for (int j = 0; j < normalizedHeatMap[i].length; j++) {
                    normalizedHeatMap[i][j] = 0;
                }
            }
        }
        return normalizedHeatMap;
    }
}
