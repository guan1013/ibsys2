package de.hska.centurion;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.input.categories.CompletedOrders;
import de.hska.centurion.domain.input.components.Article;
import de.hska.centurion.domain.input.components.Batch;
import de.hska.centurion.domain.input.components.CompletedOrder;
import de.hska.centurion.domain.input.components.CycleOrder;
import de.hska.centurion.domain.input.components.MissingPart;
import de.hska.centurion.domain.input.components.Order;
import de.hska.centurion.domain.input.components.WorkplaceCosts;
import de.hska.centurion.domain.input.components.WorkplaceOrder;
import de.hska.centurion.domain.input.components.WorkplaceWaiting;
import de.hska.centurion.io.XmlInputParser;

/**
 *
 */
public class App {
	public static void main2(String[] args) {

		try {

			Results results = XmlInputParser
					.parseXmlFile("C:/Users/Amin/Desktop/output2.xml");

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
