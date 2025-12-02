# Xiangqi  
**12.2 开发记录**

## ✅ 已完成：

### 1. ChessBoard  
- 初始化数组 `board`，用于表示棋盘上的空位与棋子位置。

### 2. Piece  
- 可通过 `name` 和 `position` 唯一确定棋子。  
- 目前为基础类，后续计划改为抽象类。  
- 棋子子类包括：  
  - Advisor  
  - Cannon  
  - Elephant  
  - Horse  
  - King  
  - Rook  
  - Soldier  

### 3. Player（枚举类型）  
- 成员：`RED`、`BLACK`

### 4. PieceFactory  
- 负责创建 32 个棋子对象。  
- 初始化棋子位置。  
- 将棋子存储在 `List` 中。

### 5. Position  
- 表示棋子在棋盘上的坐标位置。

### 6. CriticalTest  
- 用于测试初始化流程的测试类，非核心模块。
    
