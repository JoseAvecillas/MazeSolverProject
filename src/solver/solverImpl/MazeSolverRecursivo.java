package solver.solverImpl;

import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.ProgressPublisher;

import java.util.ArrayList;
import java.util.List;

public class MazeSolverRecursivo implements MazeSolver {

    private long startTime;
    private Cell[][] maze;
    private Cell startCell; 
    private Cell endCell;
    private int rows;
    private int cols;
    private boolean[][] visited;
    private List<Cell> solutionPath;
    private boolean foundSolution;
    private ProgressPublisher publisher; 

    private static final int[] dRow = {-1, 1, 0, 0}; 
    private static final int[] dCol = {0, 0, -1, 1};

    @Override
    public SolveResults solve(Cell[][] maze, Cell start, Cell end, ProgressPublisher publisher) { 
        this.startTime = System.currentTimeMillis();
        this.maze = maze;
        this.startCell = start; 
        this.endCell = end;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.visited = new boolean[rows][cols];
        this.solutionPath = new ArrayList<>();
        this.foundSolution = false;
        this.publisher = publisher; 

        dfsRecursive(start.getRow(), start.getCol());

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        boolean isSolved = foundSolution;

        if (startCell != null) { 
            startCell.setState(CellState.START);
        }
        if (endCell != null) {   
            endCell.setState(CellState.END);
        }

        if (this.publisher != null) { 
            if (startCell != null && endCell != null) {
                this.publisher.publish(startCell, endCell);
            } else if (startCell != null) {
                this.publisher.publish(startCell);
            } else if (endCell != null) {
                this.publisher.publish(endCell);
            }
        }
        
        return new SolveResults(solutionPath, timeTaken, isSolved);
    }

    private boolean dfsRecursive(int r, int c) {
        if (foundSolution) { 
            return true;
        }

        if (r < 0 || r >= rows || c < 0 || c >= cols || visited[r][c] || maze[r][c].isWall()) {
            return false;
        }

        Cell currentCell = maze[r][c];
        visited[r][c] = true;

        if (currentCell != startCell && currentCell != endCell) { 
            currentCell.setState(CellState.VISITED); 
            if (publisher != null) publisher.publish(currentCell); 
        }

        if (currentCell.equals(endCell)) {
            foundSolution = true;
            solutionPath.add(0, currentCell); 
            if (currentCell != startCell && currentCell != endCell) { 
                currentCell.setState(CellState.SOLUTION); 
                if (publisher != null) publisher.publish(currentCell); 
            }
            return true;
        }

        for (int i = 0; i < dRow.length; i++) { 
            int newRow = r + dRow[i];
            int newCol = c + dCol[i]; 

            if (dfsRecursive(newRow, newCol)) {
                solutionPath.add(0, currentCell); 
                if (currentCell != startCell && currentCell != endCell) { 
                    currentCell.setState(CellState.SOLUTION); 
                    if (publisher != null) publisher.publish(currentCell); 
                }
                return true;
            }
        }
        
        return false;
    }

    @Override
    public String getName() {
        return "MazeSolverRecursivo";
    }
}