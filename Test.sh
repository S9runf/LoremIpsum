read  player1 player2

echo 3 3 3: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 3 3 3 $player1 $player2 -t 10 -r 10
java -cp ".." mnkgame.MNKPlayerTester 3 3 3 $player2 $player1 -t 10 -r 10

echo 4 3 3: Expected Victory
java -cp ".." mnkgame.MNKPlayerTester 4 3 3 $player1 $player2 -t 10 -r 10
java -cp ".." mnkgame.MNKPlayerTester 4 3 3 $player2 $player1 -t 10 -r 10

echo 4 4 3: Expected Victory
java -cp ".." mnkgame.MNKPlayerTester 4 4 3 $player1 $player2 -t 10 -r 10
java -cp ".." mnkgame.MNKPlayerTester 4 4 3 $player2 $player1 -t 10 -r 10

echo 4 4 4: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 4 4 4 $player1 $player2 -t 10 -r 10
java -cp ".." mnkgame.MNKPlayerTester 4 4 4 $player2 $player1 -t 10 -r 10

echo 5 4 4: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 5 4 4 $player1 $player2 -t 10 -r 10
java -cp ".." mnkgame.MNKPlayerTester 5 4 4 $player2 $player1 -t 10 -r 10

echo 5 5 4: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 5 5 4 $player1 $player2 -t 10 -r 10
java -cp ".." mnkgame.MNKPlayerTester 5 5 4 $player2 $player1 -t 10 -r 10

echo 5 5 5: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 5 5 5 $player1 $player2 -t 10 -r 10
java -cp ".." mnkgame.MNKPlayerTester 5 5 5 $player2 $player1 -t 10 -r 10

echo 6 4 4: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 6 4 4 $player1 $player2 -t 10 -r 10
java -cp ".." mnkgame.MNKPlayerTester 6 4 4 $player2 $player1 -t 10 -r 10

echo 6 5 4: Expected Victory
java -cp ".." mnkgame.MNKPlayerTester 6 5 4 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 6 5 4 $player2 $player1 -t 10 -r 5

echo 6 6 4: Expected Victory
java -cp ".." mnkgame.MNKPlayerTester 6 6 4 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 6 6 4 $player2 $player1 -t 10 -r 5

echo 6 6 5: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 6 6 5 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 6 6 5 $player2 $player1 -t 10 -r 5

echo 6 6 6: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 6 6 6 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 6 6 6 $player2 $player1 -t 10 -r 5

echo 7 4 4: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 7 4 4 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 7 4 4 $player2 $player1 -t 10 -r 5

echo 7 5 4: Expected Victory
java -cp ".." mnkgame.MNKPlayerTester 7 5 4 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 7 5 4 $player2 $player1 -t 10 -r 5

Echo 7 6 4: Expected Victory
java -cp ".." mnkgame.MNKPlayerTester 7 6 4 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 7 6 4 $player2 $player1 -t 10 -r 5

echo 7 7 4: Expected Victory
java -cp ".." mnkgame.MNKPlayerTester 7 7 4 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 7 7 4 $player2 $player1 -t 10 -r 5

echo 7 5 5: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 7 5 5 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 7 5 5 $player2 $player1 -t 10 -r 5

echo 7 6 5: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 7 6 5 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 7 6 5 $player2 $player1 -t 10 -r 5

echo 7 7 5: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 7 7 5 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 7 7 5 $player2 $player1 -t 10 -r 5

echo 7 7 6: Expected Draw
java -cp ".." mnkgame.MNKPlayerTester 7 7 6 $player1 $player2 -t 10 -r 5
java -cp ".." mnkgame.MNKPlayerTester 7 7 6 $player2 $player1 -t 10 -r 5

echo 7 7 7: Expected ???
java -cp ".." mnkgame.MNKPlayerTester 7 7 7 $player1 $player2 -t 10 -r 1
java -cp ".." mnkgame.MNKPlayerTester 7 7 7 $player2 $player1 -t 10 -r 1

echo 8 8 4: Expected Victory
java -cp ".." mnkgame.MNKPlayerTester 8 8 4 $player1 $player2 -t 10 -r 1
java -cp ".." mnkgame.MNKPlayerTester 8 8 4 $player2 $player1 -t 10 -r 1

echo 10 10 5: Expected ???
java -cp ".." mnkgame.MNKPlayerTester 10 10 5 $player1 $player2 -t 10 -r 1
java -cp ".." mnkgame.MNKPlayerTester 10 10 5 $player2 $player1 -t 10 -r 1

echo 50 50 10: Expected ???
java -cp ".." mnkgame.MNKPlayerTester 50 50 10 $player1 $player2 -t 10 -r 1
java -cp ".." mnkgame.MNKPlayerTester 50 50 10 $player2 $player1 -t 10 -r 1

echo 70 70 10: Expected ???
java -cp ".." mnkgame.MNKPlayerTester 70 70 10 $player1 $player2 -t 10 -r 1
java -cp ".." mnkgame.MNKPlayerTester 70 70 10 $player2 $player1 -t 10 -r 1
