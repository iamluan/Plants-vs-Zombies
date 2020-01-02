package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;
import model.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GamePlay {

    @FXML
    private AnchorPane GamePlayRoot;
    @FXML
    private ImageView lawnImage;
    @FXML
    private ImageView peaShooterBuy;
    @FXML
    private ImageView repeaterBuy;
    @FXML
    private Label sunScoreLabel;
    @FXML
    private ImageView GameMenuLoaderButton;
    @FXML
    private int levelNumber;
    @FXML
    private GridPane lawn_grid;
    /**
     * Set lanes in the yard
     */
    private static int sunScore = 50;
    public static final int LANE1 = 50;
    public static final int LANE2 = 150;
    public static final int LANE3 = 250;
    public static final int LANE4 = 350;
    public static final int LANE5 = 450;
    public static boolean gameStatus;
    public static Timeline spawnZombieTimeline;
    private static Label sunScoreLabelControl;
    public static List allZombies;
    public static List allPlants;
    public static ArrayList<Timeline> animationTimelines;
    public ArrayList<PlantCard> plantCards = new ArrayList<>();
    public int spawnedZombies = 0;
    /**
     *   Initialize all the data in the game screen
     */
    public void createGame(){
        Random rand = new Random();
        animationTimelines = new ArrayList<Timeline>();
        sunScoreLabelControl.setText(String.valueOf(sunScore));
        SidebarElement.getSidebarElements(GamePlayRoot);
        createFallingSuns(rand);
        normalZombieGenerator(rand, 10);
        coneHeadZombieGenerator(rand, 16);
        bucketZombieGenerator(rand, 35);
        startAnimations();
    }

    public void startAnimations()
    {
        synchronized (allPlants) {
            Iterator<Plant> i = allPlants.iterator();
            while (i.hasNext()) {
                Plant p = i.next();
                p.act(GamePlayRoot);
                p.eaten();
            }
        }
        synchronized (allZombies)
        {
            Iterator<Zombie> i = allZombies.iterator();
            while(i.hasNext())
            {
                Zombie z = i.next();
                z.drawImage(GamePlayRoot);
                z.moveZombie();
            }
        }
    }

    public void initialize() throws Exception {

        String waveFile = "src/resource/sound/zombies_coming.wav";
        Media wave = new Media(new File(waveFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(wave);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(5));
        mediaPlayer.play();

        gameStatus = true;
        sunScoreLabelControl = sunScoreLabel;
        allZombies = Collections.synchronizedList(new ArrayList<Zombie>());
        allPlants = Collections.synchronizedList(new ArrayList<Plant>());
    }
    @FXML
    void getGridPosition(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Integer colIndex = lawn_grid.getColumnIndex(source);
        Integer rowIndex = lawn_grid.getRowIndex(source);

        if(SidebarElement.getCardSelected() != -1) {
            if (colIndex != null && rowIndex != null) {
                boolean flag = true;
                synchronized (allPlants) {
                    Iterator<Plant> plantIterator = allPlants.iterator();
                    while (plantIterator.hasNext()) {
                        Plant plant = plantIterator.next();
                        if (plant.getCol() == colIndex && plant.getRow() == rowIndex) {
                            flag = false;
                        }
                    }
                }
                if (flag && sunScore >= SidebarElement.getElement(SidebarElement.getCardSelected()).getCost()) {
                    plants(SidebarElement.getCardSelected(),
                            (int) (source.getLayoutX() + source.getParent().getLayoutX()),
                            (int) (source.getLayoutY() + source.getParent().getLayoutY()),
                            colIndex, rowIndex);
                    updateSunScore((-1) * SidebarElement.getElement(SidebarElement.getCardSelected()).getCost());
                    SidebarElement.getElement(SidebarElement.getCardSelected()).setDisabledOn(GamePlayRoot);
                }
                SidebarElement.setCardSelectedToNull();
            }
        }
    }
    public void plants(int type, int x, int y, int col, int row ){
        Plant p;
        switch (type){
            case 1:
                p = new Sunflower(x, y, col, row);
                allPlants.add(p);
                p.drawImage(lawn_grid);
                p.act(GamePlayRoot);
                break;
            case 2:
                p = new Peashooter(x, y, col, row);
                allPlants.add(p);
                p.drawImage(lawn_grid);
                p.act(GamePlayRoot);
                break;
        }
    }
    /**
     *
     * @param t : Time to spawn new zombie(by seconds)
     */
    public void normalZombieGenerator(Random rand, double t) {
        Timeline spawnZombie = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            int lane;
            int laneNumber = rand.nextInt(5);
            if (laneNumber == 0)
                lane = LANE1;
            else if (laneNumber == 1)
                lane = LANE2;
            else if (laneNumber == 2)
                lane = LANE3;
            else if (laneNumber == 3)
                lane = LANE4;
            else
                lane = LANE5;

            spawnNormalZombie(GamePlayRoot, lane, laneNumber);
        }));
        spawnZombie.setCycleCount(Timeline.INDEFINITE);
        spawnZombie.play();
        spawnZombieTimeline = spawnZombie;
        animationTimelines.add(spawnZombie);

    }

    public void coneHeadZombieGenerator(Random rand, double t) {
        Timeline spawnZombie = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            int lane;
            int laneNumber = rand.nextInt(5);
            if (laneNumber == 0)
                lane = LANE1;
            else if (laneNumber == 1)
                lane = LANE2;
            else if (laneNumber == 2)
                lane = LANE3;
            else if (laneNumber == 3)
                lane = LANE4;
            else
                lane = LANE5;

            spawnConeHeadZombie(GamePlayRoot, lane, laneNumber);
        }));
        spawnZombie.setCycleCount(Timeline.INDEFINITE);
        spawnZombie.play();
        //spawnNormalZombieTimeline = spawnZombie;
        animationTimelines.add(spawnZombie);
        updateSpawnedZombie();
    }

    public void bucketZombieGenerator(Random rand, double t) {
        Timeline spawnZombie = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            int lane;
            int laneNumber = rand.nextInt(5);
            if (laneNumber == 0)
                lane = LANE1;
            else if (laneNumber == 1)
                lane = LANE2;
            else if (laneNumber == 2)
                lane = LANE3;
            else if (laneNumber == 3)
                lane = LANE4;
            else
                lane = LANE5;

            spawnBucketZombie(GamePlayRoot, lane, laneNumber);
        }));
        spawnZombie.setCycleCount(Timeline.INDEFINITE);
        spawnZombie.play();
        //spawnNormalZombieTimeline = spawnZombie;
        animationTimelines.add(spawnZombie);
        updateSpawnedZombie();
    }


    public static void spawnNormalZombie(Pane pane, int lane, int laneNumber)
    {
        NormalZombie zombie = new NormalZombie(1024, lane, laneNumber); // The x location of the outer right of the yard is 1024
        zombie.drawImage(pane);
        zombie.moveZombie();
        GamePlay.allZombies.add(zombie);
    }

    public static void spawnConeHeadZombie(Pane pane, int lane, int laneNumber)
    {
        ConehHeadZombie zombie = new ConehHeadZombie(1024, lane, laneNumber);
        zombie.drawImage(pane);
        GamePlay.allZombies.add(zombie);
        zombie.moveZombie();
    }

    public static void spawnBucketZombie(Pane pane, int lane, int laneNumber)
    {
        BucketZombie zombie = new BucketZombie(1024, lane, laneNumber);
        zombie.drawImage(pane);
        GamePlay.allZombies.add(zombie);
        zombie.moveZombie();
    }

    public void updateSpawnedZombie() {
        this.spawnedZombies += 1;
    }

    public int getNumSpawnedZombie() {
        return spawnedZombies;
    }




    public static void updateSunScore(int numOfSunAdded) {
        sunScore += numOfSunAdded;
        getSunScoreLabel().setText(Integer.toString(sunScore));
    }

    public static Label getSunScoreLabel() {
        return sunScoreLabelControl;
    }

    public static void removeZombie(Zombie zombie){
        zombie.image.setVisible(false);
        allZombies.remove(zombie);
    }

    // generate falling suns
    public void createFallingSuns(Random rand){
        Timeline fallingSuns= new Timeline(new KeyFrame(Duration.seconds(7), actionEvent -> {
            Sun s = new Sun(rand.nextInt(850) + 150, 0, true);
            s.drawImage(GamePlayRoot);
            s.dropSun();
        }));
        fallingSuns.setCycleCount(Timeline.INDEFINITE);
        fallingSuns.play();
        animationTimelines.add(fallingSuns);
    }

    public void endGame(){
        for(Timeline timeline : animationTimelines){
            timeline.stop();
        }
    }
}
