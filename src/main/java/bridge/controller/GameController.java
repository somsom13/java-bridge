package bridge.controller;

import bridge.BridgeRandomNumberGenerator;
import bridge.domain.Bridge;
import bridge.domain.BridgeGame;
import bridge.domain.bridgeenum.Restart;
import bridge.util.BridgeMaker;
import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.List;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BridgeMaker bridgeMaker;
    private BridgeGame bridgeGame;
    private boolean isPlaying;

    public GameController() {
        inputView = new InputView();
        outputView = new OutputView();
        bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
        initializeGame();
    }

    private void initializeGame() {
        bridgeGame = new BridgeGame(createNewBridge());
        isPlaying = true;
    }

    private Bridge createNewBridge() {
        while (true) {
            try {
                return new Bridge(generateBridge());
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void play() {
        while (isPlaying) {
            move();
            printMoveResult();
            checkGameEnd();
        }
    }

    private void move() {
        String nextPosition;
        while (true) {
            try {
                nextPosition = inputView.readMoving();
                bridgeGame.move(nextPosition); //그리고 bridgeGame 내에서 성공 여부 연산, 이동한 내역 업데이트
                break;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private List<String> generateBridge() {
        return bridgeMaker.makeBridge(requestBridgeLength());
    }

    private int requestBridgeLength() {
        return inputView.readBridgeSize();
    }

    private void printMoveResult() {
        outputView.printMap(bridgeGame.getCurrentMap());
    }

    private void checkGameEnd() {
        if (bridgeGame.isGameOver()) {
            requestGameRestart();
            return;
        }
        if (bridgeGame.isGamePass()) {
            isPlaying = false;
        }
    }

    private void requestGameRestart() {
        String restartCommand;
        while (true) {
            try {
                restartCommand = inputView.readGameCommand();
                isPlaying = Restart.isRestart(restartCommand);
                break;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

}
