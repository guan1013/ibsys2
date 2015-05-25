package de.hska.centurion;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.input.components.Article;
import de.hska.centurion.domain.input.components.Order;
import de.hska.centurion.domain.input.components.WorkplaceCosts;
import de.hska.centurion.domain.input.components.WorkplaceWaiting;

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

			// LAGERHAUS ARTIKEL
			System.out
					.println("-----------------------------------------------------------------------");
			System.out.println("WAREHOUSE:");
			List<Article> articles = results.getWarehouseStock().getArticles();
			for (Article a : articles) {
				System.out.println(a);
			}
			System.out
					.println("-----------------------------------------------------------------------");

			System.out
					.println("-----------------------------------------------------------------------");
			System.out.println("INWARD STOCK MOVEMENT:");
			List<Order> orders = results.getInwardStockMovement().getOrders();
			for (Order o : orders) {
				System.out.println(o);
			}
			System.out
					.println("-----------------------------------------------------------------------");

			System.out
					.println("-----------------------------------------------------------------------");
			System.out.println("FUTURE INWARD STOCK MOVEMENT:");
			List<Order> ordersFuture = results.getFutureInwardStockMovement()
					.getOrders();
			for (Order o : ordersFuture) {
				System.out.println(o);
			}
			System.out
					.println("-----------------------------------------------------------------------");

			System.out
					.println("-----------------------------------------------------------------------");
			System.out.println("IDLE TIME COSTS:");
			List<WorkplaceCosts> workplaces = results.getIdleTimeCosts()
					.getWorkplaces();
			for (WorkplaceCosts w : workplaces) {
				System.out.println(w);
			}
			System.out.println("Sum: " + results.getIdleTimeCosts().getSum());
			System.out
					.println("-----------------------------------------------------------------------");

			System.out
					.println("-----------------------------------------------------------------------");
			System.out.println("WAITING LIST WORKSTATIONS:");
			List<WorkplaceWaiting> workplacesWaiting = results
					.getWaitingListWorkstations().getWorkplaces();
			for (WorkplaceWaiting w : workplacesWaiting) {
				System.out.println(w);
			}
			System.out
					.println("-----------------------------------------------------------------------");

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
