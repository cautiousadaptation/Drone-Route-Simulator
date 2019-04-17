package util;

import controller.*;
import model.entity.Antenna;
import model.entity.Hospital;
import model.entity.River;
import model.entity.boat.Boat;
import model.entity.drone.Drone;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import util.exceptions.ClickOutsideRegionException;
import util.exceptions.MinimumHospitalQuantityException;
import view.CellView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

abstract public class EnvironmentMarshal {

    public static boolean serialize (File file) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element environmentElements = document.createElement(ConstantXml.ROOT_ELEMENT);
        document.appendChild(environmentElements);

        //RIVER
        Element riverElements = document.createElement(ConstantXml.ROOT_RIVER_ELEMENT);
        environmentElements.appendChild(riverElements);

        for(River river : RiverController.getInstance().getRiverMap().values()){

            Element riverElement = document.createElement(ConstantXml.RIVER_ELEMENT);

            riverElement.setAttribute(ConstantXml.UNIQUE_ID_ATTRIBUTE,river.getUniqueID());
            riverElement.setAttribute(ConstantXml.COLUMN_POSITION_ATTRIBUTE, String.valueOf(river.getColumnPosition()));
            riverElement.setAttribute(ConstantXml.ROW_POSITION_ATTRIBUTE, String.valueOf(river.getRowPosition()));

            riverElements.appendChild(riverElement);

        }



        //HOSPITAL
        Element hospitalElements = document.createElement(ConstantXml.ROOT_HOSPITAL_ELEMENT);
        environmentElements.appendChild(hospitalElements);

        for(Hospital hospital : HospitalController.getInstance().getHospitalMap().values()){

            Element hospitalElement = document.createElement(ConstantXml.HOSPITAL_ELEMENT);

            hospitalElement.setAttribute(ConstantXml.UNIQUE_ID_ATTRIBUTE,hospital.getUniqueID());
            hospitalElement.setAttribute(ConstantXml.COLUMN_POSITION_ATTRIBUTE, String.valueOf(hospital.getColumnPosition()));
            hospitalElement.setAttribute(ConstantXml.ROW_POSITION_ATTRIBUTE, String.valueOf(hospital.getRowPosition()));

            hospitalElements.appendChild(hospitalElement);

        }

        //ANTENNA
        Element antennaElements = document.createElement(ConstantXml.ROOT_ANTENNA_ELEMENT);
        environmentElements.appendChild(antennaElements);

        for(Antenna antenna : AntennaController.getInstance().getAntennaMap().values()){

            Element antennaElement = document.createElement(ConstantXml.ANTENNA_ELEMENT);

            antennaElement.setAttribute(ConstantXml.UNIQUE_ID_ATTRIBUTE, antenna.getUniqueID());
            antennaElement.setAttribute(ConstantXml.COLUMN_POSITION_ATTRIBUTE, String.valueOf(antenna.getColumnPosition()));
            antennaElement.setAttribute(ConstantXml.ROW_POSITION_ATTRIBUTE, String.valueOf(antenna.getRowPosition()));

            antennaElements.appendChild(antennaElement);

        }


        //DRONE
        Element droneElements = document.createElement(ConstantXml.ROOT_DRONE_ELEMENT);
        environmentElements.appendChild(droneElements);

        for(Drone drone : DroneController.getInstance().getDroneMap().values()){

            Element droneElement = document.createElement(ConstantXml.DRONE_ELEMENT);

            droneElement.setAttribute(ConstantXml.UNIQUE_ID_ATTRIBUTE, drone.getUniqueID());
            droneElement.setAttribute(ConstantXml.COLUMN_POSITION_ATTRIBUTE, String.valueOf(drone.getInitialPositionJ()));
            droneElement.setAttribute(ConstantXml.ROW_POSITION_ATTRIBUTE, String.valueOf(drone.getInitialPosistionI()));

            droneElements.appendChild(droneElement);

        }

        //BOAT
        Element boatElements = document.createElement(ConstantXml.ROOT_BOAT_ELEMENT);
        environmentElements.appendChild(boatElements);

        for(Boat boat : BoatController.getInstance().getBoatMap().values()){

            Element boatElement = document.createElement(ConstantXml.BOAT_ELEMENT);

            boatElement.setAttribute(ConstantXml.UNIQUE_ID_ATTRIBUTE, boat.getUniqueID());
            boatElement.setAttribute(ConstantXml.COLUMN_POSITION_ATTRIBUTE, String.valueOf(boat.getInitialCollunmPosition()));
            boatElement.setAttribute(ConstantXml.ROW_POSITION_ATTRIBUTE, String.valueOf(boat.getInitialRowPosition()));

            boatElements.appendChild(boatElement);

        }


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);

        transformer.transform(domSource, streamResult);

        return true;



    }

    public static boolean parser(File selectedFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document document = builder.parse(selectedFile);
        Element root = document.getDocumentElement();

       Node rootRiverElement = root.getElementsByTagName(ConstantXml.ROOT_RIVER_ELEMENT).item(0);

       //RIVER
       for(int i=0; i<rootRiverElement.getChildNodes().getLength(); i++){
           Node riverNode = rootRiverElement.getChildNodes().item(i);

           if(riverNode.getNodeName().equals("#text")){ // I dont now why this problem
               continue;
           }

           String uniqueID = riverNode.getAttributes().getNamedItem(ConstantXml.UNIQUE_ID_ATTRIBUTE).getNodeValue();
           int columnPosition = Integer.parseInt(riverNode.getAttributes().getNamedItem(ConstantXml.COLUMN_POSITION_ATTRIBUTE).getNodeValue());
           int rowPosition = Integer.parseInt(riverNode.getAttributes().getNamedItem(ConstantXml.ROW_POSITION_ATTRIBUTE).getNodeValue());

           CellController cellController = CellController.getInstance();
           CellView cellView = cellController.getCellViewFrom(rowPosition,columnPosition);

           EnvironmentController environmentController = EnvironmentController.getInstance();

           try {
               environmentController.createRiver(uniqueID, cellView);
           } catch (ClickOutsideRegionException e) {
               e.printStackTrace();
           }


       }


       //HOSPITAL
        Node rootHospitalElement = root.getElementsByTagName(ConstantXml.ROOT_HOSPITAL_ELEMENT).item(0);


        for(int i=0; i<rootHospitalElement.getChildNodes().getLength(); i++){
            Node hospitalNode = rootHospitalElement.getChildNodes().item(i);

            if(hospitalNode.getNodeName().equals("#text")){ // I dont now why this problem
                continue;
            }

            String uniqueID = hospitalNode.getAttributes().getNamedItem(ConstantXml.UNIQUE_ID_ATTRIBUTE).getNodeValue();
            int columnPosition = Integer.parseInt(hospitalNode.getAttributes().getNamedItem(ConstantXml.COLUMN_POSITION_ATTRIBUTE).getNodeValue());
            int rowPosition = Integer.parseInt(hospitalNode.getAttributes().getNamedItem(ConstantXml.ROW_POSITION_ATTRIBUTE).getNodeValue());

            CellController cellController = CellController.getInstance();
            CellView cellView = cellController.getCellViewFrom(rowPosition,columnPosition);

            EnvironmentController environmentController = EnvironmentController.getInstance();

            try {
                environmentController.createHospital(uniqueID, cellView);
            } catch (ClickOutsideRegionException e) {
                e.printStackTrace();
            }


        }

        //ANTENNA
        Node rootAntennaElement = root.getElementsByTagName(ConstantXml.ROOT_ANTENNA_ELEMENT).item(0);


        for(int i=0; i<rootAntennaElement.getChildNodes().getLength(); i++){
            Node antennaNode = rootAntennaElement.getChildNodes().item(i);

            if(antennaNode.getNodeName().equals("#text")){ // I dont now why this problem
                continue;
            }

            String uniqueID = antennaNode.getAttributes().getNamedItem(ConstantXml.UNIQUE_ID_ATTRIBUTE).getNodeValue();
            int columnPosition = Integer.parseInt(antennaNode.getAttributes().getNamedItem(ConstantXml.COLUMN_POSITION_ATTRIBUTE).getNodeValue());
            int rowPosition = Integer.parseInt(antennaNode.getAttributes().getNamedItem(ConstantXml.ROW_POSITION_ATTRIBUTE).getNodeValue());

            CellController cellController = CellController.getInstance();
            CellView cellView = cellController.getCellViewFrom(rowPosition,columnPosition);

            EnvironmentController environmentController = EnvironmentController.getInstance();

            try {
                environmentController.createAntenna(uniqueID, cellView);
            } catch (ClickOutsideRegionException e) {
                e.printStackTrace();
            }


        }


        //DRONE
        Node rootDroneElement = root.getElementsByTagName(ConstantXml.ROOT_DRONE_ELEMENT).item(0);


        for(int i=0; i<rootDroneElement.getChildNodes().getLength(); i++){
            Node droneNode = rootDroneElement.getChildNodes().item(i);

            if(droneNode.getNodeName().equals("#text")){ // I dont now why this problem
                continue;
            }

            String uniqueID = droneNode.getAttributes().getNamedItem(ConstantXml.UNIQUE_ID_ATTRIBUTE).getNodeValue();
            int columnPosition = Integer.parseInt(droneNode.getAttributes().getNamedItem(ConstantXml.COLUMN_POSITION_ATTRIBUTE).getNodeValue());
            int rowPosition = Integer.parseInt(droneNode.getAttributes().getNamedItem(ConstantXml.ROW_POSITION_ATTRIBUTE).getNodeValue());

            CellController cellController = CellController.getInstance();
            CellView cellView = cellController.getCellViewFrom(rowPosition,columnPosition);

            EnvironmentController environmentController = EnvironmentController.getInstance();

            try {
                environmentController.createDrone(uniqueID, cellView);
            } catch (ClickOutsideRegionException e) {
                e.printStackTrace();
            } /*catch (MinimumHospitalQuantityException e) {
                e.printStackTrace();
            }*/


        }


        //BOAT
        Node rootBoatElement = root.getElementsByTagName(ConstantXml.ROOT_BOAT_ELEMENT).item(0);


        for(int i=0; i<rootBoatElement.getChildNodes().getLength(); i++){
            Node boatNode = rootBoatElement.getChildNodes().item(i);

            if(boatNode.getNodeName().equals("#text")){ // I dont now why this problem
                continue;
            }

            String uniqueID = boatNode.getAttributes().getNamedItem(ConstantXml.UNIQUE_ID_ATTRIBUTE).getNodeValue();
            int columnPosition = Integer.parseInt(boatNode.getAttributes().getNamedItem(ConstantXml.COLUMN_POSITION_ATTRIBUTE).getNodeValue());
            int rowPosition = Integer.parseInt(boatNode.getAttributes().getNamedItem(ConstantXml.ROW_POSITION_ATTRIBUTE).getNodeValue());

            CellController cellController = CellController.getInstance();
            CellView cellView = cellController.getCellViewFrom(rowPosition,columnPosition);

            EnvironmentController environmentController = EnvironmentController.getInstance();

            try {
                environmentController.createBoat(uniqueID, cellView);
            } catch (ClickOutsideRegionException e) {
                e.printStackTrace();
            }


        }




        return true;
    }
}
