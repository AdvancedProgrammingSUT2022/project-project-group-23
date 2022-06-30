package controller;

import database.TechnologyDatabase;
import database.TerrainDatabase;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CivilizationController extends GameController{

    private UnitController unitController;
    private CityController cityController;

    public CivilizationController(ArrayList<User> players) {
        GameController.players = players;
        GameController.civilizationController = this;
        tiles = new Tile[mapHeight][mapWidth];
        for (User user : players) user.newGame();
        currentPlayer = players.get(0);
        initializeMap();
        turn = 0;
        cityController = new CityController();
        unitController = new UnitController(cityController);
    }

    public void initializeMap() {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                tiles[i][j] = new Tile(i, j);
                for (int k = 0; k < players.size(); k++) {
                    tiles[i][j].setRuin(false);
                    tiles[i][j].setRuinFirst(false);
                    tiles[i][j].setVisibilityForUser("fog of war", k);
                }
                TerrainDatabase.addRandomTerrainAndFeatureToTile(tiles[i][j]);
                TerrainDatabase.addRandomResourceToTile(tiles[i][j]);
            }
        }
        for(int i=0;i<(mapWidth*mapHeight)/30;i++){
            Tile tile=tiles[new Random().nextInt(mapHeight)][new Random().nextInt(mapWidth)];
            tile.setRuinFirst(true);
            tile.setRuin(true);
        }
        addRiversToMap();
    }

    private void addRiversToMap() {
        Random random = new Random();
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                ArrayList<Integer> rivers = tiles[i][j].getRivers();
                for (int k = 1; k <= 6; k++) {
                    if (random.nextInt(10) == 0 && !rivers.contains(k)) {
                        rivers.add(k);
                        if (k == 1 && isCoordinateValid(i - 1, j)) tiles[i - 1][j].addRiver(4);
                        else if (k == 4 && isCoordinateValid(i + 1, j)) tiles[i + 1][j].addRiver(1);
                        else if (j % 2 == 0) {
                            if (k == 2 && isCoordinateValid(i - 1, j + 1)) tiles[i - 1][j + 1].addRiver(5);
                            else if (k == 3 && isCoordinateValid(i, j + 1)) tiles[i][j + 1].addRiver(6);
                            else if (k == 5 && isCoordinateValid(i, j - 1)) tiles[i][j - 1].addRiver(2);
                            else if (k == 6 && isCoordinateValid(i - 1, j - 1)) tiles[i - 1][j - 1].addRiver(3);
                        } else {
                            if (k == 2 && isCoordinateValid(i, j + 1)) tiles[i][j + 1].addRiver(5);
                            else if (k == 3 && isCoordinateValid(i + 1, j + 1)) tiles[i + 1][j + 1].addRiver(6);
                            else if (k == 5 && isCoordinateValid(i + 1, j - 1)) tiles[i + 1][j - 1].addRiver(2);
                            else if (k == 6 && isCoordinateValid(i, j - 1)) tiles[i][j - 1].addRiver(3);
                        }
                    }
                }
            }
        }
    }


    public String nextTurn() {


        String message;
        if(!(message = unitController.isTurnPossible()).equals("ok"))return message;
        if(currentPlayer.getCurrentStudy() == null && !(currentPlayer.getCities().isEmpty()))return "you have to choose a technology to research";
        if(!(message = cityController.nextTurn()).equals("ok"))return message;
        if(currentPlayer.getCurrentStudy() != null) {
            currentPlayer.getWaitedTechnologies().put(currentPlayer.getCurrentStudy().getName(), currentPlayer.getWaitedTechnologies().get(currentPlayer.getCurrentStudy().getName()) - currentPlayer.totalCup());
            if (currentPlayer.getWaitedTechnologies().get(currentPlayer.getCurrentStudy().getName()) <= 0) {
                currentPlayer.addNotification("you know have technology : " + currentPlayer.getCurrentStudy().getName());
                currentPlayer.addTechnology(currentPlayer.getCurrentStudy());
                currentPlayer.getWaitedTechnologies().remove(currentPlayer.getCurrentStudy().getName());
                currentPlayer.setCurrentStudy(null);
            }
        }



        turn = (turn + 1) % players.size();
        currentPlayer = players.get(turn);


        unitController.checkVisibility();
        selectedCity=null;
        selectedUnit=null;
        return "it's " + currentPlayer.getNickname() + " turn";
    }

    public ArrayList<Unit> showUnitsInfo()
    {
        return currentPlayer.getUnits();
    }

    public ArrayList<City> showCitiesInfo()
    {
        return currentPlayer.getCities();
    }

    public Technology showCurrentStudy(){
        return currentPlayer.getCurrentStudy();
    }


    public UnitController getUnitController() {
        return unitController;
    }

    public CityController getCityController() {
        return cityController;
    }


    public void studyTechnology(Technology technology){
        currentPlayer.setCurrentStudy(technology);
        HashMap<String,Integer> waitedTechnologies=currentPlayer.getWaitedTechnologies();
        if(!waitedTechnologies.containsKey(technology.getName())){
            waitedTechnologies.put(technology.getName(),technology.getCost());
        }
    }

    public String benefitsOfRuin(Tile tile){
        tile.setRuin(false);
        int gold=new Random().nextInt(10);
        for (City city : GameController.getCurrentPlayer().getCities()) {
            city.setCountOfCitizens(city.getCountOfCitizens()+1);
        }
        GameController.getCurrentPlayer().setGold(GameController.getCurrentPlayer().getGold()+gold);
        Technology technology= TechnologyDatabase.getTechnologies().get(new Random().nextInt(46));
        GameController.getCurrentPlayer().addTechnology(technology);
        return "benefits of ruined tile: +1 citizen, technology: "+technology.getName()+" unlocked, +"+gold+" gold";
    }


}
