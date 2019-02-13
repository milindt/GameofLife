import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Universe {

    private int[][] cells;

    public static Universe seed(int[][] seeds) {
        Universe universe = new Universe();
        universe.cells = seeds;
        return universe;
    }

    public int[][] cells() {
        return cells;
    }

    /*  public Universe evolve2() {
          AtomicInteger counter = new AtomicInteger(0);
          return next(Arrays.stream(cells)
                  .flatMapToInt(IntStream::of)
                  .map((cell) -> {
                      int count = counter.getAndIncrement();
                      boolean alive = cell == 1 &&
                              evolve(getAliveNeighboursCount(getXCoordinate(count), getYCordinate(count)));
                      return alive ? 1 : 0;
                  })
                  //.peek(System.out::println)
                  .boxed()
                  .collect(Collectors.toList())
          );
      }
  */

    public Universe evolve() {

        AtomicInteger counter = new AtomicInteger(0);

        List<int[]> cellsToPreserve = Arrays.stream(cells)
                .map(Arrays::stream)
                .map((rowCells) -> rowCells.map(c -> {
                    int count = counter.getAndIncrement();
                    int aliveNeighboursCount =
                            getAliveNeighboursCount(getXCoordinate(count), getYCordinate(count));
                    return isAlive(c) ?
                          toCellVal(aliveNeighboursCount > 1 && aliveNeighboursCount < 4)
                          : toCellVal(aliveNeighboursCount == 3);
                }).toArray())
                .collect(Collectors.toList());

        return Universe.seed(to2DArray(cellsToPreserve));
    }

    private boolean isAlive(int cellValue) {
        return cellValue == 1;
    }

    private int toCellVal(boolean alive) {
        return alive ? 1 : 0;
    }

    private int[][] to2DArray(List<int[]> cellsToPreserve) {
        int[][] seeds = new int[this.cells.length][this.cells[0].length];
        int index = 0;
        for (int[] cells : cellsToPreserve) {
            seeds[index++] = cells;
        }
        return seeds;
    }

    private boolean evolve(int aliveNeighboursCount) {
        return aliveNeighboursCount > 1 &&
                aliveNeighboursCount < 4;
    }

    private int getYCordinate(int counter) {
        return counter % cells[0].length;
    }

    private int getXCoordinate(int counter) {
        return counter / cells.length;
    }

    private int getAliveNeighboursCount(int x, int y) {
        int[] neighbours = {
                getLeftAlive(x, y),
                getRightAlive(x, y),
                getTopAlive(x, y),
                getBottomAlive(x, y),
                getTopLeftAlive(x, y),
                getBottomLeftAlive(x, y),
                getTopRightAlive(x, y),
                getBottomRightAlive(x, y)
        };
        return Arrays.stream(neighbours)
                .reduce((a, b) -> a + b)
                .orElseThrow(RuntimeException::new);
    }

    private int getBottomRightAlive(int x, int y) {
        return onBottomBorder(x, y) || onRightBorder(x, y) ? 0 : cells[++x][++y];
    }

    private int getTopRightAlive(int x, int y) {
        return onTopBorder(x, y) || onRightBorder(x, y) ? 0 : cells[++x][--y];
    }

    private int getBottomLeftAlive(int x, int y) {
        return onBottomBorder(x, y) || onLeftBorder(x, y) ? 0 : cells[--x][++y];
    }

    private int getTopLeftAlive(int x, int y) {
        return onTopBorder(x, y) || onLeftBorder(x, y) ? 0 : cells[--x][--y];
    }

    private int getBottomAlive(int x, int y) {
        return onBottomBorder(x, y) ? 0 : cells[x][++y];
    }

    private boolean onBottomBorder(int x, int y) {
        return y >= cells[0].length - 1;
    }

    private int getTopAlive(int x, int y) {
        return onTopBorder(x, y) ? 0 : cells[x][--y];
    }

    private boolean onTopBorder(int x, int y) {
        return y < 1;
    }

    private int getRightAlive(int x, int y) {
        return onRightBorder(x, y) ? 0 : cells[++x][y];
    }

    private boolean onRightBorder(int x, int y) {
        return x >= cells.length - 1;
    }

    private int getLeftAlive(int x, int y) {
        return onLeftBorder(x, y) ? 0 : cells[--x][y];
    }

    private boolean onLeftBorder(int x, int y) {
        return x < 1;
    }


    Universe next(List<Integer> cells) {
        return seed(toSeeds(cells));
    }

    private int[][] toSeeds(List<Integer> cells) {

        final AtomicInteger counter = new AtomicInteger(0);
        Map<Integer, List<Integer>> collect = cells.stream().collect(Collectors.groupingBy(toRowIndexByCellsSize(counter)));
        int[][] seeds = new int[this.cells.length][this.cells[0].length];
        collect.entrySet().stream().forEach(getInsertRow(seeds));
        return seeds;
    }

    private Consumer<Map.Entry<Integer, List<Integer>>> getInsertRow(int[][] seeds) {
        return entry -> insertRowAt(seeds, entry.getValue(), entry.getKey());
    }

    private Function<Integer, Integer> toRowIndexByCellsSize(AtomicInteger counter) {
        return x -> counter.getAndIncrement() / this.cells[0].length;
    }

    private void insertRowAt(int[][] seeds, List<Integer> row, int rowIndex) {
        seeds[rowIndex] = row.stream().mapToInt(i -> i).toArray();
    }

    /*public static void main(String[] args) {
        int[][] seeds = {
                {1, 1, 0},
                {0, 1, 0},
                {1, 0, 0}
        };
        *//*Universe universe = Universe.seed(seeds);
        for (int i = 0; i < 9; i++) {
            System.out.println("X, Y: " + universe.getXCoordinate(i) + ", " + universe.getYCordinate(i));
        }*//*
        Universe.seed(seeds).evolve();
    }*/

    @Override
    public String toString() {
        final StringBuilder toString = new StringBuilder("");
        Arrays.stream(cells())
                .map(Arrays::stream)
                .peek(x -> toString.append("\n"))
                .flatMapToInt(x -> x)
                //.peek(x -> toString.append("\n"))
                .forEach(x -> toString.append(x));
        return toString.toString();
    }

}
