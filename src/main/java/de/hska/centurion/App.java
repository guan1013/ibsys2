package de.hska.centurion;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.hska.centurion.domain.input.Article;
import de.hska.centurion.domain.input.Order;
import de.hska.centurion.domain.input.Results;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		System.out.println("Hello World!");

		// try {
		//
		// Results results = new Results();
		// results.setGame(266);
		// results.setGroup(6);
		// results.setPeriod(6);
		//
		// File file = new File("C:\\file.xml");
		// JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
		// Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		//
		// // output pretty printed
		// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		//
		// // jaxbMarshaller.marshal(results, file);
		// jaxbMarshaller.marshal(results, System.out);
		//
		// } catch (JAXBException e) {
		// e.printStackTrace();
		// }
		try {

			File file = new File("C:/Users/Amin/Desktop/output2.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Results results = (Results) jaxbUnmarshaller.unmarshal(file);
			System.out.println(results);
			List<Article> articles = results.getWarehouseStock().getArticles();
			for (Article a : articles) {
				System.out.println(a);
			}
			List<Order> orders = results.getInwardstockmovement().getOrders();
			for (Order o : orders) {
				System.out.println(o);
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
