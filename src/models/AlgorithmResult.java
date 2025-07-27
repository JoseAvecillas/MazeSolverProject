package models;

import java.io.Serializable;

public class AlgorithmResult implements Serializable {
    private static final long serialVersionUID = 1L; // Para serialización
    private String algorithmName;
    private int pathLength;
    private long timeTakenMillis;
    private int mazeSize; // Campo agregado para el tamaño del laberinto

    public AlgorithmResult(String algorithmName, int pathLength, long timeTakenMillis, int mazeSize) {
        this.algorithmName = algorithmName;
        this.pathLength = pathLength;
        this.timeTakenMillis = timeTakenMillis;
        this.mazeSize = mazeSize;
    }

    // Getters
    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getPathLength() {
        return pathLength;
    }

    public long getTimeTakenMillis() {
        return timeTakenMillis;
    }

    public int getMazeSize() { // Nuevo getter
        return mazeSize;
    }

    @Override
    public String toString() {
        return "AlgorithmResult{" +
               "algorithmName='" + algorithmName + '\'' +
               ", pathLength=" + pathLength +
               ", timeTakenMillis=" + timeTakenMillis +
               ", mazeSize=" + mazeSize +
               '}';
    }
}