import java.util.*;
class Grid
{
    public static final int INFINITY = Integer.MAX_VALUE/3;
   
    class Square
    {
       
        public List<Square> getAdjacents(){
           
            int lowRow = (row == 0 ) ? 0 : (row - 1);
            int lowCol = (col == 0 ) ? 0 : (col - 1);
            int highRow = (row == numRows - 1) ? row : (row + 1);
            int highCol = (col == numCols - 1) ? col : (col + 1);
           
            List<Square> result = new ArrayList<>();
           
            for (int i = lowRow; i <= highRow; ++i) {
                for (int j = lowCol; j <= highCol; ++j) {
                    if (row != i || col != j) {
                        result.add(squares[i][j]);
                    }
                }
            }
            return result;
        }
       
        public String toString()
        {
            return "(" + row + "," + col + ") cell is " + matrix[row][col];
        }
       
        public Square getPrevious(){
            return prev;
        }
       
        public void setDistance(int newDistance, Square prevSquare)
        {
            dist = newDistance;
            prev = prevSquare;
        }
       
        public int getDistance(){
            return dist;
        }
       
        public int getCost(){
            return matrix[row][col];
        }
       
        public void setCost(int newCost){
            matrix[row][col] = newCost;
        }
       
        public Square (int r, int c)
        {
            row = r;
            col = c;
            dist = INFINITY;
            prev = null;
           
        }
       
        int row;
        int col;       
        int dist;
        Square prev;
    }
       
    public Grid ( int [][]m)
    {
        matrix = m;
        numRows = m.length;
        numCols = m[0].length;
        squares = new Square [numRows][numCols];
       
            for (int r = 0; r < numRows; ++r)
                for (int c = 0; c < numCols; ++c)
                    squares[r][c] = new Square(r, c);
               
        UPPER_LEFT = squares[0][0];
        LOWER_RIGHT = squares[numRows - 1][numCols - 1];
    }
   
    public final Square UPPER_LEFT;
    public final Square LOWER_RIGHT;
    private Square [][] squares;
    private int [][] matrix;
    private int numRows;
    private int numCols;
   
    public void computeShortestPaths(Square s){
       
        for (int r = 0; r < numRows; ++r)
            for (int c = 0; c < numCols; ++c)
                squares[r][c].setDistance(INFINITY, null);
       
        // min pq, smallest distance first
        PriorityQueue<Square> pq = new PriorityQueue<>(
        (lhs, rhs) -> lhs.getDistance() - rhs.getDistance()
        );
       
        s.setDistance(0, null);
        pq.add(s);
        while(!pq.isEmpty()){
           
            Square v = pq.remove();
           
            for (Square w : v.getAdjacents()) {
                if(w.getDistance() == INFINITY){
                    w.setDistance(v.getDistance() + w.getCost(), v);
                    pq.add(w);
                }
            }
        }
    }
   
    public void computeBottleNeckPath(Square s) // styart point s
    {
        for (int r = 0; r < numRows; ++r)
            for (int c = 0; c < numCols; ++c)
                squares[r][c].setDistance(0, null);
       
        PriorityQueue<Square> pq = new PriorityQueue<>(
                // max pq, smallest distance first
        (lhs, rhs) -> rhs.getDistance() - lhs.getDistance()
        );
       
        int oldLowerCost = LOWER_RIGHT.getCost();
        LOWER_RIGHT.setCost(INFINITY);
        s.setDistance(INFINITY, null);
        pq.add(s);
       
        while(!pq.isEmpty())
        {
            Square v = pq.remove();
           
            for (Square w : v.getAdjacents()) {
                if ( w.getDistance() == 0)
                {
                    w.setDistance(Math.min(v.getDistance(), w.getCost()), v);
                    pq.add(w);
                }
            }
        }
        LOWER_RIGHT.setCost(oldLowerCost);
    }
   
   public void  printPath(Square t)
   {
       Square prev = t.getPrevious();
      
       if ( prev != null)
       {
           printPath(prev);
       }
       System.out.println(t);
   }
   
    public static void main(String[] args) {
        int [][]input = {{  0, 44, 15, 82},
                         { 61, 17, 28, 94},
                         { 11, 54, 10, 28},
                         { 12, 43, 77, 62},
                         { 40, 13, 14,  0}};
       
        Grid grind = new Grid(input);
       
        grind.computeShortestPaths(grind.UPPER_LEFT);
        grind.printPath(grind.LOWER_RIGHT);
        System.out.println(grind.LOWER_RIGHT.getDistance());
       
        grind.computeBottleNeckPath(grind.UPPER_LEFT);
        grind.printPath(grind.LOWER_RIGHT);
        System.out.println(grind.LOWER_RIGHT.getDistance());
    }
}
