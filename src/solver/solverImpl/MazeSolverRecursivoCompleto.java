package solver.solverImpl;

import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.ProgressPublisher; // <-- NUEVA IMPORTACIÓN

import java.util.ArrayList;
import java.util.List;

public class MazeSolverRecursivoCompleto implements MazeSolver {

    private long startTime;
    private Cell[][] maze;
    private Cell startCell;
    private Cell endCell;
    private int rows;
    private int cols;
    private boolean[][] visited;
    private List<Cell> solutionPath;
    private boolean foundSolution;
    private ProgressPublisher publisher; // <-- CAMBIO AQUÍ

    private static final int[] dRow = {-1, 1, 0, 0}; 
    private static final int[] dCol = {0, 0, -1, 1};

    @Override
    public SolveResults solve(Cell[][] maze, Cell start, Cell end, ProgressPublisher publisher) { // <-- CAMBIO AQUÍ
        this.startTime = System.currentTimeMillis();
        this.maze = maze;
        this.startCell = start;
        this.endCell = end;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.visited = new boolean[rows][cols];
        this.solutionPath = new ArrayList<>();
        this.foundSolution = false;
        this.publisher = publisher; // <-- CAMBIO AQUÍ

        dfsRecursive(startCell.getRow(), startCell.getCol());

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        boolean isSolved = foundSolution;

        // Limpiar VISITED si no son parte de la solución final, solo si NO estamos visualizando.
        if (this.publisher == null) { // Usamos 'this.publisher'
             for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Cell current = maze[r][c];
                    if (current.getState() == CellState.VISITED) {
                        current.setState(CellState.PATH); 
                    }
                }
            }
        }
        
        // Asegurar que inicio y fin retienen su estado original y se publican para el repintado final.
        if (startCell != null) {
            startCell.setState(CellState.START);
        }
        if (endCell != null) {
            endCell.setState(CellState.END);
        }

        if (this.publisher != null) { // Usamos 'this.publisher'
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
            if (publisher != null) publisher.publish(currentCell); // <-- CAMBIO AQUÍ
        }

        if (currentCell.equals(endCell)) {
            if (!foundSolution) { 
                foundSolution = true;
            }
            return true; 
        }

        boolean pathFoundInThisBranch = false;
        for (int i = 0; i < 4; i++) {
            int newRow = r + dRow[i];
            int newCol = c + dCol[i];

            if (dfsRecursive(newRow, newCol)) {
                pathFoundInThisBranch = true;
                if (foundSolution && !solutionPath.contains(currentCell)) {
                    solutionPath.add(0, currentCell); 
                    if (currentCell != startCell && currentCell != endCell) {
                        currentCell.setState(CellState.SOLUTION);
                        if (publisher != null) publisher.publish(currentCell); // <-- CAMBIO AQUÍ
                    }
                }
            }
        }
        
        if (!foundSolution && currentCell != startCell && currentCell != endCell && currentCell.getState() == CellState.VISITED) {
            currentCell.setState(CellState.PATH); 
            if (publisher != null) publisher.publish(currentCell); // <-- CAMBIO AQUÍ
        }

        return pathFoundInThisBranch;
    }

    @Override
    public String getName() {
        return "MazeSolverDFSCompleto";
    }
}