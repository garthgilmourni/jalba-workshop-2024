package game.of.life;

import java.util.List;

import static game.of.life.CellState.ALIVE;
import static game.of.life.CellState.DEAD;

public class Cell {
    private List<Cell> neighbours;
    private CellState state;

    public Cell(List<Cell> neighbours) {
        super();
        state = DEAD;
        this.neighbours = neighbours;
        if (neighbours.size() > 8) {
            throw new IllegalStateException();
        }
    }

    public void makeAlive() {
        setState(ALIVE);
    }

    public void makeDead() {
        setState(DEAD);
    }

    private void setState(CellState newState) {
        state = newState;
    }

    public void addNeighbour(Cell neighbour) {
        if (neighbours.size() == 8) {
            throw new IllegalStateException();
        }
        neighbours.add(neighbour);
    }

    public void changeState() {
        long aliveNeighbours = countAliveNeighbours();
        CellState result = DEAD;
        if (state == DEAD && aliveNeighbours == 3) {
            result = ALIVE;
        } else if (state == ALIVE) {
            if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                result = ALIVE;
            }
        }
        state = result;
    }

    private long countAliveNeighbours() {
        int count = 0;
        for(int i=0;i<neighbours.size();i++) {
            if(neighbours.get(i).isAlive()) {
                count++;
            }
        }
        return count;
    }

    public boolean isAlive() {
        return state == ALIVE;
    }

    public boolean isDead() {
        return state == DEAD;
    }
}