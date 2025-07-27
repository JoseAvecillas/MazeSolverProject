package views;

import models.Cell;
import models.CellState; // Asegúrate de que esta importación esté presente

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazePanel extends JPanel {
    private Cell[][] maze;
    private int cellSize = 20;
    private MazePanelClickListener clickListener;

    public MazePanel(int defaultRows, int defaultCols) {
        setMaze(new Cell[defaultRows][defaultCols]);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickListener != null) {
                    int col = e.getX() / cellSize;
                    int row = e.getY() / cellSize;
                    if (row >= 0 && row < maze.length && col >= 0 && col < maze[0].length) {
                        clickListener.onCellClicked(row, col, e.getButton());
                    }
                }
            }
        });
    }

    public void setMaze(Cell[][] maze) {
        this.maze = maze;
        if (maze != null && maze.length > 0 && maze[0].length > 0) {
            setPreferredSize(new Dimension(maze[0].length * cellSize, maze.length * cellSize));
        } else {
            setPreferredSize(new Dimension(cellSize, cellSize));
        }
        revalidate();
        repaint();
    }

    public void setStartCell(Cell start) {
        // No es necesario guardar la celda aquí, MazeController maneja el estado del modelo
        repaint();
    }

    public void setEndCell(Cell end) {
        // No es necesario guardar la celda aquí, MazeController maneja el estado del modelo
        repaint();
    }

    public void setMazePanelClickListener(MazePanelClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (maze == null) return;

        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                Cell cell = maze[r][c];
                Color color;
                switch (cell.getState()) {
                    case WALL:
                        color = Color.BLACK;
                        break;
                    case START:
                        color = Color.GREEN.darker();
                        break;
                    case END:
                        color = Color.RED.darker();
                        break;
                    case SOLUTION:
                        color = Color.BLUE;
                        break;
                    case VISITED:
                        color = Color.LIGHT_GRAY;
                        break;
                    case PATH:
                    default:
                        color = Color.WHITE;
                        break;
                }
                g.setColor(color);
                g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                
                g.setColor(Color.DARK_GRAY);
                g.drawRect(c * cellSize, r * cellSize, cellSize, cellSize);
            }
        }
    }

    public interface MazePanelClickListener {
        void onCellClicked(int row, int col, int mouseButton);
    }
}