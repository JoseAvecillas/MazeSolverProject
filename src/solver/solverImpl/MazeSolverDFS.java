package solver.solverImpl;

import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.ProgressPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MazeSolverDFS implements MazeSolver {

    @Override
    public SolveResults solve(Cell[][] maze, Cell start, Cell end, ProgressPublisher publisher) {
        long startTime = System.currentTimeMillis();
        boolean isVisualizing = (publisher != null);

        int rows = maze.length;
        int cols = maze[0].length;
        Stack<Cell> stack = new Stack<>();
        boolean[][] visited = new boolean[rows][cols];
        Cell[][] parent = new Cell[rows][cols];

        stack.push(start);
        visited[start.getRow()][start.getCol()] = true;
        
        boolean found = false;

        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        while (!stack.isEmpty()) {
            Cell current = stack.pop();

            if (current != start && current != end) {
                current.setState(CellState.VISITED);
                if (isVisualizing) publisher.publish(current);
            }

            if (current.equals(end)) {
                found = true;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current.getRow() + dRow[i];
                int newCol = current.getCol() + dCol[i];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                    !visited[newRow][newCol] && !maze[newRow][newCol].isWall()) { // ¡CORRECCIÓN AQUÍ!
                    
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = current;
                    stack.push(maze[newRow][newCol]);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        List<Cell> path = new ArrayList<>();
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
        
        // Asegurarse de que el inicio y fin se repinten al final de la visualización si hay un publisher
        if (isVisualizing) {
            if (start != null) {
                start.setState(CellState.START); // Asegura que el estado final sea START
                publisher.publish(start);
            }
            if (end != null) {
                end.setState(CellState.END); // Asegura que el estado final sea END
                publisher.publish(end);
            }
        } else { // Para el caso de resolución rápida, solo actualiza el estado final
             if (start != null) {
                start.setState(CellState.START);
            }
            if (end != null) {
                end.setState(CellState.END);
            }
        }


        return new SolveResults(path, timeTaken, found);
    }

    @Override
    public String getName() {
        return "MazeSolverDFS";
    }
}