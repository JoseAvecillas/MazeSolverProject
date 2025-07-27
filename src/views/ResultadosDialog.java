package views;

import models.AlgorithmResult; // Asegúrate de que esta importación esté presente

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ResultadosDialog extends JDialog {

    private JTable resultsTable;
    private DefaultTableModel tableModel;

    public ResultadosDialog(Frame owner, List<AlgorithmResult> results) {
        super(owner, "Resultados de Resolución de Laberintos", true); // true para que sea modal
        setSize(700, 400); // Tamaño adecuado para la tabla
        setLocationRelativeTo(owner); // Centrar respecto a la ventana principal

        // Configurar el modelo de la tabla
        String[] columnNames = {"Algoritmo", "Tamaño del Laberinto", "Longitud del Camino", "Tiempo (ms)"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que las celdas no sean editables
            }
        };
        resultsTable = new JTable(tableModel);

        // Llenar la tabla con los resultados
        for (AlgorithmResult result : results) {
            Object[] rowData = {
                result.getAlgorithmName(),
                result.getMazeSize() + " celdas", // Mostrar tamaño del laberinto
                result.getPathLength() + " celdas", // Mostrar longitud del camino
                result.getTimeTakenMillis()
            };
            tableModel.addRow(rowData);
        }

        // Añadir la tabla a un JScrollPane para que sea desplazable si hay muchos resultados
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Añadir un botón para cerrar el diálogo
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose()); // Cierra el diálogo al hacer clic

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}