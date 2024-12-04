
# Tic-Tac-Toe Game - JavaFX

## Overview

The **Tic-Tac-Toe** game is a classic 2-player game developed using **JavaFX**. This version of the game includes multiple features such as single-player and two-player modes, customizable themes, sound effects, feedback submission, difficulty levels, and an AI opponent with varying difficulty levels. The game uses a graphical user interface (GUI) to provide an interactive experience.

## Features

1. **Two Modes:**
   - **Single Player**: Play against the AI.
   - **Two Players**: Play with another human player on the same machine.
   
2. **Difficulty Levels for AI:**
   - **Easy**: AI picks random moves.
   - **Medium**: AI picks random moves, but occasionally uses some smart strategies.
   - **Hard**: AI plays optimally, trying to win, block the player, or make the best move.
   
3. **Themes:**
   - **Default**
   - **Light**
   - **Dark**
   - **Gradient**

4. **Sound Effects**:
   - Background sounds for various events such as winning, drawing, and making a move.
   - Sound can be muted/unmuted via the "Mute" button.

5. **Feedback System**:
   - After completing a game, players can provide feedback through a smiley rating system (😀, 😐, 😞) and leave a comment.

6. **Responsive Layout**:
   - The game layout adjusts to the screen size, with all UI components organized in an easy-to-use format.

7. **Added Test Cases**
   - I have also created 2 basic test cases to make TDD.

## Technologies Used

- **JavaFX**: For building the graphical user interface (GUI) and handling events.
- **AudioClip**: For playing sound effects like winning, making a move, or drawing.
- **File I/O**: Writing feedback to a file (`feedback.txt`).
- **Animations**: Implemented using `Timeline` and `PauseTransition` to simulate AI thinking or highlight winning moves.
- **ComboBox**: Used for selecting themes, difficulty levels, and game modes.
- **RadioButtons**: For selecting feedback ratings using smiley faces.

## Java Concepts Used

1. **Object-Oriented Programming (OOP)**
2. **Event Handling**
3. **Collections**
4. **Control Flow**
5. **JavaFX Layouts**
6. **Concurrency**
7. **File Handling**
8. **Regular Expressions**
9. **Audio Handling**
10. **Validation**
11. **Exception Handeling**

## Installation and Setup

1. Clone the repository or download the project files.
2. Make sure you have **Java 11 or higher** installed on your system.
3. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
4. Add the necessary JavaFX libraries if they aren't bundled with your JDK.
5. Build and run the project.


