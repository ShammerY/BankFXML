package com.example.seguimientotable;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {
    public static File path;
    @Override
    public void start(Stage stage) throws IOException {
        createDataFolder();
        loadData();
        openWindow("bankTable.fxml");
    }
    public static void openWindow(String path){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(path));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Bank table!");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    private void createDataFolder(){
        File folder = new File(System.getProperty("user.dir")+"/data");
        if(!folder.exists()){
            folder.mkdirs();
        }
        path = new File(folder,"bankData.txt");
    }
    private void loadData() throws IOException {
        try{
            FileInputStream fis = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            ObservableList<Movement> list = MovementList.getInstance().getMovements();
            while((line=reader.readLine())!=null){
                String[] content = line.split("::");
                list.add(new Movement(Double.parseDouble(content[0]),content[1],content[2],calculateMovementType(content[3])));
            }
            fis.close();
        }catch(Exception ex){
            FileOutputStream fos = new FileOutputStream(path);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write("");
            writer.flush();
            fos.close();
        }
    }
    public static void saveData(){
        ObservableList<Movement> list = MovementList.getInstance().getMovements();
        try{
            FileOutputStream fos = new FileOutputStream(path);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            StringBuilder data = new StringBuilder();
            for(Movement m:list){
                data.append(toString(m)+"\n");
            }
            writer.write(data.toString());
            writer.flush();
            fos.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private static String toString(Movement m){
        return m.getMonto()+"::"+m.getDescripcion()+"::"+m.getFecha()+"::"+m.getTipo();
    }
    private MovementType calculateMovementType(String type){
        if(type.equals("INGRESO")){
            return MovementType.INGRESO;
        }else if(type.equals("GASTO")){
            return MovementType.GASTO;
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}