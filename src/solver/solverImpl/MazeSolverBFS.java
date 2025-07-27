package solver.solverImpl;

import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.ProgressPublisher;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MazeSolverBFS implements MazeSolver {

    @Override
    public SolveResults solve(Cell[][] maze, Cell start, Cell end, ProgressPublisher publisher) { 
        long startTime = System.currentTimeMillis();
        boolean isVisualizing = (publisher != null); 

        int rows = maze.length;
        int cols = maze[0].length;
        Queue<Cell> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        Cell[][] parent = new Cell[rows][cols];

        queue.add(start);
        visited[start.getRow()][start.getCol()] = true;
        
        boolean found = false;

        while (!queue.isEmpty() && !found) {
            Cell current = queue.poll();

            if (current != start && current != end) {
                current.setState(CellState.VISITED);
                if (isVisualizing) publisher.publish(current); 
            }

            if (current.equals(end)) {
                found = true;
                break;
            }

            int[] dRow = {-1, 1, 0, 0};
            int[] dCol = {0, 0, -1, 1};

            for (int i = 0; i < 4; i++) {
                int newRow = current.getRow() + dRow[i];
                int newCol = current.getCol() + dCol[i];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                    !visited[newRow][newCol] && !maze[newRow][newCol].isWall()) {
                    
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = current;
                    queue.add(maze[newRow][newCol]);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        List<Cell> path = new LinkedList<>();
        if (found) {
            Cell current = end;
            while (current != null) {
                path.add(0, current); 
                if (current != start && current != end) {
                    current.setState(CellState.SOLUTION);
                    if (isVisualizing) publisher.publish(current); 
                }
                current = parent[current.getRow()][current.getCol()];
            }
        }
        
        if (start != null) {
            start.setState(CellState.START);
        }
        if (end != null) {
            end.setState(CellState.END);
        }

        if (isVisualizing) {
            if (start != null && end != null) {
                publisher.publish(start, end); 
            } else if (start != null) {
                publisher.publish(start); 
            } else if (end != null) {
                publisher.publish(end); 
            }
        }

        return new SolveResults(path, timeTaken, found);
    }

    @Override
    public String getName() {
        return "MazeSolverBFS";
    }
}