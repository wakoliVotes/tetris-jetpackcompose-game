# Tetris Jetpackcompose Game
<img src="images/tetris.png" >

### Tetris Game Documentation

- This forms an overview, that summarizes key aspects in the Tetris game.

#### 1. **Project Setup**
- **Dependencies:** Ensure that your project is using Jetpack Compose with the required libraries in `build.gradle`:
  ```gradle
  implementation "androidx.compose.ui:ui:1.x.x"
  implementation "androidx.compose.material:material:1.x.x"
  implementation "androidx.compose.ui:ui-tooling:1.x.x"
  implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.x.x"
  ```

#### 2. **Game Components**
   - **Grid Setup:** Define a grid that represents the Tetris board. Use a 2D array or list of lists to track the game state (e.g., filled, empty, block color).
     ```kotlin
     val grid = Array(20) { Array(10) { 0 } } // 20 rows, 10 columns
     ```

   - **Tetrimino Shapes:** Create different block shapes (L, T, I, O, etc.) using 2D arrays. Each block can have rotation states that update as the player rotates the piece.
     ```kotlin
     val blockI = arrayOf(
         arrayOf(1, 1, 1, 1),
         arrayOf(0, 0, 0, 0)
     )
     ```

#### 3. **Game Loop**
   - Use `LaunchedEffect` with `rememberCoroutineScope` to create a game loop that controls the game state (block movement, rotation, collision detection, etc.).
   ```kotlin
   LaunchedEffect(Unit) {
       while (true) {
           delay(500L) // Control block speed
           moveBlockDown()
       }
   }
   ```

#### 4. **Rendering the Grid**
   - Render the Tetris grid using `Canvas` or `Box` composables. Each block on the grid can be a colored square.
     ```kotlin
     Canvas(modifier = Modifier.size(300.dp)) {
         for (row in grid) {
             for (cell in row) {
                 if (cell != 0) {
                     drawRect(color = Color.Blue, size = Size(30f, 30f))
                 }
             }
         }
     }
     ```

#### 5. **User Input**
   - Handle user inputs (left, right, down, rotate) using `Modifier.pointerInput` or by mapping to hardware buttons like arrow keys.
     ```kotlin
     Modifier.pointerInput(Unit) {
         detectTapGestures(onDoubleTap = { rotateBlock() })
     }
     ```

#### 6. **Game Logic**
   - **Collision Detection:** Implement logic to detect collisions when a block reaches the bottom or hits another block.
   - **Line Clearing:** Detect full rows, clear them, and drop the above blocks.
     ```kotlin
     fun clearLines() {
         for (i in grid.indices) {
             if (grid[i].all { it != 0 }) {
                 grid.removeAt(i)
                 grid.add(0, Array(10) { 0 })
             }
         }
     }
     ```

#### 7. **Score Tracking**
   - Keep track of score based on the number of lines cleared. You can display the score using `Text` composables.

#### 8. **State Management**
   - Use `remember` and `MutableState` to manage game state (current block position, grid, score).
     ```kotlin
     var score by remember { mutableStateOf(0) }
     ```

#### 9. **Game Over**
   - Check for a "game over" condition when a new block cannot be placed, then reset or stop the game.

### Key Considerations:
- **Responsive UI:** Use flexible sizing with `Modifier.size()` to ensure the game looks good across different screen sizes.
- **Animations:** Jetpack Compose's animation APIs can be used for smoother transitions (e.g., block movements).

### Read More On Medium
[Extra Articles](https://medium.com/@acceldia)

<!-- [Tetris Jetpackcompose Game](https://medium.com/@acceldia) -->

### Project Time by Wakatime
[![wakatime](https://wakatime.com/badge/user/516374bf-c07b-49d5-9972-bc15f71c20a3/project/1ef1b07f-5a35-40ce-8aa3-37f3594a40df.svg)](https://wakatime.com/badge/user/516374bf-c07b-49d5-9972-bc15f71c20a3/project/1ef1b07f-5a35-40ce-8aa3-37f3594a40df)

