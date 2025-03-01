# Bingo Slot Game

## Overview

Bingo Slot Game is a server-side application developed using Spring Boot. It manages a Bingo-based slot game, allowing users to place bets, track game progress, and maintain historical data using Redis and MySQL.

## Features

- Users can place bets on different rows and columns.
- The game processes user bets and determines winners.
- Game state management to control different phases (**NoGame, Bets Open, In-Game**).
- Tracks game statistics and bet frequency using Redis and MySQL.
- Stores game history for analysis.
- Randomized number selection each round.
- 3x3 slot-style gameplay mechanics.
- Line completion for winnings.
- Multiplier bonus for connected lines.
- Hint system to show possible winning numbers and their respective win amounts.
- Risk-reward system with strategic marking.

## Implementation Details

- The **main logic** of the game is implemented using **multithreading**, where **vertical and horizontal operations** are processed separately to ensure efficient execution.
- A **linked list** is used as the primary data structure, as it provides an optimal approach for managing and traversing game states.
- A **Scheduler Service** is used for managing time constraints, particularly for tracking the **bets open duration** and transitioning between game states.
- A **Configuration File** is included in `java/com.bingo.slotGame` to manage game settings and environment variables.

## Gameplay Mechanics

- The game is played on a **3x3 grid**:
  ```
  1   15   21  
  4   16   24  
  7   19   27  
  ```
- Each turn, a **random number** from the matrix is selected.
- If the number **is not marked**, it gets **marked**.
- The game continues until all numbers are marked.

## Winning Conditions

- Completing a **full row or column** grants **money equal to the last number** that completed it.
- If the new line **touches another previously completed line**, a **multiplier is applied**:

  **Win = (Number of connected lines) × (Last number used to complete the line)**
- Multiple lines can be completed in a single turn, increasing the reward potential.

## Example Rounds

### Scenario 1: Simple Line Completion

1. **Spin: "4"** → Marked ✅  *(No win yet)*
2. **Spin: "7"** → Marked ✅ *(Still no full row)*
3. **Spin: "1"** → **Row completed!** → **Win: 1** 💰

### Scenario 2: Multiplier Bonus

1. **Spin: "6"** → Marked ✅ *(No win yet)*
2. **Spin: "9"** → Marked ✅ *(Still no full row)*
3. **Spin: "11"** → **Row completed!** → **Win: 11** 💰
4. **Spin: "21"** → **Row + Column completed!** → **Multiplier applied!** → **Win: 42** 🎉

### Hint System

- After every round, the game will display **possible numbers that can complete a line** and their respective **win amounts**.
- Example:
  - If **4 and 7** are marked, the game will suggest **"Mark 1 to complete the row"** and display the **potential win amount (1)**.
  - If **6 and 9** are marked, the game will suggest **"Mark 11 to complete the row"** and display the **potential win amount (11)**.
  - If **15 and 21** are marked, the game will suggest **"Mark 8 to complete the row"** and display the **potential win amount (8)**.

## Technologies Used

- **Java & Spring Boot** - Backend development
- **Redis** - Caching and user session management
- **MySQL** - Persistent data storage
- **JDBC Template** - Database interactions
- **Web API (RESTful Services)** - User interaction through HTTP requests

## Installation & Setup

### Prerequisites

Ensure you have the following installed:

- Java 17+
- Maven
- MySQL Database
- Redis Server

### Steps to Run

1. Clone the repository:
   ```sh
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```sh
   cd Bingo-SlotGame
   ```
3. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bingo_game
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.redis.host=localhost
   spring.redis.port=6379
   ```
4. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

### Game Management

- **Start a new game:** `POST /Bingo/newGame`
  ![image](https://github.com/user-attachments/assets/3852e288-5a14-4d7c-9e7c-58af74e33c38)

- **Close an ongoing game:** `POST /Bingo/close`
- **Submit a bet:** `POST /Bingo/bet`
  - Parameters: `userId`, `Row1`, `Row2`, `Row3`, `Column1`, `Column2`, `Column3`
  - if it was in the BetsOpen Stage (10 Sec)
    ![image](https://github.com/user-attachments/assets/296d8178-a5e1-49ae-ac3a-da9fce779d27)
    ![image](https://github.com/user-attachments/assets/3fbd826a-b4fb-40bf-9f94-748da684f27c)

  - if bets Closed and stage went to InGame
    ![image](https://github.com/user-attachments/assets/3ed35eaa-bf76-455a-b761-4216e94a2a55)


- **Submit a number for the game:** `POST /Bingo/number/{number}`
  ![image](https://github.com/user-attachments/assets/30381307-7513-48b2-a36a-fbf877880735)
  ![image](https://github.com/user-attachments/assets/e906526b-4eef-49c5-95e5-f7469e9b5070)
  ![image](https://github.com/user-attachments/assets/376bcb3a-29c5-4721-bf9c-921b32245774)
  ![image](https://github.com/user-attachments/assets/84ed5700-f2ae-46c9-a85f-133680ca1676)
  ![image](https://github.com/user-attachments/assets/a86606d6-1f2c-4499-89d3-cf4ebd04bb8d)
  ![image](https://github.com/user-attachments/assets/aa501fa1-67ae-41ac-a87c-decee369b40e)
  ![image](https://github.com/user-attachments/assets/a403527b-893e-47e7-a9ba-89a2496ae828)








### Game Data

- **Game history:** `GET /Bingo/history`
- **Number frequency:** `GET /Bingo/frequency`
- **Line frequency:** `GET /Bingo/linefrequency`

## Database Structure

### Tables Used

- `bet_summary` - Stores bet and net change details
- `number_frequency` - Tracks occurrence of drawn numbers
- `line_frequency` - Records winning line occurrences
- `game_count` - Maintains total game count
- `game_id_storage` - Stores game ID

## Project Structure

```
Bingo-SlotGame/
│── src/
│   ├── main/
│   │   ├── java/com/Bingo/SlotGame/
│   │   │   ├── Controller/
│   │   │   ├── DAO/
│   │   │   ├── Entity/
│   │   │   ├── Model/
│   │   │   ├── Repository/
│   │   │   ├── Service/
│   │   │   ├── Config/
│   ├── resources/
│   │   ├── application.properties
│── pom.xml
```

## Future Enhancements

- Implement WebSocket for real-time game updates.
- Enhance UI with a frontend framework (React or Angular).
- Improve database performance using indexing and query optimization.


