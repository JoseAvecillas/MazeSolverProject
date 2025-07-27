package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    private JComboBox<String> algorithmSelector;
    private JSpinner numRowsSpinner; 
    private JSpinner numColsSpinner; 
    private JButton generateMazeButton;
    private JButton solveMazeButton; 
    private JButton visualizeStepByStepButton; 
    private JButton clearResultsButton;
    private JButton viewResultsButton;
    private JLabel statusLabel;
    private JSlider speedSlider; 

    private JToggleButton startModeButton;
    private JToggleButton endModeButton;
    private JToggleButton wallModeButton;
    private JToggleButton eraseModeButton;

    private ButtonGroup editModeGroup;

    public MazeFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        addComponentsToFrame();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        mazePanel = new MazePanel(20, 20); 

        // Asegúrate de que los nombres aquí coincidan con los que pones en MazeController.initializeSolvers()
        String[] algorithms = {
            "MazeSolverBFS",
            "MazeSolverDFS",
            "MazeSolverRecursivo",
            "MazeSolverDFSCompleto"
            // Si NO quieres usar MazeSolverRecursivoCompletoBT, asegúrate de que no esté aquí:
            // "MazeSolverRecursivoCompletoBT" 
        };
        algorithmSelector = new JComboBox<>(algorithms);

        SpinnerModel rowsSpinnerModel = new SpinnerNumberModel(20, 5, 50, 1);
        numRowsSpinner = new JSpinner(rowsSpinnerModel);

        SpinnerModel colsSpinnerModel = new SpinnerNumberModel(20, 5, 50, 1);
        numColsSpinner = new JSpinner(colsSpinnerModel);

        generateMazeButton = new JButton("Generar Laberinto");
        solveMazeButton = new JButton("Resolver Laberinto (Rápido)"); 
        visualizeStepByStepButton = new JButton("Visualizar Paso a Paso"); 
        clearResultsButton = new JButton("Limpiar Resultados");
        viewResultsButton = new JButton("Ver Resultados");

        statusLabel = new JLabel("Listo.");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        startModeButton = new JToggleButton("Inicio");
        endModeButton = new JToggleButton("Fin");
        wallModeButton = new JToggleButton("Bloqueo");
        eraseModeButton = new JToggleButton("Borrar"); 

        editModeGroup = new ButtonGroup();
        editModeGroup.add(startModeButton);
        editModeGroup.add(endModeButton);
        editModeGroup.add(wallModeButton);
        editModeGroup.add(eraseModeButton); 

        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 50); 
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setMinorTickSpacing(10);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setInverted(true); 
        speedSlider.setBorder(BorderFactory.createTitledBorder("Velocidad (ms de retraso)"));
    }

    private void addComponentsToFrame() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        controlPanel.add(new JLabel("Filas:"));
        controlPanel.add(numRowsSpinner);
        controlPanel.add(new JLabel("Columnas:")); 
        controlPanel.add(numColsSpinner); 
        
        controlPanel.add(generateMazeButton);
        controlPanel.add(new JLabel("Algoritmo:"));
        controlPanel.add(algorithmSelector);
        
        controlPanel.add(solveMazeButton); 
        controlPanel.add(visualizeStepByStepButton); 
        
        controlPanel.add(clearResultsButton);
        controlPanel.add(viewResultsButton);

        JPanel editModePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        editModePanel.setBorder(BorderFactory.createTitledBorder("Modo Edición (Clic Izquierdo)"));
        editModePanel.add(startModeButton);
        editModePanel.add(endModeButton);
        editModePanel.add(wallModeButton);
        editModePanel.add(eraseModeButton); 

        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        speedPanel.add(speedSlider, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.NORTH);
        topPanel.add(editModePanel, BorderLayout.CENTER);
        topPanel.add(speedPanel, BorderLayout.SOUTH); 

        add(topPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(mazePanel);
        add(scrollPane, BorderLayout.CENTER);

        add(statusLabel, BorderLayout.SOUTH);
    }

    public MazePanel getMazePanel() {
        return mazePanel;
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmSelector.getSelectedItem();
    }

    public int getNumRows() { 
        return (Integer) numRowsSpinner.getValue();
    }

    public int getNumCols() { 
        return (Integer) numColsSpinner.getValue();
    }

    public int getVisualizationDelay() { 
        return speedSlider.getValue();
    }

    public void setGenerateMazeButtonListener(ActionListener listener) {
        generateMazeButton.addActionListener(listener);
    }

    public void setSolveMazeButtonListener(ActionListener listener) {
        solveMazeButton.addActionListener(listener);
    }
    
    public void setVisualizeStepByStepButtonListener(ActionListener listener) {
        visualizeStepByStepButton.addActionListener(listener);
    }

    public void setClearResultsButtonListener(ActionListener listener) {
        clearResultsButton.addActionListener(listener);
    }

    public void setViewResultsButtonListener(ActionListener listener) {
        viewResultsButton.addActionListener(listener);
    }

    public void setStartModeButtonListener(ActionListener listener) {
        startModeButton.addActionListener(listener);
    }

    public void setEndModeButtonListener(ActionListener listener) {
        endModeButton.addActionListener(listener);
    }

    public void setWallModeButtonListener(ActionListener listener) {
        wallModeButton.addActionListener(listener);
    }

    public void setEraseModeButtonListener(ActionListener listener) {
        eraseModeButton.addActionListener(listener);
    }

    public void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public int showMessage(String message, String title, int messageType) {
        return JOptionPane.showConfirmDialog(this, message, title, messageType);
    }

    public void setButtonsEnabled(boolean enabled) {
        generateMazeButton.setEnabled(enabled);
        solveMazeButton.setEnabled(enabled);
        visualizeStepByStepButton.setEnabled(enabled); 
        clearResultsButton.setEnabled(enabled);
        viewResultsButton.setEnabled(enabled);
        algorithmSelector.setEnabled(enabled);
        numRowsSpinner.setEnabled(enabled);
        numColsSpinner.setEnabled(enabled);
        
        startModeButton.setEnabled(enabled);
        endModeButton.setEnabled(enabled);
        wallModeButton.setEnabled(enabled);
        eraseModeButton.setEnabled(enabled);
        speedSlider.setEnabled(enabled); 
    }

    public EditMode getSelectedEditMode() {
        if (startModeButton.isSelected()) {
            return EditMode.START;
        } else if (endModeButton.isSelected()) {
            return EditMode.END;
        } else if (wallModeButton.isSelected()) {
            return EditMode.WALL;
        } else if (eraseModeButton.isSelected()) {
            return EditMode.ERASE;
        }
        return EditMode.NONE;
    }

    public enum EditMode {
        START, END, WALL, ERASE, NONE
    }
}