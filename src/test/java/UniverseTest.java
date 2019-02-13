import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UniverseTest {

    @Test
    @DisplayName("Create universe with seed")
    void createUniverse() {
        int[][] seeds = {{1, 0},
                         {0, 1}};
        Universe universe = Universe
                .seed(seeds);
        assertThat(universe.cells()).containsExactly(seeds);
    }

    @Test
    @DisplayName("Cells with fewer than 2 neighbours die")
    void underpopulation() {
        int[][] seeds = {{1, 0},
                         {0, 1}};
        Universe universe = Universe
                .seed(seeds);

        int[][] evolvedCells = {{ 0, 0},
                         { 0, 0}};
        assertThat(universe.evolve().cells()).containsExactly(evolvedCells);
    }

    @Test
    @DisplayName("Cells with 2 or 3 neighbours live through the evolution")
    void survive() {
        int[][] seeds = {
                {1, 1, 0},
                {0, 0, 1},
                {0, 0, 1}
        };
        int[][] evolvedCells = {
                {0, 1, 0},
                {0, 0, 1},
                {0, 0, 0}
        };
        assertThat(Universe.seed(seeds).evolve().cells())
                .containsExactly(evolvedCells);
    }

    @Test
    @DisplayName("Cells with more than 3 neighbours die due to overpopulation")
    void overpopulate() {
        int[][] seeds = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        Universe universe = Universe.seed(seeds);
        int[][] evolvedCells = {
                {1, 0, 1},
                {0, 0, 0},
                {1, 0, 1}
        };
        assertThat(universe.evolve().cells()).containsExactly(evolvedCells);
    }

    @Test
    @DisplayName("Dead cells with exactly 3 cells are born again due reproduction")
    void reproduction() {
        int[][] seeds = {
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}
        };
        Universe universe = Universe.seed(seeds);
        int[][] evolvedCells = {
                {0, 1, 0},
                {1, 0, 1},
                {0, 1, 0}
        };
        assertThat(universe.evolve().cells()).containsExactly(evolvedCells);
    }

    @Test
    @DisplayName("Still life: holds life forever, first pattern: Block ")
    void block() {
        int[][] seeds = {
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };
        Universe universe = Universe.seed(seeds);
        int[][] evolvedCells = {
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };
        Universe evolved = universe.evolve();
        assertThat(evolved.cells()).containsExactly(evolvedCells);
        assertThat(evolved.evolve().cells()).containsExactly(evolvedCells);
    }

    @Test
    @DisplayName("Oscillators: return to initial state within finite steps, first pattern: Blinker")
    void blinker() {
        int[][] seeds = blinkerSeeds();
        Universe universe = Universe.seed(seeds);
        int[][] evolvedCells = {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        Universe evolved = universe.evolve();
        assertThat(evolved.cells()).containsExactly(evolvedCells);
        assertThat(evolved.evolve().cells()).containsExactly(seeds);
    }

    private int[][] blinkerSeeds() {
        return new int[][]{
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            };
    }

    @Test
    @Disabled
    @DisplayName("Observe the observable universe for Blinker")
    void display() throws InterruptedException {
        Universe universe = Universe.seed(blinkerSeeds());
        while(true) {
            System.out.println(universe);
            universe = universe.evolve();
            Thread.sleep(2000);
        }
    }

    @Test
    @Disabled
    @DisplayName("Observe an universe with Glider")
    void displayGlider() throws InterruptedException {
        Universe universe = Universe.seed(gliderSeeds());
        while(true) {
            System.out.println(universe);
            universe = universe.evolve();
            Thread.sleep(3000);
        }
    }

    private int[][] gliderSeeds() {
        return new int[][]{
                {0, 0, 1, 0, 0},
                {1, 0, 1, 0, 0},
                {0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
    }

}
