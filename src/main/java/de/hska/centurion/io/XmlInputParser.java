package de.hska.centurion.io;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.input.components.Article;
import de.hska.centurion.domain.input.components.Batch;
import de.hska.centurion.domain.input.components.CompletedOrder;
import de.hska.centurion.domain.input.components.CycleOrder;
import de.hska.centurion.domain.input.components.MissingPart;
import de.hska.centurion.domain.input.components.Order;
import de.hska.centurion.domain.input.components.WorkplaceCosts;
import de.hska.centurion.domain.input.components.WorkplaceOrder;
import de.hska.centurion.domain.input.components.WorkplaceWaiting;

public class XmlInputParser {

	private static void generateOutputXmlExample() {

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
	}

	public static Results parseXmlFile(String path) throws JAXBException {

		File file = new File(path);
		JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Results results = (Results) jaxbUnmarshaller.unmarshal(file);

//		printOnConsolue(results);

		return results;
	}

	private static void printOnConsolue(Results results) {
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

		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println("WAITING LIST STOCK:");
		List<MissingPart> missingParts = results.getWaitingListStock()
				.getMissingParts();
		for (MissingPart m : missingParts) {
			System.out.println(m);
		}
		System.out
				.println("-----------------------------------------------------------------------");

		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println("ORDERS IN WORK:");
		List<WorkplaceOrder> workplaceOrders = results.getOrdersInWork()
				.getWorkplaceOrders();

		for (WorkplaceOrder wO : workplaceOrders) {
			System.out.println(wO);
		}
		System.out
				.println("-----------------------------------------------------------------------");

		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println("COMPLETED ORDERS:");
		List<CompletedOrder> completedOrders = results.getCompletedOrders()
				.getCompletedOrders();

		for (CompletedOrder cO : completedOrders) {
			System.out.println(cO);
			for (Batch b : cO.getBatches()) {
				System.out.println(b);
			}
		}
		System.out
				.println("-----------------------------------------------------------------------");

		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println("CYCLE TIMES:");
		System.out.println("Started orders: "
				+ results.getCycleTimes().getStartedOrders());
		System.out.println("Waiting orders: "
				+ results.getCycleTimes().getWaitingOrders());
		List<CycleOrder> cycleOrders = results.getCycleTimes().getOrders();

		for (CycleOrder cO : cycleOrders) {
			System.out.println(cO);

		}
		System.out
				.println("-----------------------------------------------------------------------");

		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println("RESULT:");

		System.out.println("GENERAL");
		System.out.println(" - CAPACITY");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getCapacity().getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getCapacity()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getCapacity().getAll());
		System.out.println(" - POSSIBLE CAPACITY");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getPossibleCapacity()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getPossibleCapacity()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getPossibleCapacity()
						.getAll());
		System.out.println(" - REL POSSIBLE NORMAL CAPACITY");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral()
						.getRelPossibleNormalCapacity().getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral()
						.getRelPossibleNormalCapacity().getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral()
						.getRelPossibleNormalCapacity().getAll());
		System.out.println(" - PRODUCTIVE TIME");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getProductiveTime()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getProductiveTime()
						.getAverageStr());
		System.out
				.println(" -- All: "
						+ results.getResult().getGeneral().getProductiveTime()
								.getAll());
		System.out.println(" - EFFIENCY");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getEffiency().getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getEffiency()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getEffiency().getAll());
		System.out.println(" - SELL WISH");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getSellWish().getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getSellWish()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getSellWish().getAll());
		System.out.println(" - SALES QUANTITY");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getSalesQuantity()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getSalesQuantity()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getSalesQuantity().getAll());
		System.out.println(" - delivery reliability ");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getDeliveryReliability()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getDeliveryReliability()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getDeliveryReliability()
						.getAll());
		System.out.println(" - IDLE TIME");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getIdleTime().getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getIdleTime()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getIdleTime().getAll());

		System.out.println(" - IDLE TIME COSTS");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getIdleTimeCosts()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getIdleTimeCosts()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getIdleTimeCosts().getAll());

		System.out.println(" - STORE VALUE");
		System.out
				.println(" -- Current: "
						+ results.getResult().getGeneral().getStoreValue()
								.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getStoreValue()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getStoreValue().getAll());

		System.out.println(" - STORAGE COSTS");
		System.out.println(" -- Current: "
				+ results.getResult().getGeneral().getStorageCosts()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getGeneral().getStorageCosts()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getGeneral().getStorageCosts().getAll());

		System.out.println(" DEFECTIVE GOODS");

		System.out.println(" - QUANTITY");
		System.out.println(" -- Current: "
				+ results.getResult().getDefectiveGoods().getQuantity()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getDefectiveGoods().getQuantity()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getDefectiveGoods().getQuantity()
						.getAll());

		System.out.println(" - COSTS");
		System.out.println(" -- Current: "
				+ results.getResult().getDefectiveGoods().getCosts()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getDefectiveGoods().getCosts()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getDefectiveGoods().getCosts().getAll());

		System.out.println("NORMAL SALE");
		System.out.println(" - SALES PRICE");
		System.out.println(" -- Current: "
				+ results.getResult().getNormalSale().getSalesprice()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getNormalSale().getSalesprice()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getNormalSale().getSalesprice().getAll());
		System.out.println(" - PROFIT");
		System.out.println(" -- Current: "
				+ results.getResult().getNormalSale().getProfit().getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getNormalSale().getProfit()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getNormalSale().getProfit().getAll());

		System.out.println(" - PROFIT PER UNIT");
		System.out.println(" -- Current: "
				+ results.getResult().getNormalSale().getProfitPerUnit()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getNormalSale().getProfitPerUnit()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getNormalSale().getProfitPerUnit()
						.getAll());
		System.out.println("DIRECT SALE");
		System.out.println(" - PROFIT");
		System.out.println(" -- Current: "
				+ results.getResult().getDirectSale().getProfit().getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getDirectSale().getProfit()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getDirectSale().getProfit().getAll());
		System.out.println(" - CONTRACT PENALTY");
		System.out.println(" -- Current: "
				+ results.getResult().getDirectSale().getContractPenalty()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getDirectSale().getContractPenalty()
						.getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getDirectSale().getContractPenalty()
						.getAll());
		System.out.println("MARKETPLACE SELL");
		System.out.println(" - PROFIT");
		System.out.println(" -- Current: "
				+ results.getResult().getMarketplaceSale().getProfit()
						.getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getMarketplaceSale().getProfit()
						.getAverageStr());
		System.out
				.println(" -- All: "
						+ results.getResult().getMarketplaceSale().getProfit()
								.getAll());
		System.out.println("SUMMARY");
		System.out.println(" - PROFIT");
		System.out.println(" -- Current: "
				+ results.getResult().getSummary().getProfit().getCurrent());
		System.out.println(" -- Average: "
				+ results.getResult().getSummary().getProfit().getAverageStr());
		System.out.println(" -- All: "
				+ results.getResult().getSummary().getProfit().getAll());

		System.out
				.println("-----------------------------------------------------------------------");
	}
}
