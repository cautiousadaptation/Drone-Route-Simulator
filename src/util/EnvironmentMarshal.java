package util;

import controller.RiverController;
import model.entity.River;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

abstract public class EnvironmentMarshal {

    public static void serialize (File file){

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(RiverController.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.marshal(RiverController.getInstance(), System.out);

         /*   // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            for(River river : RiverController.getInstance().getRiverMap().values()){
                jaxbMarshaller.marshal(river, file);
                jaxbMarshaller.marshal(river, System.out);
            }
*/

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
