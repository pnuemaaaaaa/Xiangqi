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

**12.4 开发记录**
根据你的代码，我来总结一下**已完成的任务**：

## ✅ **已完成的核心功能**

### 1. **基础架构搭建**
- 象棋棋子类层次结构（抽象类`Piece` + 具体子类）
- 棋子类型：帅/将、仕/士、相/象、马/馬、车/車、炮、兵/卒
- 坐标系统（`Position`类）
- 玩家枚举（`Player.RED/BLACK`）

### 2. **棋盘和棋子初始化**
- 工厂模式创建所有32个棋子（`PieceFactory`）
- 初始位置正确设置（符合中国象棋标准布局）
- 红黑双方棋子名称正确区分（简体/繁体）

### 3. **棋子移动规则（部分实现）**
- **兵/卒**：已实现过河前后移动规则
- **马**：已实现"日"字走法和"蹩马腿"检查
- **象**：已实现"田"字走法和"塞象眼"检查
- **炮**：已实现炮的特殊吃子规则（需要一个炮架）
- **仕/士**：已实现九宫内斜线移动
- **帅/将**：已实现九宫内一步移动

### 4. **图形界面（两个版本）**
#### 版本一：`ChessGameGUI`（功能更全）
- 美观的棋盘绘制（楚河汉界、坐标标记）
- 棋子点击选择和移动
- 被吃棋子展示区域
- 回合切换显示
- 重新开始和退出功能
- 游戏结束判断（将帅被吃）

#### 版本二：`ChessBoardPanel`（简洁版）
- 基础棋盘绘制
- 棋子选择和移动
- 选中高亮效果

### 5. **游戏逻辑基础**
- 棋子碰撞检测
- 吃子逻辑（从棋盘移除被吃棋子）
- 位置合法性检查
- 棋子存活状态管理

---

## ⚠️ **需要完善的部分**

### 1. **移动规则不完整**
- **车**：`Rook.canMoveTo()`中缺少路径阻挡检查
- **所有棋子**：缺少将帅对面（"将军"）的特殊规则
- **缺少合法性验证**：不能送将、不能被将军状态下走棋等

### 2. **游戏逻辑不完整**
- 缺少将军/将死判断
- 缺少和棋规则（长将、长捉等）
- 缺少行棋合法性检查（如送将、自困等）

### 3. **代码质量**
- 部分方法命名不一致（如`PiecesI()`）
- 包名拼写错误（`Initialiazation`应为`Initialization`）
- 部分注释为中文，建议统一

---

## 📊 **当前状态总结**

你的代码已经完成了**中国象棋的70%基础功能**：

- ✅ **核心数据结构**：棋子、棋盘、位置
- ✅ **图形界面**：两个可运行的GUI版本
- ✅ **基本规则**：各棋子的移动方式
- ✅ **交互逻辑**：点击、选择、移动

- ❌ **高级规则**：将军、将死、特殊局面
- ❌ **完整验证**：行棋合法性全面检查
- ❌ **代码优化**：命名规范、错误处理

---

## 🎯 **下一步建议**

1. **优先完成车的移动规则**（最简单）
2. **添加将军/将死判断**
3. **统一两个GUI版本**（选择一个继续开发）
4. **添加游戏状态管理**（胜负判断、回合记录）

整体来说，你已经建立了很好的基础，可以在此基础上逐步完善剩余功能。

每个类中的所有方法名列表：
## **Piece.java** (抽象类)
- **构造方法**:
  - `Piece()`
  - `Piece(String name, Player player)`
  - `Piece(String name, Position position, Player player)`
  
- **公共方法**:
  - `getName()`
  - `setName(String name)`
  - `getPosition()`
  - `setPosition(Position position)`
  - `getPlayer()`
  - `setPlayer(Player player)`
  - `isAlive()`
  - `setAlive(boolean alive)`
  - `getPiece(String name)`
  
- **抽象方法**:
  - `canMoveTo(int newX, int newY, List<Piece> allPieces)`
  - `moveTo(int newX, int newY, List<Piece> allPieces)`
  - `canBeEaten(Piece p)`
  - `beEaten(Piece p)`
  
- **保护方法**:
  - `getPieceAt(int x, int y, List<Piece> allPieces)`
  - `isStraightLine(int x1, int y1, int x2, int y2)`
  - `hasPieceBetween(int x1, int y1, int x2, int y2, List<Piece> allPieces)`
  - `isEnemy(Piece p)`
  - `getCurrentX()`
  - `getCurrentY()`
  - `isWithinBoard(int x, int y)`
  - `isSamePosition(int x, int y)`
  - `isSamePlayer(Piece p)`
  - `getStraightDistance(int x1, int y1, int x2, int y2)`

---

## **Position.java**
- **构造方法**:
  - `Position()`
  - `Position(int x, int y)`
  
- **公共方法**:
  - `getX()`
  - `setX(int x)`
  - `getY()`
  - `setY(int y)`

---

## **Player.java** (枚举)
- **方法**:
  - `getDisplayName()`

---

## **PieceFactory.java**
- **方法**:
  - `createSpecificPiece(String pieceName, int x, int y, Player player)`
  - `RedPiecesI()`
  - `BlackPiecesI()`
  - `PiecesI()`

---

## **King.java** (继承自Piece)
- **构造方法**:
  - `King(Position position, Player player)`
  
- **实现的方法**:
  - `canMoveTo(int newX, int newY, List<Piece> allPieces)`
  - `moveTo(int newX, int newY, List<Piece> allPieces)`
  - `canBeEaten(Piece p)`
  - `beEaten(Piece p)`
  
- **私有方法**:
  - `isInPalace(int x, int y)`

---

## **Advisor.java** (继承自Piece)
- **构造方法**:
  - `Advisor(Position position, Player player)`
  
- **实现的方法**:
  - `canMoveTo(int newX, int newY, List<Piece> allPieces)`
  - `moveTo(int newX, int newY, List<Piece> allPieces)`
  - `canBeEaten(Piece p)`
  - `beEaten(Piece p)`
  
- **私有方法**:
  - `isInPalace(int x, int y)`

---

## **Elephant.java** (继承自Piece)
- **构造方法**:
  - `Elephant(Position position, Player player)`
  
- **实现的方法**:
  - `canMoveTo(int newX, int newY, List<Piece> allPieces)`
  - `moveTo(int newX, int newY, List<Piece> allPieces)`
  - `canBeEaten(Piece p)`
  - `beEaten(Piece p)`
  
- **私有方法**:
  - `isOnSameSide(int newY)`

---

## **Horse.java** (继承自Piece)
- **构造方法**:
  - `Horse(Position position, Player player)`
  
- **实现的方法**:
  - `canMoveTo(int newX, int newY, List<Piece> allPieces)`
  - `moveTo(int newX, int newY, List<Piece> allPieces)`
  - `canBeEaten(Piece p)`
  - `beEaten(Piece p)`

---

## **Rook.java** (继承自Piece)
- **构造方法**:
  - `Rook(Position position, Player player)`
  
- **实现的方法**:
  - `canMoveTo(int newX, int newY, List<Piece> allPieces)`
  - `moveTo(int newX, int newY, List<Piece> allPieces)`
  - `canBeEaten(Piece p)`
  - `beEaten(Piece p)`

---

## **Cannon.java** (继承自Piece)
- **构造方法**:
  - `Cannon(Position position, Player player)`
  
- **实现的方法**:
  - `canMoveTo(int newX, int newY, List<Piece> allPieces)`
  - `moveTo(int newX, int newY, List<Piece> allPieces)`
  - `canBeEaten(Piece p)`
  - `beEaten(Piece p)`

---

## **Soldier.java** (继承自Piece)
- **构造方法**:
  - `Soldier(Position position, Player player)`
  
- **实现的方法**:
  - `canMoveTo(int newX, int newY, List<Piece> allPieces)`
  - `moveTo(int newX, int newY, List<Piece> allPieces)`
  - `canBeEaten(Piece p)`
  - `beEaten(Piece p)`

---

## **ChessBoardModel.java**
- **构造方法**:
  - `ChessBoardModel()`
  
- **静态方法**:
  - `getX()`
  - `getY()`
  
- **公共方法**:
  - `getPieces()`
  - `getPieceAt(int x, int y)`
  - `isValidPosition(int x, int y)`
  - `movePiece(Piece piece, int newX, int newY, List<Piece> allPieces)`
  
- **私有方法**:
  - `initializePieces()`

---

## **ChessBoardPanel.java** (JPanel子类)
- **构造方法**:
  - `ChessBoardPanel(ChessBoardModel model)`
  
- **覆盖方法**:
  - `paintComponent(Graphics g)`
  
- **私有方法**:
  - `handleMouseClick(int x, int y)`
  - `drawBoard(Graphics2D g)`
  - `drawPieces(Graphics2D g)`
  - `drawCornerBorders(Graphics2D g, int centerX, int centerY)`

---

## **ChessGameGUI.java** (JFrame子类)
- **构造方法**:
  - `ChessGameGUI()`
  
- **主要方法**:
  - `main(String[] args)`
  
- **私有方法**:
  - `initializeUI()`
  - `initializeBoard()`
  - `createPlayerPanel(String playerName, Color color)`
  - `styleButton(JButton button)`
  - `drawBoard(Graphics g)`
  - `drawPieces(Graphics g)`
  - `drawSelection(Graphics g)`
  - `handleBoardClick(int clickX, int clickY)`
  - `isValidMove(Piece piece, int targetX, int targetY)`
  - `capturePiece(Piece piece)`
  - `updateCapturedDisplay()`
  - `checkGameEnd()`
  - `restartGame()`

---

## **XiangqiApplication.java**
- **方法**:
  - `main(String[] args)`

---
    
