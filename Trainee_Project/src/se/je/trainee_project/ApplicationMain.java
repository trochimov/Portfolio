package se.je.trainee_project;

import se.je.trainee_project.Model.*;

import javax.xml.bind.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class ApplicationMain {

    public static void main(String[] args) throws JAXBException {

        // Setting context to package.
        String context = "se.je.trainee_project.Model";
        // Initializing JAXBContext.
        JAXBContext jaxbContext = JAXBContext.newInstance(context);

        // Creating Marshaller and setting output format.
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Creating UnMarshaller.
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // Instantiating some objects from the XSD generated classes and adding some data.
        ShipTo shipTo = new ShipTo();
        shipTo.setName("Johan Eriksson");
        shipTo.setAddress("Vartoftagatan 47B");
        shipTo.setCity("Stockholm");
        shipTo.setCountry("Sweden");

        Item item = new Item();
        item.setTitle("Guitar");
        item.setNote("Model SG");
        item.setPrice(BigDecimal.valueOf(1000));
        item.setQuantity(BigInteger.valueOf(5));

        Item item2 = new Item();
        item2.setTitle("Bass");
        item2.setNote("Model Thunderbird");
        item2.setPrice(BigDecimal.valueOf(2000));
        item2.setQuantity(BigInteger.valueOf(15));

        ShipOrder shipOrder = new ShipOrder();
        shipOrder.setOrderPerson("Johan Eriksson");
        shipOrder.setShipTo(shipTo);
        shipOrder.setItem(item);
        shipOrder.setItem(item2);
        shipOrder.setOrderId("1");

        // Marshall the data to XML to save in shipOrder.xml and to print out in console
        marshaller.marshal(shipOrder, new File("shipOrder.xml"));
        marshaller.marshal(shipOrder, System.out);

        // Reading the manually modified xml file with some added Items data.
        File file = new File("shipOrderModified.xml");
        // Unmarshalls the file and for each item prints it out in the console.
        ShipOrder shipOrders = (ShipOrder) unmarshaller.unmarshal(file);
        shipOrders.getItem().forEach(System.out::println);
    }
}
