# GameofLife
A zero player game that simulates the life itself! [Wiki](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) 

## Rules
The universe of the Game of Life is a two-dimensional grid of square cells, each of which is in one of two possible states,
alive or dead, (or populated and unpopulated, respectively). Every cell interacts with its eight neighbours, which are the cells
that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:

1. Any live cell with *fewer than two* live neighbors dies, as if by **underpopulation**.
2. Any live cell with *two or three* live neighbors lives on to the next generation.
3. Any live cell with *more than three* live neighbors dies, as if by **overpopulation**.
4. Any dead cell with *exactly three* live neighbors becomes a live cell, as if by **reproduction**.

## How to play
Provide initial state of the universe or *seed* and let it evolve into a next generation. See Test cases for some of the common
seed patterns

## Patterns

### Block(Still life)

#### Input
    {
      {0, 0, 0, 0},
      {0, 1, 1, 0},
      {0, 1, 1, 0},
      {0, 0, 0, 0},
    }

#### Output
    {
      {0, 0, 0, 0},
      {0, 1, 1, 0},
      {0, 1, 1, 0},
      {0, 0, 0, 0},
    }
    
### Blinker(Oscillators)

#### Input
    {
      {0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0},
      {0, 0, 1, 0, 0},
      {0, 0, 1, 0, 0},
      {0, 0, 0, 0, 0},
    }

#### Outputs
    {
      {0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0},
      {0, 1, 1, 1, 0},
      {0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0},
    }

    {
      {0, 0, 0, 0, 0},
      {0, 0, 1, 0, 0},
      {0, 0, 1, 0, 0},
      {0, 0, 1, 0, 0},
      {0, 0, 0, 0, 0},
    }

### Glider(Spaceship)

#### Input
    {
      {0, 0, 1, 0, 0},
      {1, 0, 1, 0, 0},
      {0, 1, 1, 0, 0},
      {0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0},
    }

#### Outputs
    {
      {0, 1, 0, 0, 0}, 
      {0, 0, 1, 1, 0}, 
      {0, 1, 1, 0, 0}, 
      {0, 0, 0, 0, 0}, 
      {0, 0, 0, 0, 0}, 
    }
    
    {    
      {0, 0, 1, 0, 0}, 
      {0, 0, 0, 1, 0}, 
      {0, 1, 1, 1, 0}, 
      {0, 0, 0, 0, 0}, 
      {0, 0, 0, 0, 0}, 
    }
    
    {    
      {0, 0, 0, 0, 0, 
      {0, 1, 0, 1, 0, 
      {0, 0, 1, 1, 0, 
      {0, 0, 1, 0, 0, 
      {0, 0, 0, 0, 0, 
    }

    {
      {0, 0, 0, 0, 0}, 
      {0, 0, 0, 1, 0}, 
      {0, 1, 0, 1, 0}, 
      {0, 0, 1, 1, 0}, 
      {0, 0, 0, 0, 0}, 
    }

    {
      {0, 0, 0, 0, 0}, 
      {0, 0, 1, 0, 0}, 
      {0, 0, 0, 1, 1}, 
      {0, 0, 1, 1, 0}, 
      {0, 0, 0, 0, 0}, 
    }
    
    {    
      {0, 0, 0, 0, 0}, 
      {0, 0, 0, 1, 0}, 
      {0, 0, 0, 0, 1}, 
      {0, 0, 1, 1, 1}, 
      {0, 0, 0, 0, 0}, 
    }
    
## Future developement

After I was done with the implementation, I realized the use of streams was a bit of a overkill. Since the core calculations
were based on indices, we had to calculate those in the streams, which was an overhead. I think simple nested loops to get
the indices would be easy and cleaner.

Since next state of any cell can be calculated independently, we could do those in parallel. Having the test coverage gives me
confidence of refactoring the code. Stay tuned for further updates.
