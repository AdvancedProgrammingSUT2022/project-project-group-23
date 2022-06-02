package view_graphic;

import controller.CityController;
import controller.CivilizationController;
import controller.UnitController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.GraphicTile;
import model.Tile;
import model.User;

public class Game {
    @FXML
    private AnchorPane tileMap;
    @FXML
    private HBox bar;
    private GraphicTile[][] tiles;
    private double size = 100,v=Math.sqrt(3)/2.0;
    private CivilizationController civilizationController;
    private UnitController unitController;
    private CityController cityController;
    private VBox tileInformation;

    public void initialize(){
        Platform.runLater(()->tileMap.requestFocus());
        civilizationController=new CivilizationController(GameMenuPage.players);
        unitController=civilizationController.getUnitController();
        cityController=civilizationController.getCityController();
        bar.setMinHeight(70);
        bar.setMinWidth(1280);
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource("/images/backgrounds/topBar.png").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                backgroundSize);
        bar.setBackground(new Background(backgroundImage));
        bar.setSpacing(30);
        Text userNickname=new Text(User.getUserLogged().getNickname());
        userNickname.setY(45);
        userNickname.getStyleClass().add("info");
        bar.getChildren().add(userNickname);
        Circle gold=new Circle(30);
        gold.setCenterY(15);
        ImagePattern goldImage=new ImagePattern(new Image(getClass().getResource("/images/info/Gold.png").toExternalForm()));
        gold.setFill(goldImage);
        bar.getChildren().add(gold);
        Text goldAmount=new Text("Gold:  "+civilizationController.getCurrentPlayer().getGold());
        goldAmount.setY(45);
        goldAmount.getStyleClass().add("info");
        bar.getChildren().add(goldAmount);
        Circle happiness=new Circle(30);
        happiness.setCenterY(15);
        ImagePattern happinessImage=new ImagePattern(new Image(getClass().getResource("/images/info/Happiness.png").toExternalForm()));
        happiness.setFill(happinessImage);
        bar.getChildren().add(happiness);
        Text happinessAmount=new Text("Happiness:  "+civilizationController.getCurrentPlayer().getHappiness());
        happinessAmount.setY(45);
        happinessAmount.getStyleClass().add("info");
        bar.getChildren().add(happinessAmount);
        Circle science=new Circle(30);
        science.setCenterY(15);
        ImagePattern scienceImage=new ImagePattern(new Image(getClass().getResource("/images/info/Science.png").toExternalForm()));
        science.setFill(scienceImage);
        bar.getChildren().add(science);
        Text scienceAmount=new Text("Science:  "+civilizationController.getCurrentPlayer().totalCup());
        scienceAmount.setY(45);
        scienceAmount.getStyleClass().add("info");
        bar.getChildren().add(scienceAmount);
        Button nextTurn=new Button("Next Turn");
        nextTurn.getStyleClass().add("primary-btn");
        nextTurn.setMaxWidth(100);
        bar.getChildren().add(nextTurn);
//        nextTurn.setOnMouseClicked(mouseEvent -> {
//            System.out.println(civilizationController.nextTurn());
//            tileMap.requestFocus();
//        });
        tileMap.setOnKeyPressed(this::move);
        tiles=new GraphicTile[civilizationController.getMapHeight()][civilizationController.getMapWidth()];
        Tile[][] modelTiles= civilizationController.getTiles();
        for(double y=0,i=0;i<civilizationController.getMapHeight();y+=size*Math.sqrt(3),i+=1)
        {
            for(double x=size/2,dy=y,j=0;j<civilizationController.getMapWidth();x+=(3.0/2.0)*size,j+=1)
            {
                GraphicTile tile = new GraphicTile(x,dy,
                        x+size,dy,
                        x+size*(3.0/2.0),dy+size*v,
                        x+size,dy+size*Math.sqrt(3),
                        x,dy+size*Math.sqrt(3),
                        x-(size/2.0),dy+size*v,(int)i+","+(int)j,modelTiles[(int)i][(int)j],tileMap,civilizationController);
                ImagePattern imagePattern=new ImagePattern(new Image(getClass().getResource("/images/tile/"+modelTiles[(int)i][(int)j].getTerrain().getName()+".png").toExternalForm()));
                tile.setFill(imagePattern);
                if(modelTiles[(int) i][(int) j].getVisibilityForUser(civilizationController.getTurn()).equals("fog of war")){
                    ImagePattern fog=new ImagePattern(new Image(getClass().getResource("/images/tile/Fog.png").toExternalForm()));
                    tile.setFill(fog);
                }
                tile.setStroke(Color.rgb(15, 65, 135));
                tile.setStrokeWidth(4);
                tileMap.getChildren().add(tile);
                if(modelTiles[(int) i][(int) j].getFeature()!=null && !modelTiles[(int) i][(int) j].getVisibilityForUser(civilizationController.getTurn()).equals("fog of war")){
                    ImagePattern imagePattern1=new ImagePattern(new Image(getClass().getResource("/images/tile/"+modelTiles[(int)i][(int)j].getFeature().getName()+".png").toExternalForm()));
                    tile.getFeature().setFill(imagePattern1);
                    tileMap.getChildren().add(tile.getFeature());
                }
                tileMap.getChildren().add(tile.getLocation());
                tiles[(int) i][(int) j]=tile;
                VBox infos=new VBox();
                infos.setMinHeight(60);
                infos.setMinWidth(200);
                BackgroundImage backgroundImage1 = new BackgroundImage(new Image(getClass().getResource("/images/backgrounds/loginBackground.png").toExternalForm()),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        backgroundSize);
                infos.setBackground(new Background(backgroundImage1));
                if(tile.getTile().getResource()!=null){
                    HBox resourceHBox=new HBox();
                    Rectangle resource=new Rectangle();
                    ImagePattern resourceImage=new ImagePattern(new Image(getClass().getResource("/images/Resources/"+tile.getTile().getResource().getName()+".png").toExternalForm()));
                    resource.setHeight(100);
                    resource.setWidth(100);
                    resource.setFill(resourceImage);
                    resourceHBox.getChildren().add(resource);
                    Text resourceName=new Text("    Resource: "+tile.getTile().getResource().getName());
                    Text resourceValues=new Text("    Resource Values: Gold: "+tile.getTile().getResource().getGold()+"  Food: "+tile.getTile().getResource().getFood()+"   Production: "+tile.getTile().getResource().getProduction());
                    resourceName.setFill(Color.WHITE);
                    resourceName.getStyleClass().add("tileInfo");
                    resourceValues.setFill(Color.WHITE);
                    resourceValues.getStyleClass().add("tileInfo");
                    VBox resourceInfo=new VBox();
                    resourceInfo.getChildren().add(resourceName);
                    resourceInfo.getChildren().add(resourceValues);
                    resourceHBox.getChildren().add(resourceInfo);
                    infos.getChildren().add(resourceHBox);
                }
                Text tileInfo=new Text("Tile Values: Gold: "+tile.getTile().getGold()+"  Production:  "+tile.getTile().getProduction()+"  Food:  "+tile.getTile().getFood());
                tileInfo.setFill(Color.WHITE);
                tileInfo.getStyleClass().add("tileInfo");
                infos.getChildren().add(tileInfo);
                tile.setOnMouseClicked(mouseEvent -> {
                    if (tileInformation != null) {
                        tileMap.getChildren().remove(tileInformation);
                    }
                    if(!tile.getTile().getVisibilityForUser(civilizationController.getTurn()).equals("fog of war")) {
                        if(!(tileInformation!=null&&tileInformation.equals(infos))) {
                            tileMap.getChildren().add(infos);
                            tileInformation = infos;
                        }else {
                            tileInformation=null;
                        }
                    }else tileInformation=null;
                });
                if(tile.getTile().getFeature()!=null){
                    tile.getFeature().setOnMouseClicked(mouseEvent -> {
                        if (tileInformation != null) {
                            tileMap.getChildren().remove(tileInformation);
                        }
                        if(!tile.getTile().getVisibilityForUser(civilizationController.getTurn()).equals("fog of war")) {
                            if(!(tileInformation!=null&&tileInformation.equals(infos))) {
                                tileMap.getChildren().add(infos);
                                tileInformation = infos;
                            }else {
                                tileInformation=null;
                            }
                        }else tileInformation=null;
                    });
                }
                dy = dy==y ? dy+size*v : y;
            }
        }
    }

    public void move(KeyEvent keyEvent){
        if(tileInformation!=null){
            tileMap.getChildren().remove(tileInformation);
            tileInformation=null;
        }
        double dx=0,dy=0;
        if(keyEvent.getCode().getName().equals("Right") && tiles[civilizationController.getMapHeight()-1][civilizationController.getMapWidth()-1].getPoints().get(4)>1280){
                dx = -10.0;
                dy = 0.0;
        }
        if(keyEvent.getCode().getName().equals("Left") && tiles[0][0].getPoints().get(10)<0){
            dx = 10.0;
            dy = 0.0;
        }
        if(keyEvent.getCode().getName().equals("Up") && tiles[0][0].getPoints().get(1)<0){
            dx = 0.0;
            dy = 10.0;
        }
        if(keyEvent.getCode().getName().equals("Down") && tiles[civilizationController.getMapHeight()-1][1].getPoints().get(7)>620){
            dx = 0.0;
            dy = -10.0;
        }
        for(int i=0;i<civilizationController.getMapHeight();i++){
            for(int j=0;j<civilizationController.getMapWidth();j++){
                tiles[i][j].move(dx,dy);
            }
        }
    }
}